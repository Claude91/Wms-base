package com.shqtn.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.shqtn.base.controller.presenter.ActivityResultCallback;
import com.shqtn.base.controller.presenter.IKeyDownPresenter;
import com.shqtn.base.controller.view.IAty;
import com.shqtn.base.controller.view.IDialogView;
import com.shqtn.base.listener.OnClickDeleteListener;
import com.shqtn.base.utils.ActivityUtils;
import com.shqtn.base.utils.DialogFactory;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.base.widget.dialog.AskMsgDialog;
import com.shqtn.base.widget.dialog.EditQuantityDialog;


/**
 * @author ql
 * @e-mail strive_bug@yeah.net
 * 基准Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, IDialogView, TitleView.OnClickToBackListener, IAty {
    public static final int RESULT_CODE_CLOSE = 0x11;
    public static final String INTENT_BUNDLE = "bundle";
    private ProgressDialog mProgressDialog;
    private AskMsgDialog mAskMsgDialog;

    private EditQuantityDialog mEditQuantityDialog;
    private AlertDialog mMsgDialog;
    private BaseFragment currentKJFragment;

    private IKeyDownPresenter mKeyDownPresenter;
    private ActivityResultCallback activityResultCallback;

    public SystemEditText setInputCode;

    public TitleView titleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActivityUtils.getInstance().addAty(this);
        super.onCreate(savedInstanceState);
        setRootView();
        otherInit();
        initializer();

    }

    public void otherInit() {

    }

    @Override
    public void toast(CharSequence msg) {
        ToastUtils.show(this, msg.toString());
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    // 仅仅是为了代码整洁点
    private void initializer() {
        initData();
        bindView();
        initWidget();
    }

    public void bindView() {

    }


    public void initWidget() {

    }


    public void initData() {

    }

    protected abstract void setRootView();

    /*********************************************************************/
    /*********************************************************************/
    /********************
     * 用于隐藏软键盘
     **********************************/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void clickBack() {
        finish();
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /********************************************************************/

    private long lastClickTime;
    private final static int LONG_TIME = 600;

    @Override
    public void onClick(View v) {
        widgetClick(v);
        if (System.currentTimeMillis() - lastClickTime > LONG_TIME) {
            widgetForbid(v);
            lastClickTime = System.currentTimeMillis();
        }

    }

    @Override
    public void onBackPressed() {
        clickBack();
    }

    public void widgetClick(View v) {
    }

    /**
     * 用于过滤点击
     *
     * @param v
     */
    public void widgetForbid(View v) {

    }

    @Override
    public void displayMsgDialog(String title, String msg) {
        if (!isFinishing()) {
            if (mMsgDialog == null) {
                mMsgDialog = DialogFactory.createMsgDialog(this);
            }
            mMsgDialog.setMessage(msg);
            mMsgDialog.setTitle(title);
            if (!mMsgDialog.isShowing()) {
                mMsgDialog.show();
            }
        }
    }

    @Override
    public void displayMsgDialog(String msg) {
        displayMsgDialog("", msg);
    }

    @Override
    public void cancelMsgDialog() {
        if (mMsgDialog != null) {
            if (mMsgDialog.isShowing()) {
                mMsgDialog.cancel();
            }
        }
    }


    @Override
    public void displayProgressDialog(String title, String msg) {
        if (!isFinishing()) {
            if (mProgressDialog == null) {
                mProgressDialog = DialogFactory.createProgressDialog(this);
            }
            mProgressDialog.setMessage(msg);
            mProgressDialog.setTitle(title);
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        }
    }

    @Override
    public void displayProgressDialog(String msg) {
        displayProgressDialog("", msg);
    }

    @Override
    public void cancelProgressDialog() {
        if (mProgressDialog != null && !isFinishing()) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.cancel();
            }
        }
    }

    @Override
    public void displayAskMsgDialog(String msg, AskMsgDialog.OnAskClickListener listener) {
        displayAskMsgDialog("", msg, listener);
    }

    @Override
    public void displayAskMsgDialog(String title, String msg, AskMsgDialog.OnAskClickListener listener) {
        if (!isFinishing()) {
            if (mAskMsgDialog == null) {
                mAskMsgDialog = DialogFactory.createAskMsgDialog(this);
            }
            if (!StringUtils.isEmpty(msg)) {
                mAskMsgDialog.setMsg(msg);
            }
            mAskMsgDialog.setTitle(title);
            mAskMsgDialog.setOnAskClickListener(listener);
            mAskMsgDialog.show();
        }
    }

    @Override
    public void cancelAskMsgDialog() {
        if (!isFinishing()) {
            if (mAskMsgDialog != null && !mAskMsgDialog.isShowing()) {
                mAskMsgDialog.cancel();
            }
        }
    }

    @Override
    public void displayEditQty(Double maxQty) {
        if (!isFinishing()) {
            checkEditQuantityDialog();
            mEditQuantityDialog.setMaxQuantity(maxQty);
            mEditQuantityDialog.show();
        }
    }

    @Override
    public void displayEditQty(EditQuantityDialog.OnResultListener resultListener) {
        if (!isFinishing()) {
            if (mEditQuantityDialog == null) {
                mEditQuantityDialog = DialogFactory.createEditQuantityDialog(this);
            }
            mEditQuantityDialog.setOnResultListener(resultListener);
            mEditQuantityDialog.show();
        }
    }

    @Override
    public void displayEditQty(double maxQty, EditQuantityDialog.OnResultListener resultListener) {
        if (!isFinishing()) {
            checkEditQuantityDialog();
            mEditQuantityDialog.setMaxQuantity(maxQty);
            mEditQuantityDialog.setOnResultListener(resultListener);
            mEditQuantityDialog.show();
        }
    }

    private void checkEditQuantityDialog() {
        if (mEditQuantityDialog == null) {
            mEditQuantityDialog = DialogFactory.createEditQuantityDialog(this);
        }
    }

    @Override
    public void cancelEditQty() {
        if (!isFinishing()) {
            if (mEditQuantityDialog != null) {
                if (mEditQuantityDialog.isShowing()) {
                    mEditQuantityDialog.cancel();
                }
            }
        }
    }


    public void changeFragment(int fm_content, BaseFragment targetFragment) {
        if (targetFragment == null) {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(fm_content, targetFragment, targetFragment.getClass().getName());
        }
        if (currentKJFragment != null && currentKJFragment.isAdded() && !currentKJFragment.isHidden()) {
            transaction.hide(currentKJFragment);
        }
        currentKJFragment = targetFragment;

        transaction.show(targetFragment).commit();
    }

    /**
     * 启动一个Activity
     *
     * @param aty
     */
    @Override
    public void startActivity(Class aty) {
        Intent intent = new Intent(this, aty);
        startActivity(intent);
    }

    @Override
    public void startActivity(Class clazz, Bundle bundle) {
        startActivity(clazz, bundle, 4);
    }

    public Bundle getBundle() {
        Intent intent = getIntent();
        if (intent == null) {
            return null;
        }
        return intent.getBundleExtra(INTENT_BUNDLE);
    }

    /**
     * 带有返回值
     *
     * @param clazz
     * @param requestCode
     */
    @Override
    public void startActivity(Class clazz, int requestCode) {
        startActivity(clazz, null, requestCode);
    }

    @Override
    public void startActivity(Class clazz, Bundle bundle, int requestCode) {
        startActivity(clazz, bundle, requestCode, -1);
    }

    @Override
    public void startActivity(Class clazz, Bundle bundle, int requestCode, int flags) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(INTENT_BUNDLE, bundle);
        if (flags != -1) {
            intent.setFlags(flags);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 控件获得焦点
     *
     * @param view
     */
    public void toFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mKeyDownPresenter != null && mKeyDownPresenter.onKeyDown(keyCode, event)) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_F1) {
            return onKeyF1();
        } else if (keyCode == KeyEvent.KEYCODE_F2) {
            return onKeyF2();
        } else if (keyCode == KeyEvent.KEYCODE_F3) {
            return onKeyF3();
        } else if (keyCode == KeyEvent.KEYCODE_F4) {
            return onKeyF4();
        } else if (keyCode == KeyEvent.KEYCODE_F5) {
            return onKeyF5();
        } else if (keyCode == KeyEvent.KEYCODE_F6) {
            return onKeyF6();
        } else if (keyCode == KeyEvent.KEYCODE_F7) {
            return onKeyF7();
        } else if (keyCode == KeyEvent.KEYCODE_F8) {
            return onKeyF8();
        } else if (keyCode == KeyEvent.KEYCODE_F9) {
            return onKeyF9();
        } else if (keyCode == KeyEvent.KEYCODE_F10) {
            return onKeyF10();
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyF10() {
        return false;
    }

    public boolean onKeyF9() {
        return false;
    }

    public boolean onKeyF8() {
        return false;
    }

    public boolean onKeyF7() {
        return false;
    }

    public boolean onKeyF6() {
        return false;
    }


    public boolean onKeyF5() {
        return false;
    }

    public boolean onKeyF4() {
        return false;
    }


    public boolean onKeyF3() {
        return false;
    }

    public boolean onKeyF2() {
        return false;
    }


    public boolean onKeyF1() {
        return false;
    }

    public void setOnKeyDownPresenter(IKeyDownPresenter p) {
        mKeyDownPresenter = p;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (activityResultCallback != null) {
            activityResultCallback.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setActivityResultCallback(ActivityResultCallback activityResultCallback) {
        this.activityResultCallback = activityResultCallback;
    }

    @Override
    public void displayEditQtyDelBtn(OnClickDeleteListener l) {
        checkEditQuantityDialog();
        mEditQuantityDialog.setOnClickDeleteListener(l);
        mEditQuantityDialog.showBtnDelete();
    }

    @Override
    public void hideEditQtyDelBtn() {
        checkEditQuantityDialog();
        mEditQuantityDialog.hideBtnDelete();
        mEditQuantityDialog.setOnClickDeleteListener(null);
    }

}
