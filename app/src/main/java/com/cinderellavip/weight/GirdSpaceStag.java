package com.cinderellavip.weight;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class GirdSpaceStag extends RecyclerView.ItemDecoration {
    private int space ;
    private int gridNumber = 2;

    private int  topNumber=0;

    private boolean isNeedTopSpace = false;


    public GirdSpaceStag(int space) {
        this.space = space;
    }
    public GirdSpaceStag(int space, int number) {
        this.space = space;
        this.gridNumber = number;
    }
    public GirdSpaceStag(int space, int number, int topNumber) {
        this.space = space;
        this.gridNumber = number;
        this.topNumber = topNumber;
    }
    public GirdSpaceStag(int space, int number, int topNumber, boolean isNeedTopSpace) {
        this.space = space;
        this.gridNumber = number;
        this.topNumber = topNumber;
        this.isNeedTopSpace = isNeedTopSpace;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);
        outRect.bottom = space;
        if (position < topNumber){
            return;
        }if (position >=topNumber && position <(topNumber+gridNumber) && isNeedTopSpace){
            outRect.top = space;
        }
    }
}

