package com.kwaijian.facility.OldSource.pageview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kwaijian.facility.UI.Home.Old.PageActivity;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Contacts;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.OldSource.widget.CustomDialog;
import com.kwaijian.facility.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactAddPage extends Page implements RequestCallback{
	public static final String COMPANY_NAME = "companyName";
	public static final String CONTACT_NAME = "contactName";
	public static final String CONTACT_NUMBER = "contactNumber";
	public static final int IMPORT_CONTACT = 21;
	private Contacts linkman;
	private Handler mHandler;
	
	public ContactAddPage(Context context) {
		super(context);
		setContentView(R.layout.page_contact_add);
		initView();
	}

	public void initView() {
		linkman = new Contacts();
		mHandler = new Handler();

		((TextView) findViewById(R.id.title)).setText(getContext().getString(R.string.add_contact));
		((TextView) findViewById(R.id.save)).setText(getContext().getString(R.string.save));
		
		((TextView)findViewById(R.id.company)).setText(getBundle().getString(COMPANY_NAME));
		findViewById(R.id.right_title_layout).setOnClickListener(this);
		findViewById(R.id.add_device).setOnClickListener(this);
	}
	
	private boolean checkParams(String name, String mobile) {
		Context context = getContext();
		if(TextUtils.isEmpty(name)){
			ToastUtils.show(context, "请输入联系人姓名");
			return false;
		}
		if (TextUtils.isEmpty(mobile)) {
			ToastUtils.show(context, "请输入手机号码");
			return false;
		} 
		if(name.length()<2 || name.length()>10){
			ToastUtils.show(context, "联系人姓名长度不合法(2~10字符)");
			return false;
		}
		if(mobile.length()<2 || mobile.length()>20){
			ToastUtils.show(context, "手机号码长度不合法(2~20字符)");
			return false;
		}
		return true;
	}
	
	private void addLinkman() {
		CustomDialog.createLoadingDialog(getContext());
		new Thread(new Runnable() {
			public void run() {
				HttpRequest request = new HttpRequest(ContactAddPage.this);
				JSONObject json;
				try {
					json = new JSONObject().put("linkman", linkman.contactsToJson());
					request.postRequest(HttpConst.Request.ADD_LINKMAN, json);
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
				CustomDialog.dismissDialog();
				if (data == null) {
					ToastUtils.show(getContext(), "请求服务器失败");
					return;
				}else{
					try {
						JSONObject json = new JSONObject(data);
						if(json.get("errCode").equals(0)){
							((Activity)getContext()).setResult(Activity.RESULT_OK);
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

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.right_title_layout:
			String name = ((EditText)findViewById(R.id.name)).getText().toString();
			String mobile = ((EditText)findViewById(R.id.mobile)).getText().toString();
			String department = ((EditText)findViewById(R.id.department)).getText().toString();
			String post = ((EditText)findViewById(R.id.post)).getText().toString();
			String tel = ((EditText)findViewById(R.id.tel)).getText().toString();
			String fax = ((EditText)findViewById(R.id.fax)).getText().toString();
			String email = ((EditText)findViewById(R.id.email)).getText().toString();
			String address = ((EditText)findViewById(R.id.address)).getText().toString();
			String birth = ((EditText)findViewById(R.id.birth)).getText().toString();
			String remark = ((EditText)findViewById(R.id.remark)).getText().toString();
			if(checkParams(name, mobile)){
				linkman.setName(name);
				linkman.setMobile(mobile);
				linkman.setDepartment(department);
				linkman.setPost(post);
				linkman.setTel(tel);
				linkman.setFax(fax);
				linkman.setEmail(email);
				linkman.setAddress(address);
				linkman.setBirth(birth);
				linkman.setRemark(remark);
				linkman.setCompanyId(getBundle().getString(ContactsListPage.COMPANY_ID));
				addLinkman();
			}
			break;

		case R.id.add_device:
			PageActivity.openPageForResult(getContext(), MobileContactPage.class, IMPORT_CONTACT);
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			if (data!=null) {
				((EditText)findViewById(R.id.name)).setText(data.getStringExtra(CONTACT_NAME));
				((EditText)findViewById(R.id.mobile)).setText(data.getStringExtra(CONTACT_NUMBER));
			}
		}
	}

	
}
