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

public class InterpretationFactor {
    Attribute attr;
    char[] val;

    public InterpretationFactor(Attribute attr, String values) {
	this.attr = attr;

	if(values.indexOf('.') > -1) {
	    String[] tmp = values.split("\\.");
	    val = new char[tmp.length];
	    for(int i = 0; i < val.length; i ++)
		val[i] = attr.matchingValue(tmp[i]);
	    
	}

	if(values.endsWith("*")) {
	    val = new char[attr.nOfValues()];
	    for(int i = 0; i < val.length; i ++)
		val[i] = Attribute.intToChar(i);
   	}
    }
}
