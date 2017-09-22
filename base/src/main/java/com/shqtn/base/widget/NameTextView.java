package com.shqtn.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.shqtn.base.R;


/**
 * Created by Administrator on 2016-12-20.
 */
public class NameTextView extends TextView {
    public String name;
    private String content = "";

    public NameTextView(Context context) {
        this(context, null);
    }

    public NameTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NameTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NameTextView, defStyleAttr, -1);
        String content = getText().toString();
        if (name == null)
            name = typedArray.getString(R.styleable.NameTextView_name);
        if (name == null) {
            name = "";
        }
        setSingleLine();

        setText(name, content);
        typedArray.recycle();
    }

    /**
     * 设置标题
     *
     * @param name
     * @param content
     */
    public void setText(String name, String content) {
        if (name == null) {
            this.name = "";
        } else {
            this.name = name;
        }
        setText(content);
    }

    public void setName(String name) {
        this.name = name;
        if (content == null) content = "";
        setText(name, content);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text == null) {
            text = "";
        }
        if (name != null) {
            content = text.toString();
            text = name + ":" + text;
        }
        super.setText(text, type);
    }
}
