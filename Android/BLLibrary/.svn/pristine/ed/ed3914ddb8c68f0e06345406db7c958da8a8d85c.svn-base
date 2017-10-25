package com.kwaijian.facility.BaseClasses.Objects;

/**
 * Created by BinGe on 2017/8/31.
 * 错误信息类
 */

public class HTError extends HTObject {


    /**
     * 默认错误代码
     */
    public final static int DEFAULT_ERROR_CODE = Integer.MIN_VALUE;

    /**
     * 错误代码，后期添加一个错误代码对应表
     */
    public int errorCode;
    /**
     * 错误提示，可用于显示错误提示能用户看
     */
    public String errorInfo;

    public HTError() {
        this(HTError.DEFAULT_ERROR_CODE);
    }

    public HTError(String info) {
        this(DEFAULT_ERROR_CODE, info);
    }

    public HTError(int errorCode) {
        this(errorCode, null);
    }

    public HTError(int errorCode, String errorInfo) {
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    @Override
    public String toString() {
        return "errorCode: " + this.errorCode + ", errorInfo: " + this.errorInfo;
    }
}
