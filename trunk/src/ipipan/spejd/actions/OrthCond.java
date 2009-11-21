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

import ipipan.spejd.rules.Match;
import ipipan.spejd.tagset.Attribute;
import ipipan.spejd.util.Config;

import java.util.regex.Pattern;

public class OrthCond extends Action {
	int segno;
	Pattern pattern;

	public OrthCond(Config config, int segno, String regex) {
		super(config);
		this.segno = segno - 1;
		pattern = Pattern.compile(regex);
	}

	@Override
	public int[] required() {
		int[] res = new int[1];
		res[0] = segno;
		return res;
	}

	@Override
	public boolean apply(Match matcher) {
		String orth = matcher.getSingle(segno).getAttr(Attribute.ORTH);
		return !pattern.matcher(orth).matches();
	}

	@Override
	public String toString() {
		return "orth " + segno + " not " + pattern.pattern();
	}
}
