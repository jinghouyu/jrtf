package org.jinghouyu.doc.rtf.protocol2.parser.header.handler;

import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol2.bean.header.Header;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderControlHandler;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderEntityScanner;

public class VersionHandler implements HeaderControlHandler {

	//rtf
	public void handleControl(Header header, ControlWord word, HeaderEntityScanner it) {
		header.setVersion("1.9.1");
	}
}