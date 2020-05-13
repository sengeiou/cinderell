package com.cinderellavip.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cinderellavip.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CartNumberView1 extends FrameLayout {




    private Context mContext;

    private EditText tv_number;
    private RelativeLayout rl_reduce;
    private RelativeLayout rl_add;
    private ImageView iv_reduce;

    public EditText getTv_number() {
        return tv_number;
    }

    public CartNumberView1(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public CartNumberView1(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CartNumberView1(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public CartNumberView1(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
            rl_reduce.setOnClickListener(v -> {
                onNumberClickListener.reduce();
            });
            rl_add.setOnClickListener(v -> {
                onNumberClickListener.add();
            });

//        tv_number.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

//        tv_number.setOnFocusChangeListener((v, hasFocus) -> {
//            String content = tv_number.getText().toString().trim();
//            LogUtil.e("setOnFocusChangeListener= "+content+"=="+hasFocus);
//            if (hasFocus)
//            tv_number.setSelection(tv_number.getText().toString().length());
//
//        });
        tv_number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return true;
            }
        });
    }


//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        String content = tv_number.getText().toString().trim();
//        if (TextUtils.isEmpty(content)){
//            number = 1;
//        }else {
//            number = Integer.parseInt(content);
//        }
//
//        if (id == R.id.rl_reduce) {
//            number--;
//        } else if (id == R.id.rl_add) {
//            number++;
//        }
//        setNumber(number);
//    }

    private int number = 1;
    public void setNumber(int number) {
        this.number = number;
        tv_number.setText(number+"");
//        tv_number.setSelection(tv_number.getText().toString().length());
    }



    private OnNumberClickListener onNumberClickListener;

    public void setOnNumberClickListener(OnNumberClickListener onNumberClickListener) {
        this.onNumberClickListener = onNumberClickListener;
    }

    public interface OnNumberClickListener{
        void add();
        void reduce();

    }

//    public interface

    public void setNumber(String number) {
        tv_number.setText(number);
//        tv_number.setSelection(number.length());
    }



}
