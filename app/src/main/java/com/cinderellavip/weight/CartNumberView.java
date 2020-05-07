package com.cinderellavip.weight;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cinderellavip.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CartNumberView extends FrameLayout implements View.OnClickListener {




    private Context mContext;

    private EditText tv_number;
    private RelativeLayout rl_reduce;
    private RelativeLayout rl_add;
    private ImageView iv_reduce;

    public EditText getTv_number() {
        return tv_number;
    }

    public CartNumberView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public CartNumberView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CartNumberView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public CartNumberView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.view_cart_number, null);
        this.mContext = context;
        tv_number = view.findViewById(R.id.tv_number);
        rl_reduce = view.findViewById(R.id.rl_reduce);
        rl_add = view.findViewById(R.id.rl_add);
        iv_reduce = view.findViewById(R.id.iv_reduce);
        tv_number.setText(number+"");
        addView(view);
        initListener();
    }

    private void initListener() {
        rl_reduce.setOnClickListener(this);
        rl_add.setOnClickListener(this);
        tv_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if (content.length()>4){
                    content = "9999";
                    tv_number.setText("9999");
                    tv_number.setSelection(tv_number.getText().toString().length());
                }
                if (TextUtils.isEmpty(content)){
                    tv_number.setText("1");
                    tv_number.setSelection(tv_number.getText().toString().length());
                    return;
                }
                try {
                    number = Integer.parseInt(content);
                    if (number>1){
                        rl_reduce.setEnabled(true);
                        iv_reduce.setImageResource(R.mipmap.cart_reduce);
                    }else {
                        rl_reduce.setEnabled(false);
                        iv_reduce.setImageResource(R.mipmap.caer_reduce_unclick);
                    }

                }catch (Exception e){

                }


            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        String content = tv_number.getText().toString().trim();
        if (TextUtils.isEmpty(content)){
            number = 1;
        }else {
            number = Integer.parseInt(content);
        }

        if (id == R.id.rl_reduce) {
            number--;
        } else if (id == R.id.rl_add) {
            number++;
        }
        setNumber(number);
    }

    private int number = 1;
    public void setNumber(int number) {
        this.number = number;
        tv_number.setText(number+"");
        tv_number.setSelection(tv_number.getText().toString().length());
    }






}
