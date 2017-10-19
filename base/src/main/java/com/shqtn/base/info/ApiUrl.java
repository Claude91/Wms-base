package com.shqtn.base.info;

/**
 * 用于存放基础网址
 * Created by Administrator on 2016-11-16.
 */
public class ApiUrl {

    //http://192.168.0.171:8089/shqtn-wms-rf/
    public static String HTTP = "http://";

    public static String IP = "192.168.0.252";

    public static String POST = "8084";

    public static String BASE_ = "/shqtn-wms-rf/api/rf/wms/";

    public static String BASE_URL = HTTP + IP + ":" + POST + BASE_;

    /**
     * 下载 app 地址，在地址后面加上需要跟新的版本号
     */
    public static String file_app_download = BASE_URL + "sys/fdown/";

    /**
     * 解码
     * 用于扫描二维码的基准
     */
    public static String URL_BASE_CODE = BASE_URL + "base/barcode/decode";
    /**
     * 解码 专门用于装箱 操作的解码操作
     */
    public static String URL_LPN_CODE = BASE_URL + "base/barcode/decodePack";
    /**
     * 登录
     */
    public static String URL_LOGIN = BASE_URL + "sec/auth/in";

    public static void changeBase(String ip, String post) {
        changeBase(ip, post, BASE_);
    }

