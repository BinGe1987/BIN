package com.kwaijian.facility.UI.BaseClass.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kwaijian.facility.UI.BaseClass.Views.Browser.HTImageBrowser;
import com.kwaijian.facility.Utils.UI.ActivityUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by BinGe on 2017/10/24.
 */

public class ImageBrowserActivity extends HTActivity {

    public static final String IMAGE_LIST = "image_list";
    public static final String IMAGE_INDEX = "image_index";

    public static void open(Context context, String[] list, int index) {
        Intent intent = new Intent(context, ImageBrowserActivity.class);
        intent.putStringArrayListExtra(IMAGE_LIST, new ArrayList<>(Arrays.asList(list)));
        intent.putExtra(IMAGE_INDEX, index);
        ActivityUtils.presentationActivity(context, intent);
    }

    public static void open(Context context, ArrayList<String> list, int index) {
        Intent intent = new Intent(context, ImageBrowserActivity.class);
        intent.putStringArrayListExtra(IMAGE_LIST, list);
        intent.putExtra(IMAGE_INDEX, index);
        ActivityUtils.presentationActivity(context, intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        List<String> list = intent.getStringArrayListExtra(IMAGE_LIST);
        int index = intent.getIntExtra(IMAGE_INDEX, 0);
        HTImageBrowser browser = new HTImageBrowser(ImageBrowserActivity.this);
        browser.setImageDatas(list);
        browser.setIndex(index);
        setContentView(browser);
    }


}
