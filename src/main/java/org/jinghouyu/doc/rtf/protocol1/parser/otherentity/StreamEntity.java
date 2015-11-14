package org.jinghouyu.doc.rtf.protocol1.parser.otherentity;

import java.io.IOException;
import java.io.InputStream;

import org.jinghouyu.doc.rtf.protocol1.parser.Entity;

public class StreamEntity implements Entity {

	private InputStream stream;
	
	public StreamEntity(InputStream stream) {
		this.stream = stream;
	}
	
	public InputStream getContent() {
		return this.stream;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			while(true) {
				int d = stream.read();
				if(d == -1) return sb.toString();
				sb.append((char) d);
			}
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
