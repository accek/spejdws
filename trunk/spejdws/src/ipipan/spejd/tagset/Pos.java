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

package ipipan.spejd.tagset;

/**
 * Representation of part of speech in tagset, like noun or adjective. Contains
 * methods for validating tags (checking if the given tag has the required
 * attributes for this part of speech).
 */
public class Pos {
	private String name;
	private PosAttribute[] attributes;

	public Pos(String name, String[] l) {
		this.setName(name);
		setAttributes(new PosAttribute[l.length]);
		for (int i = 0; i < l.length; i++)
			getAttributes()[i] = new PosAttribute(l[i]);
	}

	/**
	 * Trims the tag to allowed attributes.
	 */
	public String trim(String tag) {
		return tag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAttributes(PosAttribute[] attributes) {
		this.attributes = attributes;
	}

	public PosAttribute[] getAttributes() {
		return attributes;
	}
}
