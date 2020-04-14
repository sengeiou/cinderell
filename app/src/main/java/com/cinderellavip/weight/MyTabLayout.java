package com.cinderellavip.weight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.util.ScreenUtil;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyTabLayout extends TabLayout {
    private ViewPager viewPager;



    private int DEAULT_POSITION = 0;

    private int magin = 0;

    public MyTabLayout(Context context) {
        super(context);
        init(context);
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
//        magin = DpUtil.dip2px(context, 5);
        this.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(Tab tab) {
//                Log.e("onTabSelected","1");
                setTagSelete(tab, true);

            }

            @Override
            public void onTabUnselected(Tab tab) {
//                Log.e("onTabSelected","12");
                setTagSelete(tab, false);

            }

            @Override
            public void onTabReselected(Tab tab) {
//                Log.e("onTabSelected","123");
            }
        });
    }





    public void setTitle(List<String> titles) {
        removeAllTabs();
        /**
         * 开始添加切换的Tab。
         */
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
            Tab tab = newTab();
            tab.setCustomView(R.layout.tab_item_home_category);
            if (tab.getCustomView() != null) {
                TextView tab_text = tab.getCustomView().findViewById(R.id.tab_text);
                tab_text.setText(title);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab_text.getLayoutParams();
                int tabMode = getTabMode();
                if (tabMode == MODE_SCROLLABLE) {
                    if (i == 0) {
                        layoutParams.setMargins(magin, 0, magin, 0);
                    } else {
                        layoutParams.setMargins(0, 0, magin, 0);
                    }
                }
            }
            //设置点击事件
            View tabView = (View) tab.getCustomView().getParent();
            tabView.setTag(i);
            tabView.setOnClickListener(mTabOnClickListener);
            if (i == DEAULT_POSITION) {
                tab.select();
            }
            this.addTab(tab);
        }
    }





    private void setTagSelete(Tab tab, boolean isSelete) {
        if (tab != null && tab.getCustomView() != null) {
            TextView tab_text = tab.getCustomView().findViewById(R.id.tab_text);
            TextPaint paint = tab_text.getPaint();
            paint.setFakeBoldText(isSelete);
            tab_text.setTextSize(isSelete?18 :14);
        }

    }


    private OnClickListener mTabOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            TabLayout.Tab tab = getTabAt(pos);
            if (onTabPositionClickLister != null && !tab.isSelected()) {
                onTabPositionClickLister.onTabClick(pos);
            }
            if (tab != null) {
                tab.select();
                if (viewPager != null) {
                    viewPager.setCurrentItem(pos);
                }

            }


        }
    };
    public OnTabPositionClickLister onTabPositionClickLister;


    public interface OnTabPositionClickLister {
        void onTabClick(int position);
    }


    public void setOnTabPositionClickLister(OnTabPositionClickLister onTabPositionClickLister) {
        this.onTabPositionClickLister = onTabPositionClickLister;
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    public void setCurrent(int index) {
        int screenWidth = ScreenUtil.getScreenWidth((Activity) getContext()) / 2;
        if (getTabAt(index) != null) getTabAt(index).select();
        //加上半个item的宽度（这个需要自己微调，不一定是半个）如果有设置margin还需要加上margin的距离
        int width = 0;
        for (int i = 0; i <= index; i++) {
            Tab tabAt = getTabAt(i);

            View customView = tabAt.getCustomView();
            TextView tab_title = customView.findViewById(R.id.tab_text);
            tab_title.setGravity(Gravity.CENTER);
            int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            tab_title.measure(spec, spec);
            int measuredWidthTicketNum = tab_title.getMeasuredWidth();

            if (i == index) {
                width += measuredWidthTicketNum / 2;
            } else {
                if (i == 0) {
                    width += measuredWidthTicketNum + magin * 2;
                } else {
                    width += measuredWidthTicketNum + magin;
                }
            }
        }
//        Log.e("TAG",width+"::"+screenWidth);
        if (width < screenWidth) {
            width = 0;
        } else {
            width = width - screenWidth;
        }
        final int halfWidth = width; //偏移量
        post(new Runnable() {
            @Override
            public void run() {
                smoothScrollTo(halfWidth, 0);
            }
        });
    }
}
