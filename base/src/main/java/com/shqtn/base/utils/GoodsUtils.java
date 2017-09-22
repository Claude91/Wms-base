package com.shqtn.base.utils;

import com.shqtn.base.bean.base.IGoods;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by android on 2017/7/18.
 */

public class GoodsUtils {

    /**
     * 用于判断是否是相同的 货品
     *
     * @param srcGoods
     * @param compareGoods
     * @return
     */
    public static <T extends IGoods, E extends IGoods> boolean isSame(IGoods srcGoods, IGoods compareGoods) {
        if (srcGoods == null || StringUtils.isEmpty(srcGoods.getGoodsSku()) || compareGoods == null)
            return false;

        if (!srcGoods.getGoodsSku().equals(compareGoods.getGoodsSku())) {
            return false;
        }

        if (StringUtils.isEmpty(srcGoods.getGoodsBatchNo())) {
            if (!StringUtils.isEmpty(compareGoods.getGoodsBatchNo())) {
                return false;
            }

        } else {
            if (!srcGoods.getGoodsBatchNo().equals(compareGoods.getGoodsBatchNo())) {
                return false;
            }
        }

        return true;
    }

    public static <T extends IGoods, E extends IGoods> ArrayList<T> getManifestOfGoodsSame(List<T> goodsList, E goods) {
        if (goodsList == null || goods == null || goods.getGoodsSku() == null) {
            return null;
        }
        ArrayList<T> arrayList = new ArrayList<>();
        for (T iGoods : goodsList) {
            if (isSame(iGoods, goods)) {
                arrayList.add(iGoods);
            }
        }

        if (arrayList.size() == 0) {
            return null;
        }

        return arrayList;
    }

    /**
     * 合拼相同的货品
     *
     * @param goodsList
     * @param <T>
     * @return
     */
    public static <T extends IGoods> ArrayList<GoodsSame> filterSame(List<T> goodsList) {
        ArrayList<GoodsSame> arrayList = new ArrayList<>();

        for (T lpnOfgoodsList : goodsList) {
            for (GoodsSame t : arrayList) {
                if (isSame(t, lpnOfgoodsList)) {
                    double qty = t.getGoodsQty();
                    qty = NumberUtils.getDouble(qty + lpnOfgoodsList.getGoodsQty());
                    t.setGoodsQty(qty);
                    continue;
                }
            }
            GoodsSame gs = new GoodsSame();
            gs.setGoodsQty(lpnOfgoodsList.getGoodsQty());
            gs.setGoodsBatchNo(lpnOfgoodsList.getGoodsBatchNo());
            gs.setGoodsSku(lpnOfgoodsList.getGoodsSku());
            arrayList.add(gs);
        }
        return arrayList;
    }

    /**
     * 用于合并相同货品，数量累加
     *
     * @param goodsList
     * @param <T>
     * @return
     */
    public static <T extends IGoods> ArrayList<T> repeatGoodsList(ArrayList<T> goodsList) {
        if (goodsList == null || goodsList.size() == 0) {
            return goodsList;
        }
        ArrayList<T> arrayList = new ArrayList<>();
        for (T t : goodsList) {
            boolean isHas = false;
            for (T t1 : arrayList) {
                if (isSame(t, t1)) {
                    double qty = t1.getGoodsQty();
                    qty = NumberUtils.getDouble(qty + t.getGoodsQty());
                    t1.setGoodsQty(qty);
                    isHas = true;
                }
            }
            if (!isHas)
                arrayList.add(t);
        }
        return arrayList;
    }


    public static class GoodsSame extends IGoods {

    }

    /**
     * 用于判断数量是否满足
     *
     * @param lpnGoodsList
     * @param goodsOfManifest
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T extends IGoods, E extends IGoods> C<T, E> isQuantitySatisfactory(ArrayList<E> lpnGoodsList, List<T> goodsOfManifest) {
        C<T, E> c = new C<>();
        if (lpnGoodsList == null || goodsOfManifest == null) {
            return c;
        }
        //获取LPN中货品相同的
        ArrayList<D<E>> lpnOfGoodsList = new ArrayList<>();
        a:
        for (E e : lpnGoodsList) {
            for (D<E> ed : lpnOfGoodsList) {
                if (isSame(ed, e)) {
                    double qty = ed.getGoodsQty();
                    qty += e.getGoodsQty();
                    ed.setGoodsQty(qty);
                    ed.sameGoodsList.add(e);
                    continue a;
                }
            }
            D<E> d = new D<>();
            d.setGoodsQty(e.getGoodsQty());
            ;
            d.sameGoodsList.add(e);
            d.setGoodsSku(e.getGoodsSku());
            d.setGoodsBatchNo(e.getGoodsBatchNo());
            lpnOfGoodsList.add(d);
        }
        //过滤相同的货品后 在进行比较
        ArrayList<T> nowGoodsList = new ArrayList<>();
        for (D<E> ed : lpnOfGoodsList) {

            for (T t : goodsOfManifest) {
                if (isSame(t, ed)) {
                    nowGoodsList.add(t);
                    double qty = NumberUtils.getDouble(ed.getGoodsQty() - t.getGoodsQty());
                    ed.setGoodsQty(qty);
                }
            }

            if (ed.getGoodsQty() > 0) {
                //当前不符合条件 该商品的数量不足
                c.isSatisfactory = false;
                c.planGoods = ed.sameGoodsList;
                c.nowGoods = nowGoodsList;

                return c;
            }
            nowGoodsList.removeAll(null);
        }
        //符合要求
        c.isSatisfactory = true;
        return c;
    }

    public static class D<T> extends IGoods {
        ArrayList<T> sameGoodsList;
    }

    public static class C<T, E> {
        public boolean isSatisfactory;
        public ArrayList<E> planGoods;
        public ArrayList<T> nowGoods;
    }


    /**
     * 用于获取 lpn中的货品 在任务单据中的货品集合;
     *
     * @param lpnGoodsList
     * @param goodsOfManifest
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T extends IGoods, E extends IGoods> B<E> isLpnAtManifsetOfGoodsList(ArrayList<E> lpnGoodsList, List<T> goodsOfManifest) {
        B<E> b = new B<>();

        if (lpnGoodsList == null || goodsOfManifest == null) {
            return b;
        }
        for (E e : lpnGoodsList) {
            boolean isSame = false;
            for (T t : goodsOfManifest) {
                if (isSame(e, t)) {
                    isSame = true;
                }
            }
            if (!isSame) {
                b.notAtGoods = e;
                return b;
            }
        }
        b.isAt = true;

        return b;
    }


    public static class B<T> {
        public boolean isAt;//是否在任务单据中
        public T notAtGoods;//该货品不在任务单中
    }


}
