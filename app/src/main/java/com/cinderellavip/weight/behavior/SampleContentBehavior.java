package com.cinderellavip.weight.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class SampleContentBehavior extends CoordinatorLayout.Behavior<View> {
    // 列表顶部和title底部重合时，列表的滑动距离。
    private int  deltaY;

    public SampleContentBehavior() {
    }

    public SampleContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



//    @Override
//    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
//        if (deltaY == 0) {
//            deltaY = dependency.getY() - child.getHeight();
//        }
//
//        float dy = dependency.getY() - child.getHeight();
//        dy = dy < 0 ? 0 : dy;
//        float y = -(dy / deltaY) * child.getHeight();
//        child.setTranslationY(y);
//
//        return true;
//    }

    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                deltaY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) ev.getRawY();
                child.setTranslationY(child.getTranslationY() + y - deltaY);
                deltaY = y;
                break;

        }
        return true;
    }
}
