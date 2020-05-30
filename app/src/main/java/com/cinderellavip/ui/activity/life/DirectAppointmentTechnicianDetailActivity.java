package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.DirectCommentAdapter;
import com.cinderellavip.adapter.recycleview.TechnicalProjectAdapter;
import com.cinderellavip.adapter.viewpager.BannerViewPagerAdapter;
import com.cinderellavip.bean.direct.DirectPersonComment;
import com.cinderellavip.bean.direct.DirectPersonInfo;
import com.cinderellavip.bean.direct.DirectProjectItem;
import com.cinderellavip.bean.request.TechnicalComment;
import com.cinderellavip.global.CinderellApplication;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.ColorUtil;
import com.cinderellavip.weight.MyIndicator;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.tozzais.baselibrary.weight.ProgressLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 * 直约 技师详情
 */
public class DirectAppointmentTechnicianDetailActivity extends BaseActivity {


    @BindView(R.id.title)
    LinearLayout title;
    @BindView(R.id.tv_technical_title)
    TextView tv_technical_title;
    @BindView(R.id.tv_tv_technical_name)
    TextView tv_tv_technical_name;
    @BindView(R.id.ll_flag)
    LinearLayout ll_flag;
    @BindView(R.id.view_back)
    ImageView view_back;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_distance)
    TextView tv_distance;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.nestView)
    NestedScrollView nestView;
    @BindView(R.id.xbanner)
    ViewPager xbanner;
    @BindView(R.id.indicator)
    MyIndicator indicator;
    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;
    @BindView(R.id.rv_project)
    RecyclerView rv_project;
    @BindView(R.id.rv_package)
    RecyclerView rv_package;
    @BindView(R.id.tv_comment_number)
    TextView tvCommentNumber;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.tv_time_project)
    TextView tvTimeProject;
    @BindView(R.id.ll_project)
    LinearLayout llProject;
    @BindView(R.id.tv_time_package)
    TextView tvTimePackage;
    @BindView(R.id.ll_package)
    LinearLayout llPackage;
    @BindView(R.id.progress)
    ProgressLayout progress;


    private BaseQuickAdapter commentAdapter;
    private BaseQuickAdapter projectAdapter;
    private BaseQuickAdapter packageAdapter;

    //员工id
    private int waiter;

    public static void launch(Context from, int waiter) {
        Intent intent = new Intent(from, DirectAppointmentTechnicianDetailActivity.class);
        intent.putExtra("waiter", waiter);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        waiter = getIntent().getIntExtra("waiter", 0);
        rv_comment.setLayoutManager(new LinearLayoutManager(mActivity));
        commentAdapter = new DirectCommentAdapter();
        rv_comment.setAdapter(commentAdapter);

        rv_project.setLayoutManager(new LinearLayoutManager(mActivity));
        projectAdapter = new TechnicalProjectAdapter(waiter);
        rv_project.setAdapter(projectAdapter);

        rv_package.setLayoutManager(new LinearLayoutManager(mActivity));
        packageAdapter = new TechnicalProjectAdapter(waiter);
        rv_package.setAdapter(packageAdapter);


    }


    @Override
    public void loadData() {
        if (!isLoad)progress.showLoading();
        getData();
    }

    private void getData() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("waiter", "" + waiter);
        hashMap.put("city", "" + CinderellApplication.name);
        hashMap.put("longitude", "" + CinderellApplication.longitude);
        hashMap.put("latitude", "" + CinderellApplication.latitude);
        new RxHttp<BaseResult<DirectPersonInfo>>().send(ApiManager.getService().getPersonInfo(hashMap),
                new Response<BaseResult<DirectPersonInfo>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<DirectPersonInfo> result) {
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

    private void setData(DirectPersonInfo data) {
        List<String> bannerList = new ArrayList<>();
        bannerList.add(data.avatar);
        xbanner.setAdapter(new BannerViewPagerAdapter(bannerList, mActivity));
        indicator.setVisibility(View.GONE);

        tv_tv_technical_name.setText(data.name);

        List<String> qualification = data.qualification;
        if (qualification == null || qualification.size() == 0) {
            ll_flag.setVisibility(View.GONE);
        } else {
            ll_flag.setVisibility(View.VISIBLE);
            for (String s : qualification) {
                View view = View.inflate(mActivity, R.layout.item_direct_person_flag, null);
                TextView tv_title = view.findViewById(R.id.tv_title);
                tv_title.setText(s);
                ll_flag.addView(view);
            }
        }
        tv_content.setText(data.work);
        tv_address.setText(data.address);
        tv_distance.setText(data.distance + "km");
        tv_status.setText(data.getStatusText());

        DirectPersonComment comment = data.comment;
        if (comment == null || TextUtils.isEmpty(comment.address)){
            llComment.setVisibility(View.GONE);
        }else {
            llComment.setVisibility(View.VISIBLE);
            List<DirectPersonComment> comments = new ArrayList<>();
            comments.add(comment);
            commentAdapter.setNewData(comments);
        }

        List<DirectProjectItem> project = data.project;
        if (project == null || project.size() ==0){
            llProject.setVisibility(View.GONE);
        }else {
            tvTimeProject.setText(data.getRecentTime());
            llProject.setVisibility(View.VISIBLE);
            projectAdapter.setNewData(project);
        }
        List<DirectProjectItem> pack_age = data.pack_age;
        if (pack_age == null || pack_age.size() ==0){
            llPackage.setVisibility(View.GONE);
        }else {
            tvTimePackage.setText(data.getRecentTime());
            llPackage.setVisibility(View.VISIBLE);
            packageAdapter.setNewData(pack_age);
        }

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

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.fragment_direct_technical_detail;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(DirectAppointmentTechnicianDetailActivity.this, null);
        StatusBarUtil.setLightMode(this);
    }


    @OnClick({R.id.ll_comment, R.id.view_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_comment:
                TechnicalComment technicalComment = new TechnicalComment(waiter + "", "", TechnicalComment.comment_technical);
                DirectAppointmentTechnicianCommentActivity.launch(mActivity
                        , technicalComment);
                break;
            case R.id.view_back:
                finish();
                break;
        }
    }
}
