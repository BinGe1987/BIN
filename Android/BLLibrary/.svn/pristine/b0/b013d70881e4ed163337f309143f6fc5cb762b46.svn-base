package com.kwaijian.facility.OldSource.tools;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

	private static Toast sToast;
	
	public static void show(Context c, int string) {
		show(c, c.getResources().getString(string));
	}
	
	public static void show(Context c, String text) {
		if (sToast != null) {
			sToast.cancel();
		}
		sToast = Toast.makeText(c, text, Toast.LENGTH_SHORT);
		sToast.show();
	}
}
