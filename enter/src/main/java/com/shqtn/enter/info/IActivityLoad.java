package com.shqtn.enter.info;

import android.content.Intent;
import android.os.Bundle;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.bean.enter.TakeBoxGoods;
import com.shqtn.base.bean.enter.TakeBoxPlan;
import com.shqtn.base.bean.exit.DepotOutManifest;
import com.shqtn.base.bean.exit.RackDownGoods;
import com.shqtn.base.bean.exit.SortingGoods;

/**
 * Created by android on 2017/9/22.
 */

public interface IActivityLoad {
    Class getLoginActivity();

    Class getHomeActivity();

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

        /**
         * 出库的包装
         *
         * @param bundle
         * @return
         */
        Class getPackingMain(Bundle bundle);

        /**
         * 包装出库
         *
         * @param bundle
         * @return
         */
        Class getPackingOutMain(Bundle bundle);

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

        /**
         * bundle.putString(C.MANIFEST_STR,mManifest);
         * bundle.putParcelable(C.OPERATE_GOODS,takeBoxGoods);
         *
         * @param bundle
         * @return
         * @see TakeBoxGoods
         */
        Class getTakeBoxGoodsTakeSelectActivity(Bundle bundle);

        /**
         * bundle.putString(C.MANIFEST_STR,mManifest);
         * bundle.putParcelable(C.OPERATE_GOODS,takeBoxGoods);
         * bundle.putParcelable(C.TAKE_BOX_PLAN,takeBoxPlan);
         *
         * @param bundle
         * @return
         * @see TakeBoxGoods
         * @see TakeBoxPlan
         */
        Class getTakeBoxTakeOperateActivity(Bundle bundle);
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

        /**
         * bundle.putParcelable(C.MANIFEST_BEAN,checkQuantityManifest);
         * CheckQuantityManifest
         *
         * @param bundle
         * @return
         * @see com.shqtn.base.bean.in.CheckQuantityManifest
         */
        Class getCheckQuantityManifestOperateActivity(Bundle bundle);

        /**
         * 货位调整添加货品移动
         * bundle.putString(C.RACK_NO,mRackDetailsParams.getLocCode());
         * 要操作的货位 编码
         *
         * @param bundle
         * @return
         */
        Class getGoodsAdjustAddMoveGoodsActivity(Bundle bundle);
    }


    public interface ExitActivityLoad {
        /**
         * 下架中，跳转到货位列表
         * C.MANIFEST_STR  String 任务单据号
         *
         * @param bundle
         * @return
         */
        Class getRackDownRackListActivity(Bundle bundle);

        /**
         * C.MANIFEST_STR string 任务单据号
         * C.RACK_NO string 货位编码;
         *
         * @param bundle
         * @return
         */
        Class getRackDownGoodsListActivity(Bundle bundle);

        /**
         * C.MANIFEST_STR string 任务单据号
         * C.RACK_NO string 货位编码
         * C.OPERATE_GOODS RackDownGoods 操作货品详情
         * C.SCANNING_QTY double qty 扫描数量
         *
         * @param bundle
         * @return
         * @see RackDownGoods
         */
        Class getRackDownGoodsOperateActivity(Bundle bundle);

        /**
         * bundle.putParcelableArrayList(C.GOODS_LIST, mGoodsList);
         * bundle.putString(C.OPERATE_RACK_NO, mOperateRack);
         * bundle.putString(C.MANIFEST_STR, mOperateManifest);
         * bundle.putParcelable(C.OPERATE_LPN,lpn);
         * CodeLpn
         *
         * @param bundle
         * @return
         * @see RackDownGoods
         */
        Class getRackDownLpnSubmitActivity(Bundle bundle);

        /**
         * C.AREA_CODE ,string 区域编码
         *
         * @param bundle
         * @return
         */
        Class getDepotOutAreaListActivity(Bundle bundle);

        /**
         * C.AREA_CODE .string 区域编码
         * C.MANIFEST_BEAN DepotOutManifest 出库任务单数据
         *
         * @param bundle
         * @return
         * @see DepotOutManifest
         */
        Class getDepotOutManifestListActivity(Bundle bundle);

        Class getDepotOutGoodsOperateActivity(Bundle bundle);

        Class getDepotOutGoodsListActivity(Bundle bundle);

        /**
         * C.MANIFEST_STR string  任务单据号
         *
         * @param bundle
         * @return
         */
        Class getSortingGoodsListActivity(Bundle bundle);

        /**
         * bundle.putParcelable(C.OPERATE_GOODS,sortingGoods);//SortingGoods
         * bundle.putDouble(C.SCANNING_GOODS_QTY,goodsQty);
         *
         * @param bundle
         * @return
         * @see SortingGoods
         */
        Class getSortingGoodsOperateActivity(Bundle bundle);


        /**
         * bundle.putString(C.MANIFEST_STR,packageNo);
         *
         * @param bundle
         * @return
         */
        Class getPackingLpnListActivity(Bundle bundle);

        /**
         * bundle.putString(C.MANIFEST_STR,packageNo);
         *
         * @param bundle
         * @return
         */
        Class getPackingAddLpnOrGoodsOperateActivity(Bundle bundle);

    }
}
