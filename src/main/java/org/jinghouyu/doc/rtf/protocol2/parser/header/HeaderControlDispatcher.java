package org.jinghouyu.doc.rtf.protocol2.parser.header;

import java.io.IOException;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.Map;

import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlName;
import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol2.bean.header.Header;
import org.jinghouyu.doc.rtf.protocol2.parser.header.handler.CharsetHandler;
import org.jinghouyu.doc.rtf.protocol2.parser.header.handler.ColorTableHandler;
import org.jinghouyu.doc.rtf.protocol2.parser.header.handler.DefaultFontHandler;
import org.jinghouyu.doc.rtf.protocol2.parser.header.handler.FontTableHandler;
import org.jinghouyu.doc.rtf.protocol2.parser.header.handler.UcHandler;
import org.jinghouyu.doc.rtf.protocol2.parser.header.handler.VersionHandler;

public class HeaderControlDispatcher {
	
	private Map<ControlName, HeaderControlHandler> mapping = new HashMap<ControlName, HeaderControlHandler>();

	{
		mapping.put(ControlName.rtf, new VersionHandler());
		
		mapping.put(ControlName.ansi, new CharsetHandler());
		mapping.put(ControlName.mac, new CharsetHandler());
		mapping.put(ControlName.pc, new CharsetHandler());
		mapping.put(ControlName.pca, new CharsetHandler());
		mapping.put(ControlName.ansicpg, new CharsetHandler());
		
		mapping.put(ControlName.deff, new DefaultFontHandler());
		mapping.put(ControlName.stshfdbch, new DefaultFontHandler());
		mapping.put(ControlName.stshfloch, new DefaultFontHandler());
		mapping.put(ControlName.stshfhich, new DefaultFontHandler());
		
		mapping.put(ControlName.uc, new UcHandler());
		
		mapping.put(ControlName.colortbl, new ColorTableHandler());
		
		mapping.put(ControlName.fonttbl, new FontTableHandler());
	}
	
	boolean dispatch(Header header, ControlWord word, HeaderEntityScanner it)  throws IOException {
		HeaderControlHandler handler = mapping.get(word.getControlName());
		if(handler == null) return false;
		handler.handleControl(header, word, it);
		return true;
	}
}