    public static void changeBase(String ip, String post, String base_) {
        IP = ip;
        POST = post;
        if (!BASE_.equals(base_)) {
            BASE_ = base_;
        }
        BASE_URL = "http://" + ip + ":" + post + base_;

        URL_BASE_CODE = BASE_URL + "base/barcode/decode";

        URL_LOGIN = BASE_URL + "sec/auth/in";

        URL_LOGOUT = BASE_URL + "sec/auth/out";

        URL_CODE_FLAG_LIST = BASE_URL + "base/fr/list";


        URL_LPN_CODE = BASE_URL + "base/barcode/decodePack";
        /**
         * 仓库查询
         */
        URL_DEPOT = BASE_URL + "base/wh/list";
        /**
         * 系统检测是否需要更新
         */
        update_app = BASE_URL + "auth/verify/verifyVersion";

        /**
         * 托盘管理中的 扫描完货品需要提交  组托提交的网址；
         */
        URL_PM_PALLET_IN_HAVE_CODE_SUBMIT = BASE_URL + "rk/pallet/generatePallet";

        /**
         * 托盘管理 中 用于检测LpnNo
         */
        URL_PM_PALLET_NO_CHECK = BASE_URL + "rk/pallet/checkLpnNo";

        /**
         * 根据托盘号查询托盘内内容：
         */
        URL_PALLET_QUERY_CODE = BASE_URL + "rk/pallet/list";

        /**
         * 托盘管理取消入托
         */
        URL_PM_PALLET_QUERY_CANCEL = BASE_URL + "rk/pallet/cancelPallet";
        /**
         * 托盘管理，没有托盘号 进行入托；
         */
        URL_PM_PALLET_IN_OF_NOT_CODE_SUBMIT = BASE_URL + "rk/pallet/generateNoPallet";


        /**
         * 托盘管理，拆托
         */
        URL_PM_PALLET_OUT = BASE_URL + "rk/pallet/ungeneratePallet";

        /**
         * 托盘管理中 查询商品详情
         */
        URL_PM_PALLET_QUERY_PRODUCT_DETAILS = BASE_URL + "rk/pallet/getSku";

        /**
         * 用于质检查询用
         */
        URL_QUALITY_CHECKING_QUERY_DETAILS = BASE_URL + "rk/iqcAct/list";

        /**
         * 用于质检提交
         */
        URL_QUALITY_CHECKING_SUBMIT = BASE_URL + "rk/iqcAct/drirectAct";

        /**
         * 查询不良品规则列表
         * 在质检操作中，包含不良品操作规则
         */
        URL_QUALITY_CHECKING_QCREASON_LIST = BASE_URL + "base/qcreason/list";


        /**
         * 收货单扫描接口
         */
        URL_TAKE_DELIVERY_GOODS_LIST = BASE_URL + "rk/receivingAct/list/";

        /**
         * 收货页面接口，用于查询当前仓库中的货品信息
         */
        URL_TAKE_DELIVERY_DEPOT_MANIFEST_LIST = BASE_URL + "rk/receivingAct/pageList";
        /**
         * 收货 单品提交
         */
        URL_TAKE_DELIVERY_GOODS_SUBMIT = BASE_URL + "rk/receivingAct/directAct";

        /**
         * 收货 LPN提交
         */
        URL_TAKE_DELIVERY_LPN_SUBMIT = BASE_URL + "rk/receivingAct/directActLpn";

        /**
         * 上架  扫描货单号,货品
         */
        URL_RACK_UP_DEPOT_GOODS_LIST = BASE_URL + "tallying/pageList";
        /**
         * 上架 扫描货单时 获得货单中详情
         */
        URL_RACK_UP_PRODUCT_LIST = BASE_URL + "tallying/list";

        /**
         * 上架 提交
         */
        URL_RACK_UP_SUBMIT = BASE_URL + "tallying/directActPis";

        /**
         * 装箱 通过 manifest code 所要装箱的内容
         */
        URL_TAKE_BOX_QUERY_PRODUCT_LIST = BASE_URL + "rk/packing/get/";

        /**
         * 装箱装箱 检验
         */
        URL_TAKE_BOX_CHECK_LPN_NO = BASE_URL + "rk/packing/checkLpnNo";
        /**
         * 获得当前货品的打包形式
         */
        URL_TAKE_BOX_QUERY_PACK_LIST = BASE_URL + "rk/packing/packList";

        /**
         * 装箱完成后，提交保存
         */
        URL_TAKE_BOX_SUBMIT = BASE_URL + "rk/packing/savePack";


        /**
         * 下架操作的基准
         */
        URL_RACK_DOWN_QUERY_DROP_BASE = BASE_URL + "os/offShelf/";

        /**
         * 下架操作
         * 获取 下架中 下架任务单中的列表
         */
        URL_RACK_DOWN_QUERY_DOCNO_LIST = URL_RACK_DOWN_QUERY_DROP_BASE + "pageDocNo";

        /**
         * 下架操作
         * 根据单号获取货架信息
         * 下架任务直接 拼接到后面
         */
        URL_RACK_DOWN_QUERY_LOCTION_LIST = URL_RACK_DOWN_QUERY_DROP_BASE + "getLoction/";

        /**
         * 下架操作
         * 查询货架上的货品集合
         */
        URL_RACK_DOWN_QUERY_RACK_PRODUCT_LIST = URL_RACK_DOWN_QUERY_DROP_BASE + "getAllSkuInfo";


        /**
         * 下架操作后的提交
         */
        URL_RACK_DOWN_QUERY_RACK_PRODUCT_SUBMIT = URL_RACK_DOWN_QUERY_DROP_BASE + "saveInfo";
        /**
         *  下架操作 提交 lpn
         */
        URL_RACK_DOWN_LPN_SUBMIT = URL_RACK_DOWN_QUERY_DROP_BASE + "offShelfLpn";


        /**
         * 出库获得 区域的列表
         */
        URL_DEPOT_OUT_AREA_LIST = BASE_URL + "base/area/pageAreaCodeList";

        /**
         * 出库获得当前 获得当前区域内的货单列表
         */
        URL_DEPOT_OUT_MANIFEST_LIST = BASE_URL + "os/deliver/pagePickingNoList";

        URL_DEPOT_OUT_LPN_SUBMIT = BASE_URL + "os/deliver/assignOsByLpn";

        /**
         * 出货获得当前仓库中区域中的货单中的货品列表
         */
        URL_DEPOT_OUT_GOODS_LIST = BASE_URL + "os/deliver/getPickingList";

        /**
         * 根据货品编码和批次号获取下架清单Ikey和序列号
         */
        URL_DEPOT_OUT_IKEY_LIST = BASE_URL + "os/deliver/getPickingIkeyList";

        /**
         * 出库货品提交
         */
        URL_DEPOT_OUT_SUBMIT = BASE_URL + "os/deliver/assignOutStock";

        /**
         * 货位调整:根据货位编码获取货位库存量(带分页的)
         */
        URL_GOODS_ADJUST_PALLET_LIST = BASE_URL + "lm/locAdjust/pageStockPosSkuList";

        /**
         * 货位调整：用于查询货品是否在当前货位上。
         */
        URL_GOODS_ADJUST_QUERY_PRO = BASE_URL + "lm/locAdjust/listBySkuCode";
        /**
         * 货位调整: 用于提交
         */
        URL_GOODS_ADJUST_GOODS_SUBMIT = BASE_URL + "lm/locAdjust/locAdjustSubmit";

        URL_GOODS_ADJUST_LOCK_RACK = BASE_URL + "lm/locAdjust/lockBySkuCode";

        /**
         * 盘点 盘点的理货单
         */
        check_query_manifest = BASE_URL + "lm/checking/getCheckingNoList";


        /**
         * 盘点 根据盘点单号获取货品明细列表
         */
        check_query_goods_of_manifest = BASE_URL + "lm/checking/pageSkuListByDocNo";

        /**
         * 盘点 用于查询当前所要质检的货品详细信息
         */
        check_query_check_goods = BASE_URL + "lm/checking/getSkuListByDocNo";

        /**
         * 盘点 提交当前盘点的实际数量
         */
        check_submit = BASE_URL + "lm/checking/checkingSubmit";

        /**
         * 分拣 获取任务单据列表
         */
        URL_SORTING_GET_MANIFEST_LIST = BASE_URL + "os/sorting/pageDeliveryNoList";

        /**
         * 分拣 获取任务单据列表
         */
        URL_SORTING_GET_MANIFEST_DETAILS = BASE_URL + "os/sorting/getDeliveryPordersList";
        /**
         * 分拣 获取任务单据列表
         */
        URL_SORTING_SUBMIT = BASE_URL + "os/sorting/toSaveSorting";
        URL_PACKING_MANIFEST_GET_DETAILS = BASE_URL + "os/packTask/getBoxNo";
        URL_PACKING_GET_SKU_BY_BOX_NO = BASE_URL + "os/packTask/getSkuByBoxNo";
        URL_PACKING_SUBMIT = BASE_URL + "os/packTask/boxsCommit";

        URL_GOODS_ADJUST_LPN_SUBMIT = BASE_URL + "lm/locAdjust/locAdjustLpn";

        check_lpn_submit = BASE_URL + "lm/checking/checkingLpn";
        /**
         * 包装出库 通过任务单 获取Lpn列表
         */
        URL_DOB_GET_LPN_LIST = BASE_URL + "os/deliverByBox/getBoxByPackageBox";
        /**
         * 包装出库 批量提交
         */
        URL_DOB_SUBMIT = BASE_URL + "os/deliverByBox/assignOutStockByPackageTask";

        URL_PACKING_DEPOT_OF_MANIFEST = BASE_URL + "os/deliverByBox/getPackageBox";


    }


