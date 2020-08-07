package com.cinderellavip.ui.fragment.goods;

import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.cinderellavip.R;
import com.tozzais.baselibrary.ui.BaseFragment;

import butterknife.BindView;

public class GraphicFragment extends BaseFragment {

    private WebView web_view;


    @Override
    public int setLayout() {
        return R.layout.fragment_webview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        web_view = mRootView.findViewById(R.id.web_view);
    }

    @Override
    public void loadData() {
        //支持javascript
        web_view.getSettings().setJavaScriptEnabled(true);


        // 设置可以支持缩放
//        web_view.getSettings().setSupportZoom(false);
        // 设置出现缩放工具
//        web_view.getSettings().setBuiltInZoomControls(false);
        //扩大比例的缩放
//        web_view.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        web_view.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web_view.getSettings().setLoadWithOverviewMode(true);

        //不适用浏览器打开
        web_view.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


    }

    private String getHtmlData() {
        String head = "<p><img src=\"https://hongkong-shopping.cn/gp/profile/ueditor/20190925/2019/09/25/474107d36f5fb0008bf38ec7f4618a49.png\" title=\"2019/09/25/474107d36f5fb0008bf38ec7f4618a49.png\"/></p><p><img src=\"https://hongkong-shopping.cn/gp/profile/ueditor/20190925/2019/09/25/9fc43b65e8f67a775d3b4d6117bf8154.png\" title=\"2019/09/25/9fc43b65e8f67a775d3b4d6117bf8154.png\"/></p><p><br/></p>";
        return head;
    }

    public void setData(String content){
        if (!isAdded()){
            new Handler().postDelayed(()->{setData(content);},500);
            return;
        }
        String varjs = "<style>p{margin:0;}</style> <script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
        web_view.loadDataWithBaseURL("", varjs + content, "text/html", "UTF-8", null);
    }
    public void setData(){
        if (!isAdded()){
            new Handler().postDelayed(()->{setData();},500);
            return;
        }
        web_view.loadUrl("https://www.baidu.com");
    }

    @Override
    public void onDestroy() {

        DestoryWebview();
        super.onDestroy();


    }

    private void DestoryWebview() {
        if (web_view != null) {
            ViewGroup parent = (ViewGroup) web_view.getParent();
            if (parent != null) {
                parent.removeView(web_view);
            }
            web_view.removeAllViews();
            web_view.destroy();
        }
    }







}
