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
import ipipan.spejd.rules.Match;
import ipipan.spejd.tagset.Attribute;

public class WordBasePart {
    static final int CONC = -1; // concat all values
    static final int TEXT = -2; // plain text
    static final int THIS = -3; // take base from the interpretation
    
    int segno;
    int attrid;
    String text;

    public WordBasePart() {
	segno  = THIS;
	this.attrid = Attribute.BASE;
    }
    
    public WordBasePart(String text) {
	segno = TEXT;
	this.text = text;
    }

    public WordBasePart(int segno, int attrid) {
	this.segno  = segno - 1;
	this.attrid = attrid;
    }


//     public void renumber(int left, int match) {
// 	if(segno < 0)	    
// 	    return;
// 	if(segno == 0) {
// 	    segno --;
// 	    return;
// 	}
// 	if(segno <= left || segno > left + match)
// 	    System.err.println("Token reference "+segno+" out of match range <"
// 			       + (left+1) + "," + (left+match) + ">");
// 	segno -= left;
// 	segno --;
//     }   
    

    public String eval(Match matcher) {
	if(segno == TEXT)
	    return text;
	if(segno == THIS)
	    return Interpretation.REPLACE_BASE;

	Entity[] e = (segno == CONC) ? 
	    matcher.getMatch() : 
	    matcher.getSpec(segno);
	
	return Entity.concatAttr(attrid, e);
    }

    public String toString() {
	if(segno == TEXT)
	    return text;
	
	if(segno == CONC) 
	    return "all." + attrid;

	if(segno == THIS) 
	    return "this." + attrid;

	return segno + "." + attrid;
    }
}
