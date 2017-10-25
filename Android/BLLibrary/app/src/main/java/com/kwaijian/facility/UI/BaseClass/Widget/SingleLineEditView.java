package com.kwaijian.facility.UI.BaseClass.Widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kwaijian.facility.BaseClasses.OS.SystemInfo;

/**
 * Created by binge on 2017/10/21.
 * 单行输入
 */

public class SingleLineEditView extends LinearLayout {

    private TextView nameText;

    private EditText editText;

    public SingleLineEditView(Context context) {
        this(context, null);
    }

    public SingleLineEditView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setOrientation(LinearLayout.HORIZONTAL);

        initViews();
    }

    private void initViews() {
        int paddingHorizontal = (int) (SystemInfo.Screen.scale * 10);
        int paddingVertical = (int) (SystemInfo.Screen.scale * 5);
        LinearLayout.LayoutParams nameParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 6);
        nameText = new TextView(getContext());
        nameText.setText("标题");
        nameText.setGravity(Gravity.LEFT);
        nameText.setTextColor(Color.BLACK);
        nameText.setSingleLine();
        nameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        nameText.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
        addView(nameText, nameParams);

        LinearLayout.LayoutParams editParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2);
        editText = new EditText(getContext());
//        editText.setText("测试文本");
        editText.setTextColor(Color.BLACK & 0x9fffffff);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        editText.setPadding(0, paddingVertical, paddingHorizontal, paddingVertical);
        editText.setSingleLine();
        editText.setBackgroundColor(Color.BLACK & 0x00ffffff);
        addView(editText, editParams);
        setEditable(false);
    }

    public void setEditable(boolean editable) {
        editText.setEnabled(editable);
    }

    public void setName(String name) {
        nameText.setText(name);
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public void setHint(String hint) {
        editText.setHint(hint);
    }

}
