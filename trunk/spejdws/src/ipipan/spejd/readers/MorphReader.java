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

package ipipan.spejd.readers;

import ipipan.spejd.entities.Entity;
import ipipan.spejd.entities.Interpretation;
import ipipan.spejd.entities.NoSpace;
import ipipan.spejd.entities.Segment;
import ipipan.spejd.util.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class MorphReader extends Reader {

	BufferedReader in;
	int lineno;
	String line;
	ArrayList<Interpretation> ibuf;
	
	public MorphReader(Config config) {
		super(config);
	}

	private static BufferedReader readerFromFile(Config config, File path) {
		try {
			InputStream s = new FileInputStream(path);
			if (path.getName().endsWith(".gz"))
				s = new GZIPInputStream(s);
			return new BufferedReader(new InputStreamReader(s,
					config.inputEncoding));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public MorphReader(Config config, File path) {
		this(config, readerFromFile(config, path));
	}

	public MorphReader(Config config, BufferedReader reader) {
		this(config);
		in = reader;
		lineno = 0;
		ibuf = new ArrayList<Interpretation>();
		nextLine();
	}

	void nextLine() {
		String s = "";
		try {
			while ((s != null) && (s.length() == 0)) {
				lineno++;
				s = in.readLine();
			}
			line = s;
		} catch (IOException e) {
			line = null;
		}
	}

	void error(String desc) {
		throw new RuntimeException("Error: " + desc + "\nAt line " + lineno + "\n" + line);
	}

	// word form as it appeared in text
	String getOrth() {
		if (!line.startsWith("<orth>")) {
			error("<orth> expected");
			return null;
		}

		return line.substring(6, line.indexOf("</orth>"));
	}

	// 
	Interpretation getInterp() {
		int i = 0, j = 0;
		if (!line.startsWith("<lex")) {
			error("<lex> expected");
			return null;
		}

		i = line.indexOf("<base>") + 6;
		j = line.indexOf("</base>", i);
		String base = line.substring(i, j);

		i = line.indexOf("<ctag>", j) + 6;
		j = line.indexOf("</ctag>", i);
		String ctag = line.substring(i, j);

		return new Interpretation(config, config.tagset.cToFtag(ctag) + base, line
				.indexOf("disamb=\"1\"") != -1);
	}

	String getId() {
		if (!line.startsWith("<tok")) {
			error("<tok> expected");
			return null;
		}
		int i = line.indexOf("id=\"");
		if (i == -1)
			return null;

		i += 4;
		return line.substring(i, line.indexOf('"', i));
	}

	@Override
	public Entity loadToken() {
		String id, orth;

		ibuf.clear();

		if (line.equals("</chunk>")) {
			nextLine();
			return null;
		}

		if (line.equals("<ns/>")) {
			nextLine();
			return new NoSpace();
		}

		id = getId();
		nextLine();
		orth = getOrth();
		nextLine();
		while (!line.equals("</tok>")) {
			ibuf.add(getInterp());
			nextLine();
		}
		nextLine();
		return new Segment(config, id, orth, ibuf.toArray(Interpretation.array));
	}

	/**
	 * 
	 * @param out
	 * @return true if there exists a next sentence
	 */
	@Override
	public boolean nextSentence(PrintStream out) {
		while (true) {
			if (line == null)
				return false;
			if (line.equals("<chunk type=\"s\">"))
				break;
			out.println(line);
			nextLine();
		}
		;

		nextLine();
		return true;
	}

	public static void main(String[] args) throws IOException {
		Config.getInstance().configure(args, 0, 0);
		MorphReader in = new MorphReader(Config.getInstance(), new File(args[0]));
		in.nextSentence(System.out);
		while (in.loadToken() != null)
			;
		in.nextSentence(System.out);
	}
}
