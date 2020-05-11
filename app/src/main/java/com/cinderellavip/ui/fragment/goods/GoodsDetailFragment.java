package com.cinderellavip.ui.fragment.goods;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CommentAdapter;
import com.cinderellavip.adapter.recycleview.RecommentGoodsAdapter;
import com.cinderellavip.adapter.viewpager.DetailBannerAdapter;
import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.bean.local.GoodsDetialBanner;
import com.cinderellavip.bean.net.goods.GoodsInfo;
import com.cinderellavip.bean.net.goods.GoodsResult;
import com.cinderellavip.bean.net.goods.GroupInfo;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.activity.home.BrandDetailActivity;
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;
import com.cinderellavip.weight.CountDownView;
import com.cinderellavip.weight.MyIndicator;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

public class GoodsDetailFragment extends BaseFragment {
    @BindView(R.id.xbanner)
    ViewPager xbanner;
    @BindView(R.id.indicator)
    MyIndicator indicator;

    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName; //名称
    @BindView(R.id.tv_price)
    TextView tvPrice; //价格
    @BindView(R.id.tv_advance_price)
    TextView tvAdvancePrice;//原价
    @BindView(R.id.tv_ship)
    TextView tv_ship;//快递 包邮
    @BindView(R.id.tv_tax)
    TextView tv_tax;//快递 包邮
    @BindView(R.id.tv_intro)
    TextView tv_intro;//销量
    @BindView(R.id.tv_coupon_text)
    TextView tvCouponText;//满500减100
    @BindView(R.id.tv_promotion_text)
    TextView tv_promotion_text;//满500减100
    @BindView(R.id.merchant_icon)
    ImageView merchantIcon; //商家图标
    @BindView(R.id.merchant_name)
    TextView merchantName;//商家名称
    @BindView(R.id.tv_comment_number)
    TextView tvCommentNumber; //商品评价（4396）

    @BindView(R.id.ll_recommet_cookbook_space)
    View llRecommetCookbookSpace;//间隔
    @BindView(R.id.rl_comment)
    RecyclerView rlComment; //评价
    @BindView(R.id.rl_recommend)
    RecyclerView rlRecommend;//推荐

    @BindView(R.id.tv_group_old_price)
    TextView tvGroupOldPrice;


    @BindView(R.id.time_view)
    CountDownView timeView;
    @BindView(R.id.tv_group_price)
    TextView tvGroupPrice;
    @BindView(R.id.tv_group_tip)
    TextView tvGroupTip;
    @BindView(R.id.ll_group)
    LinearLayout llGroup;
    @BindView(R.id.ll_coupon)
    LinearLayout llCoupon;
    @BindView(R.id.ll_promotion)
    LinearLayout llPromotion;
    @BindView(R.id.ll_normal_price)
    LinearLayout ll_normal_price;



    @Override
    public int setLayout() {
        return R.layout.fragment_goodsdetail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tvAdvancePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //下划线
    }

    //评价
    CommentAdapter commentInAdapter;
    //推荐
    RecommentGoodsAdapter recommentInAdapter;

    @Override
    public void loadData() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlComment.setLayoutManager(linearLayoutManager);
        commentInAdapter = new CommentAdapter();
        rlComment.setAdapter(commentInAdapter);


        LinearLayoutManager recommentLayoutManager = new LinearLayoutManager(mActivity);
        recommentLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlRecommend.setLayoutManager(recommentLayoutManager);
        recommentInAdapter = new RecommentGoodsAdapter();
        rlRecommend.setAdapter(recommentInAdapter);


    }

    private GoodsResult goodsResult;

    public void setData(GoodsResult goodsResult) {
        this.goodsResult = goodsResult;
        GoodsInfo productInfo = goodsResult.product_info;

        List<GoodsDetialBanner> bannerList = new ArrayList<>();
        if (!TextUtils.isEmpty(productInfo.video)) {
            bannerList.add(new GoodsDetialBanner(productInfo.video, true));
        }
        for (String path : productInfo.images) {
            bannerList.add(new GoodsDetialBanner(path, false));
        }
        xbanner.setAdapter(new DetailBannerAdapter(bannerList, mActivity));
        indicator.bindViewPager(xbanner, bannerList.size());

        tvGroupOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线


        tvGoodsName.setText(productInfo.name);


        tv_ship.setText(productInfo.getShip());
        tv_tax.setText("销量：" + productInfo.sale + "件");
        tv_intro.setText("发货：" + productInfo.send_area);

        if (productInfo.hasGroup){
            //是团购商品
            llGroup.setVisibility(View.VISIBLE);
            GroupInfo group_info = goodsResult.group_info;

            tvGroupPrice.setText(group_info.getGroup_price());
            tvGroupOldPrice.setText(group_info.getProduct_price());
            tvGroupTip.setText(group_info.group_user+"人团，"+group_info.has_user+"人已参团");

            timeView.startTime(group_info.end_time - group_info.timestamp);
        }else {
            ll_normal_price.setVisibility(View.VISIBLE);
            tvPrice.setText(productInfo.getPrice());
            tvAdvancePrice.setText("￥" + productInfo.getOld_price());
        }

        tvAdvancePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线

        List<CouponsBean> coupons = goodsResult.coupons;
        if (coupons == null || coupons.size() == 0) {
            tvCouponText.setText("暂无优惠券");
            tvCouponText.setTextColor(getResources().getColor(R.color.grayText));
        } else {
            CouponsBean couponsBean = coupons.get(0);
            tvCouponText.setText(couponsBean.condition);
        }
        if (goodsResult.actives == null || goodsResult.actives.size() == 0) {
            tv_promotion_text.setText("暂无活动");
            tv_promotion_text.setTextColor(getResources().getColor(R.color.grayText));
        } else {

        }
        ImageUtil.loadNet1(mActivity, merchantIcon, productInfo.brand_image);
        merchantName.setText(productInfo.brand_name);

        tvCommentNumber.setText("商品评价(" + goodsResult.comments_num + ")");
        recommentInAdapter.setNewData(goodsResult.recommend_products);
        commentInAdapter.setNewData(goodsResult.comments);





    }

    @OnClick({R.id.ll_coupon, R.id.ll_comment, R.id.ll_merchant})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_coupon:
                getCoupons();
                break;
            case R.id.ll_comment:
                ((GoodsDetailActivity) mActivity).setCurrent(2);
                break;
            case R.id.ll_merchant:
                if (goodsResult != null){
                    GoodsInfo goodsInfo = goodsResult.product_info;
//                    BrandDetailActivity.launch(mActivity);
//                    ShopDetailActivity.launchShop(mActivity,goodsInfo.brand_id+"",goodsInfo.brand_name);
                    BrandDetailActivity.launch(mActivity,goodsInfo.brand_id+"");
                }
                break;
        }
    }

    private void getCoupons(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("product_id", ((GoodsDetailActivity)mActivity).getId());
        new RxHttp<BaseResult<GoodsResult>>().send(ApiManager.getService().getGoodsCoupons(hashMap),
                new Response<BaseResult<GoodsResult>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<GoodsResult> result) {
                        DialogUtil.showReceiveCouponDialog(mActivity, result.data.coupons);
                    }

                });
    }


}
