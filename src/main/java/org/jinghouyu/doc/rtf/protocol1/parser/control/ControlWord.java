package org.jinghouyu.doc.rtf.protocol1.parser.control;

import java.io.IOException;

import org.jinghouyu.doc.rtf.protocol1.parser.Entity;
import org.jinghouyu.doc.rtf.protocol1.parser.stream.OneBackInputStream;

public class ControlWord implements Entity {
	
	private String parameter;
	private String name;
	private ControlName controlName;
	
	public ControlWord(ControlName name) {
		this.controlName = name;
	}
	
	public ControlWord(String name) {
		this.name = name;
	}
	
	public String getDisplayName() {
		if(controlName != null) return controlName.toString();
		return name;
	}
	
	public ControlName getControlName() {
		return controlName;
	}
	
	public int getControlType() {
		if(controlName != null) return controlName.getType();
		return ControlType.Unrecgonized;
	}
	
	public boolean isFlag() {
		return ControlType.isFlag(getControlType());
	}
	
	public boolean isSymbol() {
		return ControlType.isSymbol(getControlType());
	}
	
	public boolean isToggle() {
		return ControlType.isToggle(getControlType());
	}
	
	public boolean isUnit() {
		return ControlType.isUnit(getControlType());
	}
	
	public boolean isDestination() {
		return ControlType.isDestination(getControlType());
	}
	
	public boolean isUnrecgonized() {
		return ControlType.isUnrecgonized(getControlType());
	}
	
	public void readExtends(OneBackInputStream in) throws IOException {
		StringBuilder sb = new StringBuilder(10);
		int i = 0;
		while(true) {
			int d = in.read();
			if(d == -1) break;
			char c = (char) d;
			if(i == 0 && c == '-') {
				
			} else if(c < '0' || c > '9') {
				in.back();
				break;
			}
			sb.append(c);
		}
		if(sb.length() != 0) {
			this.parameter = sb.toString();
		}
	}
	
	public String getParameter() {
		return this.parameter;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("\\");
		if(controlName != null) {
			sb.append(controlName);
		} else {
			sb.append(name);
		}
		if(parameter != null) {
			sb.append(parameter);
		}
		return sb.toString();
	}
}