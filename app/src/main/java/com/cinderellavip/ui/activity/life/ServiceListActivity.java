package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.life.ServiceListFragment;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.DpUtil;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ServiceListActivity extends BaseActivity {



    public static void launch(Context from,String name) {
        Intent intent = new Intent(from, ServiceListActivity.class);
        intent.putExtra("name",name);
        from.startActivity(intent);
    }

    private String name;

    @Override
    public void initView(Bundle savedInstanceState) {

        name = getIntent().getStringExtra("name");
        setBackTitle(name);


        setRightText("上海");

        Drawable nav_up =getResources().getDrawable(R.mipmap.icon_arrow_down);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        tv_right.setCompoundDrawables(null, null, nav_up, null);
        tv_right.setCompoundDrawablePadding(DpUtil.dip2px(mContext,5));



    }


    @Override
    public void loadData() {
        ServiceListFragment fragment = new ServiceListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


}
