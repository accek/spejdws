package ipipan.spejd.analyzer;

public class MorphosyntacticInterpretation {
	private final String tagImage;
	private final String lemmaImage;
	
	public MorphosyntacticInterpretation(String tagImage, String lemmaImage) {
		super();
		this.tagImage = tagImage;
		this.lemmaImage = lemmaImage;
	}

	public String getTagImage() {
		return tagImage;
	}

	public String getLemmaImage() {
		return lemmaImage;
	}
}
