package com.cinderellavip.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.cinderellavip.R;
import com.tozzais.baselibrary.util.DpUtil;


public class ScaleLinearLayout extends LinearLayout {

    /**
     * 高 宽 比
     */
    private float scale;



    public ScaleLinearLayout(Context context) {
        super(context);
    }

    public ScaleLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageView);
        scale = ta.getFloat(R.styleable.ScaleImageView_scale,0);
        ta.recycle();


    }

    public ScaleLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
//        LogUtil.e(width+"=="+ (int) (width*scale));
        setMeasuredDimension(width, (int) (width*scale));
    }


}
