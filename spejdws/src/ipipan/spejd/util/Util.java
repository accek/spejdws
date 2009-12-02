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

package ipipan.spejd.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Utility functions that do not fit in other classes.
 */
public class Util {
	static public int safeParseInt(String c) {
		try {
			return Integer.parseInt(c);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * Loads a list of Strings, every line treated as a separate String. Empty
	 * strings are ignored. Similar to file() in php.
	 * 
	 * @param filename
	 *            name of the file to load
	 * @return a list of Strings representing the given file's content
	 */
	public static ArrayList<String> fileAsList(Config config, String filename) {
		ArrayList<String> rv = new ArrayList<String>();

		try {
			BufferedReader d;
			d = new BufferedReader(new InputStreamReader(new FileInputStream(
					filename), config.inputEncoding));
			String inputLine;

			while ((inputLine = d.readLine()) != null) {
				if (inputLine.length() > 0 && inputLine.charAt(0) != '#') {
					rv.add(inputLine);
				}
			}
			d.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return rv;
	}

	/**
	 * Loads an array of Strings, every line treated as a separate String. Empty
	 * strings are ignored. Similar to file() in php.
	 * 
	 * @param filename
	 *            name of the file to load
	 * @return an arrays of Strings representing the given file's content
	 */
	public static String[] file(Config config, String filename) {
		return fileAsList(config, filename).toArray(new String[0]);
	}

	/**
	 * Similar to join / implode in php.
	 */
	public static String join(String c, Collection d) {
		StringBuilder res = new StringBuilder();
		boolean notFirst = false;
		for (Iterator i = d.iterator(); i.hasNext();) {
			if (notFirst)
				res.append(c);
			else
				notFirst = true;
			res.append(i.next());
		}
		return res.toString();
	}
}
