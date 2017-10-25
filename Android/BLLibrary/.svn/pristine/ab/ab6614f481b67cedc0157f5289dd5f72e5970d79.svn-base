package com.kwaijian.facility.UI.Home.Facility;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kwaijian.facility.Constants.EventConst;
import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.Config.DataType;
import com.kwaijian.facility.DataCenter.Config.Operations;
import com.kwaijian.facility.DataCenter.DataCenter;
import com.kwaijian.facility.DataCenter.Home.DataObj.Facility;
import com.kwaijian.facility.DataCenter.Home.DataObj.Order;
import com.kwaijian.facility.DataCenter.Home.Facility.FacilityData;
import com.kwaijian.facility.DataCenter.Home.HomeData;
import com.kwaijian.facility.DataCenter.Home.Repair.RepairData;
import com.kwaijian.facility.EventCenter.Event;
import com.kwaijian.facility.EventCenter.EventBus;
import com.kwaijian.facility.EventCenter.EventFilter;
import com.kwaijian.facility.EventCenter.EventHandler;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.R;
import com.kwaijian.facility.UI.BaseClass.Views.ActionSheet;
import com.kwaijian.facility.UI.BaseClass.Views.HTFragment;
import com.kwaijian.facility.UI.BaseClass.Views.TitleView;
import com.kwaijian.facility.UI.Home.HomeActivity;
import com.kwaijian.facility.UI.Home.Repair.RepairListAdapter;
import com.kwaijian.facility.Utils.Log.LogUtils;
import com.kwaijian.facility.Utils.UI.ActivityUtils;

import java.util.ArrayList;

/**
 * Created by BinGe on 2017/10/18.
 */

public class FacilityFragment extends HTFragment implements EventHandler {

    public static final int ADD_FACILITY = 8;
    private FacilityListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_facility, container, false);

        initTitle();
        initListView();
        initEvent();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFocus()) {
            DataCenter.get().perform(Operations.Home.RefreshFacilityList, null, null);
            LogUtils.d("onResume:"+this);
        }
    }

    private void initTitle() {
        TitleView titleView = (TitleView) mView.findViewById(R.id.title);
        titleView.setTitle("设备");
        titleView.setColor(Color.WHITE);
        titleView.setUnit(TitleView.Unit.TEXT | TitleView.Unit.ADD);
        titleView.setAddOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.pushActivityForResult(getContext(), AddFacilityActivity.class, ADD_FACILITY);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_FACILITY) {

        }
    }

    private void initListView() {
        mAdapter = new FacilityListAdapter();
        ListView listView = (ListView) mView.findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);

        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataCenter.get().perform(Operations.Home.RefreshFacilityList, null, null);
            }
        });

        updateList();
    }

    private void initEvent() {
        EventBus.getInstance().registerEvent(this, new EventFilter()
                .addFilter(EventConst.Home.OnFacilityListRefreshed)
        );
    }

    @Override
    public void onFragmentFocus(boolean focus) {
        if (focus) {
            DataCenter.get().perform(Operations.Home.RefreshFacilityList, null, null);
        }
    }

    @Override
    public void onEvent(Event event) {
        switch (event.event) {
            case EventConst.Home.OnFacilityListRefreshed:
                updateList();
                break;
        }
    }

    private void updateList() {
        HomeData data = (HomeData) DataCenter.get().getData(DataType.HomeData);
        mAdapter.update(data.facilityData.list);

        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.refreshLayout);
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            ToastUtils.show(getContext(), "刷新完成");
        }
    }
}
