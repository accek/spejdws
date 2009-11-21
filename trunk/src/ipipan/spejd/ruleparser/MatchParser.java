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
import ipipan.spejd.util.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

/**
 * Parser of match specifications.
 */
public class MatchParser {
	String line;
	int currentChar;
	int currentLine;
	BufferedReader in;
	Map<String, EntityRequirement> macros;
	
	protected Config config;

	static final String eqOperators = "!~";

	public MatchParser(Config config) {
		this.config = config;
		macros = new TreeMap<String, EntityRequirement>();
		line = "";
		currentLine = 0;
		currentChar = 0;
	}

	public MatchParser(Config config, BufferedReader in) {
		this(config);
		this.in = in;
		eatWhite();
	}

	protected boolean feed() {
		try {
			line = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		currentLine++;
		if (line == null)
			return false;
		line = " " + line + " ";
		currentChar = 0;
		return true;
	}

	// protected void feed(String line) {
	// this.line = line;
	// currentChar = 0;
	// }

	protected char currentChar() {
		return line.charAt(currentChar);
	}

	protected boolean charsLeft() {
		if (line == null)
			return false;
		if (currentChar < line.length())
			return true;
		return feed();
	}

	protected boolean currentCharIs(char c) {
		if (!charsLeft())
			return false;
		return currentChar() == c;
	}

	protected boolean currentCharNot(char c) {
		if (!charsLeft())
			return false;
		return currentChar() != c;
	}

	protected boolean currentCharIsDigit() {
		if (!charsLeft())
			return false;
		return Character.isDigit(currentChar());
	}

	protected boolean currentCharIsIn(String set) {
		if (!charsLeft())
			return false;
		return set.indexOf(currentChar()) >= 0;
	}

	protected boolean currentCharNotIn(String set) {
		if (!charsLeft())
			return false;
		return set.indexOf(currentChar()) < 0;
	}

	protected void error(String desc) {
		String message = "    Rules parse error at line " + currentLine + ", char "
				+ currentChar + "\n" + line + "\n";
		for (int i = 0; i < currentChar; i++)
			message = message + ' ';
		message = message + "^\n     " + desc + ".";
		throw new RuntimeException(message);
	}

	protected void eatWhite() {
		while (charsLeft()) {
			if (Character.isWhitespace(currentChar())) {
				currentChar++;
				continue;
			}
			if (currentChar() == '#') {
				feed();
				continue;
			}
			break;
		}
	}

	protected boolean preview(String expected) {
		if (!charsLeft())
			return false;
		return line.substring(currentChar).startsWith(expected);
	}

	protected boolean eat(String expected, boolean eatWhite) {
		if (preview(expected)) {
			currentChar += expected.length();
			if (eatWhite)
				eatWhite();
			return true;
		}
		return false;
	}

	protected void forceEat(String expected, boolean eatWhite) {
		if (!eat(expected, eatWhite))
			error("'" + expected + "' expected");
	}

	protected String readWord(String nonLetterChars, boolean allowEmpty) {
		eatWhite();
		if (nonLetterChars == null)
			nonLetterChars = "";
		int start = currentChar;
		String res = line.substring(start);
		while (charsLeft()
				&& (Character.isLetterOrDigit(currentChar()) || currentCharIsIn(nonLetterChars)))
			currentChar++;

		if (!allowEmpty && currentChar == start)
			error("Alphanumeric characters expected");

		if (currentChar >= start)
			res = line.substring(start, currentChar);

		eatWhite();
		return res;
	}

	protected String readId() {
		return readWord("_-", false);
	}

	protected String readTag() {
		return readWord("_-:.*", true);
	}

	protected String readFilename() {
		String res = readWord("_-.", false);
		if (!(new File(res)).exists())
			error("File not found: " + res);
		eatWhite();
		return res;
	}

	protected String readRegexp() {
		forceEat("\"", false);
		int start = currentChar;
		while (currentCharNot('"')) {
			if (currentChar() == '\\')
				currentChar++;
			currentChar++;
			if (currentChar == 1)
				error("Unmatched \" or illegal line break inside an expresion.");
		}
		String res = line.substring(start, currentChar);
		forceEat("\"", true);
		return res;
	}

	protected String readString() {
		if (currentCharIs('"'))
			return readRegexp();
		else
			return readId();
	}

	protected int readFlags() {
		if (!eat("/", false))
			return 0;

		int res = 0;
		while (charsLeft() && Character.isLetter(currentChar())) {
			switch (currentChar()) {
			case 'i':
				res |= Requirement.CASE_INS;
				break;
			case 'x':
				res |= Requirement.PARTIAL;
				break;
			default:
				error("Unknown flag: " + currentChar());
			}
			currentChar++;
		}
		eatWhite();
		return res;
	}

	protected int readNumber(boolean eatWhite) {
		int start = currentChar;
		while (currentCharIsDigit())
			currentChar++;
		if (currentChar == start)
			error("Number expected");
		int res = Integer.parseInt(line.substring(start, currentChar));
		if (eatWhite)
			eatWhite();
		return res;
	}

	protected int readEqOperator() {
		boolean negated, forall;

		negated = eat("!", false);
		forceEat("~", false);
		forall = eat("~", false);
		if (negated)
			forall = !forall;

		return (forall ? Requirement.FORALL : 0)
				| (negated ? Requirement.NEGATED : 0);
	}

	protected Requirement readRequirement() {
		String attr = readId();
		int attrId = config.tagset.attrToInt(attr);
		if (attrId < 0)
			error("Unknown attribute: " + attr);

		int flags = readEqOperator();
		String exp = readString();
		flags |= readFlags();

		String val = exp;
		if ((flags & Requirement.CASE_INS) > 0)
			val = "(?iu:" + val + ")";

		if ((flags & Requirement.PARTIAL) > 0)
			val = val.length() > 0 ? ".*" : ".*" + val + ".*";

		if (attrId > Attribute.BASE) {
			val = config.tagset.matchingValues(attrId, val);
			if (val.length() == 0)
				error("Expression \"" + exp
						+ "\" does not match any values of attribute " + attr);
		}

		return new Requirement(attrId, val, flags);
	}

	protected TokenRequirement readTokenRequirement() {
		TokenRequirement res = new TokenRequirement(config);
		boolean cont = false;
		while (currentCharNotIn(",]")) {
			if (cont)
				forceEat("&&", true);
			res.add(readRequirement());
			cont = true;
		}
		return res;
	}

	protected GroupRequirement readGroupRequirement() {
		GroupRequirement res = new GroupRequirement(config);
		boolean cont = false;
		while (currentCharNot(']')) {
			if (cont)
				forceEat("&&", true);
			cont = true;

			int lhs = GroupRequirement.attribute(readWord("", false));
			if (lhs < 0)
				error("Unknown group attribute");

			forceEat("=", false);

			if (lhs == GroupRequirement.TYPE) {
				res.setType(readString());
			} else {
				forceEat("[", true);
				TokenRequirement head = readTokenRequirement();
				forceEat("]", true);
				res.setHead(lhs, head);
			}
		}
		return res;
	}

	protected EntityRequirement readEntityRequirement() {
		eatWhite();
		if (eat("ns", true))
			return new SpecialRequirement(config, 'n');
		if (eat("sb", true))
			return new SpecialRequirement(config, 'b');
		if (eat("se", true))
			return new SpecialRequirement(config, 'e');

		EntityRequirement res;

		// macro reference
		if (eat("$", false)) {
			String key = readId();
			res = macros.get(key);
			if (res == null)
				error("Undefined macro $" + key);
			return res;
		}

		// parenthises alternative
		if (eat("(", true)) {
			res = readAltRequirement();
			forceEat(")", true);
			return res;
		}

		// token or group specification
		forceEat("[", true);
		if (preview("head=") || preview("semh=") || preview("synh=")
				|| preview("type=")) {
			res = readGroupRequirement();
		} else {
			res = readTokenRequirement();
		}
		forceEat("]", true);

		return res;
	}

	protected char readQuantifier() {
		if (eat("?", true))
			return ('?');
		if (eat("*", true))
			return ('*');
		if (eat("+", true))
			return ('+');
		return EntityRequirement.NQ;
	}

	protected SequenceRequirement readSequenceRequirement() {
		SequenceRequirement res = new SequenceRequirement();
		EntityRequirement e;
		while (currentCharNotIn("|);")) {
			e = readEntityRequirement();
			e.setQuantifier(readQuantifier());
			res.add(e);
		}
		return res;
	}

	protected AltRequirement readAltRequirement() {
		AltRequirement res = new AltRequirement(config);
		res.add(readSequenceRequirement());
		while (currentCharNotIn(");")) {
			forceEat("|", true);
			res.add(readSequenceRequirement());
		}
		return res;
	}

	protected String[] readMatchPattern(String part, boolean required) {
		if (eat(part, true)) {
			String[] res = readSequenceRequirement().toRegexpArray();
			forceEat(";", true);
			return res;
		} else {
			if (!required)
				return new String[0];
			else {
				error(part + " expected");
				return null;
			}
		}
	}

	public boolean readMacroDef() {
		if (!eat("Define", true))
			return false;

		String key = readId();
		forceEat("=", true);

		macros.put(key, readAltRequirement());
		forceEat(";", true);
		return true;
	}

	public static void main(String[] args) throws IOException {
		// Config.tagset.init(); [FIXME]
		String[] res;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		MatchParser parser = new MatchParser(Config.getInstance(), in);

		res = parser.readMatchPattern("", true);
		for (int i = 0; i < res.length; i++)
			System.err.println(res[i]);
	}
}
