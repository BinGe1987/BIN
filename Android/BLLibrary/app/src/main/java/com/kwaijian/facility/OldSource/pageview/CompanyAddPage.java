package com.kwaijian.facility.OldSource.pageview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kwaijian.facility.UI.Home.Old.PageActivity;
import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Company;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.OldSource.widget.CustomDialog;
import com.kwaijian.facility.OldSource.widget.CustomDialog.ReturnResults;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanyAddPage extends Page implements RequestCallback{

	public static int PICK_CONTACT = 5;
	private Company company;
	private Handler mHandler;

	public CompanyAddPage(Context context) {
		super(context);
		setContentView(R.layout.page_company_add);
		((TextView) findViewById(R.id.title)).setText(context.getString(R.string.add_company));
		((TextView) findViewById(R.id.save)).setText(context.getString(R.string.save));
		initView();
	}

	public void initView() {
		company = new Company();
		mHandler = new Handler();
		
		final TextView chooseType = (TextView) findViewById(R.id.choose_type);
		final String[] items = new String[] { "系统生产商", "设备制造商", "代理商", "技术服务商", "最终客户" };
		chooseType.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable draw = getContext().getResources().getDrawable(R.mipmap.up_list_icon);
				draw.setBounds(0, 0, draw.getIntrinsicWidth(), draw.getIntrinsicHeight());
				chooseType.setCompoundDrawables(null, null, draw, null);
				CustomDialog.createSelecterDialog(getContext(), "选择类型", items, new ReturnResults() {
					@Override
					public void result(Object o) {
						Drawable draw = getContext().getResources().getDrawable(R.mipmap.down_list_icon);
						draw.setBounds(0, 0, draw.getIntrinsicWidth(), draw.getIntrinsicHeight());
						chooseType.setCompoundDrawables(null, null, draw, null);
						chooseType.setText(o + "");
					}
				}).show();
			}
		});

		findViewById(R.id.right_title_layout).setOnClickListener(this);
	}
	
	private void addCompany(/*final Company company*/) {
		new Thread(new Runnable() {
			public void run() {
				HttpRequest request = new HttpRequest(CompanyAddPage.this);
				JSONObject json;
				try {
					json = new JSONObject().put("company", company.companyToJson());
					request.postRequest(HttpConst.Request.ADD_COMPANY, json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.right_title_layout:
			String type = ((TextView)findViewById(R.id.choose_type)).getText().toString();
			String name = ((EditText)findViewById(R.id.name)).getText().toString();
			String address = ((EditText)findViewById(R.id.address)).getText().toString();
			String zipcode = ((EditText)findViewById(R.id.zipcode)).getText().toString();
			String phone = ((EditText)findViewById(R.id.phone)).getText().toString();
			String fax = ((EditText)findViewById(R.id.fax)).getText().toString();
			if (checkParams(type, name, address, zipcode, phone, fax)) {
				company.setType(type);
				company.setName(name);
				company.setAddress(address);
				company.setZipcode(zipcode);
				company.setPhone(phone);
				company.setFax(fax);
				addCompany();
				ToastUtils.show(getContext(), "添加成功，点击左上角返回");
			}
			break;

		case R.id.add_financial_info:
			PageActivity.openPage(getContext(), CompanyAddFinancialInfoPage.class);
			break;
			
		case R.id.open_contact:
			
			break;
		default:
			break;
		}
	}

	private boolean checkParams(String type, String name, String address, String zipcode, String phone, String fax) {
		Context context = getContext();
		if(type.equals("选择客户类型")){
			ToastUtils.show(context, "请选择客户类型");
			return false;
		}
		if(TextUtils.isEmpty(name)){
			ToastUtils.show(context, "请输入企业名称");
			return false;
		}
		if(TextUtils.isEmpty("address")){
			ToastUtils.show(context, "请输入企业地址");
			return false;
		}
		if (TextUtils.isEmpty(zipcode)) {
			ToastUtils.show(context, "请输入邮政编码");
			return false;
		}
		if (TextUtils.isEmpty(phone)) {
			ToastUtils.show(context, "请输入联系电话");
			return false;
		}
		if (name.length()<2 || name.length()>50) {
			ToastUtils.show(context, "企业名称长度不合法(2~50字符)");
			return false;
		}
		if (address.length()<2 || address.length()>100) {
			ToastUtils.show(context, "企业地址长度不合法(2~100字符)");
			return false;
		}
		if (zipcode.length()!=6) {
			ToastUtils.show(context, "邮政编码长度不合法(6字符)");
			return false;
		}
		if (phone.length()<2 || phone.length()>20) {
			ToastUtils.show(context, "联系电话长度不合法(2~20字符)");
			return false;
		}
		if (fax.length()<2 || fax.length()>30) {
			ToastUtils.show(context, "传真号码长度不合法(2~30字符)");
			return false;
		}
		return true;
	}

	@Override
	public void callback(final String data) {
		LogUtils.d(data); 
		mHandler.post(new Runnable() {
			public void run() {
				if (data == null) {
					ToastUtils.show(getContext(), "请求服务器失败");
					return;
				}else {
					JSONObject json;
					try {
						json = new JSONObject(data);
						if(json.get("errCode").equals(0)){
							finish();
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

	
}
