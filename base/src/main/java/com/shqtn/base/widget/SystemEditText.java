package com.shqtn.base.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shqtn.base.R;
import com.shqtn.base.clipboard.ClipBoardManager;
import com.shqtn.base.clipboard.TextChangeManager;
import com.shqtn.base.utils.SystemEditModeUtils;

/**
 * 功能：用于系统录入的输入框
 * Created by Administrator on 2016-12-20.
 */
public class SystemEditText extends FrameLayout implements View.OnClickListener, TextChangeManager.IChangeView, TextChangeManager.OnTimeAfterTextChangeListener, ClipBoardManager.OnClipListener {
    public final static long FLITE_DOUBLE_CLICK_TIME = 600;
    private TextView vInputMode;
    private View vInputModeGroup;
    private EditText et;
    private View vSearch;


    public final static int MODE_AUTO = 0;//自动
    public final static int MODE_HAND = 1;//手动
    public final static int MODE_SYSTEM = 2;//系统录入

    private AlertDialog mModeSelectDialog;
    private TextChangeManager mTextChangeManager;

    private int mModeType;

    private OnToTextSearchListener mOnToTextSearchListener;
    private String text;
    private OnTextChangeListener mOnTextChangeListener;

    private boolean focusActivity;//附在当前Activity中是否 有焦点

    public SystemEditText(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SystemEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SystemEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mModeType = SystemEditModeUtils.getMode(context, MODE_AUTO);
        createTextChangeManager();
        initChildrenView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SystemEditText, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.SystemEditText_hintContent) {
                et.setHint(typedArray.getString(index));
            }
        }
        typedArray.recycle();

        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //这里的s表示改变之前的内容，通常start和count组合，可以在s中读取本次改变字段中被改变的内容。而after表示改变后新的内容的数量。
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //这里的s表示改变之后的内容，通常start和count组合，可以在s中读取本次改变字段中新的内容。而before表示被改变的内容的数量。
                mTextChangeManager.textChange(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mOnTextChangeListener != null) {
                    mOnTextChangeListener.afterTextChanged(s.toString());
                }
                if (mModeType == MODE_AUTO || mModeType == MODE_SYSTEM) {
                    mTextChangeManager.startReaderTime();
                }
            }
        });


        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO
                        || (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        || event.getKeyCode() == KeyEvent.KEYCODE_MEDIA_NEXT))) {
//隐藏软键盘

                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }
                    toSearch();
                    return true;
                }
                return false;
            }
        });

    }


    public void setOnToTextSearchListener(OnToTextSearchListener mOnToTextSearchListener) {
        this.mOnToTextSearchListener = mOnToTextSearchListener;
    }

    public void setOnTextChangeListener(OnTextChangeListener l) {
        mOnTextChangeListener = l;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ClipBoardManager.getInstance().removeListener(this);
    }

    private static final String TAG = "SystemEditText";

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
    }

    private void initChildrenView(Context context) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.view_system_edit, null, false);


        et = view.findViewById(R.id.view_system_edit_et);
        vInputMode = view.findViewById(R.id.view_system_edit_tv_input_mode);
        vInputModeGroup = view.findViewById(R.id.view_system_edit_input_mode);
        vSearch = view.findViewById(R.id.view_system_edit_search);

        vInputModeGroup.setOnClickListener(this);
        vSearch.setOnClickListener(this);
        setModeText(mModeType);
        addView(view);
    }

    private void createTextChangeManager() {
        mTextChangeManager = new TextChangeManager();
        mTextChangeManager.setIChangeView(SystemEditText.this);
        mTextChangeManager.setOnTimeAfterTextChangeListener(SystemEditText.this);
    }


    private void toSearch() {
        if (mOnToTextSearchListener != null)
            mOnToTextSearchListener.onSearchText(et.getText().toString());
    }

    private long lastClickTime;

    @Override
    public void onClick(View view) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - lastClickTime > FLITE_DOUBLE_CLICK_TIME) {
            lastClickTime = nowTime;

            int id = view.getId();
            if (id == R.id.view_system_edit_search) {
                toSearch();
            } else if (id == R.id.view_system_edit_input_mode) {
                if (mModeSelectDialog == null) {
                    mModeSelectDialog = new AlertDialog.Builder(getContext())
                            .setTitle("输入模式选择")
                            .setItems(new String[]{"自动", "手动", "系统录入"}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i == 0) {
                                        mModeType = MODE_AUTO;
                                    } else if (i == 1) {
                                        mModeType = MODE_HAND;
                                    } else if (i == 2) {
                                        mModeType = MODE_SYSTEM;
                                    }
                                    setModeText(mModeType);
                                }
                            })
                            .show();
                } else {
                    mModeSelectDialog.show();
                }
            }

        }
    }

    public void setMode(int mode) {
        mModeType = mode;
    }

    private void setModeText(int modeType) {
        switch (modeType) {
            case MODE_AUTO:
                vInputMode.setText("自动");
                mTextChangeManager.setOnTimeAfterTextChangeListener(this);
                ClipBoardManager.getInstance().addListener(this);
                break;
            case MODE_HAND:
                vInputMode.setText("手动");
                mTextChangeManager.setOnTimeAfterTextChangeListener(null);
                ClipBoardManager.getInstance().removeListener(this);
                break;
            case MODE_SYSTEM:
                vInputMode.setText("系统录入");
                mTextChangeManager.setOnTimeAfterTextChangeListener(this);
                ClipBoardManager.getInstance().addListener(this);
                break;
            default:
        }
        SystemEditModeUtils.saveMode(getContext(), modeType);
    }

    public void setHintText(String hint) {
        et.setHint(hint);
    }

    public void setHintText(int s) {
        et.setHint(s);
    }

    @Override
    public void clearText() {
        et.setText("");
    }

    @Override
    public String getText() {
        return et.getText().toString();
    }

    @Override
    public void onTextChange(String content) {
        if (!isCanReaderText()) {
            return;
        }
        toSearch();
    }

    @Override
    public void onClipContent(String content) {
        if (!isCanReaderText()) {
            return;
        }

        et.setText(content);
        if (mTextChangeManager.isPassRead(content, System.currentTimeMillis())) {
            return;
        }
        toSearch();
    }

    public void setText(String text) {
        this.text = text;
        et.setText(text);
    }

    public boolean isCanReaderText() {
        return focusActivity;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        focusActivity = hasWindowFocus;

    }

    public interface OnToTextSearchListener {
        void onSearchText(String content);
    }

    public interface OnTextChangeListener {
        void afterTextChanged(String text);
    }
}
