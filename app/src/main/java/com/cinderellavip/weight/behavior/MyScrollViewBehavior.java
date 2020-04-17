package com.cinderellavip.weight.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by jumpbox on 2017/6/29.
 */

public class MyScrollViewBehavior extends CoordinatorLayout.Behavior<ViewPager> {
    public MyScrollViewBehavior() {
    }

    public MyScrollViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ViewPager child, View dependency) {
        if (dependency instanceof RelativeLayout) {
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    private int maxHeight = 0;
    private View titleView;

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, ViewPager child, int layoutDirection) {
        int top = parent.getPaddingTop();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) childView.getLayoutParams();

            if (childView instanceof TableLayout) {
                titleView = childView;
            }
            if (childView instanceof ViewPager) {
                maxHeight = top;
            }
            int left = parent.getPaddingLeft() + params.leftMargin;
            int right = parent.getPaddingRight() + params.rightMargin + childView.getMeasuredWidth();
            int bottom = top + childView.getMeasuredHeight();
            childView.layout(
                    left,
                    top + params.topMargin,
                    right,
                    bottom);
            top = bottom + params.bottomMargin;

        }
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ViewPager child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, ViewPager child, View target, int dx, int dy, int[] consumed) {
        int preY = child.getTop() - dy;
        if (preY > titleView.getMeasuredHeight() && preY < maxHeight) {
            child.offsetTopAndBottom(-dy);
            if (titleView != null) {
                titleView.offsetTopAndBottom(-dy);
            }
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, ViewPager child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
