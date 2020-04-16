package com.cinderellavip.weight;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class TopSpace extends RecyclerView.ItemDecoration {
    private int space ;



    public TopSpace(int space) {
        this.space = space;
    }




    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);
        outRect.top = space;

    }
}

