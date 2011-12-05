package hd3gtv.log2.demo;

import hd3gtv.log2.Log2;
import hd3gtv.log2.Log2Dump;
import hd3gtv.log2.Log2Dumpable;

public class SampleThread extends Thread implements Log2Dumpable {
	
	public Log2Dump getLog2Dump() {
		return new Log2Dump("info", "Nothing to tell");
	}
	
	public void run() {
		Log2.log.info("I run from a thread !");
	}
	
}
