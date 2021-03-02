package com.cinderellavip.ui.web;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.AppJs;
import com.cinderellavip.R;
import com.cinderellavip.util.Utils;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.ycbjie.webviewlib.X5WebView;

import butterknife.BindView;
import butterknife.OnClick;
import me.jingbin.progress.WebProgress;


/**
 * Created by Administrator on 2016/9/8.
 */
public class AgreementWebViewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    X5WebView web_view;
    @BindView(R.id.progress)
    WebProgress mProgress;
    @BindView(R.id.title)
    TextView title;


    public static void launch(Context from,  String  type) {
        if (!Utils.isFastClick()){
            return;
        }

        Intent intent = new Intent(from, AgreementWebViewActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }





    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected int getToolbarLayout() {
        return R.layout.layout_header;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


    }

    @Override
    public void loadData() {

        web_view.loadUrl(getIntent().getStringExtra("type"));


        web_view.setWebChromeClient(new WebChrome());
        //显示进度条
        mProgress.show();
        mProgress.setColor("#FF0000");

        web_view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web_view.addJavascriptInterface(new AppJs(this), "toLogin");


    }
    public void toLogin(){
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.cinderellavip.store");
        if (intent != null) {
            startActivity(intent);
        } else {
//            tsg("");
        }

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.iv_left_back, R.id.iv_left_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left_back:
                if (web_view.canGoBack()){
                    web_view.goBack();
                }else {
                    finish();
                }
                break;
            case R.id.iv_left_close:
                finish();
                break;
        }
    }

    public class WebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            mProgress.setProgress(i);
            if (i == 100)
            title.setText(webView.getTitle());
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
            AgreementWebViewActivity.this.valueCallback = valueCallback;
            selectImage();
            return true;
        }
    }
    //全局声明，用于记录选择图片返回的地址值
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> valueCallback;
    private void selectImage() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), 15);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {
            web_view.goBack();// 返回前一个页面
                       return true;
                  }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 15) {
            if (null == uploadMessage && null == valueCallback) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (valueCallback != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != 15 || valueCallback == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        valueCallback.onReceiveValue(results);
        valueCallback = null;
    }
}
