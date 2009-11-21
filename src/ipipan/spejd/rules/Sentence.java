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

package ipipan.spejd.rules;

import ipipan.spejd.entities.Entity;
import ipipan.spejd.entities.SentenceBorder;
import ipipan.spejd.readers.Reader;
import ipipan.spejd.util.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Sentence {
	Map<String, Entity> map;
	List<Entity> list;

	Reader in;
	PrintStream out;
	String text;
	public LexDictionary dict; // FIXME - czy to na pewno powinno byc tutaj?
	
	protected Config config;

	static int lastId = 0;

	public Sentence(Config config) {
		this.config = config;
		map = new TreeMap<String, Entity>();
		list = new ArrayList<Entity>();
		dict = config.lexDictionaries.length() > 0 ?
				new LexDictionary(config, config.lexDictionaries) : null;
	}

	/**
	 * Gets the core of filename (strips extension etc.)
	 */
	String getCoreName(File f) {
		String name = f.getName();
		if (name.endsWith(".gz"))
			name = name.substring(0, name.lastIndexOf('.'));
		if (name.indexOf('.') > 0)
			name = name.substring(0, name.lastIndexOf('.'));
		return name;
	}

	public void openFile(File path) {
		in = Reader.getReader(config, path);
		try {
			File res = new File(path.getParent(), getCoreName(path)
					+ config.outputSuffix);
			out = new PrintStream(new FileOutputStream(res), true, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error opening file: " + path.getPath(), e);
		}
	}

	public Reader getIn() {
		return in;
	}

	public void setIn(Reader in) {
		this.in = in;
	}

	public PrintStream getOut() {
		return out;
	}

	public void setOut(PrintStream out) {
		this.out = out;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Entity get(String segId) {
		return map.get(segId);
	}

	void addEntity(Entity e) {
		map.put(e.getId(), e);
		list.add(e);
	}

	public boolean loadSentence() {
		Entity e;
		map.clear();
		list.clear();
		if (!in.nextSentence(out)) {
			out.close();
			return false;
		}

		addEntity(new SentenceBorder(SentenceBorder.BEGIN));
		while ((e = in.loadToken()) != null) {
			if (dict != null)
				dict.apply(e);
			addEntity(e);
		}
		addEntity(new SentenceBorder(SentenceBorder.END));

		updateTxt();
		return true;
	}

	public Entity[] toArray() {
		return list.toArray(Entity.ARRAY);
	}

	void updateTxt() {
		StringBuilder res = new StringBuilder();
		for (Iterator<Entity> i = list.iterator(); i.hasNext(); res.append(i
				.next().toTxt()))
			;
		text = res.toString();
		// System.out.println(text);
	}

	public void update() {
		List<Entity> oldList = list;
		list = new ArrayList<Entity>();
		Entity e;
		for (Iterator<Entity> i = oldList.iterator(); i.hasNext();) {
			e = i.next().getReplacement();
			if (e != null)
				addEntity(e);
		}
		updateTxt();
	}

	public void printXML() {
		out.println("<chunk type=\"s\">");
		for (Iterator<Entity> i = list.iterator(); i.hasNext();)
			i.next().printXML(out);
		out.println("</chunk>");
	}
}
