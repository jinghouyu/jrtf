package org.jinghouyu.doc.rtf.utils.syntax;

import java.util.ArrayList;
import java.util.List;

public abstract class WordIterator {

	private List<Word> preWords = new ArrayList<Word>();
	private int preIndex = 0;
	
	private Word current;
	
	private boolean hasNext() {
		if(preIndex < preWords.size()) return true;
		if(current == null) {
			current = next0();
		}
		return current != null;
	}
	
	public Word next() {
		if(!hasNext()) return null;
		if(preIndex < preWords.size()) {
			preIndex ++;
			return preWords.get(preIndex - 1);
		}
		Word tmp = current;
		current = null;
		preIndex ++;
		preWords.add(tmp);
		return tmp;
	}
	
	public void back() {
		if(preIndex <= 0) return;
		preIndex --;
	}
	
	protected abstract Word next0();
	
	protected final int getPreIndex() {
		return this.preIndex;
	}
	
	protected final List<Word> getPreWords() {
		return this.preWords;
	}
	
	public void removeCache() {
		preWords = new ArrayList<Word>();
		preIndex = 0;
	}
}
