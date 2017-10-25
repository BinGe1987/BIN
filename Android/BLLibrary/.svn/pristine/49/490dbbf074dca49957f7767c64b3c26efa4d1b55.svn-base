package com.kwaijian.facility.UI.Home.Server;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kwaijian.facility.DataCenter.Home.DataObj.Order;

import java.util.ArrayList;
import java.util.List;

public class ServerListAdapter extends BaseAdapter {

	private List<Order> mOrders = new ArrayList<>();

	public ServerListAdapter() {
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
		ServerItem item;
		if (convertView == null) {
			item = new ServerItem(parent.getContext());
			convertView = item;
		} else {
			item = (ServerItem) convertView;
		}
		item.setOrder(mOrders.get(position));
		return convertView;
	}

	public void update(List<Order> list) {
		mOrders.clear();
		mOrders.addAll(list);
		notifyDataSetChanged();
	}

}
