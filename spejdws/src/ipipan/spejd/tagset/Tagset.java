/*
 * Copyright (C) 2007 by Instytut Podstaw Informatyki Polskiej
 * Akademii Nauk (IPI PAN; Institute of Computer Science, Polish
 * Academy of Sciences; cf. www.ipipan.waw.pl).  All rights reserved.
 *
 * This file is part of Spejd.
 *
 * Spejd is free software: it may be distributed and/or modified under 
 * the terms of the GNU General Public License version 3 as published 
 * by the Free Software Foundation and appearing in the file doc/gpl.txt
 * included in the packaging of this file.
 *
 * A commercial license is available from IPI PAN (contact
 * Michal.Ciesiolka@ipipan.waw.pl or ipi@ipipan.waw.pl for more
 * information).  Licensees holding a valid commercial license from IPI
 * PAN may use this file in accordance with that license.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING
 * THE WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE.
 */

package ipipan.spejd.tagset;

import ipipan.spejd.util.SimpleDict;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * A representation of corpora tagset. Provides information about possible
 * attributes for different parts of speech, aliases for the attributes and
 * possible attribute values.
 */
public class Tagset {
	public static final String undefinedValue = "-";
	public static final String orthAttr = "orth";
	public static final String baseAttr = "base";
	public static final String posAttr = "pos";
	public static final String tagDictFile = "tagdict.ini";

	static final String[] noValues = new String[0];
	static final String defaultCfgFile = "sample.cfg";

	private static final int LOAD_IGNORE = 0;
	private static final int LOAD_POS = 1;
	private static final int LOAD_NAMED = 2;
	private static final int LOAD_ATTR = 3;

	public static Tagset tagset;

	TreeMap<String, Attribute> attrByName;
	TreeMap<String, Attribute> attrByValues;
	TreeMap<String, Integer> valOrder;
	ArrayList<Attribute> allAttributes;
	private final ArrayList<Pos> posList;
	Dictionary tagDict;

