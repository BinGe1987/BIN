package com.kwaijian.facility.DataCenter.Home.DataObj;

import com.kwaijian.facility.DataCenter.BaseClasses.HTData;

import org.json.JSONException;
import org.json.JSONObject;

public class Repair extends HTData {

    public final static int STATUS_UNDOME = 1;
    public final static int STATUS_DOME = 0;

    public String id;
    public String repair_id;//维修工单id
    public String r_orderNumber;//工单单号
    public String user_id;//：客户id
    public String engineer_id;//：工程师id
    public String apply_company_id;//：申请维修客户(企业)id
    public String final_company_id;//：最终客户(企业)id
    public String instock_time;//：入库时间
    public String r_brand;//：品牌
    public String r_ON;//：订货号
    public String r_SN;//：序列号
    public String[] fault_image;//：入库图片
    public String fault_desc;//：故障描述
    public String r_remark;//：工单备注
    public String check_time;//：检测时间
    public String fault_symptom;//：故障现象
    public String fault_reason;//：故障原因
    public String repair_time;//：维修时间
    public String fault_process;//：故障处理过程
    public String fault_completion;//：故障完成情况
    public String test_time;//：测试时间
    public String repair_duration;//：工作(维修)时长：单位(小时)，小数点后一位
    public String r_record;//：工程师备注
    public String outstock_time;//：出库时间
    public String express_company;//：快递公司
    public String express_no;//：快递单号
    public String create_time;//：派单时间
    public String repair_status;//工单状态

    public Company applyCompany;
    public Company finalCompany;


    @Override
    public void setData(JSONObject json) {
        super.setData(json);

        id = stringByKey("id");
        repair_id = stringByKey("repair_id");//：维修工单id
        repair_status = stringByKey("repair_status");
        r_orderNumber = stringByKey("r_orderNumber");//：工单单号
        user_id = stringByKey("user_id");//：客户id
        engineer_id = stringByKey("engineer_id");//：工程师id
        apply_company_id = stringByKey("apply_company_id");//：申请维修客户(企业)id
        final_company_id = stringByKey("final_company_id");//：最终客户(企业)id
        instock_time = stringByKey("instock_time");//：入库时间
        r_brand = stringByKey("r_brand");//：品牌
        r_ON = stringByKey("r_ON");//：订货号
        r_SN = stringByKey("r_SN");//：序列号

        if (stringByKey("fault_image") != null) {
            fault_image = stringByKey("fault_image").split(",");
        }

        fault_desc = stringByKey("fault_desc");//：故障描述
        r_remark = stringByKey("r_remark");//：工单备注
        check_time = stringByKey("check_time");//：检测时间
        fault_symptom = stringByKey("fault_symptom");//：故障现象
        fault_reason = stringByKey("fault_reason");//：故障原因
        repair_time = stringByKey("repair_time");//：维修时间
        fault_process = stringByKey("fault_process");//：故障处理过程
        fault_completion = stringByKey("fault_completion");//：故障完成情况
        test_time = stringByKey("test_time");//：测试时间
        repair_duration = stringByKey("repair_duration");//：工作(维修)时长：单位(小时)，小数点后一位
        r_record = stringByKey("r_record");//：工程师备注
        outstock_time = stringByKey("outstock_time");//：出库时间
        express_company = stringByKey("express_company");//：快递公司
        express_no = stringByKey("express_no");//：快递单号
        create_time = stringByKey("create_time");//：派单时间

        try {
            if (stringByKey("apply_company") != null && !stringByKey("apply_company").equals("null")) {        //居然返回了字符串“null”
                applyCompany = new Company();
                applyCompany.setData(new JSONObject(stringByKey("apply_company")));
            }
        } catch (Exception e) {
        }
        try {
            if (stringByKey("final_company") != null && !stringByKey("final_company").equals("null")) {
                finalCompany = new Company();
                finalCompany.setData(new JSONObject(stringByKey("final_company")));
            }
        }catch (Exception e) {
        }

    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
		try {
			json.put("repair_id", id)

				.put("r_record", r_record)
				.put("fault_symptom", fault_symptom)
				.put("fault_reason", fault_reason)
				.put("fault_process", fault_process)
				.put("fault_completion", fault_completion)

				.put("check_time", check_time)
				.put("repair_time", repair_time)
				.put("test_time", test_time)
				.put("repair_duration", repair_duration);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return json;
    }

    public String getId() {
        return id;
    }

    public Company getApplyCompany() {
        return applyCompany;
    }

    public Company getFinalCompany() {
        return finalCompany;
    }

    public String getRepairId() {
        return repair_id;
    }

    public String getOrderNumber() {
        return r_orderNumber;
    }

    public String getUserId() {
        return user_id;
    }

    public String getEngineerId() {
        return engineer_id;
    }

    public String getApplyCompanyId() {
        return apply_company_id;
    }

    public String getFinalCompanyId() {
        return final_company_id;
    }

    public String getInstockTime() {
        return instock_time;
    }

    public String getBrand() {
        return r_brand;
    }

    public String getON() {
        return r_ON;
    }

    public String getSN() {
        return r_SN;
    }

    public String[] getFaultImage() {
        return fault_image;
    }

    public String getFaultDesc() {
        return fault_desc;
    }

    public String getRemark() {
        return r_remark;
    }

    public String getCheckTime() {
        return check_time;
    }

    public String getFaultSymptom() {
        return fault_symptom;
    }

    public String getFaultReason() {
        return fault_reason;
    }

    public String getRepairTime() {
        return repair_time;
    }

    public String getFaultProcess() {
        return fault_process;
    }

    public String getFaultCompletion() {
        return fault_completion;
    }

    public String getTestTime() {
        return test_time;
    }

    public String getRepairDuration() {
        return repair_duration;
    }

    public String getRecord() {
        return r_record;
    }

    public String getOutstockTime() {
        return outstock_time;
    }

    public String getExpressCompany() {
        return express_company;
    }

    public String getExpressNo() {
        return express_no;
    }

    public String getCreateTime() {
        return create_time;
    }

    public boolean isFinish() {
        return repair_status != null && repair_status.equals("0");
    }


    public void setCheckTime(String check_time) {
        this.check_time = check_time;
    }

    public void setFaultSymptom(String fault_symptom) {
        this.fault_symptom = fault_symptom;
    }

    public void setRepiarTime(String repair_time) {
        this.repair_time = repair_time;
    }

    public void setFaultReason(String fault_reason) {
        this.fault_reason = fault_reason;
    }

    public void setFaultProcess(String fault_process) {
        this.fault_process = fault_process;
    }

    public void setFaultCompletion(String fault_completion) {
        this.fault_completion = fault_completion;
    }

    public void setTestTime(String test_time) {
        this.test_time = test_time;
    }

    public void setFaultDesc(String fault_desc) {
        this.fault_desc = fault_desc;
    }

    public void setRecord(String r_record) {
        this.r_record = r_record;
    }

    public void setRemark(String r_remark) {
        this.r_remark = r_remark;
    }

    public void setRepairDuration(String repair_duration) {
        this.repair_duration = repair_duration;
    }
}
