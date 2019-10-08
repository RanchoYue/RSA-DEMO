package com.yue.rsa_demo.utils;

import android.text.TextUtils;

import java.security.MessageDigest;

public class Md5Util {

    /****
     *
     * MD5加密
     *
     */
    public static String encrypt(String plainText) {
        try {

            String passwordString = plainText;

            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(passwordString.getBytes());
            byte b[] = md.digest();

            return bytesToHexString(b);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return null;
        }
    }

    /****
     *
     * MD5加密
     *
     */
    public static String encrypt16(String plainText) {
        try {

            String passwordString = plainText;

            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(passwordString.getBytes());
            byte b[] = md.digest();

            String key = bytesToHexString(b);

            if (!TextUtils.isEmpty(key) && key.length() > 24) {
                key = key.substring(8, 24);
            }

            return key;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return null;
        }
    }


    /****
     *
     * MD5加密
     *
     */
    public static String encryptTwice(String plainText, String key) {

        return Md5Util.encrypt(key + Md5Util.encrypt(plainText));

    }

    /**
     * bytes 转化为字符串
     **/
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }


}

