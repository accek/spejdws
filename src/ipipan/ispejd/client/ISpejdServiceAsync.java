package ipipan.ispejd.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ISpejdServiceAsync {

	void getPredefinedRuleSet(String name, AsyncCallback<String> callback);

	void getPredefinedText(String name, AsyncCallback<String> callback);

	void getVersion(AsyncCallback<String> callback);

	void listPredefinedRuleSets(AsyncCallback<List<String>> callback);

	void listPredefinedTexts(AsyncCallback<List<String>> callback);

	void parse(String rulesMode, String textMode, String rules, String text,
			AsyncCallback<String> callback);

	void formatXmlAsHtml(String xml, AsyncCallback<String> callback);

	void parseAndFormatAsHtml(String rulesMode, String textMode, String rules,
			String text, AsyncCallback<String> callback);

}
