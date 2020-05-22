package com.cinderellavip.http;


import com.litesuits.common.utils.MD5Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class SignUtil {


    //静态方法，便于作为工具类
    public static String getMd5(TreeMap<String, String> keys,String time) {
       StringBuffer sign = new StringBuffer();
        for (Map.Entry<String, String> entry : keys.entrySet()) {
            sign.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        sign.append("secret=241cd2aa2aae01cd2&");
        sign.append("timestamp="+time);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((sign.toString()).getBytes("UTF-8"));
            byte b[] = md.digest();

            int i;
            StringBuffer buf = new StringBuffer();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }



    //静态方法，便于作为工具类
    public static String getMd51(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update((s).getBytes("utf-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }



    //静态方法，便于作为工具类
    public static String getMd51(TreeMap<String, String> keys,String time) {
        StringBuffer sign = new StringBuffer();
        for (Map.Entry<String, String> entry : keys.entrySet()) {
            sign.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        sign.append("secret=241cd2aa2aae01cd2&");
        sign.append("timestamp="+time);
//        LogUtil.e(sign.toString());
        System.out.println(sign.toString());
        byte[] bytes = MD5Util.md5(sign.toString());
        return new String(bytes);


    }


    //静态方法，便于作为工具类
    public static String getMd5(String time) {
//        LogUtil.e(time);

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((time).getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }


}
