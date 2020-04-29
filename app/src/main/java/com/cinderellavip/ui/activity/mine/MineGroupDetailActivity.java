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
import com.cinderellavip.toast.SecondDialogUtil;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.CountDownView;
import com.tozzais.baselibrary.ui.BaseActivity;

import java.util.ArrayList;
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


    private String type;

    public static void launch(Context from, String type) {
        Intent intent = new Intent(from, MineGroupDetailActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        setBackTitle("拼团详情");

    }


    @Override
    public void loadData() {
        List<String> userList = new ArrayList<>();
        userList.add("1");
        userList.add("1");
        userList.add("1");
        if (type.equals("0")) {
            ivStatus.setImageResource(R.mipmap.icon_daichengtuan);
            tvStatus.setText("待成团");
            tvGroupNumber.setText("5人团，还差2人");
            cdTime.setVisibility(View.VISIBLE);
            tvInvite.setVisibility(View.VISIBLE);
            cdTime.startTime(15 * 3600 + 34 * 60 + 29);
            cdTime.setTimeBackhround(R.color.yellow_shadow);
            cdTime.setPointColor(R.color.yellow_deep);
            cdTime.setTextColor(R.color.yellow_deep);
            userList.add("");
            userList.add("");
        } else if (type.equals("1")) {
            ivStatus.setImageResource(R.mipmap.icon_yichengtuan);
            tvStatus.setText("已成团，待发货");
            tvGroupNumber.setText("5人团，拼团成功");
            tvGroupNumber.setTextColor(getColor(R.color.baseColor));
            userList.add("1");
            userList.add("1");
        } else if (type.equals("2")) {
            ivStatus.setImageResource(R.mipmap.icon_weichengtuan);
            tvStatus.setText("未成团");
            tvGroupNumber.setText("5人团，拼团失败");
            userList.add("");
            userList.add("");
        }

        //用户
        rvUser.setLayoutManager(new GridLayoutManager(mContext, 5));
        GroupDetailUserAdapter mAdapter = new GroupDetailUserAdapter();
        rvUser.setAdapter(mAdapter);
        mAdapter.setNewData(userList);


        //商品
        lvGoods.setLayoutManager(new LinearLayoutManager(mActivity));
        OrderGoodsAdapter adapter = new OrderGoodsAdapter(OrderGoodsAdapter.RETURN);
        lvGoods.setAdapter(adapter);
        adapter.setNewData(DataUtil.getData(1));

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_groupup_detail;
    }


    @OnClick({R.id.tv_shop,R.id.tv_invite})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_shop:
                ShopDetailActivity.launch(mActivity);
                break;
            case R.id.tv_invite:
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
        }

    }
}
