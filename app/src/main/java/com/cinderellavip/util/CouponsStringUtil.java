package com.cinderellavip.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.tozzais.baselibrary.util.log.LogUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 32672 on 2019/2/28.
 */

public class CouponsStringUtil {
    /**
     *
     * @param advanceString 原来字符串
     * @param id 添加或删除的字符串
     * @param isAdd 是否增加
     * @return
     */
    public static String getString(String advanceString,String id,boolean isAdd) {
        if ("0".equals(advanceString)){
            if (isAdd){
                advanceString = id;
            }
            return advanceString;
        }else {
            String[] split = advanceString.split(",");
            List<String> list = new ArrayList<>();
            boolean isHava = false;
            for (String s:split){
                if (s.equals(id)){
                    isHava = true;
                }
                list.add(s);
            }
            if (isAdd && isHava){
                //增加 并且原来就有
                return advanceString;
            }else if (isAdd){
                //增加 并且原来没有
                return advanceString+","+id;
            }else if (isHava){
                //减少并且有
                List<String> list1 = new ArrayList<>();
                for (String s:split){
                    if (!s.equals(id)){
                        list1.add(s);
                    }
                }
                StringBuffer stringBuffer = new StringBuffer();
                for (int i=0;i<list1.size();i++){
                    String item = list1.get(i);
                    if (i==list1.size()-1){
                        stringBuffer.append(item);
                    }else {
                        stringBuffer.append(item+",");
                    }
                }
                if ("".equals(stringBuffer.toString())){
                    return "0";
                }
                return stringBuffer.toString();
            }else {
                //减少并且没有
                return advanceString;
            }
        }


    }


}
