package org.jinghouyu.doc.rtf.protocol2.parser.header.handler;

import java.io.IOException;

import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol2.bean.header.Header;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderControlHandler;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderEntityScanner;

public class FontTableHandler implements HeaderControlHandler {

	//fonttbl
	public void handleControl(Header header, ControlWord word, HeaderEntityScanner it) throws IOException {
		HeaderEntityScanner scanner = it.getInnerGroupScanner();
	}
}