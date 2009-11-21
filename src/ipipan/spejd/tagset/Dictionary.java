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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * Bi-directional dictionary of tags for conversion between external and
 * internal format.
 */
class Dictionary {
	TreeMap<String, String> dict1, dict2;

	public Dictionary() {
		dict1 = new TreeMap<String, String>();
		dict2 = new TreeMap<String, String>();
	}

	public Dictionary(String filename) {
		this();
		load(filename);
	}

	/**
	 * Loads dictionary from a specified file.
	 * 
	 * @param filename
	 *            name of the file
	 */
	void load(String filename) {
		int i, line = 1;

		try {
			BufferedReader d;
			d = new BufferedReader(new InputStreamReader(new FileInputStream(
					filename), "utf-8"));
			String inputLine;

			while ((inputLine = d.readLine()) != null) {
				line++;
				// empty line
				if (inputLine.length() == 0) {
					continue;
				}
				// comment
				if (inputLine.charAt(0) == '#' || inputLine.charAt(0) == ';') {
					continue;
				}
				// find =
				i = inputLine.indexOf('=');

				if (i < 0) {
					System.err.println("No assignment in " + inputLine);
					continue;
				}
				put(inputLine.substring(0, i), inputLine.substring(i + 1));
			}
			d.close();
		} catch (IOException e) {
			System.err.println("Error loading " + filename);
		}
	}

	public void put(String key, String val) {
		dict1.put(key, val);
		dict2.put(val, key);
	}

	/**
	 * Gets the value of a String variable.
	 * 
	 * @param var
	 *            name of the variable
	 * @return value of the variable
	 */
	public String get1(String var) {
		return dict1.get(var);
	}

	/**
	 * Gets the key for a value.
	 * 
	 * @param var
	 *            value of the variable
	 * @return name of the variable
	 */
	public String get2(String var) {
		return dict2.get(var);
	}

	public void save(String filename) {
		Map.Entry<String, String>[] entries = dict1.entrySet().toArray(
				new Map.Entry[0]);

		try {
			PrintStream out;
			out = new PrintStream(new FileOutputStream(filename), true, "utf-8");
			for (int i = 0; i < entries.length; i++) {
				out.print(entries[i].getKey());
				out.print('=');
				out.println(entries[i].getValue());
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
