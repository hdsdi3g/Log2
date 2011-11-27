package org.slf4j;

import hd3gtv.log2.Log2;
import hd3gtv.log2.LogHandlerToSysouterr;

public class Log2Logger implements Logger {
	
	private String name;
	
	public static boolean enabledtrace = true;
	public static Log2 log;
	
	public Log2Logger(String name) {
		this.name = name;
		if (log == null) {
			log = new Log2(new LogHandlerToSysouterr());
			// log.setCallerposition(2);
		}
	}
	
	/**
	 * Remplace la premiere occurence de {} dans format par what.
	 */
	private String format(String format, Object arg) {
		int bracketL = format.indexOf("{");
		int bracketR = format.indexOf("}");
		
		String what = arg.toString();
		
		StringBuffer sb = new StringBuffer(format.length() + what.length());
		
		if ((bracketL == -1) | (bracketR == -1) | (bracketL > bracketR)) {
			sb.append(format);
			sb.append(" ; ");
			sb.append(what);
			return sb.toString();
		}
		
		sb.append(format.substring(0, bracketL));
		sb.append(what);
		sb.append(format.substring(bracketR + 1));
		return sb.toString();
	}
	
	private String format(String format, Object arg1, Object arg2) {
		String result = format(format, arg1);
		return format(result, arg2);
	}
	
	private String format(String format, Object[] argArray) {
		if (argArray == null) {
			return format;
		}
		if (argArray.length == 0) {
			return format;
		}
		
		String result = format;
		for (int pos = 0; pos < argArray.length; pos++) {
			result = format(result, argArray[pos]);
		}
		return result;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isTraceEnabled() {
		return enabledtrace;
	}
	
	public void trace(String msg) {
		log.debug(msg);
	}
	
	public void trace(String format, Object arg) {
		log.debug(format(format, arg));
	}
	
	public void trace(String format, Object arg1, Object arg2) {
		log.debug(format(format, arg1, arg2));
	}
	
	public void trace(String format, Object[] argArray) {
		log.debug(format(format, argArray));
	}
	
	public void trace(String msg, Throwable t) {
		log.error(msg, t);
	}
	
	public boolean isTraceEnabled(Marker marker) {
		return false;
	}
	
	public void trace(Marker marker, String msg) {
	}
	
	public void trace(Marker marker, String format, Object arg) {
	}
	
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
	}
	
	public void trace(Marker marker, String format, Object[] argArray) {
	}
	
	public void trace(Marker marker, String msg, Throwable t) {
	}
	
	public static boolean enableddebug;
	
	public boolean isDebugEnabled() {
		return enableddebug;
	}
	
	public void debug(String msg) {
		log.debug(msg);
	}
	
	public void debug(String format, Object arg) {
		log.debug(format(format, arg));
	}
	
	public void debug(String format, Object arg1, Object arg2) {
		log.info(format(format, arg1, arg2));
	}
	
	public void debug(String format, Object[] argArray) {
		log.debug(format(format, argArray));
	}
	
	public void debug(String msg, Throwable t) {
		log.error(name, t);
	}
	
	@Override
	public boolean isDebugEnabled(Marker marker) {
		return false;
	}
	
	@Override
	public void debug(Marker marker, String msg) {
		
	}
	
	@Override
	public void debug(Marker marker, String format, Object arg) {
		
	}
	
	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		
	}
	
	@Override
	public void debug(Marker marker, String format, Object[] argArray) {
		
	}
	
	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		
	}
	
	public boolean isInfoEnabled() {
		return true;
	}
	
	public void info(String msg) {
		log.info(msg);
	}
	
	public void info(String format, Object arg) {
		log.info(format(format, arg));
	}
	
	public void info(String format, Object arg1, Object arg2) {
		log.info(format(format, arg1, arg2));
	}
	
	public void info(String format, Object[] argArray) {
		log.info(format(format, argArray));
	}
	
	@Override
	public void info(String msg, Throwable t) {
		log.error(msg, t);
	}
	
	@Override
	public boolean isInfoEnabled(Marker marker) {
		return false;
	}
	
	@Override
	public void info(Marker marker, String msg) {
		
	}
	
	@Override
	public void info(Marker marker, String format, Object arg) {
		
	}
	
	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		
	}
	
	@Override
	public void info(Marker marker, String format, Object[] argArray) {
		
	}
	
	@Override
	public void info(Marker marker, String msg, Throwable t) {
		
	}
	
	public boolean isWarnEnabled() {
		return true;
	}
	
	public void warn(String msg) {
		log.info(msg);
	}
	
	public void warn(String format, Object arg) {
		log.info(format(format, arg));
	}
	
	public void warn(String format, Object[] argArray) {
		log.info(format(format, argArray));
	}
	
	public void warn(String format, Object arg1, Object arg2) {
		log.info(format(format, arg1, arg2));
	}
	
	@Override
	public void warn(String msg, Throwable t) {
		log.error(msg, t);
	}
	
	@Override
	public boolean isWarnEnabled(Marker marker) {
		return false;
	}
	
	@Override
	public void warn(Marker marker, String msg) {
		
	}
	
	@Override
	public void warn(Marker marker, String format, Object arg) {
		
	}
	
	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		
	}
	
	@Override
	public void warn(Marker marker, String format, Object[] argArray) {
		
	}
	
	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		
	}
	
	public boolean isErrorEnabled() {
		return true;
	}
	
	public void error(String msg) {
		log.error(msg, null);
	}
	
	public void error(String format, Object arg) {
		log.error(format(format, arg), null);
	}
	
	@Override
	public void error(String format, Object arg1, Object arg2) {
		log.error(format(format, arg1, arg2), null);
	}
	
	@Override
	public void error(String format, Object[] argArray) {
		log.info(format(format, argArray));
	}
	
	public void error(String msg, Throwable t) {
		log.error(msg, t);
	}
	
	@Override
	public boolean isErrorEnabled(Marker marker) {
		
		return false;
	}
	
	@Override
	public void error(Marker marker, String msg) {
		
	}
	
	@Override
	public void error(Marker marker, String format, Object arg) {
		
	}
	
	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		
	}
	
	@Override
	public void error(Marker marker, String format, Object[] argArray) {
		
	}
	
	@Override
	public void error(Marker marker, String msg, Throwable t) {
		
	}
	
}
