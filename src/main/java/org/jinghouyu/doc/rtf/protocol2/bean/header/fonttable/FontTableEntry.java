package org.jinghouyu.doc.rtf.protocol2.bean.header.fonttable;

public class FontTableEntry {

	private int id;
	
	private String fontName;
	private boolean pitch = false;  //是否每个字符等宽
	private String charset = "windows-1252";
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public boolean isPitch() {
		return pitch;
	}

	public void setPitch(boolean pitch) {
		this.pitch = pitch;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}
