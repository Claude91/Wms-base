package com.shqtn.base.model;

import com.shqtn.base.bean.enter.TakeDeliveryGoods;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by android on 2017/7/18.
 */

public class TakeDeliveryModel {
    private final static String SERIAL_FLAG = "Y";
    /**
     * 用于判断当前货品是否要添加 序列号 不等于0 即需要添加
     */
    private final static String SERIAL_NO_FLAG = "0";

    /**
     * 当前显示对象 batchNoFlag =2 需要進行編輯
     */
    private final static String BATCH_NO_FLAG = "2";

    /**
     *
     */
    private final static String BATCH_FLAG = "Y";

    /**
     * 是否添加批次号
     *
     * @return
     */
    public boolean isAddBatchNo(String batchFlag, String batchNoFlag) {
          /*
        * 判断当前货品是否需要编写批次属性
         */
        if (BATCH_FLAG.equals(batchFlag)) {
            if (BATCH_NO_FLAG.equals(batchNoFlag)) {
                return true;
            }
        }

        return false;
    }


    /**
     * 是否需要添加序列号
     *
     * @param serialFlag
     * @return
     */
    public boolean isAddSerial(String serialFlag) {
          /*
        *   判断当前是否需要添加 序列属性
         */
        if (SERIAL_FLAG.equals(serialFlag))
            if (!SERIAL_NO_FLAG.equals(serialFlag)) {
                return true;
            }

        return false;
    }


    /**
     * 提交Lpn时，需要获得所要提交的货品列表集合
     *
     * @param lpn       当前要提交的货品
     * @param goodsList 任务单据中的货品列表
     * @return 提交需要的参数；
     */
    public ArrayList<GoodsOfLpn> getLpnOfGoodsListParams(CodeLpn lpn, List<TakeDeliveryGoods> goodsList) {
        ArrayList<GoodsOfLpn> paramsList = new ArrayList<>();

        ArrayList<CodeGoods> repeatGoodsList = GoodsUtils.repeatGoodsList(lpn.getGoodsList());
        next:
        for (CodeGoods codeGoods : repeatGoodsList) {

            GoodsOfLpn goods = new GoodsOfLpn();
            ArrayList<TakeDeliveryGoods> hasGoodsList = GoodsUtils.getManifestOfGoodsSame(goodsList, codeGoods);

            double surplusQty = codeGoods.getGoodsQty();
            for (TakeDeliveryGoods takeDeliveryGoods : hasGoodsList) {
                double qty = 0;
                if (takeDeliveryGoods.getRquantity() > surplusQty) {

                    qty = surplusQty;
                    surplusQty = surplusQty - qty;
                } else {
                    qty = takeDeliveryGoods.getRquantity();
                    surplusQty = NumberUtils.getDouble(surplusQty - qty);
                }
                goods.setBatchNo(codeGoods.getBatchNo());
                goods.setSkuCode(codeGoods.getSkuCode());
                goods.setSkuQty(qty);
                goods.setTikey(takeDeliveryGoods.getIkey());
                paramsList.add(goods);
                if (surplusQty == 0) {
                    continue next;//当前货品操作完毕，就可以添加下一个货品
                }
            }

        }
        return paramsList;
    }

    public static class GoodsOfLpn {
        private Long tikey;
        private String skuCode;
        private String batchNo;
        private Double skuQty;

        public Long getTikey() {
            return tikey;
        }

        public void setTikey(Long tikey) {
            this.tikey = tikey;
        }

        public String getSkuCode() {
            return skuCode;
        }

        public void setSkuCode(String skuCode) {
            this.skuCode = skuCode;
        }

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public Double getSkuQty() {
            return skuQty;
        }

        public void setSkuQty(Double skuQty) {
            this.skuQty = skuQty;
        }
    }
}
