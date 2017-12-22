package com.shqtn.base.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shqtn.base.R;
import com.shqtn.base.widget.i.OnRadioChildrenCLickListener;

/**
 * Created by android on 2017/9/21.
 */

public class ImageTextButton extends LinearLayout implements RadioGroupView.RadioChildren {
    private ImageView iv;
    private TextView tv;

    private OnRadioChildrenCLickListener mOnChildrenClickListener;

    private static final int NORMAL_TEXT_SIZE = 12;

    public ImageTextButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ImageTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ImageTextButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(LinearLayout.VERTICAL);
        addBindView(context);
        float normalTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, NORMAL_TEXT_SIZE, getResources().getDisplayMetrics());
        tv.setTextSize(normalTextSize);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageTextButton, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.ImageTextButton_src) {
                Drawable drawable = typedArray.getDrawable(index);
                iv.setImageDrawable(drawable);

            } else if (index == R.styleable.ImageTextButton_labelT) {
                String text = typedArray.getString(index);
                tv.setText(text);

            } else if (index == R.styleable.ImageTextButton_labelTColor) {
                ColorStateList colorStateList = typedArray.getColorStateList(index);
                tv.setTextColor(colorStateList);

            } else if (index == R.styleable.ImageTextButton_labelTSize) {
                typedArray.getDimensionPixelSize(index, (int) normalTextSize);
            } else if (index == R.styleable.ImageTextButton_isSelect) {
                boolean aBoolean = typedArray.getBoolean(index, false);
                if (aBoolean) {
                    setSelected(aBoolean);
                }
            }
        }
        typedArray.recycle();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnChildrenClickListener != null) {
                    mOnChildrenClickListener.radioChildrenClick(view);
                }
            }
        });
    }

    private void addBindView(Context context) {
        iv = new ImageView(context);
        LayoutParams ivLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        iv.setLayoutParams(ivLp);

        tv = new TextView(context);
        LayoutParams tvLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(tvLp);

        addView(iv);
        addView(tv);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        iv.setSelected(selected);
        tv.setSelected(selected);
    }


    @Override
    public void setOnEventChildrenClickListener(OnRadioChildrenCLickListener l) {
        mOnChildrenClickListener = l;
    }

    @Override
    public OnRadioChildrenCLickListener getOnEventChildrenClickListener() {
        return mOnChildrenClickListener;
    }
}
