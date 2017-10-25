package com.kwaijian.facility.OldSource.widget;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kwaijian.facility.R;

import java.util.Calendar;

public class PickTimeView extends LinearLayout implements DatePickerDialog.OnDateSetListener, OnTimeSetListener {

	private PickTimeComplete mPickTimel;
	public final static int SELECT_DATE_REQUEST = 6;
	private boolean clickFlag = true;
	private TextView textView;
	private String data;

	public PickTimeView(Context context) {
		super(context);

	}

	public PickTimeView(final Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundResource(R.drawable.selector);
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);
				SetDateDialog sdt = new SetDateDialog(getContext(), PickTimeView.this, year, month, day);
				if (clickFlag) {
					clickFlag = false;
					sdt.show();
				} else {
					sdt.dismiss();
				}
			}
		});
	}

	class SetDateDialog extends DatePickerDialog {

		public SetDateDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
			super(context, callBack, year, monthOfYear, dayOfMonth);
		}

		@Override
		public void dismiss() {
			clickFlag = true;
			super.dismiss();
		}

		@Override
		public void setTitle(CharSequence title) {
			super.setTitle("选择日期");
		}

	}

	class SetTimeDialog extends TimePickerDialog {

		public SetTimeDialog(Context context, OnTimeSetListener listener, int hourOfDay, int minute,
				boolean is24HourView) {
			super(context, listener, hourOfDay, minute, is24HourView);
		}

		@Override
		public void setTitle(CharSequence title) {
			super.setTitle("选择时间");
		}

	}

	public void onDateSet(DatePicker view, int year, int month, int day) {

		data = year + "/" + (month + 1) + "/" + day + "  ";
		Calendar ca = Calendar.getInstance();
		int hour = ca.get(Calendar.HOUR_OF_DAY);
		int minute = ca.get(Calendar.MINUTE);
		SetTimeDialog setTime = new SetTimeDialog(getContext(), this, hour, minute, true);
		setTime.show();
	}

	public void pickTimeLinstening(TextView textView, PickTimeComplete pick) {
		mPickTimel = pick;
		this.textView = textView;
	}

	public interface PickTimeComplete {
		void completed(TextView p, String String);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		if (textView != null && mPickTimel != null) {
			String hour = hourOfDay + "";
			String min = minute + "";
			if (hourOfDay < 10) {
				hour = "0" + hourOfDay;
			}
			if (minute < 10) {
				min = "0" + minute;
			}
			mPickTimel.completed(textView, data + " " + hour + ":" + min);
		}
	}

}
