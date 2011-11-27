package hd3gtv.log2;

public enum Log2Level {
	DEBUG, INFO, ERROR, NONE;
	
	boolean isMostImportant(Log2Level compare) {
		
		if (compare == this) {
			return true;
		}
		if (compare == NONE) {
			return false;
		}
		if (this == NONE) {
			return true;
		}
		
		int thislevel = 0; // DEBUG
		int comparelevel = 0; // DEBUG
		if (compare == INFO) {
			comparelevel = 1;
		}
		if (compare == ERROR) {
			comparelevel = 2;
		}
		if (this == INFO) {
			thislevel = 1;
		}
		if (this == ERROR) {
			thislevel = 2;
		}
		if (comparelevel > thislevel) {
			return true;
		}
		return false;
	}
	
}
