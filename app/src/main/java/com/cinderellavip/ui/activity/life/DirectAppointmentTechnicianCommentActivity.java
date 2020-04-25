package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.life.DirectAppointmentCommentFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 * 直约 技师详情
 */
public class DirectAppointmentTechnicianCommentActivity extends BaseActivity {




    public static final int comment_technical = 1;
    public static final int comment_project = 2;
    private int type ;


    public static void launch(Context from,int type) {
        Intent intent = new Intent(from, DirectAppointmentTechnicianCommentActivity.class);
        intent.putExtra("type",type);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        type = getIntent().getIntExtra("type",comment_technical);
        if (type == comment_technical){
            setBackTitle("对TA的评价");
        }else {
            setBackTitle("用户评价");
        }



    }


    @Override
    public void loadData() {
        DirectAppointmentCommentFragment fragment = new DirectAppointmentCommentFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


}
