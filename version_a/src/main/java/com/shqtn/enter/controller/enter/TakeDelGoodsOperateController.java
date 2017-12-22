package com.shqtn.enter.controller.enter;

import com.shqtn.base.controller.presenter.BasePresenter;
import com.shqtn.base.controller.view.IDialogView;

/**
 * Created by android on 2017/9/26.
 */

public interface TakeDelGoodsOperateController {

    public interface View extends IDialogView {
        void setOperateManifest(String manifest);

        void setGoodsName(String name);

        void setIsInputBatchNo(boolean isInputBatchNo);

        void setInputBatchNo(String s);

        void setTakeQty(String takeQty);

        void setPlanQty(String planQty);

        String getTakeQty();

        void setInputBatchNoHint(String hint);

        void setUnit(String unitName);

        String getEtInputBatchNo();

        /**
         * 设置需要解码的标志
         *
         * @param type
         */
        void setDecodeType(int... type);
    }

    public interface Presenter extends BasePresenter {

        void submit();

        void clickToEditQty();


    }

    public interface Model {

    }
}
