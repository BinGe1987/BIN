package com.kwaijian.facility.UI.Home.Server.Detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kwaijian.facility.DataCenter.Home.DataObj.Order;
import com.kwaijian.facility.R;

/**
 * Created by binge on 2017/10/23.
 */

public class SpareReplaceView extends FrameLayout {

    private Order.OrderSpare mSpare;

    public SpareReplaceView(@NonNull Context context) {
        this(context, null);
    }

    public SpareReplaceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.company_spareparts_item, this);
    }

    public void setOrderSpare(Order.OrderSpare spare) {
        mSpare = spare;
        TextView mOldModel = (TextView) findViewById(R.id.old_company_spareparts_model);
        mOldModel.setText(spare.getOldModel());

        EditText mOldSN = (EditText) findViewById(R.id.old_company_spareparts_SN);
        mOldSN.setText(spare.getOldSN());
        mOldSN.addTextChangedListener(new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                mSpare.setOldSN(editable.toString());
            }
        });

        EditText mNewModel = (EditText) findViewById(R.id.new_company_spareparts_model);
        mNewModel.setText(spare.getNewModel());
        mNewModel.addTextChangedListener(new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                mSpare.setNewModel(editable.toString());
            }
        });

        EditText mNewSN = (EditText) findViewById(R.id.new_company_spareparts_SN);
        mNewSN.setText(spare.getNewSN());
        mNewSN.addTextChangedListener(new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                mSpare.setNewSN(editable.toString());
            }
        });
    }

    public Order.OrderSpare getOrderSpare() {
        return mSpare;
    }

    public void setCloseClickListener(View.OnClickListener listener) {
        findViewById(R.id.close).setOnClickListener(listener);
    }

    public void setCloseable(boolean closeable) {
        findViewById(R.id.close).setVisibility(closeable ? View.VISIBLE : View.GONE);
        findViewById(R.id.old_company_spareparts_model).setEnabled(closeable);
        findViewById(R.id.old_company_spareparts_SN).setEnabled(closeable);
        findViewById(R.id.new_company_spareparts_model).setEnabled(closeable);
        findViewById(R.id.new_company_spareparts_SN).setEnabled(closeable);
    }


    private abstract class TextChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public abstract void afterTextChanged(Editable editable);
    }
}
