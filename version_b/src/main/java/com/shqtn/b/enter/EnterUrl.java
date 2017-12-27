package com.shqtn.b.enter;

import com.shqtn.base.info.ApiUrl;

/**
 * 创建时间:2017/12/25
 * 描述:
 *
 * @author ql
 */

public class EnterUrl {

    //收货基准
    public static final String base_take_del = "rk/receivingAct/";
    /**
     * 通过货品查询 任务单据号
     */
    public static String take_del_search_manifest_by_goods;

    /**
     * 查询收货任务单明细
     */
    public static String take_del_search_manifest_details;


    public static void changeUrl(String baseUrl) {
        take_del_search_manifest_by_goods = String.format("%s%s%s", ApiUrl.BASE_URL, base_take_del, "listBySku");
        take_del_search_manifest_details = String.format("%s%s%s", ApiUrl.BASE_URL, base_take_del, "data");

    }
}
