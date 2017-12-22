package com.shqtn.base.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.shqtn.base.utils.CashierInputFilter;


/**
 * 可输入小数的编辑框
 * Created by Administrator on 2017-2-17.
 */
public class MaxQuantityEditText extends EditText {

    private double maxQuantity;
    private OnOverstepListener mListener;

    private TextWatcher mTextWatcher;
    /**
     * 用于判断是否开启了最大值限制
     */
    private boolean isOpenOverstep;

    public MaxQuantityEditText(Context context) {
        super(context);
        setFilters(new InputFilter[]{new CashierInputFilter()});
    }

    public MaxQuantityEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFilters(new InputFilter[]{new CashierInputFilter()});
    }

    public MaxQuantityEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFilters(new InputFilter[]{new CashierInputFilter()});
    }

    public void setMaxQuantity(double quantity) {
        isOpenOverstep = true;
        maxQuantity = quantity;
        if (mTextWatcher == null){
            mTextWatcher = addTextChangeListener();
        }
        this.addTextChangedListener(mTextWatcher);
    }
    public void setOnOverStepListener(OnOverstepListener listener){
        mListener = listener;
    }

    private TextWatcher addTextChangeListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isOpenOverstep && mListener != null) {
                    double d = 0;
                    try {
                        d = Double.parseDouble(s.toString());
                    } catch (Exception e) {
                        d = 0;
                    }
                    if (d > maxQuantity) {

                        mListener.overstepMaxQuantity(MaxQuantityEditText.this, d);
                    } else {
                        mListener.normal(MaxQuantityEditText.this, d);
                    }
                }
            }
        };
    }

    public void closeOverstep() {
        isOpenOverstep = false;
        mListener = null;
        mTextWatcher = null;
        this.addTextChangedListener(null);
    }

    public interface OnOverstepListener {
        /**
         * 超出限制
         */
        void overstepMaxQuantity(EditText e, double s);

        void normal(EditText e, double d);
    }
}
