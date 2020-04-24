package com.cinderellavip.ui.fragment.life;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.cinderellavip.MainActivity;
import com.cinderellavip.R;
import com.cinderellavip.util.lifeaddress.SelectCityAdapter;
import com.cinderellavip.util.lifeaddress.User;
import com.cinderellavip.util.lifeaddress.UserModel;
import com.lwkandroid.rcvadapter.bean.RcvSectionWrapper;
import com.lwkandroid.rcvadapter.ui.RcvStickyLayout;
import com.lwkandroid.rcvadapter.utils.RcvLinearDecoration;
import com.lwkandroid.widget.indexbar.IndexBar;
import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SelectCityFragment extends BaseFragment  implements IndexBar.OnIndexLetterChangedListener{

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    private LinearLayoutManager mLayoutManager;
    private SelectCityAdapter mAdapter;
    private Handler mHandler;
    private boolean mMoved;
    private int mSectionP;

    private static final CharSequence[] INDEX_ARRAY = new CharSequence[]{
            "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    @Override
    public int setLayout() {
        return R.layout.fragment_select_city_life;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);


    }

    @Override
    public void loadData() {
        mHandler = new Handler(mActivity.getMainLooper());
        indexBar.setTextArray(INDEX_ARRAY);
        indexBar.setOnIndexLetterChangedListener(this);
        mLayoutManager = new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false);
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new SelectCityAdapter(mActivity, null);
        rvList.setAdapter(mAdapter);

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                //在这里进行第二次滚动来让Sction滚动到顶部
                if (mMoved)
                {
                    mMoved = false;
                    //获取要置顶的项在当前屏幕的位置，mSectionP是记录的要置顶项在RecyclerView中的位置
                    int n = mSectionP - mLayoutManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < rvList.getChildCount())
                    {
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = rvList.getChildAt(n).getTop();
                        //最后的移动
                        rvList.scrollBy(0, top);
                    }
                }
            }
        });

        initData();


    }
    private void initData()
    {
        //获取模拟数据
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                List<User> list = UserModel.getUserDatas();
                final List<RcvSectionWrapper<String, User>> resultList = new ArrayList<>();
                Set<String> charSet = new HashSet<>();

                for (User user : list)
                {
                    if (!charSet.contains(user.getFirstChar()))
                    {
                        resultList.add(new RcvSectionWrapper<String, User>(true, user.getFirstChar(), null));
                        charSet.add(user.getFirstChar());
                    }

                    resultList.add(new RcvSectionWrapper<String, User>(false, null, user));
                }

                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mAdapter.refreshDatas(resultList);
                    }
                });
            }
        }).start();
    }


    @Override
    public void initListener() {
        super.initListener();

    }


    @Override
    public void onTouched(boolean touched) {

    }

    @Override
    public void onLetterChanged(CharSequence indexChar, int index, float y) {

        int p = mAdapter.getSectionP(String.valueOf(indexChar));
        if (p != -1)
        {
            mSectionP = p;
            //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
            int firstItem = mLayoutManager.findFirstVisibleItemPosition();
            int lastItem = mLayoutManager.findLastVisibleItemPosition();
            //然后区分情况
            if (p <= firstItem)
            {
                //当要置顶的项在当前显示的第一个项的前面时
                rvList.scrollToPosition(p);
            } else if (p <= lastItem)
            {
                //当要置顶的项已经在屏幕上显示时
                int top = rvList.getChildAt(p - firstItem).getTop();
                rvList.scrollBy(0, top);
            } else
            {
                //当要置顶的项在当前显示的最后一项的后面时
                rvList.scrollToPosition(p);
                //这里这个变量是用在RecyclerView滚动监听里面的
                mMoved = true;
            }
        }
    }
}
