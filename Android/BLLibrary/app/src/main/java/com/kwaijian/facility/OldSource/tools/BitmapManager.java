package com.kwaijian.facility.OldSource.tools;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class BitmapManager {
	public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
		int height = options.outHeight;
		int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	// 得到压缩后图片的byte[]
	public static byte[] getSmallBitmap(String filePath) {
		final Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
//		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		options.inSampleSize = calculateInSampleSize(options, 1200, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		Bitmap bit = BitmapFactory.decodeFile(filePath, options);
		String name = UUID.randomUUID().toString();
		if (bit != null) { // 如果bit大小为0会报空指针
			return bitmapToByte(bit, name);
		} else
			return null;
	}

	// bitmap---->byte[]
	public static byte[] bitmapToByte(Bitmap bmp, String name) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();// 初始化一个流对象
		bmp.compress(CompressFormat.JPEG, 100, output);// 把bitmap100%高质量压缩 到
														// output对象里
		bmp.recycle();// 自由选择是否进行回收

		byte[] result = output.toByteArray();// 转换成功了
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Bitmap compressImage(String filePath) {
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		opts.inSampleSize = 1;
		while(width > 2000 || height > 1500) {
			opts.inSampleSize *= 2;
			width /= 2;
			height /= 2;
		}
		opts.inJustDecodeBounds = false;
		Bitmap image = null;
		if (opts.inSampleSize == 1) {
			image = BitmapFactory.decodeFile(filePath);
		}else {
			image = BitmapFactory.decodeFile(filePath, opts);
		}
		return image;
	}
}
