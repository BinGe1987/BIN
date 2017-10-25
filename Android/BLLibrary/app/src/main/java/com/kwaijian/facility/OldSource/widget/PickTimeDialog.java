package com.kwaijian.facility.OldSource.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.tools.ToastUtils;

import java.util.Calendar;

public class PickTimeDialog extends Dialog {
	private Context mContext;
	private static int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int mWidth;
	private int mHeight;
	private boolean havePickedDate = true;
	private PickTimeListener listener;

	public PickTimeDialog(Context context, PickTimeListener listener) {
		super(context, R.style.dialog);
		mContext = context;
		this.listener = listener;
		mWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth()*3/4;
		mHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight()/3;
	}

	class PickTimeLayout extends LinearLayout /*implements OnDateChangedListener, OnTimeChangedListener*/ {

		public PickTimeLayout(Context context) {
			super(context);
			showDialog();
		}

		private void showDialog() {
			Calendar c = Calendar.getInstance();
			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DAY_OF_MONTH);
			hour = c.get(Calendar.HOUR_OF_DAY);
			minute = c.get(Calendar.MINUTE);
			
			LinearLayout linear = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.pick_time_layout,
					null);
			final DatePicker datePicker = (DatePicker) linear.findViewById(R.id.datePicker);
			datePicker.init(year, month, day, /*PickTimeLayout.this*/null);
			final TimePicker timePicker = (TimePicker) linear.findViewById(R.id.timePicker);
			final Button next = (Button) linear.findViewById(R.id.next);
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (havePickedDate) {
						next.setText("完成"); 
						timePicker.setVisibility(View.VISIBLE);
						datePicker.setVisibility(View.GONE);
						havePickedDate = false;
					}else{
						ToastUtils.show(mContext, year+"-"+month+"-"+day+"  "+hour+":"+minute);
						havePickedDate = true;
						listener.completed(year+"-"+month+"-"+day+"  "+hour+":"+minute);
						dismiss();
					}
				}
			});
					
			addView(linear, mWidth, mHeight);
		}

//		@Override
//		public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//		}

//		@Override
//		public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//			ToastUtils.show(getContext(), year+"/"+month+"/"+day+"/  "+hourOfDay+":"+minute+":");
//		}

	}
	public static int getYear(){
		return year;
	}	
	
	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	@Override
	public void show() {
		setContentView(new PickTimeLayout(getContext()));
		super.show();
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}
	
	public interface PickTimeListener{
		void completed(String time);
	}
	
	
}
