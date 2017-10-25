package com.kwaijian.facility.UI.Home.Repair;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kwaijian.facility.DataCenter.Home.DataObj.Repair;

import java.util.ArrayList;
import java.util.List;

public class RepairListAdapter extends BaseAdapter implements RepairItem.NotifyDataChange{

	private List<Repair> mList = new ArrayList<>();

	public RepairListAdapter() {
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		RepairItem item;
		if (convertView == null) {
			item = new RepairItem(parent.getContext());
			item.setNotifyDataChange(this);
			convertView = item;
		} else {
			item = (RepairItem) convertView;
		}
		item.setOrder(mList.get(position));
		return convertView;
	}

	public void update(List<Repair> list) {
		mList.clear();
		mList.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public void notifyDataChange() {
		notifyDataSetChanged();
	}

}
