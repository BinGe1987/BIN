package com.kwaijian.facility;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.kwaijian.facility", appContext.getPackageName());

        JSONObject jaon = make("key1:value2");
        System.out.println("");

    }


    public static JSONObject make(String... args) {
        JSONObject jsonObject = new JSONObject();
        int length = args.length;
        for (int i = 0; i < length; i++) {
            String arg = args[i].replaceFirst(":", "~~~");
            String[] argArray = arg.split("~~~");
            try {
                if (argArray.length == 2) {
                    jsonObject.put(argArray[0], argArray[1]);
                }
            } catch (Exception e) {
                throw new RuntimeException("JSON数据异常");
            }
        }
        return jsonObject;
    }

    public static JSONObject make(Object... args) {
        JSONObject jsonObject = new JSONObject();
        int length = args.length;
        try {
            for (int i = 0; i < length; ) {
                Object key = args[i];
                Object value = args[i + 1];
                jsonObject.put(key.toString(), value);
                i += 2;
            }
        } catch (Exception e) {
            throw new RuntimeException("JSON数据异常");
        }
        return jsonObject;
    }
}
