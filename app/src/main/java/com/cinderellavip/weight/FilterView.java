package com.cinderellavip.weight;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cinderellavip.R;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.listener.OnFilterListener;
import com.cinderellavip.util.dialog.RightDialogUtil;
import com.tozzais.baselibrary.util.sign.SignUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FilterView extends FrameLayout implements View.OnClickListener {

    public OnFilterListener getOnFilterListener() {
        return onFilterListener;
    }

    public void setOnFilterListener(OnFilterListener onFilterListener) {
        this.onFilterListener = onFilterListener;
    }

    private OnFilterListener onFilterListener;
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
            if (!isComplex && onFilterListener != null) {
                onFilterListener.onComplex();
            }
            tv_complex.setTextColor(!isComplex ? getResources().getColor(R.color.red) : getResources().getColor(R.color.black_title_color));
            isComplex = !isComplex;
        } else if (id == R.id.ll_sale_volume) {
            clean();
            isComplex = false;
            isPrice = false;
            isCategray = false;
            if (onFilterListener != null) {
                onFilterListener.onSaleVolume(isSale);
            }
            tv_sale_volume.setTextColor(getResources().getColor(R.color.baseColor));
            if (!isSale) {
                iv_sale_volume.setImageResource(R.mipmap.icon_condition_down);
            } else {
                iv_sale_volume.setImageResource(R.mipmap.icon_condition_up);
            }
            isSale = !isSale;
        } else if (id == R.id.ll_price) {
            clean();
            isComplex = false;
            isSale = false;
            isCategray = false;
            if (onFilterListener != null) {
                onFilterListener.onPrice(isPrice);
            }
            tv_price.setTextColor(getResources().getColor(R.color.red));
            if (!isPrice) {
                iv_price.setImageResource(R.mipmap.icon_condition_down);
            } else {
                iv_price.setImageResource(R.mipmap.icon_condition_up);
            }
            isPrice = !isPrice;

        } else if (id == R.id.ll_comment) {
            clean();
            isComplex = false;
            isSale = false;
            isPrice = false;
            if (onFilterListener != null) {
                onFilterListener.onCategray(isCategray);
            }
            tv_comment.setTextColor(getResources().getColor(R.color.red));
            if (!isCategray) {
                iv_comment.setImageResource(R.mipmap.icon_condition_down);
            } else {
                iv_comment.setImageResource(R.mipmap.icon_condition_up);
            }
            isCategray = !isCategray;
        }
    }

    private void clean() {
        tv_complex.setTextColor(getResources().getColor(R.color.black_title_color));
        tv_sale_volume.setTextColor(getResources().getColor(R.color.black_title_color));
        tv_price.setTextColor(getResources().getColor(R.color.black_title_color));
        tv_comment.setTextColor(getResources().getColor(R.color.black_title_color));
//        tv_categray.setTextColor(getResources().getColor(R.color.black_title_color));
        iv_price.setImageResource(R.mipmap.icon_condition_default);
        iv_sale_volume.setImageResource(R.mipmap.icon_condition_default);
        iv_comment.setImageResource(R.mipmap.icon_condition_default);
    }




    private String type_id = "0";
    private String brand_id = "0";

    public void setFilterCondition(String type_id, String brand_id) {
        this.type_id = type_id;
        this.brand_id = brand_id;

    }





}
