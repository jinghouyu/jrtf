package org.jinghouyu.doc.rtf.protocol2.parser.header.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.jinghouyu.doc.rtf.protocol1.parser.Entity;
import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlName;
import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol1.parser.otherentity.StreamEntity;
import org.jinghouyu.doc.rtf.protocol2.bean.header.Header;
import org.jinghouyu.doc.rtf.protocol2.bean.header.colortable.ColorTableEntry;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderControlHandler;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderEntityScanner;

public class ColorTableHandler implements HeaderControlHandler {
	
	private static final Set<ControlName> regNames = new HashSet<ControlName>();

	static {
		regNames.add(ControlName.red);
		regNames.add(ControlName.green);
		regNames.add(ControlName.blue);
	}
	
	public void handleControl(Header header, ControlWord word, HeaderEntityScanner it) throws IOException {
		HeaderEntityScanner scanner = it.getInnerGroupScanner();
		ColorTableEntry entry = null;
		while(true) {
			Entity entity = scanner.next();
			if(entity == null) break;
			if(entity instanceof StreamEntity) {
				String text = getText((StreamEntity) entity).trim();
				if(text.equals(";")) {
					if(entry != null) {
						header.addColorTableEntry(entry);
						entry = null;
					}
				}
				continue;
			}
			if(!(entity instanceof ControlWord)) continue;
			ControlWord cw = (ControlWord) entity;
			if(!regNames.contains(cw.getControlName())) continue;
			if(entry == null) {
				entry = new ColorTableEntry();
			}
			setEntry(entry, cw);
		}
	}
	
	private void setEntry(ColorTableEntry entry, ControlWord cw) {
		ControlName name = cw.getControlName();
		switch(name) {
		case red : entry.setRed(Integer.parseInt(cw.getParameter())); break;
		case green : entry.setGreen(Integer.parseInt(cw.getParameter())); break;
		case blue : entry.setBlue(Integer.parseInt(cw.getParameter())); break;
		default : return;
		}
	}
	
	private String getText(StreamEntity entity) throws IOException {
		InputStream in = entity.getContent();
		try {
			StringBuilder sb = new StringBuilder(1);
			while(true) {
				int d = in.read();
				if(d == -1) break;
				sb.append((char) d);
			}
			return sb.toString();
		} finally {
			in.close();
		}
	}
}