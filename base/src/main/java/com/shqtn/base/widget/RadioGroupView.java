package com.shqtn.base.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.shqtn.base.utils.LogUtils;
import com.shqtn.base.widget.i.OnRadioChildrenCLickListener;

/**
 * Created by android on 2017/9/21.
 */

public class RadioGroupView extends LinearLayout implements OnRadioChildrenCLickListener {
    private OnRadioChildChangeListener mOnRadioChildChangeListener;
    public RadioGroupView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RadioGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RadioGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int childCount = getChildCount();
        LogUtils.i(childCount + "孩子数量");
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof RadioChildren) {
                if (((RadioChildren) childAt).getOnEventChildrenClickListener() == null) {
                    ((RadioChildren) childAt).setOnEventChildrenClickListener(this);
                }
            }
        }
    }


    @Override
    public void radioChildrenClick(View v) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.equals(v)) {
                childAt.setSelected(true);
                if (mOnRadioChildChangeListener != null) {
                    mOnRadioChildChangeListener.onChangeChildren(v.getTag(),v);
                }
            } else {
                if (childAt.isSelected()) {
                    childAt.setSelected(false);
                }
            }
        }
    }

    public void setSelectChilren(@NonNull View view){
        radioChildrenClick(view);
    }

    public void setSelectChildren(@NonNull Object tag){
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (tag.equals(childAt.getTag())) {
                childAt.setSelected(true);
            } else {
                if (childAt.isSelected()) {
                    childAt.setSelected(false);
                }
            }
        }
    }

    public void setOnRadioChildChangeListener(OnRadioChildChangeListener l) {
        this.mOnRadioChildChangeListener = l;
    }

    public interface OnRadioChildChangeListener{
        void onChangeChildren(Object tag,View view);
    }

    public interface RadioChildren {
        void setOnEventChildrenClickListener(OnRadioChildrenCLickListener l);

        OnRadioChildrenCLickListener getOnEventChildrenClickListener();
    }

}
