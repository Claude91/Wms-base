package com.shqtn.base.bean.enter;

import com.shqtn.base.bean.base.IManifest;


/**
 * Created by android on 2017/7/14.
 */

public class TakeDeliveryManifest implements IManifest {
    //{"rs":true,"total":14,"rows":[{"asnNo":"SH20170306001"},{"asnNo":"SH20170301005"},{"asnNo":"SH20170301004"},{"asnNo":"SH20170301003"},{"asnNo":"SH20170301002"},{"asnNo":"SH20170301001"},{"asnNo":"SH20170228001"},{"asnNo":"SH20170221002"},{"asnNo":"SH20170217007"},{"asnNo":"SH20170217002"},{"asnNo":"SH20170215005"},{"asnNo":"SH20170215003"},{"asnNo":"SH20170215002"},{"asnNo":"SH20170215001"}]}
    private String asnNo;

    public String getAsnNo() {
        return asnNo;
    }

    public void setAsnNo(String asnNo) {
        this.asnNo = asnNo;
    }

    @Override
    public String getManifest() {
        return asnNo;
    }
}
