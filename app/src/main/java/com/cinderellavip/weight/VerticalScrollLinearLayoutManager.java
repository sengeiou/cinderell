package com.cinderellavip.weight;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

public class VerticalScrollLinearLayoutManager extends LinearLayoutManager {

    public void setScrollEnable(boolean mIsScroll) {
        this.mIsScroll = mIsScroll;
    }

    private boolean mIsScroll = false;
    public VerticalScrollLinearLayoutManager(Context context) {
        super(context);
    }

    public VerticalScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public VerticalScrollLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean canScrollVertically() {
        return mIsScroll && super.canScrollVertically();
    }
}
