package com.kwaijian.facility.UI.Launch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.kwaijian.facility.BaseClasses.OS.SystemInfo;
import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.Config.Operations;
import com.kwaijian.facility.DataCenter.DataCenter;
import com.kwaijian.facility.UI.BaseClass.Activity.HTActivity;
import com.kwaijian.facility.UI.Home.HomeActivity;
import com.kwaijian.facility.UI.Home.Old.MainActivity;
import com.kwaijian.facility.UI.Login.LoginActivity;
import com.kwaijian.facility.R;

public class LaunchActivity extends HTActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        TextView bate = (TextView) findViewById(R.id.bate);
        bate.setText("V" + SystemInfo.VersionName);

        ViewGroup vg = (ViewGroup) findViewById(R.id.root);
        for (int i = 0; i < vg.getChildCount(); i++) {
            View view = vg.getChildAt(i);
            TranslateAnimation t = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_PARENT, 1,
                    Animation.RELATIVE_TO_SELF, 0
            );
            t.setDuration(600);
            t.setStartOffset(i * 200);
            view.startAnimation(t);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DataCenter.get().perform(Operations.Login.LoginWithAuto, null, new DataCenter.Callback() {
                    @Override
                    public void onCallback(String operation, HTData data) {
                        Intent intent = new Intent(LaunchActivity.this,
                                HomeActivity.class);
                        startActivity(intent);
                        if (!data.isDataNormal()) {
                            intent = new Intent(LaunchActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                        }
                        finish();
                        overridePendingTransition(0, 0);
                    }
                });
            }
        }, 1500);
    }

}
