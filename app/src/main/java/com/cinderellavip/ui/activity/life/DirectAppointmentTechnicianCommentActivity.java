package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.bean.request.TechnicalComment;
import com.cinderellavip.ui.fragment.life.DirectAppointmentCommentFragment;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 * 直约 技师详情
 */
public class DirectAppointmentTechnicianCommentActivity extends BaseActivity {




    private TechnicalComment technicalComment;

    public static void launch(Context from, TechnicalComment technicalComment) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, DirectAppointmentTechnicianCommentActivity.class);
        intent.putExtra("technicalComment",technicalComment);
        from.startActivity(intent);
    }




    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        technicalComment = getIntent().getParcelableExtra("technicalComment");
        if (technicalComment.type == TechnicalComment.comment_technical){
            setBackTitle("对TA的评价");
        }else {
            setBackTitle("用户评价");
        }



    }


    @Override
    public void loadData() {
        DirectAppointmentCommentFragment fragment =DirectAppointmentCommentFragment.newInstance(technicalComment);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


}
