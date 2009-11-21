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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchPattern implements Match {

	Pattern pattern;
	int[] group;
	int matchStart, matchEnd;

	Sentence map;
	Matcher matcher;

	ArrayList<Entity>[] match;

	/**
	 * Count number of parenthesis in the regexp ignoring escaped \( and
	 * non-capturing (?
	 */
	int countParenth(StringBuilder regexp) {
		int res = 0, i = 0;
		while ((i = regexp.indexOf("(", i)) >= 0) {
			if ((i == 0 || regexp.charAt(i - 1) != '\\')
					&& (i == regexp.length() - 1 || regexp.charAt(i + 1) != '?'))
				res++;
			i++;
		}
		return res;
	}

	void addPart(StringBuilder re, String[] part, int pos, boolean[] required) {
		for (int i = 0; i < part.length; i++) {
			if (required[pos + i]) {
				group[pos + i] = countParenth(re) + 1;
				re.append('(');
			} else {
				group[pos + i] = -1;
			}
			re.append(part[i]);
			if (required[pos + i]) {
				re.append(')');
			}
		}
	}

	public MatchPattern(String[] left, String[] match, String[] right,
			boolean[] required) {
		group = new int[required.length];

		matchStart = left.length;
		matchEnd = matchStart + match.length;

		StringBuilder re = new StringBuilder();
		addPart(re, left, 0, required);
		addPart(re, match, matchStart, required);
		addPart(re, right, matchEnd, required);

		pattern = Pattern.compile(re.toString());
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(pattern.pattern());
		res.append("\nMatch: ");
		res.append(String.valueOf(matchStart));
		res.append(" - ");
		res.append(String.valueOf(matchEnd));
		res.append(" of ");
		res.append(String.valueOf(group.length));
		res.append("\nGroups: ");
		for (int i = 0; i < group.length; i++) {
			if (i != 0)
				res.append(',');
			res.append(String.valueOf(group[i]));
		}
		return res.toString();
	}

	public void feed(Sentence sentence) {
		matcher = pattern.matcher(sentence.text);
		match = new ArrayList[group.length];
		this.map = sentence;
	}

	ArrayList<Entity> findIds(String matchPart) {
		ArrayList<Entity> res = new ArrayList<Entity>();
		int i = 0, j;
		while ((i = matchPart.indexOf("<<", i)) >= 0) {
			i += 4;
			j = matchPart.indexOf('<', i);
			res.add(map.get(matchPart.substring(i, j)));
			i = j;
		}
		return res;
	}

	public boolean nextMatch() {
		if (!matcher.find())
			return false;

		String tmp;

		for (int i = 0; i < group.length; i++) {
			if (group[i] < 0)
				continue;
			tmp = matcher.group(group[i]);
			if (tmp == null) {
				System.err.println("Entity " + i + " (pattern group "
						+ group[i] + ") in " + pattern.pattern()
						+ " not found.");
				System.err.println("Whole match: " + matcher.group(0));
			}
			match[i] = findIds(tmp);
		}
		return true;
	}

	public void printMatch() {
		// System.err.println(matcher.group(0));
		for (int i = 0; i < group.length; i++) {
			System.err.print(' ');
			if (group[i] < 0)
				System.err.print("<_>");
			else {
				System.err.print('<');
				for (int j = 0; j < match[i].size(); j++) {
					if (j > 0)
						System.err.print(' ');
					System.err.print(match[i].get(j).getAttr(0));
				}
				System.err.print('>');
			}
		}
		System.err.println();
	}

	public Entity getSingle(int s) {
		if (match[s].size() != 1) {
			System.err.println("Entity " + s + " not single in: ");
			printMatch();
		}
		return match[s].get(0);
	}

	public Entity[] getSpec(int s) {
		return match[s].toArray(Entity.ARRAY);
	}

	public Entity[] get(int[] s) {
		if (s == null)
			return getMatch();

		ArrayList<Entity> res = new ArrayList<Entity>();

		for (int i = 0; i < s.length; i++)
			res.addAll(match[s[i]]);

		return res.toArray(Entity.ARRAY);
	}

	public Entity[] getMatch() {
		ArrayList<Entity> res = new ArrayList<Entity>();

		for (int i = matchStart; i < matchEnd; i++)
			res.addAll(match[i]);

		return res.toArray(Entity.ARRAY);
	}
}
