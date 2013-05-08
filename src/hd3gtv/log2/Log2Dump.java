/*
 * This file is part of Log2.
 * 
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * Copyright (C) hdsdi3g for hd3g.tv 2008
 * 
*/

package hd3gtv.log2;

import java.io.File;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Log2Dump implements Serializable, Log2DumpMBean {
	
	private static final long serialVersionUID = -959819410567578438L;
	ArrayList<Log2DumpElement> elements;
	
	class Log2DumpElement implements Serializable {
		
		private static final long serialVersionUID = -8057438352855836993L;
		String name = "(null)";
		String value = "(null)";
		String meta = null;
	}
	
	private static String strnull(String value) {
		if (value == null) {
			return "(null)";
		}
		if (value.equals("")) {
			return "(empty)";
		}
		return value;
	}
	
	public Log2Dump() {
		elements = new ArrayList<Log2DumpElement>();
	}
	
	public Log2Dump(String name, String value) {
		this();
		add(name, value);
	}
	
	public Log2Dump(String name, File value) {
		this();
		add(name, value);
	}
	
	public Log2Dump(String name, Number value) {
		this();
		add(name, value);
	}
	
	public synchronized void addAll(Log2Dump dump) {
		if (dump != null) {
			elements.addAll(dump.elements);
		}
	}
	
	public synchronized void addAll(Log2Dumpable dump) {
		if (dump != null) {
			elements.addAll(dump.getLog2Dump().elements);
		}
	}
	
	private static void addSpaces(int count, StringBuffer sb) {
		for (int i = 0; i < count; i++) {
			sb.append(" ");
		}
	}
	
	public void dumptoString(StringBuffer sb, String newline) {
		if (elements.size() > 0) {
			sb.append(newline);
			Log2DumpElement dumpelement;
			int strlen = -1;
			for (int pos = 0; pos < elements.size(); pos++) {
				dumpelement = elements.get(pos);
				if (dumpelement.name == null) {
					dumpelement.name = strnull(null);
				}
				if (dumpelement.name.length() > strlen) {
					strlen = dumpelement.name.length();
				}
			}
			for (int pos = 0; pos < elements.size(); pos++) {
				dumpelement = elements.get(pos);
				sb.append(dumpelement.name);
				addSpaces(strlen - dumpelement.name.length(), sb);
				sb.append(" ");
				sb.append(dumpelement.value);
				if (dumpelement.meta != null) {
					sb.append("\t");
					sb.append(dumpelement.meta);
				}
				sb.append(newline);
			}
		}
	}
	
	public ArrayList<String> dumptoString() {
		ArrayList<String> result = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		if (elements.size() > 0) {
			Log2DumpElement dumpelement;
			int strlen = -1;
			for (int pos = 0; pos < elements.size(); pos++) {
				dumpelement = elements.get(pos);
				if (dumpelement.name == null) {
					dumpelement.name = strnull(null);
				}
				if (dumpelement.name.length() > strlen) {
					strlen = dumpelement.name.length();
				}
			}
			for (int pos = 0; pos < elements.size(); pos++) {
				dumpelement = elements.get(pos);
				sb.append(dumpelement.name);
				addSpaces(strlen - dumpelement.name.length(), sb);
				sb.append(" ");
				sb.append(dumpelement.value);
				if (dumpelement.meta != null) {
					sb.append("   [");
					sb.append(dumpelement.meta);
					sb.append("]");
				}
				sb.append(Log2Event.LINESEPARATOR);
				result.add(sb.toString());
				sb = new StringBuffer();
			}
		}
		return result;
	}
	
	public Element toXMLElement(Document document) {
		if (document == null) {
			return null;
		}
		if (elements.size() <= 0) {
			return null;
		}
		Element result = document.createElement("dump");
		Log2DumpElement dumpelement;
		for (int pos = 0; pos < elements.size(); pos++) {
			dumpelement = elements.get(pos);
			Element var = document.createElement("var");
			if (dumpelement.name == null) {
				dumpelement.name = strnull(null);
			}
			if (dumpelement.value == null) {
				dumpelement.value = strnull(null);
			}
			var.setAttribute("name", dumpelement.name);
			var.setAttribute("value", dumpelement.value);
			
			if (dumpelement.meta != null) {
				var.setAttribute("meta", dumpelement.meta);
			}
			result.appendChild(var);
		}
		return result;
	}
	
	public synchronized void add(String name, String value) {
		Log2DumpElement element = new Log2DumpElement();
		element.name = strnull(name);
		element.value = strnull(value);
		elements.add(element);
	}
	
	public synchronized void add(String name, InetAddress value) {
		Log2DumpElement element = new Log2DumpElement();
		element.name = strnull(name);
		if (value == null) {
			element.value = strnull(null);
		} else {
			element.value = value.getHostAddress();
		}
		elements.add(element);
	}
	
	public synchronized void add(String name, StringBuffer value) {
		Log2DumpElement element = new Log2DumpElement();
		element.name = strnull(name);
		if (value != null) {
			element.value = value.toString();
		} else {
			element.value = strnull(null);
		}
		elements.add(element);
	}
	
	public synchronized void add(String name, Number value) {
		Log2DumpElement element = new Log2DumpElement();
		element.name = strnull(name);
		element.value = String.valueOf(value);
		elements.add(element);
	}
	
	public synchronized void addDate(String name, long value) {
		Log2DumpElement element = new Log2DumpElement();
		element.name = strnull(name);
		element.value = Log2Event.dateLog(value);
		elements.add(element);
	}
	
	public synchronized void add(String name, Calendar value) {
		Log2DumpElement element = new Log2DumpElement();
		element.name = strnull(name);
		if (value == null) {
			element.value = strnull(name);
		} else {
			element.value = Log2Event.dateLog(value.getTimeInMillis());
		}
		elements.add(element);
	}
	
	public synchronized void add(String name, boolean value) {
		Log2DumpElement element = new Log2DumpElement();
		element.name = strnull(name);
		element.value = String.valueOf(value);
		elements.add(element);
	}
	
	public synchronized void add(String name, File file) {
		Log2DumpElement element = new Log2DumpElement();
		element.name = strnull(name);
		if (file == null) {
			element.value = strnull(null);
		}
		element.value = file.getPath();
		StringBuffer sb = new StringBuffer();
		if (file.exists()) {
			if (file.isHidden()) {
				sb.append("h");
			}
			if (file.isDirectory()) {
				sb.append("D");
			} else {
				sb.append("F");
			}
			if (file.canRead()) {
				sb.append("R");
			} else {
				sb.append("!");
			}
			if (file.canWrite()) {
				sb.append("W");
			} else {
				sb.append("o");
			}
			if (file.isDirectory() == false) {
				sb.append(" ");
				sb.append(file.length());
			}
			sb.append(" (");
			sb.append(Log2Event.dateLog(file.lastModified()).toString());
			sb.append(")");
		} else {
			sb.append("(not exists)");
		}
		element.meta = sb.toString();
		elements.add(element);
	}
	
	/**
	 * value.getNodeName();
	 */
	public synchronized void addNodeName(String name, Node value) {
		Log2DumpElement element = new Log2DumpElement();
		element.name = strnull(name);
		if (value != null) {
			element.value = strnull(value.getNodeName());
		} else {
			element.value = strnull(null);
		}
		elements.add(element);
	}
	
	/**
	 * value.getTextContent();
	 */
	public synchronized void addNodeTextContent(String name, Node value) {
		Log2DumpElement element = new Log2DumpElement();
		element.name = strnull(name);
		if (value != null) {
			element.value = strnull(value.getTextContent());
		} else {
			element.value = strnull(null);
		}
		elements.add(element);
	}
	
	public synchronized void addAll(String prefix_name, Map<String, String> values) {
		if (values == null) {
			Log2DumpElement element = new Log2DumpElement();
			element.name = strnull(prefix_name);
			element.value = strnull(null);
			elements.add(element);
			return;
		}
		if (values.size() == 0) {
			Log2DumpElement element = new Log2DumpElement();
			element.name = strnull(prefix_name);
			element.value = String.valueOf(0);
			elements.add(element);
			return;
		}
		for (Map.Entry<String, String> entry : values.entrySet()) {
			Log2DumpElement element = new Log2DumpElement();
			element.name = prefix_name + "[" + entry.getKey() + "]";
			element.value = entry.getValue();
			elements.add(element);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public synchronized void add(String name, ArrayList values) {
		if (values == null) {
			Log2DumpElement element = new Log2DumpElement();
			element.name = strnull(name);
			element.value = strnull(null);
			elements.add(element);
			return;
		}
		if (values.size() == 0) {
			Log2DumpElement element = new Log2DumpElement();
			element.name = strnull(name);
			element.value = String.valueOf(0);
			elements.add(element);
			return;
		}
		StringBuffer newname;
		for (int i = 0; i < values.size(); i++) {
			newname = new StringBuffer();
			newname.append(name);
			newname.append(" [");
			newname.append(i + 1);
			newname.append("/");
			newname.append(values.size());
			newname.append("]");
			
			Object value = values.get(i);
			if (value instanceof String) {
				add(newname.toString(), (String) value);
				continue;
			}
			if (value instanceof Number) {
				add(newname.toString(), (Number) value);
				continue;
			}
			if (value instanceof Calendar) {
				add(newname.toString(), (Calendar) value);
				continue;
			}
			if (value instanceof File) {
				add(newname.toString(), (File) value);
				continue;
			}
			if (value instanceof Boolean) {
				add(newname.toString(), value);
				continue;
			}
			if (value instanceof Throwable) {
				add(newname.toString(), (Throwable) value);
				continue;
			}
			if (value instanceof StackTraceElement) {
				add(newname.toString(), (StackTraceElement) value);
				continue;
			}
			if (value instanceof InetAddress) {
				add(newname.toString(), (InetAddress) value);
				continue;
			}
			if (value instanceof Log2Dumpable) {
				add(newname.toString(), "");
				addAll((Log2Dumpable) value);
				continue;
			}
			if (value instanceof Log2Dump) {
				add(newname.toString(), "");
				addAll((Log2Dump) value);
				continue;
			}
			add(newname.toString(), value);
		}
	}
	
	public synchronized void add(String name, Object value) {
		Log2DumpElement element = new Log2DumpElement();
		element.name = strnull(name);
		if (value != null) {
			if (value instanceof String) {
				element.value = (String) value;
				element.meta = "";
			} else if (value instanceof Number) {
				element.value = String.valueOf(value);
				element.meta = value.getClass().getName();
			} else if (value instanceof Calendar) {
				element.value = String.valueOf(((Calendar) value).getTimeInMillis());
				element.meta = "";
			} else if (value instanceof Log2Dump) {
				add(name + "  :\\", "");
				addAll(((Log2Dump) value));
			} else if (value instanceof Log2Dumpable) {
				add(name + "  :\\", "");
				addAll(((Log2Dumpable) value));
			} else {
				element.value = value.toString();
				element.meta = value.getClass().getName();
			}
		} else {
			element.value = strnull(null);
		}
		elements.add(element);
	}
	
	public synchronized void add(String name, StackTraceElement value) {
		Log2DumpElement element = new Log2DumpElement();
		element.name = strnull(name);
		if (value != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(value.getClassName());
			sb.append(".");
			sb.append(value.getMethodName());
			sb.append("(");
			sb.append(value.getFileName());
			sb.append(":");
			sb.append(value.getLineNumber());
			sb.append(")");
			element.value = sb.toString();
		} else {
			element.value = strnull(null);
		}
		elements.add(element);
	}
	
	public synchronized void add(String name, Throwable value) {
		Log2DumpElement element = new Log2DumpElement();
		element.name = strnull(name);
		if (value != null) {
			element.value = value.getMessage();
			element.meta = value.getClass().getName();
		} else {
			element.value = strnull(null);
		}
		elements.add(element);
		
		if (value != null) {
			StackTraceElement[] stacks = value.getStackTrace();
			for (int pos = 0; pos < stacks.length; pos++) {
				add(name, stacks[pos]);
			}
		}
	}
	
	public synchronized ArrayList<String> getAllKeyName() {
		ArrayList<String> keys = new ArrayList<String>(elements.size());
		for (int pos = 0; pos < elements.size(); pos++) {
			keys.add(elements.get(pos).name);
		}
		return keys;
	}
	
	/**
	 * @return [value, meta]
	 */
	public synchronized ArrayList<String[]> getAllValueMeta() {
		ArrayList<String[]> values = new ArrayList<String[]>(elements.size());
		String[] value;
		for (int pos = 0; pos < elements.size(); pos++) {
			value = new String[2];
			value[0] = elements.get(pos).value;
			value[1] = elements.get(pos).meta;
			values.add(value);
		}
		return values;
	}
}
