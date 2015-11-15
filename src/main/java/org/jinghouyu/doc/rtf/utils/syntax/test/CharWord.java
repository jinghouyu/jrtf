package org.jinghouyu.doc.rtf.utils.syntax.test;

import org.jinghouyu.doc.rtf.utils.syntax.Word;

public class CharWord implements Word {

	private String code = null;
	
	public CharWord(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public String toString() {
		return getCode();
	}
}
