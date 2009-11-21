/*
 * Copyright (C) 2008 by Instytut Podstaw Informatyki Polskiej
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

package ipipan.spejd.readers;

import java.util.LinkedList;
import java.util.List;

/**
 * Class for adding diacrits to Strings.
 */
public class Ogonkifier {

    /**
     * Generate a list of ogonkified words - candidates to check.
     * @param orth form to generate candidates from
     * @return 
     */
    public static List<String> ogonkify(String orth) {
	int i;
        LinkedList<String> candidates = new LinkedList<String>();
	candidates.add("");
	i = 0;
	
        if ((orth.length()>=3) && (orth.length()<12)) {   
	    while(i < orth.length()) {
		char nowAdding = orth.charAt(i);
                
                LinkedList<String> newcandidates = new LinkedList<String>();
                
		switch(nowAdding) {
		case 'a':
                    for(String candidate : candidates) {
			newcandidates.add(candidate +"a");
			newcandidates.add(candidate +"ą");
		    }
		    break;
		case 'c':
                    for(String candidate : candidates) {
			newcandidates.add(candidate +"c");
			newcandidates.add(candidate +"ć");
		    }
		    break;
		case 'e':
                    for(String candidate : candidates) {
			newcandidates.add(candidate +"e");
			newcandidates.add(candidate +"ę");
		    }
		    break;
		case 'l':
                    for(String candidate : candidates) {
			newcandidates.add(candidate +"l");
			newcandidates.add(candidate +"ł");
		    }
		    break;
		case 'n':
                    for(String candidate : candidates) {
			newcandidates.add(candidate +"n"); 
			newcandidates.add(candidate +"ń");
		    }
		    break;
		case 'o':
                    for(String candidate : candidates) {
			newcandidates.add(candidate +"o");
			newcandidates.add(candidate +"ó");
		    }
		    break;
		case 's':
                    for(String candidate : candidates) {
			newcandidates.add(candidate +"s"); 
			newcandidates.add(candidate +"ś");
		    }
		    break;
		case 'z':
                    for(String candidate : candidates) {
			newcandidates.add(candidate +"z"); 
			newcandidates.add(candidate +"ż"); 
			newcandidates.add(candidate +"ź");
		    }
		    break;
		default:
                    for(String candidate : candidates) 
			newcandidates.add(candidate + nowAdding);
		}
                candidates = newcandidates;
                i ++;
            }
        }
	return candidates;
    }
}    
        