    /**
     * 包装出库 通过任务单 获取Lpn列表
     */
    public static String URL_DOB_GET_LPN_LIST = BASE_URL + "os/deliverByBox/getBoxByPackageBox";
    /**
     * 包装出库 批量提交
     */
    public static String URL_DOB_SUBMIT = BASE_URL + "os/deliverByBox/assignOutStockByPackageTask";
    /**
     * 包装 获得任务单中详细数据
     */
    public static String URL_PACKING_MANIFEST_GET_DETAILS = BASE_URL + "os/packTask/getBoxNo";
    /**
     * 包装 获得仓库中的任务单列表
     */
    public static String URL_PACKING_DEPOT_OF_MANIFEST = BASE_URL + "os/deliverByBox/getPackageBox";

    /**
     * 根据箱号获取箱子明细
     */
    public static String URL_PACKING_GET_SKU_BY_BOX_NO = BASE_URL + "os/packTask/getSkuByBoxNo";

    /**
     * 包装提交
     */
    public static String URL_PACKING_SUBMIT = BASE_URL + "os/packTask/boxsCommit";

    /**
     * 系统检测是否需要更新
     */
    public static String update_app = BASE_URL + "auth/verify/verifyVersion";

    /**
     * 注销
     */
    public static String URL_LOGOUT = BASE_URL + "sec/auth/out";

    /**
     * 获得二维码标示符
     */
    public static String URL_CODE_FLAG_LIST = BASE_URL + "base/fr/list";
    /**
     * 仓库查询
     */
    public static String URL_DEPOT = BASE_URL + "base/wh/list";

    /**
     * 托盘管理中的 扫描完货品需要提交  组托提交的网址；
     */
    public static String URL_PM_PALLET_IN_HAVE_CODE_SUBMIT = BASE_URL + "rk/pallet/generatePallet";

