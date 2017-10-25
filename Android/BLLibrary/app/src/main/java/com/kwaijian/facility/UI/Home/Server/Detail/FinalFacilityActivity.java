package com.kwaijian.facility.UI.Home.Server.Detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.Config.Operations;
import com.kwaijian.facility.DataCenter.DataCenter;
import com.kwaijian.facility.DataCenter.Home.DataObj.Company;
import com.kwaijian.facility.DataCenter.Home.DataObj.Facility;
import com.kwaijian.facility.DataCenter.Home.DataObj.Order;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.OldSource.widget.CustomDialog;
import com.kwaijian.facility.R;
import com.kwaijian.facility.UI.BaseClass.Activity.HTActivity;
import com.kwaijian.facility.UI.BaseClass.Views.TitleView;
import com.kwaijian.facility.UI.Home.Old.OrderDetailPage;
import com.kwaijian.facility.UI.Home.Server.ServerItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binge on 2017/10/21.
 */

public class FinalFacilityActivity extends HTActivity {

    private Company company;
    private FacilityListAdapter adapter = new FacilityListAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        company = (Company) getIntent().getSerializableExtra("company");
        company = new Company();
        company.setData("{\"id\":\"3\"}");
        setContentView(R.layout.activity_server_facility_list);
        initView();
        refreshFacilityList();
    }

    public void initView() {

        TitleView titleView = (TitleView) findViewById(R.id.title);
        titleView.setUnit(TitleView.Unit.BACK | TitleView.Unit.TEXT | TitleView.Unit.MENU);
        titleView.setTitle("设备列表");
        titleView.setMenuOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PageActivity.openPageForResult(context, CompanyFacilityAddPage.class, getBundle(), ADD_COMPANY_FACILITY);
            }
        });

        final ListView facilities = ((ListView) findViewById(R.id.list));
        facilities.setAdapter(adapter);

//        facilities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
//                Facility facility = (Facility) facilities.getAdapter().getItem(pos);
//                Bundle bundle = new Bundle();
//                Intent intent = new Intent();
//                bundle.putString(OrderDetailPage.FACILITY_ID, facility.getId());
//                bundle.putString(OrderDetailPage.FACILITY_NAME, facility.getName());
//                intent.putExtras(bundle);
//                setResult(Activity.RESULT_OK, intent);
//                finish();
//
//            }
//        });
//        facilities.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long arg3) {
////                CustomDialog.createChooseDialog(context, "是否删除设备", "", new CustomDialog.ReturnResults() {
////                    @Override
////                    public void result(Object o) {
////                        if (o.toString().equals("yes")) {
////                            ToastUtils.show(context, "删除成功      " + o);
////                            deleteFacility(((Facility) facilities.getAdapter().getItem(pos)).getId());
////                        }
////                    }
////                }).show();
//                return true;
//            }
//        });
    }

    private void refreshFacilityList() {
        DataCenter.get().perform(Operations.Home.Server.RefreshFacilityList,
                "{\"company_id\":" + company.getId() + "}",
                new DataCenter.Callback() {
                    @Override
                    public void onCallback(String operation, HTData data) {
                        LogUtils.d("");
                        company.setFacilityListData(data);
                        adapter.update(company.facilityList);
                    }
                });
    }

    public void callback(final String data) {
        LogUtils.d(data);
        CustomDialog.dismissDialog();
//        mHandler.post(new Runnable() {
//            public void run() {
//                if (data == null) {
//                    ToastUtils.show(context, "请求服务器失败");
//                    return;
//                }
//                try {
//                    final JSONObject json = new JSONObject(data);
//                    if (json.get("errCode").equals(0)) {
////                        if (httpFlag.equals(HttpConst.Request.GET_FACILITY_LIST)) {
////                            ListView listView = (ListView) findViewById(R.id.list);
////                            JSONArray jsonArray = new JSONArray();
////                            String s = json.getString("list_facility");
////                            if (!TextUtils.isEmpty(s) && !s.equals("null")) {
////                                jsonArray = json.getJSONArray("list_facility");
////                            }
////                            Data.getInstence().getFacilities(jsonArray);
////                            List<Facility> facilities = Data.getInstence().getmFacilitylist();
////                            Bundle bundle = new Bundle();
////                            bundle.putString(COMPANY_ID, companyId);
////                            listView.setAdapter(new FacilityListAdapter(facilities, R.layout.company_facility_item, bundle));
////                        } else if (httpFlag.equals(HttpConst.Request.DELETE_FACILITY)) {
////                            getFacilities();
//////							CustomDialog.createLoadingDialog(context).show();
////                        }
//                    }
//                } catch (Exception e) {
//                    LogUtils.d(e.getMessage());
//                }
//            }
//        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            refreshFacilityList();
        }
    }

    class FacilityListAdapter extends BaseAdapter {

        private List<Facility> facilityList = new ArrayList<>();

        public FacilityListAdapter() {

        }

        @Override
        public int getCount() {
            return facilityList == null ? 0 : facilityList.size();
        }

        @Override
        public Facility getItem(int position) {
            return facilityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_facility_item, null);
            }

            final Facility facility = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(facility.getName());
            TextView model = (TextView) convertView.findViewById(R.id.model);
            model.setText(facility.getModel());

            ImageView iv = (ImageView) convertView.findViewById(R.id.image);
            String[] imageId = facility.getImageId();
            if (imageId != null && imageId.length > 0) {
                String url = HttpConst.Request.IMAGE_PATH + imageId[0];
                com.kwaijian.facility.Utils.Log.LogUtils.d("url : " + url);
                Picasso.with(iv.getContext()).load(url).centerCrop().resize(300, 300).into(iv);
            } else {
                iv.setImageResource(R.mipmap.icon_default_device);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("facility", facility);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });
            convertView.findViewById(R.id.detail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            return convertView;
        }

        public void update(List<Facility> list) {
            facilityList.clear();
            facilityList.addAll(list);
            notifyDataSetChanged();
        }
    }


}
