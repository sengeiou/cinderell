package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.fragment.ShopDetailFragment;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ShopDetailActivity extends BaseActivity{


    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;

    public void setTvTitleName(String title) {
        tvTitleName.setText(title);
    }

    public static void launchShop(Context from, String id) {
        Intent intent = new Intent(from, ShopDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id",id);
        from.startActivity(intent);
    }

    public static void launch(Context from) {
        Intent intent = new Intent(from, ShopDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        from.startActivity(intent);
    }

    String id;

//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_shop_detail;
//    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
         id = intent.getStringExtra("id");

    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_shop_detail;
    }

    @Override
    public void loadData() {



        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,
                ShopDetailFragment.newInstance(id)).commit();


    }

    @Override
    public void initListener() {
    }





    @OnClick({R.id.ll_back, R.id.ll_search, R.id.rl_collect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                mActivity.finish();
                break;
            case R.id.ll_search:
                break;
            case R.id.rl_collect:
                collect();
                break;

        }
    }

    private void collect(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("id", id + "");
        hashMap.put("type",   "2");
        new RxHttp<BaseResult>().send(ApiManager.getService().getCollect(hashMap),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        isCollect = !isCollect;
                        setIvCollect(isCollect);


                    }
                });

    }

    private boolean isCollect;
    public void setIvCollect(boolean isCollect){
        this.isCollect = isCollect;
        if (isCollect) {
            ivCollect.setImageResource(R.mipmap.brand_collect_select);
        } else {
            ivCollect.setImageResource(R.mipmap.brand_collect);
        }
    }


}
