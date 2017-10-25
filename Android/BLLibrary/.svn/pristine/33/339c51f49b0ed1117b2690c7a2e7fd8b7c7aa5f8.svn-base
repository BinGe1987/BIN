package com.kwaijian.facility.OldSource.tools;


import android.util.Log;

import com.kwaijian.facility.BuildConfig;

public class LogUtils {
	
	public static void d(Object log) {
		log("facility", log);
	}

	private static void log(String tag, Object log) {
		if (BuildConfig.DEBUG) {
			Log.d(tag, log != null ? log.toString() : "null");
		}
	}
	
}
