package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.bean.eventbus.UpdateHomeMainData;
import com.cinderellavip.bean.spike.SpikeTime;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.fragment.home.SpikeFragment;
import com.cinderellavip.util.Utils;
import com.cinderellavip.weight.tab.SpikeTabLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.tozzais.baselibrary.util.log.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class SpikeListActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    SpikeTabLayout tablayout;

    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    public static void launch(Context activity) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(activity, SpikeListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        activity.startActivity(intent);
    }




    @Override
    public int getLayoutId() {
        return R.layout.activity_spike;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("今日秒杀");
        mTitle.setTextColor(getResources().getColor(R.color.white));
        getToolbar().setBackgroundColor(getResources().getColor(R.color.baseColor));
        getToolbar().setNavigationIcon(R.mipmap.back_white);

        startTime();

    }

    @Override
    public void loadData() {
        if (!isLoad)showProress();
        new RxHttp<BaseResult<ListResult<SpikeTime>>>().send(ApiManager.getService().spikeTime(),
                new Response<BaseResult<ListResult<SpikeTime>>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<SpikeTime>> result) {
                        showContent();
                        List<SpikeTime> spikeTimes = result.data.list;
                        List<String> list = new ArrayList<>();
                        fragmentList.clear();
                        int position = 0;
                        for (int i = 0 ; i<spikeTimes.size();i++){
                            SpikeTime spikeTime = spikeTimes.get(i);
                            fragmentList.add(SpikeFragment.newInstance(spikeTime.begin_time));
                            list.add("");
                            if (spikeTime.select){
                                position = i;
                            }
                        }
                        tablayout.setTitle(spikeTimes);
                        adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList,list);
                        viewpager.setAdapter(adapter);
                        tablayout.setupWithViewPager(viewpager);
                        viewpager.setCurrentItem(getIntent().getIntExtra("type",0));
                        viewpager.setOffscreenPageLimit(4);
                        viewpager.setCurrentItem(position);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });



    }

    @Override
    public void initListener() {

    }

    @Override
    protected void setStatusBar() {
        int mColor = getResources().getColor(R.color.baseColor);
        StatusBarUtil.setColor(this, mColor, 0);
        StatusBarUtil.setDarkMode(this);
    }


    private void startTime(){
        Calendar calendar = Calendar.getInstance();
        //分钟
        int minute = calendar.get(Calendar.MINUTE);
        LogUtil.e(minute+"");
        if (!mHandler.hasMessages(3))
            mHandler.sendEmptyMessageDelayed(3, 1000*60*(60-minute+2));

    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 3){
                loadData();
                LogUtil.e("刷新了");
                mHandler.sendEmptyMessageDelayed(3, 1000*60*60);
            }
            return false;
        }
    });
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(3);
        }
        mHandler = null;
    }


}
