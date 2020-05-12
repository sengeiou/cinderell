package com.cinderellavip.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.listener.OnFilterListener;
import com.cinderellavip.listener.OnSureClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FilterView extends FrameLayout implements View.OnClickListener {

    public OnFilterListener getOnFilterListener() {
        return onFilterListener;
    }

    public void setOnFilterListener(OnFilterListener onFilterListener) {
        this.onFilterListener = onFilterListener;
    }

    public void setOnDialogClickListener(OnSureClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    private OnFilterListener onFilterListener;
    private OnSureClickListener onDialogClickListener;
    private Context mContext;
    private TextView tv_complex;
    private TextView tv_sale_volume;
    private TextView tv_price;
    private TextView tv_comment;
    private LinearLayout ll_sale_volume;
    private LinearLayout ll_price;
    private LinearLayout ll_comment;
    private ImageView iv_sale_volume;
    private ImageView iv_price;
    private ImageView iv_comment;


    public void setTv_comment(String text) {
        tv_comment.setText(text);
    }

    public FilterView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public FilterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FilterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public FilterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.view_filter, null);
        this.mContext = context;
        tv_complex = view.findViewById(R.id.tv_complex);
        tv_sale_volume = view.findViewById(R.id.tv_sale_volume);
        tv_price = view.findViewById(R.id.tv_price);
        tv_comment = view.findViewById(R.id.tv_comment);
        ll_sale_volume = view.findViewById(R.id.ll_sale_volume);
        ll_price = view.findViewById(R.id.ll_price);
        ll_comment = view.findViewById(R.id.ll_comment);
        iv_sale_volume = view.findViewById(R.id.iv_sale_volume);
        iv_price = view.findViewById(R.id.iv_price);
        iv_comment = view.findViewById(R.id.iv_comment);

        addView(view);
        initListener();
    }

    private void initListener() {
        tv_complex.setOnClickListener(this);
        ll_comment.setOnClickListener(this);
        ll_price.setOnClickListener(this);
        ll_sale_volume.setOnClickListener(this);
    }

    private boolean isComplex, isPrice, isSale, isCategray;

    @Override
    public void onClick(View v) {
        int id = v.getId();


        if (id == R.id.tv_complex) {
            clean();
            isPrice = false;
            isSale = false;
            isCategray = false;

            tv_complex.setTextColor(getResources().getColor(R.color.red));
            isComplex = !isComplex;
            sort = 0;
            sort_type = 0;
            if (onDialogClickListener != null) {
                onDialogClickListener.onSure();
            }
        } else if (id == R.id.ll_sale_volume) {
            clean();
            isComplex = false;
            isPrice = false;
            isCategray = false;


            sort = 1;

            tv_sale_volume.setTextColor(getResources().getColor(R.color.baseColor));
            if (!isSale) {
                sort_type = 1;
                iv_sale_volume.setImageResource(R.mipmap.icon_condition_down);
            } else {
                sort_type = 0;
                iv_sale_volume.setImageResource(R.mipmap.icon_condition_up);
            }
            isSale = !isSale;
            if (onDialogClickListener != null) {
                onDialogClickListener.onSure();
            }
        } else if (id == R.id.ll_price) {
            clean();
            isComplex = false;
            isSale = false;
            isCategray = false;

            sort = 2;
            tv_price.setTextColor(getResources().getColor(R.color.red));
            if (!isPrice) {
                sort_type = 1;
                iv_price.setImageResource(R.mipmap.icon_condition_down);
            } else {
                sort_type = 0;
                iv_price.setImageResource(R.mipmap.icon_condition_up);
            }
            isPrice = !isPrice;
            if (onDialogClickListener != null) {
                onDialogClickListener.onSure();
            }

        } else if (id == R.id.ll_comment) {
            clean();
            isComplex = false;
            isSale = false;
            isPrice = false;

            sort = 3;
            tv_comment.setTextColor(getResources().getColor(R.color.red));
            if (!isCategray) {
                sort_type = 1;
                iv_comment.setImageResource(R.mipmap.icon_condition_down);
            } else {
                sort_type = 0;
                iv_comment.setImageResource(R.mipmap.icon_condition_up);
            }
            isCategray = !isCategray;
            if (onDialogClickListener != null) {
                onDialogClickListener.onSure();
            }
        }
    }

    private void clean() {
        tv_complex.setTextColor(getResources().getColor(R.color.black_title_color));
        tv_sale_volume.setTextColor(getResources().getColor(R.color.black_title_color));
        tv_price.setTextColor(getResources().getColor(R.color.black_title_color));
        tv_comment.setTextColor(getResources().getColor(R.color.black_title_color));
        iv_price.setImageResource(R.mipmap.icon_condition_default);
        iv_sale_volume.setImageResource(R.mipmap.icon_condition_default);
        iv_comment.setImageResource(R.mipmap.icon_condition_default);
    }




    //排序 0综合，1销量，2价格，3评论
    private int sort = 0;
    //排序方式：0正序，1倒序
    private int sort_type = 0;

    public int getSort() {
        return sort;
    }

    public int getSort_type() {
        return sort_type;
    }
}
