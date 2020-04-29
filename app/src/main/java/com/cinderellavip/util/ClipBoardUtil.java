package com.cinderellavip.util;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.tozzais.baselibrary.util.toast.ToastCommom;

public class ClipBoardUtil {
    public static void copy(Context context,String text){
        ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", text);
        clipboard.setPrimaryClip(clip);
        ToastCommom.createToastConfig().ToastShow(context,"复制成功");

    }



}
