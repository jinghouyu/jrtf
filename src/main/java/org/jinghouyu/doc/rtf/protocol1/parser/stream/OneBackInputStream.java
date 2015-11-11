package org.jinghouyu.doc.rtf.protocol1.parser.stream;

import java.io.IOException;
import java.io.InputStream;

public class OneBackInputStream {

	private InputStream in = null;
	
	public OneBackInputStream(InputStream in) {
		this.in = in;
	}
	
	private boolean hasBack = true;
	private Integer last;
	
	private long readed;
	
	public int read() throws IOException {
		int c = in.read();
		if(c == -1) return c;
		last = c;
		hasBack = false;
		readed++;
		return c;
	}
	
	public void back() {
		if(hasBack) return;
		readed--;
	}
	
	public long getReaded() {
		return this.readed;
	}
}