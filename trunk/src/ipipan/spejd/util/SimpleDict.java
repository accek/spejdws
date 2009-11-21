package ipipan.spejd.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TreeMap;

/**
 * Configuration class, containing a set of variables with values.
 */
public class SimpleDict {
	protected TreeMap<String, String> dict;

	/**
	 * Creates a new configuration with no variables.
	 */
	public SimpleDict() {
		dict = new TreeMap<String, String>();
		load(SimpleDict.class.getResourceAsStream("/ipipan/spejd/config.ini"));
	}

	/**
	 * Creates a new configuration with variables loaded from a given filename.
	 */
	public SimpleDict(String filename) {
		this();
		load(filename);
	}

	/**
	 * Loads configuration from a specified file.
	 * 
	 * @param filename
	 *            name of the configuration file
	 */
	
	public void load(String filename) {
		try {
			load(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void load(InputStream stream) {
		int i, line = 1;

		try {
			BufferedReader d;
			d = new BufferedReader(new InputStreamReader(stream, "utf-8"));
			String inputLine;

			while ((inputLine = d.readLine()) != null) {
				line++;
				inputLine = inputLine.trim();
				// empty line
				if (inputLine.length() == 0) {
					continue;
				}
				// comment
				if (inputLine.charAt(0) == '#' || inputLine.charAt(0) == ';') {
					continue;
				}
				// find =
				for (i = 1; i < inputLine.length(); i++) {
					if (inputLine.charAt(i) == '=') {
						break;
					}
				}
				if (i == inputLine.length()) {
					System.err.println("Line " + line
							+ " is not assignment:");
					System.err.println(inputLine);
					continue;
				}
				put(inputLine.substring(0, i).trim(), inputLine
						.substring(i + 1).trim());
			}
			d.close();
		} catch (IOException e) {
			System.err.println("SimpleDict.load(): "
					+ e.getMessage());
		}
	}

	/**
	 * Sets value of a configuration variable.
	 * 
	 * @param var
	 *            name of the variable to set
	 * @param value
	 *            value of the variable as String
	 */
	public void put(String var, String value) {
		dict.put(var, value);
	}

	public TreeMap<String, String> getDict() {
		return dict;
	}
}
