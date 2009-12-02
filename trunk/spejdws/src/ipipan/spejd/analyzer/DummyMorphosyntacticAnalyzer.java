package ipipan.spejd.analyzer;

import ipipan.spejd.util.Config;

public class DummyMorphosyntacticAnalyzer implements MorphosyntacticAnalyzer {

	public DummyMorphosyntacticAnalyzer(Config config) {
	}

	public MorphosyntacticInterpretation[] analyze(String segment) {
		// This way PlainTextParser will think, that Morfeusz does not
		// recognize the given segment.
		return new MorphosyntacticInterpretation[]{
				new MorphosyntacticInterpretation("", segment)
		};
	}

	public int getTokensNumber() {
		return 1;
	}

}
