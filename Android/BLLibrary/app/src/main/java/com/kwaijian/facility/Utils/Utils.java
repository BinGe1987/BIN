package com.kwaijian.facility.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by BinGe on 2017/9/14.
 * 常用通过工具类
 */
public class Utils {

    /**
     * 格式化数量统计
     *
     * @param count
     * @return
     */
    public static String countFormat(String count) {
        double num = Double.parseDouble(count);
        if (num > 10000) {
            num = num / 10000.0;
            NumberFormat nf = new DecimalFormat("0.0");
            num = Double.parseDouble(nf.format(num));
            return String.valueOf(num) + "w";
        } else {
            return count;
        }
    }
}
