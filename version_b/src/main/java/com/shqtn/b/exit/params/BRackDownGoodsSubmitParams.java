package com.shqtn.b.exit.params;

import com.shqtn.base.bean.SerialNoVo;
import com.shqtn.base.bean.params.RackDownGoodsSubmitParams;

import java.util.List;

/**
 * 创建时间:2018/1/24
 * 描述:
 *
 * @author ql
 */

public class BRackDownGoodsSubmitParams extends RackDownGoodsSubmitParams {

    private List<SerialNoVo> serials;

    public List<SerialNoVo> getSerials() {
        return serials;
    }

    public void setSerials(List<SerialNoVo> serials) {
        this.serials = serials;
    }
}
