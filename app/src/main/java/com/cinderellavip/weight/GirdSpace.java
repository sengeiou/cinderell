package com.cinderellavip.weight;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class GirdSpace extends RecyclerView.ItemDecoration {
    private int space ;
    private int gridNumber = 2;

    private int  topNumber=0;

    private boolean isNeedTopSpace = false;

    public GirdSpace(int space) {
        this.space = space;
    }
    public GirdSpace(int space, int number) {
        this.space = space;
        this.gridNumber = number;
    }
    public GirdSpace(int space, int number, int topNumber) {
        this.space = space;
        this.gridNumber = number;
        this.topNumber = topNumber;
    }
    public GirdSpace(int space, int number, int topNumber, boolean isNeedTopSpace) {
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
        if (position % gridNumber == topNumber%gridNumber) {
            //第一项
            outRect.left = space;
            outRect.right = space/2;

        }else if (position % gridNumber == ((topNumber+(gridNumber - 1))%gridNumber)){
            //最后一个
            outRect.left = space/2;
            outRect.right = space;
        }else {
            outRect.left = space/2;
            outRect.right = space/2;
        }


    }
}

