package org.jinghouyu.doc.rtf.utils.syntax.expression;

import org.jinghouyu.doc.rtf.utils.syntax.Expression;
import org.jinghouyu.doc.rtf.utils.syntax.Word;
import org.jinghouyu.doc.rtf.utils.syntax.WordIterator;

public class NonOrOneExpression implements Expression {

	private Expression expression;
	
	public NonOrOneExpression(Expression expression) {
		this.expression = expression;
	}
	
	public Word parse(WordIterator it) {
		return expression.parse(it);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(expression.toString());
		sb.append("?");
		return sb.toString();
	}
}