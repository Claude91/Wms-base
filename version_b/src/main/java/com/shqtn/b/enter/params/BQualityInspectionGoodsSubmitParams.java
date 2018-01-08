package com.shqtn.b.enter.params;

import com.shqtn.base.bean.SerialNoVo;
import com.shqtn.base.bean.params.QualityInspectionGoodsSubmitParams;

import java.util.List;

/**
 * 创建时间:2018/1/8
 * 描述:
 *
 * @author ql
 */

public class BQualityInspectionGoodsSubmitParams  extends QualityInspectionGoodsSubmitParams{
    private List<SerialNoVo> sIList;//合格
    private List<SerialNoVo> sBList;//不良品
    private List<SerialNoVo> sDList;//报废序列号


    public List<SerialNoVo> getsIList() {
        return sIList;
    }

    public void setsIList(List<SerialNoVo> sIList) {
        this.sIList = sIList;
    }

    public List<SerialNoVo> getsBList() {
        return sBList;
    }

    public void setsBList(List<SerialNoVo> sBList) {
        this.sBList = sBList;
    }

    public List<SerialNoVo> getsDList() {
        return sDList;
    }

    public void setsDList(List<SerialNoVo> sDList) {
        this.sDList = sDList;
    }
}
