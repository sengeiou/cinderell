package com.cinderellavip.weight;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class GirdSpaceRight extends RecyclerView.ItemDecoration {
    private int space ;


    public GirdSpaceRight(int space) {
        this.space = space;
    }
    public GirdSpaceRight(int space, int number) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);
        outRect.right = space;



    }
}

