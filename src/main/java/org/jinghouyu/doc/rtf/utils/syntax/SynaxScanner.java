package org.jinghouyu.doc.rtf.utils.syntax;

public class SynaxScanner {
	
	private char[] syntax = null;
	private int pos = 0;
	
	public SynaxScanner(String syntax) {
		this.syntax = syntax.toCharArray();
	}
	
	public boolean hasNext() {
		return pos < syntax.length;
	}
	
	public char next() {
		return syntax[pos++];
	}
	
	public int pos() {
		return pos;
	}
	
	public void back() {
		
	}
}