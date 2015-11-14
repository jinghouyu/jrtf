package org.jinghouyu.doc.rtf.protocol1.parser;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlName;
import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol1.parser.exp.ProtocolException;
import org.jinghouyu.doc.rtf.protocol1.parser.otherentity.GroupEndEntity;
import org.jinghouyu.doc.rtf.protocol1.parser.otherentity.GroupStartEntity;
import org.jinghouyu.doc.rtf.protocol1.parser.otherentity.StreamEntity;
import org.jinghouyu.doc.rtf.protocol1.parser.stream.OneBackInputStream;
import org.jinghouyu.doc.rtf.utils.IOUtils;

public class StandardRtfParser implements IRtfParser {
	
	private OneBackInputStream in;
	
	private LinkedList<Boolean> groupStack = new LinkedList<Boolean>(); 
	
	public StandardRtfParser(InputStream in) {
		this.in = new OneBackInputStream(in);
	}

	public EntityIterator parse() throws IOException {
		return new EntityIterator() {
			private Entity entity = null;;
			public boolean hasNext() throws IOException {
				if(entity != null) return true;
				entity = readNext();
				return entity != null;
			}
			public Entity next() throws IOException {
				Entity tmp = entity;
				entity = null;
				return tmp;
			}
		};
	}
	
	private Entity previousEntity = null;
	private Entity nextEntity = null;
	
	/**
	 * CRLF 在group control之间，在Hex之间是可被忽略的，但是在text和binary中是不可被忽略的
	 * @return
	 * @throws IOException
	 */
	private Entity readNext() throws IOException {
		if(previousEntity != null) {
			if(previousEntity instanceof StreamEntity) {
				IOUtils.exhaust(((StreamEntity) previousEntity).getContent());
			}
		}
		if(nextEntity != null) {
			previousEntity = nextEntity;
			nextEntity = null;
			return previousEntity;
		}
		while(true) {
			int d = in.read();
			if(d == -1) break;
			char c = (char) d;
			switch(c) {
			case '{' : return previousEntity = groupStart();
			case '}' : return previousEntity = groupEnd();
			case '\\' : {
				int nextd = in.read();
				if(nextd == -1) {
					throw new ProtocolException("unexpected group end lack of " + (groupStack.size() + 1) + " } at position : " + in.getReaded());
				}
				char nextc = (char) nextd;
				if(nextc == '\\' || nextc == '{' || nextc == '}') {
					in.back();
					return previousEntity = new StreamEntity(new TextInputStream('\\', this, in));  //TODO 将\\假如到流中
				} else {
					in.back();
					return previousEntity = controlWord();
				}
			}
			default : {
				in.back();
				return previousEntity = new StreamEntity(new TextInputStream(null, this, in));
			}
			}
		}
		if(groupStack.poll() != null) {
			throw new ProtocolException("unexpected group end lack of " + (groupStack.size() + 1) + " } at position : " + in.getReaded());
		}
		return null;
	}
	
	private GroupStartEntity groupStart() {
		groupStack.push(true);
		return new GroupStartEntity();
	}
	
	private GroupEndEntity groupEnd() throws IOException {
		if(groupStack.poll() == null) {
			throw new ProtocolException("unexpected group end } cannt find the start { at position : " + in.getReaded());
		}
		while(true) {
			int d = in.read();
			if(d == -1) break;
			if(d == '\r' || d == '\n' || d == '\t' || d == ' ') {
				continue;
			}
			in.back();
			break;
		}
		return new GroupEndEntity();
	}
	
	/**
	 * The <Delimiter> can be one of the following:
     1	A space. This serves only to delimit a control word and is ignored in subsequent processing.
     2	A numeric digit or an ASCII minus sign (-), which indicates that a numeric parameter is associated with the control word. The subsequent digital sequence is then delimited by any character other than an ASCII digit (commonly another control word that begins with a backslash). The parameter can be a positive or negative decimal number. The range of the values for the number is nominally –32768 through 32767, i.e., a signed 16-bit integer. A small number of control words take values in the range −2,147,483,648 to 2,147,483,647 (32-bit signed integer). These control words include \binN, \revdttmN, \rsidN related control words and some picture properties like \bliptagN. Here N stands for the numeric parameter. An RTF parser must allow for up to 10 digits optionally preceded by a minus sign. If the delimiter is a space, it is discarded, that is, it’s not included in subsequent processing.
     3  Any character other than a letter or a digit. In this case, the delimiting character terminates the control word and is not part of the control word. Such as a backslash “\”, which means a new control word or a control symbol follows.

	 */
	private static boolean[] controlWordChars = new boolean[256];
	
	private static boolean[] singleControlWordChars = new boolean[256];
	
