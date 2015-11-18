package org.jinghouyu.doc.rtf.protocol2.bean.header;

import org.jinghouyu.doc.rtf.protocol2.Context;

public class Header {

	private String version = "1.9.1";
	private String defCharset = "Windows-1252";
	private Integer defFont;
	private Integer defEastAsianFont;
	private Integer defASCFont;
	private Integer defANSIFont;

	private Context context = new Context();

	public void setVersion(String version) {
		this.version = version;
	}

	public void setDefCharset(String charset) {
		this.defCharset = charset;
	}

	public Context getContext() {
		return this.context;
	}

	public void setDefFont(Integer defFont) {
		this.defFont = defFont;
	}

	public void setDefEastAsianFont(Integer defEastAsianFont) {
		this.defEastAsianFont = defEastAsianFont;
	}

	public void setDefASCFont(Integer defASCFont) {
		this.defASCFont = defASCFont;
	}

	public void setDefANSIFont(Integer defANSIFont) {
		this.defANSIFont = defANSIFont;
	}

}