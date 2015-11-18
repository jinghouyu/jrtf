package org.jinghouyu.doc.rtf.protocol2.parser.header;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol2.bean.header.Header;

public class HeaderControlDispatcher {
	
	private Map<ControlWord, HeaderControlHandler> mapping = new HashMap<ControlWord, HeaderControlHandler>();

	boolean dispatch(Header header, ControlWord word, HeaderEntityScanner it)  throws IOException {
		HeaderControlHandler handler = mapping.get(word);
		if(handler == null) return false;
		handler.handleControl(header, word, it);
		return true;
	}
}