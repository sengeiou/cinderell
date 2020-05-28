package com.cinderellavip.weight;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cinderellavip.R;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


/**
 * Created by xumingming on 2017/6/30.
 * 倒计时 组件
 */

public class CountDownView1 extends FrameLayout {

    private TextView tvDay, tvHour, tvMinute, tvSecond, tvPoint0, tvPoint1, tvPoint2;

    public CountDownView1(Context context) {
        super(context);
        init();
    }

    public CountDownView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public CountDownView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CountDownView1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }


    private void init() {

        View view = View.inflate(getContext(), R.layout.view_countdown, null);
        tvDay = (TextView) view.findViewById(R.id.tv_day);
        tvHour = (TextView) view.findViewById(R.id.tv_hour);
        tvMinute = (TextView) view.findViewById(R.id.tv_minute);
        tvSecond = (TextView) view.findViewById(R.id.tv_second);
        tvPoint0 = (TextView) view.findViewById(R.id.tv_point0);
        tvPoint1 = (TextView) view.findViewById(R.id.tv_point1);
        tvPoint2 = (TextView) view.findViewById(R.id.tv_point2);
        addView(view);


    }

    ;

    /**
     * 设置小时
     *
     * @param hour
     */
    public void setTvHour(String hour) {
        if (tvHour != null) {
            tvHour.setText(hour);
        }
    }

    /**
     * 设置分钟
     *
     * @param minute
     */
    public void setTvMinute(String minute) {
        if (tvMinute != null) {
            tvMinute.setText(minute);
        }
    }

    /**
     * 设置秒
     *
     * @param second
     */
    public void setTvSecond(String second) {
        if (tvSecond != null) {
            tvSecond.setText(second);
        }
    }

    /**
     * 设置 时分秒 背景颜色
     *
     * @param resoures
     */
    public void setTimeBackhround(int resoures) {
        if ((tvHour != null) && (tvMinute != null) && (tvSecond != null)) {
            tvDay.setBackgroundResource(resoures);
            tvHour.setBackgroundResource(resoures);
            tvMinute.setBackgroundResource(resoures);
            tvSecond.setBackgroundResource(resoures);
        }
    }

    /**
     * 设置点的颜色
     *
     * @param resoures
     */
    public void setPointColor(int resoures) {
        if ((tvPoint1 != null) && (tvPoint2 != null)) {
            tvPoint0.setTextColor(getResources().getColor(resoures));
            tvPoint1.setTextColor(getResources().getColor(resoures));
            tvPoint2.setTextColor(getResources().getColor(resoures));
        }
    }

    /**
     * 设置文字的颜色
     *
     * @param resoures
     */
    public void setTextColor(int resoures) {
        if ((tvHour != null) && (tvMinute != null) && (tvSecond != null)) {
            tvDay.setTextColor(getResources().getColor(resoures));
            tvHour.setTextColor(getResources().getColor(resoures));
            tvMinute.setTextColor(getResources().getColor(resoures));
            tvSecond.setTextColor(getResources().getColor(resoures));
        }
    }


    //一共的秒数
    private int totalTime = 0;

    public void setTime(int time) {

        totalTime = time;
//        int day = time / 60 / 60 / 24;
        //小时
        int hour = (time ) / 60 / 60;
        //分钟
        int minute = (time  - hour * 60 * 60) / 60;
        //秒
        int second = time % 60;

        if (false) {
            tvPoint0.setVisibility(VISIBLE);
            tvDay.setVisibility(VISIBLE);
        } else {
            tvPoint0.setVisibility(GONE);
            tvDay.setVisibility(GONE);
        }

        tvHour.setText(hour < 10 ? "0" + hour : hour + "");
        tvMinute.setText(minute < 10 ? "0" + minute : minute + "");
        tvSecond.setText(second < 10 ? "0" + second : second + "");


    }

    public void startTime(int time) {
        if (first) {
            first = false;
            setTime(time);
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    }



    public void startOnResfhTime(int time) {
            setTime(time);
            mHandler.sendEmptyMessageDelayed(1, 1000);
    }




    boolean first = true;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (totalTime != 0) {
                    totalTime--;
                    setTime(totalTime);
                    if (totalTime == 0) {
                        if (listener != null) {
                            listener.onFinish(true);
                        }
                    } else {
                        if (mHandler != null)
                            mHandler.sendEmptyMessageDelayed(1, 1000);
                    }
                }

            }

        }
    };

    private OnFinishListener listener;

    public OnFinishListener getListener() {
        return listener;
    }

    public void setListener(OnFinishListener listener) {
        this.listener = listener;
    }

    public interface OnFinishListener {
        void onFinish(boolean isFinish);
    }


    @Override
    protected void onDetachedFromWindow() {
//        Log.e("TAG", "onDetachedFromWindow: ");
        if (mHandler != null){
            mHandler.removeMessages(1);
            mHandler = null;
        }

        super.onDetachedFromWindow();
    }
}
