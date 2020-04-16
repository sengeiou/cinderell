package com.cinderellavip.weight;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class MarginDecorationextendsHeader extends RecyclerView.ItemDecoration {
    private int space;
    private int number = 2;

    public MarginDecorationextendsHeader(int space) {
        this.space = space;
    }
    public MarginDecorationextendsHeader(int space, int number) {
        this.space = space;
        this.number = number;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
//        outRect.top = space;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (parent.getChildLayoutPosition(view)  == 0){
            return;
        }
        if (parent.getChildLayoutPosition(view) % 2 == 0) {
            outRect.left = space;
            outRect.bottom = space;
            outRect.right = space;
        }else {
            outRect.left = space;
            outRect.bottom = space;
        }

    }
}

