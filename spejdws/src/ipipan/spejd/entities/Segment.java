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
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * A representation of segment - the smallest interpreted unit, ie. a sequence
 * of characters together with their morphosyntactic interpretations.
 */
public class Segment extends Entity {
	String orth;
	Interpretation[] interp;
	
	protected Config config;

	public Segment(Config config) {
		this.config = config;
	}

	public Segment(Config config, String id, String orth, Interpretation[] interp) {
		this(config);
		this.id = (id == null) ? getNewId() : id;
		type = TOKEN;
		this.orth = orth;
		this.interp = interp;
		modified = true;
	}

	@Override
	Set<String> getAttrValues(int[] attrIds, boolean acceptNull) {
		int i, j, k;
		boolean hasNull;
		Set<String> res = new TreeSet<String>();
		char[] buf = new char[config.tagset.nOfAttributes() - 2];

		for (i = 0; i < buf.length; i++)
			buf[i] = '.';

		for (i = 0; i < interp.length; i++) {
			hasNull = false;
			if (!interp[i].disambSh)
				continue;
			for (j = 0; j < attrIds.length; j++) {
				k = attrIds[j] - 2;
				buf[k] = interp[i].data.charAt(k);
				if (buf[k] < 'A')
					hasNull = true;
			}
			if (!hasNull || acceptNull)
				res.add(new String(buf));
			// System.err.println(new String(buf));
		}
		return res;
	}

	@Override
	public void add(boolean delOld, String[] data) {
		int i;
		Interpretation[] newInterp = new Interpretation[interp.length
				+ data.length];

		for (i = 0; i < interp.length; i++) {
			newInterp[i] = interp[i];
			if (delOld)
				newInterp[i].delete();
		}

		int bs = config.tagset.nOfAttributes() - 2;
		String base = interp[0].data.substring(bs);
		for (i = 0; i < data.length; i++)
			newInterp[interp.length + i] = new Interpretation(config,
					data[i].length() > bs ? data[i] : data[i] + base, false);
		interp = newInterp;
		modified = true;
	}

	@Override
	public void delete(Pattern pattern, boolean neg) {
		boolean delete;

		for (int i = 0; i < interp.length; i++) {
			if (!interp[i].disambSh)
				continue;
			delete = interp[i].matches(pattern);
			if (neg)
				delete = !delete;
			if (delete) { // mark this interpretation as deleted
				modified = true;
				interp[i].delete();
				continue;
			}
		}
	}

	@Override
	void updateTxt() {
		StringBuilder res = new StringBuilder();
		res.append("<<t<");
		res.append(id);
		res.append('<');
		res.append(orth);
		for (int i = 0; i < interp.length; i++)
			interp[i].printTxt(res);
		res.append('>');
		text = res.toString();
		modified = false;
	}

	/**
	 * Returns orthographic form of the token or base form (lemma) of the first
	 * interpretation.
	 */
	@Override
	public String getAttr(int id) {
		switch (id) {
		case Attribute.ORTH:
			return orth;
		case Attribute.BASE:
			return interp[0].data.substring(config.tagset.nOfAttributes() - 2);
		default:
			return "";
		}
	}

	@Override
	public Segment getSemHead() {
		return this;
	}

	@Override
	public Segment getSynHead() {
		return this;
	}

	// public Interpretation[] selectInterp(String modifier) {
	// int i, c = 0;
	// for(i = 0; i < interp.length; i++) {
	// if(interp[i].disambSh)
	// c++;
	// }
	// Interpretation[] res = new Interpretation[c];
	// c = 0;
	// for(i = 0; i < interp.length; i++) {
	// if(interp[i].disambSh) {
	// res[c] = new Interpretation(interp[i], modifier);
	// c++;
	// }
	// }
	// return res;
	// }

	@Override
	public Set<Interpretation> getInterpretations() {
		TreeSet<Interpretation> res = new TreeSet<Interpretation>();
		for (int i = 0; i < interp.length; i++)
			if (interp[i].disambSh)
				res.add(interp[i]);
		return res;
	}

	@Override
	public Interpretation[] getInterpretationArray() {
		return interp;
	}

	void appendData(PrintStream out) {
		out.print("<orth>");
		out.print(orth);
		out.println("</orth>");
		for (int i = 0; i < interp.length; i++)
			interp[i].printXML(out);
	}

	@Override
	public void printXML(PrintStream out) {
		out.print("<tok id=\"");
		out.print(id);
		out.print("\">\n");
		appendData(out);
		out.println("</tok>");
	}
}
