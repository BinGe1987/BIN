package com.kwaijian.facility.OldSource.pageview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kwaijian.facility.R;

public class FacilityDetailPage extends Page {

	public FacilityDetailPage(Context context) {
		super(context);
		setContentView(R.layout.page_facility_detail);
		((TextView) findViewById(R.id.title)).setText(getContext().getString(R.string.facility_detail));
		initView();
	}

	public void initView() {
		findViewById(R.id.right_title_layout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
		});
	}

}
