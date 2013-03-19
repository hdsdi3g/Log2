package hd3gtv.log2.demo;

import hd3gtv.log2.Log2;
import hd3gtv.log2.LogHandlerToLogfile;

import java.io.File;

public class SampleRotate {
	
	public static void main(String[] args) {
		System.out.println("Kill me to exit...");
		System.out.println();
		
		final LogHandlerToLogfile loghandler = new LogHandlerToLogfile(new File("my.log"), 10000, 10);
		Log2.log = new Log2(loghandler);
		
		Thread t_makemessages = new Thread(new Runnable() {
			public void run() {
				while (true) {
					Log2.log.info("This is a stupid message log : " + String.valueOf(Math.round(Math.random() * 100d)));
					try {
						Thread.sleep(Math.round(Math.random() * 100d));
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}
				}
			}
		});
		t_makemessages.setDaemon(false);
		t_makemessages.start();
		
		Thread t_regular_rotate = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						loghandler.rotatefile();
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
				}
			}
		});
		t_regular_rotate.setDaemon(false);
		t_regular_rotate.start();
	}
}
