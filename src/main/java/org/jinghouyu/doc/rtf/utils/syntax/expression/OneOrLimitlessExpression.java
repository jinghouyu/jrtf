package org.jinghouyu.doc.rtf.utils.syntax.expression;

import org.jinghouyu.doc.rtf.utils.syntax.Expression;
import org.jinghouyu.doc.rtf.utils.syntax.ExpressionResult;
import org.jinghouyu.doc.rtf.utils.syntax.Sentence;
import org.jinghouyu.doc.rtf.utils.syntax.Sentence.DefaultSentence;
import org.jinghouyu.doc.rtf.utils.syntax.Word;
import org.jinghouyu.doc.rtf.utils.syntax.WordIterator;

public class OneOrLimitlessExpression implements Expression {

	private Expression expression;

	public OneOrLimitlessExpression(Expression exp) {
		this.expression = exp;
	}
	
	public ExpressionResult parse(WordIterator it) {
		Sentence s = null;
		while(true) {
			ExpressionResult er = expression.parse(it);
			if(!er.isSuccess()) break;
			Word word = er.getWord();
			if(s == null) {
				s = newSentence();
			}
			if(word != null) {
				s.addWord(word);
			}
		}
		if(s == null) {
			return ExpressionResult.error();
		} else {
			return ExpressionResult.success(s);
		}
	}
	
	protected Sentence newSentence() {
		return new DefaultSentence();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(expression.toString());
		sb.append("+");
		return sb.toString();
	}
}