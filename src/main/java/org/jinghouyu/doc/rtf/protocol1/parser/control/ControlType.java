package org.jinghouyu.doc.rtf.protocol1.parser.control;

public class ControlType {
	
	public static final int Symbol = 1;
	public static final int Unit = 2;
	public static final int Flag = 4;
	public static final int Destination = 8;
	public static final int Toggle = 16;
	public static final int Unrecgonized = 32;
	
	public static boolean isSymbol(int type) {
		return (Symbol | type) == Symbol;
	}
	
	public static boolean isUnit(int type) {
		return (Unit | type) == Unit;
	}
	
	public static boolean isFlag(int type) {
		return (Flag | type) == Flag;
	}
	
	public static boolean isDestination(int type) {
		return (Destination | type) == Destination;
	}
	
	public static boolean isToggle(int type) {
		return (Toggle | type) == Toggle;
	}
	
	public static boolean isUnrecgonized(int type) {
		return (Unrecgonized | type) == Unrecgonized;
	}
}