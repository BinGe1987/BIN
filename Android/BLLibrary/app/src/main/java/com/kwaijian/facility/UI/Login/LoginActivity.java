package com.kwaijian.facility.UI.Login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.Config.Operations;
import com.kwaijian.facility.DataCenter.DataCenter;
import com.kwaijian.facility.DataCenter.User.UserData;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.UI.BaseClass.Activity.HTActivity;
import com.kwaijian.facility.UI.BaseClass.Views.HTProgress;
import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.OldSource.tools.Utils;
import com.kwaijian.facility.Utils.Localize.Saver;
import com.kwaijian.facility.Utils.UI.ActivityUtils;


import java.util.HashMap;

public class LoginActivity extends HTActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText userView = (EditText) findViewById(R.id.user);
        EditText pwdView = (EditText) findViewById(R.id.pwd);
        userView.setText(Saver.getString("username", ""));
        pwdView.setText(Saver.getString("password", ""));

        findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView userView = (TextView) findViewById(R.id.user);
        TextView pwdView = (TextView) findViewById(R.id.pwd);

        if (TextUtils.isEmpty(userView.getText())) {
            ToastUtils.show(this, "请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pwdView.getText())) {
            ToastUtils.show(this, "请输入密码");
            return;
        }
        Saver.set("username", userView.getText());
        Saver.set("password", pwdView.getText());

        if (Utils.isNetWork(this)) {
            login(userView.getText().toString(), pwdView.getText().toString());
        } else {
            ToastUtils.show(this, "网络错误");
        }
    }

    public void login(final String user, final String password) {
        HTProgress.create(this, "LoginLoading").setType(HTProgress.TYPE.LOADING).show();
        DataCenter.get().perform(Operations.Login.LoginWithPwd, new HashMap<String, String>() {{
            put("account", user);
            put("password", password);
        }}, new DataCenter.Callback() {
            @Override
            public void onCallback(String operation, HTData data) {
                LogUtils.d("operation:" + operation);
                UserData userData = (UserData) data;
                if (userData.isLogin()) {
                    ActivityUtils.dismissActivity(LoginActivity.this);
                } else {
                    TextView info = (TextView) findViewById(R.id.errorInfo);
                    info.setText("errCode:"+userData.errCode()+", "+userData.errInfo());
                }
                HTProgress.dismissAll();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
