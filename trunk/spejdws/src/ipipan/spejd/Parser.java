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

package ipipan.spejd;

import ipipan.spejd.readers.InputFileFilter;
import ipipan.spejd.rules.RuleSet;
import ipipan.spejd.rules.Sentence;
import ipipan.spejd.util.Config;
import ipipan.spejd.util.DirFileFilter;
import ipipan.spejd.util.TimeWatch;

import java.io.File;
import java.io.FileFilter;

/**
 * Main SPADE/Spejd class.
 */
public class Parser {
	RuleSet rules;
	TimeWatch ttime, ptime;
	long tinit, tload, tparse, tsave;
	FileFilter subdirs, inputFiles;
	Config config;

	int files;
	Sentence chunk;

	public Parser(Config config) {
		this.config = config;
		
		ttime = new TimeWatch();
		ptime = new TimeWatch();
		subdirs = new DirFileFilter();
		inputFiles = new InputFileFilter(config);
		tload = tparse = tsave = 0;
		rules = new RuleSet(config, config.rulesFile);
		ttime.display("RuleSet ready: ");
		tinit = ptime.getInterval();
		files = 0;
		chunk = new Sentence(config);
	}

	void parseFile(File path) {
		chunk.openFile(path);
		int n = 0;
		System.err.println("Shallowparsing " + path.getPath());
		while (chunk.loadSentence()) {
			tload += ptime.getInterval();
			rules.applyTo(chunk);
			tparse += ptime.getInterval();
			chunk.printXML();
			tsave += ptime.getInterval();
			n++;
			if (config.reportInterval > 0 && n % config.reportInterval == 0)
				ttime.display(n + " sentences parsed: ");
		}
	}

	public void parseDirectory(File path) {
		if (!path.exists()) {
			throw new RuntimeException("Error: file/directory does not exists.");
		}

		if (!path.isDirectory()) {
			parseFile(path);
			files++;
			return;
		}

		for (File f : path.listFiles(inputFiles)) {
			parseFile(f);
			files++;
		}

		for (File f : path.listFiles(subdirs))
			parseDirectory(f);
	}

	public void report() {
		System.out.println(files + " file(s) parsed.");
		rules.report();
		if (chunk.dict != null)
			chunk.dict.report();
		System.out.println("Init: " + tinit + "; load: " + tload + "; parse: "
				+ tparse + "; save " + tsave + " ms");
	}

	/**
	 * Loads rule set, parse the give directory and write a summary of results.
	 */
	public static void main(String[] args) {
		try {
			if (args.length < 1) {
				System.out.println("Usage: java Parser DIR [OPTIONS]");
				System.exit(2);
			}
			int a = Config.getInstance().configure(args, 0, 0);
			Parser p = new Parser(Config.getInstance());
			p.parseDirectory(new File(args[a]));
			p.report();
			Config.getInstance().tagset.saveTagDictionary(Config.getInstance().logDir);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
