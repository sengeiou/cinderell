package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.ShopInfo;
import com.cinderellavip.bean.net.ShopResult;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.listener.OnSureClickListener;
import com.cinderellavip.ui.fragment.ShopDetailFragment;
import com.cinderellavip.util.Utils;
import com.cinderellavip.weight.FilterView;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ShopDetailActivity extends BaseActivity implements OnSureClickListener {


    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.tv_collect)
    TextView tv_collect;
    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    @BindView(R.id.iv_image)
    ImageView iv_image;

    @BindView(R.id.filter_view)
    FilterView filter_view;

    public void setTvTitleName(String title) {
        tvTitleName.setText(title);
    }

    public static void launchShop(Context from, String id) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, ShopDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id",id);
        from.startActivity(intent);
    }
    String id;


    @Override
    public void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
         id = intent.getStringExtra("id");
        filter_view.setTv_comment("新品");
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(ShopDetailActivity.this,null);
        StatusBarUtil.setLightMode(this);
    }

    private ShopInfo storeInfo;
    private void getShopInfo() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("store_id", id + "");
        new RxHttp<BaseResult<ShopResult>>().send(ApiManager.getService().getShopInfo(hashMap),
                new Response<BaseResult<ShopResult>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<ShopResult> result) {
                        storeInfo = result.data.store_info;
                        if(mActivity instanceof ShopDetailActivity ){
                            setTvTitleName(storeInfo.name);
                            setIvCollect(storeInfo.collect);
                            ImageUtil.loadNet(mActivity,iv_logo,storeInfo.logo);
                            ImageUtil.loadNet(mActivity,iv_image,storeInfo.image);
                        }

                    }
                });
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
        getShopInfo();

        fragment =  ShopDetailFragment.newInstance(id);
        getSupportFragmentManager().beginTransaction().add(R.id.brand_container,
                fragment).commit();

    }
    private ShopDetailFragment fragment;

    @Override
    public void initListener() {
        filter_view.setOnDialogClickListener(this);
    }





    @OnClick({R.id.iv_back, R.id.ll_search, R.id.ll_service, R.id.ll_category})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                mActivity.finish();
                break;
            case R.id.ll_search:
                SearchListActivity.launch(mActivity,id,SearchListActivity.SHOP);
                break;
            case R.id.ll_service:
                collect();
                break;
            case R.id.ll_category:
                ShopGoodsCategoryActivity.launch(mActivity,Integer.parseInt(id));
                break;

        }
    }

    private void collect(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("id", id + "");
        hashMap.put("type",   "1");
        new RxHttp<BaseResult>().send(ApiManager.getService().getCollect(hashMap),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        storeInfo.collect = !storeInfo.collect;
                        tsg(storeInfo.collect?"收藏成功":"取消收藏");
                        setIvCollect(storeInfo.collect);
                    }
                });

    }
//
    public void setIvCollect(boolean isCollect){
        if (isCollect) {

            Drawable drawable= getResources().getDrawable(R.mipmap.brand_collect_select);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_collect.setCompoundDrawables(drawable,null,null,null);
        } else {
            Drawable drawable= getResources().getDrawable(R.mipmap.brand_collect);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_collect.setCompoundDrawables(drawable,null,null,null);
        }
    }


    @Override
    public void onSure() {
        fragment.setSortAndArea(filter_view.getSort()+"",filter_view.getSort_type()+"");
    }
}
