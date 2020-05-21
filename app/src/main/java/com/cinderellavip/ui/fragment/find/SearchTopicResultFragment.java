package com.cinderellavip.ui.fragment.find;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SearchTopicResultAdapter;
import com.cinderellavip.bean.net.find.FindItem;
import com.cinderellavip.bean.net.find.ListDiscussesResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class SearchTopicResultFragment extends BaseListFragment<FindItem> {


    /**
     *
     * @param keyword 所搜页面进入
     * @return
     */
    private String keyword;
    public static SearchTopicResultFragment newInstance( String keyword) {
        SearchTopicResultFragment cartFragment = new SearchTopicResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        cartFragment.setArguments(bundle);
        return cartFragment;
    }



    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        keyword = getArguments().getString("keyword");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SearchTopicResultAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("没有搜索结果");

        View view = View.inflate(mActivity, R.layout.header_search_topic_result,null);
        tv_number = view.findViewById(R.id.tv_title);
        mAdapter.addHeaderView(view);


    }
    private TextView tv_number;



    @Override
    public void loadData() {
        super.loadData();
            TreeMap<String, String> hashMap = new TreeMap<>();
            hashMap.put("keyword", ""+keyword);
            hashMap.put("type", "2");
            hashMap.put("limit", ""+PageSize);
            hashMap.put("page", ""+page);
            new RxHttp<BaseResult<ListDiscussesResult>>().send(ApiManager.getService().getDiscussSearch(hashMap),
                    new Response<BaseResult<ListDiscussesResult>>(isLoad,getContext()) {
                        @Override
                        public void onSuccess(BaseResult<ListDiscussesResult> result) {
                            String span = result.data.total+"";
                            String s = "共找到"+span+"个相关话题";
                            SpannableString spannableString = new SpannableString(s);
                            spannableString.setSpan(new ForegroundColorSpan(getContext().getColor(R.color.baseColor)),
                                    s.indexOf(span),
                                    s.indexOf(span)+span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tv_number.setText(spannableString);
                            setData(result.data.discusses);
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

    public void setKeyword(String keyword) {
        this.keyword = keyword;
        onRefresh();
    }

}
