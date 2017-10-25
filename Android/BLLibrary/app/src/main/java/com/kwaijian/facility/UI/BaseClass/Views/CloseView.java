package com.kwaijian.facility.UI.BaseClass.Views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.kwaijian.facility.UI.BaseClass.Drawables.Shape.AddDrawable;
import com.kwaijian.facility.UI.BaseClass.Drawables.Shape.CloseDrawable;

/**
 * Created by BinGe on 2017/10/16.
 */

public class CloseView extends AppCompatTextView {

    public CloseView(Context context) {
        this(context, null);
    }

    public CloseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CloseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Drawable background = getBackground();
        if (background instanceof ColorDrawable) {
            ColorDrawable cd = (ColorDrawable) background;
            CloseDrawable newBackground = new CloseDrawable(context);
            newBackground.setColor(cd.getColor());
            setBackground(newBackground);
        } else {
            setBackground(new CloseDrawable(context));
        }
    }
}
