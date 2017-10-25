package com.kwaijian.facility.Utils.Log;


import android.util.Log;

import com.kwaijian.facility.Application.App;

public class LogUtils {

    public static boolean isTempDebugOn = true;

    public static void d(Object log) {
        if (isLogDebugSwitchOn()) {
            log("api_debug", log);
        }
    }

    public static void a(Object log) {
        if (isLogDebugSwitchOn()) {
            error("api_agora", log);
        }
    }

    public static void c(Object log) {
        if (isLogDebugSwitchOn()) {
            error("api_cnc", log);
        }
    }

    public static void k(Object log) {
        if (isLogDebugSwitchOn()) {
            error("api_ksy", log);
        }
    }

    public static void test(Object log) {
        if (isLogDebugSwitchOn()) {
            log("api_test", log);
        }
    }

    public static void p(Object log) {
        if (isLogDebugSwitchOn()) {
            log("api_post", log);
        }
    }

    public static void r(Object log) {
        if (isLogDebugSwitchOn()) {
            log("live_room", log);
        }
    }

    public static void im(Object log) {
        if (isLogDebugSwitchOn()) {
            log("api_im", log);
        }
    }

    public static void t(Object log) {
        if (isLogDebugSwitchOn()) {
            log("api_toast", log);
        }
    }

    public static void e(Object log) {
        if (isLogDebugSwitchOn()) {
            error("ht_error", log);
        }
    }

    public static void toast(Object tip) {
        if (isLogDebugSwitchOn() && tip != null) {
            ToastUtils.show(App.getContext(), tip.toString());
        }
    }

//    public static void report(String message, Exception e) {
//        error("ht_error", message);
//        UmengApi.reportError(new RuntimeException(message, e));
//    }

    /**
     * 打印当前的堆栈信息
     */
    public static void callStack() {
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                StackTraceElement s = stackElements[i];
                log("callStack", s.getFileName() +
                        ":" + s.getClassName() +
                        ":" + s.getMethodName()
                );
            }
        }
    }

    public static void log(String tag, Object log) {
        Log.d(tag, log != null ? log.toString() : "null");
    }

    public static void error(String tag, Object log) {
        Log.e(tag, log != null ? log.toString() : "null");
    }


    public static boolean isLogDebugSwitchOn() {
        return /*Config.DEBUG_MODE ||*/ isTempDebugOn;
    }

}
