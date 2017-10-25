package com.kwaijian.facility.UI.BaseClass.Views;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinGe on 2017/10/19.
 */

public class MenuListView extends PopupWindow {

    private List<String> menus = new ArrayList<>();
    private ContentView mRootView;
    private LinearLayout mMenuLayout;

    public MenuListView(Context context) {
        super(context);
        mRootView = new ContentView(context);
        setContentView(mRootView);

        mMenuLayout = new LinearLayout(context);
        mMenuLayout.setOrientation(LinearLayout.VERTICAL);
        mRootView.addView(mMenuLayout);
    }

    public void addItem(String item) {
        menus.add(item);
    }

    class ContentView extends LinearLayout {

        public ContentView(Context context) {
            super(context);
            setOrientation(LinearLayout.VERTICAL);
        }
    }

    public interface OnMenuItemClicklistener {
        public void onMenuItemClick(int position);
    }

}
