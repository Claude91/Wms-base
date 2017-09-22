package com.shqtn.enter.controller;

import com.shqtn.base.controller.presenter.BasePresenter;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.base.controller.view.IBaseView;

/**
 * Created by android on 2017/9/21.
 */

public interface CodeController {

    public interface View extends IBaseView {


    }

    public interface DecodeCallback {

        void decodeLpn(CodeLpn lpn);

        void decodeRack(CodeRack rack);

        void decodeGoods(CodeGoods goods);

        void decodeManifest(CodeManifest manifest);

        void decodeOther(AllotBean code);
    }

    public interface Presenter extends BasePresenter{

        void toDecode(String code);

        /**
         * 设置需要解码的标志
         *
         * @param type
         */
        void setDecodeType(int... type);

        void setDecodeCallback(DecodeCallback decodeCallback);
    }
}
