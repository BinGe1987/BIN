package com.kwaijian.facility.OldSource.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.kwaijian.facility.OldSource.tools.Density;

public class ScorllTextView extends HorizontalScrollView {


	public ScorllTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_UP:
			if (getScrollX() > Density.dip2px(50)) {
				scrollTo(getWidth(), 0);
				
			}else{
				scrollTo(0, 0);
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

}
