package com.cinderellavip.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.mine.MineAddressFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


public class MineAddressActivity extends BaseActivity {
    public static final int LOOK = 1,SELETE = 2,EXCHANGE = 3;
    public static final int REQUESTCODE = 101;

    public static void launch(Activity activity, int type){
        Intent intent = new Intent(activity, MineAddressActivity.class);
        intent.putExtra("type",type);
        activity.startActivityForResult(intent,REQUESTCODE);
    }
    public static void launch(Activity activity, int type, String addressID){
        Intent intent = new Intent(activity, MineAddressActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("addressID",addressID);
        activity.startActivityForResult(intent,REQUESTCODE);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        int type = getIntent().getIntExtra("type", LOOK);
        String addressID = getIntent().getStringExtra("addressID");
        if (addressID == null){
            addressID = "";
        }
        if (type == SELETE  || type == EXCHANGE){
            setBackTitle("选择收货地址");
            if (type == EXCHANGE){
                setRightText("新增");
            }
        }else {
            setBackTitle("地址管理");
        }
        MineAddressFragment mineAddressFragment = new MineAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        bundle.putString("addressID",addressID);
        mineAddressFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, mineAddressFragment).commit();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initListener() {
        super.initListener();
        tv_right.setOnClickListener(v -> {
//            EditAddressActivity.launch(mActivity, EditAddressActivity.ADD);
        });
    }
}
