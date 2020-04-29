package com.cinderellavip.weight;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class HomeTabLayout extends TabLayout {


    public HomeTabLayout(Context context) {
        super(context);
        init(context);
    }

    public HomeTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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





    public void setTitle(List<String> titles) {
        removeAllTabs();
        /**
         * 开始添加切换的Tab。
         */
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
            Tab tab = newTab();
            tab.setCustomView(R.layout.tab_item_home_goods);
            if (tab.getCustomView() != null) {
                TextView tab_text = tab.getCustomView().findViewById(R.id.tab_text);
                tab_text.setText(title);

            }
            //设置点击事件
            View tabView = (View) tab.getCustomView().getParent();
            tabView.setTag(i);
            tabView.setOnClickListener(mTabOnClickListener);
            if (i == 0) {
                tab.select();
            }
            this.addTab(tab);
        }
        setDivider();
    }

    private void setDivider(){
        LinearLayout linearLayout = (LinearLayout) getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(), R.drawable.tablayout_divider_vertical));
        linearLayout.setDividerPadding(DpUtil.dip2px(getContext(), 16));
    }

    private void setTagSelete(Tab tab, boolean isSelete) {
        if (tab != null && tab.getCustomView() != null) {
            TextView tab_text = tab.getCustomView().findViewById(R.id.tab_text);
            ImageView iv_indicator = tab.getCustomView().findViewById(R.id.iv_indicator);
            TextPaint paint = tab_text.getPaint();
            paint.setFakeBoldText(isSelete);
            tab_text.setTextColor(isSelete ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.black_title_color));
            iv_indicator.setVisibility(isSelete ? VISIBLE : INVISIBLE);
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

    }

}
