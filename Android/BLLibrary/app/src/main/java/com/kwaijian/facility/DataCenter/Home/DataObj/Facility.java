package com.kwaijian.facility.DataCenter.Home.DataObj;

import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.OldSource.http.HttpConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Facility extends HTData implements Serializable {
    private static final long serialVersionUID = 1570669526438120937L;

    public final static int STATUS_UNDOME = 0;
    public final static int STATUS_DOME = 1;

    public String id;
    public String name;
    public String manufacturer;
    public String model;
    public String controllerBrand;
    public String controllerType;
    public String[] imageId;
    public String remark;
    public List<Spare> spareparts;
    public int status = STATUS_UNDOME;
    public String fSN;

    ///////company->facility中的参数
    public String ON;        //订单号
    public String SN;        //序列号

    public Facility() {
    }

    public Facility(String name, String manufacturer, String model, String controllerBrand,
                    String[] imageId, String remark) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.model = model;
        this.controllerBrand = controllerBrand;
        this.imageId = imageId;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(JSONObject json) {
        super.setData(json);
        id = stringByKey("id");
        name = stringByKey(HttpConst.Facility.NAME);
        manufacturer = stringByKey(HttpConst.Facility.MANUFACTURER);
        model = stringByKey(HttpConst.Facility.MODEL);
        controllerBrand = stringByKey(HttpConst.Facility.CONTROLLER_BRAND);
        controllerType = stringByKey(HttpConst.Facility.CONTROLLER_TYPE);
        fSN = stringByKey("f_SN");
        imageId = stringByKey(HttpConst.Facility.IMAGE, "").split(",");
        remark = stringByKey(HttpConst.Facility.REMARK);
        ON = stringByKey("cf_ON");
        SN = stringByKey("cf_SN");
        spareparts = new ArrayList<>();
        try {
            if (stringByKey(HttpConst.Facility.SPAREPARTS) != null) {
                JSONArray jsonArray = new JSONArray(stringByKey(HttpConst.Facility.SPAREPARTS));
                for (int i = 0; i < jsonArray.length(); i++) {
                    Spare sparepart = new Spare();
                    sparepart.setData(jsonArray.getJSONObject(i));
                    spareparts.add(sparepart);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject facilityToJson() {
        JSONObject json = new JSONObject();
        try {
            json.put(HttpConst.Facility.NAME, name)
                    .put(HttpConst.Facility.ID, id)
                    .put(HttpConst.Facility.MANUFACTURER, manufacturer)
                    .put(HttpConst.Facility.MODEL, model)
                    .put(HttpConst.Facility.CONTROLLER_BRAND, controllerBrand)
                    .put(HttpConst.Facility.REMARK, remark)
                    .put(HttpConst.Facility.STATUS, status)            //company_facility中传1，facility中传0
                    .put("f_SN", fSN)
                /*.put("cf_ON", ON)
				.put("cf_SN", SN)*/;
            if (imageId != null) {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < imageId.length; i++) {
                    jsonArray.put(imageId[i]);
                }
                json.put(HttpConst.Facility.IMAGE, jsonArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getControllerBrand() {
        return controllerBrand;
    }

    public String getControllerType() {
        return controllerType;
    }

    public String[] getImageId() {
        return imageId;
    }

    public String getRemark() {
        return remark;
    }

    public List<Spare> getSpareparts() {
        return spareparts;
    }

    public String getON() {
        return ON;
    }

    public String getSN() {
        return SN;
    }

    public void setON(String oN) {
        ON = oN;
    }

    public void setSN(String sN) {
        SN = sN;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getfSN() {
        return fSN;
    }

    public void setfSN(String fSN) {
        this.fSN = fSN;
    }


}
