package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.life.ShortDate;
import com.cinderellavip.bean.net.life.ShortTime;
import com.cinderellavip.bean.net.life.ShortTimeResult;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.fragment.life.SelectServiceTimeFragment;
import com.cinderellavip.util.TabLayoutAddOnClickHelper;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;

import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SelectServiceTimeActivity extends BaseActivity {



    @BindView(R.id.tablayout)
    TabLayout tablayout;
    private List<ShortDate> shortDates;

//    private GoodsDetailPagerAdapter adapter;
//    private List<BaseFragment> fragmentList = new ArrayList<>();

    public static void launch(Activity from) {
        Intent intent = new Intent(from, SelectServiceTimeActivity.class);
        from.startActivityForResult(intent, RequestCode.request_service_time);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("选择服务时间");


        fragment = new SelectServiceTimeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.viewpager, fragment).commit();


    }

    SelectServiceTimeFragment fragment;

    private String day = "";
    @Override
    public void loadData() {
        if (!isLoad)showProress();
        TreeMap<String,String> map = new TreeMap<>();
        if (!TextUtils.isEmpty(day)){
            map.put("day",day);
        }
        new RxHttp<BaseResult<ShortTimeResult>>().send(ApiManager.getService().shortOrderTime(map),
                new Response<BaseResult<ShortTimeResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ShortTimeResult> result) {
                        ShortTimeResult shortTimeResult = result.data;
                        if (!isLoad){
                            showContent();
                            shortDates = shortTimeResult.date;
                            shortDate = shortDates.get(0);
                            for (int i = 0; i< shortDates.size(); i++ ){
                                ShortDate shortDate = shortDates.get(i);
                                TabLayout.Tab tab = tablayout.newTab();
                                if (i == 0){
                                    tab.setText("今天\n"+shortDate.yue+"月"+shortDate.ri+"号");
                                }else if (i == 1){
                                    tab.setText("明天\n"+shortDate.yue+"月"+shortDate.ri+"号");
                                }else {
                                    tab.setText(shortDate.getWeek()+"\n"+shortDate.yue+"月"+shortDate.ri+"号");
                                }
                                tablayout.addTab(tab);
                            }
                            isLoad = true;
                        }
                        fragment.setDate(shortTimeResult.time);

                    }
                });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_service_time;
    }


    @OnClick(R.id.tv_sure)
    public void onClick() {
        ShortTime time = fragment.getShortTime();
        if (time == null){
            tsg("请选择服务时间");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("time",time);
        intent.putExtra("shortDate",shortDate);
        setResult(RESULT_OK,intent);
        finish();
        }


    ShortDate shortDate;
    @Override
    public void initListener() {
        super.initListener();
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tablayout.getSelectedTabPosition();
                shortDate = shortDates.get(position);
                day = shortDate.nian+"-"+shortDate.yue+"-"+shortDate.ri;
                loadData();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
}
