package com.shqtn.enter.controller;

import com.shqtn.base.CommonAdapter;
import com.shqtn.base.controller.presenter.BasePresenter;
import com.shqtn.base.controller.view.IAty;
import com.shqtn.base.controller.view.IDialogView;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.model.BaseModel;
import com.shqtn.enter.controller.impl.DecodeCallbackImpl;

/**
 * Created by android on 2017/9/28.
 */

public interface LpnSubmitController {

    interface View extends IDialogView {
        void closeActivity();

        void setLpnNo(String lpnNo);

        void setRackCode(String poscode);

        void setRackCodeLabel(String label);

        void setLpnNoLabel(String label);

        String getRackCode();

        void setListViewAdapter(CommonAdapter adapter);

        void setDecodeType(int ...types);

    }

    abstract class AbstractPresenter extends DecodeCallbackImpl implements BasePresenter{
        public abstract void setView(View view);

        public abstract void setAty(IAty aty);

        @Override
        public void decodeGoods(CodeGoods goods) {
            super.decodeGoods(goods);
        }

        @Override
        public void decodeManifest(CodeManifest manifest) {
            super.decodeManifest(manifest);
        }

        @Override
        public void decodeOther(AllotBean code) {
            super.decodeOther(code);
        }

        public abstract void submit();

    }
}
