package com.cinderellavip.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * desc:
 * Created by huangxy on 2018/8/15.
 */
public class WrapLayout extends LinearLayout {


    public WrapLayout(Context context, AttributeSet attrs,
                      int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WrapLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);

        View child1 = getChildAt(0);
        View child2 = getChildAt(1);
        // 测量每个子View的宽高
        measureChild(child1, widthMeasureSpec, heightMeasureSpec);
        measureChild(child2, widthMeasureSpec, heightMeasureSpec);

        LayoutParams lp1 = (LayoutParams) child1.getLayoutParams();
        LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();

        int width1 = 0;
        int width2 = 0;
        width1 = child1.getMeasuredWidth() + lp1.leftMargin + lp1.rightMargin;
        width2 = child2.getMeasuredWidth() + lp2.leftMargin + lp2.rightMargin;

        if (width1+width2>specWidthSize){
            lp1.width = specWidthSize - width2;
            child1.setLayoutParams(lp1);
        }

//        Log.e("TAG",specWidthSize+"=="+width1+"=="+width2);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

    }





}
