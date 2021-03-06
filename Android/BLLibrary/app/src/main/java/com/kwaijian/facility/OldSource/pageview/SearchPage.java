package com.kwaijian.facility.OldSource.pageview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.kwaijian.facility.UI.Home.Old.FacilityListAdapter;
import com.kwaijian.facility.UI.Home.Old.PageActivity;
import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Data;
import com.kwaijian.facility.DataCenter.Home.DataObj.Facility;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.OldSource.widget.CustomDialog;
import com.kwaijian.facility.OldSource.widget.CustomDialog.ReturnResults;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends Page implements TextWatcher, RequestCallback {
	private String httpFlag;
	private Handler mHandler;
	public static final int UPDATE_FACILITY = 0;
	private ListView mListView;

	public SearchPage(Context context) {
		super(context);
		setContentView(R.layout.page_search);
		mHandler = new Handler();
		initView(context);
	}

	private void initView(final Context context) {
		final EditText keyword = (EditText) findViewById(R.id.keyword);
		mListView = (ListView) findViewById(R.id.list);
		keyword.addTextChangedListener(SearchPage.this);
		findViewById(R.id.search).setOnClickListener(this);
		final ImageView delete = (ImageView) findViewById(R.id.delete);
		delete.setOnClickListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				Facility facility = (Facility) mListView.getAdapter().getItem(pos);
				Bundle bundle = new Bundle();
				bundle.putSerializable(FacilityAddPage.FACILITY_DETAIL, facility);
				PageActivity.openPageForResult(context, FacilityAddPage.class, bundle, UPDATE_FACILITY);
			}
		});
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long arg3) {
				CustomDialog.createChooseDialog(context, "是否删除设备", "", new ReturnResults() {
					@Override
					public void result(Object o) {
						if (o.toString().equals("yes")) {
							ToastUtils.show(context, "删除成功      " + o);
							deleteFacility(((Facility) mListView.getAdapter().getItem(pos)).getId());
						}
					}
				}).show();
				return true;
			}
		});
		keyword.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
					if (TextUtils.isEmpty(keyword.getText()) || keyword.getText().toString().length() == 1) {
						mListView.setAdapter(null);
						delete.setVisibility(View.GONE);
					}
				}
				return false;
			}
		});
	}

	@Override
	public void afterTextChanged(Editable s) {
		if (!TextUtils.isEmpty(s)) {
			searchFacility(s.toString());
			findViewById(R.id.delete).setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		EditText etKeyword = (EditText) findViewById(R.id.keyword);
		switch (v.getId()) {
		case R.id.search:
			String keyword = etKeyword.getText().toString();
			if (!TextUtils.isEmpty(keyword)) {
				searchFacility(keyword);
			}
			break;

		case R.id.delete:
			etKeyword.setText("");
			v.setVisibility(View.GONE);
			mListView.setAdapter(null);
			break;
		default:
			break;
		}
	}

	private void searchFacility(String keyword) {
		String tem = "\"f_name\":\"" + keyword;
		List<Facility> facilities = Data.getInstence().getmFacilitylist();
		List<Facility> temp = new ArrayList<Facility>();
		for (int i = 0; i < facilities.size(); i++) {
			if (facilities.get(i).isContainsKey(tem)) {
				temp.add(facilities.get(i));
			}
		}
		mListView.setAdapter(new FacilityListAdapter(temp, R.layout.page_facility_item));
	}

	private void deleteFacility(final String id) {
		new Thread(new Runnable() {
			public void run() {
				httpFlag = HttpConst.Request.DELETE_FACILITY;
				HttpRequest request = new HttpRequest(SearchPage.this);
				JSONObject json;
				try {
					json = new JSONObject().put(HttpConst.Facility.ID, id);
					request.postRequest(HttpConst.Request.DELETE_FACILITY, json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void callback(final String data) {
		mHandler.post(new Runnable() {
			public void run() {
				CustomDialog.dismissDialog();
				if (data == null) {
					ToastUtils.show(getContext(), "请求服务器失败");
					return;
				}
				try {
					final JSONObject json = new JSONObject(data);
					if (json.get("errCode").equals(0)) {
						if (httpFlag.equals(HttpConst.Request.GET_FACILITY_LIST)) {
							JSONArray jsonArray = json.getJSONArray("list_facility");
							Data.getInstence().getFacilities(jsonArray);
							String keyword = ((EditText) findViewById(R.id.keyword)).getText().toString();
							searchFacility(keyword);
						} else if (httpFlag.equals(HttpConst.Request.DELETE_FACILITY)) {
							getFacilities();
							CustomDialog.createLoadingDialog(getContext()).show();
						}
					}
				} catch (Exception e) {
					LogUtils.d(e.getMessage());
				}
			}
		});
	}

	public void getFacilities() {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				httpFlag = HttpConst.Request.GET_FACILITY_LIST;
//				HttpRequest request = new HttpRequest(SearchPage.this);
//				request.postRequest(HttpConst.Request.GET_FACILITY_LIST);
//			}
//		}).start();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			getFacilities();
		}
	}
}
