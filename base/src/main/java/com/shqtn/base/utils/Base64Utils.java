package com.shqtn.base.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * B都得到ase64 加密
 * 订单Base64 (Base64(时间戳前13位)&Base64(密码))
 * Created by Administrator on 2016-11-16.
 */
public class Base64Utils {
    /**
     * 加密
     * @param val 明文
     * @return ciphertext 密文
     */
    public static String endBase64(String val, String time) {
        String a = singleBase64("&");
        String timeBase64 = singleBase64(time);
        String valBase64 = singleBase64(val);
        String base64Content = singleBase64(timeBase64 +a+valBase64);
        return base64Content;
    }
    // 加密
    public static String singleBase64(String str) {
        String result = null;
        try {
            result = new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    // 解密
    public static String getFromBase64(String str)  {
        String result = null;
        try {
            result = new String(Base64.decode(str, Base64.NO_WRAP), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
