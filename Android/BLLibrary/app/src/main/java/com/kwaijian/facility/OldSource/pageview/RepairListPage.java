package com.kwaijian.facility.OldSource.pageview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.kwaijian.facility.UI.Login.LoginActivity;
import com.kwaijian.facility.UI.Home.Old.PageActivity;
import com.kwaijian.facility.UI.Home.Old.RepairAdapter;
import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Data;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.OldSource.widget.CustomDialog;
import com.kwaijian.facility.OldSource.widget.CustomDialog.ReturnResults;

import org.json.JSONException;
import org.json.JSONObject;

public class RepairListPage extends Page implements RequestCallback {
	private Handler mHandler;
	private String httpFlag;

	public RepairListPage(Context context) {
		super(context);
		setContentView(R.layout.tab_repair);
		mHandler = new Handler();
		initView(context);
	}

	private void initView(final Context context) {
		ListView listview = (ListView) findViewById(R.id.list);
		findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomDialog.createLoadingDialog(getContext());
				update();
			}
		});
		findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				CustomDialog.createMessageDialog(context, "你确定现在要退出", new ReturnResults() {
					@Override
					public void result(Object o) {
						if (o.equals("YES")) {
							new Thread(new Runnable() {
								public void run() {
//									httpFlag = HttpConst.Request.LOGOUT;
//									HttpRequest request = new HttpRequest(RepairListPage.this);
//									request.postRequest(HttpConst.Request.LOGOUT);
								}
							}).start();
						}
					}
				}).show();
			}
		});

		listview.setAdapter(new RepairAdapter(Data.getInstence().getRepairlist()));

		findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				PageActivity.openPage(context, RepairHistoryListPage.class);
			}
		});
	}

	public void update() {
//		new Thread(new Runnable() {
//			public void run() {
//				httpFlag = HttpConst.Request.GET_REPAIR_LIST;
//				HttpRequest http = new HttpRequest(RepairListPage.this);
//				http.postRequest(HttpConst.Request.GET_REPAIR_LIST);
//			}
//		}).start();
	}

	@Override
	public void onResume() {
		super.onResume();
		ListView listview = (ListView) findViewById(R.id.list);
//		CustomDialog.createLoadingDialog(getContext()).show();
		((RepairAdapter) listview.getAdapter()).updata();
	}

	@Override
	public void callback(final String data) {
		LogUtils.d(data);
		mHandler.post(new Runnable() {
			public void run() {
				CustomDialog.dismissDialog();
				if (data == null) {
					ToastUtils.show(getContext(), "请求服务器失败");
					return;
				}
				JSONObject json;
				try {
					json = new JSONObject(data);
					if (json.get("errCode").equals(10)) {
						if (httpFlag.equals(HttpConst.Request.LOGOUT)) {
							Intent intent = new Intent(getContext(), LoginActivity.class);
							((Activity) getContext()).startActivity(intent);
							finish();
						}
					} else if (json.get("errCode").equals(0)) {
						if (httpFlag.equals(HttpConst.Request.GET_REPAIR_LIST)) {
							Data.getInstence().getRepairlist(json.getJSONArray("list_repair"));
							ListView listview = (ListView) findViewById(R.id.list);
							listview.setAdapter(new RepairAdapter(Data.getInstence().getRepairlist()));
						}
					} else {
						ToastUtils.show(getContext(), (String) json.get("errMsg"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
