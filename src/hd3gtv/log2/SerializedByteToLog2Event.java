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
