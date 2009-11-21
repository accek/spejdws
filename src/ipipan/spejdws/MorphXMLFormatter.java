package ipipan.spejdws;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MorphXMLFormatter {
	private StringBuilder htmlBuilder;
	private List<String> synHeads;
	private List<String> semHeads;
	private List<String> groupTypes;
	private Map<String, Integer> groupIds;
	private int groupLevel;
	private boolean noSpace;
	
	public String xmlToHtml(String xml) {
		htmlBuilder = new StringBuilder();
		synHeads = new ArrayList<String>();
		semHeads = new ArrayList<String>();
		groupTypes = new ArrayList<String>();
		groupLevel = 0;
		groupIds = new HashMap<String, Integer>();
		
		InputStream is;
		
		try {
			is = new ByteArrayInputStream(xml.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("XML Encoding Error: " + e, e);
		}
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document doc;
		try {
			doc = dbf.newDocumentBuilder().parse(is);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("XML Parse Error: " + e, e);
		}
		NodeList chunkLists = doc.getElementsByTagName("chunkList");
		for (int i = 0; i < chunkLists.getLength(); i++) {
			parseElements(chunkLists.item(i));
		}
		return htmlBuilder.toString();
	}
	
	private Integer groupId(String name) {
		Integer id = groupIds.get(name);
		if (id == null) {
			id = groupIds.size();
			groupIds.put(name, id);
		}
		return id;
	}

	private void parseElements(Node node) {
		Element el = (Element)node;
		Node child = el.getFirstChild();
		while (child != null) {
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				Element childEl = (Element)child;
				String tag = childEl.getTagName();
				if (tag.equals("chunk")) {
					parseChunk(childEl);
				} else if (tag.equals("tok") || tag.equals("syntok")) {
					parseToken(childEl);
				} else if (tag.equals("group")) {
					parseGroup(childEl);
				}
				
				noSpace = tag.equals("ns");
			}
			child = child.getNextSibling();
		}
	}

	private void parseChunk(Element el) {
		String type = el.getAttribute("type");
		if (type.equals("p")) {
			// Paragraph.
			htmlBuilder.append("<p>");
			noSpace = true;
			parseElements(el);
			htmlBuilder.append("</p>");
		} else {
			parseElements(el);
		}
	}

	private void parseGroup(Element el) {
		emitSpace();
		generateElementPre(el);
		synHeads.add(el.getAttribute("synh"));
		semHeads.add(el.getAttribute("semh"));
		groupTypes.add(el.getAttribute("type"));
		groupLevel++;
		parseElements(el);
		groupLevel--;
		synHeads.remove(groupLevel);
		semHeads.remove(groupLevel);
		groupTypes.remove(groupLevel);
		generateElementHeads(el);
		generateElementPost(el);
	}

	private void generateElementPre(Element el) {
		htmlBuilder.append("<div class='element" + (groupTypes.size() > 0 ? "" : " outerelement") + "'>");
		if (el.hasAttribute("type")) {
			String type = el.getAttribute("type");
			htmlBuilder.append("<div class='elementtype elementtype" + groupId(type) + "'>" + type + "</div>");
		}
		htmlBuilder.append("<div class='elementbody'>");
	}

	private void generateElementHeads(Element el) {
		htmlBuilder.append("</div>");
		String id = el.getAttribute("id");
		for (int i = groupTypes.size() - 1; i >= 0; i--) {
			if (id.equals(synHeads.get(i))) {
				Integer gid = groupId(groupTypes.get(i));
				htmlBuilder.append("<div class='synh head" + gid + "'></div>");
			}
			if (id.equals(semHeads.get(i))) {
				Integer gid = groupId(groupTypes.get(i));
				htmlBuilder.append("<div class='semh head" + gid + "'></div>");
			}
		}
	}
	
	private void generateElementPost(Element el) {
		htmlBuilder.append("</div>");
	}
	
	private Set<String> getSimpleTags(Element token, String lookupAttribute, boolean selectedByDefault) {
		Set<String> tags = new HashSet<String>();
		NodeList lexs = token.getElementsByTagName("lex");
		for (int i = 0; i < lexs.getLength(); i++) {
			Element el = (Element)lexs.item(i);
			boolean selected;
			if (el.hasAttribute(lookupAttribute))
				selected = el.getAttribute(lookupAttribute).equals("1");
			else
				selected = selectedByDefault;
			if (!selected)
				continue;
			String ctag = el.getElementsByTagName("ctag").item(0).getFirstChild().getNodeValue();
			tags.add(ctag);
		}
		return tags;
	}

	private void parseToken(Element el) {
		emitSpace();
		generateElementPre(el);
		String orth = el.getElementsByTagName("orth").item(0).getFirstChild().getNodeValue();
		htmlBuilder.append("<div class='orth'>" + orth + "</div>");
		generateElementHeads(el);
		htmlBuilder.append("<div class='tag' title='");
		Set<String> tags = getSimpleTags(el, "disamb_sh", true);
		if (tags.size() >= 2) {
			for (String tag: tags) {
				htmlBuilder.append(tag + ' ');
			}
		}
		htmlBuilder.append("'>");
		String tag;
		if (tags.size() >= 1) {
			tag = tags.iterator().next() + (tags.size() > 1 ? "*" : "");
		} else {
			tag = "&nbsp;";
		}
		htmlBuilder.append(tag + "</div>");
		generateElementPost(el);
	}

	private void emitSpace() {
		if (!noSpace) {
			htmlBuilder.append("<span class='space'> </span>");
		}
		noSpace = false;
	}

}
