package com.kwaijian.facility.UI.BaseClass.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by LooooG on 2017/9/18.
 * Fragment适配器
 */
public class HTFragmentAdapter extends FragmentStatePagerAdapter {

    List<HTFragment> fragmentList;

    public HTFragmentAdapter(FragmentManager fm, List<HTFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }


    /**
     * 触发销毁对象以及重建对象
     *
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
