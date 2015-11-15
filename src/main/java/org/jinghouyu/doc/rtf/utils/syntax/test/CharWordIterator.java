package org.jinghouyu.doc.rtf.utils.syntax.test;

import java.util.List;

import org.jinghouyu.doc.rtf.utils.syntax.ExpressionResult;
import org.jinghouyu.doc.rtf.utils.syntax.SyntaxAlynasist;
import org.jinghouyu.doc.rtf.utils.syntax.Word;
import org.jinghouyu.doc.rtf.utils.syntax.WordIterator;

public class CharWordIterator extends WordIterator {

	private String str = null;
	private int index;
	
	public CharWordIterator(String str) {
		this.str = str;
	}
	
	@Override
	protected Word next0() {
		if(index < str.length()) return new CharWord(str.charAt(index++) + "");
		return null;
	}
	
	public String toString() {
		int preIndex = getPreIndex();
		List<Word> preWords = getPreWords();
		StringBuilder sb = new StringBuilder();
		for(; preIndex < preWords.size(); preIndex ++) {
			sb.append(preWords.get(preIndex));
		}
		if(index >= str.length()) return sb.toString();
		sb.append(str.substring(index));
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String syntax = "C(A&(B?)&D)+";
		SyntaxAlynasist sa = new SyntaxAlynasist(syntax);
		System.out.println(sa.getCompiledExpression());
		CharWordIterator wi = new CharWordIterator("CDAADDA");
		ExpressionResult er = sa.parse(wi);
		System.out.println(er.isSuccess() + ":" + er.getWord());
		while(true) {
			Word w = wi.next();
			if(w == null) {
				break;
			}
			System.out.print(w.getCode());
		}
	}
}
