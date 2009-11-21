package ipipan.spejd.readers;

import ipipan.spejd.analyzer.DummyMorphosyntacticAnalyzer;
import ipipan.spejd.analyzer.MorphosyntacticAnalyzer;
import ipipan.spejd.entities.Entity;
import ipipan.spejd.util.Config;

import java.io.File;
import java.io.PrintStream;

/**
 * 
 * @author axw
 */
abstract public class Reader {
	protected Config config;

	public static final int UNKNOWN = -1;
	public static final int AUTO = 0;
	public static final int PLAIN_TXT = 1;
	public static final int XCES_ANA = 2;

	static int readerType = UNKNOWN;

	/**
	 * Get the next token from the input.
	 * 
	 * @return segment or no space, null if end of sentence or file
	 */
	abstract public Entity loadToken();

	/**
	 * Move on to the next sentence.
	 * 
	 * @param out
	 *            result file
	 * @return true if there exists a next sentence
	 */
	abstract public boolean nextSentence(PrintStream out);

	protected Reader(Config config) {
		this.config = config;
	}
	
	private static MorphosyntacticAnalyzer getMorphosyntacticAnalyzer(Config config) {
		// TODO Morfeusz?
		return new DummyMorphosyntacticAnalyzer(config);
	}

	static Reader getTextReader(Config config, File path) {
		PlainTextReader plainTextReader = new PlainTextReader(config,
				getMorphosyntacticAnalyzer(config));
		plainTextReader.readTextFile(path.getPath(), config.inputEncoding);
		return plainTextReader;
	}

	static int getReaderType(String type) {
		if (type.equals("auto"))
			return AUTO;
		if (type.equals("txt"))
			return PLAIN_TXT;
		if (type.equals("xcesAna"))
			return XCES_ANA;
		System.out.println("Unknown inputType [" + type
				+ "], please correct configuration.");
		return UNKNOWN;
	}

	static int getFileType(File path) {
		String f = path.getName();
		if (f.equals("morph.xml") || f.equals("morph.xml.gz"))
			return XCES_ANA;
		if (f.endsWith(".txt"))
			return PLAIN_TXT;
		System.out.println("Unknown type of file: " + f);
		return UNKNOWN;
	}

	/**
	 * Get the appropriate reader for this path.
	 */
	public static Reader getReader(Config config, File path) {
		if (readerType == UNKNOWN)
			readerType = getReaderType(config.inputType);

		int type = readerType == AUTO ? getFileType(path) : readerType;

		switch (type) {
		case PLAIN_TXT:
			return getTextReader(config, path);
		case XCES_ANA:
			return new MorphReader(config, path);
		default:
			return null;
		}
	}
}
