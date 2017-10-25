package com.kwaijian.facility.OldSource.http;

import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import com.kwaijian.facility.OldSource.tools.BitmapManager;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.Saver;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest implements Runnable {

	private RequestCallback mCallback;
	private String mInterface;
	private JSONObject mJson;

	public HttpRequest(RequestCallback cb) {
		mCallback = cb;
	}

	public void postRequest(String interfaceType) {
		postRequest(interfaceType, new JSONObject());
	}

	public void postRequest(String interfaceType, JSONObject json) {
		mInterface = interfaceType;
		mJson = json;
		run();
	}

	public void postRequestOnChildThread(String interfaceType) {
		postRequestOnChildThread(interfaceType, new JSONObject());
	}

	public void postRequestOnChildThread(String interfaceType, JSONObject json) {
		mInterface = interfaceType;
		mJson = json;
		new Thread(this).start();
	}

	@Override
	public void run() {
		String data = null;
		data = post(mInterface, mJson);
		if (mCallback != null) {
			mCallback.callback(data);
		}
	}

	public static String post(String interfaceType, JSONObject json) {
		return json.toString();
	}


	public static String jsonToParams(JSONObject json) {
		String params = json.toString();
		params = params.replace("}", "");
		params = params.replace("{", "");
		params = params.replace("\"", "");
		params = params.replace(":", "=");
		String[] strings = params.split(",");
		params = "";
		for (int i = 0; i < strings.length; i++) {
			params += strings[i] + "&";
		}
		return params.substring(0, params.length() - 1);
	}



	public void uploadImage(String interfaceType, String path) {
//		String cookies;
//		String data=null;
//		StringBuilder builder = new StringBuilder();
//		DefaultHttpClient client = new DefaultHttpClient();
//		client.getParams().setParameter(
//				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
//		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
//		cookies = Saver.getIntance().getString("cookies", "");
//		setRequestCookies(cookies,client);
////		if (mCookies != null) {
////			client.setCookieStore(mCookies);
////		}
//		HttpContext httpContext = new BasicHttpContext();
//		HttpPost httpPost = new HttpPost(HttpConst.Request.URL);
//		try {
//			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
//			entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//			entityBuilder.addPart("image", new FileBody(new File(path)));
//			entityBuilder.addTextBody(HttpConst.Request.REQUEST_INTERFACE, interfaceType);
//			HttpEntity entity = entityBuilder.build();// 生成 HTTP POST 实体
//			httpPost.setEntity(entity);
//			HttpResponse response = client.execute(httpPost, httpContext);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
//				builder.append(s);
//			}
//			data = JSONTokener(builder.toString());
//			LogUtils.d("-------->"+data);
//            try {
//                JSONObject json = new JSONObject(data);
//                json.put("local_path", path);
//                data = json.toString();
//            } catch (Exception e) {
//            }
//			mCallback.callback(data);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public void downloadImage(String imageId, File file){
//		int cache = 10 * 1024;
//		HttpClient client = new DefaultHttpClient();
//		HttpGet httpget = new HttpGet(HttpConst.Request.IMAGE_PATH + imageId);
//        try {
//			HttpResponse response = client.execute(httpget);
//			HttpEntity entity = response.getEntity();
//            InputStream is = entity.getContent();
//
//            FileOutputStream fileout = new FileOutputStream(file);
//
//            byte[] buffer=new byte[cache];
//            int ch = 0;
//            while ((ch = is.read(buffer)) != -1) {
//                fileout.write(buffer,0,ch);
//            }
//            is.close();
//            fileout.flush();
//            fileout.close();
//            mCallback.callback(new String("{\"errCode\":0,\"errMsg\":\"downloadImage:ok\",\"imageId\":\""+imageId+"\"}"));
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static boolean downloadImage(String url, String path) {
//        if (new File(path).exists()) {
//            return true;
//        }
//		int cache = 10 * 1024;
//		HttpClient client = new DefaultHttpClient();
//		HttpGet httpget = new HttpGet(url);
//		try {
//			HttpResponse response = client.execute(httpget);
//			HttpEntity entity = response.getEntity();
//			InputStream is = entity.getContent();
//
//			FileOutputStream fileout = new FileOutputStream(path);
//
//			byte[] buffer=new byte[cache];
//			int ch = 0;
//			while ((ch = is.read(buffer)) != -1) {
//				fileout.write(buffer,0,ch);
//			}
//			is.close();
//			fileout.flush();
//			fileout.close();
//			return true;
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return false;
	}

	public void downloadImage(Handler handler, final String imageId, File file, final ImageView iv){
//		int cache = 10 * 1024;
//		HttpClient client = new DefaultHttpClient();
//		HttpGet httpget = new HttpGet(HttpConst.Request.IMAGE_PATH + imageId);
//        try {
//			HttpResponse response = client.execute(httpget);
//			HttpEntity entity = response.getEntity();
//            InputStream is = entity.getContent();
//
//            FileOutputStream fileout = new FileOutputStream(file);
//
//            byte[] buffer=new byte[cache];
//            int ch = 0;
//            while ((ch = is.read(buffer)) != -1) {
//                fileout.write(buffer,0,ch);
//            }
//            is.close();
//            fileout.flush();
//            fileout.close();
//            mCallback.callback(new String("{\"errCode\":0,\"errMsg\":\"downloadImage:ok\",\"imageId\":\""+imageId+"\"}"));
//            handler.post(new Runnable() {
//				public void run() {
//					File image = new File(Environment.getExternalStorageDirectory(), "Facility/image/"+imageId+".png");
//					if (iv.getTag()!=null && iv.getTag().equals(imageId)) {
//						iv.setImageBitmap(BitmapManager.compressImage(image.toString()));
//					}
//				}
//			});
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
}
