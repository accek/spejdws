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

public abstract class SyntAction extends Action {
	
	public SyntAction(Config config) {
		super(config);
	}

	@Override
	public int[] required() {
		return null;
	}

	abstract Entity result(Match matcher, Entity[] match);

	@Override
	public boolean apply(Match matcher) {
		Entity[] match = matcher.getMatch();
		Entity res = result(matcher, match);
		res.setRule(rule);

		for (int i = 0; i < match.length; i++)
			match[i].setReplacement(i == 0 ? res : null);

		return true;
	}
}
