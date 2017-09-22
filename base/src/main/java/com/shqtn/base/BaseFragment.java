package com.shqtn.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Administrator on 2016-11-4.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int viewId = getCreateViewId();
        if (viewId != -1){
            return inflater.inflate(viewId,container,false);
        }else {
           return getCreateView();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        bindView(view);
        initWidget(view);
    }

    public void initData() {

    }

    public void initWidget(View view) {

    }

    public void bindView(View view) {

    }

    @Override
    public void onClick(View view) {

    }

    public void startActivity(Class aty, Bundle bundle){
        Intent intent = new Intent(getActivity(),aty);
        intent.putExtra(BaseActivity.INTENT_BUNDLE,bundle);
        startActivity(intent);
    }

    public void startActivity(Class aty ,Bundle bundle ,int requestCode){
        Intent intent = new Intent(getActivity(),aty);
        intent.putExtra(BaseActivity.INTENT_BUNDLE,bundle);
        startActivityForResult(intent,requestCode);
    }

    public View getCreateView() {
        return null;
    }

    public int getCreateViewId() {
        return -1;
    }
}
