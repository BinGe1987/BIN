package com.kwaijian.facility.OldSource.pageview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.tools.ToastUtils;

public class CompanyAddFinancialInfoPage extends Page {

	public CompanyAddFinancialInfoPage(Context context) {
		super(context);
		setContentView(R.layout.page_company_financial_info);
		((TextView)findViewById(R.id.title)).setText("添加财务信息");
		findViewById(R.id.save).setVisibility(View.GONE);
		initView();
	}

	public void initView() {

		findViewById(R.id.capacitor_config_save).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ToastUtils.show(getContext(), "添加成功，点击左上角返回");

			}
		});
	}

}
