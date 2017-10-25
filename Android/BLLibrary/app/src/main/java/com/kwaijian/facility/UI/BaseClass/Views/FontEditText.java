package com.kwaijian.facility.UI.BaseClass.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by BinGe on 2017/10/16.
 */

public class FontEditText extends AppCompatEditText {

    private static Typeface typeface;

    public FontEditText(Context context) {
        this(context, null);
    }

    public FontEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(getContext().getAssets(), "font/ultralight.ttf");
        }
        setTypeface(typeface);
    }
}
