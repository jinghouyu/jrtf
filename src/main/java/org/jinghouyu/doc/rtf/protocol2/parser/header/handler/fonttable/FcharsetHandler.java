package org.jinghouyu.doc.rtf.protocol2.parser.header.handler.fonttable;

import java.util.HashMap;
import java.util.Map;

import org.jinghouyu.doc.rtf.protocol1.parser.control.ControlWord;
import org.jinghouyu.doc.rtf.protocol2.bean.header.fonttable.FontTableEntry;
import org.jinghouyu.doc.rtf.protocol2.parser.header.HeaderEntityScanner;
import org.jinghouyu.doc.rtf.protocol2.parser.header.handler.FontTableHandler.FontTableEntryControlHandler;

public class FcharsetHandler implements FontTableEntryControlHandler {

	private static final Map<String, String> cpMap = new HashMap<String, String>();

	static {
		cpMap.put("0", "1252");
		cpMap.put("1", "0");
		cpMap.put("2", "42");
		cpMap.put("77", "10000");
		cpMap.put("78", "10001");
		cpMap.put("79", "10003");
		cpMap.put("80", "10008");
		cpMap.put("81", "10002");
		cpMap.put("83", "10005");
		cpMap.put("84", "10004");
		cpMap.put("85", "10006");
		cpMap.put("86", "10081");
		cpMap.put("87", "10021");
		cpMap.put("88", "10029");
		cpMap.put("89", "10007");
		cpMap.put("128", "932");
		cpMap.put("129", "949");
		cpMap.put("130", "1361");
		cpMap.put("134", "936");
		cpMap.put("136", "950");
		cpMap.put("161", "1253");
		cpMap.put("162", "1254");
		cpMap.put("163", "1258");
		cpMap.put("177", "1255");
		cpMap.put("178", "1256");
		cpMap.put("186", "1257");
		cpMap.put("204", "1251");
		cpMap.put("222", "874");
		cpMap.put("238", "1250");
		cpMap.put("254", "437");
		cpMap.put("255", "850");
	}

	public void handleControl(FontTableEntry entry, ControlWord word, HeaderEntityScanner it) {
		String code = word.getParameter();
		String codePage = cpMap.get(code);
		if(codePage != null) {
			entry.setCharset("CP" + codePage);
		}
	}
}