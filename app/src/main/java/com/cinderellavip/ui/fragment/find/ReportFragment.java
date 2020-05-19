package com.cinderellavip.ui.fragment.find;

import android.os.Bundle;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.ReportAdapter;
import com.cinderellavip.bean.ReportItem;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class ReportFragment extends BaseListFragment<ReportItem> {


    @BindView(R.id.tv_add)
    TextView tvAdd;

    private String id;
    private String type;

    public static ReportFragment newInstance(String id, String type) {
        ReportFragment cartFragment = new ReportFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("type", type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_mine_address;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        id = getArguments().getString("id");
        type = getArguments().getString("type");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ReportAdapter();
        mRecyclerView.setAdapter(mAdapter);

        tvAdd.setText("提交");
    }

    @Override
    public void loadData() {
        super.loadData();
        list = new ArrayList<>();
        list.add(new ReportItem(true, "广告内容"));
        list.add(new ReportItem(false, "不友善内容"));
        list.add(new ReportItem(false, "违法违规内容"));
        list.add(new ReportItem(false, "抄袭他人内容"));
        list.add(new ReportItem(false, "虚假互动数据"));
        list.add(new ReportItem(false, "其他"));
        setData(list);


    }


    @Override
    public void initListener() {
        super.initListener();

    }

    List<ReportItem> list;

    @OnClick(R.id.tv_add)
    public void onClick() {
        for (ReportItem reportItem:list){
            if (reportItem.isCheck){
                report(reportItem.name);
                break;
            }
        }

//
    }


    private void report(String content) {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("object_id", id + "");
        hashMap.put("type", type + "");
        hashMap.put("content", content + "");
        new RxHttp<BaseResult>().send(ApiManager.getService().getDiscussReport(hashMap),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        tsg("举报成功");
                        mActivity.finish();
                    }
                });
    }


}
