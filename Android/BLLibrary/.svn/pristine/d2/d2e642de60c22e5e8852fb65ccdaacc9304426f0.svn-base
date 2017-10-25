package com.kwaijian.facility.UI.Home.Old;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kwaijian.facility.DataCenter.Home.DataObj.Spare;
import com.kwaijian.facility.OldSource.tools.Density;

import java.util.List;

public class CompanySparepartsListAdapter extends BaseAdapter {

	private List<Spare> mSpareparts;

	public CompanySparepartsListAdapter(List<Spare> list) {
		mSpareparts = list;
	}

	@Override
	public int getCount() {
		return mSpareparts == null ? 0 : mSpareparts.size();
		// return 0;
	}

	@Override
	public Spare getItem(int position) {
		return mSpareparts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		TextView item = null;
		if (convertView == null) {
			item = new TextView(parent.getContext());
			item.setPadding(Density.dip2px(10), Density.dip2px(10), Density.dip2px(10), Density.dip2px(10));
			convertView = item;
		} else {
			item = (TextView) convertView;
		}
		item.setText(getItem(position).getModel());
		return convertView;
	}

}
