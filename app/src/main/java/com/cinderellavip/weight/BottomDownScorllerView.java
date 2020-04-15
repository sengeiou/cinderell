package com.cinderellavip.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;

public class BottomDownScorllerView extends NestedScrollView {

	private int startX;
	private int startY;

	public BottomDownScorllerView(Context context, AttributeSet attrs,
                                  int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public BottomDownScorllerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BottomDownScorllerView(Context context) {
		super(context);
	}

	/**
	 * 事件分发
	 * 
	 * 1. 上下滑动需要拦截 2. 左右滑动:如果当前是第一个页面,向右划需要拦截; 如果当前是最后一个页面,向左划,需要拦截
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = (int) ev.getX();
			startY = (int) ev.getY();

			getParent().requestDisallowInterceptTouchEvent(true);// 不要拦截,
			// 这样才可以走到ACTION_MOVE方法中
			break;
		case MotionEvent.ACTION_MOVE:
			int endX = (int) ev.getX();
			int endY = (int) ev.getY();

			int dx = endX - startX;
			int dy = endY - startY;

			if (dy < 0 && Math.abs(dx) < Math.abs(dy)) {// 检测是否滑动到底
				View childView = this.getChildAt(0);
				if (childView != null
						&& childView.getMeasuredHeight() <= getScrollY()
								+ getHeight()) {
					getParent().requestDisallowInterceptTouchEvent(false);
					return false;
				}
			}

			break;

		default:
			break;
		}
		super.onTouchEvent(ev);
		return true;
	}

}
