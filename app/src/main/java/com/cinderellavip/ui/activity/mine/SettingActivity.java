package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.global.Constant;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.ui.activity.WebViewActivity;
import com.cinderellavip.ui.activity.account.LoginActivity;
import com.cinderellavip.ui.activity.account.ModifyPassActivity;
import com.cinderellavip.ui.web.AgreementWebViewActivity;
import com.cinderellavip.util.CacheClear;
import com.tozzais.baselibrary.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SettingActivity extends BaseActivity {


    @BindView(R.id.ll_modify_password)
    LinearLayout llModifyPassword;
    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;
    @BindView(R.id.ll_black_list)
    LinearLayout llBlackList;
    @BindView(R.id.ll_user_agreement)
    LinearLayout llUserAgreement;
    @BindView(R.id.ll_privacy_policy)
    LinearLayout llPrivacyPolicy;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.ll_clear)
    LinearLayout llClear;
    @BindView(R.id.tv_exit)
    TextView tvExit;

    public static void launch(Context from) {
        Intent intent = new Intent(from, SettingActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("设置");


    }


    @Override
    public void loadData() {
        tvCache.setText(getDataSize());

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }


    @OnClick({R.id.ll_modify_password, R.id.ll_about_us, R.id.ll_black_list, R.id.ll_user_agreement
            , R.id.ll_privacy_policy,  R.id.ll_clear, R.id.tv_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_modify_password:
                ModifyPassActivity.launch(mActivity);
                break;
            case R.id.ll_about_us:
                AgreementWebViewActivity.launch(mActivity, Constant.H5_ABOUT_US);
                break;
            case R.id.ll_black_list:
                BlackListActivity.launch(mActivity);
                break;
            case R.id.ll_user_agreement:
                AgreementWebViewActivity.launch(mActivity, Constant.H5_SERVICE);
                break;
            case R.id.ll_privacy_policy:
                AgreementWebViewActivity.launch(mActivity, Constant.H5_PRIVACY);
                break;
            case R.id.ll_clear:
                clearAppCache();
                break;
            case R.id.tv_exit:
                exit();
                break;
        }
    }
    private void exit(){
        CenterDialogUtil.showTwo(mContext,"提示","确定要退出当前账号？","取消","确定", s->{
            if (s.equals("1")){
                GlobalParam.setUserLogin(false);
                GlobalParam.setUserToken("");
                GlobalParam.setUserId("");
                LoginActivity.launch(mActivity);
                finish();
            }

        });
    }

    private final int CLEAN_SUC = 1001;
    private final int CLEAN_FAIL = 1002;
    public void clearAppCache() {
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    msg.what = CLEAN_SUC;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = CLEAN_FAIL;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    // 清除缓存Handler
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLEAN_FAIL:
                    //ToastUtils.showToastNew("清除失败");
                    break;
                case CLEAN_SUC:
                    CacheClear.cleanApplicationDataNoSP(mActivity);
                    String dataSize = getDataSize();//获取缓存大小
                    tvCache.setText(dataSize.equals("0.0Byte")?"0.0Byte":dataSize);
                    //ToastUtils.showToastNew("清除成功");
                    break;
            }
        }
    };

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        mContext.startActivity(intent);
    }

    //计算缓存
    private String getDataSize() {
        long fileSize = 0;
        File filesDir = getFilesDir();
        File cacheDir = getCacheDir();
        fileSize += CacheClear.getDirSize(filesDir);
        fileSize += CacheClear.getDirSize(cacheDir);
        String formatSize = CacheClear.getFormatSize(fileSize);
        return formatSize;
    }
}
