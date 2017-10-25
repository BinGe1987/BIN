package com.kwaijian.facility.UI.Home.Old;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kwaijian.facility.DataCenter.Home.DataObj.Data;
import com.kwaijian.facility.DataCenter.Home.DataObj.Order;
import com.kwaijian.facility.UI.Home.Server.ServerItem;

import java.util.List;

public class OrderAdapter extends BaseAdapter {

	private List<Order> mOrders;

	public OrderAdapter(List<Order> list) {
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
		ServerItem item = null;
		if (convertView == null) {
			item = new ServerItem(parent.getContext());
			convertView = item;
		} else {
			item = (ServerItem) convertView;
		}
		item.setOrder(mOrders.get(position));
		return convertView;
	}

	public void update() {
		notifyDataSetChanged();
	}

}
