package com.kwaijian.facility.UI.Home.Facility;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kwaijian.facility.DataCenter.Home.DataObj.Facility;
import com.kwaijian.facility.R;
import com.kwaijian.facility.UI.BaseClass.Activity.HTActivity;
import com.kwaijian.facility.UI.BaseClass.Views.TitleView;
import com.kwaijian.facility.UI.BaseClass.Widget.SingleLineEditView;

/**
 * Created by BinGe on 2017/10/24.
 */

public class AddFacilityActivity extends HTActivity {

    private Facility facility;

    private SingleLineEditView devicesEdit, manufacturerEidt, modelEidt, controllerBrandEdit, snEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_add);
        facility = (Facility) getIntent().getSerializableExtra(Facility.class.getName());
        initTitle();
        initViews();
    }

    private void initTitle() {
        TitleView titleView = (TitleView) findViewById(R.id.title);
        titleView.setUnit(TitleView.Unit.BACK | TitleView.Unit.TEXT | TitleView.Unit.SURE);
        if (facility == null) {
            titleView.setTitle("添加设备");
        } else {
            titleView.setTitle("修改设备");
        }

    }

    private void initViews() {
        devicesEdit = (SingleLineEditView) findViewById(R.id.facility_name);
        devicesEdit.setEditable(true);
        devicesEdit.setHint("请输入设备名称");
        devicesEdit.setName("设备名称：");
        manufacturerEidt = (SingleLineEditView) findViewById(R.id.facility_manufacturer);
        manufacturerEidt.setEditable(true);
        manufacturerEidt.setHint("请输入生产商家");
        manufacturerEidt.setName("生产商家：");
        modelEidt = (SingleLineEditView) findViewById(R.id.facility_model);
        modelEidt.setEditable(true);
        modelEidt.setHint("请输入设备型号");
        modelEidt.setName("型号：");
        controllerBrandEdit = (SingleLineEditView) findViewById(R.id.facility_controller_brand);
        controllerBrandEdit.setEditable(true);
        controllerBrandEdit.setHint("请输入设备控制器品牌");
        controllerBrandEdit.setName("品牌：");
        snEdit = (SingleLineEditView) findViewById(R.id.facility_sn);
        snEdit.setEditable(true);
        snEdit.setHint("请输入设备序列号");
        snEdit.setName("序列号：");
    }
}
