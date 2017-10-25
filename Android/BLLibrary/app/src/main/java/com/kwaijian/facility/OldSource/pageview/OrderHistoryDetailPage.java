package com.kwaijian.facility.OldSource.pageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Order;
import com.kwaijian.facility.OldSource.tools.LoadImageManager;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.OldSource.tools.Utils;
import com.kwaijian.facility.OldSource.widget.CustomDialog;
import com.kwaijian.facility.UI.Home.Old.OrderDetailPage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class OrderHistoryDetailPage extends Page implements RequestCallback {
	private Order order;
	private com.kwaijian.facility.DataCenter.Home.DataObj.Data mData;
	private String httpFlag;
	private Handler mHandler;
	private StringBuilder imageId;

	public OrderHistoryDetailPage(Context context) {
		super(context);

		mData = com.kwaijian.facility.DataCenter.Home.DataObj.Data.getInstence().getInstence();
		mHandler = new Handler();
		setContentView(R.layout.page_order_detail_info);
		((TextView) findViewById(R.id.title)).setText("历史工单详情");
		findViewById(R.id.save).setVisibility(View.GONE);
		initView(context);

	}

	private void setViewText(JSONObject json) throws JSONException {
		order = new Order();
		order.setData(json.getJSONObject("service"));
		if (order.getApplyCompany() != null) {
			((TextView) findViewById(R.id.company)).setText(order.getApplyCompany().getName());
		}
		if (order.getFinalCompany() != null) {
			((TextView) findViewById(R.id.final_company)).setText(order.getFinalCompany().getName());
		}
		if (order.getApplyLinkman() != null) {
			((TextView) findViewById(R.id.contact)).setText(order.getApplyLinkman().getName());
			((TextView) findViewById(R.id.contact_tel)).setText(order.getApplyLinkman().getMobile());
		}
		if (order.getFinalLinkman() != null) {
			((TextView) findViewById(R.id.final_contact)).setText(order.getFinalLinkman().getName());
			((TextView) findViewById(R.id.final_contact_tel)).setText(order.getFinalLinkman().getMobile());
		}
		if (order.getFacility() != null) {
			((TextView) findViewById(R.id.device_info)).setText(order.getFacility().getName());
		}
		if (order.getSpareparts() != null) {
			for (int i = 0; i < order.getSpareparts().size(); i++) {
				Order.OrderSpare spareparts = order.getSpareparts().get(i);
				initSparepartsList(spareparts.getOldId(), spareparts.getOldModel(), spareparts.getOldSN(),
						spareparts.getNewModel(), spareparts.getNewSN(), (ViewGroup) findViewById(R.id.container));
			}
		}
		((EditText) findViewById(R.id.fault_reason)).setText(order.getFaultReason());
		((EditText) findViewById(R.id.fault_symptom)).setText(order.getFaultSymptom());
		((EditText) findViewById(R.id.fault_desc)).setText(order.getFaultDesc());
		((EditText) findViewById(R.id.fault_process)).setText(order.getFaultProcess());
		((EditText) findViewById(R.id.fault_completion)).setText(order.getFaultCompletion());
		((TextView) findViewById(R.id.remark)).setText(order.getRemark());
		((EditText) findViewById(R.id.record)).setText(order.getRecord());
		if (!order.getApplyTime().equals("0")) {
			((TextView) findViewById(R.id.apply_time)).setText(Utils.formatTime(order.getApplyTime()));
		}
		if (!order.getConfirmTime().equals("0")) {
			((TextView) findViewById(R.id.confirm_time)).setText(Utils.formatTime(order.getConfirmTime()));
		}
		if (!order.getContactTime().equals("0")) {
			((TextView) findViewById(R.id.contact_time)).setText(Utils.formatTime(order.getContactTime()));
		}
		if (!order.getDepartTime().equals("0")) {
			((TextView) findViewById(R.id.depart_time)).setText(Utils.formatTime(order.getDepartTime()));
		}
		if (!order.getArriveTime().equals("0")) {
			((TextView) findViewById(R.id.arrive_time)).setText(Utils.formatTime(order.getArriveTime()));
		}
		if (!order.getLeaveTime().equals("0")) {
			((TextView) findViewById(R.id.leave_time)).setText(Utils.formatTime(order.getLeaveTime()));
		}
		if (!order.getServiceTime().equals("0.0")) {
			((TextView) findViewById(R.id.service_time)).setText(order.getServiceTime() + "小时");
		}
		String[] mImageId = order.getFaultImage();
		File sdDir = new File(Environment.getExternalStorageDirectory(), "Facility/image/");
		if (!sdDir.exists()) {
			sdDir.mkdirs();
		}
		for (int i = 0; i < mImageId.length; i++) {
			if (TextUtils.isEmpty(mImageId[i])) {
				continue;
			}
			if (imageId == null) {
				imageId = new StringBuilder();
				imageId.append(mImageId[i]);
			} else {
				imageId.append("," + mImageId[i]);
			}
			final File image = new File(sdDir, mImageId[i] + ".png");
			if (image.exists()) {
				LoadImageManager.addImage(getContext(), image.toString(),
						(ViewGroup) findViewById(R.id.add_image).getParent());
			} else {
				CustomDialog.createLoadingDialog(getContext());
				httpFlag = HttpConst.Request.DOWNLOAD_IMAGE;
				final String id = mImageId[i];
				new Thread(new Runnable() {
					public void run() {
						HttpRequest request = new HttpRequest(OrderHistoryDetailPage.this);
						request.downloadImage(id, image);
					}
				}).start();

			}
		}
	}

	@SuppressLint("NewApi")
	public void initView(final Context context) {
		((TextView) findViewById(R.id.title)).setText("工单详情");
		((TextView) findViewById(R.id.images)).setText("故障图片");
		findViewById(R.id.save).setVisibility(View.GONE);
		findViewById(R.id.add_image).setVisibility(View.GONE);
		findViewById(R.id.add_company_spareparts).setVisibility(View.GONE);
		findViewById(R.id.add_image).setVisibility(View.GONE);
		findViewById(R.id.get_report).setVisibility(View.GONE);
		((TextView)findViewById(R.id.device_info)).setCompoundDrawables(null, null, null, null);
		EditText symptom = (EditText) findViewById(R.id.fault_symptom);
		symptom.setEnabled(false); 
//		symptom.setFocusable(false);
		((View)symptom.getParent()).setBackground(null);
		EditText reason = (EditText) findViewById(R.id.fault_reason);
		reason.setEnabled(false);
		((View)reason.getParent()).setBackground(null);
		EditText process = (EditText) findViewById(R.id.fault_process);
		process.setEnabled(false);
		((View)process.getParent()).setBackground(null);
		EditText completion = (EditText) findViewById(R.id.fault_completion);
		completion.setEnabled(false);
		((View)completion.getParent()).setBackground(null);
		TextView record = (TextView)findViewById(R.id.record);
		record.setText("暂无备注信息");
		record.setEnabled(false); 
		((View)record.getParent()).setBackground(null);
		((View)findViewById(R.id.depart_time).getParent()).setBackground(null);
		((View)findViewById(R.id.arrive_time).getParent()).setBackground(null);
		((View)findViewById(R.id.leave_time).getParent()).setBackground(null);
		EditText serviceTime = (EditText) findViewById(R.id.service_time);
		serviceTime.setEnabled(false);
		((View)serviceTime.getParent()).setBackground(null);
		
//		findViewById(R.id.facility).setClickable(false);
//		findViewById(R.id.depart_time).setClickable(false);
//		findViewById(R.id.arrive_time).setClickable(false);
//		findViewById(R.id.leave_time).setClickable(false);
		final int id = Integer.parseInt(getBundle().getString(OrderDetailPage.ORDER_ID));
		List<Order> historyOrders = mData.getHistoryOrderlist();
		for (int i = 0; i < historyOrders.size(); i++) {
			if (historyOrders.get(i).getId().equals(id)) {
				order = historyOrders.get(i);
				break;
			}
		}
		new Thread(new Runnable() {
			public void run() {
				httpFlag = HttpConst.Request.GET_SERVICE_DETAIL;
				HttpRequest request = new HttpRequest(OrderHistoryDetailPage.this);
				try {
					JSONObject json = new JSONObject().put("service_id", id);
					request.postRequest(HttpConst.Request.GET_SERVICE_DETAIL, json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@SuppressLint("NewApi")
	private void initSparepartsList(String id, String oldModel, String oldSN, String newModel, String newSN,
			ViewGroup rootView) {
		View container;
		final TextView mOldModel;
		container = LayoutInflater.from(getContext()).inflate(R.layout.company_spareparts_item, null);
		container.setId(Integer.parseInt(id));
		rootView.addView(container);
		EditText mOldSN = (EditText) container.findViewById(R.id.old_company_spareparts_SN);
		mOldSN.setEnabled(false); 
		mOldSN.setBackground(null);
		EditText mNewModel = (EditText) container.findViewById(R.id.new_company_spareparts_model);
		mNewModel.setEnabled(false);
		mNewModel.setBackground(null);
		EditText mNewSN = (EditText) container.findViewById(R.id.new_company_spareparts_SN);
		mNewSN.setEnabled(false);
		mNewSN.setBackground(null);
		
		mOldSN.setText(oldSN);
		mNewModel.setText(newModel);
		mNewSN.setText(newSN);
		mOldModel = (TextView) container.findViewById(R.id.old_company_spareparts_model);
		mOldModel.setText(oldModel);
		mOldModel.setTag(id);
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
				}
				try {
					JSONObject json = new JSONObject(data);
					if (json.get("errCode").equals(0)) {
						if (httpFlag.equals(HttpConst.Request.GET_SERVICE_DETAIL)) {
							setViewText(json);
						}
					} else {
						ToastUtils.show(getContext(), (String) json.get("errMsg"));
					}
				} catch (Exception e) {

				}
			}
		});

	}

}
