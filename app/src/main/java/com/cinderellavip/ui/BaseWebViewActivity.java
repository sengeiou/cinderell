package com.cinderellavip.ui;

import android.os.Bundle;

import com.cinderellavip.R;
import com.tencent.smtt.sdk.WebSettings;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.ycbjie.webviewlib.X5WebView;

public  abstract class BaseWebViewActivity extends BaseActivity {


    protected String url = "https://www.baidu.com";
    protected X5WebView web_view;

    protected void loadUrl(String url){
        web_view.loadUrl(url);
    }

//    protected void loadData(String url){
//        web_view.loadData(url,"text/html", "UTF-8");
//    }
    public void loadData(String content){
        String varjs = "<style>p{margin:0;}</style> <script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
        web_view.loadDataWithBaseURL("", varjs + content, "text/html", "UTF-8", null);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        web_view = findViewById(R.id.veb_view);
        WebSettings settings = web_view.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);

        settings.setLoadWithOverviewMode(true);


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
