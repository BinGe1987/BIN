package com.kwaijian.facility.DataCenter.Home;


import com.kwaijian.facility.Constants.EventConst;
import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.BaseClasses.HTDataHandler;
import com.kwaijian.facility.DataCenter.BaseClasses.HTDataParser;
import com.kwaijian.facility.DataCenter.BaseClasses.HTOperationPerformer;
import com.kwaijian.facility.DataCenter.Config.Operations;
import com.kwaijian.facility.DataCenter.DataCenter;
import com.kwaijian.facility.DataCenter.Home.DataObj.Order;
import com.kwaijian.facility.DataCenter.Home.Facility.FacilityPerformer;
import com.kwaijian.facility.DataCenter.Home.Repair.HistoryRepairPerformer;
import com.kwaijian.facility.DataCenter.Home.Repair.RepairPerformer;
import com.kwaijian.facility.DataCenter.Home.Server.ServerPerformer;
import com.kwaijian.facility.EventCenter.EventBus;

import org.json.JSONException;

/**
 * Created by BinGe on 2017/9/15.d
 * 首页数据管理器
 */

public class HomeHandler extends HTDataHandler implements HTDataParser<HTData>, HTOperationPerformer{

    private HomeData data = new HomeData();

    public HomeHandler() {
        bindOperation(Operations.Home.ClearData, this);

        bindOperation(Operations.Home.Server.RefreshServerList, new ServerPerformer());
        bindOperation(Operations.Home.Server.RefreshServerHistoryList, new ServerPerformer());
        bindOperation(Operations.Home.Server.FinishServer, new ServerPerformer());
        bindOperation(Operations.Home.Server.ServerDetail, new ServerPerformer());
        bindOperation(Operations.Home.Server.RefreshFacilityList, new ServerPerformer());
        bindOperation(Operations.Home.Server.RefreshSpareList, new ServerPerformer());
        bindOperation(Operations.Home.Server.UpdateServer, new ServerPerformer());

        bindOperation(Operations.Home.RefreshRepairList, new RepairPerformer());
        bindOperation(Operations.Home.RefreshRepairHistoryList, new HistoryRepairPerformer());

        bindOperation(Operations.Home.RefreshFacilityList, new FacilityPerformer());




        bindDataParser(Operations.Home.Server.RefreshServerList, this);
        bindDataParser(Operations.Home.Server.RefreshServerHistoryList, this);
        bindDataParser(Operations.Home.Server.ServerDetail, this);

        bindDataParser(Operations.Home.RefreshRepairList, this);
        bindDataParser(Operations.Home.RefreshRepairHistoryList, this);
        bindDataParser(Operations.Home.RefreshFacilityList, this);
    }

    @Override
    public HTData getData() {
        return data;
    }

    @Override
    public HTData parse(String operation, HTData data) {
        switch (operation) {
            case Operations.Home.Server.RefreshServerList:
                this.data.setServerData(data);
                EventBus.getInstance().postEvent(EventConst.Home.OnServerListRefreshed, this.data);
                break;
            case Operations.Home.Server.RefreshServerHistoryList:
                this.data.setServerData(data);
                EventBus.getInstance().postEvent(EventConst.Home.OnServerHistoryListRefreshed, this.data);
                break;
            case Operations.Home.Server.ServerDetail:
                Order order = new Order();
                try {
                    order.setData(data.getJSONObject().getJSONObject("service"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return order;
            case Operations.Home.RefreshRepairList:
                this.data.setRepairData(data);
                EventBus.getInstance().postEvent(EventConst.Home.OnRepairListRefreshed, this.data);
                break;
            case Operations.Home.RefreshRepairHistoryList:
                this.data.setRepairData(data);
                break;
            case Operations.Home.RefreshFacilityList:
                this.data.setFacilityData(data);
                EventBus.getInstance().postEvent(EventConst.Home.OnFacilityListRefreshed, this.data);
                break;
        }
        return this.data;
    }

    @Override
    public HTData performOperation(String operation, Object params, DataCenter.Callback callback) {
        this.data.setData("{}");
        return data;
    }

    @Override
    public boolean isAsynchronous() {
        return false;
    }
}
