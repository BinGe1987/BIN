package com.kwaijian.facility.DataCenter.Login.LoginPerformer;

import com.kwaijian.facility.DataCenter.BaseClasses.HTOperationPerformer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinGe on 2017/9/14.
 * 登录处理器的父类，会有一个公共的url
 */

public abstract class LoginPerformer<T> implements HTOperationPerformer<T> {

    /**
     * 后台的登录接口
     * 参数：
     * type：登录类型（weixin，weibo，qq，auto）
     * mid：机器码
     * im：是否使用IM
     * account：登录账号，type为mobile时，account=手机号
     * code：登录码，type为mobile时，code=短信验证码
     */
    protected static String UrlLogin = /*HttpConstant.LG + */"lg/login.do";

    /**
     * 合并登录的通用参数
     * @param params 原始参数
     * @return  返回合并后的参数
     */
    protected static Map<String, Object> mergeLoginParams(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    @Override
    public boolean isAsynchronous() {
        return false;
    }
}
