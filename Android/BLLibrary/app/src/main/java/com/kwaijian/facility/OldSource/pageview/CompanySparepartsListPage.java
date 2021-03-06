package com.kwaijian.facility.OldSource.pageview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kwaijian.facility.DataCenter.Home.DataObj.Spare;
import com.kwaijian.facility.UI.Home.Old.CompanySparepartsListAdapter;
import com.kwaijian.facility.UI.Home.Old.PageActivity;
import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Data;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.UI.Home.Old.OrderDetailPage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CompanySparepartsListPage extends Page implements RequestCallback{
	public static final String COMPANY_ID = "companyId";
	public static final String FACILITY_ID = "facilityId";
	private static final int ADD_COMPANY_SPAREPARTS = 21;
//	public static final String SELECTED_SPAREPARTS_ID = "selectedSparepartsId";
	private Handler mHandler;

	public CompanySparepartsListPage(Context context) {
		super(context);
		setContentView(R.layout.page_company_spareparts_list);
		
		mHandler = new Handler();
		
		initView();
	}

	private void initView() {
		((TextView) findViewById(R.id.title)).setText("备件列表");
		findViewById(R.id.right_title_layout).setOnClickListener(this);
		final ListView listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				Spare sparepart = (Spare) listView.getAdapter().getItem(pos);
				Bundle bundle = new Bundle();
				Intent intent = new Intent();
				bundle.putString(OrderDetailPage.SPAREPARTS_ID, sparepart.getId());
				bundle.putString(OrderDetailPage.SPAREPARTS_MODEL, sparepart.getModel());
				bundle.putString(OrderDetailPage.SPAREPARTS_NUM, sparepart.getNum());
				intent.putExtras(bundle);
				((Activity)getContext()).setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
		
		getSparepartsList();
	}

	private void getSparepartsList() {
		new Thread(new Runnable() {
			public void run() {
				HttpRequest request = new HttpRequest(CompanySparepartsListPage.this);
				JSONObject json;
				try {
					Bundle bundle = getBundle();
					String companyId = bundle.getString(COMPANY_ID);
					String facilityId = bundle.getString(FACILITY_ID);
					json = new JSONObject()
										.put("company_id", companyId)
										.put("facility_id", facilityId);
					request.postRequest(HttpConst.Request.GET_SPAREPARTS_LIST, json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void callback(final String data) {
		LogUtils.d(data);
		mHandler.post(new Runnable() {
			public void run() {
				if (data == null) {
					ToastUtils.show(getContext(), "请求服务器失败");
					return;
				}else{
					JSONObject json;
					try {
						json = new JSONObject(data);
						if(json.get("errCode").equals(0)){
							Data.getInstence().getSpareparts(json.getJSONArray("list_spareparts"));
							List<Spare> spareparts = Data.getInstence().getmSparepartslist();
//							List<String> selectedId = getBundle().getStringArrayList(SELECTED_SPAREPARTS_ID);
//							List<OrderSpare> temp = new ArrayList<OrderSpare>();
//							for (int i = 0; i < selectedId.size(); i++) {
//								for (int j = 0; j < spareparts.size(); j++) {
//									OrderSpare sparepart = spareparts.get(j);
//									if (sparepart.getId().equals(selectedId.get(i))) {
//										temp.add(sparepart);
//										break;
//									}
//								}
//							}
//							spareparts.removeAll(temp);
							((ListView)findViewById(R.id.list)).setAdapter(new CompanySparepartsListAdapter(spareparts));
						}else{
							ToastUtils.show(getContext(), (String)json.get("errMsg"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.right_title_layout:
			Bundle bundle = getBundle();
			String companyId = bundle.getString(COMPANY_ID);
			String facilityId = bundle.getString(FACILITY_ID);
			Bundle b = new Bundle();				//为了不搞混淆,另new一个bundle，使用CompanySparepartAddPage中的常量作为key
			b.putString(CompanySparepartAddPage.COMPANY_ID, companyId);
			b.putString(CompanySparepartAddPage.FACILITY_ID, facilityId);
			PageActivity.openPageForResult(getContext(), CompanySparepartAddPage.class, b, ADD_COMPANY_SPAREPARTS);
			break;

		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			getSparepartsList();
		}
	}
	
}
