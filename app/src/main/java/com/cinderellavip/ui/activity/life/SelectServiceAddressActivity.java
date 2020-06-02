package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.ui.fragment.life.LifeAddressFragment;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SelectServiceAddressActivity extends BaseActivity {


    public static final int LOOK = 1;
    public static final int SELECT = 2;


    private int type;

    public static void launch(Activity activity, int type){
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(activity, SelectServiceAddressActivity.class);
        intent.putExtra("type",type);
        activity.startActivityForResult(intent, RequestCode.request_service_address);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        type = getIntent().getIntExtra("type", LOOK);
        setBackTitle("选择城市");
        if (type == SELECT){
            setBackTitle("选择服务地址");
        }


    }



    @Override
    public void loadData() {
        LifeAddressFragment fragment = new LifeAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


}
