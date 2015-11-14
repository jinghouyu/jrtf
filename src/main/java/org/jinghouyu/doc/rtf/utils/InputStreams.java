package org.jinghouyu.doc.rtf.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InputStreams extends InputStream {

	private List<InputStream> ins = new ArrayList<InputStream>();
	private int index = 0;
	
	private boolean closed;
	
	private void assertClose() throws IOException {
		if(closed) {
			throw new IOException("inputstream has closed");
		}
	}
	
	public InputStreams addInputStream(InputStream in) {
		ins.add(in);
		return this;
	}
	
	@Override
	public int read() throws IOException {
		byte[] b = new byte[1];
		int i = read(b);
		if(i == -1) return -1;
		if(i == 0) return read();
		return b[0];
	}

	@Override
	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		assertClose();
		if(ins.size() == 0) return -1;
		if(index >= ins.size()) return -1;
		for(; index < ins.size(); index++) {
			InputStream in = ins.get(index);
			int readLength = in.read(b, off, len);
			if(readLength != -1) return readLength;
		}
		return -1;
	}

	@Override
	public long skip(long n) throws IOException {
		assertClose();
		long c = 0;
		while(true) {
			int i = read();
			if(i == -1) break;
			c++;
		}
		return c;
	}

	@Override
	public int available() throws IOException {
		int available = 0;
		for(int i = index; i < ins.size(); i++) {
			available += ins.get(i).available();
		}
		return available;
	}

	@Override
	public void close() throws IOException {
		closed = true;
		IOException e = null;
		RuntimeException re = null;
		for(int i = 0; i < ins.size(); i++) {
			InputStream in = ins.get(i);
			try {
				in.close();
			} catch(IOException e1) {
				e = e1;
			} catch(RuntimeException e1) {
				re = e1;
			}
		}
		if(e != null) throw e;
		if(re != null) throw re;
	}

	@Override
	public synchronized void mark(int readlimit) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized void reset() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean markSupported() {
		return false;
	}
}