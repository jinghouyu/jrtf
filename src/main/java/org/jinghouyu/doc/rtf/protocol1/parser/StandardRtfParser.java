package org.jinghouyu.doc.rtf.protocol1.parser;

import java.io.IOException;
import java.util.LinkedList;

import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlName;
import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol1.parser.exp.ProtocolException;
import org.jinghouyu.doc.rtf.protocol1.parser.stream.OneBackInputStream;

public class StandardRtfParser implements IRtfParser {
	
	private OneBackInputStream in;
	
	private LinkedList<Boolean> groupStack = new LinkedList<Boolean>(); 

	public void parse() throws IOException {
		while(true) {
			int c = in.read();
			if(c == -1) break;
			switch(c) {
			case '{' : groupStart();break;
			case '}' : groupEnd();break;
			case '\\' : onControlWord(); break;
			}
		}
		if(groupStack.pop() != null) {
			throw new ProtocolException("unexpected group end lack of " + (groupStack.size() + 1) + " } at position : " + in.getReaded());
		}
	}
	
	private void groupStart() {
		groupStack.push(true);
	}
	
	private void groupEnd() {
		if(groupStack.pop() == null) {
			throw new ProtocolException("unexpected group end } cannt find the start { at position : " + in.getReaded());
		}
	}
	
	/**
	 * The <Delimiter> can be one of the following:
     1	A space. This serves only to delimit a control word and is ignored in subsequent processing.
     2	A numeric digit or an ASCII minus sign (-), which indicates that a numeric parameter is associated with the control word. The subsequent digital sequence is then delimited by any character other than an ASCII digit (commonly another control word that begins with a backslash). The parameter can be a positive or negative decimal number. The range of the values for the number is nominally –32768 through 32767, i.e., a signed 16-bit integer. A small number of control words take values in the range −2,147,483,648 to 2,147,483,647 (32-bit signed integer). These control words include \binN, \revdttmN, \rsidN related control words and some picture properties like \bliptagN. Here N stands for the numeric parameter. An RTF parser must allow for up to 10 digits optionally preceded by a minus sign. If the delimiter is a space, it is discarded, that is, it’s not included in subsequent processing.
     3  Any character other than a letter or a digit. In this case, the delimiting character terminates the control word and is not part of the control word. Such as a backslash “\”, which means a new control word or a control symbol follows.

	 */
	private static boolean[] controlWordChars = new boolean[256];
	
	static {
		for(char i = 'a'; i <= 'z'; i++) {
			controlWordChars[i + 128] = true;
		}
		for(char i = 'A'; i <= 'Z'; i++) {
			controlWordChars[i + 128] = true;
		}
	}
	
	private void onControlWord() throws IOException {
		StringBuilder sb = new StringBuilder();
		while(true) {
			int d = in.read();
			if(d == -1) {
				throw new ProtocolException("unexpected end at position : " + in.getReaded());
			}
			char c = (char) d;
			if(!controlWordChars[c + 128]) {
				in.back();
				break;
			}
			sb.append(c);
			if(sb.length() > 32) {
				throw new ProtocolException("control word too long at position : " + in.getReaded());
			}
		}
		String name = sb.toString();
		if(name.length() == 0) {
			throw new ProtocolException("empty control word at position : " + in.getReaded());
		}
		ControlWord control = ControlName.valueOf1(name);
		control.readExtends(in);
	}
}
