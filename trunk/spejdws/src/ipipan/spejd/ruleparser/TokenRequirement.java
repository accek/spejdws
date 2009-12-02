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

package ipipan.spejd.ruleparser;

import ipipan.spejd.util.Config;

/**
 * Representation of a token specification, like <code>[case~"acc|gen"]</code>
 * or <code>[base~~woda/i && number~sg]</code>.
 */
public class TokenRequirement extends EntityRequirement {
	Requirement[][] reqs;
	int parenth;
	boolean hasForall, hasExists;

	private static final int FORALL = 0;
	private static final int EXISTS = 1;

	public TokenRequirement(Config config) {
		super(config);
		
		hasForall = false;
		hasExists = false;

		reqs = new Requirement[2][config.tagset.nOfAttributes()];
		for (int i = 0; i < reqs.length; i++) {
			reqs[0][i] = null;
			reqs[1][i] = null;
		}
	}

	public void add(Requirement r) {
		if (r.forall()) {
			reqs[0][r.attrId] = r;
			reqs[1][r.attrId] = r;
			if (r.attrId > 0)
				hasForall = true;
		} else {
			reqs[1][r.attrId] = r;
			if (r.attrId > 0)
				hasExists = true;
		}
	}

	String wordToRegexp(int forall, int id) {
		if (reqs[forall][id] == null)
			return anyId;
		else
			return reqs[forall][id].toRegexp();
	}

	String lex(int forall) {
		StringBuilder res = new StringBuilder();
		res.append('<');
		for (int i = 2; i < reqs[forall].length; i++) {
			if (reqs[forall][i] == null)
				res.append('.');
			else {
				res.append(reqs[forall][i].toRegexp());
			}
		}
		res.append(wordToRegexp(forall, 1));
		return res.toString();
	}

	public String toHeadRegexp() {
		String forall;
		String regex;

		if (hasForall)
			forall = lex(FORALL);
		else
			forall = "<" + anyId;

		if (hasExists) {
			regex = "(?:" + forall + ")*(?:" + lex(EXISTS) + ")" + "(?:"
					+ forall + ")*";
		} else {
			regex = "(?:" + forall + ")+";
		}

		if (!hasForall && !hasExists)
			regex = "<[^>]+";

		return regex + ">";
	}

	@Override
	public String toRegexp() {
		return "(?><<t<" + anyId + "<" + wordToRegexp(1, 0) + toHeadRegexp()
				+ ")";
	}

	public String toLexRegexp() {
		return lex(EXISTS).substring(1);
	}
}
