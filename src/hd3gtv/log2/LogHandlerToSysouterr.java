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

public class LogHandlerToSysouterr implements LogHandler {
	
	public void onLog2Event(Log2Event event) {
		String value = event.toStringVerbose();
		
		if (value == null) {
			return;
		}
		
		if (event.level.equals(Log2Level.ERROR)) {
			System.err.println(value);
		} else {
			System.out.println(value);
		}
		
	}
}
