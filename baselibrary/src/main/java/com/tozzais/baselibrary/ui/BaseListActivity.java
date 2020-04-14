package com.tozzais.baselibrary.ui;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonSyntaxException;
import com.tozzais.baselibrary.R;
import com.tozzais.baselibrary.util.NetworkUtil;

import java.net.SocketTimeoutException;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.adapter.rxjava.HttpException;


public abstract class BaseListActivity<T> extends BaseActivity {

    protected BaseQuickAdapter mAdapter;
    protected int DEFAULT_PAGE = 0;
    protected int page = DEFAULT_PAGE;
    protected int PageSize = 10;
    public SwipeRefreshLayout swipeLayout;
    public RecyclerView mRecyclerView;



    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerView = findViewById(R.id.rv_list);
        swipeLayout = findViewById(R.id.swipeLayout);
    }


    @Override
    public void initListener() {

            //刷新
            swipeLayout.setOnRefreshListener(this::onRefresh);
            //加载更多
            mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
                loadData();
            });
            mAdapter.getLoadMoreModule().setAutoLoadMore(true);
            //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
            mAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

    }

    protected void onRefresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
//        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
//        page = DEFAULT_PAGE;
//        loadData();
        swipeLayout.setRefreshing(false);
    }


    protected void setData(boolean isRefresh, List<T> data) {
        showContent();
        int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PageSize) {
            /**
             *  参数是 false的话 显示 没有更多数据
             *  参数是 true的话 不显示
             */
            mAdapter.getLoadMoreModule().loadMoreEnd(false);
        } else {
            //自动加载下一个 显示加载中
            mAdapter.getLoadMoreModule().loadMoreComplete();
        }
        isLoad = true;
        page++;
    }

    protected void setData(List<T> data) {
        setData(page == DEFAULT_PAGE, data);
    }


    /**
     * 解决列表加载失败的
     *
     * @param e
     */
    public void onErrorResult(Throwable e) {
        if (swipeLayout != null)
            swipeLayout.setRefreshing(false);
        String s = "";
        if (page != DEFAULT_PAGE) {
            mAdapter.getLoadMoreModule().loadMoreFail();
            return;
        } else if (!NetworkUtil.isNetworkAvailable(mActivity)) {
            s = getResources().getString(R.string.error_net);
        } else if (e != null && e instanceof SocketTimeoutException) {
            s = getResources().getString(R.string.error_timeout);
        } else if (e != null && e instanceof JsonSyntaxException) {
            s = getResources().getString(R.string.error_syntax);
        } else if (e != null && e instanceof HttpException) {
            s = getResources().getString(R.string.error_http);
        } else {
            s = e.getMessage();
        }
        if (!isLoad) {
            showError(s);
        } else {
            tsg(s);
        }

    }

    //返回数据 code不等于500的
    public void onErrorResult(String s) {
        if (swipeLayout != null)
            swipeLayout.setRefreshing(false);
        if (page != DEFAULT_PAGE) {
            mAdapter.getLoadMoreModule().loadMoreFail();
            return;
        }
        if (!isLoad) {
            showError(s);
        } else {
            tsg(s);
        }

    }


}