	static {
		for(char i = 'a'; i <= 'z'; i++) {
			controlWordChars[i + 128] = true;
		}
		for(char i = 'A'; i <= 'Z'; i++) {
			controlWordChars[i + 128] = true;
		}
		singleControlWordChars['*' + 128] = true;
		singleControlWordChars['~' + 128] = true;
		singleControlWordChars['|' + 128] = true;
		singleControlWordChars['-' + 128] = true;
		singleControlWordChars['_' + 128] = true;
		singleControlWordChars[':' + 128] = true;
		singleControlWordChars['\'' + 128] = true;
	}
	
	private ControlWord controlWord() throws IOException {
		StringBuilder sb = new StringBuilder();
		boolean single = false;
		while(true) {
			int d = in.read();
			if(d == -1) {
				break;
			}
			char c = (char) d;
			if(sb.length() == 0 && singleControlWordChars[c + 128]) {
				sb.append(c);
				single = true;
				break;
			}
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
		name = name.toLowerCase();
		ControlWord control = ControlName.valueOf1(name);
		if(!single) {
			control.readExtends(in);
		}
		while(true) {
			int d = in.read();
			if(d == -1) break;
			if(d == '\r' || d == '\n') {
				continue;
			}
			in.back();
			break;
		}
		int d = in.read();
		if(d != ' ') {
			in.back();
		}
		if(control.getControlName() == ControlName.bin) {
			long length = Long.parseLong(control.getParameter());
			nextEntity = new StreamEntity(new BinInputStream(in, length));
		}
		return control;
	}
	
	private static class TextInputStream extends InputStream {
		
		private OneBackInputStream stream = null;
		
		private StandardRtfParser parser = null;
		
		private Character c = null;
		
		public TextInputStream(Character c, StandardRtfParser parser, OneBackInputStream stream) {
			this.stream = stream;
			this.c = c;
			this.parser = parser;
		}

		private int read0() throws IOException {
			if(c != null) {
				char temp = c;
				c = null;
				return temp;
			}
			return stream.read();
		}
		
		private boolean end = false;
		private boolean isPreBlash = false;
		
		@Override
		public int read() throws IOException {
			if(end) {
				return -1;
			}
			int d = read0();
			if(d == -1) {
				end = true;
				return -1;
			}
			if(d != '\\' && d != '}' && d != '{') {
				isPreBlash = false;
				return d;
			}
			if(isPreBlash) {
				return d;
			}
			if(d == '\\') {
				int nextd = read0();
				if(nextd == -1) return d;
				if(nextd == '\\' || nextd == '}' || nextd == '{') {
					isPreBlash = true;
					stream.back();
					return d;
				} else {
					stream.back();
					parser.nextEntity = parser.controlWord();
					isPreBlash = false;
					end = true;
					return -1;
				}
			} else {
				stream.back();
				isPreBlash = false;
				end = true;
				return -1;
			}
		}

		@Override
		public int read(byte[] b) throws IOException {
			return read(b, 0, b.length);
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			if(end) return -1;
			if(len <= 0) return -1;
			int i = 0;
			int lastIndex = len - 1;
			while(true) {
				int d = read();
				if(d == -1) break;
				b[off + i] = (byte) d;
				if(i < lastIndex) {
					i++;
				}
			}
			return i;
		}

		@Override
		public long skip(long n) throws IOException {
			return IOUtils.skip(this, n);
		}

		@Override
		public int available() throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void close() throws IOException {
			IOUtils.exhaust(this);
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
	
	private static class BinInputStream extends InputStream {
		
		private long readed;
		private long length;
		private OneBackInputStream in;

		public BinInputStream(OneBackInputStream in, long length) {
			this.in = in;
			this.length = length;
		}
		
		@Override
		public int read() throws IOException {
			int d = in.read();
			if(d == -1) return -1;
			readed ++;
			return d;
		}

		@Override
		public int read(byte[] b) throws IOException {
			return this.read(b);
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			len = Math.min(IOUtils.getClosest(length - readed), len);
			if(len <= 0) return -1;
			int i = 0;
			int lastIndex = len - 1;
			while(true) {
				int d = in.read();
				if(d == -1) break;
				b[off + i] = (byte) d;
				if(i < lastIndex) {
					i++;
				}
			}
			readed += i;
			return i;
		}
		
		@Override
		public long skip(long n) throws IOException {
			return IOUtils.skip(this, n);
		}

		@Override
		public int available() throws IOException {
			return IOUtils.getClosest(length - readed);
		}

		@Override
		public void close() throws IOException {
			IOUtils.exhaust(this);
		}

		@Override
		public synchronized void mark(int readlimit) {
			super.mark(readlimit);
		}

		@Override
		public synchronized void reset() throws IOException {
			super.reset();
		}

		@Override
		public boolean markSupported() {
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		StandardRtfParser parser = new StandardRtfParser(new BufferedInputStream(new FileInputStream("C:\\Users\\shinelon\\Desktop\\1.rtf")));
		EntityIterator ei = parser.parse();
		while(ei.hasNext()) {
			System.out.print(ei.next());
		}
	}
}
