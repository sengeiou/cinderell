package com.cinderellavip.weight.tab;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.local.CommnetTabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CommentTabLayout extends TabLayout {


    public CommentTabLayout(Context context) {
        super(context);
        init(context);
    }

    public CommentTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CommentTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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





    public void setTitle(List<CommnetTabItem> titles) {
        removeAllTabs();
        /**
         * 开始添加切换的Tab。
         */
        for (int i = 0; i < titles.size(); i++) {
            CommnetTabItem title = titles.get(i);
            Tab tab = newTab();
            tab.setCustomView(R.layout.tab_comment);
            if (tab.getCustomView() != null) {
                TextView tab_text = tab.getCustomView().findViewById(R.id.tab_text);
                TextView tab_text_number = tab.getCustomView().findViewById(R.id.tab_text_number);
                tab_text.setText(title.name);
                tab_text_number.setText(title.number);
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
    }

    private void setTagSelete(Tab tab, boolean isSelete) {
        if (tab != null && tab.getCustomView() != null) {
            TextView tab_text = tab.getCustomView().findViewById(R.id.tab_text);
            TextView tab_text_number = tab.getCustomView().findViewById(R.id.tab_text_number);
            TextPaint paint = tab_text.getPaint();
            paint.setFakeBoldText(isSelete);
            tab_text.setTextColor(isSelete ? getResources().getColor(R.color.black_title_color) : getResources().getColor(R.color.grayText));
            tab_text_number.setTextColor(isSelete ? getResources().getColor(R.color.black_title_color) : getResources().getColor(R.color.grayText));
//            tab_text.setTextSize(isSelete?14 :12);
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
