package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.DirectCommentAdapter;
import com.cinderellavip.adapter.recycleview.ServiceDetailContentAdapter;
import com.cinderellavip.adapter.viewpager.BannerViewPagerAdapter;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.util.ColorUtil;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.MyIndicator;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 * 服务项目
 */
public class ServiceProjectDetailActivity extends BaseActivity {
    public static final int project_service = 0;
    public static final int package_service = 1;
    public int type;


    @BindView(R.id.title)
    LinearLayout title;
    @BindView(R.id.tv_technical_title)
    TextView tv_technical_title;
    @BindView(R.id.view_back)
    ImageView view_back;
    @BindView(R.id.nestView)
    NestedScrollView nestView;
    @BindView(R.id.xbanner)
    ViewPager xbanner;
    @BindView(R.id.indicator)
    MyIndicator indicator;
    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;
    @BindView(R.id.rv_service_list)
    RecyclerView rv_service_list;
    @BindView(R.id.tv_service_content1)
    TextView tvServiceContent1;
    @BindView(R.id.tv_service_content2)
    TextView tvServiceContent2;
    @BindView(R.id.tv_service_content3)
    TextView tvServiceContent3;
    @BindView(R.id.tv_service_content4)
    TextView tvServiceContent4;
    @BindView(R.id.tv_service_content5)
    TextView tvServiceContent5;
    @BindView(R.id.tv_service_content6)
    TextView tvServiceContent6;
    @BindView(R.id.tv_select_time_tip)
    TextView tvSelectTimeTip;
    @BindView(R.id.tv_select_time)
    TextView tvSelectTime;


    private BaseQuickAdapter commentAdapter;
    private BaseQuickAdapter serviceListAdapter;

    public static void launch(Context from, int type) {
        Intent intent = new Intent(from, ServiceProjectDetailActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type",project_service);
        if (type == project_service){
            tv_technical_title.setText("服务项目详情");
        }else {
            tv_technical_title.setText("服务套餐详情");
        }

        rv_comment.setLayoutManager(new LinearLayoutManager(mActivity));
        commentAdapter = new DirectCommentAdapter();
        rv_comment.setAdapter(commentAdapter);

        rv_service_list.setLayoutManager(new LinearLayoutManager(mActivity));
        serviceListAdapter = new ServiceDetailContentAdapter();
        rv_service_list.setAdapter(serviceListAdapter);

    }


    @Override
    public void loadData() {
        List<Integer> bannerList = new ArrayList<>();
        bannerList.add(R.mipmap.demo_technical);
        bannerList.add(R.mipmap.demo_technical);
        bannerList.add(R.mipmap.demo_technical);
        xbanner.setAdapter(new BannerViewPagerAdapter(bannerList, mActivity));
        indicator.bindViewPager(xbanner, bannerList.size());

        commentAdapter.setNewData(DataUtil.getData(1));
        serviceListAdapter.setNewData(DataUtil.getData(3));
    }

    @Override
    public void initListener() {
        super.initListener();
        nestView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            float percent = Math.abs(scrollY) * 1.0f / 200 % 100;
            percent = percent > 1 ? 1 : percent;
            String bgColor = ColorUtil.caculateColor("#00000000", "#FFFFFFFF", percent);
            title.setBackgroundColor(Color.parseColor(bgColor));
            String textColor = ColorUtil.caculateColor("#00000000", "#FF333333", percent);
            tv_technical_title.setTextColor(Color.parseColor(textColor));

            String backColor = ColorUtil.caculateColor("#FF333333", "#FF000000", percent);
            Drawable wrap = DrawableCompat.wrap(getDrawable(R.mipmap.back));
            DrawableCompat.setTintList(wrap, ColorStateList.valueOf(Color.parseColor(backColor)));
            view_back.setImageDrawable(wrap);
            if (scrollY == 0)
                view_back.setImageResource(R.mipmap.back_technical);
        });

        setForegroundColor(tvServiceContent1);
        setForegroundColor(tvServiceContent2);
        setForegroundColor(tvServiceContent3);
        setForegroundColor(tvServiceContent4);
        setForegroundColor(tvServiceContent5);
        setForegroundColor(tvServiceContent6);


    }

    private void setForegroundColor(TextView textView) {
        SpannableString spannableString = new SpannableString(
                textView.getText().toString().trim());
        spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.yellow_deep)), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_service_project_detail;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(ServiceProjectDetailActivity.this, null);
        StatusBarUtil.setLightMode(this);
    }


    @OnClick({R.id.ll_comment, R.id.view_back, R.id.ll_select_time, R.id.tv_service, R.id.tv_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_comment:
                DirectAppointmentTechnicianCommentActivity.launch(mActivity
                        , DirectAppointmentTechnicianCommentActivity.comment_project);
                break;
            case R.id.view_back:
                finish();
                break;
            case R.id.ll_select_time:
                SelectServiceTimeActivity.launch(mActivity);
                break;
            case R.id.tv_service:
                DialogUtil.showCallPhoneDialog(mActivity);
                break;
            case R.id.tv_buy:
                BuyServiceActivity.launch(mActivity);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.request_service_time && resultCode == RESULT_OK) {
            tvSelectTimeTip.setVisibility(View.GONE);
            tvSelectTime.setText("1月6号 今天 18:00");
        }

    }
}
