package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.global.Constant;
import com.cinderellavip.ui.fragment.mine.MinePotatoFragment;
import com.cinderellavip.ui.fragment.mine.SmallVaultFragment;
import com.cinderellavip.ui.web.AgreementWebViewActivity;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class MinePotatoActivity extends BaseActivity {



    public static void launch(Context from) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, MinePotatoActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("我的灰豆");
        setRightIcon(R.mipmap.icon_rule);
    }


    @Override
    public void loadData() {
        MinePotatoFragment fragment = new MinePotatoFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


    @Override
    public void initListener() {
        super.initListener();
        iv_right_icon.setOnClickListener(v -> {
            AgreementWebViewActivity.launch(mActivity, Constant.H18);
        });
    }
}
