package com.kwaijian.facility.UI.Home.Server.Detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.Config.Operations;
import com.kwaijian.facility.DataCenter.DataCenter;
import com.kwaijian.facility.DataCenter.Home.DataObj.Company;
import com.kwaijian.facility.DataCenter.Home.DataObj.Facility;
import com.kwaijian.facility.DataCenter.Home.DataObj.Spare;
import com.kwaijian.facility.OldSource.tools.Density;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.R;
import com.kwaijian.facility.UI.BaseClass.Activity.HTActivity;
import com.kwaijian.facility.UI.BaseClass.Views.TitleView;
import com.kwaijian.facility.UI.Home.Old.OrderDetailPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binge on 2017/10/22.
 */

public class SpareSelectActivity extends HTActivity {

    private Company company;
    private Facility facility;
    private CompanySpareAdapter adapter = new CompanySpareAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_select);
        company = (Company) getIntent().getSerializableExtra("company");
        facility = (Facility) getIntent().getSerializableExtra("facility");
        initView();
        refreshSpareList();
    }


    private void initView() {
        TitleView titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("备件列表");
        titleView.setUnit(TitleView.Unit.TEXT | TitleView.Unit.BACK | TitleView.Unit.ADD);
        titleView.setAddOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle bundle = getBundle();
//                String companyId = bundle.getString(COMPANY_ID);
//                String facilityId = bundle.getString(FACILITY_ID);
//                Bundle b = new Bundle();				//为了不搞混淆,另new一个bundle，使用CompanySparepartAddPage中的常量作为key
//                b.putString(CompanySparepartAddPage.COMPANY_ID, companyId);
//                b.putString(CompanySparepartAddPage.FACILITY_ID, facilityId);
//                PageActivity.openPageForResult(getContext(), CompanySparepartAddPage.class, b, ADD_COMPANY_SPAREPARTS);

            }
        });

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                Spare spare = adapter.getItem(pos);
                Intent intent = new Intent();
                intent.putExtra("spare", spare);
//                bundle.putString(OrderDetailPage.SPAREPARTS_ID, spare.getId());
//                bundle.putString(OrderDetailPage.SPAREPARTS_MODEL, spare.getModel());
//                bundle.putString(OrderDetailPage.SPAREPARTS_NUM, spare.getNum());
//                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    private void refreshSpareList() {
        String companyId = company.getId();
        String facilityId = facility.getId();
        DataCenter.get().perform(Operations.Home.Server.RefreshSpareList,
                "{\"company_id\":\"" + companyId + "\",\"facility_id\":\"" + facilityId + "\"}",
                new DataCenter.Callback() {
                    @Override
                    public void onCallback(String operation, HTData data) {
                        if (data.isDataNormal()) {
                            List<Spare> list = new ArrayList<>();
                            JSONArray array = data.JSONArrayByKey("list_spareparts");
                            for (int i = 0; i < array.length(); i++) {
                                try {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    Spare spare = new Spare();
                                    spare.setData(jsonObject);
                                    list.add(spare);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.update(list);
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            refreshSpareList();
        }
    }

    class CompanySpareAdapter extends BaseAdapter {

        private List<Spare> mSpareList;

        public void update(List<Spare> list) {
            mSpareList = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mSpareList == null ? 0 : mSpareList.size();
        }

        @Override
        public Spare getItem(int position) {
            return mSpareList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            TextView item;
            if (convertView == null) {
                item = new TextView(parent.getContext());
                item.setBackgroundColor(Color.WHITE);
                item.setPadding(Density.dip2px(10), Density.dip2px(10), Density.dip2px(10), Density.dip2px(10));
                convertView = item;
            } else {
                item = (TextView) convertView;
            }
            Spare spare = getItem(position);
            item.setText(spare.getModel());
            return convertView;
        }
    }
}
