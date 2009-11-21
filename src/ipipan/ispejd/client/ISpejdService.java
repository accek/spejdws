package ipipan.ispejd.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc")
public interface ISpejdService extends RemoteService {
	public abstract String getVersion() throws ISpejdServiceException;
	public abstract String getPredefinedText(final String name) throws ISpejdServiceException;
	public abstract List<String> listPredefinedTexts() throws ISpejdServiceException;
	public abstract String getPredefinedRuleSet(final String name) throws ISpejdServiceException;
	public abstract List<String> listPredefinedRuleSets() throws ISpejdServiceException;
	public abstract String parse(String rulesMode, String textMode,
			String rules, String text) throws ISpejdServiceException;
	public abstract String formatXmlAsHtml(String xml) throws ISpejdServiceException;
	public abstract String parseAndFormatAsHtml(String rulesMode, String textMode,
			String rules, String text) throws ISpejdServiceException;
}