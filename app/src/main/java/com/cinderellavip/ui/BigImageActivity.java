package com.cinderellavip.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.BigImageAdapter;
import com.cinderellavip.util.DonwloadSaveImg;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/18.
 */

public class BigImageActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_pic_number)
    TextView tv_pic_number;

    public static void launch(Context form, String[] data, int position) {
        Intent intent = new Intent(form, BigImageActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("position", position);
        form.startActivity(intent);
    }

    public static void launch(Context form, String data) {
        Intent intent = new Intent(form, BigImageActivity.class);
        String[] array = new String[]{data};
        intent.putExtra("data", array);
        intent.putExtra("position", 0);
        form.startActivity(intent);
    }


    @Override
    public int getBaseLayout() {
        return R.layout.activity_big_image;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
    }

    private String[] data;

    @Override
    public void loadData() {
        data = getIntent().getStringArrayExtra("data");
        pointIndex = getIntent().getIntExtra("position", 0);
        tv_pic_number.setText(pointIndex + 1 + "/" + data.length);

        viewpager.setAdapter(new BigImageAdapter(data, mActivity));
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int newPosition = position;
                tv_pic_number.setText(position + 1 + "/" + data.length);
                // 更新标志位
                pointIndex = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setCurrentItem(pointIndex);
    }

    private int pointIndex = 0;

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public int getLayoutId() {
        return -1;
    }


    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(BigImageActivity.this, null);
        StatusBarUtil.setDarkMode(this);
    }

    @OnClick(R.id.tv_sava)
    public void onClick() {
        DonwloadSaveImg.donwloadImg(mActivity, data[pointIndex]);
    }
}
