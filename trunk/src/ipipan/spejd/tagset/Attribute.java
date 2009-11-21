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

import java.util.regex.Pattern;

/**
 * Representation of attribute in tagset, like pos, case or gender.
 * Contains methods for conversion between internal and external
 * tagsets.
 */
public class Attribute {
    public static final int ORTH = 0;
    public static final int BASE = 1;
    public static final int POS  = 2;

    int id;
    String name;
    String[] values;

    public static char intToChar(int i) {
	i += 'A';
	if(i > 'Z') 
	    i += 'a' - 'Z';
	return (char) i;
    }

    public static int charToInt(char c) {
	if(c >= 'a')
	    c -= 'a' - 'Z';
	c -= 'A';
	return c;
    }

    public Attribute(int id, String name, String[] values) {
        this.id     = id;
        this.name   = name;
        this.values = values;
    }

    public int nOfValues() {
        return values.length;
    }

    public String getValue(char c) {
	int i = charToInt(c);
	if(i >= values.length) {
	    System.err.println("Value [" + c + "] out of range for " + name);
	    return "-";
	}
        return values[charToInt(c)];
    }

    /**
     * Find matching value.
     *
     * @return A character representing matching values.
     */
    public char matchingValue(String value) {
	for(int i = 0; i < values.length; i ++) 
	    if(values[i].equals(value))
		return intToChar(i);
	System.err.println("[" + value + "] is not a valid value of " + name);
	return '0';
    }

    /**
     * Find values matching a regular expression.
     *
     * @return A string of characters representing matching values.
     */
    public String matchingValues(String regexp) {
	Pattern p = Pattern.compile(regexp);
	StringBuilder res = new StringBuilder();
	
	for(int i = 0; i < values.length; i ++) {
	    if(p.matcher(values[i]).matches())
		res.append(intToChar(i));
	}
        return res.toString();
    }

    public void printValues() {
        System.out.println(id + ". " + name);
        for(int i = 0; i < values.length; i++)
	    System.out.println("    " + intToChar(i) + ". " + values[i]);
    }
}
