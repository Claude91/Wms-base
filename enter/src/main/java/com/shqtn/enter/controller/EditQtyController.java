package com.shqtn.enter.controller;

import com.shqtn.base.controller.view.IDialogView;

/**
 * Created by android on 2017/9/28.
 */

public interface EditQtyController {

    interface Presenter {

        void editQty();
    }

    public interface View extends IDialogView {

        void setEditQty(double qty);
    }
}
