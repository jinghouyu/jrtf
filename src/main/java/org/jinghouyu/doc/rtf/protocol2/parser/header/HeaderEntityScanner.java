package org.jinghouyu.doc.rtf.protocol2.parser.header;

import java.io.IOException;

import org.jinghouyu.doc.rtf.protocol1.parser.Entity;
import org.jinghouyu.doc.rtf.protocol1.parser.EntityIterator;
import org.jinghouyu.doc.rtf.protocol1.parser.otherentity.GroupEndEntity;
import org.jinghouyu.doc.rtf.protocol1.parser.otherentity.GroupStartEntity;

public class HeaderEntityScanner {
	
	private EntityIterator it;
	
	public Entity next() throws IOException {
		return it.next();
	}
	
	public void skipThisGroup() throws IOException {
		int count = 0;
		while(true) {
			Entity entity = next();
			if(entity == null) return;
			if(count == 0 && entity instanceof GroupEndEntity) {
				return;
			}
			if(entity instanceof GroupStartEntity) {
				count++;
			} else if(entity instanceof GroupEndEntity) {
				count--;
			}
		}
	}
	
	public HeaderEntityScanner getInnerGroupScanner() {
		final HeaderEntityScanner othis = this;
		return new HeaderEntityScanner() {
			int count = 0;
			public Entity next() throws IOException {
				Entity entity = othis.next();
				if(entity == null) return null;
				if(count == 0 && entity instanceof GroupEndEntity) {
					return null;
				}
				if(entity instanceof GroupStartEntity) {
					count++;
				} else if(entity instanceof GroupEndEntity) {
					count--;
				}
				return entity;
			}
		};
	}
	
	public HeaderEntityScanner getPlusScanner(final Entity... entities) {
		if(entities == null || entities.length == 0) return this;
		final HeaderEntityScanner othis = this;
		return new HeaderEntityScanner() {
			private int index = 0;
			public Entity next() throws IOException {
				if(index < entities.length) {
					return entities[index++];
				}
				Entity entity = othis.next();
				return entity;
			}
		};
	}
}