package org.jinghouyu.doc.rtf.utils.syntax.expression;

import org.jinghouyu.doc.rtf.utils.syntax.Expression;
import org.jinghouyu.doc.rtf.utils.syntax.ExpressionResult;
import org.jinghouyu.doc.rtf.utils.syntax.Sentence;
import org.jinghouyu.doc.rtf.utils.syntax.Sentence.DefaultSentence;
import org.jinghouyu.doc.rtf.utils.syntax.Word;
import org.jinghouyu.doc.rtf.utils.syntax.WordIterator;

public class NonOrLimitlessExpression implements Expression {

	private Expression expression;
	
	public NonOrLimitlessExpression(Expression exp) {
		this.expression = exp;
	}
	
	public ExpressionResult parse(WordIterator it) {
		Sentence s = null;
		while(true) {
			ExpressionResult er = expression.parse(it);
			if(!er.isSuccess()) {
				break;
			}
			Word w = er.getWord();
			if(w != null) {
				if(s == null) {
					s = newSentence();
				}
				s.addWord(w);
			}
		}
		return ExpressionResult.success(s);
	}
	
	protected Sentence newSentence() {
		return new DefaultSentence();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(expression.toString());
		sb.append("*");
		return sb.toString();
	}
}
