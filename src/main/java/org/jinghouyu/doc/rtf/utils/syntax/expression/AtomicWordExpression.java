package org.jinghouyu.doc.rtf.utils.syntax.expression;

import org.jinghouyu.doc.rtf.utils.syntax.Expression;
import org.jinghouyu.doc.rtf.utils.syntax.Word;
import org.jinghouyu.doc.rtf.utils.syntax.WordIterator;

public class AtomicWordExpression implements Expression {

	private String code;
	
	public AtomicWordExpression(String code) {
		this.code = code;
	}
	
	public Word parse(WordIterator it) {
		Word word = it.next();
		if(word.getCode().equals(code)) return word;
		it.back();
		return null;
	}
	
	public String toString() {
		return code;
	}
}