package com.shqtn.base.bean.enter;

import com.shqtn.base.bean.base.IManifest;


/**
 * Created by android on 2017/7/18.
 */

public class RackUpManifest implements IManifest {
    private String ccode;

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    @Override
    public String getManifest() {
        return ccode;
    }
}
