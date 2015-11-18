package org.jinghouyu.doc.rtf.protocol2;

import java.util.LinkedList;

public class Context {
	
	private LinkedList<UC> ucs = new LinkedList<UC>();
	private int currentDepth = 0;
	
	public void addDepth() {
		currentDepth++;
	}
	
	public void minusDepth() {
		UC uc = ucs.peek();
		if(uc != null) {
			if(uc.getDepth() == currentDepth) {
				ucs.pop();
			}
		}
		currentDepth--;
	}
	
	public void setCurrentUC(int u) {
		UC uc = ucs.peek();
		if(uc == null) {
			ucs.push(new UC(currentDepth, u));
		} else {
			if(uc.getDepth() == currentDepth) {
				uc.setUc(u);
			} else {
				ucs.push(new UC(currentDepth, u));
			}
		}
	}
	
	public Integer getCurrentUc() {
		UC uc = ucs.peek();
		if(uc == null) return null;
		return uc.getUc();
	}
}