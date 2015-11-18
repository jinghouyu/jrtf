package org.jinghouyu.doc.rtf.protocol2.parser.header.handler;

import java.io.IOException;

import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol2.bean.header.Header;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderControlHandler;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderEntityScanner;

public class DefaultFontHandler implements HeaderControlHandler {

	//deff stshfdbch stshfloch stshfhich
	public void handleControl(Header header, ControlWord word, HeaderEntityScanner it) throws IOException {
		switch(word.getControlName()) {
		case deff : header.setDefFont(Integer.parseInt(word.getParameter())); break;
		case stshfdbch : header.setDefEastAsianFont(Integer.parseInt(word.getParameter())); break;
		case stshfloch : header.setDefASCFont(Integer.parseInt(word.getParameter())); break;
		case stshfhich : header.setDefANSIFont(Integer.parseInt(word.getParameter())); break;
		default: break;
		}
	}
}
