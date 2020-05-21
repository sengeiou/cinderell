package com.cinderellavip.ui;

import android.os.Bundle;

import com.cinderellavip.R;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.ycbjie.webviewlib.X5WebView;

public  abstract class BaseWebViewActivity extends BaseActivity {


    protected String url = "https://www.baidu.com";
    protected X5WebView web_view;

    protected void loadUrl(String url){
        web_view.loadUrl(url);
    }

    protected void loadData(String url){
        web_view.loadData(url,"text/html", "UTF-8");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        web_view = findViewById(R.id.veb_view);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (web_view != null) {
            web_view.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (web_view != null) {
            web_view.getSettings().setJavaScriptEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (web_view != null) {
                web_view.stopLoading();
                web_view.destroy();
                web_view = null;
            }
        } catch (Exception e) {
        }
        super.onDestroy();
    }

}
