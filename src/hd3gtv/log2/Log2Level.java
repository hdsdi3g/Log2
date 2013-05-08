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

public enum Log2Level {
	DEBUG, INFO, ERROR, SECURITY, NONE;
	
	boolean isMostImportant(Log2Level compare) {
		
		if (compare == this) {
			return true;
		}
		if (compare == NONE) {
			return false;
		}
		if (this == NONE) {
			return true;
		}
		
		int thislevel = 0; // DEBUG
		int comparelevel = 0; // DEBUG
		if (compare == INFO) {
			comparelevel = 1;
		}
		if (compare == ERROR) {
			comparelevel = 2;
		}
		if (compare == SECURITY) {
			comparelevel = 3;
		}
		if (this == INFO) {
			thislevel = 1;
		}
		if (this == ERROR) {
			thislevel = 2;
		}
		if (this == SECURITY) {
			thislevel = 3;
		}
		if (comparelevel > thislevel) {
			return true;
		}
		return false;
	}
	
}
