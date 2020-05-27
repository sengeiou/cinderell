package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.bean.eventbus.UpdateMineScore;
import com.cinderellavip.bean.net.mine.IntegralNumber;
import com.cinderellavip.bean.net.mine.IntegralResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.mine.LeaderBoardActivity;
import com.cinderellavip.ui.activity.mine.WithDrawActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * 小金推荐
 */
public class SmallVaultFragment extends BaseFragment {


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.mine_code)
    TextView mineCode;
    @BindView(R.id.tv_score_consume)
    TextView tvScoreConsume;
    @BindView(R.id.tv_score_recommend)
    TextView tvScoreRecommend;
    @BindView(R.id.tv_score_moon_crown)
    TextView tvScoreMoonCrown;
    @BindView(R.id.tv_score_interest)
    TextView tvScoreInterest;
    @BindView(R.id.tv_invite)
    TextView tvInvite;

    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    @Override
    public int setLayout() {
        return R.layout.fragment_small_vault;
    }

    @Override
    public void loadData() {

        fragmentList.add(SmallVaultConsumeIntegralFragment.newInstance(1));
        fragmentList.add(SmallVaultConsumeIntegralFragment.newInstance(2));
        fragmentList.add(SmallVaultConsumeIntegralFragment.newInstance(3));
        List<String> list = new ArrayList<>();
        list.add("消费积分");
        list.add("推荐积分");
        list.add("积分排行");
        adapter = new GoodsDetailPagerAdapter(getChildFragmentManager(), fragmentList, list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        viewpager.setOffscreenPageLimit(4);

        getNumber();

    }

    @Override
    public void initListener() {
        super.initListener();

    }

    private void getNumber() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("page", 1 + "");
        hashMap.put("limit", 1 + "");
        new RxHttp<BaseResult<IntegralResult>>().send(ApiManager.getService().mine_integral(hashMap),
                new Response<BaseResult<IntegralResult>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<IntegralResult> result) {
                        IntegralNumber data = result.data.integrals;
                        tvScoreConsume.setText(data.consumption);
                        tvScoreRecommend.setText(data.invite);
                        tvScoreMoonCrown.setText(data.month_best);
                        tvScoreInterest.setText(data.total);
                        mineCode.setText(data.with_int);
                    }
                });
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

    }


    @OnClick(R.id.tv_invite)
    public void onClick() {
        WithDrawActivity.launch(mActivity,WithDrawActivity.SCORE);
//        LeaderBoardActivity.launch(mActivity);
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof UpdateMineScore){
            getNumber();
        }
    }
}
