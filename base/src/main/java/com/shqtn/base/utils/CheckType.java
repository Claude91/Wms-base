package com.shqtn.base.utils;

/**
 * Created by android on 2017/8/3.
 */

public class CheckType {
    /**
     * 3.period;--盘点阶段(0:初盘;1:复盘;)
     * 4.checkType;--盘点类型(0:按仓库;1:按货位;2:按批次;3:按存货大类;4:按存货SKU;5:按货主)
     *
     * @param type
     * @return
     */
    public static String getTypeName(String type) {
        switch (type) {
            case "0":
                return "仓库";
            case "1":
                return "货位";
            case "2":
                return "批次";
            case "3":
                return "存货大类";
            case "4":
                return "存货SKU";
            case "5":
                return "货主";
        }
        return null;
    }

    public static String getPeriodName(String period) {
        switch (period) {
            case "0":
                return "初盘";
            case "1":
                return "复盘";
        }
        return null;
    }
}
