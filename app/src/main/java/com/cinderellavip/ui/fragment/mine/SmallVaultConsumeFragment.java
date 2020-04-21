package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.toast.SecondDialogUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * 小金推荐
 */
public class SmallVaultConsumeFragment extends BaseFragment {


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @BindView(R.id.appbar)
    AppBarLayout appbar;

    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    @Override
    public int setLayout() {
        return R.layout.fragment_small_vault;
    }

    @Override
    public void loadData() {

        fragmentList.add(OrderFragment.newInstance(OrderFragment.ALL));
        fragmentList.add(OrderFragment.newInstance(OrderFragment.UNPAY));
        fragmentList.add(OrderFragment.newInstance(OrderFragment.UNSEND));
        fragmentList.add(OrderFragment.newInstance(OrderFragment.UNRECEIVE));
        fragmentList.add(OrderFragment.newInstance(OrderFragment.FINISH));
        List<String> list = new ArrayList<>();
        list.add("消费积分");
        list.add("推荐积分");
        list.add("月冠积分");
        list.add("利息积分");
        adapter = new GoodsDetailPagerAdapter(getChildFragmentManager(), fragmentList,list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        viewpager.setOffscreenPageLimit(4);

    }

    @Override
    public void initListener() {
        super.initListener();

    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

    }


    @OnClick(R.id.tv_invite)
    public void onClick() {
        SecondDialogUtil.showRecommendDialog(mActivity, (payString1, bitmap) -> {
            switch (payString1){
                case "1":
                    tsg("分享微信");
                    break;
                case "2":
                    tsg("分享朋友圈");
                    break;
                case "down":
                    tsg("保存成功");
                    break;
            }

        });
    }
}
