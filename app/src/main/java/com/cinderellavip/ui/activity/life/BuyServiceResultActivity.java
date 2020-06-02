package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class BuyServiceResultActivity extends BaseActivity {


    @BindView(R.id.tv_content)
    TextView tvContent;

    public static void launch(Context from, String result) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, BuyServiceResultActivity.class);
        intent.putExtra("result",result);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("发送成功");
    }


    @Override
    public void loadData() {
        String result = getIntent().getStringExtra("result");
        String content = "您购买的"+result+"长期服务已经提交，我们会有专人与您联系，与您确认长期服务相关信息，确认后，会生成长期服务订单。请您注意接听电话哦";
        tvContent.setText(content);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_service_result;
    }


    @OnClick(R.id.tv_sure)
    public void onClick() {
        finish();
    }
}
