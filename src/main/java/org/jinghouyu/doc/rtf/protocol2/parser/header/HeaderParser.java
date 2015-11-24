package org.jinghouyu.doc.rtf.protocol2.parser.header;

import java.io.IOException;

import org.jinghouyu.doc.rtf.protocol1.parser.Entity;
import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlName;
import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol2.bean.header.Header;

public class HeaderParser {
	
	private HeaderControlDispatcher dispatcher = new HeaderControlDispatcher();
	
	public void setHeaderControlDispatcher(HeaderControlDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	public Header parse(HeaderEntityScanner it) throws IOException {
		Header header = new Header();
		while(true) {
			if(header.isHeadEnd()) return header;
			Entity entity = it.next();
			if(entity == null) return header;
			if(!(entity instanceof ControlWord)) continue;
			ControlWord word = (ControlWord) entity;
			if(word.getControlName() == null) continue;
			if(word.getControlName() == ControlName.asterisk) {
				Entity nextEntity = it.next();
				if(nextEntity == null) return header;
				if(!(nextEntity instanceof ControlWord)) it.skipThisGroup();
				if(!dispatch(header, (ControlWord) entity, it)) {
					it.skipThisGroup();
				}
			} else {
				dispatch(header, (ControlWord) entity, it);
			}
		}
	}
	
	private boolean dispatch(Header header, ControlWord word, HeaderEntityScanner it) throws IOException {
		return dispatcher.dispatch(header, word, it);
	}
}