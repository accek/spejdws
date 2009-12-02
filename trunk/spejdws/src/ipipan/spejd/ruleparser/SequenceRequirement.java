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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Representation of a sequence of entity specifications, like
 * <code>[case~"acc|gen"]+ [base~~woda/i && number~sg]</code>.
 */
public class SequenceRequirement {
    ArrayList<EntityRequirement> content;

    public SequenceRequirement() {
	content = new ArrayList<EntityRequirement>();
    }

    public void add(EntityRequirement req) {
	content.add(req);
    }

    public String toRegexp() {
	StringBuilder res = new StringBuilder();
	EntityRequirement e;
	
	for(Iterator i = content.iterator(); i.hasNext(); ) {
	    e = (EntityRequirement) i.next();
	    res.append(e.toQuantifiedRegexp());
	}
	
	return res.toString();
    }

    public String[] toRegexpArray() {
	String[] res = new String[content.size()];
	
	for(int i = 0; i < content.size(); i++) 
	    res[i] = content.get(i).toQuantifiedRegexp(); 
	
	return res;
	
    }
}
