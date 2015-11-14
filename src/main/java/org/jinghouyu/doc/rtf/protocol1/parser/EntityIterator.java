package org.jinghouyu.doc.rtf.protocol1.parser;

import java.io.IOException;

public interface EntityIterator {
	
	public boolean hasNext() throws IOException;
	
	public Entity next() throws IOException;
}