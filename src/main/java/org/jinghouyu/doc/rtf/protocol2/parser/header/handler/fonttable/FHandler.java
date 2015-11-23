package org.jinghouyu.doc.rtf.protocol2.parser.header.handler.fonttable;

import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol2.bean.header.fonttable.FontTableEntry;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderEntityScanner;
import org.jinghouyu.doc.rtf.protocol2.parser.header.handler.FontTableHandler.FontTableEntryControlHandler;

public class FHandler implements FontTableEntryControlHandler {

	public void handleControl(FontTableEntry entry, ControlWord word, HeaderEntityScanner it) {
		entry.setId(Integer.parseInt(word.getParameter()));
	}
}
