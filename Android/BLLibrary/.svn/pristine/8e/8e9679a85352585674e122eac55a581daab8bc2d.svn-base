package com.kwaijian.facility.Application;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.Config.Operations;
import com.kwaijian.facility.DataCenter.DataCenter;
import com.kwaijian.facility.R;
import com.kwaijian.facility.UI.BaseClass.Activity.HTActivity;
import com.kwaijian.facility.UI.BaseClass.Views.PromptBox;
import com.kwaijian.facility.Utils.Log.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by BinGe on 2017/9/19.
 */

public class TestActivity extends HTActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        DataCenter.get().perform(Operations.Login.LoginWithPwd, new HashMap<String, String>() {{
//            put("account", "cwenhui");
//            put("password", "123456");
//        }}, new DataCenter.Callback() {
//            @Override
//            public void onCallback(String operation, HTData data) {
//                LogUtils.d("operation:" + operation);
//                DataCenter.get().perform(Operations.Home.Server.RefreshServerList, null, new DataCenter.Callback() {
//                    @Override
//                    public void onCallback(String operation, HTData data) {
//                        LogUtils.d("operation:" + operation);
//                    }
//                });
//            }
//        });

//        DataCenter.get().perform(Operations.Login.LoginWithAuto, null, new DataCenter.Callback() {
//            @Override
//            public void onCallback(String operation, HTData data) {
//
//            }
//        });

//        View view = new View(this);
//        setContentView(view);
//
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PromptBox.showText(TestActivity.this, "测试文本");
//            }
//        });
        new Thread() {
            @Override
            public void run() {
                final String file = downloadFile("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508848059529&di=df8d7624ab0ed031f4cd3b0923d8b8bf&imgtype=0&src=http%3A%2F%2Fimg.kutoo8.com%2Fupload%2Fthumb%2F938479%2F9f043b3297e9ce3b101b44f5525ef205_960x540.jpg", "/sdcard/image/");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageView iv = new ImageView(TestActivity.this);
                        if (file != null) {
                            LogUtils.d("file : "+ file);
                            Bitmap bitmap = BitmapFactory.decodeFile(file);
                            iv.setImageBitmap(bitmap);
                            setContentView(iv);
                        }
                    }
                });
            }
        }.start();


    }

    public static String downloadFile(String urlString, String toDir) {
        File file = new File(toDir, urlString.replaceAll("[^(A-Za-z.)]", ""));
        if (checkImage(file.getAbsolutePath())) {
            return file.getAbsolutePath();
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);  // 注意要设置超时，设置时间不要超过10秒，避免被android系统回收
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                FileOutputStream outStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                is.close();

                if (checkImage(file.getAbsolutePath())) {
                    return file.getAbsolutePath();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    private static boolean checkImage(String path) {
        if (new File(path).exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            if (options.outWidth > 0 && options.outHeight > 0) {
                return true;
            }
        }
        return false;
    }

}
