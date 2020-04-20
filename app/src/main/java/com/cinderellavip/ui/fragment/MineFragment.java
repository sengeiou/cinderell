package com.cinderellavip.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.ui.activity.mine.MineDataActivity;
import com.cinderellavip.ui.activity.mine.MineOrderActivity;
import com.cinderellavip.ui.activity.order.ApplyReturnActivity;
import com.cinderellavip.ui.activity.order.ReturnListActivity;
import com.cinderellavip.ui.fragment.mine.OrderFragment;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.CircleImageView;
import com.cinderellavip.weight.GirdSpace;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class MineFragment extends BaseListFragment<HomeGoods> {

    //个人资料是否加载成功 加载成功之后 则一直算成功
    public boolean mineInfoIsLoad = false;

    @BindView(R.id.vi_image)
    CircleImageView viImage;
    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.ll_logined_info)
    LinearLayout llLoginedInfo;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    @BindView(R.id.dot_message)
    View dot_message;
    @BindView(R.id.dot_message1)
    View dot_message1;

    public void setDotVisible(boolean visible){
        if (dot_message !=null){
            dot_message.setVisibility(visible? View.VISIBLE: View.GONE);
            dot_message1.setVisibility(visible? View.VISIBLE: View.GONE);
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


    }


    @Override
    public void loadData() {
        new Handler().postDelayed(() -> {
            setData(DataUtil.getHomeGoods(8,0));
        }, 100);
    }


    @OnClick({R.id.vi_image, R.id.tv_to_be_cinderell
            , R.id.tv_all_order
            , R.id.rl_unpay
            , R.id.rl_unsend
            , R.id.rl_unreceive
            , R.id.rl_finish
            , R.id.rl_return
            })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vi_image:
                MineDataActivity.launch(mActivity);

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
        }
    }








}
