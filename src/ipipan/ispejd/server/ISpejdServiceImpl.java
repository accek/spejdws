package ipipan.ispejd.server;

import ipipan.ispejd.client.ISpejdService;
import ipipan.ispejd.client.ISpejdServiceException;
import ipipan.spejdws.SpejdService;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ISpejdServiceImpl extends RemoteServiceServlet implements
		ISpejdService {
	private static final long serialVersionUID = -2431132885916335196L;

	private SpejdService getSpejdService() throws ISpejdServiceException {
		try {
			return new SpejdService(this.getServletConfig());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public String getPredefinedRuleSet(String name) throws ISpejdServiceException {
		try {
			return getSpejdService().getPredefinedRuleSet(name);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public String getPredefinedText(String name) throws ISpejdServiceException {
		try {
			return getSpejdService().getPredefinedText(name);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public String getVersion() throws ISpejdServiceException {
		try {
			return getSpejdService().getVersion();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public List<String> listPredefinedRuleSets() throws ISpejdServiceException {
		try {
			return getSpejdService().listPredefinedRuleSets();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public List<String> listPredefinedTexts() throws ISpejdServiceException {
		try {
			return getSpejdService().listPredefinedTexts();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public String parse(String rulesMode, String textMode, String rules,
			String text) throws ISpejdServiceException {
		try {
			return getSpejdService().parse(rulesMode, textMode, rules, text);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public String formatXmlAsHtml(String xml) throws ISpejdServiceException {
		try {
			return getSpejdService().formatXmlAsHtml(xml);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

	public String parseAndFormatAsHtml(String rulesMode, String textMode,
			String rules, String text) throws ISpejdServiceException {
		try {
			return getSpejdService().parseAndFormatAsHtml(rulesMode, textMode, rules, text);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISpejdServiceException(e);
		}
	}

}
