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

package ipipan.spejd.rules;

import ipipan.spejd.actions.Action;

import java.io.PrintStream;

public class Rule extends AbstractRule {

	MatchPattern pattern;

	public Rule(String title, String[] left, String[] match, String[] right,
			Action[] actions) {
		super(title, actions);

		int i;

		boolean[] required = new boolean[left.length + match.length
				+ right.length];
		for (i = 0; i < required.length; i++)
			required[i] = false;
		boolean matchRequired = false;

		for (i = 0; i < actions.length; i++) {
			int[] req = actions[i].required();
			if (req == null)
				matchRequired = true;
			else
				for (int j = 0; j < req.length; j++) {
					if (req[j] < required.length)
						required[req[j]] = true;
					else {
						throw new RuntimeException("Token reference " + req[j]
								+ " in rule " + title + ", action " + i
								+ " out of range.");
					}

				}
		}
		if (matchRequired)
			for (i = 0; i < match.length; i++)
				required[left.length + i] = true;

		pattern = new MatchPattern(left, match, right, required);
	}

	@Override
	public void log(PrintStream log) {
		log.println("Rule: " + title);
		log.println(pattern.toString());

		for (int i = 0; i < actions.length; i++)
			log.println(actions[i].toString());
		log.println();
	}

	public boolean apply(Sentence sentence) {
		boolean modified = false;

		timer.reset();
		pattern.feed(sentence);

		while (pattern.nextMatch()) {
			tmatch += timer.getInterval();
			modified |= applyActions(pattern);
		}
		tmatch += timer.getInterval();

		return modified;
	}
}
