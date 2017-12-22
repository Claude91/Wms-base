package com.shqtn.enter.controller.impl;

import com.shqtn.base.widget.dialog.EditQuantityDialog;
import com.shqtn.enter.controller.EditQtyController;

/**
 * Created by android on 2017/9/28.
 */
public class EditQtyPresenterImpl implements EditQtyController.Presenter {
    private Double mMaxQty;

    private EditQtyController.View mView;
    private EditQuantityDialog.OnResultListener resultListener = new EditQuantityDialog.OnResultListener() {
        @Override
        public void clickOk(double quantity) {
            mView.setEditQty(quantity);
            mView.cancelEditQty();
        }

        @Override
        public void clickCancel() {
            mView.cancelEditQty();
        }
    };

    public EditQtyPresenterImpl(Double mMaxQty, EditQtyController.View mView) {
        this.mMaxQty = mMaxQty;
        this.mView = mView;
    }

    @Override
    public void editQty() {
        mView.displayEditQty(mMaxQty, resultListener);
    }
}
