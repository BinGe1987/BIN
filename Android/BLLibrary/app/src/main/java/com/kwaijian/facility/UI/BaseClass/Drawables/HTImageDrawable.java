package com.kwaijian.facility.UI.BaseClass.Drawables;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by BinGe on 2017/9/20.
 * 加载网络或本地图片
 */

public class HTImageDrawable extends Drawable {

    /**
     * 绘制相关属性
     */
    private Bitmap mBitmap;
    private Rect mRect = new Rect();
    private Paint mPaint = new Paint();

    /**
     * 设置分辨率
     */
    private int density = 3;

    /**
     * 用于drawable的渐变动画
     */
    private long time;

    private boolean isRecycle;

    public HTImageDrawable() {

    }

    public HTImageDrawable(Bitmap bitmap) {
        setImageBitmap(bitmap);
    }

    public Bitmap getImageBitmap() {
        return mBitmap;
    }

    public void setImageBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        time = System.currentTimeMillis();
        invalidateSelf();
    }

    /**
     * 判断当前drawable是否处于回收状态
     *
     * @return
     */
    public boolean isRecycle() {
        return isRecycle;
    }

    /**
     * 绘制图片，根据以下规则
     * 1、以宽为标准，当图片大于
     *
     * @param canvas
     */
    @Override
    public void draw(@NonNull Canvas canvas) {
        Bitmap bitmap = getImageBitmap();
        if (bitmap != null) {
            //这里做了一个图片渐变的动画
            if (time != 0) {
                float t = (System.currentTimeMillis() - time) / 300f;
                if (t <= 1) {
                    mPaint.setAlpha((int) (255 * t));
                    invalidateSelf();
                } else {
                    time = 0;
                    mPaint.setAlpha(255);
                }
            }
            mRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());

            float canvasWidth = canvas.getWidth();
            float scale = mRect.width() / canvasWidth;
            float height = mRect.height() / scale;
            mRect.set(0, 0, canvas.getWidth(), (int)height);
            setBounds(mRect);
            if (height < canvas.getHeight()) {
                mRect.offsetTo(mRect.left, canvas.getHeight() / 2 - mRect.height() / 2);
            }
            canvas.drawBitmap(bitmap, null, mRect, mPaint);
        }
    }

    @NonNull
    @Override
    public Rect getDirtyBounds() {
        return mRect;
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
        invalidateSelf();
    }

    public void recycle() {
        mBitmap = null;
        isRecycle = true;
    }

}
