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

import ipipan.spejd.util.Config;

import java.io.PrintStream;
import java.util.regex.Pattern;

/**
 * A representation of a single morphosyntactic interpretation: lemma,
 * grammatical class, grammatical categories. Additionally it contains data for
 * disambiguation annotation - previous disambiguation (for example read from
 * the input file, ignored during parsing, but preserved for the final output)
 * and current disambiguation (added and used by the parser itself).
 */
public class Interpretation implements Comparable<Interpretation> {
	String data;
	boolean disamb, disambSh;
	
	protected Config config;

	public static final String REPLACE_BASE = "%";

	public static final Interpretation[] array = new Interpretation[0];

	/**
	 * Create an interpretation with the specified data. Previous disambiguation
	 * is set to false, current to true.
	 */
	public Interpretation(Config config, String s) {
		this.config = config;
		data = s;
		disamb = false;
		disambSh = true;
	}

	/**
	 * Create an interpretation with the specified data and preserving previous
	 * disambiguation value. Current disambiguation is set to true.
	 */
	public Interpretation(Config config, String s, boolean disamb) {
		this(config, s);
		this.disamb = disamb;
	}

	private static String interpretationToString(Interpretation interp, String modifier) {
		char[] res = new char[interp.data.length()];
		for (int i = 0; i < interp.data.length(); i++) {
			if (i < modifier.length() && modifier.charAt(i) != '0')
				res[i] = modifier.charAt(i);
			else
				res[i] = interp.data.charAt(i);
		}
		return new String(res);
	}

	/**
	 * Create an interpretation by modyfying attributes of an existing one. Base
	 * form (lemma) is copied without modifications.
	 */
	public Interpretation(Config config, Interpretation interp, String modifier) {
		this(config, interpretationToString(interp, modifier), interp.disamb);
	}

	private static String interpretationToString(Config config, Interpretation interp, String modifier,
			String base) {
		int taglen = config.tagset.nOfAttributes() - 2;

		// replace attributes overriden by modifier
		// copy the rest
		char[] res = new char[taglen];
		for (int i = 0; i < taglen; i++) {
			if (i < modifier.length() && modifier.charAt(i) != '0')
				res[i] = modifier.charAt(i);
			else
				res[i] = interp.data.charAt(i);
		}

		return new String(res) +
		// base from action, replace % with base from interpretation
				// FIXME - sth quicker than replaceAll
				base.replaceAll(REPLACE_BASE, interp.data.substring(taglen));
	}

	/**
	 * Create an interpretation by modifying attributes of an existing one. Base
	 * form can be substituted or pre/suffixed.
	 */
	public Interpretation(Config config, Interpretation interp, String modifier, String base) {
		this(config, interpretationToString(config, interp, modifier, base), interp.disamb);
	}

	public int compareTo(Interpretation i) {
		return data.compareTo(i.data);
	}

	/**
	 * Check if the interpretation matches the specified pattern.
	 */
	public boolean matches(Pattern pattern) {
		return pattern.matcher(data).matches();
	}

	/**
	 * Mark the interpretations as deleted (set the disambiguation annotation).
	 */
	public void delete() {
		disambSh = false;
	}

	/**
	 * Modify attributes of an interpretations.
	 */
	public void modify(String modifier) {
		char[] res = new char[data.length()];
		for (int i = 0; i < data.length(); i++) {
			if (i < modifier.length() && modifier.charAt(i) != '0')
				res[i] = modifier.charAt(i);
			else
				res[i] = data.charAt(i);
		}
		data = new String(res);
	}

	public String getBase() {
		return data.substring(config.tagset.nOfAttributes() - 2);
	}

	public void printTxt(StringBuilder res) {
		if (disambSh) {
			res.append('<');
			res.append(data);
		}
	}

	public void printXML(PrintStream out) {
		int baseIndex = config.tagset.nOfAttributes() - 2;
		if (config.discardDeleted && !disambSh)
			return;
		out.print("<lex");
		if (disamb)
			out.print(" disamb=\"1\"");
		if (!disambSh)
			out.print(" disamb_sh=\"0\"");
		out.print("><base>");
		out.print(data.substring(baseIndex));
		out.print("</base><ctag>");
		out.print(config.tagset.fToCtag(data.substring(0, baseIndex)));
		out.println("</ctag></lex>");
	}
}
