package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.life.LiftHomeCategory;
import com.cinderellavip.global.CinderellaApplication;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.ui.fragment.life.ServiceListFragment;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.DpUtil;

import androidx.annotation.Nullable;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ServiceListActivity extends BaseActivity {


    LiftHomeCategory item;
    public static void launch(Context from, LiftHomeCategory item) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, ServiceListActivity.class);
        intent.putExtra("item",item);
        from.startActivity(intent);
    }

    private String name;

    @Override
    public void initView(Bundle savedInstanceState) {

        name = getIntent().getStringExtra("name");


        item = getIntent().getParcelableExtra("item");
        setBackTitle(item.name);

        setRightText(CinderellaApplication.name);

        Drawable nav_up =getResources().getDrawable(R.mipmap.icon_arrow_down);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        tv_right.setCompoundDrawables(null, null, nav_up, null);
        tv_right.setCompoundDrawablePadding(DpUtil.dip2px(mContext,5));



    }


    @Override
    public void loadData() {
        serviceListFragment = ServiceListFragment.newInstance(item);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container,
                serviceListFragment).commit();
    }
    private ServiceListFragment serviceListFragment;

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initListener() {
        super.initListener();
        tv_right.setOnClickListener(v -> {
            SelectCityActivity.launch(mActivity);
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.request_service_city && resultCode == Activity.RESULT_OK){
            String name = data.getStringExtra("name");
            tv_right.setText(name);
            serviceListFragment.setAddress(name);
        }
    }
}
