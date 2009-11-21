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

public abstract class EntityRequirement {
	public final static char NQ = ' ';

	static final String anyId = "[^<>]+";

	char q = NQ;
	protected Config config;
	
	public EntityRequirement(Config config) {
		this.config = config;
	}

	public abstract String toRegexp();

	public void setQuantifier(char q) {
		this.q = q;
	}

	String toQuantifiedRegexp() {
		if (q == NQ)
			return toRegexp();

		String qs = "" + q;

		if (config.matchStrategy != '*')
			qs += config.matchStrategy;

		return "(?:" + toRegexp() + ")" + qs;
	}
}
