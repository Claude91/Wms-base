package com.shqtn.wms.info;

import com.shqtn.base.info.ApiUrl;

/**
 * 创建时间:2017/12/18
 * 描述:
 *
 * @author ql
 */

public class YTEnterApiUrl {

    /**
     * 收货列表- 通过货品筛选任务单号列表
     */
    public static String take_delivery_decode_goods_get_manifest_list;

    public static void changeUrl() {
        take_delivery_decode_goods_get_manifest_list = ApiUrl.BASE_URL + "rk/receivingAct/listAsnNoBySku";
    }
}
