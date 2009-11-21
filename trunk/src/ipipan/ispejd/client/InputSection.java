package ipipan.ispejd.client;

import java.util.Collections;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class InputSection extends VerticalPanel {
	private final String type;
	
	private final ListBox list;
	private final TextArea box;
	private final CheckBox xmlCheckbox;
	private boolean previewAvailable;
	
	public InputSection(String type, String comboLabelText, boolean showXmlCheckbox) {
		super();
		this.type = type;
		
		addStyleName("mypanel");
		setWidth("100%");
	
		HorizontalPanel hpanel = new HorizontalPanel();
		
		Label comboLabel = new Label(comboLabelText);
		hpanel.add(comboLabel);
		
		list = new ListBox();
		list.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				listChanged();
			}
		});	
		hpanel.add(list);
		
		xmlCheckbox = new CheckBox("XML");
		xmlCheckbox.setValue(true);
		if (showXmlCheckbox) {
			xmlCheckbox.setVisible(showXmlCheckbox);
			hpanel.add(xmlCheckbox);
		}
		
		add(hpanel);
		
		box = new TextArea();
		box.setWidth("100%");
		box.setHeight("200px");
		add(box);
		
		update();
	}

	public InputSection(String type, String comboLabelText) {
		this(type, comboLabelText, false);
	}

	public ListBox getList() {
		return list;
	}

	public TextArea getBox() {
		return box;
	}

	private void listChanged() {
		if (!isPredefined()) {
			// Custom.
			if (!previewAvailable)
				box.setText("");
			box.setEnabled(true);
			box.setFocus(true);
			xmlCheckbox.setEnabled(true);
			previewAvailable = true;
		} else {
			// Predefined.
			box.setEnabled(false);
			xmlCheckbox.setEnabled(false);
			getPredefinedResource(getPredefinedName(),
					new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							box.setText(EntryPoint.theStrings
									.previewNotAvailable());
							previewAvailable = false;
						}

						public void onSuccess(String result) {
							box.setText(result);
							xmlCheckbox.setValue(true);
							previewAvailable = true;
						}
					});
		}
	}
	
	private void getPredefinedResource(String name,
			AsyncCallback<String> callback) {
		if (this.type.equals("Text")) {
			EntryPoint.rpcClient.getPredefinedText(name, callback);
		} else if (this.type.equals("RuleSet")) {
			EntryPoint.rpcClient.getPredefinedRuleSet(name, callback);
		}
	}

	private void update() {
		list.setEnabled(false);
		box.setEnabled(false);
		
		listPredefinedResources(new AsyncCallback<List<String>>() {
			public void onFailure(Throwable caught) {
				EntryPoint.handleRpcError(caught);
			}

			public void onSuccess(List<String> result) {
				Collections.sort(result);
				list.clear();
				for (String rule : result) {
					list.addItem(rule);
				}
				list.addItem(EntryPoint.theStrings.custom());
				list.setSelectedIndex(0);
				list.setEnabled(true);
				listChanged();
			}
		});
	}
	
	private void listPredefinedResources(
			AsyncCallback<List<String>> callback) {
		if (this.type.equals("Text")) {
			EntryPoint.rpcClient.listPredefinedTexts(callback);
		} else if (this.type.equals("RuleSet")) {
			EntryPoint.rpcClient.listPredefinedRuleSets(callback);
		}
	}

	public boolean isPredefined() {
		return list.getSelectedIndex() != list.getItemCount() - 1;
	}
	
	public boolean isXML() {
		return xmlCheckbox.getValue();
	}
	
	public String getPredefinedName() {
		return list.getValue(list.getSelectedIndex());
	}
	
	public String getData() {
		return box.getText();
	}
}
