package com.cinderellavip.ui.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.toast.SecondDialogUtil;
import com.cinderellavip.ui.fragment.goods.CommentFragment;
import com.cinderellavip.ui.fragment.goods.GoodsDetailFragment;
import com.cinderellavip.ui.fragment.goods.GraphicFragment;
import com.cinderellavip.weight.VerticalViewPager;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;

import java.util.ArrayList;
import java.util.List;

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


    public static void launch(Activity activity, int id) {
        Intent intent = new Intent(activity, GoodsDetailActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    private int id;

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
        id = getIntent().getIntExtra("id", 0);

        fragmentList = new ArrayList<>();
        list_Title = new ArrayList<>();
        goodsDetailGoodsFragment = new GoodsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        goodsDetailGoodsFragment.setArguments(bundle);
        fragmentList.add(goodsDetailGoodsFragment);
        graphicFragment = new GraphicFragment();
        fragmentList.add(graphicFragment);
        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("id", id);
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
        new Handler().postDelayed(() -> {
            graphicFragment.setData();
        }, 500);

    }

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
                break;
            case R.id.iv_share:
                SecondDialogUtil.showPosterDialog(mContext, (payString1, bitmap) -> {
                    switch (payString1){
                        case "1":
                            tsg("分享微信");
//                            shareImage(SHARE_MEDIA.WEIXIN,bitmap);
                            break;
                        case "2":
                            tsg("分享朋友圈");
//                            shareImage(SHARE_MEDIA.WEIXIN_CIRCLE,bitmap);
                            break;
                        case "down":
                            tsg("保存成功");
                            break;
                    }

                });
                break;
            case R.id.tv_service:
                break;
            case R.id.tv_shop:
                break;
            case R.id.ll_buy_left_btn:
                DialogUtil.showSpeciSpecialDialog(mContext,payString -> {});
                break;
            case R.id.ll_buy_right_btn:
                DialogUtil.showSpeciSpecialDialog(mContext,payString -> {});
                break;
        }
    }
}
