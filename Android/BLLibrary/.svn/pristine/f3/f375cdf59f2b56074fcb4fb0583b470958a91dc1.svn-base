package com.kwaijian.facility.Utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeFormatUtils {

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param timeStr 时间戳
     * @return
     */
    public static String getStandardDate(long timeStr) {
        return getDate(timeStr);
    }

    public static String getStandardByDate(long timeStr) {
        return getByDate(timeStr);
    }

    public static String getStandardDate(String timeStr) {
        long t = Long.parseLong(timeStr);
        return getDate(t);
    }

    private static String getDate(long t) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sf;
        Date date = new Date(t);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        long time = calendar.getTimeInMillis() - t + 24 * 60 * 60 * 1000;
        float day = time / 24 / 60 / 60 / 1000.f;// 天前

        if (day < 1) {
            sf = new SimpleDateFormat("HH:mm", Locale.CHINA);
            sb.append(sf.format(date));
        } else if (day > 1 && day < 2) {
            sb.append("昨天 ");
            sf = new SimpleDateFormat("HH:mm", Locale.CHINA);
            sb.append(sf.format(date));
        } else if (day > 2 && day < 3) {
            sb.append("前天 ");
            sf = new SimpleDateFormat("HH:mm", Locale.CHINA);
            sb.append(sf.format(date));
        } else {
            sf = new SimpleDateFormat("MM月dd日", Locale.CHINA);
            sb.append(sf.format(date));
        }
        return sb.toString();
    }

    private static String getByDate(long t) {
        StringBuilder sb = new StringBuilder();

        long time = System.currentTimeMillis() - t;
        long mill = (long) Math.ceil(time / 1000);//秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

}
