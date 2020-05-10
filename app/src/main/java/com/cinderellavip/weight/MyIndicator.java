package com.cinderellavip.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.cinderellavip.R;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyIndicator extends LinearLayout {

    public MyIndicator(Context context) {
        super(context);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
    }

    public MyIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyIndicator(Context context, @Nullable  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private ViewPager viewPager;
    private int size;
    public void bindViewPager(ViewPager viewPager, int size){
        this.viewPager = viewPager;
        this.size = size;
        initPoints(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                initPoints(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initPoints(int position) {
        removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView view = new ImageView(getContext());
            view.setBackgroundResource((i == position) ? R.drawable.shape_oval_red : R.drawable.shape_oval_gray);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            addView(view);
            int width = 0, height = 0;
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (i != position) {
                width = 20;
                height = 20;
            } else {
                width = 20;
                height = 20;
            }
            if (i != 0) {
                layoutParams.leftMargin = 20;
            }
            layoutParams.width = width;
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }
    }

}
