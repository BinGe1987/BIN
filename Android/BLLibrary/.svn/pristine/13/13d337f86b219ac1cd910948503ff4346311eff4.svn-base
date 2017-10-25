package com.kwaijian.facility.UI.Home.Old;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.pageview.Page;

import java.lang.reflect.Constructor;

public class PageActivity extends BaseActivity {

	public static void openPage(Context context, Class<?> cls) {
		Intent intent = new Intent(context, PageActivity.class);
		intent.putExtra("class", cls);

		context.startActivity(intent);
		if (context instanceof Activity) {
			Activity a = (Activity) context;
			a.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
		}
	}

	public static void openPage(Context context, Class<?> cls, Bundle b) {
		Intent intent = new Intent(context, PageActivity.class);
		intent.putExtra("class", cls);
		intent.putExtra("bundle", b);
		context.startActivity(intent);
		if (context instanceof Activity) {
			Activity a = (Activity) context;
			a.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
		}
	}
	
	public static void openPageForResult(Context context, Class<?> cls, int requestCode){
		Intent intent = new Intent(context, PageActivity.class);
		intent.putExtra("class", cls);
		Activity a = (Activity) context;
		a.startActivityForResult(intent, requestCode);
		a.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
	}
	public static void openPageForResult(Context context, Class<?> cls, Bundle b, int requestCode){
		Intent intent = new Intent(context, PageActivity.class);
		intent.putExtra("class", cls);
		intent.putExtra("bundle", b);
		Activity a = (Activity) context;
		a.startActivityForResult(intent, requestCode);
		a.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
	}

	private Page page;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStatusViewColor(getResources().getColor(R.color.background_title));
		try {
			Intent intent = getIntent();
			Class<?> cls = (Class<?>) intent.getSerializableExtra("class");

			if (cls != null) {
				Constructor<?> con = cls.getConstructor(Context.class);
				Object obj = con.newInstance(this);
				if (obj instanceof View) {
					setContentView((Page) obj);
					page = (Page) obj;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (page != null) {
			page.onActivityResult(requestCode, resultCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (page != null) {
			page.onPause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (page != null) {
			page.onResume();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (page != null) {
			page.onDestroy();
		}
	}
	
	

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.back_in, R.anim.back_out);
		}
		return super.onKeyUp(keyCode, event);
	}

}
