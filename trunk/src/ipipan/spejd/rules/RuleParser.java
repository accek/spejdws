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

import ipipan.spejd.actions.ActionParser;
import ipipan.spejd.util.Config;

import java.io.BufferedReader;

public class RuleParser extends ActionParser {

	public RuleParser(Config config, BufferedReader in) {
		super(config, in);
	}

	public Rule readRule() {
		if (!charsLeft())
			return null;

		forceEat("Rule", true);
		String title = readString();
		// forceEat(";", true);

		return new Rule(title, readMatchPattern("Left:", false),
				readMatchPattern("Match:", true), readMatchPattern("Right:",
						false), readActionList("Eval:"));
	}

	GazetteerEntry readGazetteerEntry() {
		if (!charsLeft() || preview("Rule") || preview("Gazetteer"))
			return null;

		if (eat("File", true))
			return new GazetteerEntry(readFilename(), readActionList("="), true);

		forceEat("Entry", true);
		return new GazetteerEntry(readRegexp(), readActionList("="), false);
	}

	public Gazetteer readGazetteer() {
		if (!eat("Gazetteer", true))
			return null;
		Gazetteer res = new Gazetteer(config, readNumber(true));
		forceEat(":", true);
		GazetteerEntry entry;
		while ((entry = readGazetteerEntry()) != null)
			res.addEntry(entry);
		return res;
	}
}
