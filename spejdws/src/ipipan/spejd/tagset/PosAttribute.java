package ipipan.spejd.tagset;

public class PosAttribute {
	String name;
	boolean optional;

	PosAttribute(String s) {
		if (s.startsWith("[") && s.endsWith("]")) {
			optional = true;
			name = s.substring(1, s.length() - 1);
		} else {
			optional = false;
			name = s;
		}
	}
}

