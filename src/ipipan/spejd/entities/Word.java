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

package ipipan.spejd.entities;

import ipipan.spejd.tagset.Attribute;
import ipipan.spejd.util.Config;

import java.io.PrintStream;

/**
 * A representation of syntactic group.
 */
public class Word extends Segment {
	Entity[] content;
	
	void init(Entity[] content) {
		type = WORD;
		this.content = content;
		id = getNewId();
		orth = concatAttr(Attribute.ORTH, content);
		modified = true;
	}

	public Word(Config config, Interpretation[] interp, Entity[] content) {
		super(config);
		this.interp = interp;
		init(content);
	}

	// // create an array of new interpretations
	// public Word(String[] data, Entity[] content) {
	// init(content);
	// interp = new Interpretation[data.length];
	// int taglen = Config.tagset.nOfAttributes() - 2;

	// for(int i = 0; i < data.length; i++)
	// interp[i] = new Interpretation(data[i].length() > taglen ?
	// data[i] : data[i] + orth);
	// }

	// // modify existing interpretations
	// public Word(Entity head, String data, Entity[] content) {
	// init(content);
	// interp = head.getSynHead().selectInterp(data);
	// }

	@Override
	public void printXML(PrintStream out) {
		out.print("<syntok id=\"");
		out.print(id);
		if (rule != null) {
			out.print("\" rule=\"");
			out.print(rule.getTitle());
		}
		out.print("\">\n");
		appendData(out);
		for (int i = 0; i < content.length; i++)
			content[i].printXML(out);
		out.print("</syntok>\n");
	}
}
