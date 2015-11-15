package org.jinghouyu.doc.rtf.utils.syntax.expression;

import org.jinghouyu.doc.rtf.utils.syntax.Expression;
import org.jinghouyu.doc.rtf.utils.syntax.Sentence;
import org.jinghouyu.doc.rtf.utils.syntax.Sentence.DefaultSentence;
import org.jinghouyu.doc.rtf.utils.syntax.Word;
import org.jinghouyu.doc.rtf.utils.syntax.WordIterator;

public class OneOrLimitlessExpression implements Expression {

	private Expression expression;

	public OneOrLimitlessExpression(Expression exp) {
		this.expression = exp;
	}
	
	public Sentence parse(WordIterator it) {
		Sentence s = null;
		while(true) {
			Word w = expression.parse(it);
			if(w == null) break;
			if(s == null) {
				s = newSentence();
			}
			s.addWord(w);
		}
		return s;
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