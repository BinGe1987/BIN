package com.kwaijian.facility.DataCenter.User;

import android.text.TextUtils;

import com.kwaijian.facility.BaseClasses.Objects.HTJSONObject;
import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.Utils.Log.LogUtils;

import org.json.JSONObject;

/**
 * Created by LooooG on 2017/9/7.
 * 用户数据
 */
public class UserData extends HTData {

    public String uid;          // 用户ID
    public String token;        // 用户token
    public String username;     // 昵称
    public String company;      // 公司名称
    public String logintime;    // 登录时间
    public String loginip;      // 登录IP
    public String mobile;       // 手机

    @Override
    public void onData() {
        JSONObject userObject = JSONObjectByKey("user");
        HTJSONObject user = new HTJSONObject(userObject);
        uid = user.stringByKey("uid");
        token = user.stringByKey("token");
        username = user.stringByKey("username");
        company = user.stringByKey("company");
        logintime = user.stringByKey("logintime");
        loginip = user.stringByKey("loginip");
        mobile = user.stringByKey("mobile");
    }

    public boolean isLogin() {
        return isDataNormal() && token != null;
    }
}
