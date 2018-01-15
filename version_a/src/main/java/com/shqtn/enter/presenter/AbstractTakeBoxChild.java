package com.shqtn.enter.presenter;

import android.support.annotation.NonNull;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.shqtn.base.bean.LpnCheck;
import com.shqtn.base.bean.enter.TakeBoxGoods;
import com.shqtn.base.bean.enter.TakeBoxPlan;
import com.shqtn.base.bean.params.TakeBoxSubmitParams;

import java.util.ArrayList;

/**
 * Created by android on 2017/11/21.
 */

public abstract class AbstractTakeBoxChild<T, P extends TakeBoxSubmitParams> {
    private String boxNo;//最外层包装 编码
    private ArrayList<T> childs = new ArrayList<>();
    public TakeBoxPlan operateGoodsPlan;
    public TakeBoxGoods operateGoods;
    public String manifest;
    public ArrayList<String> childsSerials;

    public AbstractTakeBoxChild(@NonNull TakeBoxPlan takeBoxGoodsPlan, @NonNull TakeBoxGoods takeBoxGoods) {
        this.operateGoodsPlan = takeBoxGoodsPlan;
        this.operateGoods = takeBoxGoods;
    }


    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public String getBoxNo() {
        return boxNo;
    }

    /**
     * 添加所要包装的
     *
     * @param t
     */
    public void addChildren(T t) {
        childs.add(t);
    }

    public ArrayList<T> getChilds() {
        return childs;
    }

    public void removeChildren(int position) {
        childs.remove(position);

    }

    public void removeChildren(T t) {
        childs.remove(t);
    }

    public int getTakeingQty() {
        return operateGoodsPlan.getInBoxingQty();
    }

    public int getTakeOverQty() {
        return operateGoodsPlan.getQty();
    }

    public int getUntakeOverQty() {
        return operateGoodsPlan.getUnBoxedQty();
    }

    /**
     * 当前显示的列表集合
     *
     * @return
     */
    public abstract BaseAdapter getAdapter();

    public abstract int getDecodeType();

    public abstract String getHintText();

    public abstract void setListView(ListView lv);

    public abstract boolean isCanAdd(T t);

    public abstract P getOverSubmit();

    public abstract P getSubmit();

    public void setChilds(ArrayList chlids) {
        this.childs = chlids;
    }

    public String getManifest() {
        return manifest;
    }

    public void setManifest(String manifest) {
        this.manifest = manifest;
    }

    public boolean isAddSerial() {
        return false;
    }

    /**
     * 是否添加序列号button
     *
     * @return
     */
    public boolean isAddSerialButton() {
        return false;
    }

    public void setChildsSerials(ArrayList<String> childsSerials) {
        this.childsSerials = childsSerials;
    }

    public ArrayList<String> getAddSerial() {
        return childsSerials;
    }

    public abstract void addOverChild(LpnCheck lpnStatus);

    /**
     * 可添加序列号数量
     *
     * @return
     */
    public double getAddSerialSize() {
        return 0.0d;
    }
}
