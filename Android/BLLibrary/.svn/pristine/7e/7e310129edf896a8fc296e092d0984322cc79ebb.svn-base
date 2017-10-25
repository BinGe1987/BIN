package com.kwaijian.facility.OldSource.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.tools.Density;

/**
 * Created by BinGe on 2017/2/6.
 */

public class FacilityImageView extends FrameLayout implements View.OnClickListener {

    private String imageId;
    private ImageView imageView;
    private ImageView close;

    public FacilityImageView(Context context) {
        this(context, null);
    }

    public FacilityImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int padding = Density.dip2px(2);
        setLayoutParams(new ViewGroup.LayoutParams(Density.dip2px(80), Density.dip2px(80)));
        imageView = new ImageView(context);
//        imageView.setPadding(3, 3, 3, 3);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.leftMargin = padding;
        params.topMargin = params.leftMargin;
        params.rightMargin = params.leftMargin;
        params.bottomMargin = params.leftMargin;
        addView(imageView, params);


        close = new ImageView(context);
        close.setPadding(padding, padding, padding, padding);
        close.setImageResource(R.mipmap.close);
        close.setOnClickListener(this);
        addView(close, new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.TOP));
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageId() {
        return this.imageId;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setOnDeleteClickListener(OnClickListener listener) {
        close.setVisibility(listener == null ? View.GONE : View.VISIBLE);
        close.setOnClickListener(listener);
    }

    @Override
    public void onClick(View view) {

    }
}
