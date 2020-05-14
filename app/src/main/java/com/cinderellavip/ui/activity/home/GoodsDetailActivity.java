package com.cinderellavip.ui.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.bean.eventbus.AddCart;
import com.cinderellavip.bean.local.RequestSettlePara;
import com.cinderellavip.bean.net.goods.GoodsInfo;
import com.cinderellavip.bean.net.goods.GoodsResult;
import com.cinderellavip.bean.net.goods.GroupInfo;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.activity.order.CartActivity;
import com.cinderellavip.ui.fragment.goods.CommentFragment;
import com.cinderellavip.ui.fragment.goods.GoodsDetailFragment;
import com.cinderellavip.ui.fragment.goods.GraphicFragment;
import com.cinderellavip.weight.VerticalViewPager;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

public class GoodsDetailActivity extends CheckPermissionActivity {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    VerticalViewPager viewpager;
    @BindView(R.id.tv_cart_number)
    TextView tv_cart_number;
    @BindView(R.id.tv_left_price)
    TextView tvLeftPrice;
    @BindView(R.id.tv_right_price)
    TextView tvRightPrice;

    private List<BaseFragment> fragmentList;
    private List<String> list_Title;


    public static void launch(Activity activity, String id) {
        Intent intent = new Intent(activity, GoodsDetailActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }
    private String id;

    public String getId() {
        return id;
    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(view -> finish());
        id = getIntent().getStringExtra("id");

        fragmentList = new ArrayList<>();
        list_Title = new ArrayList<>();
        goodsDetailGoodsFragment = new GoodsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        goodsDetailGoodsFragment.setArguments(bundle);
        fragmentList.add(goodsDetailGoodsFragment);
        graphicFragment = new GraphicFragment();
        fragmentList.add(graphicFragment);
        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("id", id);
        commentFragment.setArguments(bundle1);
        fragmentList.add(commentFragment);
        list_Title.add("商品");
        list_Title.add("详情");
        list_Title.add("评价");
        for (int i = 0; i < list_Title.size(); i++) {
            tablayout.addTab(tablayout.newTab());
            tablayout.getTabAt(i).setText(list_Title.get(i));
        }
        GoodsDetailPagerAdapter goodsDetailAdapter = new
                GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList, list_Title);
        viewpager.setAdapter(goodsDetailAdapter);
        viewpager.setOffscreenPageLimit(3);
    }

    GoodsDetailFragment goodsDetailGoodsFragment;
    GraphicFragment graphicFragment;


    @Override
    public void loadData() {

        new RxHttp<BaseResult<GoodsResult>>().send(ApiManager.getService().getGoodsDetail(id),
                new Response<BaseResult<GoodsResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<GoodsResult> result) {
                        goodsResult = result.data;
                        GoodsInfo productInfo = goodsResult.product_info;
                        if (goodsDetailGoodsFragment != null)
                        goodsDetailGoodsFragment.setData(goodsResult);
                        if (graphicFragment != null) {
                            graphicFragment.setData(productInfo.detail);
                        }
                        if (productInfo.hasGroup){
                            GroupInfo groupInfo = goodsResult.group_info;
                            tvLeftPrice.setText("￥"+groupInfo.getProduct_price()+"\n单独购买");
                            tvRightPrice.setText("￥"+groupInfo.getGroup_price()+"\n参团购买");
                        }else {
                            tvLeftPrice.setText("加入购物车");
                            tvRightPrice.setText("立即购买");
                        }

                    }
                });
    }

    GoodsResult goodsResult;

    @Override
    public void initListener() {
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                tablayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewpager.setCurrentItem(position);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }


    public void setCurrent(int position) {
        viewpager.setCurrentItem(position);
    }


    @Override
    public void permissionGranted() {

    }


    @OnClick({R.id.rv_cart, R.id.iv_share, R.id.tv_service, R.id.tv_shop, R.id.ll_buy_left_btn, R.id.ll_buy_right_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rv_cart:
                CartActivity.launch(mActivity);

                break;
            case R.id.iv_share:
                ShareActivity.launch(mActivity,0);
                break;
            case R.id.tv_service:
                DialogUtil.showCallPhoneDialog(mActivity);
                break;
            case R.id.tv_shop:
                if (goodsResult != null){
                    GoodsInfo goodsInfo = goodsResult.product_info;
                    ShopDetailActivity.launchShop(mActivity,goodsInfo.store_id+"");
                }

                break;
            case R.id.ll_buy_left_btn:
                if (goodsResult != null) {
                    DialogUtil.showSpeciSpecialDialog(mContext,goodsResult, (norm_id, number) -> {
                        addCart(norm_id,number);
                    });
                }
                break;
            case R.id.ll_buy_right_btn:
                if (goodsResult != null) {
                    DialogUtil.showSpeciSpecialDialog(mContext,goodsResult, (norm_id, number) -> {
                        RequestSettlePara para = new RequestSettlePara(RequestSettlePara.PRODUCT, id, norm_id, number);
                        EnsureOrderActivity.launch(mActivity,para);
                    });
                }
//                DialogUtil.showSpeciSpecialDialog(mContext,payString -> {
//                    EnsureOrderActivity.launch(mActivity);
//                });
                break;
        }
    }

    private void addCart(String norm_id,String number){
            TreeMap<String, String> hashMap = new TreeMap<>();
            hashMap.put("product_id", id + "");
            hashMap.put("norm_id", norm_id + "");
            hashMap.put("number",   number);
            new RxHttp<BaseResult>().send(ApiManager.getService().getAddCart(hashMap),
                    new Response<BaseResult>(mActivity) {
                        @Override
                        public void onSuccess(BaseResult result) {
                            EventBus.getDefault().post(new AddCart());
                          tsg("商品已成功加入购物车");
                        }
                    });
    }
}
