package com.cinderellavip.ui.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.MainActivity;
import com.cinderellavip.R;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.listener.OnDialogClickListener;
import com.cinderellavip.toast.PrivacyUtil;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;


public class GuideActivity extends BaseActivity {


    @BindView(R.id.viewpager)
    ViewPager viewpager;

    public static void launch(Activity context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
        context.finish();


    }


    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void loadData() {
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            //解决测试
            // Duplicate finish request for ActivityRecord{d46542c u0 com.cinderellavip/.ui.activity.account.GuideActivity t51 f}
            if (!isFinishing())
            finish();
            return;
        }
        boolean firstUse = GlobalParam.getFirstUse();
        if (!firstUse) {
            PrivacyUtil.showTwo(mActivity, new OnDialogClickListener() {
                @Override
                public void onSure() {
                    GlobalParam.setFirstUse(true);
                    MainActivity.launch(mActivity);
                }
                @Override
                public void onCancel() {
                    if (!isFinishing())
                    finish();
                }
            });
        }else {
            MainActivity.launch(mActivity);
        }


    }

    @Override
    public void initListener() {

    }


    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    protected void setStatusBar() {
//        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
        StatusBarUtil.setTransparentForImageViewInFragment(GuideActivity.this, null);
        StatusBarUtil.setLightMode(this);
    }


}
