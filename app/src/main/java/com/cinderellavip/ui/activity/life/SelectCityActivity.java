package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.map.LocationUtil;
import com.cinderellavip.ui.fragment.life.SelectCityFragment;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SelectCityActivity extends BaseActivity {


    @BindView(R.id.tv_address)
    TextView tvAddress;

    public static void launch(Activity from) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, SelectCityActivity.class);
        from.startActivityForResult(intent, RequestCode.request_service_city);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        setBackTitle("选择城市");
        location();

    }


    @Override
    public void loadData() {
        SelectCityFragment fragment = new SelectCityFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_list, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_city;
    }


    private void location() {
        LocationUtil.getInstance().start(mActivity, (aMapLocation, lat, lnt) -> {
            if (aMapLocation.getErrorCode() == 0) {
                String city = aMapLocation.getCity();
                String replaceAll = city.replaceAll("市", "");
                tvAddress.setText(TextUtils.isEmpty(replaceAll) ? "南京" : replaceAll);
            } else {
                tvAddress.setText("未知");
            }

        });
    }

    @OnClick({R.id.tv_address, R.id.ll_refresh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                String name = tvAddress.getText().toString().trim();
                if ("未知".equals(name)){
                    tsg("操作失败，未获取到当前城市");
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("name",name);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
                break;
            case R.id.ll_refresh:
                location();
                break;
        }
    }
}
