package org.jinghouyu.doc.rtf.protocol1.parser;

import java.io.IOException;

public interface IRtfParser {

	EntityIterator parse() throws IOException;
}