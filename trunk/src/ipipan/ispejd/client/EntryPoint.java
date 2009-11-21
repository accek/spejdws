package ipipan.ispejd.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EntryPoint implements com.google.gwt.core.client.EntryPoint {
	public static final Strings theStrings = (Strings)GWT.create(Strings.class);
	public static final ISpejdServiceAsync rpcClient = (ISpejdServiceAsync)GWT.create(ISpejdService.class);
	private static EntryPoint instance;
	
	private InputSection rulesSection;
	private InputSection textSection;
	private Button parseButton;
	private RootPanel rootPanel;
	private VerticalPanel indicatorPanel;
	private VerticalPanel resultsPanel;
	private SimplePanel separator;
	private HTML resultsHTML;
	
	public void onModuleLoad() {
		instance = this;
		
		DOM.getElementById("header").setInnerHTML(theStrings.header());
		RootPanel.get("title").add(new HTML(theStrings.title()));
		
		rootPanel = RootPanel.get("root");
		rootPanel.setWidth("100%");
		
		VerticalPanel rootVP = new VerticalPanel();
		rootVP.setWidth("100%");
		rootPanel.add(rootVP);
		
		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		splitPanel.setWidth("100%");
		splitPanel.setHeight("283px");
		rootVP.add(splitPanel);
		
		rulesSection = new InputSection("RuleSet", theStrings.rulesComboLabel());
		splitPanel.add(rulesSection);
		textSection = new InputSection("Text", theStrings.textsComboLabel(), true);
		splitPanel.add(textSection);
		
		parseButton = new Button(theStrings.parse());
		parseButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				parse();
			}
		});
		textSection.add(parseButton);
		textSection.setCellHorizontalAlignment(parseButton, HasHorizontalAlignment.ALIGN_RIGHT);
		
		separator = new SimplePanel();
		separator.addStyleName("separator");
		separator.setVisible(false);
		rootVP.add(separator);
		
		indicatorPanel = new VerticalPanel();
		indicatorPanel.addStyleName("indicator");
		indicatorPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		indicatorPanel.add(new Image("images/ajax-loader.gif"));
		indicatorPanel.add(new Label(theStrings.pleaseWait()));
		indicatorPanel.setVisible(false);
		rootVP.add(indicatorPanel);
		rootVP.setCellHorizontalAlignment(indicatorPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		resultsPanel = new VerticalPanel();
		resultsPanel.addStyleName("mypanel");
		resultsPanel.setVisible(false);
		rootVP.add(resultsPanel);
		
		resultsHTML = new HTML();
		resultsPanel.add(resultsHTML);
		
		SimplePanel separator2 = new SimplePanel();
		separator2.addStyleName("separator");
		rootVP.add(separator2);
		
		HTML footer = new HTML(theStrings.footer());
		footer.addStyleName("footer");
		rootVP.add(footer);
	}

	private void parse() {
		resultsPanel.setVisible(false);
		indicatorPanel.setVisible(true);
		separator.setVisible(true);
		rpcClient.parseAndFormatAsHtml(
				rulesSection.isPredefined() ? "PREDEFINED" : "CUSTOM",
				textSection.isPredefined() ? "PREDEFINED" :
					(textSection.isXML() ? "XML" : "PLAIN-TO-TAG"),
				rulesSection.isPredefined() ? rulesSection.getPredefinedName() : rulesSection.getData(),
				textSection.isPredefined() ? textSection.getPredefinedName() : textSection.getData(),
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						indicatorPanel.setVisible(false);
						EntryPoint.handleRpcError(caught);
					}
					public void onSuccess(String result) {
						resultsHTML.setHTML(result);
						indicatorPanel.setVisible(false);
						resultsPanel.setVisible(true);
					}
				});
	}

	public static void handleRpcError(Throwable caught) {
		instance.resultsHTML.setHTML("<div class='error'><h3>" + theStrings.errorTitle() + "</h3><pre>"
				+ caught.getMessage().replaceAll("^\\s+", "") + "</pre></div>");
		instance.indicatorPanel.setVisible(false);
		instance.resultsPanel.setVisible(true);
	}
}
