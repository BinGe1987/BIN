package com.kwaijian.facility.OldSource.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kwaijian.facility.UI.Home.Old.PageActivity;
import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.tools.LoadImageManager;

import java.io.File;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;

public class PickImageUploadDialog extends Dialog {
	private Context mContext;
	private int mWidth;
    private GalleryFinal.OnHanlderResultCallback mImageCallback;

	public PickImageUploadDialog(Context context) {
		super(context, R.style.dialog);
		createFile(context);
		mContext = context;
		getWindow().setGravity(Gravity.BOTTOM);
		mWidth=getWindow().getWindowManager().getDefaultDisplay().getWidth();

	}

    public PickImageUploadDialog(Context context, GalleryFinal.OnHanlderResultCallback callback) {
        super(context, R.style.dialog);
        createFile(context);
        mContext = context;
        getWindow().setGravity(Gravity.BOTTOM);
        mWidth=getWindow().getWindowManager().getDefaultDisplay().getWidth();
        mImageCallback = callback;
    }

	class PickImageLayout extends FrameLayout {

		public PickImageLayout(Context context) {
			super(context);
			showActionSheet();
		}

		public void showActionSheet() {
			LinearLayout linear = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.pick_image_layout,
					null);
			LinearLayout mLinearCacel = (LinearLayout) linear.findViewById(R.id.pick_image_layout);
			TextView mOpenCamera = (TextView) mLinearCacel.getChildAt(0);
			TextView mPickImage = (TextView) mLinearCacel.getChildAt(2);
			final TextView mCancel = (TextView) mLinearCacel.getChildAt(4);
			mOpenCamera.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(getPath())));
					((PageActivity) mContext).startActivityForResult(intent, LoadImageManager.IMAGE_CAPTURE);
					dismiss();
				}
			});
			mPickImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
//					Intent intent = new Intent(Intent.ACTION_PICK,
//							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//					intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//					((Activity) mContext).startActivityForResult(intent, LoadImageManager.CHOOSE_IMAGE);

                    FunctionConfig config = new FunctionConfig.Builder()
                            .setMutiSelectMaxSize(8)
                            .setEnablePreview(true)//是否开启预览功能
                            .build();
                    GalleryFinal.openGalleryMuti(100, config, mImageCallback);
					dismiss();
				}
			});
			mCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
			mLinearCacel.setVisibility(View.VISIBLE);
			mLinearCacel.setAnimation(AnimationUtils.loadAnimation(

			getContext(), R.anim.pick_anim_into));
			addView(linear, mWidth, LayoutParams.WRAP_CONTENT);
		}

		
	}

	private static String mImagePathString = "";

	public String getPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			sdDir = new File(sdDir, "image/");
			if (!sdDir.exists()) {
				sdDir.mkdirs();
			}
			sdDir = new File(sdDir, "temp" + ".png");
		} else {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			sdDir = new File(sdDir, "image/");
			if (!sdDir.exists()) {
				sdDir.mkdirs();
			}
			sdDir = new File(sdDir, "temp" + ".png");
		}
		mImagePathString = sdDir.toString();
		return sdDir.toString();

	}

	public static String getImagePath() {
		return mImagePathString;
	}

	public void createFile(Context context) {
		File imageFolder = new File(context.getFilesDir(), "image/");
		if (!imageFolder.exists()) {
			imageFolder.mkdirs();
		}
		File voiceFolder = new File(context.getFilesDir(), "voice/");
		if (!voiceFolder.exists()) {
			voiceFolder.mkdirs();
		}
	}

	@Override
	public void show() {
		setContentView(new PickImageLayout(getContext()));
		super.show();
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

}
