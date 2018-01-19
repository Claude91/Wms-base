package com.shqtn.b.enter.params;

import com.shqtn.base.bean.SerialNoVo;
import com.shqtn.base.bean.params.RackUpGoodsSubmitParams;

import java.util.ArrayList;

/**
 * 创建时间:2018/1/19
 * 描述:
 *
 * @author ql
 */

public class BRackUpGoodsSubmitParams extends RackUpGoodsSubmitParams {
    private ArrayList<SerialNoVo> serialList;

    public ArrayList<SerialNoVo> getSerialList() {
        return serialList;
    }

    public void setSerialList(ArrayList<SerialNoVo> serialList) {
        this.serialList = serialList;
    }
}
