package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.DirectCommentAdapter;
import com.cinderellavip.adapter.recycleview.TechnicalProjectAdapter;
import com.cinderellavip.adapter.viewpager.BannerViewPagerAdapter;
import com.cinderellavip.util.ColorUtil;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.MyIndicator;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.rv_project)
    RecyclerView rv_project;
    @BindView(R.id.rv_package)
    RecyclerView rv_package;


    private BaseQuickAdapter commentAdapter;
    private BaseQuickAdapter projectAdapter;
    private BaseQuickAdapter packageAdapter;

    public static void launch(Context from, int type) {
        Intent intent = new Intent(from, DirectAppointmentTechnicianDetailActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        rv_comment.setLayoutManager(new LinearLayoutManager(mActivity));
        commentAdapter = new DirectCommentAdapter();
        rv_comment.setAdapter(commentAdapter);

        rv_project.setLayoutManager(new LinearLayoutManager(mActivity));
        projectAdapter = new TechnicalProjectAdapter();
        rv_project.setAdapter(projectAdapter);

        rv_package.setLayoutManager(new LinearLayoutManager(mActivity));
        packageAdapter = new TechnicalProjectAdapter();
        rv_package.setAdapter(packageAdapter);


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
        projectAdapter.setNewData(DataUtil.getData(3));
        packageAdapter.setNewData(DataUtil.getData(2));
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
                DirectAppointmentTechnicianCommentActivity.launch(mActivity
                ,DirectAppointmentTechnicianCommentActivity.comment_technical);
                break;
            case R.id.view_back:
                finish();
                break;
        }
    }
}
