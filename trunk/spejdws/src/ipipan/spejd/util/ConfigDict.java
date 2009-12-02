package ipipan.spejd.util;

import java.io.File;
import java.security.AccessControlException;

/**
 * Configuration class, containing a set of variables with values.
 */
public class ConfigDict extends SimpleDict {

	/**
	 * Creates a new configuration with variables loaded from a given filename.
	 */
	public ConfigDict(String filename) {
		super(filename);
	}

	/**
	 * The name of the default configuration file.
	 */
	public static final String configFilename = "config.ini";

	public static boolean isAssignment(String assign) {
		if (assign.length() == 0) {
			return false;
		}

		return assign.indexOf('=') > 0;
	}

	/**
	 * Sets value of a configuration variable.
	 * 
	 * @param assign
	 *            assignment in a form of <code>variable=value</code>
	 * @return true, if assign is valid assignment, false otherwise
	 */
	public boolean putAssignment(String assign) {

		if (!isAssignment(assign)) {
			System.err.print("Not a proper assignment: ");
			System.err.println(assign);
			return false;
		}

		int i = assign.indexOf('=');

		put(assign.substring(0, i).trim(), assign.substring(i + 1).trim());

		return true;
	}

	public boolean putAssignments(String[] args, int start, int end) {
		boolean res = true;
		if (end <= 0)
			end = args.length - end;
		for (int i = start; i < end; i++)
			res = res && putAssignment(args[i]);
		return res;
	}

	/**
	 * Gets the value of a configuration String variable.
	 * 
	 * @param var
	 *            name of the variable to get
	 * @return value of the variable
	 */
	public String get(String var) {
		String s = dict.get(var);
		if (s != null) {
			return s;
		} else {
			return notFound(var);
		}
	}

	/**
	 * Gets the value of a configuration int variable.
	 * 
	 * @param var
	 *            name of the variable to get
	 * @return value of the variable
	 */
	public int getInt(String var) {
		return Integer.parseInt(get(var));
	}

	/**
	 * Gets the value of a configuration hexadecimal variable.
	 * 
	 * @param var
	 *            name of the variable to get
	 * @return value of the variable
	 */
	public int getHex(String var) {
		return Integer.parseInt(get(var), 16);
	}

	/**
	 * Gets the value of a configuration boolean variable.
	 * 
	 * @param var
	 *            name of the variable to get
	 * @return value of the variable
	 */
	public boolean getBool(String var) {
		String s = get(var);
		if (s.equalsIgnoreCase("true")) {
			return true;
		}
		if (s.equalsIgnoreCase("yes")) {
			return true;
		}
		if (s.equalsIgnoreCase("on")) {
			return true;
		}
		if (s.equalsIgnoreCase("1")) {
			return true;
		}

		if (s.equalsIgnoreCase("false")) {
			return false;
		}
		if (s.equalsIgnoreCase("no")) {
			return false;
		}
		if (s.equalsIgnoreCase("off")) {
			return false;
		}
		if (s.equalsIgnoreCase("0")) {
			return false;
		}

		System.err.println("Config: variable " + var
				+ " has no valid boolean value.");
		return false;
	}

	public String getDir(String var) {
		String s = get(var);
		if (!s.endsWith("/"))
			s += "/";
		try {
			(new File(s)).mkdir();
		} catch (AccessControlException e) {
		}
		return s;
	}

	public char getChar(String var, String allowed) {
		String s = get(var);
		if (s.length() == 1) {
			char c = s.charAt(0);
			if (allowed.indexOf(c) >= 0)
				return c;
		}
		System.err.println("Config: variable " + var
				+ " should be a single character, one of the following: ["
				+ allowed + "]");
		return ' ';
	}

	/**
	 * Method called when a requested variable can not be found. Can be
	 * overriden by subclasses to allow a "plan B".
	 * 
	 * @param var
	 *            name of the variable requested
	 * @return null
	 */
	public String notFound(String var) {
		System.err.println("Config: variable " + var + " not found");
		return null;
	}
}
