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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * Rotate is only manual
 */
public class LogHandlerToLogfile implements LogHandler {
	
	private File logfile;
	private long maxsize;
	private int maxfilelogs;
	
	/**
	 * @param logfile like something.log
	 * @param maxsize in bytes
	 * @param maxfilelogs more than 3
	 */
	public LogHandlerToLogfile(File logfile, long maxsize, int maxfilelogs) {
		this.logfile = logfile;
		this.maxsize = maxsize;
		if (maxfilelogs < 3) {
			maxfilelogs = 3;
		}
		this.maxfilelogs = maxfilelogs;
	}
	
	public synchronized void onLog2Event(Log2Event event) {
		String value = event.toStringVerbose();
		if (value == null) {
			return;
		}
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(logfile, true);
			fw.write(value);
			fw.write(Log2Event.LINESEPARATOR);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Rotate only if need to rotate.
	 */
	public synchronized void rotatefile() throws FileNotFoundException, IOException {
		if (logfile.length() < maxsize) {
			return;
		}
		
		String path = logfile.getPath();
		
		System.out.println("LogHandlerToLogfile, rotatefile() " + path);
		
		File currentfile;
		
		(new File(path + "." + String.valueOf(maxfilelogs - 1) + ".gz")).delete();
		
		for (int pos = (maxfilelogs - 2); pos >= 0; pos--) {
			currentfile = new File(path + "." + String.valueOf(pos) + ".gz");
			if (currentfile.exists()) {
				currentfile.renameTo(new File(path + "." + String.valueOf(pos + 1) + ".gz"));
			}
		}
		
		File newfilerotated = new File(path + ".0.gz");
		File temp = new File(path + ".tmp");
		logfile.renameTo(temp);
		logfile = new File(path);
		
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(temp));
		GZIPOutputStream gzip = new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(newfilerotated)));
		
		byte[] buffer = new byte[0xFFFF];
		int len;
		while ((len = bis.read(buffer)) > 0) {
			gzip.write(buffer, 0, len);
		}
		bis.close();
		gzip.close();
		temp.delete();
	}
}
