package com.shqtn.b.enter;

import android.support.annotation.NonNull;

import com.shqtn.b.enter.result.BTakeBoxManifest;
import com.shqtn.base.bean.enter.TakeBoxGoods;
import com.shqtn.base.bean.enter.TakeBoxPlan;
import com.shqtn.base.bean.params.TakeBoxSubmitParams;
import com.shqtn.enter.presenter.AbstractTakeBoxChild;

/**
 * 创建时间:2018/1/19
 * 描述:
 *
 * @author ql
 */

public abstract class AbstractBTakeBoxChild<T, P extends TakeBoxSubmitParams> extends AbstractTakeBoxChild<T, P> {
    BTakeBoxManifest mOpearetManifest;

    public AbstractBTakeBoxChild(@NonNull TakeBoxPlan takeBoxGoodsPlan, @NonNull TakeBoxGoods takeBoxGoods) {
        super(takeBoxGoodsPlan, takeBoxGoods);
    }


    public BTakeBoxManifest getOpearetManifest() {
        return mOpearetManifest;
    }

    public void setOpearetManifest(BTakeBoxManifest opearetManifest) {
        this.mOpearetManifest = opearetManifest;
    }
}
