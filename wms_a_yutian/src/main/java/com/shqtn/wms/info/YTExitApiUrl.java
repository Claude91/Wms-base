package com.shqtn.wms.info;

import com.shqtn.base.info.ApiUrl;

/**
 * 创建时间:2017/12/28
 * 描述:
 *
 * @author ql
 */

public class YTExitApiUrl {

    private static final String base_depot_out = "os/deliver/";
    private static final String base_rack_down = "os/offShelf/";

    public static String rack_down_manifest_by_goods;

    public static void changeUrl() {
        String baseUrl = ApiUrl.BASE_URL;
        rack_down_manifest_by_goods = String.format("%s%s%s", baseUrl, base_rack_down, "pageDocNoBySkuItem");

    }
}
