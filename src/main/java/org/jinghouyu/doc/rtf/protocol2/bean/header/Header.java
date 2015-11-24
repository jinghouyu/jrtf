package org.jinghouyu.doc.rtf.protocol2.bean.header;

import java.util.ArrayList;
import java.util.List;

import org.jinghouyu.doc.rtf.protocol2.Context;
import org.jinghouyu.doc.rtf.protocol2.bean.header.colortable.ColorTableEntry;
import org.jinghouyu.doc.rtf.protocol2.bean.header.fonttable.FontTableEntry;

public class Header {

	private String version = "1.9.1";
	private String defCharset = "Windows-1252";
	private Integer defFont;
	private Integer defEastAsianFont;
	private Integer defASCFont;
	private Integer defANSIFont;
	
	private List<FontTableEntry> fontTable;
	private List<ColorTableEntry> colorTable;
	
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
	
	public void addFontTableEntry(FontTableEntry entry) {
		if(fontTable == null) fontTable = new ArrayList<FontTableEntry>();
		fontTable.add(entry);
	}

	public void addColorTableEntry(ColorTableEntry entry) {
		if(colorTable == null) colorTable = new ArrayList<ColorTableEntry>();
		colorTable.add(entry);
	}

	public List<FontTableEntry> getFontTable() {
		return fontTable;
	}

	public void setFontTable(List<FontTableEntry> fontTable) {
		this.fontTable = fontTable;
	}

	public List<ColorTableEntry> getColorTable() {
		return colorTable;
	}

	public void setColorTable(List<ColorTableEntry> colorTable) {
		this.colorTable = colorTable;
	}

	public String getVersion() {
		return version;
	}

	public String getDefCharset() {
		return defCharset;
	}

	public Integer getDefFont() {
		return defFont;
	}

	public Integer getDefEastAsianFont() {
		return defEastAsianFont;
	}

	public Integer getDefASCFont() {
		return defASCFont;
	}

	public Integer getDefANSIFont() {
		return defANSIFont;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	private boolean headEnd = false;
	
	public void setHeadEnd(boolean headEnd) {
		this.headEnd = headEnd;
	}
	
	public boolean isHeadEnd() {
		return this.headEnd;
	}
}