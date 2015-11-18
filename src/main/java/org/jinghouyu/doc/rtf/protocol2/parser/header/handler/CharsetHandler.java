package org.jinghouyu.doc.rtf.protocol2.parser.header.handler;

import java.io.IOException;
import java.nio.charset.Charset;

import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol2.bean.header.Header;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderControlHandler;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderEntityScanner;

public class CharsetHandler implements HeaderControlHandler {
	
	//ansi mac pc pca ansicpg
	public void handleControl(Header header, ControlWord word, HeaderEntityScanner it) throws IOException {
		switch(word.getControlName()) {
		case ansi : break;
		case mac : header.setDefCharset("macintosh");break;
		case pc : header.setDefCharset("cp437");break;
		case pca : header.setDefCharset("850");break;
		case ansicpg : {
			header.setDefCharset("cp" + word.getParameter());
			break;
		}
		default:
			break;
		}
	}
	
	public static void main(String[] arg) {
		Charset.forName("macintosh");
	}
}