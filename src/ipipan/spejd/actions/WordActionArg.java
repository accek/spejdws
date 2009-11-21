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

package ipipan.spejd.actions;

import ipipan.spejd.entities.Interpretation;
import ipipan.spejd.rules.Match;
import ipipan.spejd.util.Config;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class WordActionArg {
	int token; // -1 if two arguments
	WordBasePart[] base;
	String[] interp;

	protected Config config;
	
	public WordActionArg(Config config, int token, String tag, WordBasePart[] base) {
		this.config = config;
		this.token = token - 1;
		this.base = base;
		interp = config.tagset.cToFtagArray(tag);
	}

	public Set<Interpretation> createInterpretations(Match matcher) {
		StringBuilder baseb = new StringBuilder();
		for (int i = 0; i < base.length; i++)
			baseb.append(base[i].eval(matcher));
		String bases = baseb.toString();

		Set<Interpretation> res = new TreeSet<Interpretation>();
		Interpretation i;
		int j;

		if (token < 0) {
			// 2 arguments
			for (j = 0; j < interp.length; j++)
				res.add(new Interpretation(config, interp[j] + bases));
		} else {
			// 3 arguments
			Iterator<Interpretation> ii = matcher.getSingle(token)
					.getInterpretations().iterator();
			while (ii.hasNext()) {
				i = ii.next();
				for (j = 0; j < interp.length; j++) {
					res.add(new Interpretation(config, i, interp[j], bases));
				}
			}
		}
		return res;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();

		if (token >= 0) {
			res.append(token);
			res.append(", ");
		}

		for (int i = 0; i < interp.length; i++) {
			if (i > 0)
				res.append(" | ");
			res.append(interp[i]);
		}
		res.append(',');

		for (int i = 0; i < base.length; i++) {
			res.append(' ');
			res.append(base[i].toString());
		}

		return res.toString();
	}
}
