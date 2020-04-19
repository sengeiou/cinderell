package com.cinderellavip.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import org.jetbrains.annotations.Nullable;

public class RecycleViewRootLinearLayout extends LinearLayout {


    public RecycleViewRootLinearLayout(Context context) {
        super(context);
    }

    public RecycleViewRootLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecycleViewRootLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RecycleViewRootLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
