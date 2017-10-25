package com.kwaijian.facility.UI.BaseClass.Views;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.Calendar;

/**
 * Created by binge on 2017/10/23.
 */

public class TimePickDialog extends TimePickerDialog {



    public TimePickDialog(Context context, OnTimeSetListener listener) {
        super(context, listener, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (dialog instanceof TimePickerDialog) {
            ((TimePickerDialog) dialog).getWindow().getDecorView().clearFocus();
        }
        super.onClick(dialog, which);
    }

    @Override
    protected void onStart() {

    }

    @Override
    public void show() {
        super.show();
    }
}
