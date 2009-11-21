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

import ipipan.spejd.actions.Action;

import java.io.PrintStream;

public class GazetteerEntry extends AbstractRule {
    static public final int MAX_TITLE_LEN = 40;

    boolean fromFile;
    String key;

    public GazetteerEntry(String key, Action[] actions, boolean fromFile) {
	super("_gaz " + (key.length() <= MAX_TITLE_LEN ? 
			 key : key.substring(0, MAX_TITLE_LEN - 3) + "..."), 
	      actions);
    
	this.key = key;
	this.fromFile = fromFile;
    }
   
    public void log(PrintStream log) {
	log.println("Entry: " + title);

	for(int i = 0; i < actions.length; i++)
	    log.println(actions[i].toString());
	log.println();
    }
}
