package ipipan.spejd.analyzer;

public interface MorphosyntacticAnalyzer {

	public MorphosyntacticInterpretation[] analyze(String segment);

	public int getTokensNumber();

}
