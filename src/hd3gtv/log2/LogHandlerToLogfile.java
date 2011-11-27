package hd3gtv.log2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Rotate is only manual
 */
public class LogHandlerToLogfile implements LogHandler {
	
	private File logfile;
	
	public LogHandlerToLogfile(File logfile) {
		this.logfile = logfile;
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
	
	public synchronized void rotatefile() {
		
		String path = logfile.getPath();
		
		System.out.println("LogHandlerToLogfile, rotatefile() " + path);
		
		File currentfile;
		
		(new File(path + ".999")).delete();
		
		for (int pos = 998; pos >= 0; pos--) {
			currentfile = new File(path + "." + String.valueOf(pos));
			if (currentfile.exists()) {
				currentfile.renameTo(new File(path + "." + String.valueOf(pos + 1)));
			}
		}
		
		logfile.renameTo(new File(path + ".0"));
		
		logfile = new File(path);
	}
}
