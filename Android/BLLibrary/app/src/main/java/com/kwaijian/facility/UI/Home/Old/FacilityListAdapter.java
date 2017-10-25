package com.kwaijian.facility.UI.Home.Old;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Facility;
import com.kwaijian.facility.OldSource.pageview.CompanyFacilityAddPage;
import com.kwaijian.facility.OldSource.tools.BitmapManager;

import java.io.File;
import java.util.List;

public class FacilityListAdapter extends BaseAdapter implements RequestCallback{

	private List<Facility> mFacilitys;
	private int mLayoutId;
	private Bundle mBundle;
//	private ImageView iv;
	private Handler mHandler = new Handler();

	// public FacilityListAdapter(List<Facility> list) {
	// mFacilitys = list;
	// }

	public FacilityListAdapter(List<Facility> list, int layoutId) {
		mFacilitys = list;
		mLayoutId = layoutId;
	}

	public FacilityListAdapter(List<Facility> list, int layoutId, Bundle bundle) {
		mFacilitys = list;
		mLayoutId = layoutId;
		mBundle = bundle;
	}

	@Override
	public int getCount() {
		return mFacilitys == null ? 0 : mFacilitys.size();
		// return 0;
	}

	@Override
	public Facility getItem(int position) {
		return mFacilitys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		final Context context = parent.getContext();
		final Facility facility = getItem(position);
		if (mBundle == null) {		//FacilityListPage
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(mLayoutId, null);
			}
			TextView name = (TextView) convertView.findViewById(R.id.name);
			name.setText(facility.getName());
			TextView model = (TextView) convertView.findViewById(R.id.model);
			model.setText(facility.getModel());

			final ImageView iv = (ImageView) convertView.findViewById(R.id.image);
			iv.setImageResource(R.mipmap.icon_default_device);
			String[] imageId = facility.getImageId();
			if (imageId != null && imageId.length > 0 && !TextUtils.isEmpty(imageId[0])) {
				iv.setTag(imageId[0]);
				File sdDir = new File(Environment.getExternalStorageDirectory(), "Facility/image/");
				final File image = new File(sdDir, imageId[0] + ".png");
				if (image.exists()&&iv.getTag().equals(imageId[0])&& iv.getTag()!=null) {
					iv.setImageBitmap(BitmapManager.compressImage(image.toString()));
				}else {
//					CustomDialog.createLoadingDialog(context);
					final String id = imageId[0];
					new Thread(new Runnable() {
						public void run() {
							HttpRequest request = new HttpRequest(FacilityListAdapter.this);
							request.downloadImage(mHandler, id, image, iv);
						} 
					}).start();
				}
			}

		} else {					//CompanyFacilityListPage
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(mLayoutId, null);
			}
			TextView name = (TextView) convertView.findViewById(R.id.name);
			name.setText(facility.getName());
			((View) convertView.findViewById(R.id.detail).getParent()).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mBundle.putSerializable(CompanyFacilityAddPage.FACILITY_DETAIL, facility);
					PageActivity.openPageForResult(context, CompanyFacilityAddPage.class, mBundle, 1);
				}
			});
		}
		return convertView;
	}

	@Override
	public void callback(final String data) {
	}

}
