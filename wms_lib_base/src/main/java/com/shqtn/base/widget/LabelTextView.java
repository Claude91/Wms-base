package com.shqtn.base.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shqtn.base.R;


/**
 * Created by android on 2017/7/14.
 */

public class LabelTextView extends LinearLayout {
    private final String symbol_def = ":";
    private static final int NORMAL_TEXT_SIZE = 16;
    private static final int NORMAL_MARGIN_LEFT = 4;

    private static final int LABEL_STYLE_BOLD = 100;
    private TextView tvLabel;
    private TextView tvContent;
    private TextView tvSymbol;

    private int labelColor;
    private int lobelSize;
    private int textColor;
    private int textSize;

    public LabelTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public LabelTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public LabelTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setOrientation(HORIZONTAL);
        tvLabel = new TextView(context);
        tvContent = new TextView(context);
        tvSymbol = new TextView(context);

        tvSymbol.setText(symbol_def);

        float normalMarginLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, NORMAL_MARGIN_LEFT, getResources().getDisplayMetrics());
        setTextSizeAll(NORMAL_TEXT_SIZE);
        setTextMarginLeft((int) normalMarginLeft);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LabelTextView, defStyleAttr, 0);
        int cont = ta.getIndexCount();
        for (int i = 0; i < cont; i++) {
            int index = ta.getIndex(i);
            if (index == R.styleable.LabelTextView_color) {
                int color = ta.getColor(index, 0);
                setTextColorAll(color);

            } else if (index == R.styleable.LabelTextView_labelStyle) {

            } else if (index == R.styleable.LabelTextView_labelColor) {
                int labelColor = ta.getColor(index, 0);
                setLabelColor(labelColor);

            } else if (index == R.styleable.LabelTextView_labelSize) {
                float labelSize = ta.getDimension(index, NORMAL_TEXT_SIZE);
                setLabelSize(TypedValue.COMPLEX_UNIT_PX, labelSize);

            } else if (index == R.styleable.LabelTextView_labelText) {
                String labelText = ta.getString(index);
                setLabelText(labelText);

            } else if (index == R.styleable.LabelTextView_text) {
                String text = ta.getString(index);
                tvContent.setText(text);

            } else if (index == R.styleable.LabelTextView_textSize) {
                float textSize = ta.getDimension(index, NORMAL_TEXT_SIZE);
                setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

            } else if (index == R.styleable.LabelTextView_textColor) {
                int textColor = ta.getColor(index, 0);
                setTextColor(textColor);

            } else if (index == R.styleable.LabelTextView_size) {
                float size = ta.getDimension(index, NORMAL_TEXT_SIZE);
                setTextSizeAll(TypedValue.COMPLEX_UNIT_PX, size);

            } else if (index == R.styleable.LabelTextView_textMarginLeft) {
                int textMarginLeft = (int) ta.getDimension(index, normalMarginLeft);
                setTextMarginLeft(textMarginLeft);

            } else if (index == R.styleable.LabelTextView_symbolTextMarginLeft) {
                int symbolTextMarginLeft = (int) ta.getDimension(index, normalMarginLeft);
                setTextMarginLeft(symbolTextMarginLeft);

            } else if (index == R.styleable.LabelTextView_symbolText) {
                String symbolText = ta.getString(index);
                tvContent.setText(symbolText);

            }
        }
        ta.recycle();

        tvLabel.setSingleLine();
        tvContent.setSingleLine();
        tvSymbol.setSingleLine();

        addView(tvLabel);
        addView(tvSymbol);
        addView(tvContent);

        invalidate();
    }

    public void setLabelColor(int labelColor) {
        tvLabel.setTextColor(labelColor);
    }

    public void setLabelSize(float labelSize) {
        tvLabel.setTextSize(labelSize);
    }

    public void setLabelSize(int unit, float labelSize) {
        tvLabel.setTextSize(unit, labelSize);
    }

    public void setTextSize(float textSize) {
        tvContent.setTextSize(textSize);
    }

    public void setTextSize(int unit, float textSize) {
        tvContent.setTextSize(unit, textSize);
    }

    public void setTextColor(int textColor) {
        tvContent.setTextColor(textColor);
    }

    public void setSymbol(String symbol) {
        tvSymbol.setText(symbol);
    }

    public void setLabelText(String labelText) {
        tvLabel.setText(labelText);
    }


    public void setText(CharSequence text) {
        tvContent.setText(text);
    }

    public void setTextMarginLeft(int textMarginLeft) {
        LayoutParams textLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLp.leftMargin = textMarginLeft;
        tvContent.setLayoutParams(textLp);
    }

    public void setTextSizeAll(float size) {
        tvLabel.setTextSize(size);
        tvContent.setTextSize(size);
        tvSymbol.setTextSize(size);
    }

    public void setTextSizeAll(int unit, float size) {
        tvLabel.setTextSize(unit, size);
        tvContent.setTextSize(unit, size);
        tvSymbol.setTextSize(unit, size);
    }

    public void setTextColorAll(int color) {
        tvLabel.setTextColor(color);
        tvContent.setTextColor(color);
        tvSymbol.setTextColor(color);
    }

    public String getText() {
        return tvContent.getText().toString();
    }
}
