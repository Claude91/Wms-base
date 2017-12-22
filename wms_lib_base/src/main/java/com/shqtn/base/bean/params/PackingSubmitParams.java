package com.shqtn.base.bean.params;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import com.shqtn.base.bean.exit.PackingLpnOrGoods;
import com.shqtn.base.info.code.help.CodeCallback;

/**
 * Created by android on 2017/8/2.
 */

public class PackingSubmitParams {

    @StringDef({FLAG_OVER, FLAG_SUBMIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SubmitFlag {
    }

    public static final String FLAG_SUBMIT = "0";
    public static final String FLAG_OVER = "1";

    /**
     * 参数：1.hikey; --包裝任务IKEY
     * 2.packageNo; --包装任务单号
     * 3.boxCode; --箱号
     * 4.packStatus; --箱号状态(0：未装箱；1：装箱中；2：装箱完成；3：已出库)
     * 5.private List<PackSkuVo> packSkuVos; --箱号或货品明细
     */

    private String flag;// 0 提交，1，装箱完成;

    private long hikey;
    private String packageNo;
    private String boxCode;
    private String packStatus;
    private ArrayList<PackingLpnOrGoods> packSkuVos;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public long getHikey() {
        return hikey;
    }

    public void setHikey(long hikey) {
        this.hikey = hikey;
    }

    public String getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getPackStatus() {
        return packStatus;
    }

    public void setPackStatus(String packStatus) {
        this.packStatus = packStatus;
    }

    public ArrayList<PackingLpnOrGoods> getPackSkuVos() {
        return packSkuVos;
    }

    public void setPackSkuVos(ArrayList<PackingLpnOrGoods> packSkuVos) {
        this.packSkuVos = packSkuVos;
    }


}
