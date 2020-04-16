package com.cinderellavip.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

public class MyRecycleView extends RecyclerView {


    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /*重点在这里重写onMeasure()*/
     @Override
     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                 int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                                MeasureSpec.AT_MOST);

                 super.onMeasure(widthMeasureSpec, expandSpec);
             }
}
