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

import java.util.ArrayList;

public class InterpretationArray {
	boolean single = true;
	char[] base;
	ArrayList<InterpretationFactor> factors;

	public InterpretationArray(int nAttr) {
		base = new char[nAttr];
		for (int i = 0; i < base.length; i++)
			base[i] = '0';
		factors = new ArrayList<InterpretationFactor>();
	}

	public void addValue(Attribute attr, String val) {
		base[attr.id - 2] = attr.matchingValue(val);
	}

	public void addValues(Attribute attr, String values) {
		base[attr.id - 2] = '*';
		factors.add(new InterpretationFactor(attr, values));
		single = false;
	}

	public String toInterpretation() {
		if (!single)
			System.err
					.println("Attempt to make an Array a single Interpretation");
		return new String(base);
	}

	char[] copyInterpretation(char[] ftag) {
		char[] res = new char[ftag.length];
		for (int i = 0; i < ftag.length; i++)
			res[i] = ftag[i];
		return res;
	}

	char[][] multiplyInterpretations(char[][] ftag, InterpretationFactor f) {
		char[] val = f.val;
		char[][] res = new char[ftag.length * val.length][];
		int i, j;
		for (i = 0; i < ftag.length; i++)
			for (j = 0; j < val.length; j++) {
				res[i * val.length + j] = copyInterpretation(ftag[i]);
				res[i * val.length + j][f.attr.id - 2] = val[j];
			}
		return res;
	}

	public String[] toArray() {
		int i;
		char[][] res = new char[1][];
		res[0] = copyInterpretation(base);
		for (i = 0; i < factors.size(); i++)
			res = multiplyInterpretations(res, factors.get(i));
		String[] sres = new String[res.length];
		for (i = 0; i < res.length; i++)
			sres[i] = new String(res[i]);
		return sres;
	}
}