	/**
	 * Creates a Tagset from a corpora config file.
	 */
	public Tagset(String filename) {
		tagDict = createDictionary(filename);
		posList = new ArrayList<Pos>();
		attrByName = new TreeMap<String, Attribute>();
		attrByValues = new TreeMap<String, Attribute>();
		valOrder = new TreeMap<String, Integer>();
		allAttributes = new ArrayList<Attribute>();
		addAttribute(orthAttr, noValues);
		addAttribute(baseAttr, noValues);
		addAttribute(posAttr, noValues);
		try {
			loadCfgFile(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			loadCfgFile(SimpleDict.class.getResourceAsStream("/ipipan/spejd/sample.cfg"));
		}
		System.err.println("Tagset(): " + getPosList().size() + " pos loaded.");
	}

	Dictionary createDictionary(String filename) {
		File f = new File(tagDictFile);

		/* no tag cache */
		if (!f.exists())
			return new Dictionary();

		/* tagset modified */
		if ((new File(filename)).lastModified() > f.lastModified())
			return new Dictionary();

		/* load tag cache */
		System.out.println("Using previously saved tag cache.");
		return new Dictionary(tagDictFile);
	}

	void addAttribute(String name, String[] values) {
		Attribute attr = attrByName.get(name);
		if (attr == null) {
			attr = new Attribute(allAttributes.size(), name, values);
			allAttributes.add(attr);
			attrByName.put(name, attr);
		} else {
			attr.values = values;
		}
		for (int i = 0; i < values.length; i++) {
			attrByValues.put(values[i], attr);
			valOrder.put(values[i], i);
		}
	}

	void loadCfgFile(String filename) throws FileNotFoundException {
		loadCfgFile(new FileInputStream(filename));
	}

	void loadCfgFile(InputStream stream) {
		BufferedReader in;
		String s;
		ArrayList<String> posNames = new ArrayList<String>();
		int mode = LOAD_IGNORE;

		try {
			in = new BufferedReader(new InputStreamReader(stream, "utf-8"));
			for (s = in.readLine(); s != null; s = in.readLine()) {
				s = s.trim();
				if (s.startsWith("#"))
					continue;
				if (s.length() == 0)
					continue;

				if (s.startsWith("[")) {
					if (s.equals("[POS]")) {
						mode = LOAD_POS;
						continue;
					}
					if (s.equals("[NAMED-ENTITY]")) {
						mode = LOAD_NAMED;
						continue;
					}
					if (s.equals("[ATTR]")) {
						mode = LOAD_ATTR;
						continue;
					}
					mode = LOAD_IGNORE;
					continue;
				}

				if (mode == LOAD_IGNORE)
					continue;

				int i = s.indexOf('=');
				if (i == -1) {
					throw new RuntimeException("Tagset: invalid line, assignment (=) expected.\n" + s);
				}

				String var = s.substring(0, i).trim();
				s = s.substring(i + 1).trim();
				String[] vals = s.length() > 0 ? s.split("\\s+") : noValues;

				switch (mode) {
				case LOAD_POS:
					getPosList().add(new Pos(var, vals));
					posNames.add(var);
					continue;
				case LOAD_ATTR:
					addAttribute(var, vals);
					continue;
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		addAttribute(posAttr, posNames.toArray(noValues)); // fix pos valuse
	}

	public char valueToChar(String value) {
		if (value.equals(undefinedValue))
			return '0';
		return Attribute.intToChar(valOrder.get(value));
	}

	public int attrToInt(String attr) {
		Attribute res = attrByName.get(attr);
		if (res == null) {
			return -1;
		}
		return res.id;
	}

	public String charToValue(int attrId, char valueId) {
		return allAttributes.get(attrId).getValue(valueId);
	}

	public String matchingValues(int attrId, String regexp) {
		return allAttributes.get(attrId).matchingValues(regexp);
	}

	public int nOfAttributes() {
		return allAttributes.size();
	}

	InterpretationArray interpretation(String ctag) {
		String[] tag = ctag.length() > 0 ? ctag.split(":") : new String[0];

		Attribute a;
		InterpretationArray res = new InterpretationArray(
				allAttributes.size() - 2);

		for (int i = 0; i < tag.length; i++) {

			// morfeusz _ tag - get a list of possible tags for a given
			// attribute, identified by pos+position combination
			if (tag[i].equals("_")) {

				// what pos is it? have to ugly iterate pos objects collection.
				Iterator<Pos> it = getPosList().listIterator();
				Pos pos = null;
				while (it.hasNext()) {
					pos = it.next();
					if (pos.getName().equals(tag[0])) {
						break;
					}
				}
				if (i > pos.getAttributes().length) {
					System.err.println("Tag / tagset mismatch: " + ctag);
				}

				a = attrByName.get(pos.getAttributes()[i - 1].name);

				// generate the whole list of possible values
				res.addValues(a, pos.getName() + "*");

				continue;
			}
			if (tag[i].endsWith("*")) {
				a = attrByName.get(tag[i].substring(0, tag[i].length() - 1));
				res.addValues(a, tag[i]);
				continue;
			}
			if (tag[i].indexOf('.') > -1) {
				a = attrByValues.get(tag[i].split("\\.")[0]);
				res.addValues(a, tag[i]);
				continue;
			}
			a = attrByValues.get(tag[i]);
			res.addValue(a, tag[i]);
		}
		return res;
	}

	public String cToFtag(String ctag) {
		String value = tagDict.get1(ctag);
		if (value != null)
			return value;

		value = interpretation(ctag).toInterpretation();
		tagDict.put(ctag, value);
		return value;
	}

	public String[] cToFtagArray(String ctag) {
		// String value = tagDict.get1(ctag);
		return interpretation(ctag).toArray();
	}

	public String fToCtag(String ftag) {
		String value = tagDict.get2(ftag);
		if (value != null)
			return value;

		StringBuilder res = new StringBuilder();

		for (int i = 0; i < ftag.length(); i++) {
			if (ftag.charAt(i) != '0') {
				res.append(':');
				res.append(charToValue(i + 2, ftag.charAt(i)));
			}
		}
		value = res.toString().substring(1);
		tagDict.put(value, ftag);
		return value;
	}

	public void saveTagDictionary(String logDir) {
		tagDict.save(logDir + tagDictFile);
	}

	public void printAttributes() {
		for (int i = 0; i < allAttributes.size(); i++)
			allAttributes.get(i).printValues();
	}

	public static void init() {
		if (tagset == null)
			tagset = new Tagset(defaultCfgFile);
	}

	public static void main(String[] args) {
		tagset = new Tagset(args[0]);
		tagset.printAttributes();
		// String[] ftags = tagset.cToFtagArray("adj:_:nom.gen:m1.m2.m3:pos");
		String[] ftags = tagset.cToFtagArray("*:_"); //"ppron3:sg:inst:n2:ter:_:_");
		for (int i = 0; i < ftags.length; i++)
			System.out.println(ftags[i] + " " + tagset.fToCtag(ftags[i]));
	}

	public ArrayList<Pos> getPosList() {
		return posList;
	}
}
