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

package ipipan.spejd.actions;

import ipipan.spejd.ruleparser.MatchParser;
import ipipan.spejd.util.Config;

import java.io.BufferedReader;
import java.util.ArrayList;

public class ActionParser extends MatchParser {
	static final int AGREE = 0;
	static final int UNIFY = 1;
	static final int DELETE = 2;
	static final int LEAVE = 3;
	static final int ADD = 4;
	static final int SET = 5;
	static final int WORD = 6;
	static final int GROUP = 7;
	static final int ORTHC = 8;

	static final String[] actions = { "agree", "unify", "delete", "leave",
			"add", "set", "word", "group", "orthnot" };

	public ActionParser(Config config, BufferedReader in) {
		super(config, in);
	}

	/**
	 * Read "1.", "2." etc. as in "0.orth" or "3.base"
	 */
	int readTokSpecDot() {
		int res = readNumber(false);
		forceEat(".", false);
		return res;
	}

	int[] intToInt(ArrayList<Integer> list) {
		int[] res = new int[list.size()];
		for (int i = 0; i < res.length; i++)
			res[i] = list.get(i);
		return res;
	}

	/**
	 * Read a sequence of numbers (token references), separated by commas.
	 */
	int[] readTokList() {
		ArrayList<Integer> res = new ArrayList<Integer>();

		res.add(readNumber(true) - 1); // FIXME - correct token references
		while (currentCharIs(',')) {
			forceEat(",", true);
			res.add(readNumber(true) - 1);// FIXME - correct token references
		}
		return intToInt(res);
	}

	int[] readAttrList() {
		ArrayList<Integer> res = new ArrayList<Integer>();

		while (currentCharNot(','))
			res.add(config.tagset.attrToInt(readId()));

		return intToInt(res);
	}

	AgreeAction readAgreeAction(boolean unify) {
		int[] attr = readAttrList();
		forceEat(",", true);
		int[] segs = readTokList();
		return new AgreeAction(config, unify, attr, segs);
	}

	DeleteAction readDeleteAction(boolean leave) {
		String regexp = readTokenRequirement().toLexRegexp();
		forceEat(",", true);
		int[] tok = readTokList();
		return new DeleteAction(config, leave, regexp, tok);
	}

	AddAction readAddAction(boolean deleteOld) {
		String tag, base;
		tag = readTag();
		forceEat(",", true);

		if (currentCharIs('"'))
			base = readRegexp();
		else {
			base = "";
			eat("base", true);
		}
		forceEat(",", true);

		int[] tok = readTokList();

		return new AddAction(config, deleteOld, tag, base, tok);
	}

	/**
	 * Read a tuple (pair or triple) of word arguments, separated by commas.
	 */
	WordActionArg readWordArg() {
		String tag;

		int tok = -1;

		// the type of tuple is determined
		// basing on whether the first argument is a number

		if (currentCharIsDigit()) {

			// 3 args, mword <tok>, <tag>, <base>

			tok = readNumber(true);
			forceEat(",", true);
		}

		// 2 args, nword, <tag>, <base>

		ArrayList<WordBasePart> base = new ArrayList<WordBasePart>();

		tag = readTag();
		eatWhite();
		forceEat(",", true);
		while (currentCharNotIn(";)")) {
			if (currentCharIsDigit())
				base.add(new WordBasePart(readTokSpecDot(), config.tagset
						.attrToInt(readId())));
			else if (tok > 0 && eat("base", true))
				base.add(new WordBasePart());
			else
				base.add(new WordBasePart(readRegexp()));
			eatWhite();
		}

		if (tok > 0 && base.size() == 0)
			base.add(new WordBasePart());

		return new WordActionArg(config, tok, tag, base.toArray(new WordBasePart[0]));
	}

	/**
	 * Read a sequence of argument tuples, separated by semicolon.
	 */
	WordAction readWordAction() {
		ArrayList<WordActionArg> res = new ArrayList<WordActionArg>();
		while (currentCharNot(')')) {
			res.add(readWordArg());
			if (currentCharNotIn(";)"))
				error("; or ) expected.");
			eat(";", true);
		}
		return new WordAction(config, res.toArray(new WordActionArg[0]));
	}

	GroupAction readGroupAction() {
		String type = readString();
		forceEat(",", true);
		int synh = readNumber(true);
		forceEat(",", true);
		int semh = readNumber(true);

		return new GroupAction(config, type, synh, semh);
	}

	OrthCond readOrthCond() {
		String regexp = readRegexp();
		forceEat(",", true);
		int tok = readNumber(true);

		return new OrthCond(config, tok, regexp);
	}

	/**
	 * Identify action.
	 */
	int readActionType() {
		String action = readId();

		for (int i = 0; i < actions.length; i++)
			if (action.equals(actions[i]))
				return i;

		error("Unknown action to evaluate.");
		return -1;
	}

	Action readActionContent(int type) {
		switch (type) {
		case AGREE:
			return readAgreeAction(false);
		case UNIFY:
			return readAgreeAction(true);
		case DELETE:
			return readDeleteAction(false);
		case LEAVE:
			return readDeleteAction(true);
		case ADD:
			return readAddAction(false);
		case SET:
			return readAddAction(true);
		case WORD:
			return readWordAction();
		case GROUP:
			return readGroupAction();
		case ORTHC:
			return readOrthCond();
		default:
			return null;
		}
	}

	Action readAction() {
		int type = readActionType();
		forceEat("(", true);
		Action res = readActionContent(type);
		forceEat(")", true);
		forceEat(";", true);
		if (res == null)
			error("Could not parse action.");
		return res;
	}

	public Action[] readActionList(String start) {
		forceEat(start, true);
		ArrayList<Action> res = new ArrayList<Action>();
		while (charsLeft() && !preview("Rule") && !preview("Entry")
				&& !preview("Gazetteer"))
			res.add(readAction());
		return res.toArray(new Action[0]);
	}
}
