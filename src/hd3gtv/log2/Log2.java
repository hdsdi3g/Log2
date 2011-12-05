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

import java.util.ArrayList;

public class Log2 {
	
	ArrayList<LogHandler> handlers;
	public static Log2 log = new Log2(new LogHandlerToSysouterr());
	ArrayList<Log2Filter> filters;
	
	public Log2() {
		handlers = new ArrayList<LogHandler>();
		filters = new ArrayList<Log2Filter>();
	}
	
	public Log2(LogHandler loghandler) {
		handlers = new ArrayList<LogHandler>();
		handlers.add(loghandler);
		filters = new ArrayList<Log2Filter>();
	}
	
	synchronized void handle(Log2Event event) {
		for (int i = 0; i < handlers.size(); i++) {
			handlers.get(i).onLog2Event(event);
		}
	}
	
	public ArrayList<LogHandler> getHandlers() {
		return handlers;
	}
	
	private synchronized void event(Log2Event event, Log2Level Log2Level, String message, Throwable e) {
		event.message = message;
		event.level = Log2Level;
		event.error = e;
		handle(event);
	}
	
	public synchronized void info(String message) {
		event(new Log2Event(new Throwable(), this), Log2Level.INFO, message, null);
	}
	
	public synchronized void debug(String message) {
		event(new Log2Event(new Throwable(), this), Log2Level.DEBUG, message, null);
	}
	
	public synchronized void debug(String message, Log2Dump dump) {
		Log2Event event = new Log2Event(new Throwable(), this);
		event.dump = dump;
		event(event, Log2Level.DEBUG, message, null);
	}
	
	public synchronized void debug(String message, Log2Dumpable dumpable) {
		Log2Event event = new Log2Event(new Throwable(), this);
		if (dumpable != null) {
			event.dump = dumpable.getLog2Dump();
		}
		event(event, Log2Level.DEBUG, message, null);
	}
	
	public synchronized void info(String message, Log2Dump dump) {
		Log2Event event = new Log2Event(new Throwable(), this);
		event.dump = dump;
		event(event, Log2Level.INFO, message, null);
	}
	
	public synchronized void info(String message, Log2Dumpable dumpable) {
		Log2Event event = new Log2Event(new Throwable(), this);
		if (dumpable != null) {
			event.dump = dumpable.getLog2Dump();
		}
		event(event, Log2Level.INFO, message, null);
	}
	
	public synchronized void error(String message, Throwable e) {
		event(new Log2Event(new Throwable(), this), Log2Level.ERROR, message, e);
	}
	
	public synchronized void error(String message, Throwable e, Log2Dumpable dumpable) {
		Log2Event event = new Log2Event(new Throwable(), this);
		if (dumpable != null) {
			event.dump = dumpable.getLog2Dump();
		}
		event(event, Log2Level.ERROR, message, e);
	}
	
	public synchronized void error(String message, Throwable e, Log2Dump dump) {
		Log2Event event = new Log2Event(new Throwable(), this);
		event.dump = dump;
		event(event, Log2Level.ERROR, message, e);
	}
	
	/**
	 * Si l'event correspond a baseclassname && event >= level alors on affiche en suivant filtertype.
	 * Si l'event correspond a baseclassname && event < level alors on ne l'affiche pas du tout.
	 */
	public void createFilter(String baseclassname, Log2Level level, Log2FilterType filtertype) throws NullPointerException {
		Log2Filter filter = new Log2Filter(baseclassname, level, filtertype);
		filters.add(filter);
	}
	
	public Log2Dump getFilters() {
		Log2Dump dump = new Log2Dump();
		dump.add("filters", filters);
		return dump;
	}
}
