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

import ipipan.spejd.tagset.Attribute;

/**
 * Representation of a single requirement, like <code>case~"acc|gen"</code>
 * or <code>base~~woda/i</code>.
 */
public class Requirement {
    int attrId = -1;
    int flags;
    String values = null;

    public static final int NEGATED  = 1;
    public static final int FORALL   = 2;
    public static final int CASE_INS = 4;
    public static final int PARTIAL  = 8;

    public static String safeRegexp(String regexp) {
	StringBuffer res = new StringBuffer();
	// res = regexp.replaceAll("\\.", "[^<>]");
	int i = 0, j = 0;
	while((j = regexp.indexOf('.', i)) >= 0) {
	    if(j > 0 && regexp.charAt(j-1) == '\\') {
                res.append(regexp.substring(i, j+1));
            } else {
	        res.append(regexp.substring(i, j));
                res.append("[^<>]");
            }
            i = j + 1;
        }
	res.append(regexp.substring(i));
	
	if(regexp.indexOf('|') < 0) 
	    return res.toString();
	else
	    return "(?:" + res.toString() + ")";
    }

    boolean isSet(int flag) {
	return (flags & flag) > 0;
    }

    public Requirement(int attrId, String val, int flags) 
	// throws RuleParserException 
    {
	this.attrId = attrId;
	this.flags  = flags;

	if(attrId > Attribute.BASE) {
	    if(isSet(NEGATED))
		values = "[^" + val + "]";
	    else 
		values = val.length() > 1 ? "[" + val + "]" : val;
      	} else {
	    values = safeRegexp(val);

	    // negated orth or base requires special care

	    if(isSet(NEGATED)) 
		// zero-width negative lookahead including a stop char,
		// then any id
		values = "(?!" + values + "[<>])" + EntityRequirement.anyId;
	}
    }

    public boolean forall() {
	return isSet(FORALL);
    }

    public String toRegexp() {
	return values;
    }
}
