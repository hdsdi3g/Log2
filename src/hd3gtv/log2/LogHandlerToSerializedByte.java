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
