package org.jinghouyu.doc.rtf.utils.syntax.expression;

import java.util.ArrayList;
import java.util.List;

import org.jinghouyu.doc.rtf.utils.syntax.Expression;
import org.jinghouyu.doc.rtf.utils.syntax.ExpressionResult;
import org.jinghouyu.doc.rtf.utils.syntax.WordIterator;

public class OrExpression implements Expression {

	private List<Expression> expressions = new ArrayList<Expression>();
	
	public ExpressionResult parse(WordIterator it) {
		for(Expression exp : expressions) {
			ExpressionResult er = exp.parse(it);
			if(er.isSuccess()) {
				return ExpressionResult.success(er.getWord());
			}
		}
		return ExpressionResult.error();
	}
	
	public void addExpression(Expression exp) {
		expressions.add(exp);
	}
	
	public int expressionSize() {
		return expressions.size();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(int i = 0; i < expressions.size(); i++) {
			sb.append(expressions.get(i).toString());
			if(i != expressions.size() - 1) {
				sb.append("|");
			}
		}
		sb.append(")");
		return sb.toString();
	}
}
