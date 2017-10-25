package com.kwaijian.facility.UI.BaseClass.Views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Calendar;

/**
 * Created by binge on 2017/10/23.
 */

public class DatePickDialog extends DatePickerDialog {

    public DatePickDialog(@NonNull Context context, @Nullable OnDateSetListener listener) {
        super(context, listener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    @Override
    protected void onStop() {
//        super.onStop();
    }
}
