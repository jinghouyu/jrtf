package org.jinghouyu.doc.rtf.protocol1.parser.stream;

import java.io.IOException;
import java.io.InputStream;

public class OneBackInputStream {

	private InputStream in = null;
	
	public OneBackInputStream(InputStream in) {
		this.in = in;
	}
	
	private long readed;
	
	private Integer last;
	private boolean hasBackCalled = false;
	
	public int read() throws IOException {
		if(hasBackCalled) {
			hasBackCalled = false;
			if(last != null) {
				int temp = last;
				return temp;
			}
		}
		int d = in.read();
		if(d == -1) return -1;
		last = d;
		return d;
	}
	
	public void back() {
		hasBackCalled = true;
	}
	
	public long getReaded() {
		return this.readed;
	}
}