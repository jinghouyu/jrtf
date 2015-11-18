package org.jinghouyu.doc.rtf.protocol2.parser.header.handler;

import java.io.IOException;

import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol2.Context;
import org.jinghouyu.doc.rtf.protocol2.bean.header.Header;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderControlHandler;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderEntityScanner;

public class UcHandler implements HeaderControlHandler {

	//uc
	public void handleControl(Header header, ControlWord word, HeaderEntityScanner it) throws IOException {
		Context context = header.getContext();
		context.setCurrentUC(Integer.parseInt(word.getParameter()));
	}
}
