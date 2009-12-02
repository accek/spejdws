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

import ipipan.spejd.rules.AbstractRule;
import ipipan.spejd.rules.Match;
import ipipan.spejd.util.Config;

/**
 * An action to evaluate after finding a match.
 */
public abstract class Action {
	AbstractRule rule;
	
	protected Config config;

	public Action(Config config) {
		super();
		this.config = config;
	}

	static String intArgs(int[] args) {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			res.append(args[i]);
			res.append(", ");
		}
		return res.toString();
	}

	/**
	 * Entity specifications required by this action.
	 */
	abstract public int[] required();

	/**
	 * Apply the action to the match.
	 */
	abstract public boolean apply(Match match);

	/**
	 * Set a pointer to rule containing this action. For diagnostic purposes
	 * only.
	 */
	public void setRule(AbstractRule rule) {
		this.rule = rule;
	}
}
