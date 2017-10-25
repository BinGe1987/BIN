package com.kwaijian.facility.UI.BaseClass.Views.Browser;

/**
 * Created by BinGe on 2017/9/19.
 * 如果在预览的时候有缩略图，需要用到此类对数据进行封装后再传过来
 */
public class HTImageData {

    /**
     * 缩略图，支持：Url，Path，Bitmap
     */
    public Object thumb;

    /**
     * 大图，支持：Url，Path，Bitmap
     */
    public Object image;

    public HTImageData(Object thumb, Object image) {
        this.thumb = thumb;
        this.image = image;
    }

}