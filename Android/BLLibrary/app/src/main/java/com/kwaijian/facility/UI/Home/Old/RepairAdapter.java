package com.kwaijian.facility.UI.Home.Old;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kwaijian.facility.DataCenter.Home.DataObj.Data;
import com.kwaijian.facility.DataCenter.Home.DataObj.Repair;
import com.kwaijian.facility.UI.Home.Repair.RepairItem;

import java.util.List;


public class RepairAdapter extends BaseAdapter implements RepairItem.NotifyDataChange{

	private List<Repair> mOrders;

	public RepairAdapter(List<Repair> list) {
		mOrders = list;
	}

	@Override
	public int getCount() {
		return mOrders == null ? 0 : mOrders.size();
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
		RepairItem item = null;
		if (convertView == null) {
			item = new RepairItem(parent.getContext());
			item.setNotifyDataChange(this);
			convertView = item;
		} else {
			item = (RepairItem) convertView;
		}
		item.setOrder(mOrders.get(position));
		return convertView;
	}

	public void updata() {
		notifyDataSetChanged();
	}

	@Override
	public void notifyDataChange() {
		mOrders = Data.getInstence().getRepairlist();
		notifyDataSetChanged();
	}

}
