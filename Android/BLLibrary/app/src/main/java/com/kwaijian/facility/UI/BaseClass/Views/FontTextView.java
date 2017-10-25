package com.kwaijian.facility.UI.BaseClass.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.kwaijian.facility.Utils.Log.LogUtils;

/**
 * Created by BinGe on 2017/10/16.
 */

public class FontTextView extends AppCompatTextView {

    private static Typeface typeface;
    private static Typeface bold;

    public FontTextView(Context context) {
        this(context, null);
    }

    public FontTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(getContext().getAssets(), "font/ultralight.ttf");
        }
        if (bold == null) {
            bold = Typeface.createFromAsset(getContext().getAssets(), "font/bold.ttf");
        }

        int style = getTypeface().getStyle();
        LogUtils.d("style:" + this + ">>>" + style);
        if (getTypeface().getStyle() == Typeface.BOLD || getTypeface().getStyle() == Typeface.BOLD_ITALIC) {
            setTypeface(bold);
        } else {
            setTypeface(typeface);
        }
    }
}
