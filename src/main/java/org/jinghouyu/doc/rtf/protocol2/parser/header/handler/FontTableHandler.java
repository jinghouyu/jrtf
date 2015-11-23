package org.jinghouyu.doc.rtf.protocol2.parser.header.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.jinghouyu.doc.rtf.protocol1.parser.Entity;
import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlName;
import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol1.parser.otherentity.GroupStartEntity;
import org.jinghouyu.doc.rtf.protocol1.parser.otherentity.StreamEntity;
import org.jinghouyu.doc.rtf.protocol2.bean.header.Header;
import org.jinghouyu.doc.rtf.protocol2.bean.header.fonttable.FontTableEntry;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderControlHandler;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderEntityScanner;

public class FontTableHandler implements HeaderControlHandler {
	
	private static Map<ControlName, FontTableEntryControlHandler> handlers = new HashMap<ControlName, FontTableEntryControlHandler>();
	
	//fonttbl
	public void handleControl(Header header, ControlWord word, HeaderEntityScanner it) throws IOException {
		HeaderEntityScanner scanner = it.getInnerGroupScanner();
		Entity next = scanner.next();
		if(!(next instanceof GroupStartEntity)) {
			parse(header, it.getPlusScanner(new Entity[]{next}));
		} else {
			parse(header, it.getInnerGroupScanner());
		}
	}
	
	private void parse(Header header, HeaderEntityScanner it) throws IOException {
		Entity lastEntity = null;
		FontTableEntry entry = new FontTableEntry();
		while(true) {
			Entity entity = it.next();
			if(entity == null) {
				if(!(lastEntity instanceof StreamEntity)) {
					return;
				}
				parseFontName(entry, (StreamEntity) lastEntity);
			}
			lastEntity = entity;
			if(!(entity instanceof ControlWord)) continue;
			ControlWord word = (ControlWord) entity;
			ControlName controlName = word.getControlName();
			if(controlName == null) continue;
			FontTableEntryControlHandler handler = handlers.get(controlName);
			if(handler == null) continue;
			handler.handleControl(entry, word, it);
			header.addFontTableEntry(entry);
		}
	}
	
	private void parseFontName(FontTableEntry font, StreamEntity entity) throws IOException {
		InputStream in = entity.getContent();
		StringBuilder sb = new StringBuilder();
		while(true) {
			int d = in.read();
			if(d == -1) break;
			sb.append((char) d);
		}
		
		String fontName = sb.toString();
		fontName = fontName.trim();
		if(fontName.length() != 0) {
			font.setFontName(fontName);
		}
	}
	
	public static interface FontTableEntryControlHandler {
		public void handleControl(FontTableEntry entry, ControlWord word, HeaderEntityScanner it);
	}
}