package com.kwaijian.facility.UI.Home.Old;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.tools.Density;


public class BaseActivity extends Activity {

	protected View statusView;
	private LinearLayout rootView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = new LinearLayout(this);
		rootView.setOrientation(LinearLayout.VERTICAL);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusView = new View(this);
			statusView.setBackgroundColor(getResources().getColor(
					R.color.background_statusbar));
			statusView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, Density.statusBarHeight()));
			rootView.addView(statusView);
		}
	}
	
	public void setStatusViewColor(int color) {
		if (statusView != null) {
			statusView.setBackgroundColor(color);
		}
	}

	@Override
	public void setContentView(int layoutResID) {
		View view = getLayoutInflater().inflate(layoutResID, null);
		setContentView(view);
	}

	@Override
	public void setContentView(View view) {
		// rootView.setBackgroundColor(view.getResources().getColor(
		// R.color.background_statusbar));
		rootView.addView(view, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		super.setContentView(rootView);
		View back = rootView.findViewWithTag("back");
		if (back != null) {
			back.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					finishWithAnimationHorizontalOut();
				}
			});
		}
	}

	/**
	 * 水平页面切换动画
	 * 
	 * @param activity
	 */
	public void startActivityWithAnimationAphal(Class<?> activity) {
		startActivity(activity);
		overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
	}

	/**
	 * 水平页面切换动画
	 * 
	 * @param activity
	 */
	public void startActivityWithAnimationHorizontalIn(Class<?> activity) {
		startActivity(activity);
		overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
	}

	public void finishWithAnimationHorizontalOut() {
		finish();
		overridePendingTransition(R.anim.back_in, R.anim.back_out);
	}

	/**
	 * 同下到上页面切换动画
	 * 
	 * @param activity
	 */
	public void startActivityWithAnimationVertical(Class<?> activity) {
		startActivity(activity);
		overridePendingTransition(R.anim.anim2_activity_in,
				R.anim.anim2_activity_out);
	}

	public void finishWithAnimationVertical() {
		finish();
		overridePendingTransition(R.anim.anim2_back_in, R.anim.anim2_back_out);
	}

	public void startActivity(Class<?> activity) {
		Intent intent = new Intent(this, activity);
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// if (keyCode == KeyEvent.KEYCODE_BACK) {
		// return false;
		// }
		return super.onKeyDown(keyCode, event);
	}

	public TextView textViewById(int id) {
		return (TextView) findViewById(id);
	}

}
