package ipipan.spejd.util;

import ipipan.spejd.tagset.Tagset;

import java.util.regex.Pattern;

/**
 * Configuration static wrapper, containing a set of variables with values.
 */
public class Config {

	/**
	 * The name of the default configuration file.
	 */
	public static String configFilename = "config.ini";
	
	private static Config instanceSingleton = new Config();

	public static Config getInstance() {
		return instanceSingleton;
	}

	// configuration variables

	public Tagset tagset;
	public String rulesFile;
	public String logDir;
	public char matchStrategy;
	public boolean nullAgreement, discardDeleted;
	public int reportInterval;
	public String inputType;
	public String inputEncoding;
	public Pattern inputFiles;
	public String outputSuffix;
	public String lexDictionaries;

	public int configure(String[] args, int start, int end) {
		ConfigDict config = new ConfigDict(configFilename);

		// backward compatibility check
		if (args.length >= 3 && !ConfigDict.isAssignment(args[1])
				&& !ConfigDict.isAssignment(args[2])) {
			config.put("tagset", args[0]);
			config.put("rules", args[1]);
			start = 2;
		}

		config.putAssignments(args, start + 1, end);

		logDir = config.getDir("logDir");
		tagset = new Tagset(config.get("tagset"));
		rulesFile = config.get("rules");
		inputType = config.get("inputType");
		inputEncoding = config.get("inputEncoding");
		inputFiles = Pattern.compile(config.get("inputFiles"));
		outputSuffix = config.get("outputSuffix");
		matchStrategy = config.getChar("matchStrategy", "*+?");
		nullAgreement = config.getBool("nullAgreement");
		discardDeleted = config.getBool("discardDeleted");
		reportInterval = config.getInt("reportInterval");
		lexDictionaries = config.get("lexDictionaries");
		return start;
	}

	public static void main(String[] args) {
		getInstance().configure(args, 0, 0);
	}

}
