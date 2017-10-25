package com.kwaijian.facility.OldSource.pageview;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kwaijian.facility.R;
import com.kwaijian.facility.DataCenter.Home.DataObj.Data;
import com.kwaijian.facility.DataCenter.Home.DataObj.Order;
import com.kwaijian.facility.OldSource.tools.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanyDetailPage extends Page {

	private Order mOrder;

	public CompanyDetailPage(Context context) {
		super(context);
		setContentView(R.layout.page_company_detail);
		((TextView) findViewById(R.id.title)).setText(getContext().getString(R.string.company_detail));

		initView();

	}

	public void initView() {
		findViewById(R.id.right_title_layout).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TextView text = (TextView) ((LinearLayout) v).getChildAt(0);
				if (text.getText().toString().endsWith("编辑")) {

					text.setText("保存");
				} else if (text.getText().toString().endsWith("保存") && mOrder != null) {
					ToastUtils.show(v.getContext(), "保存成功");
					saveData();
					mOrder.orderToJson();
					InputMethodManager input = (InputMethodManager) getContext()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					input.hideSoftInputFromWindow(v.getWindowToken(), 0);
					text.setText("编辑");
				}
			}
		});
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				setData();
			}
		});
	}

	public void setData() {
		String positon = (String) getTag();
		if (positon == null) {
			return;
		}
		Data mData;
		if (mOrder == null) {
			mData = Data.getInstence();
			for (int i = 0; i < mData.getOrderlist().size(); i++) {
				if (mData.getOrderlist().get(i).getId().equals(positon)) {
					mOrder = mData.getOrderlist().get(i);
					break;
				}
			}

		}

//		((TextView) findViewById(R.id.company_name)).setText(mOrder.getCompany().getName());
//		((TextView) findViewById(R.id.company_addr)).setText(mOrder.getCompany().getAddress());
//		((TextView) findViewById(R.id.company_tel)).setText(mOrder.getCompany().getTel());
	}

	public void saveData() {
		JSONObject json = new JSONObject();
		try {
			json.put("name", ((TextView) findViewById(R.id.company_name)).getText().toString());
			json.put("address", ((TextView) findViewById(R.id.company_addr)).getText().toString());
			json.put("tel", ((TextView) findViewById(R.id.company_tel)).getText().toString());
//			mOrder.getCompany().setData(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
