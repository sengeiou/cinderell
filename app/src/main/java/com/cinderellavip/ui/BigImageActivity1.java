//package com.cinderellavip.ui;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import com.cinderellavip.R;
//import com.cinderellavip.adapter.viewpager.BigImageAdapter;
//import com.cinderellavip.util.DonwloadSaveImg;
//import com.tozzais.baselibrary.ui.BaseActivity;
//import com.tozzais.baselibrary.util.StatusBarUtil;
//
//import java.util.ArrayList;
//
//import androidx.viewpager.widget.ViewPager;
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * Created by Administrator on 2017/4/18.
// */
//
//public class BigImageActivity1 extends BaseActivity {
//
//    @BindView(R.id.viewpager)
//    ViewPager viewpager;
//    @BindView(R.id.tv_pic_number)
//    TextView tv_pic_number;
//
//
//    /**
//     * 本地资源文件
//     * @param form
//     * @param data
//     * @param position
//     */
//    public static void launch(Context form, ArrayList<Integer> data, int position) {
//        Intent intent = new Intent(form, BigImageActivity1.class);
//        intent.putExtra("data", data);
//        intent.putExtra("position", position);
//        form.startActivity(intent);
//    }
//
//
//
//    @Override
//    public int getBaseLayout() {
//        return R.layout.activity_big_image;
//    }
//
//    @Override
//    public void initView(Bundle savedInstanceState) {
//    }
//
//    private ArrayList<Integer> data;
//
//    @Override
//    public void loadData() {
//        Intent intent = getIntent();
//            data = intent.getIntegerArrayListExtra("data");
//            viewpager.setAdapter(new BigImageAdapter(data, mActivity,true));
//
//        pointIndex = intent.getIntExtra("position", 0);
//        tv_pic_number.setText(pointIndex + 1 + "/" + data.size());
//
//
//        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                int newPosition = position;
//                tv_pic_number.setText(position + 1 + "/" + data.size());
//                // 更新标志位
//                pointIndex = newPosition;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        viewpager.setCurrentItem(pointIndex);
//    }
//
//    private int pointIndex = 0;
//
//    @Override
//    protected int getToolbarLayout() {
//        return -1;
//    }
//
//    @Override
//    public int getLayoutId() {
//        return -1;
//    }
//
//
//    @Override
//    protected void setStatusBar() {
//        StatusBarUtil.setTransparentForImageViewInFragment(BigImageActivity1.this, null);
//        StatusBarUtil.setDarkMode(this);
//    }
//
//    @OnClick(R.id.tv_sava)
//    public void onClick() {
////        DonwloadSaveImg.donwloadImg(mActivity, data[pointIndex]);
//    }
//}
