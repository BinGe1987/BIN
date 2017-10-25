package com.kwaijian.facility.OldSource.pageview;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kwaijian.facility.R;

import java.util.ArrayList;
import java.util.List;

public class MobileContactPage extends Page {
	Context mContext = null;

	/** 获取库Phon表字段 **/
	private static final String[] PHONES_PROJECTION = new String[] { Phone.DISPLAY_NAME, Phone.NUMBER,
			/* Photo.PHOTO_ID, */Phone.CONTACT_ID };

	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;

	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;

	/** 头像ID **/
	// private static final int PHONES_PHOTO_ID_INDEX = 2;

	/** 联系人的ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 2;

	/** 联系人名称 **/
	private List<String> mContactsName = new ArrayList<String>();

	/** 联系人头像 **/
	private List<String> mContactsNumber = new ArrayList<String>();

	/** 联系人头像 **/
	// private List<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();

	public MobileContactPage(Context context) {
		super(context);
		setContentView(R.layout.page_mobile_contact);
		mContext = getContext();
		initView();
	}

	private void initView() {
		((TextView) findViewById(R.id.title)).setText(getContext().getString(R.string.mobile_contacts));
		findViewById(R.id.save).setVisibility(View.GONE);

		getPhoneContacts();
		getSIMContacts();

		ListView contacts = (ListView) findViewById(R.id.list);
		contacts.setAdapter(new MyAdapter());
		contacts.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				Intent intent = new Intent();
				intent.putExtra(ContactAddPage.CONTACT_NAME, mContactsName.get(pos));
				intent.putExtra(ContactAddPage.CONTACT_NUMBER, mContactsNumber.get(pos));
				((Activity) mContext).setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
	}

	/**得到手机通讯录联系人信息**/ 
	private void getPhoneContacts() {
		ContentResolver resolver = mContext.getContentResolver();

		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;

				// 得到联系人名称
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

				// 得到联系人ID
				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

				// 得到联系人头像ID
				// Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
				mContactsName.add(contactName);
				mContactsNumber.add(phoneNumber);
			}
			phoneCursor.close();
		}
	}

	/** 得到手机SIM卡联系人人信息 **/
	private void getSIMContacts() {
		ContentResolver resolver = mContext.getContentResolver();
		// 获取Sims卡联系人
		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

				// Sim卡中没有联系人头像
				mContactsName.add(contactName);
				mContactsNumber.add(phoneNumber);
			}

			phoneCursor.close();
		}
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mContactsNumber.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.mobile_contact_item, null);
				convertView.setFocusable(false);
			}
			((TextView) convertView.findViewById(R.id.contact)).setText(mContactsName.get(position));
			TextView tel = (TextView) convertView.findViewById(R.id.contact_tel);
			tel.setText(mContactsNumber.get(position));
			tel.setFocusable(false);
			return convertView;
		}

	}

}
