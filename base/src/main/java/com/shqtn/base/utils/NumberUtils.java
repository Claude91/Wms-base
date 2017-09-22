package com.shqtn.base.utils;

import java.math.BigDecimal;

/**
 * Created by android on 2017/7/13.
 */

public class NumberUtils {

    public static int NORMAL_LENGTH = 4;


    public static double getDouble(String s) {
        double b = 0;
        try {
            b = Double.parseDouble(s);
        } catch (Exception e) {
            b = 0;
        }
        return b;
    }

    public static double getDouble(double d, int number) {
        BigDecimal bg = new BigDecimal(d);
        return bg.setScale(number, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double getDouble(double d) {
        BigDecimal bg = new BigDecimal(d);
        return bg.setScale(NORMAL_LENGTH, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static int getInt(String s) {
        int b = 0;
        try {
            b = (int) Double.parseDouble(s);
        } catch (Exception e) {
            b = 0;
        }
        return b;
    }

    public static long getLong(String l) {
        try {
            return Long.parseLong(l);
        } catch (Exception e) {
            return 0;
        }
    }
}
