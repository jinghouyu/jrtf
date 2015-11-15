package org.jinghouyu.doc.rtf.utils.syntax.expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jinghouyu.doc.rtf.utils.syntax.Expression;
import org.jinghouyu.doc.rtf.utils.syntax.ExpressionResult;
import org.jinghouyu.doc.rtf.utils.syntax.Sentence;
import org.jinghouyu.doc.rtf.utils.syntax.Sentence.DefaultSentence;
import org.jinghouyu.doc.rtf.utils.syntax.Word;
import org.jinghouyu.doc.rtf.utils.syntax.WordIterator;

public class AndExpression implements Expression {
	
	private List<Expression> expressions = new ArrayList<Expression>();
	
	public void addExpression(Expression exp) {
		expressions.add(exp);
	}
	
	public ExpressionResult parse(WordIterator it) {
		return permute(0, it);
	}
	
	private ExpressionResult permute(int k, WordIterator it) {
		for(int i = k; i < expressions.size(); i++){
            Collections.swap(expressions, i, k);
            ExpressionResult er = permute(k+1, it);
            if(er.isSuccess()) {
            	return er;
            }
            Collections.swap(expressions, k, i);
        }
        if (k == expressions.size() -1){
        	return doParse(it);
        }
        return ExpressionResult.error();
	}
	
	private ExpressionResult doParse(WordIterator it) {
		Sentence sentence = newSentence();
		for(int i = 0; i < expressions.size(); i++) {
			ExpressionResult er = expressions.get(i).parse(it);
			if(!er.isSuccess()) {
				for(int j = sentence.wordSize(); j > 0; j--) {
					it.back();
				}
				return ExpressionResult.error();
			} else {
				Word w = er.getWord();
				if(w != null) {
					sentence.addWord(w);
				}
			}
		}
		return ExpressionResult.success(sentence);
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
}
