package com.kwaijian.facility.UI.Home;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.kwaijian.facility.R;
import com.kwaijian.facility.UI.BaseClass.Activity.HTActivity;
import com.kwaijian.facility.UI.BaseClass.Presenter.HTPresenter;
import com.kwaijian.facility.UI.BaseClass.Views.HTFragment;
import com.kwaijian.facility.UI.BaseClass.Views.HTFragmentAdapter;
import com.kwaijian.facility.UI.BaseClass.Views.HTViewPager;
import com.kwaijian.facility.UI.Home.Facility.FacilityFragment;
import com.kwaijian.facility.UI.Home.Repair.RepairFragment;
import com.kwaijian.facility.UI.Home.Server.ServerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinGe on 2017/10/17.
 */

public class HomePresenter extends HTPresenter implements View.OnClickListener {

    private List<HTFragment> mFragments = new ArrayList<HTFragment>() {
        {
            add(new ServerFragment());
            add(new RepairFragment());
            add(new FacilityFragment());
        }
    };
    private ViewPager viewPager;

    public HomePresenter(Context context) {
        super(context);

        HTActivity activity = (HTActivity) context;
        HTViewPager viewPager = (HTViewPager) findViewById(R.id.fragment_viewpager);
        viewPager.setCanScroll(false);
        viewPager.setAdapter(new HTFragmentAdapter(activity.getSupportFragmentManager(), mFragments));
        viewPager.setOffscreenPageLimit(3);
        this.viewPager = viewPager;

        findViewById(R.id.order_service).setOnClickListener(this);
        findViewById(R.id.order_repair).setOnClickListener(this);
        findViewById(R.id.devices).setOnClickListener(this);

        setDrawableBound(findViewById(R.id.order_service));
        setDrawableBound(findViewById(R.id.order_repair));
        setDrawableBound(findViewById(R.id.devices));

    }

    private void setDrawableBound(View view) {
//        TextView tv = (TextView)view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_service:
                viewPager.setCurrentItem(0, false);
                changeTabStatus(findViewById(R.id.order_service), R.mipmap.tab1_d, R.color.color_tab_d);
                changeTabStatus(findViewById(R.id.order_repair), R.mipmap.tab4_u, R.color.color_tab_u);
                changeTabStatus(findViewById(R.id.devices), R.mipmap.tab3_u, R.color.color_tab_u);
                break;
            case R.id.order_repair:
                viewPager.setCurrentItem(1, false);
                changeTabStatus(findViewById(R.id.order_service), R.mipmap.tab1_u, R.color.color_tab_u);
                changeTabStatus(findViewById(R.id.order_repair), R.mipmap.tab4_d, R.color.color_tab_d);
                changeTabStatus(findViewById(R.id.devices), R.mipmap.tab3_u, R.color.color_tab_u);
                break;
            case R.id.devices:
                viewPager.setCurrentItem(2, false);
                changeTabStatus(findViewById(R.id.order_service), R.mipmap.tab1_u, R.color.color_tab_u);
                changeTabStatus(findViewById(R.id.order_repair), R.mipmap.tab4_u, R.color.color_tab_u);
                changeTabStatus(findViewById(R.id.devices), R.mipmap.tab3_d, R.color.color_tab_d);
                break;
        }
    }

    private void changeTabStatus(View view, int mipmapId, int colorId) {
        TextView tv = (TextView)view;
        tv.setTextColor(view.getResources().getColor(colorId));
        tv.setCompoundDrawablesWithIntrinsicBounds(0,mipmapId,0,0);
    }

}
