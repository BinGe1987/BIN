package com.kwaijian.facility.OldSource.tools;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.kwaijian.facility.UI.BaseClass.Views.DatePickDialog;
import com.kwaijian.facility.UI.BaseClass.Views.TimePickDialog;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Map<String, String> getContacts(Context context, Uri contactData) {
        Map<String, String> map = new HashMap<String, String>();
        // Uri contactData = data.getData();
        Cursor c = ((Activity) context).managedQuery(contactData, null, null, null, null);
        String contactId = contactData.getLastPathSegment();
        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            map.put("name", name);
            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            String phoneNumber = null;
            LogUtils.d("get contact for system");
            if (hasPhone.equalsIgnoreCase("1")) {
                hasPhone = "true";
            } else {
                hasPhone = "false";
            }
            if (Boolean.parseBoolean(hasPhone)) {
                Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                while (phones.moveToNext()) {
                    phoneNumber = phones
                            .getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                map.put("phoneNumber", phoneNumber);
                phones.close();
            }
        }
        return map;
    }

    public static boolean isNetWork(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 检查网络连接，如果无网络可用，就不需要进行连网操作等
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null || !manager.getBackgroundDataSetting()) {
            return false;
        } else {
            return true;
        }

    }

    public static void saveImage(String oldPath, String imageId) {
        try {
            File sdDir = new File(Environment.getExternalStorageDirectory(), "Facility/image/");
            if (!sdDir.exists()) {
                sdDir.mkdirs();
            }
            File nomedia = new File(sdDir.getAbsolutePath() + "/.nomedia");
            if (!nomedia.exists()) {
                try {
                    nomedia.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            sdDir = new File(sdDir, imageId + ".png");
            LogUtils.d("image path :    " + sdDir.toString());
            if (!sdDir.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                OutputStream outStream = new FileOutputStream(sdDir);
                byte bt[] = new byte[1024];
                int c;
                while ((c = inStream.read(bt)) > 0) {
                    outStream.write(bt, 0, c);
                }
                inStream.close();
                outStream.close();
            }
        } catch (Exception e) {
            LogUtils.d("复制图片出错");
            e.printStackTrace();
        }
    }

    public static String formatTime(String timeStamp) {
        try {
            if (TextUtils.isEmpty(timeStamp) || timeStamp.equals("0")) {
                return "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(getTimeFormat());
            String date = sdf.format(new Date(Long.parseLong(timeStamp) * 1000L));
            return date;
        } catch (NumberFormatException exception) {
            LogUtils.d(exception.getMessage());
            return timeStamp;
        }
    }

    public static String formatTime(long timeStamp) {
        if (timeStamp <= 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(getTimeFormat());
        String date = sdf.format(new Date(timeStamp * 1000L));
        return date;
    }

    private static void pickData(final Context context, final DateTimeCallback callback) {
        final StringBuffer dateTime = new StringBuffer();
        new DatePickDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                LogUtils.d("date set : " + year + "/" + month + "/" + day);
                dateTime.append(year);
                dateTime.append("/");
                if (month + 1 < 10) {
                    dateTime.append("0");
                }
                dateTime.append(month + 1);
                dateTime.append("/");
                if (day < 10) {
                    dateTime.append("0");
                }
                dateTime.append(day);

                pickTime(context, dateTime, callback);
            }
        }).show();
    }

    private static void pickTime(Context context, final StringBuffer dateTime, final DateTimeCallback callback) {
        final StringBuffer timeBuffer = new StringBuffer();
        new TimePickDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                if (TextUtils.isEmpty(timeBuffer)) {
                    timeBuffer.append(" ");
                    if (hour < 10) {
                        timeBuffer.append("0");
                    }
                    timeBuffer.append(hour);
                    timeBuffer.append(":");
                    if (minute < 10) {
                        timeBuffer.append("0");
                    }
                    timeBuffer.append(minute);
                    if (callback != null) {
                        dateTime.append(timeBuffer);
                        callback.onDataTimeCallback(dateTime.toString());
                    }
                }
            }
        }).show();
    }

    public static void pickDateTime(final Context context, final DateTimeCallback callback) {
        pickData(context, callback);
    }

    public static String getTimeFormat() {
        return "yyyy/MM/dd HH:mm";
    }

    public interface DateTimeCallback {
        public void onDataTimeCallback(String date);
    }
}
