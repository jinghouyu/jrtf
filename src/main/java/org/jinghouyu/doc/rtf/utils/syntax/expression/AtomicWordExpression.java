package org.jinghouyu.doc.rtf.utils.syntax.expression;

import org.jinghouyu.doc.rtf.utils.syntax.Expression;
import org.jinghouyu.doc.rtf.utils.syntax.ExpressionResult;
import org.jinghouyu.doc.rtf.utils.syntax.Word;
import org.jinghouyu.doc.rtf.utils.syntax.WordIterator;

public class AtomicWordExpression implements Expression {

	private String code;
	
	public AtomicWordExpression(String code) {
		this.code = code;
	}
	
	public ExpressionResult parse(WordIterator it) {
		Word word = it.next();
		if(word == null) return ExpressionResult.error();
		if(code.equals(word.getCode())) return ExpressionResult.success(word);
		it.back();
		return ExpressionResult.error();
	}
	
	public String toString() {
		return code;
	}
}