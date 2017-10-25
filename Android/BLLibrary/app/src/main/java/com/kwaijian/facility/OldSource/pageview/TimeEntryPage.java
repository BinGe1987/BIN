package com.kwaijian.facility.OldSource.pageview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwaijian.facility.OldSource.widget.PickTimeView;
import com.kwaijian.facility.R;

public class TimeEntryPage extends Page implements PickTimeView.PickTimeComplete {

	public TimeEntryPage(Context context) {
		super(context);
		setContentView(R.layout.page_time_entry);
		((TextView)findViewById(R.id.title)).setText("时间录入");
		findViewById(R.id.save).setVisibility(View.GONE);
		initView();
	}

	private boolean mIsTemporary = false;

	public void initView() {
		findViewById(R.id.is_temporary_storage).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ImageView iamge = (ImageView) findViewById(R.id.is_temporary_icon);
				if (!mIsTemporary) {
					mIsTemporary = true;
					iamge.setImageResource(R.mipmap.yes);
				} else {
					mIsTemporary = false;
					iamge.setImageBitmap(null);
				}

			}
		});
		PickTimeView reqTime, servieTime, goTiem, leaveTiem, arrTime, workTime;
		reqTime = ((PickTimeView) findViewById(R.id.user_req_time));
		reqTime.pickTimeLinstening((TextView) reqTime.getChildAt(1), this);
		servieTime = ((PickTimeView) findViewById(R.id.user_service_time));
		servieTime.pickTimeLinstening((TextView) servieTime.getChildAt(1), this);
		goTiem = ((PickTimeView) findViewById(R.id.go_time));
		goTiem.pickTimeLinstening((TextView) goTiem.getChildAt(1), this);
		leaveTiem = ((PickTimeView) findViewById(R.id.leave_time));
		leaveTiem.pickTimeLinstening((TextView) leaveTiem.getChildAt(1), this);
		arrTime = ((PickTimeView) findViewById(R.id.arr_time));
		arrTime.pickTimeLinstening((TextView) arrTime.getChildAt(1), this);
		workTime = ((PickTimeView) findViewById(R.id.wrok_time));
		workTime.pickTimeLinstening((TextView) workTime.getChildAt(1), this);

	}

	@Override
	public void completed(TextView p, String string) {
		p.setText(string);
	}
}
