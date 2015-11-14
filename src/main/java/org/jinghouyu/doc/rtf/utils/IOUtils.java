package org.jinghouyu.doc.rtf.utils;

import java.io.IOException;
import java.io.InputStream;

public class IOUtils {
	
	public static long skip(InputStream in, long count) throws IOException {
		byte[] b = new byte[4069];
		long readed = 0;
		while(true) {
			int len = Math.min(b.length, getClosest(count - readed));
			if(len <= 0) break;
			int readLen = in.read(b, 0, len);
			if(readLen == -1) return readed;
			readed += readLen;
		}
		return readed;
	}
	
	public static int getClosest(long c) {
		if(c > Integer.MAX_VALUE) return Integer.MAX_VALUE;
		return (int) c;
	}
	
	public static long exhaust(InputStream in) throws IOException {
		byte[] b = new byte[4069];
		long readed = 0;
		while(true) {
			int len = in.read(b);
			if(len == -1) return readed;
			readed += len;
		}
	}
}