package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.ui.fragment.life.SelectServiceCouponFragment;
import com.cinderellavip.ui.fragment.life.ServiceCheckListFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 * 服务清单
 */
public class ServiceCheckListActivity extends BaseActivity {



    public static void launch(Activity from) {
        Intent intent = new Intent(from, ServiceCheckListActivity.class);
        from.startActivityForResult(intent, RequestCode.request_service_coupon);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("服务清单");
        setRightIcon(R.mipmap.icon_search_black);



    }


    @Override
    public void loadData() {
        getSupportFragmentManager().beginTransaction().add(R.id.content_container,
                new ServiceCheckListFragment()).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initListener() {
        super.initListener();
        iv_right_icon.setOnClickListener(v -> {
                SearchLifeActivity.launch(mActivity);
        });
    }
}
