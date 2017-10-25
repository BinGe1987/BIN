package com.kwaijian.facility.OldSource.pageview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kwaijian.facility.R;

public class ContactsDetailPage extends Page {

	public ContactsDetailPage(Context context) {
		super(context);
		setContentView(R.layout.page_contact_detail);
		((TextView) findViewById(R.id.title)).setText(getContext().getString(R.string.contact_detail));
		initView();
	}

	public void initView() {
		findViewById(R.id.right_title_layout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
//				

			}
		});

	}

}
