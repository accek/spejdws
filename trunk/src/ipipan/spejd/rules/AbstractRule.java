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
import ipipan.spejd.util.TimeWatch;

import java.io.PrintStream;

abstract public class AbstractRule {
    String title;
    Action[] actions;
    int matched, completed;
    TimeWatch timer;
    long tmatch, taction;
    
    public AbstractRule(String title, Action[] actions) {
	this.title   = title;
	this.actions = actions;

	for(int i = 0; i < actions.length; i++)
	    actions[i].setRule(this);

        this.timer = TimeWatch.getDefaultTimer();
	matched = completed = 0;
        tmatch  = taction   = 0;
    }

    /**
     * Write parsed rule to the log file.
     */
    abstract public void log(PrintStream log);

    public boolean applyActions(Match match) {
	boolean modified = false;
	int i;	

	matched ++;

	timer.getInterval();
	for(i = 0; i < actions.length; i++) {
	    if(!actions[i].apply(match))
		break;
	    modified = true;
	}

	if(i == actions.length)
	    completed ++;

	taction += timer.getInterval();

	return modified;
    }

    public String getTitle() {
	return title;
    }

    public void report(PrintStream log) {
	log.println('"' + title + "\"," 
		    + completed + "," + matched + ","
		    + tmatch + "," + taction + "," + (tmatch+taction));
    }
}
