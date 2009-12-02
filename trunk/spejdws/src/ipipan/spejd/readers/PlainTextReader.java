/*
 * Copyright (C) 2008 by Instytut Podstaw Informatyki Polskiej
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

import ipipan.spejd.analyzer.MorphosyntacticAnalyzer;
import ipipan.spejd.analyzer.MorphosyntacticInterpretation;
import ipipan.spejd.entities.Entity;
import ipipan.spejd.entities.Interpretation;
import ipipan.spejd.entities.NoSpace;
import ipipan.spejd.entities.Segment;
import ipipan.spejd.util.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author axw
 */
public class PlainTextReader extends ipipan.spejd.readers.Reader {

	// morfeusz
	private final MorphosyntacticAnalyzer anal;

	private String fileContents; // loaded file
	private String lastWord; // to distinguish acronyms from real sentence
								// endings
	private final HashSet<String> noBreakDotAfterWords; // list of acronyms - if found
													// after ".", not a sentence
	private final HashSet<String> noBreakDotBeforeWords; // list of acronyms - if
													// found before ".", not a
													// sentence
	private int position; // where in the file is our reader?
	private boolean haveMoreSentences; // flag - are we at the end of file?
	private boolean inNoSpace; // flag - should we return NoSpace during the
								// next invocation?
	private boolean inEndOfSentence = false; // flag - are we at the end of
												// sentence?
	private boolean atBeginningOfFile = true; // flag for XML printing

	public int ogonkified = 0;
	public int ogonkified_total = 0;

	// FIXME - the method should be probably moved to Util

	static boolean isPunctuation(char ch) {
		int type = Character.getType(ch);
		return type == Character.DASH_PUNCTUATION
				|| type == Character.START_PUNCTUATION
				|| type == Character.END_PUNCTUATION
				|| type == Character.CONNECTOR_PUNCTUATION
				|| type == Character.OTHER_PUNCTUATION;
	}

	static boolean isSentenceBreak(char ch) {
		return ch == '.' || ch == '?' || ch == '!';
	}

	public PlainTextReader(Config config, MorphosyntacticAnalyzer analyzer) {
		super(config);

		/*try {
			morf = Morfeusz.getInstance();
		} catch (java.io.UnsupportedEncodingException uee) {
			System.err.print(uee.getStackTrace());
		}
		anal = morf.getAnalyzer();*/
		anal = analyzer;
		position = 0;
		// some of the below are actually incorrect (should not be followed by
		// .)
		String acronymsAfter[] = { "prof", "dr", "mgr", "doc", "ul", "np",
				"godz", "gen", "płk", "mjr", "por", "tzw", "tzn", "proc",
				"nt", "art", "ust", "ww", "www" };
		noBreakDotAfterWords = new HashSet<String>(Arrays.asList(acronymsAfter));
		String acronymsBefore[] = { "pl", "net", "biz", "com", "eu" };
		noBreakDotBeforeWords = new HashSet<String>(Arrays
				.asList(acronymsBefore));

		haveMoreSentences = true;
	}

	/**
	 * Reads the content of the text file and returns it as a string
	 * 
	 * @return The content of the file.
	 * @param fileName
	 *            The name of the file to read.
	 * @throws marker.Error
	 *             When somethings goes wrong with the reading.
	 */
	public String readTextFile(String fileName, String inputCharsetName) throws Error {
		try{
			FileInputStream stream = new FileInputStream(fileName);
			Reader r = new InputStreamReader(stream, inputCharsetName);
			return readTextFile(r);
		} catch (IOException ioe) {
			throw new Error("Error reading text file: " + ioe.getMessage());
		}
	}
	
	public String readTextFile(Reader r) throws Error {
		haveMoreSentences = true;
		position = 0;
		atBeginningOfFile = true; // to print XML at the next invocation of
									// nextSentence

		fileContents = "";
		try {
			int BUFFER_SIZE = 16384;
			int count;
			char buffer[] = new char[BUFFER_SIZE];

			//System.setProperty("file.encoding", inputCharset.name());
			while (true) {
				count = r.read(buffer, 0, BUFFER_SIZE);
				if (count < 0)
					break;
				fileContents = fileContents + new String(buffer, 0, count);
			}
			r.close();

		} catch (IOException ioe) {
			throw new Error("Error reading text file: " + ioe.getMessage());
		}
		return fileContents;
	}

