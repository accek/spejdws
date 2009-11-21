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
import ipipan.spejd.tagset.Attribute;
import ipipan.spejd.util.Config;
import ipipan.spejd.util.TimeWatch;
import ipipan.spejd.util.Util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Not official yet.
 */
public class Gazetteer implements Match {

	TreeMap<String, GazetteerEntry> gaz;
	ArrayList<GazetteerEntry> entryList;

	int order;

	Entity[] chunk;
	int pos;
	int gtime;
	TimeWatch timer;
	
	protected Config config;

	public Gazetteer(Config config, int order) {
		this.config = config;
		this.order = order;
		gaz = new TreeMap<String, GazetteerEntry>();
		entryList = new ArrayList<GazetteerEntry>();
		timer = new TimeWatch();
		gtime = 0;
	}

	public void addEntry(GazetteerEntry entry) {
		entryList.add(entry);
		String[] keys = entry.fromFile ? Util.file(config, entry.key) : entry.key
				.split("\\|");
		for (int i = 0; i < keys.length; i++)
			gaz.put(keys[i], entry);
	}

	String getKey() {
		return Entity.concatAttr(Attribute.ORTH, chunk, pos, pos + order);
	}

	public boolean apply(Entity[] chunk) {
		GazetteerEntry r;
		boolean modified = false;
		timer.getInterval();

		this.chunk = chunk;
		for (pos = 0; pos <= chunk.length - order; pos++) {
			r = gaz.get(getKey());
			if (r != null)
				modified |= r.applyActions(this);
		}

		gtime += timer.getInterval();
		return modified;
	}

	public Entity getSingle(int s) {
		return chunk[pos + s];
	}

	public Entity[] getSpec(int s) {
		Entity[] res = new Entity[1];
		res[0] = chunk[pos + s];
		return res;
	}

	public Entity[] get(int[] s) {
		Entity[] res = new Entity[s.length];
		for (int i = 0; i < s.length; i++)
			res[i] = chunk[pos + s[i]];
		return res;
	}

	public Entity[] getMatch() {
		Entity[] res = new Entity[order];
		for (int i = 0; i < order; i++)
			res[i] = chunk[pos + i];
		return res;
	}

	public void log(PrintStream log) {
		log.println("Gazetteer: " + order);
		log.println("Entries: " + entryList.size() + ", keys: " + gaz.size());
		log.println();
		for (GazetteerEntry entry : entryList)
			entry.log(log);
	}

	public void report(PrintStream log) {
		GazetteerEntry[] rules = entryList.toArray(new GazetteerEntry[0]);

		int completed = 0, matched = 0;
		long taction = 0;

		for (int i = 0; i < rules.length; i++) {
			rules[i].report(log);
			completed += rules[i].completed;
			matched += rules[i].matched;
			taction += rules[i].taction;
		}

		log.print("\"Gazetter " + order + "\",");
		log.print(completed);
		log.print(',');
		log.print(matched);
		log.print(',');
		log.print(gtime - taction);
		log.print(',');
		log.print(taction);
		log.print(',');
		log.println(gtime);

		System.out.println("Gazetteer " + order + " time: " + gtime + " ms");
	}
}
