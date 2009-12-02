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

/**
 * Representation of a groups specification, like <code>[type=NG]</code> or
 * <code>[synh=[number~sg]]</code>.
 */
public class GroupRequirement extends EntityRequirement {
	public final static int SYNH = 1;
	public final static int SEMH = 2;
	public final static int BOTH = SYNH | SEMH;
	public final static int TYPE = 4;

	static final String anyHead = "<[^>]+>";

	TokenRequirement synh, semh;
	String type;
	boolean singleHead;

	public GroupRequirement(Config config) {
		super(config);
		synh = semh = null;
		singleHead = false;
		type = anyId;
	}

	public static int attribute(String s) {
		if (s.equals("type"))
			return TYPE;
		if (s.equals("head"))
			return BOTH;
		if (s.equals("semh"))
			return SEMH;
		if (s.equals("synh"))
			return SYNH;
		return -1;
	}

	public void setHead(int flags, TokenRequirement head) {
		if ((flags & SYNH) > 0)
			synh = head;
		if ((flags & SEMH) > 0)
			semh = head;
		if (flags == BOTH)
			singleHead = true;
	}

	public void setType(String type) {
		this.type = Requirement.safeRegexp(type);
	}

	@Override
	public String toRegexp() {
		StringBuilder res = new StringBuilder("(?><<g<");
		res.append(anyId);
		res.append('<');
		res.append(singleHead ? '1' : '.');
		res.append('<');
		res.append(Requirement.safeRegexp(type));
		res.append('>');
		res.append(synh != null ? synh.toHeadRegexp() : anyHead);
		res.append(semh != null ? semh.toHeadRegexp() : anyHead);
		res.append(")");

		return res.toString();
	}
}
