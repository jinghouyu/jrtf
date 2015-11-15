package org.jinghouyu.doc.rtf.utils.syntax;

import java.util.LinkedList;

import org.jinghouyu.doc.rtf.utils.syntax.expression.AndExpression;
import org.jinghouyu.doc.rtf.utils.syntax.expression.AtomicWordExpression;
import org.jinghouyu.doc.rtf.utils.syntax.expression.NonOrLimitlessExpression;
import org.jinghouyu.doc.rtf.utils.syntax.expression.NonOrOneExpression;
import org.jinghouyu.doc.rtf.utils.syntax.expression.OneOrLimitlessExpression;
import org.jinghouyu.doc.rtf.utils.syntax.expression.OrExpression;
import org.jinghouyu.doc.rtf.utils.syntax.expression.OrderExpression;

public class SyntaxAlynasist {

	private OrderExpression expression;

	private LinkedList<Boolean> stack = new LinkedList<Boolean>();

	public SyntaxAlynasist(String syntax) {
		expression = compile(new SynaxScanner(syntax), false);
	}

	private OrderExpression compile(SynaxScanner scanner, boolean child) {
		OrderExpression ex = new OrderExpression();
		while (scanner.hasNext()) {
			char c = scanner.next();
			switch (c) {
			case ')': {
				if (stack.poll() == null) {
					throw new SyntaxException("lack of ) at " + scanner.pos());
				}
				if(child) {
					return ex;
				}
				break;
			}
			case '(':
				stack.push(true);
				ex.addExpression(compile(scanner, true));
				break;
			case '?': {
				Expression last = ex.getLastExpression();
				if (last == null) {
					throw new SyntaxException("no expression before ? " + scanner.pos());
				}
				ex.removeLast();
				Expression exp = new NonOrOneExpression(last);
				ex.addExpression(exp);
				break;
			}
			case '+': {
				Expression last = ex.getLastExpression();
				if (last == null) {
					throw new SyntaxException("no expression before + " + scanner.pos());
				}
				ex.removeLast();
				Expression exp = new OneOrLimitlessExpression(last);
				ex.addExpression(exp);
				break;
			}
			case '*': {
				Expression last = ex.getLastExpression();
				if (last == null) {
					throw new SyntaxException("no expression before * " + scanner.pos());
				}
				ex.removeLast();
				Expression exp = new NonOrLimitlessExpression(last);
				ex.addExpression(exp);
				break;
			}
			case '&': {
				Expression last = ex.getLastExpression();
				if (last == null) {
					throw new SyntaxException("no expression before & " + scanner.pos());
				}
				ex.removeLast();
				ex.addExpression(andExpression(last, scanner));
				break;
			}
			case '|': {
				Expression last = ex.getLastExpression();
				if (last == null) {
					throw new SyntaxException("no expression before | " + scanner.pos());
				}
				ex.removeLast();
				ex.addExpression(orExpression(last, scanner));
				break;
			}
			default: {
				ex.addExpression(new AtomicWordExpression(c + ""));
			}
			}
		}
		return ex;
	}
	
	private Expression andExpression(Expression last, SynaxScanner scanner) {
		AndExpression ex = new AndExpression();
		ex.addExpression(last);
		boolean expressioned = false;
		while(scanner.hasNext()) {
			char c = scanner.next();
			if(expressioned) {
				if(c != '&') {
					scanner.back();
					break;
				}
				expressioned = false;
				continue;
			} else {
				if(c == '(') {
					stack.push(true);
					ex.addExpression(compile(scanner, true));
					expressioned = true;
					continue;
				}
				if(c == '|' && c == '&' || c == ')' || c == '?' || c == '*' || c == '+') {
					throw new SyntaxException("no expression after & " + scanner.pos());
				}
				ex.addExpression(new AtomicWordExpression(c + ""));
				expressioned = true;
			}
		}
		if(ex.expressionSize() == 1) {
			throw new SyntaxException("no expression after & " + scanner.pos());
		}
		return ex;
	}
	
	private Expression orExpression(Expression last, SynaxScanner scanner) {
		OrExpression ex = new OrExpression();
		ex.addExpression(last);
		boolean expressioned = false;
		while(scanner.hasNext()) {
			char c = scanner.next();
			if(expressioned) {
				if(c != '|') {
					scanner.back();
					break;
				}
				expressioned = false;
				continue;
			} else {
				if(c == '(') {
					stack.push(true);
					ex.addExpression(compile(scanner, true));
					expressioned = true;
					continue;
				}
				if(c == '|' && c == '&' || c == ')' || c == '?' || c == '*' || c == '+') {
					throw new SyntaxException("no expression after & " + scanner.pos());
				}
				ex.addExpression(new AtomicWordExpression(c + ""));
				expressioned = true;
			}
		}
		if(ex.expressionSize() == 1) {
			throw new SyntaxException("no expression after & " + scanner.pos());
		}
		return ex;
	}
	
	public ExpressionResult parse(WordIterator it) {
		return expression.parse(it);
	}
	
	public Expression getCompiledExpression() {
		return this.expression;
	}
	
	public static void main(String[] args) {
		SyntaxAlynasist as = new SyntaxAlynasist("A|(C&D)?");
		System.out.println(as.expression);
	}
}
