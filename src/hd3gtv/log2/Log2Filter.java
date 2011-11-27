package hd3gtv.log2;

public class Log2Filter implements Log2Dumpable {
	
	String baseclassname;
	
	Log2Level level;
	
	Log2FilterType filtertype;
	
	Log2Filter(String baseclassname, Log2Level level, Log2FilterType filtertype) {
		this.baseclassname = baseclassname;
		if (baseclassname == null) {
			throw new NullPointerException("\"baseclassname\" can't to be null");
		}
		this.level = level;
		if (level == null) {
			throw new NullPointerException("\"level\" can't to be null");
		}
		this.filtertype = filtertype;
		if (filtertype == null) {
			throw new NullPointerException("\"filtertype\" can't to be null");
		}
	}
	
	public Log2Dump getLog2Dump() {
		Log2Dump dump = new Log2Dump();
		dump.add("baseclassname", baseclassname);
		dump.add("level", level);
		dump.add("filtertype", filtertype);
		return dump;
	}
	
	Log2FilterType howCanLogThis(Log2Event event) {
		StringBuffer sb = new StringBuffer();
		sb.append(event.caller[0].getClassName());
		sb.append(".");
		sb.append(event.caller[0].getMethodName());
		
		if (sb.toString().startsWith(baseclassname) == false) {
			return Log2FilterType.DEFAULT;
		}
		
		if (level.isMostImportant(event.level) == false) {
			return Log2FilterType.HIDE;
		}
		return filtertype;
	}
	
}