    /**
     * 托盘管理中 检测LpnNo 托盘号是否已经装托，已经装箱，已经被装箱，是否存在等;
     */
    public static String URL_PM_PALLET_NO_CHECK = BASE_URL + "rk/pallet/checkLpnNo";
    /**
     * 根据托盘号查询托盘内内容：
     */
    public static String URL_PALLET_QUERY_CODE = BASE_URL + "rk/pallet/list";

    /**
     * 托盘管理取消入托
     */
    public static String URL_PM_PALLET_QUERY_CANCEL = BASE_URL + "rk/pallet/cancelPallet";
    /**
     * 托盘管理，没有托盘号 进行入托；
     */
    public static String URL_PM_PALLET_IN_OF_NOT_CODE_SUBMIT = BASE_URL + "rk/pallet/generateNoPallet";


    /**
     * 托盘管理，拆托
     */
    public static String URL_PM_PALLET_OUT = BASE_URL + "rk/pallet/ungeneratePallet";

    /**
     * 托盘管理中 查询商品详情
     */
    public static String URL_PM_PALLET_QUERY_PRODUCT_DETAILS = BASE_URL + "rk/pallet/getSku";

    /**
     * 分拣 获取任务单据列表
     */
    public static String URL_SORTING_GET_MANIFEST_LIST = BASE_URL + "os/sorting/pageDeliveryNoList";

    /**
     * 分拣 获取任务单据列表
     */
    public static String URL_SORTING_GET_MANIFEST_DETAILS = BASE_URL + "os/sorting/getDeliveryPordersList";
    /**
     * 分拣 获取任务单据列表
     */
    public static String URL_SORTING_SUBMIT = BASE_URL + "os/sorting/toSaveSorting";


    /**
     * 用于质检查询用
     */
    public static String URL_QUALITY_CHECKING_QUERY_DETAILS = BASE_URL + "rk/iqcAct/list";

    /**
     * 用于质检提交
     */
    public static String URL_QUALITY_CHECKING_SUBMIT = BASE_URL + "rk/iqcAct/drirectAct";

    /**
     * 查询不良品规则列表
     * 在质检操作中，包含不良品操作规则
     */
    public static String URL_QUALITY_CHECKING_QCREASON_LIST = BASE_URL + "base/qcreason/list";


    /**
     * 收货单扫描接口 查询 任务单中货品列表
     */
    public static String URL_TAKE_DELIVERY_GOODS_LIST = BASE_URL + "rk/receivingAct/list/";

    /**
     * 收货页面接口，用于查询当前仓库中的货品信息
     */
    public static String URL_TAKE_DELIVERY_DEPOT_MANIFEST_LIST = BASE_URL + "rk/receivingAct/pageList";
    /**
     * 收货 货品单品提交
     */
    public static String URL_TAKE_DELIVERY_GOODS_SUBMIT = BASE_URL + "rk/receivingAct/directAct";

    /**
     * 收货 LPN 提交的接口
     */
    public static String URL_TAKE_DELIVERY_LPN_SUBMIT = BASE_URL + "rk/receivingAct/directActLpn";

    /**
     * 上架  获得当前仓库的所有上架货品列表
     */
    public static String URL_RACK_UP_DEPOT_GOODS_LIST = BASE_URL + "tallying/pageList";
    /**
     * 上架 扫描货单时 获得货单中详情
     */
    public static String URL_RACK_UP_PRODUCT_LIST = BASE_URL + "tallying/list";

    /**
     * 上架 提交
     */
    public static String URL_RACK_UP_SUBMIT = BASE_URL + "tallying/directActPis";

    /**
     * 装箱 通过 manifest code 所要装箱的内容
     */
    public static String URL_TAKE_BOX_QUERY_PRODUCT_LIST = BASE_URL + "rk/packing/get/";


    /**
     * 获得当前货品的打包形式
     */
    public static String URL_TAKE_BOX_QUERY_PACK_LIST = BASE_URL + "rk/packing/packList";

    /**
     * 根发货单和货品编码去容积清单表中验证扫描的Lpn号码是否满足要求
     */
    public static String URL_TAKE_BOX_CHECK_LPN_NO = BASE_URL + "rk/packing/checkLpnNo";

    /**
     * 装箱完成后，提交保存
     */
    public static String URL_TAKE_BOX_SUBMIT = BASE_URL + "rk/packing/savePack";


