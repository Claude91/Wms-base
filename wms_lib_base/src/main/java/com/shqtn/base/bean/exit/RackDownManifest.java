package com.shqtn.base.bean.exit;

import com.shqtn.base.bean.base.IManifest;


/**
 * Created by android on 2017/7/18.
 */

public class RackDownManifest implements IManifest {
    private String docNo;//理货单;

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    @Override
    public String getManifest() {
        return docNo;
    }
}
