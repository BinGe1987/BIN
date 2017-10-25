package com.kwaijian.facility.OldSource.pageview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Data;
import com.kwaijian.facility.DataCenter.Home.DataObj.Repair;
import com.kwaijian.facility.OldSource.tools.LoadImageManager;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.OldSource.tools.Utils;
import com.kwaijian.facility.OldSource.widget.AddImageDrawable;
import com.kwaijian.facility.OldSource.widget.CustomDialog;
import com.kwaijian.facility.OldSource.widget.PickImageUploadDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class RepairDetailPage extends Page implements RequestCallback {
    public static final String ORDER_ID = "orderId";
    public static final int ADD_SPAREPARTS = 12;
    public static final int UPDATE_SPAREPARTS = 13;
    public static final int GET_SPAREPARTS_LIST = 14;
    public static final int GET_FACILITY_LIST = 15;
    public static final String COMPANY = "company";
    public static final String CONTACT_ID = "contactId";
    public static final String CONTACT_NAME = "contactName";
    public static final String CONTACT_TEL = "contactTel";
    public static final String FACILITY_ID = "facilityId";
    public static final String FACILITY_NAME = "facilityName";
    public static final String SPAREPARTS_ID = "sparepartsId";
    public static final String SPAREPARTS_MODEL = "sparepartsModel";
    public static final String SPAREPARTS_NUM = "sparepartsNum";
    public static final String HISTORY_ORDER = "historyOrder";
    private Repair order;
    private Data mData;
    private Handler mHandler;
    private String httpFlag;
    private String imageUrl;
    private StringBuilder imageId;
    private int sparepartsOperator;
    private int sparepartsUpdatedIndex;
    private boolean havePickedDate = true;

    public RepairDetailPage(Context context) {
        super(context);
        mHandler = new Handler();

        mData = Data.getInstence().getInstence();
        if (order == null) {
//            CustomDialog.createLoadingDialog(getContext()).show();
        }
        setContentView(R.layout.page_repair_detail_info);

        initView();
    }

    public void initView() {
        ((TextView) findViewById(R.id.title)).setText("维修详情");
        ((TextView) findViewById(R.id.images)).setText("故障图片");
        findViewById(R.id.save).setVisibility(View.GONE);
        findViewById(R.id.add_image).setVisibility(View.GONE);
        findViewById(R.id.get_report).setOnClickListener(this);
        findViewById(R.id.check_time).setOnClickListener(this);
        findViewById(R.id.repair_time).setOnClickListener(this);
        findViewById(R.id.test_time).setOnClickListener(this);

        ImageView image = (ImageView) findViewById(R.id.add_image);
        image.setImageDrawable(new AddImageDrawable());
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickImageUploadDialog(getContext()).show();
            }
        });


        final String repairId = getBundle().getString(ORDER_ID);
        for (int i = 0; i < mData.getRepairlist().size(); i++) {
            Repair r = mData.getRepairlist().get(i);
            if (r.getOrderNumber().equals(repairId)) {
                order = r;
                break;
            }
        }
        new Thread(new Runnable() {
            public void run() {
                httpFlag = HttpConst.Request.GET_REPAIR_DETAIL;
                HttpRequest request = new HttpRequest(RepairDetailPage.this);
                try {
                    JSONObject json = new JSONObject().put("repair_id", order.getId());
                    request.postRequest(HttpConst.Request.GET_REPAIR_DETAIL, json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!(resultCode == Activity.RESULT_OK)) {
            return;
        }
        if (requestCode == GET_FACILITY_LIST) {
            TextView facility = (TextView) findViewById(R.id.device_info);
            facility.setText(data.getStringExtra(FACILITY_NAME));
            facility.setTag(data.getStringExtra(FACILITY_ID));
            ((ViewGroup) findViewById(R.id.container)).removeAllViews();
        } else if (requestCode == GET_SPAREPARTS_LIST) {
            ViewGroup rootView = (ViewGroup) findViewById(R.id.container);
            initSparepartsList(data.getStringExtra(SPAREPARTS_ID), data.getStringExtra(SPAREPARTS_MODEL), null, null, null,
                    rootView);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initSparepartsList(String id, String oldModel, String oldSN, String newModel,
                                    String newSN, ViewGroup rootView) {
        View container;
        final TextView mOldModel;
        if (sparepartsOperator == ADD_SPAREPARTS) {
            container = LayoutInflater.from(getContext()).inflate(R.layout.company_spareparts_item, null);
            container.setId(Integer.parseInt(id));
//			selectedSpareparts.add(id);
            rootView.addView(container);
        } else {
//			selectedSpareparts.remove(String.valueOf(sparepartsUpdatedIndex));
//			selectedSpareparts.add(id);
            container = findViewById(R.id.container).findViewById(sparepartsUpdatedIndex);
        }
        EditText mOldSN = (EditText) container.findViewById(R.id.old_company_spareparts_SN);
        EditText mNewModel = (EditText) container.findViewById(R.id.new_company_spareparts_model);
        EditText mNewSN = (EditText) container.findViewById(R.id.new_company_spareparts_SN);
        mOldSN.setText(oldSN);
        mNewModel.setText(newModel);
        mNewSN.setText(newSN);
        mOldModel = (TextView) container.findViewById(R.id.old_company_spareparts_model);
        mOldModel.setText(oldModel);
//		mOldModel.setTag(id);
        /*mOldModel*/
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sparepartsOperator = UPDATE_SPAREPARTS;
                sparepartsUpdatedIndex = /*Integer.parseInt((String) mOldModel.getTag())*/v.getId();
//				chooseSpareparts();
            }
        });
    }


    @Override
    public void callback(final String data) {
        LogUtils.d("orderdetail-----" + data);
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
                        if (httpFlag.equals(HttpConst.Request.GET_REPAIR_DETAIL)) {
                            setViewText(json);
                        } else if (httpFlag.equals(HttpConst.Request.UPDATE_REPAIR)) {
                            finish();
                        } else if (httpFlag.equals(HttpConst.Request.DOWNLOAD_IMAGE)) {
                            File image = new File(Environment.getExternalStorageDirectory(), "Facility/image/" + json.getString("imageId") + ".png");
                            LoadImageManager.addImage(getContext(), image.toString(), (ViewGroup) findViewById(R.id.add_image).getParent());
                        }
                    } else {
                        ToastUtils.show(getContext(), (String) json.get("errMsg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setViewText(JSONObject json) throws JSONException {
        order = new Repair();
        order.setData(json.getJSONObject("repair"));
        if (order.getApplyCompany() != null) {
            ((TextView) findViewById(R.id.company)).setText(order.getApplyCompany().getName());
        }
        if (order.getFinalCompany() != null) {
            ((TextView) findViewById(R.id.final_company)).setText(order.getFinalCompany().getName());
        }
        ((TextView) findViewById(R.id.instock_time)).setText(Utils.formatTime(order.getInstockTime()));
        ((TextView) findViewById(R.id.r_brand)).setText(order.getBrand());
        ((TextView) findViewById(R.id.no)).setText(order.getON());
        ((TextView) findViewById(R.id.sn)).setText(order.getSN());
        ((TextView) findViewById(R.id.fault_desc)).setText(order.getFaultDesc());

        if (!TextUtils.isEmpty(order.getCheckTime()) && !order.getCheckTime().equals("0")) {
            ((TextView)findViewById(R.id.check_time)).setText(Utils.formatTime(order.getCheckTime()));
        }
        if (!TextUtils.isEmpty(order.getRepairTime()) && !order.getRepairTime().equals("0")) {
            ((TextView)findViewById(R.id.repair_time)).setText(Utils.formatTime(order.getRepairTime()));
        }
        if (!TextUtils.isEmpty(order.getTestTime()) && !order.getTestTime().equals("0")) {
            ((TextView)findViewById(R.id.test_time)).setText(Utils.formatTime(order.getTestTime()));
        }
		if (!TextUtils.isEmpty(order.getRepairDuration()) && !order.getRepairDuration().equals("0.0")) {
			((TextView)findViewById(R.id.service_time)).setText(order.getRepairDuration()+"小时");
		}

        ((EditText) findViewById(R.id.fault_reason)).setText(order.getFaultReason());
        ((EditText) findViewById(R.id.fault_symptom)).setText(order.getFaultSymptom());
        ((EditText) findViewById(R.id.fault_process)).setText(order.getFaultProcess());
        ((EditText) findViewById(R.id.fault_completion)).setText(order.getFaultCompletion());
//        ((TextView) findViewById(R.id.remark)).setText(order.getRemark());
        ((EditText) findViewById(R.id.record)).setText(order.getRecord());
        String[] mImageId = order.getFaultImage();
        File sdDir = new File(Environment.getExternalStorageDirectory(), "Facility/image/");
        if (!sdDir.exists()) {
            sdDir.mkdirs();
        }
        if (mImageId == null) {
            return;
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
                LoadImageManager.addImage(getContext(), image.toString(), (ViewGroup) findViewById(R.id.add_image).getParent());
            } else {
                CustomDialog.createLoadingDialog(getContext());
                httpFlag = HttpConst.Request.DOWNLOAD_IMAGE;
                final String id = mImageId[i];
                new Thread(new Runnable() {
                    public void run() {
                        HttpRequest request = new HttpRequest(RepairDetailPage.this);
                        request.downloadImage(id, image);
                    }
                }).start();

            }
        }
    }

    @Override
    public void onClick(View v) {
        Bundle bundle;
        super.onClick(v);
        switch (v.getId()) {
            case R.id.get_report:
                String record = ((TextView) findViewById(R.id.record)).getText().toString();
                String completion = ((TextView) findViewById(R.id.fault_completion)).getText().toString();
                String process = ((TextView) findViewById(R.id.fault_process)).getText().toString();
                String reason = ((TextView) findViewById(R.id.fault_reason)).getText().toString();
                String symptom = ((TextView) findViewById(R.id.fault_symptom)).getText().toString();
                updateOrder(symptom,reason, process, completion, record);
                break;
            case R.id.check_time:
                pickTime(findViewById(R.id.check_time));
                break;

            case R.id.repair_time:
                pickTime(findViewById(R.id.repair_time));
                break;
            case R.id.test_time:
                pickTime(findViewById(R.id.test_time));
                break;
            default:
                break;
        }
    }

    private void pickTime(final View textView) {
        Utils.pickDateTime(getContext(), new Utils.DateTimeCallback() {
            @Override
            public void onDataTimeCallback(String date) {
                ((TextView)textView).setText(date);
            }
        });
    }

    private void updateOrder(final String symptom,final String reason,
                             final String process, final String completion,final String record) {
        new Thread(new Runnable() {
            public void run() {
                httpFlag = HttpConst.Request.UPDATE_REPAIR;
                HttpRequest request = new HttpRequest(RepairDetailPage.this);
                JSONObject json;
                try {
					order.setFaultCompletion(completion);
					order.setRecord(record);
					order.setFaultProcess(process);
					order.setFaultReason(reason);
					order.setFaultSymptom(symptom);
                    TextView checkTime = (TextView) findViewById(R.id.check_time);
                    TextView repairTime = (TextView) findViewById(R.id.repair_time);
                    TextView testTime = (TextView) findViewById(R.id.test_time);
                    TextView serviceTime = (TextView) findViewById(R.id.service_time);
					order.setCheckTime(checkTime.getText().toString());
					order.setRepiarTime(repairTime.getText().toString());
					order.setTestTime(testTime.getText().toString());
					order.setRepairDuration(serviceTime.getText().toString());
					json = new JSONObject().put("repair", order.toJson());
					request.postRequest(HttpConst.Request.UPDATE_REPAIR, json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
