package com.kwaijian.facility.UI.BaseClass.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;


import com.kwaijian.facility.BaseClasses.OS.HTTheme;
import com.kwaijian.facility.UI.BaseClass.Presenter.HTPresenter;
import com.kwaijian.facility.Utils.UI.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinGe on 2017/8/31.
 * 所有Activity的基类
 */

public class HTActivity extends AnimActivity {

    /**
     * HTActivity的主题
     */
    private HTTheme mTheme = new HTTheme();
    /**
     * HTPresenter列表
     */
    private List<HTPresenter> mPresenters = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyTheme(getMyTheme());
    }

    /**
     * 应用主题
     *
     * @param theme
     */
    private void applyTheme(HTTheme theme) {
        if (theme != null) {
            ActivityUtils.setWindowStatusBarColor(getWindow(), theme.statusBarBackgroundColor);
            ActivityUtils.setDarkStatusIcon(this, theme.statusBarContentStyle == HTTheme.StatusBarContentStyle.BLACK);
        } else {
            ActivityUtils.setWindowStatusBarColor(getWindow(), Color.WHITE);
            ActivityUtils.setDarkStatusIcon(this, true);
        }
    }

    /**
     * Activity中跟通知栏，title相关的主题信息
     */
    protected HTTheme getMyTheme() {
        return mTheme;
    }

    /**
     * 添加Presenter
     *
     * @param presenter
     * @return
     */
    public boolean addPresenter(HTPresenter presenter) {
        mPresenters.add(presenter);
        return false;
    }

    /**
     * 删除Presenter
     *
     * @param presenter
     * @return
     */
    public boolean removePresenter(HTPresenter presenter) {
        presenter.onDestroy();
        mPresenters.remove(presenter);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (HTPresenter p : mPresenters) {
            p.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (HTPresenter p : mPresenters) {
            p.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (HTPresenter p : mPresenters) {
            p.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (HTPresenter p : mPresenters) {
            p.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (HTPresenter p : mPresenters) {
            p.onDestroy();
        }
        mPresenters.clear();
    }
}
