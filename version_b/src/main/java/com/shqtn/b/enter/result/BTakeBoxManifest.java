package com.shqtn.b.enter.result;

import com.shqtn.base.bean.base.IManifest;

/**
 * 创建时间:2018/1/10
 * 描述:
 *
 * @author ql
 */

public class BTakeBoxManifest implements IManifest {
    // TODO: 2018/1/10 未编写返回值
    private String docNo;

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
