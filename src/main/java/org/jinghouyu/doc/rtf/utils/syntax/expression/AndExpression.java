package org.jinghouyu.doc.rtf.utils.syntax.expression;

import java.util.ArrayList;
import java.util.List;

import org.jinghouyu.doc.rtf.utils.syntax.Expression;
import org.jinghouyu.doc.rtf.utils.syntax.Sentence;
import org.jinghouyu.doc.rtf.utils.syntax.Sentence.DefaultSentence;
import org.jinghouyu.doc.rtf.utils.syntax.Word;
import org.jinghouyu.doc.rtf.utils.syntax.WordIterator;

public class AndExpression implements Expression {
	
	private List<Expression> expressions = new ArrayList<Expression>();
	
	private AndExpression2 andExpression = null;
	
	public void addExpression(Expression exp) {
		expressions.add(exp);
	}
	
	public Sentence parse(WordIterator it) {
		initAndExpression();
		return andExpression.parse(it);
	}

	private void initAndExpression() {
		if(andExpression != null) return;
		AndExpression2 currentExpression = new AndExpression2();
		andExpression = currentExpression;
		for(Expression exp : expressions) {
			if(currentExpression.getA() == null) {
				currentExpression.setA(exp);
				continue;
			}
			if(currentExpression.getB() == null) {
				currentExpression.setB(exp);
				continue;
			}
			AndExpression2 tmp = new AndExpression2();
			tmp.setA(currentExpression.getB());
			tmp.setB(exp);
			currentExpression.setB(tmp);
			currentExpression = tmp;
		}
	}
	
	protected Sentence newSentence() {
		return new DefaultSentence();
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
				sb.append("&");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	private static class AndExpression2 implements Expression {
		private Expression A;
		private Expression B;
		
		public void setA(Expression a) {
			this.A = a;
		}
		
		public void setB(Expression b) {
			this.B = b;
		}
		
		public Sentence parse(WordIterator it) {
			Sentence s = newSentence();
			Word aword = A.parse(it);
			if(aword != null) {
				s.addWord(aword);
				Word bword = B.parse(it);
				if(bword != null) {
					s.addWord(bword);
					return s;
				}
				it.back();
				return null;
			}
			Word bword = B.parse(it);
			if(bword == null) return null;
			aword = A.parse(it);
			if(aword == null) {
				it.back();
				return null;
			}
			s.addWord(bword);
			s.addWord(aword);
			return s;
		}
		
		protected Sentence newSentence() {
			return new DefaultSentence();
		}
		
		public Expression getA() {
			return A;
		}

		public Expression getB() {
			return B;
		}
	}
}
