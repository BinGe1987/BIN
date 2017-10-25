package com.kwaijian.facility.UI.BaseClass.Widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kwaijian.facility.BaseClasses.OS.SystemInfo;

/**
 * Created by binge on 2017/10/21.
 * 多行输入
 */

public class MultiLineEditView extends LinearLayout {

    private TextView nameText;

    private EditText editText;

    public MultiLineEditView(Context context) {
        this(context, null);
    }

    public MultiLineEditView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setOrientation(LinearLayout.VERTICAL);

        initViews();
    }

    private void initViews() {
        int paddingHorizontal = (int) (SystemInfo.Screen.scale * 10);
        int paddingVertical = (int) (SystemInfo.Screen.scale * 5);
        LayoutParams nameParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nameText = new TextView(getContext());
        nameText.setText("标题");
        nameText.setTextColor(Color.BLACK);
        nameText.setSingleLine();
        nameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        nameText.setPadding(paddingHorizontal, paddingVertical * 2, paddingHorizontal, paddingVertical);
        addView(nameText, nameParams);

        LayoutParams editParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editText = new EditText(getContext());
//        editText.setText();
        editText.setTextColor(Color.BLACK & 0x9fffffff);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        editText.setPadding(paddingHorizontal, 0, paddingHorizontal, paddingVertical * 2);
        editText.setBackgroundColor(Color.BLACK & 0x00ffffff);
        addView(editText, editParams);
        setEditable(false);
    }

    public void setEditable(boolean editable) {
        editText.setEnabled(editable);
        if (editable) {

        } else {
            editText.setBackgroundColor(Color.BLACK & 0x00ffffff);
        }
    }

    public void setName(String name) {
        nameText.setText(name);
    }

    public void setContent(String name, String text) {
        setName(name);
        setText(text);
    }

    public void setContent(String name, String text, String hint) {
        setName(name);
        setText(text);
        editText.setHint(hint);
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public String getText() {
        return editText.getText().toString();
    }

}
