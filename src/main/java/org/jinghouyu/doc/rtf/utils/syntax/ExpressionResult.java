package org.jinghouyu.doc.rtf.utils.syntax;

public class ExpressionResult {

	private boolean success;
	private Word word;
	
	private ExpressionResult() {}
	
	public static ExpressionResult success(Word w) {
		ExpressionResult result = new ExpressionResult();
		result.success =  true;
		result.word = w;
		return result;
	}
	
	public static ExpressionResult error() {
		ExpressionResult result = new ExpressionResult();
		result.success = false;
		return result;
	}
	
	public boolean isSuccess() {
		return this.success;
	}
	
	public Word getWord() {
		return this.word;
	}
}
