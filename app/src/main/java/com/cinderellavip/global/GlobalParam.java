package com.cinderellavip.global;


import com.tozzais.baselibrary.util.SharedPreferencesUtil;


/**
 * Created by jumpbox on 16/4/19.
 */
public class GlobalParam {





    //是否使用
    public static void setFirstUse(boolean firstUse) {
        SharedPreferencesUtil.saveBooleanData(CinderellApplication.mContext, Constant.user_first_use, firstUse);
    }
    public static boolean getFirstUse() {
        return SharedPreferencesUtil.getBooleanData(CinderellApplication.mContext, Constant.user_first_use,false);
    }



}