	/**
	 * Splits multiple interpretations written in one string in to seperate
	 * strings
	 * 
	 * @param interpretation
	 *            The interpretation to be split.
	 * @return A collection of strings - the seperate (sub)interpretations.
	 */
	private Collection<String> splitInterpretation(String interpretation) {
		Collection<String> retVal = new HashSet<String>();

		/* - split by '|' char */
		String[] parts = interpretation.split("[|]");

		for (int i = 0; i < parts.length; i++) {
			String[] colons = parts[i].split("[:]");
			iterateAndAdd(colons, 0, retVal, "");
		}

		return retVal;
	}

	/**
	 * Iterates over a table of interpretation parts and collects a cartesian
	 * product of possible interpretations
	 */
	private void iterateAndAdd(String[] table, int index,
			Collection<String> retVal, String soFar) {
		if (index >= table.length)
			retVal.add(soFar.substring(1)); /* adds without the leading ":" */
		else {
			String[] dots = table[index].split("[.]");
			for (int j = 0; j < dots.length; j++)
				iterateAndAdd(table, index + 1, retVal, soFar + ":" + dots[j]);
		}
	}

	/**
	 * Generate combinations of oginkified words, pick those that morfeusz
	 * understands
	 * 
	 * @param orth
	 *            baseform not recognized
	 * @return
	 */
	private LinkedList<Interpretation> ogonkify(String orth) {
		List<String> candidates = Ogonkifier.ogonkify(orth);
		LinkedList<Interpretation> new_interpretations = new LinkedList<Interpretation>();

		for (String candidate : candidates) {
			MorphosyntacticInterpretation[] interps = null;
			try {
				interps = anal.analyze(candidate);
			} catch (Exception e) {
			}

			if (interps != null) { /* there are some interpretations */
				for (int j = 0; j < anal.getTokensNumber(); j++) { /*
																	 * for each
																	 * returned
																	 * interpretation
																	 */
					String interpretation = interps[j].getTagImage();
					if (!interpretation.equals("")) {
						ogonkified++;
						ogonkified_total++;
						/*
						 * sometimes Morfeusz returns many interpretations in
						 * one entry
						 */
						Collection<String> subInterps = splitInterpretation(interpretation);
						Iterator<String> it = subInterps.iterator();
						while (it.hasNext()) {
							String currentTags = it.next();
							for (String ftags : config.tagset
									.cToFtagArray(currentTags))
								new_interpretations.add(new Interpretation(config,
										ftags + interps[j].getLemmaImage(),
										false));
						}
					}
				}
			}
		}

		// subtract the surface form
		if (candidates.size() > 1) {
			ogonkified--;
			ogonkified_total--;
		}

		return new_interpretations;
	}

	/**
	 * Utility to see what word starts from position in domain.com
	 * 
	 * @param pos
	 *            start word construction here
	 * @return word of up to 4 alpha characters as seen starting from pos
	 */
	public String getNextToken(int pos) {

		int startfrom = pos;
		StringBuilder sb = new StringBuilder();
		Character ch;
		// go forward max 4 chars
		while (Character.isLetter((ch = fileContents.charAt(pos)))
				&& startfrom + 5 > pos) {
			sb.append(ch);
			pos++;
		}
		return sb.toString();
	}

