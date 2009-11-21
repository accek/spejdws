package ipipan.spejd.analyzer;

import ipipan.spejd.tagset.Pos;
import ipipan.spejd.util.Config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllInterpretationsMorphosyntacticAnalyzer implements MorphosyntacticAnalyzer {
	private int numInterp;
	
	protected Config config;
	
	public AllInterpretationsMorphosyntacticAnalyzer(Config config) {
		super();
		this.config = config;
	}

	public MorphosyntacticInterpretation[] analyze(String segment) {
		// what pos is it? have to ugly iterate pos objects collection.
		Iterator<Pos> it = config.tagset.getPosList().listIterator();
		Pos pos = null;
		List<String> allTags = new ArrayList<String>();
		while (it.hasNext()) {
			pos = it.next();
			
			String template = pos.getName();
			for (int i = 0; i < pos.getAttributes().length; i++) {
				template = template + ":_";
			}
			
			String[] ftags = config.tagset.cToFtagArray(template);
			for (int i = 0; i < ftags.length; i++) {
				allTags.add(ftags[i]);
			}
		}
		
		MorphosyntacticInterpretation[] allInterpretations = new MorphosyntacticInterpretation[allTags.size()];
		for (int i = 0; i < allInterpretations.length; i++) {
			String tag = allTags.get(i);
			allInterpretations[i] = new MorphosyntacticInterpretation(tag, segment);
		}
		numInterp = allInterpretations.length;

		return allInterpretations;
	}

	public int getTokensNumber() {
		return numInterp;
	}

}
