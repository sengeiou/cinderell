package com.cinderellavip.adapter.recycleview;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.ui.activity.mine.LeaderBoardActivity;


public class SmallVaultHistoryRankAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public SmallVaultHistoryRankAdapter() {
        super(R.layout.item_smallvault_history_rank, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        int position = helper.getAdapterPosition();
        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            LeaderBoardActivity.launch(getContext());
        });
    }




}
