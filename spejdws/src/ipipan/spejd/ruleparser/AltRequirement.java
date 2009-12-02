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

import java.util.ArrayList;
import java.util.Iterator;

public class AltRequirement extends EntityRequirement {
	ArrayList<SequenceRequirement> content;

	public AltRequirement(Config config) {
		super(config);
		content = new ArrayList<SequenceRequirement>();
	}

	public void add(SequenceRequirement req) {
		content.add(req);
	}

	@Override
	public String toRegexp() {
		StringBuilder res = new StringBuilder();
		boolean notFirst = false;

		if (content.size() > 1)
			res.append('(');
		for (Iterator<SequenceRequirement> i = content.iterator(); i.hasNext();) {
			if (notFirst)
				res.append('|');
			else
				notFirst = true;
			res.append(i.next().toRegexp());
		}
		if (content.size() > 1)
			res.append(')');

		return res.toString();
	}
}
