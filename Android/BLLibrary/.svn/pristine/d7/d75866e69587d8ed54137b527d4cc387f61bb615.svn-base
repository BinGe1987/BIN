package com.kwaijian.facility.UI.BaseClass.Views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.kwaijian.facility.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinGe on 2016/10/22.
 */

public class HTProgress {

    /**
     * 显示dialog的类型
     */
    public static enum TYPE {
        LOADING,//显示一个loading动画
        ALERT,
        CONFIRM,
        CUSTOM_VIEW,
        LOADING_WITH_TEXT //显示一个loading动画，可带文本
    }

    private static Map<String, HTProgress> progressMap = new HashMap<>();
    private static float sScale = 1;

    public static HTProgress create(Context context, String key) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        sScale = dm.density;

        dismiss(key);
        HTProgress p = new HTProgress(context);
        progressMap.put(key, p);
        return p;

    }

    private Progress progress;
    private TYPE type = TYPE.LOADING;
    private boolean canceledOutSide = false;
    private Context context;

    private int backgroundColor = Color.TRANSPARENT;
    private View customView;

    private String title;
    private String content;

    private String cancelText;
    private String sureText;
    private HTProgressCallback cancelCallback;
    private HTProgressCallback sureCallback;

    private PopupWindow popupWindow;

    private HTProgress(Context context) {
        this.context = context;
    }

    /**
     * 显示的类型
     *
     * @param type
     */
    public HTProgress setType(TYPE type) {
        this.type = type;
        return this;
    }

    public HTProgress setTitle(String title) {
        this.title = title;
        return this;
    }

    public HTProgress setContent(String content) {
        this.content = content;
        return this;
    }

    public HTProgress setCancelButton(String text, HTProgressCallback cb) {
        this.cancelText = text;
        this.cancelCallback = cb;
        return this;
    }

    public HTProgress setSureButton(String text, HTProgressCallback cb) {
        this.sureText = text;
        this.sureCallback = cb;
        return this;
    }

    public HTProgress setCustomView(View view) {
        customView = view;
        return this;
    }

    public HTProgress setBackground(int color) {
        backgroundColor = color;
        return this;
    }

    public HTProgress setCanceledOnTouchOutSide(boolean cancel) {
        canceledOutSide = cancel;
        return this;
    }

    public void show() {
        switch (this.type) {
            case LOADING:
                progress = new Progress(context, new LoadingView(context));
                break;
            case CONFIRM:
                progress = new Progress(context,
                        new ConfirmView(context,
                                this.title,
                                this.content,
                                this.cancelText,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dismissAll();
                                        if (cancelCallback != null) {
                                            cancelCallback.callback();
                                        }
                                    }
                                },
                                this.sureText,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dismissAll();
                                        if (sureCallback != null) {
                                            sureCallback.callback();
                                        }
                                    }
                                }
                        ));
                break;
            case CUSTOM_VIEW:
                if (customView == null) {
                    Toast.makeText(context, "没有调用HTProgress setCustomView", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progress = new Progress(context, customView);
                }
                break;
            case LOADING_WITH_TEXT:
                break;
            default:
                break;
        }
        progress.setCanceledOnTouchOutside(canceledOutSide);
        try {
            progress.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToastAsDropDown(View v, String text, int textColor) {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
        popupWindow = new PopupWindow();

        AppCompatTextView view = new AppCompatTextView(context) {

            int action = 0;
            long time = 0;

            @Override
            protected void onDraw(Canvas canvas) {
                canvas.save();
                if (time == 0) {
                    time = System.currentTimeMillis();
                }
                float height = getHeight();
                float t = (System.currentTimeMillis() - time) / 300f;
                if (t < 1) {

                    if (action == 0) {
                        float y = -height + height * t;
                        canvas.translate(0, y);
                    } else {
                        float y = -height * t;
                        canvas.translate(0, y);
                    }

                    invalidate();
                } else {
                    if (action == 0) {
                        canvas.translate(0, 0);
                        postInvalidateDelayed(1000);
                        action = 1;
                        time = 0;
                    } else {
                        canvas.translate(0, -height);
                        popupWindow.dismiss();
                        popupWindow = null;
                    }

                }
//                canvas.drawColor(backgroundColor);
                Rect r = new Rect(0, 0, getWidth(), getHeight());
                Paint p = new Paint();
                p.setColor(backgroundColor);
                canvas.drawRect(r, p);
                super.onDraw(canvas);

                canvas.restore();
            }
        };
        view.setText(text);
        view.setTextColor(textColor);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(Color.WHITE);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        view.setPadding(0, dp2px(10), 0, dp2px(10));
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAsDropDown(v);

    }

    public static void dismiss(String key) {
        HTProgress p = progressMap.get(key);
        if (p != null) {
            if (p.progress != null) {
                try {
                    p.progress.dismiss();
                } catch (Exception e) {

                }
            }
        }
        if (progressMap.containsKey(key)) {
            progressMap.remove(key);
        }
    }

    public static void dismissAll() {
        for (Map.Entry<String, HTProgress> entry : progressMap.entrySet()) {
            HTProgress p = entry.getValue();
            if (p != null) {
                if (p.progress != null) {
                    try {
                        p.progress.dismiss();
                    } catch (Exception e) {

                    }
                }
            }
        }
        progressMap.clear();
    }

    private static int dp2px(int dp) {
        return (int) (dp * sScale);
    }


    class Progress extends Dialog {

        private FrameLayout rootView;

        public Progress(Context context, View view) {
            super(context, R.style.progress);

            rootView = new FrameLayout(context);
            rootView.setBackgroundColor(backgroundColor);

            if (view != null) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                if (params == null) {
                    params = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                }
                params.gravity = Gravity.CENTER;
                rootView.addView(view, params);
            } else {
                Toast.makeText(context, "setCustomView 为空", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(rootView);
        }

        @Override
        public boolean onKeyUp(int keyCode, KeyEvent event) {

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (!canceledOutSide) {
                    return true;
                }
            }
            return super.onKeyUp(keyCode, event);
        }

    }

    class LoadingView extends View {


        private RectF r = new RectF();
        private Paint paint = new Paint();
        private long time = 0;
        private int angle;

        private LoadingView(Context context) {
            super(context);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(dp2px(60), dp2px(60), Gravity.CENTER);
            setLayoutParams(params);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(0xddffffff);
            r.set(0, 0, getWidth(), getHeight());
            canvas.drawRoundRect(r, dp2px(10), dp2px(10), paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(dp2px(1));
            paint.setColor(0xff7c7c7c);
            r.inset(dp2px(15), dp2px(15));
            canvas.drawArc(r, angle, 320, false, paint);

            if (time == 0) {
                time = System.currentTimeMillis();
            }

            float t = (System.currentTimeMillis() - time) / 1000f;
            if (t >= 1) {
                time = System.currentTimeMillis();
                t = 1;
            }
            angle = (int) (360 * t);
            invalidate();

        }

    }

    class ConfirmView extends LinearLayout {

        public ConfirmView(Context context,
                           String title,
                           String content,
                           String cancel,
                           OnClickListener cancelListener,
                           String sure,
                           OnClickListener sureListener) {
            super(context);

//            setLayerType(View.LAYER_TYPE_HARDWARE, null);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(dp2px(250),
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            setLayoutParams(params);

            LinearLayout parent = new LinearLayout(context);
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setBackgroundColor(Color.WHITE);
            addView(parent, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

            if (TextUtils.isEmpty(content)) {
                content = " ";
            }
            if (TextUtils.isEmpty(sure)) {
                sure = "确认";
            }

            int contentPaddingTop = 10;
            if (!TextUtils.isEmpty(title)) {
                //标题
                TextView titleView = new TextView(context);
                titleView.setText(title);
                titleView.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                titleView.setPadding(dp2px(10), dp2px(20), dp2px(10), dp2px(0));
                titleView.getPaint().setFakeBoldText(true);
                titleView.setTextColor(Color.BLACK);
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                titleView.setSingleLine();
                parent.addView(titleView);
                contentPaddingTop = 0;
            }

            //内容
            TextView contentView = new TextView(context);
            contentView.setText(content);
            contentView.setGravity(Gravity.CENTER);
            contentView.setPadding(dp2px(10), dp2px(contentPaddingTop), dp2px(10), dp2px(20));
            contentView.setTextColor(Color.BLACK & 0xccffffff);
            contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            parent.addView(contentView);

            View line = new View(getContext());
            line.setBackgroundColor(Color.BLACK & 0x66ffffff);
            parent.addView(line, LayoutParams.MATCH_PARENT, dp2px(1) / 2);

            LinearLayout buttonLayout = new LinearLayout(getContext());
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            parent.addView(buttonLayout, LayoutParams.MATCH_PARENT, dp2px(45));

            if (!TextUtils.isEmpty(cancel)) {
                TextView cancelView = new TextView(context);
                cancelView.setText(cancel);
                cancelView.setGravity(Gravity.CENTER);
                cancelView.setPadding(dp2px(10), 0, dp2px(10), 0);
                cancelView.setTextColor(0xff2CA1F3);
                cancelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                cancelView.setSingleLine();
                cancelView.setClickable(true);
                cancelView.setBackground(new StateDrawable());
                LayoutParams p1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                p1.weight = 1;
                buttonLayout.addView(cancelView, p1);
                cancelView.setOnClickListener(cancelListener);

                line = new View(getContext());
                line.setBackgroundColor(Color.BLACK & 0x66ffffff);
                buttonLayout.addView(line, dp2px(1) / 2, LayoutParams.MATCH_PARENT);
            }

            TextView sureView = new TextView(context);
            sureView.setText(sure);
            sureView.setGravity(Gravity.CENTER);
            sureView.setPadding(dp2px(10), 0, dp2px(10), 0);
            sureView.setBackground(new StateDrawable());
            sureView.setTextColor(0xff2CA1F3);
            sureView.setClickable(true);
            sureView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            sureView.setSingleLine();
            sureView.setOnClickListener(sureListener);
            LayoutParams p1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            p1.weight = 1;
            buttonLayout.addView(sureView, p1);


        }


        private Bitmap bitmap;
        private Rect rect = new Rect();
        private Paint paint = new Paint();

        @Override
        protected void dispatchDraw(Canvas canvas) {
            if (bitmap == null) {
                rect.set(0, 0, getWidth() / 2, getHeight() / 2);
                bitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bitmap);
                Path p = new Path();
                p.addRoundRect(new RectF(rect), rect.width() * 0.04f, rect.width() * 0.04f, Path.Direction.CCW);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.BLACK);
                c.drawPath(p, paint);
            }
            rect.set(0, 0, getWidth(), getHeight());
            int sc = canvas.saveLayer(new RectF(rect), null,
                    Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
                            | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                            | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                            | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
            super.dispatchDraw(canvas);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(bitmap, null, rect, paint);
            canvas.restoreToCount(sc);
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
        }
    }

    public interface HTProgressCallback {
        public void callback();
    }


    class StateDrawable extends StateListDrawable {

        public StateDrawable() {
            //获取对应的属性值 Android框架自带的属性 attr
            int pressed = android.R.attr.state_pressed;
            int window_focused = android.R.attr.state_window_focused;
            int focused = android.R.attr.state_focused;
            int selected = android.R.attr.state_selected;

            addState(new int[]{pressed, window_focused}, new Background(Color.BLACK & 0x22ffffff));
//        addState(new int []{pressed , -focused}, new Background(Color.GRAY));
//        addState(new int []{selected }, new Background(Color.YELLOW));
//        addState(new int []{focused }, new Background(Color.GREEN));
            addState(new int[]{}, new Background(Color.TRANSPARENT));

        }

        class Background extends Drawable {

            private int color = Color.BLACK;

            public Background(int color) {
                this.color = color;
            }

            @Override
            public void draw(Canvas canvas) {
                canvas.drawColor(color);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.TRANSPARENT;
            }
        }

    }

}