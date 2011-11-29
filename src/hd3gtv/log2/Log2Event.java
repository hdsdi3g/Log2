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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log2Event implements Serializable {
	
	private static final long serialVersionUID = -3947024342814354776L;
	
	public static final String LINESEPARATOR = System.getProperty("line.separator");
	
	private StackTraceElement[] getCaller(Throwable source) {
		StackTraceElement[] stack = source.getStackTrace();
		
		int callerposition = autodetectCallerposition(stack);
		
		StackTraceElement[] result = new StackTraceElement[stack.length - callerposition];
		System.arraycopy(stack, callerposition, result, 0, stack.length - callerposition);
		return result;
	}
	
	private int autodetectCallerposition(StackTraceElement[] stack) {
		int callerposition = 1;
		/**
		 * Si on a intercepte une classe log on continue.
		 */
		boolean previoushasdone = true;
		for (int pos = 1; pos < stack.length; pos++) {
			if (previoushasdone == false) {
				return callerposition;
			}
			previoushasdone = false;
			if (stack[pos].getClassName().equals("org.slf4j.Log2Logger")) {
				previoushasdone = true;
				callerposition++;
				continue;
			}
			if (stack[pos].getClassName().equals("org.eclipse.jetty.util.log.Log")) {
				previoushasdone = true;
				callerposition++;
				continue;
			}
			try {
				Class<?> clazz = Class.forName(stack[pos].getClassName());
				Class<?>[] clazzitf = clazz.getInterfaces();
				for (int positf = 0; positf < clazzitf.length; positf++) {
					if (clazzitf[positf].getName().equals("org.eclipse.jetty.util.log.Logger")) {
						previoushasdone = true;
						callerposition++;
						break;
					}
					// System.out.println(clazzitf[positf].getName());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				continue;
			}
			
		}
		return callerposition;
	}
	
	long date;
	
	String message = null;
	
	Throwable error = null;
	
	StackTraceElement[] caller;
	
	Log2Dump dump = null;
	
	Log2Level level;
	
	Thread thread;
	
	LogReferer referer;
	
	Log2 log2creator;
	
	Log2Event(Throwable source, Log2 log2creator) {
		date = System.currentTimeMillis();
		caller = getCaller(source);
		thread = Thread.currentThread();
		if (thread instanceof RunnableLog) {
			referer = ((RunnableLog) thread).getReferer();
		}
		this.log2creator = log2creator;
	}
	
	public static void throwableToString(Throwable error, StringBuffer append, String newline) {
		if (append == null) {
			return;
		}
		
		if (error == null) {
			return;
		}
		
		for (int i = 0; i < error.getStackTrace().length; i++) {
			append.append("    at ");
			append.append(error.getStackTrace()[i].toString());
			if (i + 1 < error.getStackTrace().length) {
				append.append(newline);
			}
		}
		
		if (error.getCause() != null) {
			append.append("  ");
			append.append(error.getCause().getMessage());
			append.append(newline);
			throwableToString(error.getCause(), append, newline);
		}
	}
	
	public static final String dateLog(long date) {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS").format(new Date(date));
	}
	
	public String toStringVerbose() {
		Log2FilterType filtertype = Log2FilterType.DEFAULT;
		for (int posflt = log2creator.filters.size() - 1; posflt >= 0; posflt--) {
			filtertype = log2creator.filters.get(posflt).howCanLogThis(this);
			if (filtertype != Log2FilterType.DEFAULT) {
				break;
			}
		}
		
		if (filtertype == Log2FilterType.HIDE) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(dateLog(date));
		sb.append(" ");
		
		sb.append(level.toString().toUpperCase());
		sb.append(" ");
		
		if (referer != null) {
			String refname = referer.getLogRefererName();
			if (refname.equals("") == false) {
				sb.append(refname);
				sb.append("/");
			}
		}
		
		sb.append(caller[0].getFileName().subSequence(0, caller[0].getFileName().lastIndexOf(".")));
		sb.append(" ");
		
		if (message != null) {
			sb.append("\"");
			sb.append(message);
			sb.append("\" ");
		}
		
		sb.append(thread.getName());
		sb.append(",");
		sb.append(thread.getId());
		
		if (filtertype != Log2FilterType.ONE_LINE) {
			sb.append(LINESEPARATOR);
			if (filtertype != Log2FilterType.VERBOSE_CALLER) {
				sb.append(" at ");
				sb.append(caller[0].getClassName());
				sb.append(".");
				sb.append(caller[0].getMethodName());
				sb.append("(");
				sb.append(caller[0].getFileName());
				sb.append(":");
				sb.append(caller[0].getLineNumber());
				sb.append(")");
			} else {
				for (int poscaller = 0; poscaller < caller.length; poscaller++) {
					sb.append(" at ");
					sb.append(caller[poscaller].getClassName());
					sb.append(".");
					sb.append(caller[poscaller].getMethodName());
					sb.append("(");
					sb.append(caller[poscaller].getFileName());
					sb.append(":");
					sb.append(caller[poscaller].getLineNumber());
					sb.append(")");
					if (poscaller + 1 < caller.length) {
						sb.append(LINESEPARATOR);
					}
				}
				
			}
			
			if (filtertype != Log2FilterType.NO_DUMP) {
				if (dump != null) {
					dump.dumptoString(sb, LINESEPARATOR);
				}
				
				if (error != null) {
					if (dump == null) {
						sb.append(LINESEPARATOR);
					}
					sb.append("  ");
					sb.append(error.getClass().getName());
					sb.append(": ");
					sb.append(error.getMessage());
					sb.append(LINESEPARATOR);
					throwableToString(error, sb, LINESEPARATOR);
					sb.append(Log2Event.LINESEPARATOR);
				}
			}
		}
		return sb.toString();
	}
}
