package com.cinderellavip.util;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.tozzais.baselibrary.util.toast.ToastCommom;

public class ClipBoardUtil {
    public static void copy(Context context,String text){
        copy(context,text,"复制成功");

    }

    public static void copy(Context context,String text,String tip){
        ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", text);
        clipboard.setPrimaryClip(clip);
        ToastCommom.createToastConfig().ToastShow(context,tip);

    }



}