    /**
     * 下架操作的基准
     */
    public static String URL_RACK_DOWN_QUERY_DROP_BASE = BASE_URL + "os/offShelf/";

    /**
     * 下架操作
     * 获取 下架中 下架任务单中的列表
     */
    public static String URL_RACK_DOWN_QUERY_DOCNO_LIST = URL_RACK_DOWN_QUERY_DROP_BASE + "pageDocNo";

    /**
     * 下架操作
     * 根据单号获取货架信息
     * 下架任务直接 拼接到后面
     */
    public static String URL_RACK_DOWN_QUERY_LOCTION_LIST = URL_RACK_DOWN_QUERY_DROP_BASE + "getLoction/";

    /**
     * 下架操作
     * 查询货架上的货品集合
     */
    public static String URL_RACK_DOWN_QUERY_RACK_PRODUCT_LIST = URL_RACK_DOWN_QUERY_DROP_BASE + "getAllSkuInfo";


    /**
     * 下架操作后的提交
     */
    public static String URL_RACK_DOWN_QUERY_RACK_PRODUCT_SUBMIT = URL_RACK_DOWN_QUERY_DROP_BASE + "saveInfo";

    /**
     * 下架操作 提交lpn
     */
    public static String URL_RACK_DOWN_LPN_SUBMIT = URL_RACK_DOWN_QUERY_DROP_BASE + "offShelfLpn";

    /**
     * 出库获得 区域的列表
     */
    public static String URL_DEPOT_OUT_AREA_LIST = BASE_URL + "base/area/pageAreaCodeList";

    /**
     * 出库获得当前 获得当前区域内的货单列表
     */
    public static String URL_DEPOT_OUT_MANIFEST_LIST = BASE_URL + "os/deliver/pagePickingNoList";


    public static String URL_DEPOT_OUT_LPN_SUBMIT = BASE_URL + "os/deliver/assignOsByLpn";


    /**
     * 出货获得当前仓库中区域中的货单中的货品列表
     */
    public static String URL_DEPOT_OUT_GOODS_LIST = BASE_URL + "os/deliver/getPickingList";

    /**
     * 根据货品编码和批次号获取下架清单Ikey和序列号
     */
    public static String URL_DEPOT_OUT_IKEY_LIST = BASE_URL + "os/deliver/getPickingIkeyList";

    /**
     * 出库货品提交
     */
    public static String URL_DEPOT_OUT_SUBMIT = BASE_URL + "os/deliver/assignOutStock";

    /**
     * 货位调整:根据货位编码获取货位库存量(带分页的)
     */
    public static String URL_GOODS_ADJUST_PALLET_LIST = BASE_URL + "lm/locAdjust/pageStockPosSkuList";
    /**
     * 货位调整：锁定当前货位上的货品
     */
    public static String URL_GOODS_ADJUST_LOCK_RACK = BASE_URL + "lm/locAdjust/lockBySkuCode";
    /**
     * 货位调整：用于查询货品是否在当前货位上。
     */
    public static String URL_GOODS_ADJUST_QUERY_PRO = BASE_URL + "lm/locAdjust/listBySkuCode";
    /**
     * 货位调整: 用于提交 货品
     */
    public static String URL_GOODS_ADJUST_GOODS_SUBMIT = BASE_URL + "lm/locAdjust/locAdjustSubmit";
    /**
     * 货位调整: 用于提交 lpn
     */
    public static String URL_GOODS_ADJUST_LPN_SUBMIT = BASE_URL + "lm/locAdjust/locAdjustLpn";

    /**
     * 盘点 盘点的理货单
     */
    public static String check_query_manifest = BASE_URL + "lm/checking/getCheckingNoList";

    /**
     * 盘点 根据盘点单号获取货品明细列表
     */
    public static String check_query_goods_of_manifest = BASE_URL + "lm/checking/pageSkuListByDocNo";

    /**
     * 盘点， 根据Lpn进行提交
     */
    public static String check_lpn_submit = BASE_URL + "lm/checking/checkingLpn";

    /**
     * 盘点 用于查询当前所要质检的货品详细信息
     */
    public static String check_query_check_goods = BASE_URL + "lm/checking/getSkuListByDocNo";

    /**
     * 盘点 提交当前盘点的实际数量
     */
    public static String check_submit = BASE_URL + "lm/checking/checkingSubmit";
}




