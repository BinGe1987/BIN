package com.kwaijian.facility.UI.BaseClass.Views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kwaijian.facility.R;


/**
 * ActionSheet
 */
public class ActionSheet extends Dialog implements View.OnClickListener {

    private View mParentView;
    private LinearLayout mLinearLayout;
    private int margin = 20;
    private OnItemClickListener mOnItemClickListener;

    public ActionSheet(Context context) {
        super(context);
        getContext().setTheme(R.style.action_sheet);

        Window window = getWindow();
        window.setWindowAnimations(android.R.anim.fade_in);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        window.setAttributes(attributes);

        margin = (int) getContext().getResources().getDisplayMetrics().scaledDensity * 10;

//        setCanceledOnTouchOutside(false);

        initViews();
    }

    private void initViews() {

        FrameLayout root = new FrameLayout(getContext());
        setContentView(root);

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        root.addView(linearLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));


        addItemParentView(linearLayout);
        addCancelParentView(linearLayout);

        TranslateAnimation t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1,
                Animation.RELATIVE_TO_SELF, 0);
        t.setDuration(200);
        linearLayout.startAnimation(t);

        mParentView = linearLayout;
    }

    private void addItemParentView(LinearLayout linearLayout) {
        LinearLayout linear = new LinearLayout(getContext());
        linear.setBackground(new RoundDrawable());
        linear.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.leftMargin = margin;
        linearParams.rightMargin = linearParams.leftMargin;
        linearParams.bottomMargin = linearParams.leftMargin;
        linearParams.topMargin = linearParams.leftMargin;
        linearLayout.addView(linear, linearParams);
        mLinearLayout = linear;
    }

    private void addCancelParentView(LinearLayout linearLayout) {

        LinearLayout linear = new LinearLayout(getContext());
        linear.setBackground(new RoundDrawable());
        linear.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.leftMargin = margin;
        linearParams.rightMargin = linearParams.leftMargin;
        linearParams.bottomMargin = linearParams.leftMargin;
        linearLayout.addView(linear, linearParams);

        TextView item = new TextView(getContext());
        item.setBackground(new ItemDrawable());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        item.setPadding(0, margin, 0, margin);
        item.setText("取消");
        item.setTextColor(Color.BLACK);
        item.setGravity(Gravity.CENTER);
        item.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        linear.addView(item, params);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void addItem(String name) {

        LinearLayout itemLayout = new LinearLayout(getContext());
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        itemLayout.setOnClickListener(this);
        mLinearLayout.addView(itemLayout, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (mLinearLayout.getChildCount() > 0) {
            View view = new View(getContext());
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            viewParams.height = (int) (getContext().getResources().getDisplayMetrics().scaledDensity * 0.5);
            view.setBackgroundColor(Color.GRAY);
            itemLayout.addView(view, viewParams);
        }

        TextView item = new TextView(getContext());
        item.setBackground(new ItemDrawable());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        item.setPadding(0, margin, 0, margin);
        item.setText(name);
        item.setTextColor(Color.BLACK);
        item.setGravity(Gravity.CENTER);
        item.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        itemLayout.addView(item, params);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
                if (mLinearLayout.getChildAt(i) == v) {
                    mOnItemClickListener.onItemClick(i);
                    dismiss();
                    return;
                }
            }
        }
    }

    @Override
    public void dismiss() {
        TranslateAnimation t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1);
        t.setDuration(150);
        mParentView.startAnimation(t);
        t.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ActionSheet.super.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    class RoundDrawable extends Drawable {

        private RectF rect = new RectF();
        private Paint paint = new Paint();

        public RoundDrawable() {
            paint.setColor(Color.WHITE);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            rect.set(0, 0, canvas.getWidth(), canvas.getHeight());
            float round = rect.width() * 0.015f;
            canvas.drawRoundRect(rect, round, round, paint);
        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
            paint.setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }

    class ItemDrawable extends Drawable {

        private boolean mPressed = false;

        public ItemDrawable() {

        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            if (isPressed()) {
                canvas.drawColor(Color.BLACK & 0x20ffffff);
            }
        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public boolean isStateful() {
            return true;
        }

        @Override
        protected boolean onStateChange(int[] state) {
            boolean pressed = false;
            for (int current : state) {
                if (current == android.R.attr.state_pressed) {
                    pressed = true;
                    break;
                }
            }
            mPressed = pressed;
            invalidateSelf();
            return true;
        }

        public boolean isPressed() {
            return mPressed;
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}