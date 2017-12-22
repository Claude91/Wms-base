package com.shqtn.base.bean.exit;

import com.shqtn.base.bean.base.IManifest;

/**
 * Created by android on 2017/8/4.
 */
public class PackingManifest implements IManifest {
    private String packageNo;

    public String getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }

    @Override
    public String getManifest() {
        return packageNo;
    }
}
