package com.kwaijian.facility.Utils.Log;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ToastUtils {

	private static Toast sToast;
	private static Handler sHandler;
    private static Context appContext;
	
	public static void show(Context c, int string) {
		show(c, c.getResources().getString(string));
	}


    public static void show(String text) {
        show(null, text);
    }

	public static void show(Context c, final String text) {
        if (appContext == null) {
            if (c != null) {
                appContext = c.getApplicationContext();
            }
        }
        if (appContext == null) {
            return;
        }
		if (sHandler == null) {
            sHandler = new Handler(appContext.getMainLooper());
		}
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                if (sToast != null) {
                    sToast.cancel();
                }
                sToast = Toast.makeText(appContext, text, Toast.LENGTH_SHORT);
                sToast.show();

            }
        });
        LogUtils.t(text);
	}

}
