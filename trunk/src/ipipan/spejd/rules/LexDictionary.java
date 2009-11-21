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

import ipipan.spejd.entities.Entity;
import ipipan.spejd.entities.Interpretation;
import ipipan.spejd.util.Config;
import ipipan.spejd.util.SimpleDict;

public class LexDictionary extends SimpleDict {
    int lex = 0;
    int tok = 0;
    
    protected Config config;

    public LexDictionary(Config config, String filename) {
        super(filename);
        this.config = config;
     }

  /**
    * Override put() from superclass to add conversion of the tag value.
    */
    @Override
	public void put(String var, String value) {
        dict.put(var, config.tagset.cToFtag(value));
    }

    public void apply(Entity e) {
	if(dict.size() == 0)
	    return;
	
	Interpretation[] interp = e.getInterpretationArray();
	if(interp == null) 
	    return;

    boolean modified = false;
    String tag;
    for(int i = 0; i < interp.length; i ++) {
        tag = dict.get(interp[i].getBase());
        if(tag == null) 
            continue; 
        interp[i].modify(tag);
        lex ++;
        modified = true;
        }
    if(modified) tok ++;
    }

    // FIXME - report to a log file?
    public void report() {
        System.out.println("LexDictionary matches: " + tok + " (" + lex + ")");
    }
}
