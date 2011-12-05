package hd3gtv.log2.demo;

import hd3gtv.log2.Log2;
import hd3gtv.log2.Log2Dump;

import java.io.File;

/**
 * Somme simple examples for Log2
 */
public class Sample {
	
	public static void main(String[] args) {
		Log2.log.info("My Message 1");
		/*
			RETURN
			2011/12/02 22:14:13,024 INFO Sample "My Message 1" main,1
			 at hd3gtv.log2.demo.Sample.main(Sample.java:11)
			 
			YYYY/MM/DD HH:MM:SS,MSEC LEVEL Classname "Message" Threadname,ThreadId
			 at Classlongname(Classname.java:Linenumber)
		*/
		
		try {
			throw new Exception("My error");
		} catch (Exception e) {
			Log2.log.error("My catch message", e);
		}
		/*
			RETURN
			2011/12/02 22:14:13,024 ERROR Sample "My catch message" main,1
			 at hd3gtv.log2.demo.Sample.main(Sample.java:23)
			  java.lang.Exception: My error
			   at hd3gtv.log2.demo.Sample.main(Sample.java:21)

		 */
		
		Log2Dump dump = new Log2Dump();
		dump.add("var1", "Value 1");
		dump.add("var2", 44);
		dump.add("a file", new File("/etc/hosts"));
		Log2.log.info("My Message with debug informations", dump);
		
		/*
		    RETURN
		    2011/12/02 22:14:13,024 INFO Sample "My Message with debug informations" main,1
			 at hd3gtv.log2.demo.Sample.main(Sample.java:42)
			var1   Value 1
			var2   44
			a file /etc/hosts	FRo 265 (2011/09/27 17:28:59,000)
			
			File description:
			/etc/hosts = full path name
			FRo = file in read only state (for Java process)
			265 = size in octets
			2011/09/27 17:28:59,000 = last edit/creation time
			
			FRW = file can to be read and write (for Java process)
			Idem for DRW and DRo
						
		*/
		
		SampleThread samplethread1 = new SampleThread();
		SampleThread samplethread2 = new SampleThread();
		Log2.log.info("Start sample thread 1", samplethread1);
		samplethread1.start();
		Log2.log.info("Start sample thread 2", samplethread2);
		samplethread2.start();
		Log2.log.info("Done");
		
		/*
			RETURN
		2011/12/02 22:45:02,916 INFO Sample "Start sample thread 1" main,1
		 at hd3gtv.log2.demo.Sample.main(Sample.java:65)
		info Nothing to tell

		2011/12/02 22:45:02,916 INFO Sample "Start sample thread 2" main,1
		 at hd3gtv.log2.demo.Sample.main(Sample.java:67)
		info Nothing to tell

		2011/12/02 22:45:02,916 INFO SampleThread "I run from a thread !" Thread-1,10
		 at hd3gtv.log2.demo.SampleThread.run(SampleThread.java:14)
		2011/12/02 22:45:02,916 INFO SampleThread "I run from a thread !" Thread-2,11
		 at hd3gtv.log2.demo.SampleThread.run(SampleThread.java:14)
		2011/12/02 22:45:02,917 INFO Sample "Done" main,1
		 at hd3gtv.log2.demo.Sample.main(Sample.java:69)
		 
		 */
	}
	
}
