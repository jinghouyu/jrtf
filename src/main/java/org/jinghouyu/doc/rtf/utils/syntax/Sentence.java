package org.jinghouyu.doc.rtf.utils.syntax;

import java.util.ArrayList;
import java.util.List;

public abstract class Sentence implements Word {

	private List<Word> words = new ArrayList<Word>();
	
	public void addWord(Word word) {
		words.add(word);
	}
	
	public static class DefaultSentence extends Sentence {

		public String getCode() {
			return "Default";
		}
	}
}