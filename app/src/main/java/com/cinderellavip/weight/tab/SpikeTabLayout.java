package com.cinderellavip.weight.tab;

import android.app.Activity;
import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.spike.SpikeTime;
import com.cinderellavip.util.ScreenUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class SpikeTabLayout extends TabLayout {


    public SpikeTabLayout(Context context) {
        super(context);
        init(context);
    }

    public SpikeTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SpikeTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.addOnTabSelectedListener(new OnTabSelectedListener() {

            @Override
            public void onTabSelected(Tab tab) {
                setTagSelete(tab, true);

            }

            @Override
            public void onTabUnselected(Tab tab) {
                setTagSelete(tab, false);

            }

            @Override
            public void onTabReselected(Tab tab) {
            }
        });
    }





    public void setTitle(List<SpikeTime> titles) {
        removeAllTabs();
        /**
         * 开始添加切换的Tab。
         */
        for (int i = 0; i < titles.size(); i++) {
            SpikeTime title = titles.get(i);
            Tab tab = newTab();
            tab.setCustomView(R.layout.tab_comment);
            if (tab.getCustomView() != null) {
                TextView tab_text = tab.getCustomView().findViewById(R.id.tab_text);
                TextView tab_text_number = tab.getCustomView().findViewById(R.id.tab_text_number);
                tab_text.setText(title.begin_time);
                tab_text_number.setText(title.remark);
                tab_text.setTextColor(getResources().getColor(R.color.spike_tab));
                tab_text_number.setTextColor(getResources().getColor(R.color.spike_tab));
            }
            //设置点击事件
            View tabView = (View) tab.getCustomView().getParent();
            tabView.setTag(i);
            tabView.setOnClickListener(mTabOnClickListener);
            this.addTab(tab);
        }
    }

    private void setTagSelete(Tab tab, boolean isSelete) {
        if (tab != null && tab.getCustomView() != null) {
            TextView tab_text = tab.getCustomView().findViewById(R.id.tab_text);
            TextView tab_text_number = tab.getCustomView().findViewById(R.id.tab_text_number);
            TextPaint paint = tab_text.getPaint();
            paint.setFakeBoldText(isSelete);
            tab_text.setTextColor(isSelete ? getResources().getColor(R.color.white) : getResources().getColor(R.color.spike_tab));
            tab_text_number.setTextColor(isSelete ? getResources().getColor(R.color.white) : getResources().getColor(R.color.spike_tab));
            tab_text.setTextSize(isSelete?14 :12);
            tab_text_number.setTextSize(isSelete?14 :12);
        }

    }


    private OnClickListener mTabOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            Tab tab = getTabAt(pos);
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

    private ViewPager viewPager;

    private int DEAULT_POSITION = 0;

    private int magin = 0;
    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager1) {
        this.viewPager = viewPager1;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
