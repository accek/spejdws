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

import ipipan.spejd.rules.AbstractRule;
import ipipan.spejd.util.Config;
import ipipan.spejd.util.Util;

import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * An abstract syntactic entity - with subclasses like segment, word, group or
 * sentence beginning / end.
 */
public abstract class Entity {
	public static final char NULL = '0';
	public static final char TOKEN = 't';
	public static final char SEGMENT = 's';
	public static final char NOSPACE = 'n';
	public static final char WORD = 'w';
	public static final char GROUP = 'g';

	public static final Entity[] ARRAY = new Entity[0];

	private static int lastId = 0;

	char type;
	String id;
	String text;
	boolean modified, hide = false;
	Entity replacement;
	AbstractRule rule = null; // the rule which created the entity

	String getNewId() {
		lastId++;
		return 'a' + Integer.toHexString(lastId);
	}

	/**
	 * Concatenate orth or base of a sequence of entities, adding space or not
	 * with respect to ns special entities.
	 * 
	 * @param id
	 *            attribute to concatenate (Attribute.ORTH or Attribute.BASE)
	 * @param s
	 *            an array of Entities
	 * @param start
	 *            start of the sequence to concatenate
	 * @param end
	 *            end of the sequence to concatenate
	 */
	public static String concatAttr(int id, Entity[] s, int start, int end) {
		StringBuilder res = new StringBuilder();
		boolean nospace = true;
		for (int i = start; i < end; i++) {
			if (s[i].type == NOSPACE) {
				nospace = true;
				continue;
			}
			if (!nospace)
				res.append(' ');
			res.append(s[i].getAttr(id));
			nospace = false;
		}
		return res.toString();
	}

	/**
	 * Concatenate orth or base of a sequence of entities, adding space or not
	 * with respect to ns special entities.
	 * 
	 * @param id
	 *            attribute to concatenate (Attribute.ORTH or Attribute.BASE)
	 * @param s
	 *            an array of Entities
	 */
	public static String concatAttr(int id, Entity[] s) {
		return concatAttr(id, s, 0, s.length);
	}

	/**
	 * Get common values of specified attributes of specified entities.
	 */
	public static Set<String> getCommonValues(Config config, Entity[] match, int[] attrIds) {
		Set<String> values;
		values = match[0].getAttrValues(attrIds, config.nullAgreement);
		for (int i = 1; i < match.length; i++)
			values.retainAll(match[i].getAttrValues(attrIds,
					config.nullAgreement));
		return values;
	}

	/**
	 * Unify specified entities with respect to specified attributes.
	 */
	public static boolean unify(Config config, Entity[] match, int[] attrIds) {
		Set<String> values = getCommonValues(config, match, attrIds);
		if (values.size() == 0)
			return false;
		Pattern pattern = Pattern.compile(Util.join("[^<>]+|", values)
				+ "[^<>]+");
		// System.err.println(pattern.pattern());
		for (int i = 0; i < match.length; i++)
			match[i].delete(pattern, true);
		return true;
	}

	public String getId() {
		return id;
	}

	/**
	 * Get orthographic or base form of an entity.
	 * 
	 * @param id
	 *            Attribute.ORTH or Attribute.BASE
	 * @return orthographic or base form for segments and words, empty string
	 *         for groups and special entities (ns, sb, se).
	 */
	public String getAttr(int id) {
		return "";
	}

	/**
	 * Get semantic head of an entity.
	 * 
	 * @return semantic head of a group, null otherwise
	 */
	public Segment getSemHead() {
		return null;
	}

	/**
	 * Get syntactic head of an entity.
	 * 
	 * @return semantic head of a group, null otherwise
	 */
	public Segment getSynHead() {
		return null;
	}

	/**
	 * Get group type.
	 * 
	 * @return type of a group, null otherwise
	 */
	public String getGroupType() {
		return null;
	}

	/**
	 * Get the set of interpretations.
	 * 
	 * @return a set of interpretations for segments and words, null for groups
	 *         and special entities (ns, sb, se).
	 */
	public Set<Interpretation> getInterpretations() {
		return null;
	}

	/**
	 * Get the set of interpretations.
	 * 
	 * @return a set of interpretations for segments and words, null for groups
	 *         and special entities (ns, sb, se).
	 */
	public Interpretation[] getInterpretationArray() {
		return null;
	}

	/**
	 * Delete all interpretations matching / not matching specified pattern.
	 */
	public void delete(Pattern pattern, boolean neg) {
	}

	/**
	 * Add interpretations, optionally deleting old ones.
	 */
	public void add(boolean delOld, String[] data) {
	}

	Set<String> getAttrValues(int[] attrIds, boolean acceptNull) {
		return new TreeSet<String>();
	}

	/**
	 * Sets replacement entity. Should be used for the first entity in a
	 * identified syntactic structure. During the next sentence update the
	 * entity will be replaced by the new structure.
	 */
	public void setReplacement(Entity e) {
		hide = true;
		replacement = e;
	}

	/**
	 * Sets rule reference for diagnostic purposes. Should be used only if the
	 * entity has been created (or modified) by a rule (ie. not read from the
	 * input file).
	 */
	public void setRule(AbstractRule rule) {
		this.rule = rule;
	}

	public Entity getReplacement() {
		if (!hide)
			return this;
		else
			return replacement;
	}

	abstract void updateTxt();

	/**
	 * Returns the representation of entity in the internal text format.
	 */
	public String toTxt() {
		if (modified)
			updateTxt();
		return text;
	}

	/**
	 * Prints the XML (XCES) representation of entity on the given PrintStream.
	 */
	abstract public void printXML(PrintStream out);
}
