package com.kwaijian.facility.UI.BaseClass.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by BinGe on 2017/10/23.
 */

public class PromptBox {

    private static Toast toast;

    public static void showText(Context context, String text) {
        toast = null;
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        int padding = (int) textView.getTextSize();
        textView.setTextColor(Color.BLACK);
        textView.setPadding(padding * 2, padding, padding * 2, padding);
        textView.setBackground(new BackgroundDrawable());
        showView(textView);
//        textView.animate().alpha(30);

    }

    public static void showView(View view) {
        if (toast == null) {
            toast = new Toast(view.getContext());
        } else {
            toast.cancel();
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    static class BackgroundDrawable extends Drawable {

        private Paint mPaint = new Paint();
        private RectF mRect = new RectF();

        public BackgroundDrawable() {
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.RED);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {

            mRect.set(0, 0, canvas.getWidth(), canvas.getHeight());
            float radius = Math.max(mRect.width(), mRect.height()) * 0.1f;

//            int x = canvas.getWidth() / 2;
//            int y = canvas.getHeight() / 2;
//            mPaint.setShader(new RadialGradient(x, y, mRect.width(), new int[] {
//                    Color.BLACK & 0x9ffffff, Color.TRANSPARENT }, null,
//                    Shader.TileMode.REPEAT));
//            canvas.drawRoundRect(mRect, radius, radius, mPaint);


//            mRect.offsetTo(inset, inset);

            mPaint.setColor(Color.BLACK & 0x11ffffff);
            canvas.drawRoundRect(mRect, radius, radius, mPaint);

            float inset = radius * 0.05f;
            mRect.inset(inset, inset);

//            mRect.offsetTo(-inset, -inset);
            mPaint.setColor(Color.WHITE);
            canvas.drawRoundRect(mRect, radius, radius, mPaint);


        }

        public void setColor(int color) {
            if (getPaint() != null) {
                getPaint().setColor(color);
            }
            invalidateSelf();
        }

        public Paint getPaint() {
            return mPaint;
        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
            getPaint().setAlpha(alpha);
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
            getPaint().setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

    }

}
