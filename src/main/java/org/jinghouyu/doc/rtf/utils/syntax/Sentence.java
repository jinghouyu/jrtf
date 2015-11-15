package org.jinghouyu.doc.rtf.utils.syntax;

import java.util.ArrayList;
import java.util.List;

public abstract class Sentence implements Word {

	private List<Word> words = new ArrayList<Word>();
	private int wordSize;
	
	public void addWord(Word word) {
		if(word instanceof Sentence) {
			wordSize += ((Sentence) word).wordSize;
		} else {
			wordSize++;
		}
		words.add(word);
	}
	
	List<Word> getWords() {
		return this.words;
	}
	
	public static class DefaultSentence extends Sentence {

		public String getCode() {
			return "Default";
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(Word w : getWords()) {
				sb.append(w.toString());
			}
			return sb.toString();
		}
	}
	
	public int wordSize() {
		return this.wordSize;
	}
}