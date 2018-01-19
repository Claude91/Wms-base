package com.shqtn.b.info;

import android.os.Bundle;

import com.shqtn.b.enter.ui.BQualityInspectionGoodsOperateActivity;
import com.shqtn.b.enter.ui.BRackUpGoodsOperateActivity;
import com.shqtn.enter.info.impl.EnterActivityLoadImpl;

/**
 * 创建时间:2018/1/8
 * 描述:
 *
 * @author ql
 */

public class BEnterActivityLoadImpl extends EnterActivityLoadImpl {
    @Override
    public Class getQualityInspectionGoodsOperateActivity(Bundle bundle) {

        return BQualityInspectionGoodsOperateActivity.class;
    }

    @Override
    public Class getRackUpGoodsOperateActivity(Bundle bundle) {
        return BRackUpGoodsOperateActivity.class;
    }
}
