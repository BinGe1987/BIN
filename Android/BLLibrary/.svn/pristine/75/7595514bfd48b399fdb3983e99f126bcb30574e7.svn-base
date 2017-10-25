package com.kwaijian.facility.UI.Home;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kwaijian.facility.BaseClasses.OS.HTTheme;
import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.Config.Operations;
import com.kwaijian.facility.DataCenter.DataCenter;
import com.kwaijian.facility.DataCenter.Home.DataObj.Order;
import com.kwaijian.facility.OldSource.pageview.FacilityListPage;
import com.kwaijian.facility.OldSource.pageview.OrderListPage;
import com.kwaijian.facility.OldSource.pageview.Page;
import com.kwaijian.facility.OldSource.pageview.RepairListPage;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.R;
import com.kwaijian.facility.UI.BaseClass.Activity.HTActivity;
import com.kwaijian.facility.UI.BaseClass.Views.HTProgress;
import com.kwaijian.facility.UI.Login.LoginActivity;
import com.kwaijian.facility.Utils.Log.LogUtils;
import com.kwaijian.facility.Utils.UI.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends HTActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        setContentView(R.layout.activity_home);
        addPresenter(new HomePresenter(this));
    }

    @Override
    protected HTTheme getMyTheme() {
        HTTheme theme = new HTTheme();
        theme.statusBarBackgroundColor = getResources().getColor(R.color.color_1);
        theme.statusBarContentStyle = HTTheme.StatusBarContentStyle.Light;
        return theme;
    }

    @Override
    public void onBackPressed() {

        HTProgress.create(this,"exit").setType(HTProgress.TYPE.CONFIRM)
                .setTitle("是否退出APP")
                .setCancelButton("取消", null)
                .setSureButton("确定", new HTProgress.HTProgressCallback() {
                    @Override
                    public void callback() {
                        logOut();
                        finish();
                    }
                }).show();
    }

    public void logOut() {
//        ActivityUtils.presentationActivity(this, LoginActivity.class);
        DataCenter.get().perform(Operations.Login.LoginOut, null, null);
        DataCenter.get().perform(Operations.Home.ClearData, null, null);
    }
}
