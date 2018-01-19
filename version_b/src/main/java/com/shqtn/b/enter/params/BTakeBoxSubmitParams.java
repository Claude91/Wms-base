package com.shqtn.b.enter.params;

import com.shqtn.base.bean.params.TakeBoxSubmitParams;

import java.util.List;

/**
 * 创建时间:2018/1/10
 * 描述:
 *
 * @author ql
 */

public class BTakeBoxSubmitParams extends TakeBoxSubmitParams{
    private List<Stockserial> serialList;//序列号集合
    private Long ikey;

    public Long getIkey() {
        return ikey;
    }

    public void setIkey(Long ikey) {
        this.ikey = ikey;
    }

    public List<Stockserial> getSerialList() {
        return serialList;
    }

    public void setSerialList(List<Stockserial> serialList) {
        this.serialList = serialList;
    }
}
