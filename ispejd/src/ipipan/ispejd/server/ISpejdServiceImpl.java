package ipipan.ispejd.server;

import ipipan.ispejd.client.ISpejdService;
import ipipan.ispejd.client.ISpejdServiceException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ISpejdServiceImpl extends RemoteServiceServlet implements
		ISpejdService {
	private static final long serialVersionUID = -2431132885916335196L;
	
	public static final String DEFAULT_URL = "http://chopin.ipipan.waw.pl:8081/spejdws/xmlrpc";
	public static final int REPLY_TIMEOUT_MS = 10000;
	
	protected XmlRpcClient client;
	
	
	private static List<String> convertObjectsArrayToStringList(Object[] array) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < array.length; i++)
			list.add(array[i].toString());
		return list;
	}

	
	public ISpejdServiceImpl() throws MalformedURLException {
		this(DEFAULT_URL);
	}

	public ISpejdServiceImpl(String url) throws MalformedURLException {
	    XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	    config.setServerURL(new URL(url));
	    config.setConnectionTimeout(REPLY_TIMEOUT_MS);
	    config.setReplyTimeout(REPLY_TIMEOUT_MS);
	    client = new XmlRpcClient();
	    client.setConfig(config);
	}

	public String getPredefinedRuleSet(String name) throws ISpejdServiceException {
		try {
			return (String) client.execute("SpejdService.getPredefinedRuleSet", new Object[]{name});
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public String getPredefinedText(String name) throws ISpejdServiceException {
		try {
			return (String) client.execute("SpejdService.getPredefinedText", new Object[]{name});
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public String getVersion() throws ISpejdServiceException {
		try {
			return (String) client.execute("SpejdService.getVersion", new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public List<String> listPredefinedRuleSets() throws ISpejdServiceException {
		try {
			Object[] response = (Object[])client.execute("SpejdService.listPredefinedRuleSets",
					new Object[]{});
			return convertObjectsArrayToStringList(response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}
	
	public List<String> listPredefinedTexts() throws ISpejdServiceException {
		try {
			Object[] response = (Object[])client.execute("SpejdService.listPredefinedTexts",
					new Object[]{});
			return convertObjectsArrayToStringList(response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public String parse(String rulesMode, String textMode, String rules,
			String text) throws ISpejdServiceException {
		try {
			return (String) client.execute("SpejdService.parse",
					new Object[]{rulesMode, textMode, rules, text});
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public String formatXmlAsHtml(String xml) throws ISpejdServiceException {
		try {
			return (String) client.execute("SpejdService.formatXmlAsHtml", new Object[]{xml});
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public String parseAndFormatAsHtml(String rulesMode, String textMode,
			String rules, String text) throws ISpejdServiceException {
		try {
			return (String) client.execute("SpejdService.parseAndFormatAsHtml",
					new Object[]{rulesMode, textMode, rules, text});
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

}
