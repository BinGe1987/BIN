package com.kwaijian.facility.OldSource.pageview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kwaijian.facility.UI.Home.Old.CompanyListAdapter;
import com.kwaijian.facility.UI.Home.Old.PageActivity;
import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Company;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.OldSource.widget.CustomDialog;
import com.kwaijian.facility.UI.Home.Old.OrderDetailPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompanyListPage extends Page implements RequestCallback {
	public static final int ADD_COMPANY = 15;

	private List<Company> companyList;

	public CompanyListPage(Context context) {
		super(context);
		companyList = new ArrayList<Company>();
		setContentView(R.layout.page_company_list);
		getServiceCompany();
		initview();
	}

	public void getServiceCompany() {
		CustomDialog.createLoadingDialog(getContext()).show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpRequest http = new HttpRequest(CompanyListPage.this);
				http.postRequest(HttpConst.Request.GET_COMPANY_LIST);
			}
		}).start();

	}

	public void initview() {
		((TextView) findViewById(R.id.title)).setText(getContext().getString(R.string.company_list));
		TextView add = (TextView) findViewById(R.id.save);
		add.setText(getContext().getString(R.string.add));
		findViewById(R.id.right_title_layout).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				PageActivity.openPageForResult(getContext(), CompanyAddPage.class, ADD_COMPANY);
			}
		});

		ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(new CompanyListAdapter(companyList));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra(OrderDetailPage.COMPANY, companyList.get(position));
				((Activity)getContext()).setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});

	}

	@Override
	public void callback(String data) {
		LogUtils.d(data);
		if (data == null) {
			return;
		}
		try {
			JSONObject json = new JSONObject(data);
			if (json.getString("errCode").equals("0")) {
				getCompany(json);
			} else {
				String errMsg = json.getString("errMsg");
				Message message = new Message();
				message.obj = errMsg;
				message.what = 0x100;
				mHandler.sendMessage(message);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void getCompany(JSONObject data) {
		companyList.clear();
		try {
			JSONArray json = data.getJSONArray("list_company");
			for (int i = 0; i < json.length(); i++) {
				JSONObject companyjson = json.getJSONObject(i);
				Company company = new Company();
				company.setData(companyjson);
				companyList.add(company);
			}
			mHandler.sendEmptyMessage(0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			CustomDialog.dismissDialog();
			if (companyList.size() != 0) {
				ListView listView = (ListView) findViewById(R.id.list);
				((CompanyListAdapter) listView.getAdapter()).update();
			}
			if (msg != null && msg.what == 0x100) {
				ToastUtils.show(getContext(), msg.obj + "");
			}

		};
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getServiceCompany();
	}

	
}
