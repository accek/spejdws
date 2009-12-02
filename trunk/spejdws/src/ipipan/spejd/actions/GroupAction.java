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
import ipipan.spejd.entities.Group;
import ipipan.spejd.rules.Match;
import ipipan.spejd.util.Config;

public class GroupAction extends SyntAction {
	String groupType;
	int semh, synh, inhType;
	boolean oneHead;

	public GroupAction(Config config, String type, int synh, int semh) {
		super(config);
		if (Character.isDigit(type.charAt(0))) {
			inhType = Integer.parseInt(type) - 1;
			groupType = null;
		} else {
			inhType = -1;
			groupType = type;
		}
		this.synh = synh - 1;
		this.semh = semh - 1;
		oneHead = (this.synh == this.semh);
	}

	@Override
	public String toString() {
		return "group " + groupType + ", " + inhType + ", " + synh + ", "
				+ semh;
	}

	@Override
	public Entity result(Match matcher, Entity[] match) {
		if (inhType >= 0)
			groupType = matcher.getSingle(inhType).getGroupType();
		return new Group(groupType, matcher.getSingle(synh), matcher
				.getSingle(semh), match);
	}
}
