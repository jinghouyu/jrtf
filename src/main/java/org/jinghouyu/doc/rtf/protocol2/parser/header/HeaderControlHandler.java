package org.jinghouyu.doc.rtf.protocol2.parser.header;

import java.io.IOException;

import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol2.bean.header.Header;

public interface HeaderControlHandler {

	public void handleControl(Header header, ControlWord word, HeaderEntityScanner it) throws IOException;
}