package com.cinderellavip.ui.fragment.life;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.DirectCommentAdapter;
import com.cinderellavip.bean.ListBean;
import com.cinderellavip.bean.ListData;
import com.cinderellavip.bean.direct.DirectPersonComment;
import com.cinderellavip.bean.request.TechnicalComment;
import com.cinderellavip.global.CinderellaApplication;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;

public class DirectAppointmentCommentFragment extends BaseListFragment<DirectPersonComment> {



    private TechnicalComment technicalComment;
    public static DirectAppointmentCommentFragment newInstance(TechnicalComment technicalComment){
        DirectAppointmentCommentFragment cartFragment = new DirectAppointmentCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("technicalComment",technicalComment);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }




    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        technicalComment = getArguments().getParcelable("technicalComment");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new DirectCommentAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无评价");

    }

    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("waiter", technicalComment.waiter);
        hashMap.put("project", technicalComment.project);
        hashMap.put("page", ""+page);
        hashMap.put("longitude", "" + CinderellaApplication.longitude);
        hashMap.put("latitude", "" + CinderellaApplication.latitude);
        new RxHttp<BaseResult<ListBean<ListData<DirectPersonComment>>>>().send(ApiManager.getService().getPersonComment(hashMap),
                new Response<BaseResult<ListBean<ListData<DirectPersonComment>>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListBean<ListData<DirectPersonComment>>> result) {
                        setData(result.data.list.data);
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


}
