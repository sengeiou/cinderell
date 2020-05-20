package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.GroupDetailUserAdapter;
import com.cinderellavip.adapter.recycleview.OrderGoodsAdapter;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.bean.net.order.OrderGoodsInfo;
import com.cinderellavip.bean.net.order.OrderInfo;
import com.cinderellavip.bean.net.order.OrderInfoResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.SecondDialogUtil;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;
import com.cinderellavip.util.ClipBoardUtil;
import com.cinderellavip.weight.CountDownView;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class MineGroupDetailActivity extends BaseActivity {


    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tv_group_number)
    TextView tvGroupNumber;
    @BindView(R.id.cd_time)
    CountDownView cdTime;
    @BindView(R.id.rv_user)
    RecyclerView rvUser;
    @BindView(R.id.tv_invite)
    TextView tvInvite;
    @BindView(R.id.lv_goods)
    RecyclerView lvGoods;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_discounted_price)
    TextView tvDiscountedPrice;
    @BindView(R.id.tv_postage)
    TextView tvPostage;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.tv_numbering)
    TextView tvNumbering;
    @BindView(R.id.tv_copy_numbering)
    TextView tvCopyNumbering;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;

    @BindView(R.id.tv_shop)
    TextView tvShop;
    @BindView(R.id.tv_goods_number)
    TextView tvGoodsNumber;


    private String order_id;

    public static void launch(Context from, String order_id) {
        Intent intent = new Intent(from, MineGroupDetailActivity.class);
        intent.putExtra("order_id", order_id);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        order_id = getIntent().getStringExtra("order_id");
        setBackTitle("拼团详情");

    }


    @Override
    public void loadData() {

        getDetailInfo();
    }


    private void getDetailInfo() {
        new RxHttp<BaseResult<OrderInfoResult<OrderInfo>>>().send(ApiManager.getService().getGroupOrderDetail(order_id + ""),
                new Response<BaseResult<OrderInfoResult<OrderInfo>>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<OrderInfoResult<OrderInfo>> result) {
                        setData(result.data.order);
                    }
                });

    }

    private OrderInfo orderInfo;
    private void setData(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        if (orderInfo.status==2) {
            //待成团
            ivStatus.setImageResource(R.mipmap.icon_daichengtuan);
            tvStatus.setText("待成团");
            tvGroupNumber.setText(orderInfo.group_access);
            cdTime.setVisibility(View.VISIBLE);
            tvInvite.setVisibility(View.VISIBLE);
            cdTime.startTime((int) (orderInfo.end_time - orderInfo.timestamp));
            cdTime.setTimeBackhround(R.color.yellow_shadow);
            cdTime.setPointColor(R.color.yellow_deep);
            cdTime.setTextColor(R.color.yellow_deep);
            cdTime.setListener(isFinish -> {
                if (true) loadData();
            });

        } else if (orderInfo.status==3) {
            //已成团
            ivStatus.setImageResource(R.mipmap.icon_yichengtuan);
            tvStatus.setText("已成团，待发货");
            tvGroupNumber.setText(orderInfo.group_access);
            tvGroupNumber.setTextColor(getColor(R.color.baseColor));
            cdTime.setVisibility(View.GONE);
            tvInvite.setVisibility(View.GONE);
        } else if (orderInfo.status==4) {
            ivStatus.setImageResource(R.mipmap.icon_weichengtuan);
            tvStatus.setText("未成团");
            tvGroupNumber.setText(orderInfo.group_access);
            cdTime.setVisibility(View.GONE);
            tvInvite.setVisibility(View.GONE);

        }
        NetCityBean address = orderInfo.address;
        List<OrderGoodsInfo> goods = orderInfo.goods;
        tvName.setText(address.name+"  "+address.mobile);
        tvAddress.setText(address.province+address.city+address.area+address.address);

        tvShop.setText(orderInfo.store_name);
        tvGoodsNumber.setText("（共计"+ goods.size()+"件）");

        lvGoods.setLayoutManager(new LinearLayoutManager(mActivity));
        OrderGoodsAdapter adapter = new OrderGoodsAdapter(OrderGoodsAdapter.RETURN);
        lvGoods.setAdapter(adapter);
        adapter.setNewData(orderInfo.goods);


        tvGoodsPrice.setText("￥"+orderInfo.goods_amount);
        tvDiscountedPrice.setText("￥"+orderInfo.dis_amount);
        tvPostage.setText("￥"+orderInfo.ship_amount);
        tvOrderPrice.setText("￥"+orderInfo.total_amount);

        tvOrderTime.setText("下单时间："+orderInfo.create_at);
        tvNumbering.setText("订单编号："+orderInfo.order_no);

        tvPayTime.setText("付款时间："+orderInfo.pay_at);
        tvPayWay.setText("支付方式："+orderInfo.payment);

        rvUser.setLayoutManager(new GridLayoutManager(mContext, 5));
        GroupDetailUserAdapter mAdapter = new GroupDetailUserAdapter();
        rvUser.setAdapter(mAdapter);
        for (int j = orderInfo.group_users.size();j<orderInfo.total_user;j++){
            orderInfo.group_users.add("");
        }
        mAdapter.setNewData(orderInfo.group_users);




    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_groupup_detail;
    }


    @OnClick({R.id.tv_shop,R.id.tv_invite,R.id.tv_copy_numbering})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_copy_numbering:
                if(orderInfo != null)
                    ClipBoardUtil.copy(mActivity, orderInfo.order_no);
                break;

            case R.id.tv_shop:
                if (orderInfo != null)
                ShopDetailActivity.launchShop(mActivity,orderInfo.store_id+"");
                break;
            case R.id.tv_invite:
                SecondDialogUtil.showPosterDialog(mContext, null,null,(payString1, bitmap) -> {
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
        }

    }
}
