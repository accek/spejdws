package ipipan.spejdws;

import ipipan.spejd.analyzer.DummyMorphosyntacticAnalyzer;
import ipipan.spejd.readers.MorphReader;
import ipipan.spejd.readers.PlainTextReader;
import ipipan.spejd.readers.Reader;
import ipipan.spejd.rules.Sentence;
import ipipan.spejd.util.Config;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;


public class SpejdService {
	public final String VERSION = "1.1";
	
	public final int MAX_RESPONSE_SIZE = 128*1024;

	private final ServletConfig config;
	
	public SpejdService() {
		super();
		this.config = SpejdXmlRpcServlet.getConfig();
	}
	
	public SpejdService(ServletConfig config) {
		super();
		this.config = config;
	}

	private String limitedResponse(String response) {
		if (response.length() > MAX_RESPONSE_SIZE)
			throw new RuntimeException("Data too large to return via XML-RPC.");
		return response;
	}

	public String getVersion() {
		return VERSION;
	}
		
	protected String getPredefinedResourceFilename(String category, String name) {
		return config.getServletContext().getRealPath(
				"resources/" + category + "/" + name);
	}
	
	protected List<String> listPredefinedResources(String category) {
		String path = getPredefinedResourceFilename(category, "");
		File file = new File(path);
		if (!file.isDirectory()) {
			throw new RuntimeException("Resource directory not found: " + path);
		}
		return Arrays.asList(file.list());
	}
	
	protected String getPredefinedResource(String category, String name) {
		InputStreamReader reader;
		try {
			FileInputStream stream = new FileInputStream(getPredefinedResourceFilename(category, name));
			reader = new InputStreamReader(stream, "UTF-8");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Resource not found: " + category + " " + name, e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("Decoding error for resource: " + category + " " + name, e);
		}
		
		StringBuilder builder = new StringBuilder();
		char[] buf = new char[4096];
		int r;
		
		try {
			while ((r = reader.read(buf)) != -1) {
				builder.append(buf, 0, r);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("I/O error reading resource: " + category + " " + name, e);
		}
		
		return builder.toString();
	}
	
	public String getPredefinedText(final String name) {
		return limitedResponse(getPredefinedResource("texts", name));
	}
	
	public List<String> listPredefinedTexts() {
		return listPredefinedResources("texts");
	}
	
	public String getPredefinedRuleSet(final String name) {
		return limitedResponse(getPredefinedResource("rulesets", name));
	}
	
	public List<String> listPredefinedRuleSets() {
		return listPredefinedResources("rulesets");
	}
	
	public String parse(String rulesMode, String textMode, String rules, String text) {
		if (rulesMode.equalsIgnoreCase("PREDEFINED") || rulesMode.equalsIgnoreCase("")) {
			rules = getPredefinedResource("rulesets", rules);
		} else if (rulesMode.equalsIgnoreCase("CUSTOM")) {
			// do nothing
		} else {
			throw new RuntimeException("Unknown rules mode: please pass either PREDEFINED (default) or CUSTOM");
		}
		
		ipipan.spejd.rules.RuleSet ruleSet = null;
		Config config = new Config();
		config.configure(new String[0], 0, 0);
		ByteArrayOutputStream logStream = new ByteArrayOutputStream();
		StringReader rulesReader = new StringReader(rules);
		ruleSet = new ipipan.spejd.rules.RuleSet(
				config, new BufferedReader(rulesReader), new PrintStream(logStream));
		
		Sentence chunk = new Sentence(config);
		Reader spejdReader;
		
		if (textMode.equalsIgnoreCase("PREDEFINED") || textMode.equalsIgnoreCase("")) {
			StringReader stringReader = new StringReader(getPredefinedResource("texts", text));
			spejdReader = new MorphReader(config, new BufferedReader(stringReader));
		} else if (textMode.equalsIgnoreCase("PLAIN")) {
			PlainTextReader plainTextReader = new PlainTextReader(config, new DummyMorphosyntacticAnalyzer(config));
			plainTextReader.readTextFile(new StringReader(text));
			spejdReader = plainTextReader;
		} else if (textMode.equalsIgnoreCase("PLAIN-TO-TAG")) {
			text = tagText(text);
			StringReader stringReader = new StringReader(text);
			spejdReader = new MorphReader(config, new BufferedReader(stringReader));
		} else if (textMode.equalsIgnoreCase("XML")) {
			StringReader stringReader = new StringReader(text);
			spejdReader = new MorphReader(config, new BufferedReader(stringReader));
		} else {
			throw new RuntimeException("Unknown text mode: please pass either PREDEFINED (default) or PLAIN or XML");
		}
		
		chunk.setIn(spejdReader);
		
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			chunk.setOut(new PrintStream(outStream, false, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("Unsupported encoding error while creating output stream", e);
		}
		
		int n = 0;
		System.err.println("Shallowparsing...");
		while (chunk.loadSentence()) {
			ruleSet.applyTo(chunk);
			chunk.printXML();
			n++;
			if (config.reportInterval > 0 && n % config.reportInterval == 0)
				System.err.println(n + " sentences parsed: ");
		}
		
		try {
			return outStream.toString("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("Error encoding result", e);
		}
	}

	private String tagText(String text) {
		try {
			TakipiClient client = new TakipiClient();
			Object[] params = new Object[]{text, new String("TXT"), new Integer(1)};
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<!DOCTYPE cesAna SYSTEM \"xcesAnaIPI.dtd\">\n" +
				"<cesAna xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
				"<chunkList>\n" +
				client.callTakipi("Tag", params) + "\n" +
				"</chunkList>\n" +
				"</cesAna>";
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error constructing Takipi client.", e);
		}
	}

	public String formatXmlAsHtml(String xml) {
		// Effectively disable validation -- we don't have required DTDs...
		xml = xml.replaceAll("<!DOCTYPE[^>]*>", "");
		return new MorphXMLFormatter().xmlToHtml(xml);
	}

	public String parseAndFormatAsHtml(String rulesMode, String textMode,
			String rules, String text) {
		return formatXmlAsHtml(parse(rulesMode, textMode, rules, text));
	}

}
