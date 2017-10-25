package com.kwaijian.facility.OldSource.pageview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.tools.LogUtils;

public class Page extends FrameLayout implements OnClickListener {

    private Object order;

    public Page(Context context) {
        super(context);
        LogUtils.d(this + " onCreate");
    }

    public void setContentView(int layout) {
        LayoutInflater.from(getContext()).inflate(layout, this);
        View back = findViewById(R.id.back);
        if (back != null) {
            back.setOnClickListener(this);
        }
    }

    public Bundle getBundle() {
        Activity a = (Activity) getContext();
        Intent intent = a.getIntent();
        if (intent != null) {
            return intent.getBundleExtra("bundle");
        }
        return null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.d(this + " onActivityResult" + requestCode);
    }

    public void finish() {
        Activity a = (Activity) getContext();
        a.finish();
        a.overridePendingTransition(R.anim.back_in, R.anim.back_out);
    }

    public void onPause() {
        LogUtils.d(this + " onPause");
    }

    public void onResume() {
        LogUtils.d(this + " onResume");
    }

    public void onDestroy() {
        LogUtils.d(this + " onDestroy");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

}
