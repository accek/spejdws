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
import ipipan.spejd.entities.Interpretation;
import ipipan.spejd.entities.Word;
import ipipan.spejd.rules.Match;
import ipipan.spejd.util.Config;

import java.util.Set;
import java.util.TreeSet;

/**
 * Representation of a word() action, creating a new syntactic word.
 */
public class WordAction extends SyntAction {
	WordActionArg[] args;

	public WordAction(Config config, WordActionArg[] args) {
		super(config);
		this.args = args;
	}

	@Override
	Entity result(Match matcher, Entity[] match) {
		Set<Interpretation> interp = new TreeSet<Interpretation>();
		for (int i = 0; i < args.length; i++)
			interp.addAll(args[i].createInterpretations(matcher));
		return new Word(config, interp.toArray(Interpretation.array), match);
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("word ");
		for (int i = 0; i < args.length; i++) {
			if (i > 0)
				res.append("\n     ");
			res.append(args[i].toString());
		}
		return res.toString();
	}
}
