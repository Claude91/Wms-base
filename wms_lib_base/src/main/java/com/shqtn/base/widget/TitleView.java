package com.shqtn.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shqtn.base.R;


/**
 * Created by android on 2017/7/12.
 */

public class TitleView extends FrameLayout {
    private ImageButton ivBtnBack, ivBtnRight;
    private TextView tvTitle, tvRight;

    private OnClickToBackListener mOnClickToBackListener;
    private OnRightIconClickListener mOnRightIconClickListener;
    private OnRightTextClickListener mOnRightTextClickListener;

    public TitleView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setBackgroundResource(R.color.colorBlue);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView, defStyleAttr, 0);
        int count = typedArray.getIndexCount();

        int backIcon = -1;
        int rightIcon = -1;
        String title = null;
        String rightContent = null;
        boolean isHideBackIcon = false;
        boolean isShowRightIcon = false;

        for (int i = 0; i < count; i++) {
            int id = typedArray.getIndex(i);
            if (id == R.styleable.TitleView_title) {
                title = typedArray.getString(id);

            } else if (id == R.styleable.TitleView_rightText) {
                rightContent = typedArray.getString(id);

            } else if (id == R.styleable.TitleView_backIcon) {
                backIcon = typedArray.getResourceId(id, backIcon);

            } else if (id == R.styleable.TitleView_rightIcon) {
                rightIcon = typedArray.getResourceId(id, rightIcon);

            } else if (id == R.styleable.TitleView_hideBackIcon) {
                isHideBackIcon = typedArray.getBoolean(id, isHideBackIcon);

                isShowRightIcon = typedArray.getBoolean(id, isShowRightIcon);
            } else if (id == R.styleable.TitleView_showRightIcon) {
                isShowRightIcon = typedArray.getBoolean(id, isShowRightIcon);
            }
        }

        initView(context);

        if (rightIcon != -1) {
            ivBtnRight.setImageResource(rightIcon);
        }
        if (isShowRightIcon) {
            ivBtnRight.setVisibility(VISIBLE);
        }
        if (isHideBackIcon) {
            ivBtnBack.setVisibility(GONE);
        }

        if (backIcon != -1) {
            ivBtnBack.setImageResource(backIcon);
        }
        tvTitle.setText(title);

        if (rightContent != null)
            tvRight.setText(rightContent);

        typedArray.recycle();
        ivBtnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickToBackListener != null) {
                    mOnClickToBackListener.clickBack();
                }
            }
        });
        ivBtnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRightIconClickListener != null) {
                    mOnRightIconClickListener.onOnClick();
                }
            }
        });

    }


    public void setOnRightTextClickListener(OnRightTextClickListener l) {
        this.mOnRightTextClickListener = l;
        setTvRightOnClickListener();

    }

    private void setTvRightOnClickListener() {
        if (mOnRightTextClickListener != null)
            tvRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnRightTextClickListener != null) {
                        mOnRightTextClickListener.onClickTitleRightText();
                    }
                }
            });
    }

    private void initView(Context context) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewGroup = li.inflate(R.layout.view_title, null, false);
        ivBtnBack = (ImageButton) viewGroup.findViewById(R.id.v_title_btn_back_icon);
        tvRight = (TextView) viewGroup.findViewById(R.id.v_title_tv_right);
        tvTitle = (TextView) viewGroup.findViewById(R.id.v_title_tv_title);
        ivBtnRight = (ImageButton) viewGroup.findViewById(R.id.v_title_btn_right);
        tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        addView(viewGroup);
    }


    public void setRightIconVisibility(int visibility) {
        ivBtnRight.setVisibility(visibility);
    }

    public void setRightIcon(int iconId) {
        ivBtnRight.setImageResource(iconId);
        if (ivBtnRight.getVisibility() != VISIBLE) {
            ivBtnRight.setVisibility(VISIBLE);
        }
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setBackIcon(int iconId) {
        ivBtnBack.setImageResource(iconId);
    }

    public void setRightIconOnClickListener(OnClickListener l) {
        ivBtnRight.setOnClickListener(l);
    }

    public void setOnToBackClickListener(OnClickToBackListener l) {
        mOnClickToBackListener = l;
    }

    public void setOnRightIConCLickListener(OnRightIconClickListener l) {
        mOnRightIconClickListener = l;
    }


    public void setBackgroundColor(int color) {
        setBackgroundColor(color);
    }

    public void setRightText(String rightText) {
        this.tvRight.setText(rightText);
    }

    public void setRightTextVisibility(int v) {
        tvRight.setVisibility(v);
    }

    public interface OnRightTextClickListener {
        void onClickTitleRightText();
    }

    public interface OnRightIconClickListener {
        void onOnClick();
    }

    public interface OnClickToBackListener {
        void clickBack();
    }

}
