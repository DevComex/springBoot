package cn.gyyx.action.service.cs2Sign;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Timing {
	public static final int FLAG_BEFORE = -1;
	public static final int FLAG_AFTER = 1;
	public static final int FLAG_INRANGE = 0;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private boolean debug = false;
	private long min,max;
	public Timing(String min,String max) {
		try {
			this.min = format.parse(min).getTime();
			this.max = format.parse(max).getTime();
		} catch (ParseException e) {
			this.min = 0;
			this.max = Integer.MAX_VALUE;
		}
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean currentAvailable() {
		if(debug) {
			return true;
		}
		long ts = System.currentTimeMillis();
		return ts >= min && ts <= max;
	}
	
	public int getCurrentFlag() {
		if(debug) {
			return FLAG_INRANGE;
		}
		long ts = System.currentTimeMillis();
		if(ts < min) {
			return FLAG_BEFORE;
		} else if(ts > max) {
			return FLAG_AFTER;
		} else {
			return FLAG_INRANGE;
		}
	}
}
