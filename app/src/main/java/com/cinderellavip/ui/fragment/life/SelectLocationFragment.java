package com.cinderellavip.ui.fragment.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SelectCityAddressAdapter;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.ui.activity.life.SelectCityActivity;
import com.cinderellavip.util.KeyboardUtils;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;

public class SelectLocationFragment extends BaseListFragment<NetCityBean> {


    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    @Override
    public int setLayout() {
        return R.layout.fragment_select_location_life;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SelectCityAddressAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无地址");

    }

    @Override
    public void loadData() {
        super.loadData();
        new Handler().postDelayed(() -> {
            List<NetCityBean> list = new ArrayList<>();
            list.add(new NetCityBean(true));
            list.add(new NetCityBean(false));
            list.add(new NetCityBean(false));
            setData(list);
        }, 500);


    }

    //选中的item
    private NetCityBean netCityBean;

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(((baseQuickAdapter, view, position) -> {

            List<NetCityBean> data = mAdapter.getData();
            netCityBean = data.get(position);
            Intent intent = new Intent();
            intent.putExtra("netCityBean", netCityBean);
            mActivity.setResult(Activity.RESULT_OK, intent);
            mActivity.finish();

        }));
        et_search.setOnKeyListener((v, keyCode, event) -> {
            //是否是回车键
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                KeyboardUtils.hideKeyboard(et_search);
                String keyword = et_search.getText().toString().trim();
            }
            return false;
        });
    }


    @OnClick(R.id.tv_address)
    public void onClick() {
        SelectCityActivity.launch(mActivity);
    }

    public void setAddress(String address) {
        tvAddress.setText(address);
    }
}
