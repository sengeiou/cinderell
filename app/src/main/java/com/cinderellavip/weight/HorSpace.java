package com.cinderellavip.weight;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class HorSpace extends RecyclerView.ItemDecoration {
    private int space ;

    public HorSpace(int space) {
        this.space = space;
    }
    public HorSpace() {
        this.space = 8;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        int pos = parent.getChildLayoutPosition(view);
        int count = state.getItemCount();
//        LogUtil.e(count+""+pos);
        if (pos  == count-1){
            outRect.right = space;
        }
        outRect.left = space;

    }
}

