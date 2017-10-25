package com.kwaijian.facility.OldSource.pageview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kwaijian.facility.UI.Home.Old.ContactListAdapter;
import com.kwaijian.facility.UI.Home.Old.PageActivity;
import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Contacts;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.OldSource.widget.CustomDialog;
import com.kwaijian.facility.UI.Home.Old.OrderDetailPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactsListPage extends Page implements /* RemoveListener, */RequestCallback {
	public static final String COMPANY_ID = "company_id";
	public static final int ADD_CONTACT = 30;
	private Handler mHandler;

	public ContactsListPage(Context context) {
		super(context);
		setContentView(R.layout.page_contact_list);
		mHandler = new Handler();
		initView();
	}

	public void initView() {
		((TextView) findViewById(R.id.title)).setText(getContext().getString(R.string.contact_list));
		TextView add = (TextView) findViewById(R.id.save);
		add.setText(getContext().getString(R.string.add));
		findViewById(R.id.right_title_layout).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PageActivity.openPageForResult(getContext(), ContactAddPage.class, getBundle(), ADD_CONTACT);
			}
		});

		getContactList();

		final ListView listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				Contacts contacts = (Contacts) listView.getAdapter().getItem(position);
				intent.putExtra(OrderDetailPage.CONTACT_ID, contacts.getId());
				intent.putExtra(OrderDetailPage.CONTACT_NAME, contacts.getName());
				intent.putExtra(OrderDetailPage.CONTACT_TEL, contacts.getMobile());
				((Activity) getContext()).setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
	}

	private void getContactList() {
		CustomDialog.createLoadingDialog(getContext());
		new Thread(new Runnable() {
			public void run() {
				HttpRequest request = new HttpRequest(ContactsListPage.this);
				JSONObject json;
				try {
					json = new JSONObject().put("company_id", getBundle().getString(COMPANY_ID));
					request.postRequest(HttpConst.Request.GET_LINKMAN_LIST, json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/*
	 * @Override public void removeItem(RemoveDirection direction, int position,
	 * View v) { if (direction == RemoveDirection.LEFT) { ((LinearLayout)
	 * v).getChildAt(1).setVisibility(View.VISIBLE); } else { ((LinearLayout)
	 * v).getChildAt(1).setVisibility(View.GONE); }
	 * 
	 * }
	 */

	@Override
	public void callback(final String data) {
		LogUtils.d(data);
		mHandler.post(new Runnable() {
			public void run() {
				CustomDialog.dismissDialog();
				if (data == null) {
					ToastUtils.show(getContext(), "请求服务器失败");
					return;
				} else {
					JSONObject json;
					try {
						json = new JSONObject(data);
						if (json.get("errCode").equals(0)) {
							List<Contacts> list = new ArrayList<Contacts>();
							ListView listView = (ListView) findViewById(R.id.list);
							JSONArray linkmanArray = json.getJSONArray("list_company");
							for (int i = 0; i < linkmanArray.length(); i++) {
								Contacts linkman = new Contacts();
								linkman.setData(linkmanArray.getJSONObject(i));
								list.add(linkman);
							}
							listView.setAdapter(new ContactListAdapter(list));
						} else {
							ToastUtils.show(getContext(), (String) json.get("errMsg"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == ADD_CONTACT) {
			getContactList();
		}
	}

}
