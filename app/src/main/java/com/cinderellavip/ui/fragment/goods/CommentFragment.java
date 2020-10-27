package com.cinderellavip.ui.fragment.goods;

import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CommentAdapter;
import com.cinderellavip.bean.local.CommnetTabItem;
import com.cinderellavip.bean.net.goods.GoodsCommentItem;
import com.cinderellavip.bean.net.goods.GoodsCommentResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.weight.tab.CommentTabLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;


public class CommentFragment extends BaseListFragment<GoodsCommentItem> {

    @BindView(R.id.tab_label)
    CommentTabLayout tabLabel;
    private String id;

    @Override
    public int setLayout() {
        return R.layout.fragment_comment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        id = bundle.getString("id");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CommentAdapter(true);
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂无评价");
    }

    private String type = "0";

    @Override
    public void loadData() {


        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("product_id", ""+id);
        hashMap.put("type", type);
        hashMap.put("limit", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<GoodsCommentResult>>().send(ApiManager.getService().getGoodsComment(hashMap),
                new Response<BaseResult<GoodsCommentResult>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<GoodsCommentResult> result) {
                        GoodsCommentResult data = result.data;
                        if (page == 1 && "0".equals(type)){
                            List<CommnetTabItem> tabList = new ArrayList<>();
                            tabList.add(new CommnetTabItem("全部评价", data.all+""));
                            tabList.add(new CommnetTabItem("好评",data.good+""));
                            tabList.add(new CommnetTabItem("中评",data.normal+""));
                            tabList.add(new CommnetTabItem("差评",data.bad+""));
                            tabList.add(new CommnetTabItem("有图",data.image+""));
                            tabLabel.setTitle(tabList);
                        }
                        setData(data.comments);
                    }
                    @Override
                    public void onError(Throwable e) {
                        onErrorResult(e);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });


    }

    @Override
    public void initListener() {
        super.initListener();
        tabLabel.setOnTabPositionClickLister(position -> {
            type = position+"";
            onRefresh();
        });
    }
}
