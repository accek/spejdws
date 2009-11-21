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

import ipipan.spejd.entities.Entity;
import ipipan.spejd.rules.Match;
import ipipan.spejd.util.Config;

public class AddAction extends Action {
	int[] segs;
	String[] data;
	boolean delOld;

	AddAction(Config config, boolean delOld, String ctag, String base, int segs[]) {
		super(config);
		this.delOld = delOld;
		this.segs = segs;
		String[] ftag = config.tagset.cToFtagArray(ctag);
		data = new String[ftag.length];
		for (int i = 0; i < ftag.length; i++)
			data[i] = ftag[i] + base;
	}

	@Override
	public int[] required() {
		return segs;
	}

	@Override
	public boolean apply(Match matcher) {
		Entity[] match = matcher.get(segs);

		for (int i = 0; i < match.length; i++) {
			match[i].add(delOld, data);
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuffer res = new StringBuffer("add ");
		res.append(intArgs(segs));
		for (int i = 0; i < data.length; i++) {
			if (i > 0)
				res.append(" | ");
			res.append(data[i]);
		}
		return res.toString();
	}
}
