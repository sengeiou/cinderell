package com.cinderellavip.ui.fragment.goods;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CommentAdapter;
import com.cinderellavip.adapter.recycleview.RecommentGoodsAdapter;
import com.cinderellavip.banner.BannerUtil;
import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.activity.home.BrandDetailActivity;
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;
import com.cinderellavip.util.DataUtil;
import com.stx.xhb.xbanner.XBanner;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.sign.SignUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class GoodsDetailFragment extends BaseFragment {

    @BindView(R.id.xbanner)
    XBanner xbanner;//banner
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


    private int id;

    @Override
    public int setLayout() {
        return R.layout.fragment_goodsdetail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tvAdvancePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //下划线
        id = getArguments().getInt("id");
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

        setData();


    }

    public void setData() {
        BannerUtil.initBanner(mActivity, xbanner, "details.pics;details.pics;details.pics");
        tvGoodsName.setText("MAC 魅可 时尚唇膏 口红 3克小辣椒牛血麻辣鸡丝西柚都是");
        tvPrice.setText("178");
        tvAdvancePrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线


        commentInAdapter.setNewData(DataUtil.getData(2));
        recommentInAdapter.setNewData(DataUtil.getData(6));
    }

    @OnClick({R.id.ll_coupon, R.id.ll_comment, R.id.ll_merchant})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_coupon:
                List<CouponsBean> list = new ArrayList<>();
                list.add(new CouponsBean(CouponsBean.RECEIVED));
                list.add(new CouponsBean(CouponsBean.NO_HAVE));
                list.add(new CouponsBean(CouponsBean.NORMAL));
                DialogUtil.showReceiveCouponDialog(mActivity,list);

                break;
            case R.id.ll_comment:
                ((GoodsDetailActivity) mActivity).setCurrent(2);
                break;
            case R.id.ll_merchant:
                BrandDetailActivity.launch(mActivity);
//                MerchantDetailForNewActivity.launch(mActivity, (int) merchantName.getTag());
                break;
        }
    }


}
