package com.cinderellavip.ui.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.bean.eventbus.UpdateMineInfo;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.bean.net.mine.MineInfo;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.activity.account.LoginActivity;
import com.cinderellavip.ui.activity.life.LongServiceOrderListActivity;
import com.cinderellavip.ui.activity.life.SingleServiceOrderListActivity;
import com.cinderellavip.ui.activity.mine.ApplyProductSupplierActivity;
import com.cinderellavip.ui.activity.mine.CouponCenterActivity;
import com.cinderellavip.ui.activity.mine.MessageActivity;
import com.cinderellavip.ui.activity.mine.MineAddressActivity;
import com.cinderellavip.ui.activity.mine.MineBalanceActivity;
import com.cinderellavip.ui.activity.mine.MineCollectActivity;
import com.cinderellavip.ui.activity.mine.MineDataActivity;
import com.cinderellavip.ui.activity.mine.MineGroupActivity;
import com.cinderellavip.ui.activity.mine.MineOrderActivity;
import com.cinderellavip.ui.activity.mine.RecommentListActivity;
import com.cinderellavip.ui.activity.mine.SettingActivity;
import com.cinderellavip.ui.activity.mine.SmallVaultActivity;
import com.cinderellavip.ui.activity.order.ReturnListActivity;
import com.cinderellavip.ui.fragment.mine.OrderFragment;
import com.cinderellavip.util.ColorUtil;
import com.cinderellavip.weight.CircleImageView;
import com.cinderellavip.weight.GirdSpace;
import com.google.android.material.appbar.AppBarLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.util.log.LogUtil;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class MineFragment extends BaseListFragment<HomeGoods> {

    //个人资料是否加载成功 加载成功之后 则一直算成功
    public boolean mineInfoIsLoad = false;

    @BindView(R.id.dot_message)
    View dot_message;

    @BindView(R.id.vi_image)
    CircleImageView vi_image;
    @BindView(R.id.ll_logined_info)
    LinearLayout llLoginedInfo;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_roles)
    TextView tv_roles;
    @BindView(R.id.iv_role_tabel)
    ImageView iv_role_tabel;
    @BindView(R.id.tv_to_be_cinderell)
    TextView tv_to_be_cinderell;

    @BindView(R.id.tv_unpay)
    TextView tv_unpay;
    @BindView(R.id.tv_unsend)
    TextView tv_unsend;
    @BindView(R.id.tv_unreceive)
    TextView tv_unreceive;
    @BindView(R.id.tv_unpay_first)
    TextView tv_unpay_first;
    @BindView(R.id.tv_unservice_first)
    TextView tv_unservice_first;
    @BindView(R.id.tv_uncomment_first)
    TextView tv_uncomment_first;
    @BindView(R.id.tv_to_be_confirm_long)
    TextView tv_to_be_confirm_long;
    @BindView(R.id.tv_unpay_long)
    TextView tv_unpay_long;
    @BindView(R.id.tv_serviceing_long)
    TextView tv_serviceing_long;

    public void setDotVisible(boolean visible){
        if (dot_message !=null){
            dot_message.setVisibility(visible? View.VISIBLE: View.GONE);
        }

    }


    @Override
    public int setLayout() {
        return R.layout.fragment_mine;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mAdapter = new HomeGoodsAdapter();
        GridLayoutManager linearLayoutManager = new GridLayoutManager(mActivity, 2);
        GirdSpace girdSpace = new GirdSpace(DpUtil.dip2px(mActivity, 10), 2);
        mRecyclerView.addItemDecoration(girdSpace);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        getLoveData();
    }

    private void getInfo(){
        new RxHttp<BaseResult<MineInfo>>().send(ApiManager.getService().getMineInfo(),
                new Response<BaseResult<MineInfo>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<MineInfo> result) {
                        setInfoData(result.data);

                    }
                });
    }
    private void setInfoData(MineInfo mineInfo){
        if (mineInfo == null){
            llLoginedInfo.setVisibility(View.GONE);
            tvLogin.setVisibility(View.VISIBLE);
             tv_unpay.setVisibility(View.GONE);
             tv_unsend.setVisibility(View.GONE);
             tv_unreceive.setVisibility(View.GONE);
             tv_unpay_first.setVisibility(View.GONE);
             tv_unservice_first.setVisibility(View.GONE);
             tv_uncomment_first.setVisibility(View.GONE);
             tv_to_be_confirm_long.setVisibility(View.GONE);
             tv_unpay_long.setVisibility(View.GONE);
             tv_serviceing_long.setVisibility(View.GONE);
        }else {
            llLoginedInfo.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
            ImageUtil.loadAvatar(mActivity,vi_image,mineInfo.user_avatar);
            tv_name.setText(mineInfo.username);
            if (mineInfo.type == 0){
                iv_role_tabel.setVisibility(View.GONE);
                tv_roles.setText("普通用户");
                tv_to_be_cinderell.setVisibility(View.VISIBLE);
            }else {
                iv_role_tabel.setVisibility(View.VISIBLE);
                tv_roles.setText("灰姑娘");
                tv_to_be_cinderell.setVisibility(View.GONE);
            }
            if (mineInfo.store_create_num>0){
                tv_unpay.setVisibility(View.VISIBLE);
                tv_unpay.setText(mineInfo.store_create_num+"");
            }else {
                tv_unpay.setVisibility(View.GONE);
            }
            if (mineInfo.store_payed_num>0){
                tv_unsend.setVisibility(View.VISIBLE);
                tv_unsend.setText(mineInfo.store_payed_num+"");
            }else {
                tv_unsend.setVisibility(View.GONE);
            }
            if (mineInfo.store_send_num>0){
                tv_unreceive.setVisibility(View.VISIBLE);
                tv_unreceive.setText(mineInfo.store_send_num+"");
            }else {
                tv_unreceive.setVisibility(View.GONE);
            }
            if (mineInfo.serve_un_pay>0){
                tv_unpay_first.setVisibility(View.VISIBLE);
                tv_unpay_first.setText(mineInfo.serve_un_pay+"");
            }else {
                tv_unpay_first.setVisibility(View.GONE);
            }
            if (mineInfo.serve_un_serve>0){
                tv_unservice_first.setVisibility(View.VISIBLE);
                tv_unservice_first.setText(mineInfo.serve_un_serve+"");
            }else {
                tv_unservice_first.setVisibility(View.GONE);
            }
            if (mineInfo.serve_un_commit>0){
                tv_uncomment_first.setVisibility(View.VISIBLE);
                tv_uncomment_first.setText(mineInfo.serve_un_commit+"");
            }else {
                tv_uncomment_first.setVisibility(View.GONE);
            }
            if (mineInfo.serve_long_sure>0){
                tv_to_be_confirm_long.setVisibility(View.VISIBLE);
                tv_to_be_confirm_long.setText(mineInfo.serve_long_sure+"");
            }else {
                tv_to_be_confirm_long.setVisibility(View.GONE);
            }
            if (mineInfo.serve_long_pay>0){
                tv_unpay_long.setVisibility(View.VISIBLE);
                tv_unpay_long.setText(mineInfo.serve_long_pay+"");
            }else {
                tv_unpay_long.setVisibility(View.GONE);
            }
            if (mineInfo.serve_long_serve>0){
                tv_serviceing_long.setVisibility(View.VISIBLE);
                tv_serviceing_long.setText(mineInfo.serve_long_serve+"");
            }else {
                tv_serviceing_long.setVisibility(View.GONE);
            }

        }

    }


    @Override
    public void loadData() {
        if (GlobalParam.getUserLogin()){
            getInfo();
        }else {
            setInfoData(null);
        }
    }


    //得到猜你喜欢的数据
    private void getLoveData() {
        new RxHttp<BaseResult<ListResult<HomeGoods>>>().send(ApiManager.getService().getLicks(),
                new Response<BaseResult<ListResult<HomeGoods>>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<HomeGoods>> result) {
                        setData(result.data.list);
                    }
                });
    }


    @OnClick({R.id.vi_image, R.id.tv_login, R.id.tv_to_be_cinderell
            , R.id.tv_all_order, R.id.rl_unpay, R.id.rl_unsend , R.id.rl_unreceive, R.id.rl_finish, R.id.rl_return
            , R.id.tv_all_order_first, R.id.rl_unpay_first, R.id.rl_unservice_first, R.id.rl_uncomment_first
            , R.id.tv_all_order_long, R.id.rl_to_be_confirm_long,R.id.rl_unpay_long, R.id.rl_serviceing_long, R.id.rl_complete_long
            , R.id.rl_xiaohui_recomment, R.id.rl_mine_service2, R.id.rl_mine_service3, R.id.rl_mine_service4
            , R.id.rl_mine_service5, R.id.rl_mine_service6, R.id.rl_mine_service7, R.id.rl_mine_service8
            , R.id.rl_mine_service9, R.id.rl_mine_service10, R.id.rl_mine_service11
            , R.id.iv_message, R.id.iv_setting
            })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vi_image:
                if (GlobalParam.getUserLogin(mActivity))
                MineDataActivity.launch(mActivity);
                break;
            case R.id.tv_login:
                LoginActivity.launch(mActivity,true);

                break;
            case R.id.tv_to_be_cinderell:
                CenterDialogUtil.showBulletin(mActivity);
                break;
            case R.id.tv_all_order:
                MineOrderActivity.launch(mActivity, OrderFragment.ALL);
                break;
            case R.id.rl_unpay:
                MineOrderActivity.launch(mActivity, OrderFragment.UNPAY);
                break;
            case R.id.rl_unsend:
                MineOrderActivity.launch(mActivity, OrderFragment.UNSEND);
                break;
            case R.id.rl_unreceive:
                MineOrderActivity.launch(mActivity, OrderFragment.UNRECEIVE);
                break;
            case R.id.rl_finish:
                MineOrderActivity.launch(mActivity, OrderFragment.FINISH);
                break;
            case R.id.rl_return:
                ReturnListActivity.launch(mActivity);
                break;
            case R.id.tv_all_order_first:
                SingleServiceOrderListActivity.launch(mActivity,SingleServiceOrderListActivity.ALL);
                break;
            case R.id.rl_unpay_first:
                SingleServiceOrderListActivity.launch(mActivity,SingleServiceOrderListActivity.PAY);
                break;
            case R.id.rl_unservice_first:
                SingleServiceOrderListActivity.launch(mActivity,SingleServiceOrderListActivity.SERVICE);
                break;
            case R.id.rl_uncomment_first:
                SingleServiceOrderListActivity.launch(mActivity,SingleServiceOrderListActivity.COMMENT);
                break;
            case R.id.tv_all_order_long:
                LongServiceOrderListActivity.launch(mActivity,0);
                break;
            case R.id.rl_to_be_confirm_long:
                LongServiceOrderListActivity.launch(mActivity,1);
                break;
            case R.id.rl_unpay_long:
                LongServiceOrderListActivity.launch(mActivity,2);
                break;
            case R.id.rl_serviceing_long:
                LongServiceOrderListActivity.launch(mActivity,3);
                break;
            case R.id.rl_complete_long:
                LongServiceOrderListActivity.launch(mActivity,4);
                break;
            case R.id.rl_xiaohui_recomment:
                RecommentListActivity.launch(mActivity);
                break;
            case R.id.rl_mine_service2:
                SmallVaultActivity.launch(mActivity);
                break;
            case R.id.rl_mine_service3:
                CouponCenterActivity.launch(mActivity);
                break;
            case R.id.rl_mine_service4:
                MineBalanceActivity.launch(mActivity);
                break;
            case R.id.rl_mine_service5:
                MineGroupActivity.launch(mActivity);
                break;
            case R.id.rl_mine_service6:
                MineAddressActivity.launch(mActivity,MineAddressActivity.LOOK);
                break;
            case R.id.rl_mine_service7:
                ApplyProductSupplierActivity.launch(mActivity);
                break;
            case R.id.rl_mine_service8:
                //申请成为劳务用户
                CenterDialogUtil.showApplyService(mActivity,()->{

                });
                break;
            case R.id.rl_mine_service9:
                //商家入驻
                break;
            case R.id.rl_mine_service10:
                MineCollectActivity.launch(mActivity);
                break;
            case R.id.rl_mine_service11:
                DialogUtil.showCallPhoneDialog(mActivity);

                break;
            case R.id.iv_message:
                MessageActivity.launch(mActivity);
                break;
            case R.id.iv_setting:
                SettingActivity.launch(mActivity);

                break;
        }
    }


    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof UpdateMineInfo){
            loadData();
        }
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

    @BindView(R.id.app_bar)
    AppBarLayout appbar;
    @BindView(R.id.title)
    LinearLayout title;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.iv_setting)
    ImageView iv_setting;
    @BindView(R.id.iv_message)
    ImageView iv_message;
    @Override
    public void initListener() {
//        super.initListener();
        //SwipeRefreshLayout和CoordinatorLayout滑动冲突
        appbar.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout, i) -> {
            LogUtil.e("totalDy" + i);
            float percent = Math.abs(i) * 1.0f / 200 % 100;
            percent = percent > 1 ? 1 : percent;
            String bgColor = ColorUtil.caculateColor("#00000000", "#FFFFFFFF", percent);
            title.setBackgroundColor(Color.parseColor(bgColor));
            String textColor = ColorUtil.caculateColor("#00000000", "#FF111111", percent);
            tv_title_name.setTextColor(Color.parseColor(textColor));

            String backColor = ColorUtil.caculateColor("#FF000000", "#FF333333", percent);
            Drawable wrap = DrawableCompat.wrap(mActivity.getDrawable(R.mipmap.my_set_icon));
            DrawableCompat.setTintList(wrap, ColorStateList.valueOf(Color.parseColor(backColor)));

            Drawable wrap1 = DrawableCompat.wrap(mActivity.getDrawable(R.mipmap.my_msg_icon));
            DrawableCompat.setTintList(wrap1, ColorStateList.valueOf(Color.parseColor(backColor)));

            iv_setting.setImageDrawable(wrap);
            iv_message.setImageDrawable(wrap1);
            if (i == 0){
                iv_setting.setImageResource(R.mipmap.my_set_icon_white);
                iv_message.setImageResource(R.mipmap.my_msg_icon_white);
            }

        });
    }
}
