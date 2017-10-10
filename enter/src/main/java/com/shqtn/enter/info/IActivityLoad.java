package com.shqtn.enter.info;

import android.content.Intent;
import android.os.Bundle;

import com.shqtn.base.BaseActivity;

/**
 * Created by android on 2017/9/22.
 */

public interface IActivityLoad {


    public interface FunctionMainActivityLoad {

        /**
         * 收货任务单据号列表
         *
         * @return
         */
        Class getTakeDelMainActivity(Bundle bundle);

        Class getRackUpMainActivity(Bundle bundle);

        Class getGoodsAdjustMainActivity(Bundle bundle);

        Class getDepotSelectActivity(Bundle bundle);

        /**
         * 盘点
         *
         * @param bundle
         * @return
         */
        Class getCheckQuantityMain(Bundle bundle);

        /**
         * 托盘管理
         *
         * @param bundle
         * @return
         */
        Class getEnterPalletMain(Bundle bundle);

        /**
         * 装箱
         *
         * @param bundle
         * @return
         */
        Class getTakeBoxMain(Bundle bundle);

        Class getRackDownMain(Bundle bundle);

        Class getSortingMain(Bundle bundle);

        Class getDepotOutMain(Bundle bundle);

        Class getQualityInspectionMain(Bundle bundle);
    }

    public interface EnterActivityLoad {
        /**
         * 用于收货货品列表显示的Activity
         * <t>只会参入当前任务单据号 存入bundle里面 C.MANIFEST_STR 中获取</t>
         *
         * @return
         */
        Class getTakeDelGoodsListActivity(Bundle bundle);


        /**
         * 获得货品操作的Activity();
         *
         * @return
         */
        Class getTakeDelGoodsOperateActivity(Bundle bundle);

        /**
         * RackUpGoods   C.OPERATE_GOODS
         *
         * @return
         */
        Class getRackUpLpnOperateActivity(Bundle bundle);

        /**
         * RackUpGoods   C.OPERATE_GOODS
         *
         * @return
         */
        Class getRackUpGoodsOperateActivity(Bundle bundle);

        /**
         * C.OPERATE_GOODS  保存着当前要操作的货品
         * QualityInspectionGoods
         *
         * @param bundle
         * @return
         * @see com.shqtn.base.bean.enter.QualityInspectionGoods
         */
        Class getQualityInspectionGoodsOperateActivity(Bundle bundle);
    }


    public interface InActivityLoad {
        /**
         * C.GOODS_LIST  已经添加的移动货品
         * C.RACK_UP  操作的货位
         *
         * @param bundle
         * @return
         */
        Class getGoodsAdjustTargetRackActivity(Bundle bundle);

        /**
         * C.RACK_NO  操作的货位号码
         * C.LPN  操作的托盘 CodeLpn
         *
         * @param bundle
         * @return
         */
        Class getGoodsAdjustLpnSubmitActivity(Bundle bundle);

        Class getRackUpLpnOperateActivity(Bundle bundle);

        Class getRackUpGoodsOperateActivity(Bundle bundle);
    }


    public interface ExitActivityLoad {
    }
}
