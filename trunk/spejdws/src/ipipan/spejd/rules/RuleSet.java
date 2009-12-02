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

import ipipan.spejd.util.Config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class RuleSet {
	Rule[] rules;
	Gazetteer[] gazetteers;
	
	protected Config config;

	public RuleSet(Config config, String filename) {
		this.config = config;
		loadRules(filename);
		System.out.println("RuleSet(): " + rules.length
				+ " rules compiled, see " + config.logDir
				+ "rules.compiled for details.");
	}

	public RuleSet(Config config, BufferedReader in, PrintStream log) {
		this.config = config;
		loadRules(in, log);
	}

	public Config getConfig() {
		return config;
	}

	public void loadRules(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
					filename), "utf-8"));
			PrintStream log = new PrintStream(new FileOutputStream(config.logDir
					+ "rules.compiled"), true, "utf-8");
			loadRules(reader, log);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadRules(BufferedReader in, PrintStream log) {
		Rule rule;
		Gazetteer gaz;

		ArrayList<Rule> ruleSet = new ArrayList<Rule>();
		ArrayList<Gazetteer> gazSet = new ArrayList<Gazetteer>();

		try {
			RuleParser parser = new RuleParser(config, in);

			// read macro definitions
			while (parser.readMacroDef())
				;

			// read gazetteers
			while ((gaz = parser.readGazetteer()) != null) {
				gazSet.add(gaz);
				gaz.log(log);
			}

			// read rules
			while ((rule = parser.readRule()) != null) {
				ruleSet.add(rule);
				rule.log(log);
			}
			in.close();
			log.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		gazetteers = gazSet.toArray(new Gazetteer[0]);
		rules = ruleSet.toArray(new Rule[0]);
	}

	public void applyTo(Sentence sentence) {
		int i;
		for (i = 0; i < gazetteers.length; i++)
			if (gazetteers[i].apply(sentence.toArray()))
				sentence.update();

		for (i = 0; i < rules.length; i++)
			if (rules[i].apply(sentence))
				sentence.update();
	}

	public void report() {
		PrintStream log;
		try {
			log = new PrintStream(new FileOutputStream(config.logDir
					+ "rules.matched.csv"), true, "utf-8");
			report(log);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void report(PrintStream log) {
		int completed = 0, matched = 0;
		long tmatch = 0, taction = 0;

		log.print("Rule title,Completed,Matched,");
		log.println("Match[ms],Eval[ms],Time[ms]");

		for (int i = 0; i < gazetteers.length; i++)
			gazetteers[i].report(log);

		for (int i = 0; i < rules.length; i++) {
			rules[i].report(log);
			completed += rules[i].completed;
			matched += rules[i].matched;
			tmatch += rules[i].tmatch;
			taction += rules[i].taction;
		}
		log.print("\"ALL\",");
		log.print(completed);
		log.print(',');
		log.print(matched);
		log.print(',');
		log.print(tmatch);
		log.print(',');
		log.print(taction);
		log.print(',');
		log.println(tmatch + taction);
		log.close();

		System.out.println("Found " + matched + " matches, completed "
				+ completed + " rules, see " + config.logDir
				+ "rules.matched.csv for details.");
	}

	public static void main(String[] args) {
		// Tagset.init();
		new RuleSet(Config.getInstance(), args[0]);
	}
}
