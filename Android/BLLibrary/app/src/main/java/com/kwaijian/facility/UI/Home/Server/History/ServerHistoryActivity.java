package com.kwaijian.facility.UI.Home.Server.History;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.kwaijian.facility.Constants.EventConst;
import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.Config.DataType;
import com.kwaijian.facility.DataCenter.Config.Operations;
import com.kwaijian.facility.DataCenter.DataCenter;
import com.kwaijian.facility.DataCenter.Home.DataObj.Order;
import com.kwaijian.facility.DataCenter.Home.HomeData;
import com.kwaijian.facility.DataCenter.Home.Server.ServerData;
import com.kwaijian.facility.EventCenter.Event;
import com.kwaijian.facility.EventCenter.EventBus;
import com.kwaijian.facility.EventCenter.EventFilter;
import com.kwaijian.facility.EventCenter.EventHandler;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.R;
import com.kwaijian.facility.UI.BaseClass.Activity.HTActivity;
import com.kwaijian.facility.UI.BaseClass.Views.ActionSheet;
import com.kwaijian.facility.UI.BaseClass.Views.TitleView;
import com.kwaijian.facility.UI.Home.HomeActivity;
import com.kwaijian.facility.UI.Home.Server.ServerListAdapter;
import com.kwaijian.facility.Utils.UI.ActivityUtils;

import java.util.ArrayList;

/**
 * Created by BinGe on 2017/10/23.
 */

public class ServerHistoryActivity extends HTActivity implements EventHandler{

    private ServerListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_server);
        TitleView titleView = (TitleView) findViewById(R.id.title);
        titleView.setBackgroundColor(Color.WHITE);
        titleView.setTitle("历史记录");
        titleView.setUnit(TitleView.Unit.TEXT | TitleView.Unit.BACK);

        initListView();
        EventBus.getInstance().registerEvent(this, new EventFilter()
                .addFilter(EventConst.Home.OnServerHistoryListRefreshed)
        );

        DataCenter.get().perform(Operations.Home.Server.RefreshServerHistoryList, null, null);
    }

    private void initListView() {
        mAdapter = new ServerListAdapter();
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);

        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataCenter.get().perform(Operations.Home.Server.RefreshServerHistoryList, null, null);
            }
        });
    }

    @Override
    public void onEvent(Event event) {
        switch (event.event) {
            case EventConst.Home.OnServerHistoryListRefreshed:
                updateList();
                break;
        }
    }

    private void updateList() {
        HomeData data = (HomeData) DataCenter.get().getData(DataType.HomeData);
        ServerData serverData = data.serverData;
        mAdapter.update(serverData.history);

        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            ToastUtils.show(this, "刷新完成");
        }
    }
}
