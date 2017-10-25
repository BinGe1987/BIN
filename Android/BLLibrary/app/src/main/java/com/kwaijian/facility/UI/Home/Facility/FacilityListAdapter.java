package com.kwaijian.facility.UI.Home.Facility;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwaijian.facility.DataCenter.Home.DataObj.Facility;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.R;
import com.kwaijian.facility.Utils.Log.LogUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FacilityListAdapter extends BaseAdapter {

    private List<Facility> mFacility = new ArrayList<>();

    public FacilityListAdapter() {
    }

    @Override
    public int getCount() {
        return mFacility == null ? 0 : mFacility.size();
    }

    @Override
    public Facility getItem(int position) {
        return mFacility.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_facility_item, null);
        }

        Facility facility = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(facility.getName());
        TextView model = (TextView) convertView.findViewById(R.id.model);
        model.setText(facility.getModel());

        ImageView iv = (ImageView) convertView.findViewById(R.id.image);
        String[] imageId = facility.getImageId();
        if (imageId != null && imageId.length > 0) {
            String url = HttpConst.Request.IMAGE_PATH + imageId[0];
            LogUtils.d("url : " + url);
            Picasso.with(iv.getContext()).load(url).centerCrop().resize(300, 300).into(iv);
        } else {
            iv.setImageResource(R.mipmap.icon_default_device);
        }

        return convertView;
    }

    public void update(List<Facility> list) {
        mFacility.clear();
        mFacility.addAll(list);
        notifyDataSetChanged();
    }

}
