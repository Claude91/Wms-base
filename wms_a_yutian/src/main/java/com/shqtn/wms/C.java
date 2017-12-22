package com.shqtn.wms;

/**
 * 常量池
 */
public class C {


    /**
     * 初始化默认token=86e2c1a0-aba5-11e6-8ec2-162d27fbcfcd
     * 初始化手持key=b5417be0-aba5-11e6-b9e6-162d27fbcfcd
     */
    public static final String APP_NORMAL_TOKEN = "86e2c1a0-aba5-11e6-8ec2-162d27fbcfcd";
    public static final String APP_NORMAL_KEY = "b5417be0-aba5-11e6-b9e6-162d27fbcfcd";
    public static final int INIT_PAGE = 1;
    public static final int INIT_PAGE_SIZE = 20;
    /**
     * 用于判断装时，包装等级分界线，如果操作的包装等级为 "2" 时，则包装的是最小单位的货品。
     * 其他状态都是包装的LPN 箱子
     */
    public static final String INIT_MIN_PACK_LEVEL = "2";
    public static final String LAST_ACCOUNT = "lastAccount";//上次登录账号
    public static final String LAST_PASSWORD = "lastPassword";//上次登录的密码
    public static int ChangeQty_Del = -1;//用于判断是否是需要删除项

    /**
     * 用于判别是否需要APP 更新的错误编码
     */
    public static final String ERROR_APP_UPDATE_CODE = "ES0002";


    /**
     * 装箱中，
     * 包装时进行判断当前 lpnNo是 最外层包装还是其他
     */
    public static final class LpnFlag {
        //最外层包装， 也就是大的包装
        public static final String BIG_FLAG = "0";
        public static final String MIN_FLAG = "1";
        public static final String STATUS_NORMAL = "0";
        public static final String STATUS_LOAD = "1";
        public static final String STATUS_OVER = "2";
    }

    public static final class CheckType {
        //0:按仓库;1:按货位;2:按批次;3:按存货大类;4:按存货SKU;5:按货主
        public static final String TYPE_DEPOT = "0";
        public static final String TYPE_RACK = "1";
        public static final String TYPE_BATCHNO = "2";
        public static final String TYPE_CLASS_CODE = "3";
        public static final String TYPE_SKU = "4";
        public static final String TYPE_OWN = "5";
    }

    public static final class Intent {
        public static final String P = "data";
        public static final String PalletType = "palletType";
    }

    public static final class Aty {

        public static final String PRODUCT_AMOUNT = "productAmount";
        public static final String PALLET_HAVE_CODE_IN = "haveCodeIn";
        public static final String PALLET_NO_CODE_IN = "noCodeIn";
        public static final String PALLET_OUT = "palletOut";
        public static final String INTENT_SELECT_FUNCTION_ID = "selectFunctionId";
        public static final String INTENT_PALLET_DEATILE_TICKET_CODE = "palletTicketCode";

        public static final String INTENT_BEAN = "takeDeliveryData";
        public static final String INTENT_ARRAY_LIST = "arrayList";
        public static final String INTENT_P = "intentP";
        public static final String INTENT_P_RACK = "intentPRack";
        public static final String INTENT_P_QTY = "intentPQty";
        public static final String INTENT_P_LIST = "intentPList";
        public static final String INTENT_STR = "intentStr";
        public static final String INTENT_P_LIST_2 = "intentPList2";

        public static final String INTENT_WHCODE = "whcode";
        public static final String INTENT_MANIFEST = "manifest";
        public static final String INTENT_MANIFEST_P = "manifestP";
        public static final String INTENT_P_PRO = "product";
        public static final String INTENT_DEPOT = "depot";
        public static final String INTENT_RACK_CODE = "rackCode";
        public static final String INTENT_AREA = "AREA";
        public static final String INTENT_LOC_CODE = "locCode";
        public static final String INTENT_BUNDLE = "intentBundle";

        public static final String INTENT_PALLET_NO = "palletNo";
        public static final String INTENT_LPN = "intentLpn";
        public static final String INTENT_QTY = "intentQty";
        public static final String INTENT_LEVEL_RATE = "intentRate";
        public static final String INTENT_LPN_QTY = "intentMax";

        public static final String INTENT_BATCH_NO = "intentBatchNo";
    }

    public static final class SharedPreferencesName {
        /**
         * 仓库编码
         */
        public static final String Whcode = "whcode";
        public static final String CodeFlagName = "codeFlagName";
        public static final String CodeFlagKey = "codeFlag";
    }

    public static final class CodeType {
        //{"rs":true,"data":[
        // {"flagCode":"W01","flagDesc":"单据号","flagVale":"101"}
        // ,{"flagCode":"W02","flagDesc":"货位","flagVale":"201"}
        // ,{"flagCode":"W03","flagDesc":"LPN","flagVale":"301"}
        // ,{"flagCode":"W04","flagDesc":"SKU","flagVale":"999"}]}
        /**
         * 货位标识
         */
        public static final String FLAG_RACK = "W02";
        /**
         * 货品标识
         */
        public static final String FLAG_PRODUCT = "W04";
        /**
         * 托盘标识
         */
        public static final String FLAG_PALLET = "W03";
        /**
         * 单号标识
         */
        public static final String FLAG_MANIFEST = "W01";
    }

    public static class Key {
        public static final String ReadTime = "readTime";
    }
}
