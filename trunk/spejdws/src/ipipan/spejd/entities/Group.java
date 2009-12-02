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
 
 
package ipipan.spejd.entities;

import java.io.PrintStream;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * A representation of syntactic group.
 */
public class Group extends Entity {
    String groupType;
    
    Entity[] content;
    Segment synh, semh;
    boolean oneh;

    public Group(String type, Entity synh, Entity semh, Entity[] match) {
	id   = getNewId();
	content = match;
	groupType = type;
	this.synh = synh.getSynHead();
	this.semh = semh.getSemHead();
	oneh = (synh == semh);
	modified = true;
    }

    public String getAttr(int id) {
	return concatAttr(id, content);
    }

    public Segment getSynHead() {
	return synh;
    }

    public Segment getSemHead() {
	return semh;
    }

    public String getGroupType() {
	return groupType;
    }

    Set<String> getAttrValues(int[] attrIds, boolean acceptNull) {
	return synh.getAttrValues(attrIds, acceptNull);
    }

    public void delete(Pattern pattern, boolean neg) {
	synh.delete(pattern, neg);
    }

    void updateTxt() {
	int i;
	StringBuilder res = new StringBuilder();
	res.append("<<g<");
	res.append(id);
	res.append('<');
	res.append(oneh ? 1 : 2);
	res.append('<');
	res.append(groupType);
	res.append('>');
	for(i = 0; i < synh.interp.length; i++) 
	    synh.interp[i].printTxt(res);
	res.append('>');
	for(i = 0; i < semh.interp.length; i++) 
	    semh.interp[i].printTxt(res);
	res.append('>');
	text = res.toString();
	modified = false;
    }

    public void printXML(PrintStream out) {
	out.print("<group id=\"");
	out.print(id);
	if(rule != null) {
	    out.print("\" rule=\"");
	    out.print(rule.getTitle());
	}
	out.print("\" synh=\"");
	out.print(synh.id); 
	out.print("\" semh=\"");
	out.print(semh.id); 
	out.print("\" type=\"");
	out.print(groupType); 
	out.print("\">\n");
	for(int i = 0; i < content.length; i ++) 
	    content[i].printXML(out);
	out.println("</group>");
    }
}
