package com.kwaijian.facility.DataCenter.Home.Repair;

import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.Home.DataObj.Order;
import com.kwaijian.facility.DataCenter.Home.DataObj.Repair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinGe on 2017/10/18.
 */

public class RepairData extends HTData {

    public List<Repair> list = new ArrayList<>();


    @Override
    public void onData() {
        list.clear();
        if (isDataNormal()) {
            JSONArray array = JSONArrayByKey("list_repair");
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    Repair item = new Repair();
                    item.setData(obj);
                    list.add(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int size() {
        return list.size();
    }

    public Repair getItem(int index) {
        if (index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    /**
     * 列表项
     */
    public class Item extends HTData {

        String id;
        String orderNumber;
        String engineerId;
        String applyCompanyId;
        String applyLinkManId;
        String finalCompanyId;
        String finalLinkManId;
        String facilityId;
        String faultDesc;
        String faultImage;
        String faultSymptom;
        String faultReason;
        String faultProcess;
        String faultCompletion;
        String remark;
        String record;
        String serviceStatus;
        String applyTime;
        String contactTime;
        String confirmTime;
        String departTime;
        String arriveTime;
        String leaveTime;
        String completeTime;
        String pushTime;
        String evaluateTime;
        String serviceTime;
        String confirmCompletionTime;
        String createTime;
        String updateTime;
        String status;
        String token;

        @Override
        public void onData() {
            id = stringByKey("id");
            orderNumber = stringByKey("s_orderNumber");
            engineerId = stringByKey("engineer_id");
            applyCompanyId = stringByKey("apply_company_id");
            applyLinkManId = stringByKey("apply_linkman_id");
            finalCompanyId = stringByKey("final_company_id");
            finalLinkManId = stringByKey("final_linkman_id");
            facilityId = stringByKey("facility_id");
            faultDesc = stringByKey("fault_desc");
            faultImage = stringByKey("fault_image");
            faultSymptom = stringByKey("fault_symptom");
            faultReason = stringByKey("fault_reason");
            faultProcess = stringByKey("fault_process");
            faultCompletion = stringByKey("fault_completion");
            remark = stringByKey("s_remark");
            record = stringByKey("s_record");
            serviceStatus = stringByKey("service_status");
            applyTime = stringByKey("apply_time");
            contactTime = stringByKey("contact_time");
            confirmTime = stringByKey("confirm_time");
            departTime = stringByKey("depart_time");
            arriveTime = stringByKey("arrive_time");
            leaveTime = stringByKey("leave_time");
            completeTime = stringByKey("complete_time");
            pushTime = stringByKey("push_time");
            evaluateTime = stringByKey("evaluate_time");
            serviceTime = stringByKey("service_time");
            confirmCompletionTime = stringByKey("confirm_completion_time");
            createTime = stringByKey("create_time");
            updateTime = stringByKey("update_time");
            status = stringByKey("status");
            token = stringByKey("token");
        }
    }

}
