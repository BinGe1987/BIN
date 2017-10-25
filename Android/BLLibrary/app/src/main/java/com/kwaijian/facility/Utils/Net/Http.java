package com.kwaijian.facility.Utils.Net;


import android.graphics.BitmapFactory;

import com.kwaijian.facility.BaseClasses.OS.SystemInfo;
import com.kwaijian.facility.Utils.Localize.Saver;
import com.kwaijian.facility.Utils.Log.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinGe on 2017/9/13.
 * http 功能类
 */

public class Http {

    public static void removeCookie() {
        Saver.remove("JSESSIONID");
    }

    /**
     * post同步请求，返回请求结果
     *
     * @param requestUrl
     * @param params
     * @return
     */
    public static JSONObject post(String requestUrl, Map<String, Object> params) {
        //合并通用数据
        params = mergeCommonParams(params);
        LogUtils.p("post: " + requestUrl + "?" + params.toString());

        //数据准备
        StringBuffer requestParams = new StringBuffer();
        if (params != null) {
            try {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
//                    String key = URLEncoder.encode(entry.getKey(), "utf-8");
                    String key = entry.getKey();
//                    String value = URLEncoder.encode(entry.getValue().toString(), "utf-8");
                    String value = entry.getValue().toString();
                    requestParams.append(key);
                    requestParams.append("=");
                    requestParams.append(value);
                    requestParams.append("&");
                }
                if (requestParams.length() > 0) {
                    requestParams.deleteCharAt(requestParams.length() - 1);
                }
            } catch (Exception e) {
            }
        }
        long start = System.currentTimeMillis();
        JSONObject response = post(requestUrl, requestParams.toString());
        LogUtils.p("post_result(" + String.valueOf(System.currentTimeMillis() - start) + "ms): " + requestUrl + " - " + response);
        return response;
    }


    /**
     * Post服务请求
     *
     * @param requestUrl 请求地址
     * @param params     请求参数
     * @return
     */
    public static JSONObject post(String requestUrl, String params) {
        JSONObject json = new JSONObject();
        try {
            //建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            String cookie = Saver.getString("JSESSIONID", null);
            if (cookie != null) {
                LogUtils.d("cookie_1:" + cookie);
                connection.setRequestProperty("Cookie", cookie);
            }

            //设置连接属性
            connection.setDoOutput(true); //使用URL连接进行输出
            connection.setDoInput(true); //使用URL连接进行输入
            connection.setUseCaches(false); //忽略缓存
            connection.setRequestMethod("POST"); //设置URL请求方法

            //设置请求属性
            byte[] requestStringBytes = params.getBytes(); //获取数据字节数据
            connection.setRequestProperty("Content-length", "" + requestStringBytes.length);
//            connection.setRequestProperty("Content-Type", "application/octet-stream");
            connection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            connection.setRequestProperty("Charset", "UTF-8");

            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            //建立输出流,并写入数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestStringBytes);
            outputStream.close();

            //获取响应状态
            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) { //连接成功
                //当正确响应时处理数据
                StringBuffer buffer = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                //处理响应流
                responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    buffer.append(readLine).append("\n");
                }
                responseReader.close();
                json = new JSONObject(buffer.toString());
            } else {
                json.put("errCode", "-1");
                json.put("errMsg", "Unknown!");
                json.put("url", requestUrl);
            }
            String cookieVal = connection.getHeaderField("Set-Cookie");
            if (cookieVal != null && cookie == null) {
                String SESSIONID = cookieVal.substring(0, cookieVal.indexOf(";"));
                Saver.set("JSESSIONID", SESSIONID);
            }

        } catch (Exception e) {
            try {
                json.put("errCode", "-2");
                json.put("message", e.getMessage());
                json.put("url", requestUrl);
            } catch (JSONException e1) {

            }
        }
        return json;
    }

    /**
     * Get服务请求
     *
     * @param requestUrl
     * @return
     */
    public static String sendGet(String requestUrl) {
        try {
            //建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setDoInput(true);

            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            connection.connect();

            //获取响应状态
            int responseCode = connection.getResponseCode();

            if (HttpURLConnection.HTTP_OK == responseCode) { //连接成功
                //当正确响应时处理数据
                StringBuffer buffer = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                //处理响应流
                responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    buffer.append(readLine).append("\n");
                }
                responseReader.close();
                //JSONObject result = new JSONObject(buffer.toString());
                return buffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将原始参数合并上公用数据再传到服务器请求数据
     *
     * @param params 原始参数
     * @return 返回合并后的参数
     */
    private static Map<String, Object> mergeCommonParams(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        //先放通用参数
        //客户端的请求时间
        map.put("ts", System.currentTimeMillis());
        //操作系统
        map.put("os", "android");
        //客户端的版本
        map.put("version", SystemInfo.VersionCode);
        //机器码
        map.put("mid", SystemInfo.AndroidID);

        //将其它参数合并到json数据
        map.putAll(params);
        return map;
    }

    /**
     * 下载图片
     *
     * @param urlString
     * @param toDir
     * @return
     */
    public static String downloadImage(String urlString, String toDir) {
//        File file = new File(toDir, urlString.replaceAll("[^(A-Za-z.)]", ""));
        File file = new File(toDir, urlString.substring(urlString.lastIndexOf("/")) + ".jpg");
        if (checkImage(file.getAbsolutePath())) {
            LogUtils.d("Http.downloadImage : 从缓存中获取图片");
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
                    LogUtils.d("Http.downloadImage : 从网络下载图片");
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
