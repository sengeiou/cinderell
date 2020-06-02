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
import com.cinderellavip.bean.direct.DirectProjectInfo;
import com.cinderellavip.bean.net.life.ShortDate;
import com.cinderellavip.bean.net.life.ShortTime;
import com.cinderellavip.bean.request.LifePreOrder;
import com.cinderellavip.bean.request.TechnicalComment;
import com.cinderellavip.global.CinderellApplication;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.util.ColorUtil;
import com.cinderellavip.util.Utils;
import com.cinderellavip.weight.MyIndicator;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.tozzais.baselibrary.weight.ProgressLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_flag)
    LinearLayout llFlag;
    @BindView(R.id.ll_select_time)
    LinearLayout llSelectTime;
    @BindView(R.id.tv_comment_number)
    TextView tvCommentNumber;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;


    @BindView(R.id.progress)
    ProgressLayout progress;


    private BaseQuickAdapter commentAdapter;
    private BaseQuickAdapter serviceListAdapter;


    private TechnicalComment technicalComment;

    public static void launch(Context from, TechnicalComment technicalComment) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, ServiceProjectDetailActivity.class);
        intent.putExtra("technicalComment", technicalComment);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        technicalComment = getIntent().getParcelableExtra("technicalComment");
        if (technicalComment.type == TechnicalComment.comment_technical) {
            tv_technical_title.setText("服务项目详情");
        } else {
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
        if (!isLoad)progress.showLoading();
        getData();
    }

    private void getData() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("waiter", "" + technicalComment.waiter);
        hashMap.put("project", "" + technicalComment.project);
        hashMap.put("city", "" + CinderellApplication.name);
        new RxHttp<BaseResult<DirectProjectInfo>>().send(ApiManager.getService().getDirectProjectInfo(hashMap),
                new Response<BaseResult<DirectProjectInfo>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<DirectProjectInfo> result) {
                        isLoad = true;
                        progress.showContent();
                        setData(result.data);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        progress.showError(s,view -> loadData());
                    }
                });
    }

    DirectProjectInfo directProjectInfo;
    private void setData(DirectProjectInfo data) {
        directProjectInfo = data;
        List<String> bannerList = new ArrayList<>();
        bannerList.add(data.thumb_nail);
        xbanner.setAdapter(new BannerViewPagerAdapter(bannerList, mActivity));
        indicator.setVisibility(View.GONE);

        tvTitle.setText(data.title);
        tvPrice.setText(data.getPrice());
        tvTime.setText(data.duration+"分钟");
        tvSelectTime.setText(data.covenant);
        tvServiceContent1.setText("服务时长："+data.duration+"分钟");
        tvServiceContent2.setText("服务姿势："+data.attitude);
        tvServiceContent3.setText("原理功效："+data.efficacy);
        tvServiceContent4.setText("不宜人群："+data.unsuitable);
        tvServiceContent5.setText("服务流程："+data.process);
        tvServiceContent6.setText("服务用品："+data.supplies);

        setForegroundColor(tvServiceContent1);
        setForegroundColor(tvServiceContent2);
        setForegroundColor(tvServiceContent3);
        setForegroundColor(tvServiceContent4);
        setForegroundColor(tvServiceContent5);
        setForegroundColor(tvServiceContent6);

        serviceListAdapter.setNewData(data.Charges);
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
                TechnicalComment technicalComment1 = new TechnicalComment(
                        technicalComment.waiter + "", technicalComment.project+""
                        , TechnicalComment.comment_project);
                DirectAppointmentTechnicianCommentActivity.launch(mActivity,technicalComment1);
                break;
            case R.id.view_back:
                finish();
                break;
            case R.id.ll_select_time:
                SelectServiceTimeActivity.launchDirect(mActivity,technicalComment.waiter);
                break;
            case R.id.tv_service:
                DialogUtil.showCallPhoneDialog(mActivity, 3);
                break;
            case R.id.tv_buy:
                if (directProjectInfo == null){
                    return;
                }
                LifePreOrder lifePreOrder = new LifePreOrder();
                lifePreOrder.type = LifePreOrder.DIRECT;
                lifePreOrder.project = Integer.parseInt(technicalComment.project);
                lifePreOrder.city = CinderellApplication.name;
                lifePreOrder.servicetime =  directProjectInfo.covenant;
                lifePreOrder.waiter =  technicalComment.waiter;
                BuyServiceActivity.launch(mActivity,lifePreOrder);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.request_service_time && resultCode == RESULT_OK) {
            ShortTime time = data.getParcelableExtra("time");
            ShortDate shortDate = data.getParcelableExtra("shortDate");
            directProjectInfo.covenant = shortDate.nian+"-"+shortDate.yue+"-"+shortDate.ri+" "+time.start;
            tvSelectTimeTip.setVisibility(View.GONE);
            tvSelectTime.setText(shortDate.yue+"月"+shortDate.ri+"号 ("+shortDate.tip+") "+time.start);
        }

    }
}
