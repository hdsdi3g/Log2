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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPOutputStream;

public abstract class LogHandlerToSerializedByte implements LogHandler {
	
	protected abstract void manageEvent(byte[] data) throws IOException;
	
	public void onLog2Event(Log2Event event) {
		
		if (event == null) {
			return;
		}
		
		byte[] event_data = null;
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GZIPOutputStream gzipoutstream = new GZIPOutputStream(baos);
			ObjectOutputStream objectoutputstream = new ObjectOutputStream(gzipoutstream);
			objectoutputstream.writeObject(event);
			objectoutputstream.flush();
			objectoutputstream.close();
			event_data = baos.toByteArray();
			
		} catch (Exception e) {
			// MainClass.log2.error("Error to serializate event", e);
			return;
		}
		
		if (event_data == null) {
			return;
		}
		
		try {
			manageEvent(event_data);
		} catch (IOException e) {
			// MainClass.log2.error("Error manage serializated event", e);
			return;
		}
	}
	
}
