package com.cinderellavip;

import android.webkit.JavascriptInterface;

import com.cinderellavip.ui.web.AgreementWebViewActivity;
import com.tozzais.baselibrary.util.log.LogUtil;

public class AppJs extends Object {
    private AgreementWebViewActivity h5Activity;
    public AppJs(AgreementWebViewActivity h5Activity) {
            this.h5Activity = h5Activity;
        }



    @JavascriptInterface
    public void toLogin(String data) {
        LogUtil.e(data);
        h5Activity.toLogin();

    }
    @JavascriptInterface
    public void toLogin() {
        LogUtil.e("changeSuccess");
        h5Activity.toLogin();

    }

    @JavascriptInterface
    public void toLogin(Boolean isSuccess) {
        LogUtil.e("changeSuccess"+isSuccess);
        h5Activity.toLogin();

    }


}
