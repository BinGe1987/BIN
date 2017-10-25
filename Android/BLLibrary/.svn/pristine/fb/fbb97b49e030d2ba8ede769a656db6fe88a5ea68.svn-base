package com.kwaijian.facility.OldSource.pageview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Spare;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.OldSource.widget.CustomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompanySparepartAddPage extends Page implements /*ReturnResults, */RequestCallback {
	public static final String SPAREPART_DETAIL = "sparepartDetail";
	public static final String COMPANY_ID = "companyId";
	public static final String FACILITY_ID = "facilityId";
	private String httpFlag;
	private Handler mHandler;
	private Spare sparepart;			//被添加或更新的备件

	public CompanySparepartAddPage(Context context) {
		super(context);
		setContentView(R.layout.page_company_sparepart_add);

		mHandler = new Handler();

		initView();
	}

	private void initView() {
		findViewById(R.id.right_title_layout).setVisibility(View.GONE);
		if (getBundle().getSerializable(SPAREPART_DETAIL)!=null) {
			((TextView) findViewById(R.id.title)).setText(getContext().getString(R.string.update_sparepart));
			initSparepart(getContext(), getBundle());
		}else {
			((TextView) findViewById(R.id.title)).setText(getContext().getString(R.string.add_sparepart));
		}

		findViewById(R.id.add_device_sparepart).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sparepart = new Spare();
				String model = ((EditText) findViewById(R.id.model)).getText().toString();
				String brand = ((EditText) findViewById(R.id.brand)).getText().toString();
				String num = ((EditText) findViewById(R.id.num)).getText().toString();
				if (model.equals("")) {
					ToastUtils.show(getContext(), "请输入型号");
					return;
				}
				Pattern pattern = Pattern.compile("[0-9]*"); 
				Matcher isNum = pattern.matcher(num);
				if (num.equals("") || !isNum.matches()) {
					ToastUtils.show(getContext(), "请输入正确数量");
					return;
				}
				sparepart.setModel(model);
				sparepart.setBrand(brand);
				sparepart.setNum(num);
				Spare s = (Spare) getBundle().getSerializable(SPAREPART_DETAIL);
				if (s!=null) {
					sparepart.setId(s.getId());
				}
				addSpareparts();
			}
		});
	}

	private void addSpareparts() {
		new Thread(new Runnable() {
			public void run() {
				HttpRequest request = new HttpRequest(CompanySparepartAddPage.this);
				try {
					JSONObject json = sparepart.sparepartToJson();
					JSONObject finalJson = new JSONObject().put("spareparts", json)
												.put("company_id", getBundle().getString(COMPANY_ID))
												.put("facility_id", getBundle().getString(FACILITY_ID));
					LogUtils.d(finalJson+"<<<<<<<<");
					if (getBundle().getSerializable(SPAREPART_DETAIL)!=null) {
						httpFlag = HttpConst.Request.UPDATE_SPAREPARTS;
						request.postRequestOnChildThread(HttpConst.Request.UPDATE_SPAREPARTS, finalJson);
					}else {
						httpFlag = HttpConst.Request.ADD_SPAREPARTS;
						request.postRequestOnChildThread(HttpConst.Request.ADD_SPAREPARTS, finalJson);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	
	private void initSparepart(Context context, Bundle bundle) {
		Spare sparepart = (Spare) bundle.getSerializable(SPAREPART_DETAIL);
		((EditText) findViewById(R.id.model)).setText(sparepart.getModel());
		((EditText) findViewById(R.id.num)).setText(sparepart.getNum());
		((EditText) findViewById(R.id.brand)).setText(sparepart.getBrand());
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
					if(json.get("errCode").equals(0)){
						if (httpFlag.equals(HttpConst.Request.ADD_SPAREPARTS) || httpFlag.equals(HttpConst.Request.UPDATE_SPAREPARTS)) {
							Intent intent = new Intent();
							Bundle bundle = new Bundle();
							sparepart.setId(json.getString(HttpConst.Sparepart.ID));
							bundle.putSerializable(SPAREPART_DETAIL, sparepart);
							intent.putExtras(bundle);
							((Activity) getContext()).setResult(Activity.RESULT_OK, intent);
							finish();
						}
					}else{
						ToastUtils.show(getContext(), (String)json.get("errMsg"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
