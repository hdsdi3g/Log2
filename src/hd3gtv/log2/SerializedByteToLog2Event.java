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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

public class SerializedByteToLog2Event {
	
	private Log2 log2;
	
	public SerializedByteToLog2Event(Log2 log2) {
		this.log2 = log2;
	}
	
	public void manageSerializedEventBytes(byte[] data, int offset, int length) throws IOException, ClassNotFoundException {
		
		ByteArrayInputStream bais = new ByteArrayInputStream(data, offset, length);
		GZIPInputStream gzipinputstream = new GZIPInputStream(bais);
		ObjectInputStream objectinputstream = new ObjectInputStream(gzipinputstream);
		Object object = objectinputstream.readObject();
		if (object instanceof Log2Event) {
			log2.handle((Log2Event) object);
		} else {
			throw new ClassNotFoundException("Data is not a Log2Event : " + object.getClass().getName());
		}
		
	}
	
}
