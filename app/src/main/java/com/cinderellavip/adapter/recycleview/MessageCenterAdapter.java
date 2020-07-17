package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.mine.BlacklistItem;
import com.cinderellavip.bean.net.mine.MessageItem;
import com.cinderellavip.global.Constant;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.mine.MineAssetsActivity;
import com.sobot.chat.ZCSobotApi;
import com.sobot.chat.api.model.Information;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import java.util.TreeMap;

public class MessageCenterAdapter extends BaseQuickAdapter<MessageItem, BaseViewHolder> {


    public MessageCenterAdapter() {
        super(R.layout.item_message_center, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  MessageItem item) {
        int position = helper.getAdapterPosition();

        View view_space = helper.getView(R.id.view_space);
        TextView tv_number_assets = helper.getView(R.id.tv_number_assets);
        view_space.setVisibility(position ==0?View.VISIBLE:View.GONE);

        if (item.num>0){
            tv_number_assets.setVisibility(View.VISIBLE);
            tv_number_assets.setText(item.num+"");
        }else {
            tv_number_assets.setVisibility(View.GONE);
        }


        ImageView iv_image = helper.getView(R.id.iv_image);
        if (item.type == 1){
            iv_image.setImageResource(R.mipmap.icon_message1);
        }else if (item.type == 2){
            iv_image.setImageResource(R.mipmap.icon_message2);
        }else if (item.type == 3){
            iv_image.setImageResource(R.mipmap.icon_message3);
        }else if (item.type == 4){
            iv_image.setImageResource(R.mipmap.icon_message4);
        }
        helper.setText(R.id.tv_title,item.getType())
                .setText(R.id.tv_time,item.time)
                .setText(R.id.tv_content,item.message);


        LinearLayout ll_mine_assets = helper.getView(R.id.ll_mine_assets);
        ll_mine_assets.setOnClickListener(v -> {
            switch (item.type) {
            case 1:
                MineAssetsActivity.launch(getContext(),MineAssetsActivity.MINE_ASSET);
                break;
            case 2:
                MineAssetsActivity.launch(getContext(),MineAssetsActivity.ORDER);
                break;
            case 3:
                MineAssetsActivity.launch(getContext(),MineAssetsActivity.FIND);
                break;
            case 4:
                Information info = new Information();
                info.setApp_key(Constant.KEY_SERVICE);
                info.setPartnerid(GlobalParam.getUserId());
                ZCSobotApi.openZCChat(getContext(), info);
                break;
        }
        });





    }




}