	/**
	 * Move to another word and run Morfeusz over it. Method returns null if end
	 * of sentence has been reached.
	 * 
	 * @return next segment with interpretations polled from fileContents String
	 */
	@Override
	public Entity loadToken() {

		// word form as it appeared in text
		String orth = "";

		Character ch;
		boolean inWord = false;

		tokenReading: while (position < fileContents.length()) {

			if (inEndOfSentence) {
				inEndOfSentence = false;
				return null; // end of sentence)
			}

			ch = fileContents.charAt(position);

			if (Character.isWhitespace(ch)) {
				inNoSpace = false;
			} else {
				if (inNoSpace) {
					inNoSpace = false;
					return new NoSpace();
				}
			}
			position++;

			// letter?
			if (Character.isLetterOrDigit(ch)) {
				if (!inWord)
					inWord = true;
				orth = orth + ch;
			} else
			// punctuation - return as a segment
			if (isPunctuation(ch) && !isSentenceBreak(ch)) {
				if (inWord) {
					inNoSpace = true;
					position--;
					break tokenReading;
				} else {
					Interpretation[] punct_interp = new Interpretation[1];
					punct_interp[0] = new Interpretation(config, config.tagset
							.cToFtag("interp")
							+ ch.toString(), true);
					return new Segment(config, null, ch.toString(), punct_interp);
				}

			} else
			// could be a sentence break?
			if (isSentenceBreak(ch)) {

				inNoSpace = true;
				// we're in a word, process this word and later come back for
				// the "."
				if (inWord && orth.length() > 0) {
					position--;
					break tokenReading;

				} else {
					boolean nextSentence = false;
					// compare known abbreviations against the previous word
					if (lastWord != null) {
						nextSentence = true;
						if (ch == '.')
							if (noBreakDotAfterWords.contains(lastWord
									.toLowerCase()))
								nextSentence = false;
					}
					// checking for ...
					if (nextSentence) {
						if (fileContents.length() > position + 1) {
							char cr = fileContents.charAt(position);
							if (isSentenceBreak(cr)) {
								nextSentence = false;
							}
						} else
							nextSentence = false;
					}
					// check next word
					if (nextSentence) {
						String nextTok = getNextToken(position);
						if (nextTok != null) {
							if (ch == '.')
								if (noBreakDotBeforeWords.contains(nextTok
										.toLowerCase()))
									nextSentence = false;
						}
					}
					if (nextSentence) {
						lastWord = null;
						inEndOfSentence = true;
					} else
						inEndOfSentence = false;
					Interpretation[] punct_interp = new Interpretation[1];
					punct_interp[0] = new Interpretation(config, config.tagset
							.cToFtag("interp")
							+ ch.toString(), true);
					return new Segment(config, null, ch.toString(), punct_interp);
				}

				// anything else
			} else {
				// hand over the word for processing
				if (inWord) {
					break tokenReading;
				}
			}
		}
		// process the current token but no more sententes
		if (position > fileContents.length() - 1 && orth.length() == 0) {
			haveMoreSentences = false;
			return null;
		}
		lastWord = orth;

		// ibuf = set of pairs: baseform+tags generated by Morfeusz
		MorphosyntacticInterpretation[] interps = null;
		try {
			interps = anal.analyze(orth);
		} catch (Exception e) {
		}

		LinkedList<Interpretation> interpretations = new LinkedList<Interpretation>();
		if (interps != null) { /* there are some interpretations */

			for (int j = 0; j < anal.getTokensNumber(); j++) { /*
																 * for each
																 * returned
																 * interpretation
																 */
				String interpretation = interps[j].getTagImage();
				if (interpretation.equals("")) { /*
												 * Morfeusz doesn't know what it
												 * is
												 */

					// try ogonkification, append all obtained Interpretations
					LinkedList<Interpretation> new_interpretations = ogonkify(orth);
					// LinkedList<Interpretation> new_interpretations = new
					// LinkedList<Interpretation>();
					if (new_interpretations.size() > 0)
						interpretations.addAll(new_interpretations);
					else {
						interpretations.add(new Interpretation(config, config.tagset
								.cToFtag("ign")
								+ orth, false));
					}
				} else { /* Morfeusz has found some interpretations */

					/*
					 * sometimes Morfeusz returns many interpretations in one
					 * entry
					 */
					Collection<String> subInterps = splitInterpretation(interpretation);
					Iterator<String> it = subInterps.iterator();
					while (it.hasNext()) {
						String currentTags = it.next();
						for (String ftags : config.tagset
								.cToFtagArray(currentTags))
							interpretations.add(new Interpretation(config, ftags
									+ interps[j].getLemmaImage(), false));
					}
				}
			}
		}
		return new Segment(config, null, orth, interpretations
				.toArray(Interpretation.array));
	}

	public void setFileContents(String fileContents) {
		this.fileContents = fileContents;
		haveMoreSentences = true;
		position = 0;
	}

	@Override
	public boolean nextSentence(PrintStream out) {

		if (this.atBeginningOfFile) {
			out
					.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
							+ "<!DOCTYPE cesAna SYSTEM \"xcesAnaIPI.dtd\">\n"
							+ "<cesAna xmlns:xlink=\"http://www.w3.org/1999/xlink\" type=\"pre_morph\" version=\"IPI-1.2\">\n"
							+ "<chunkList>\n");
			atBeginningOfFile = false;
		}
		if (!this.haveMoreSentences) {
			out.println("</chunkList>\n" + "</cesAna>\n");
		}
		return this.haveMoreSentences;
	}

}
