package org.jinghouyu.doc.rtf.utils.syntax.expression;

import java.util.ArrayList;
import java.util.List;

import org.jinghouyu.doc.rtf.utils.syntax.Expression;
import org.jinghouyu.doc.rtf.utils.syntax.ExpressionResult;
import org.jinghouyu.doc.rtf.utils.syntax.Sentence;
import org.jinghouyu.doc.rtf.utils.syntax.Sentence.DefaultSentence;
import org.jinghouyu.doc.rtf.utils.syntax.Word;
import org.jinghouyu.doc.rtf.utils.syntax.WordIterator;

public class OrderExpression implements Expression {

	private List<Expression> expressions = new ArrayList<Expression>();
	
	public ExpressionResult parse(WordIterator it) {
		Sentence s = newSentence();
		for(int i = 0; i < expressions.size(); i++) {
			Expression exp = expressions.get(i);
			ExpressionResult er = exp.parse(it);
			if(!er.isSuccess()) {
				return ExpressionResult.error();
			} else {
				Word w = er.getWord();
				if(w != null) {
					s.addWord(w);
				}
			}
		}
		return ExpressionResult.success(s);
	}
	
	public void addExpression(Expression exp) {
		expressions.add(exp);
	}
	
	public Expression getLastExpression() {
		if(expressions.size() == 0) return null;
		return expressions.get(expressions.size() - 1);
	}
	
	public void removeLast() {
		if(expressions.size() == 0) return;
		expressions.remove(expressions.size() - 1);
	}

	protected Sentence newSentence() {
		return new DefaultSentence();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(int i = 0; i < expressions.size(); i++) {
			sb.append(expressions.get(i).toString());
		}
		sb.append(")");
		return sb.toString();
	}
}