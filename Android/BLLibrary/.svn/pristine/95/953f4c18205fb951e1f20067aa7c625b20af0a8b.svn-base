package com.kwaijian.facility.OldSource.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import java.io.File;

public class LoadImageManager {
	public static final int CHOOSE_IMAGE = 2;
	public static final int IMAGE_CAPTURE = 3;

	public static void openImageFile(Activity context) {
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

		context.startActivityForResult(intent, CHOOSE_IMAGE);
	}

	public static void openTakePhoto(Activity content) {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.png")));
		content.startActivityForResult(intent, IMAGE_CAPTURE);

	}

	public static String uriToUrl(Context context, Uri uri) {
		String[] proj = { MediaStore.Images.Media.DATA };

		Cursor actualimagecursor = ((Activity) context).managedQuery(uri, proj, null, null, null);

		int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		actualimagecursor.moveToFirst();

		String img_path = actualimagecursor.getString(actual_image_column_index);

		File file = new File(img_path);
		return file.getPath();

	}
	
	public static void openImageFile(Context context, String url){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + url), "image/*");
		context.startActivity(intent);

//		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		ContentValues contentValues = new ContentValues(1);
//		contentValues.put(MediaStore.Images.Media.DATA, url);
//		Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//		intent.setDataAndType(uri, "image/*");
//		context.startActivity(intent);
	}
	
	/**
	 * 添加url指定的图片到container中
	 * @param context	上下文
	 * @param url		图片url
	 * @param container	图片容器
	 */
	public static void addImage(final Context context, final String url, ViewGroup container){
//		byte[] b = BitmapManager.getSmallBitmap(url);
//		if (b!=null) {
			ImageView image = new ImageView(context);
			Bitmap bitmap = BitmapManager.compressImage(url);
//			InputStream sbs = new ByteArrayInputStream(b);
//			Bitmap bitmap = BitmapFactory.decodeStream(sbs);
			image.setImageBitmap(bitmap);
			image.setScaleType(ScaleType.CENTER);
			LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(Density.dip2px(80), Density.dip2px(80));
			linearParams.leftMargin = Density.dip2px(10);
			image.setTag(url);			//用于复制图片时取得图片url
			container.addView(image, linearParams);
			
			image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					openImageFile(context, url);
				}
			});
//		}
	}

	public interface CallMethod {
		void back(Object o);
	}

}
