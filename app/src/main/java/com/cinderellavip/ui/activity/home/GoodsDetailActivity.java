package com.cinderellavip.ui.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.bean.eventbus.AddCart;
import com.cinderellavip.bean.eventbus.LoginSuccess;
import com.cinderellavip.bean.local.RequestSettlePara;
import com.cinderellavip.bean.net.goods.GoodsInfo;
import com.cinderellavip.bean.net.goods.GoodsResult;
import com.cinderellavip.bean.net.goods.GroupInfo;
import com.cinderellavip.bean.net.mine.MineInfo;
import com.cinderellavip.global.Constant;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.service.SobotUtils;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.activity.order.CartActivity;
import com.cinderellavip.ui.fragment.ShopFragment;
import com.cinderellavip.ui.fragment.goods.CommentFragment;
import com.cinderellavip.ui.fragment.goods.GoodsDetailFragment;
import com.cinderellavip.ui.fragment.goods.GraphicFragment;
import com.cinderellavip.util.Utils;
import com.cinderellavip.weight.VerticalViewPager;
import com.google.android.material.tabs.TabLayout;
import com.sobot.chat.SobotApi;
import com.sobot.chat.ZCSobotApi;
import com.sobot.chat.api.model.Information;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.tozzais.baselibrary.weight.ProgressLayout;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
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
    @BindView(R.id.ll_buy_left_btn)
    LinearLayout ll_buy_left_btn;
    @BindView(R.id.tv_left_price)
    TextView tvLeftPrice;
    @BindView(R.id.tv_right_price)
    TextView tvRightPrice;
    @BindView(R.id.goods_layout)
    ProgressLayout goods_layout;

    private List<BaseFragment> fragmentList;
    private List<String> list_Title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
    }

    public static void launch(Activity activity, String id) {
        if (!Utils.isFastClick()){
            return;
        }
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
//        LogUtil.e("Detail == initView"+(savedInstanceState != null));
        if (fragmentManager == null)
            fragmentManager = getSupportFragmentManager();
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(view -> back());
        id = getIntent().getStringExtra("id");

        fragmentList = new ArrayList<>();
        list_Title = new ArrayList<>();
        //加入详情
        goodsDetailGoodsFragment = new GoodsDetailFragment();
        fragmentList.add(goodsDetailGoodsFragment);
        //图文
        graphicFragment = new GraphicFragment();
        fragmentList.add(graphicFragment);
        //评价
        commentFragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        commentFragment.setArguments(bundle);
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

    private static final String TAG_DETAIL = "tag_detail";
    private static final String TAG_GRAPHIC = "tag_graphic";
    private static final String TAG_COMMENT = "tag_comment";
    private FragmentManager fragmentManager;
    private GoodsDetailFragment goodsDetailGoodsFragment;
    private GraphicFragment graphicFragment;
    private CommentFragment commentFragment;


    @Override
    public void loadData() {
        if (!isLoad) goods_layout.showLoading();
        new RxHttp<BaseResult<GoodsResult>>().send(ApiManager.getService().getGoodsDetail(id),
                new Response<BaseResult<GoodsResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<GoodsResult> result) {
                        goods_layout.showContent();
                        //延时加载不会回报错
                        new Handler().postDelayed(()->{setData(result.data);},200);

                    }

                    @Override
                    public void onErrorShow(String s) {
                        goods_layout.showError(s, v -> {
                            loadData();
                        });
                    }
                });
    }

    private void setData( GoodsResult goodsResult){
        this.goodsResult = goodsResult;
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
            ll_buy_left_btn.setVisibility(View.VISIBLE);
        }else if (productInfo.hasSpike){
            ll_buy_left_btn.setVisibility(View.GONE);
            tvRightPrice.setText("立即秒杀");
        }else {
            ll_buy_left_btn.setVisibility(View.VISIBLE);
            tvLeftPrice.setText("加入购物车");
            tvRightPrice.setText("立即购买");
        }
        isLoad = true;

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        goodsDetailGoodsFragment = (GoodsDetailFragment) fragmentManager.getFragment(savedInstanceState,TAG_DETAIL);
        graphicFragment = (GraphicFragment) fragmentManager.getFragment(savedInstanceState,TAG_GRAPHIC);
        commentFragment = (CommentFragment) fragmentManager.getFragment(savedInstanceState,TAG_COMMENT);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (goodsDetailGoodsFragment != null && goodsDetailGoodsFragment.isAdded()){
            fragmentManager.putFragment(outState,TAG_DETAIL,goodsDetailGoodsFragment);
        }
        if (graphicFragment != null && goodsDetailGoodsFragment.isAdded()){
            fragmentManager.putFragment(outState,TAG_GRAPHIC,graphicFragment);
        }
        if (commentFragment != null && commentFragment.isAdded()){
            fragmentManager.putFragment(outState,TAG_COMMENT,commentFragment);
        }


    }

    private GoodsResult goodsResult;

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
//                shareTextAndImage(SHARE_MEDIA.WEIXIN);
                if (GlobalParam.getUserLogin(mActivity))
                CartActivity.launch(mActivity);

                break;
            case R.id.iv_share:
                if (GlobalParam.getUserLogin(mActivity)){
                    if (GlobalParam.getIsVip()){
                        //没有用户信息
                        if (GlobalParam.getUserBean() == null){
                            new RxHttp<BaseResult<MineInfo>>().send(ApiManager.getService().getMineInfo(),
                                    new Response<BaseResult<MineInfo>>(isLoad,mActivity) {
                                        @Override
                                        public void onSuccess(BaseResult<MineInfo> result) {
                                            GlobalParam.setUserBean(result.data);
                                            ShareActivity.launch(mActivity,id);
                                        }
                                    });
                        }else {
                            ShareActivity.launch(mActivity,id);
                        }

                    }else {
                        CenterDialogUtil.showBulletin(mActivity);
                    }
                }



                break;
            case R.id.tv_service:
                    if (GlobalParam.getUserLogin(mActivity)){
                        SobotUtils.start(GoodsDetailActivity.this);
                    }

                break;
            case R.id.tv_shop:
                if (goodsResult != null){
                    GoodsInfo goodsInfo = goodsResult.product_info;
                    ShopDetailActivity.launchShop(mActivity,goodsInfo.store_id+"");
                }

                break;
            case R.id.ll_buy_left_btn:
                if (GlobalParam.getUserLogin(mActivity))
                if (goodsResult != null) {
                    DialogUtil.showSpeciSpecialDialog(mContext,goodsResult, true,(norm_id, number) -> {
                        if (goodsResult.product_info.hasGroup){
                            RequestSettlePara para = new RequestSettlePara(RequestSettlePara.PRODUCT, id, norm_id, number);
                            EnsureOrderActivity.launch(mActivity,para);
                        }else {
                            addCart(norm_id,number);
                        }

                    });
                }
                break;
            case R.id.ll_buy_right_btn:
                if (GlobalParam.getUserLogin(mActivity))
                if (goodsResult != null) {
                    DialogUtil.showSpeciSpecialDialog(mContext,goodsResult, false,(norm_id, number) -> {
                        if (goodsResult.product_info.hasGroup){
                            RequestSettlePara para = new RequestSettlePara(RequestSettlePara.GROUP, id, norm_id, number);
                            EnsureOrderActivity.launch(mActivity,para);
                        }else {
                            RequestSettlePara para = new RequestSettlePara(RequestSettlePara.PRODUCT, id, norm_id, number);
                            EnsureOrderActivity.launch(mActivity,para);
                        }

                    });
                }
                break;
        }
    }

    public void shareTextAndImage(SHARE_MEDIA share_media) {
        String url = "https://hongkong-shopping.cn/gp/profile/html5/product/goodsDetail.html?" +
                "choose_product_id=" + id + "&user_id=" + GlobalParam.getUserId();
        UMWeb web = new UMWeb(url);
        web.setTitle(goodsResult.product_info.name);
        web.setThumb(new UMImage(this, goodsResult.product_info.store_name));
        web.setDescription("邀请好友注册，可获得积分奖励");
        new ShareAction(mActivity).withMedia(web)
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            tsg("分享成功");
//            Toast.makeText(ShareDetailActivity.this,"成功了",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText(ShareDetailActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(ShareDetailActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };

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

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof LoginSuccess){
            loadData();
        }
    }
    @Override
    public void back() {
        if (goodsDetailGoodsFragment.mVideoView == null || !goodsDetailGoodsFragment.mVideoView.onBackPressed()) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获取当前焦点所在的控件；
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int) ev.getRawX();
                int rawY = (int) ev.getRawY();
                // 判断点击的点是否落在当前焦点所在的 view 上；
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
