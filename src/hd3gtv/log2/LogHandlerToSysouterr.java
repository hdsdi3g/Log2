package hd3gtv.log2;

public class LogHandlerToSysouterr implements LogHandler {
	
	public void onLog2Event(Log2Event event) {
		String value = event.toStringVerbose();
		
		if (value == null) {
			return;
		}
		
		if (event.level.equals(Log2Level.ERROR)) {
			System.err.println(value);
		} else {
			System.out.println(value);
		}
		
	}
}
