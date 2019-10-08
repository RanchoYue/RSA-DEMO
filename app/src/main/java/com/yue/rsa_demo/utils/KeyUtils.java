package com.yue.rsa_demo.utils;

/**
 * @Describe:
 * @Author: Created by yue on 2019/10/08.
 */
public class KeyUtils {
    private static String privateKey;

    public static String generatePrivateKey16() {
        String pKey = System.currentTimeMillis() + "";
        privateKey = Md5Util.encrypt16(pKey);
        return privateKey;
    }

    public static String getPrivateKey16() {
        return privateKey;
    }
}
