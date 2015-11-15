package org.jinghouyu.doc.rtf.utils.syntax.expression;

import org.jinghouyu.doc.rtf.utils.syntax.Expression;
import org.jinghouyu.doc.rtf.utils.syntax.ExpressionResult;
import org.jinghouyu.doc.rtf.utils.syntax.WordIterator;

public class NonOrOneExpression implements Expression {

	private Expression expression;
	
	public NonOrOneExpression(Expression expression) {
		this.expression = expression;
	}
	
	public ExpressionResult parse(WordIterator it) {
		ExpressionResult er = expression.parse(it);
		return ExpressionResult.success(er.getWord());
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(expression.toString());
		sb.append("?");
		return sb.toString();
	}
}