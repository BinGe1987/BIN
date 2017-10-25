package com.kwaijian.facility.OldSource.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.kwaijian.facility.OldSource.tools.Density;

public class AddImageDrawable extends Drawable {

	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(0xffd9d9d9);
		RectF outrect = new RectF(0, 0, Density.dip2px(60), Density.dip2px(60));
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(Density.dip2px((float) 0.5));
		canvas.drawRoundRect(outrect, Density.dip2px(5), Density.dip2px(5), paint);
		paint.setStyle(Style.FILL);
		paint.setColor(0xffd9d9d9);
		Rect inrect1 = new Rect(Density.dip2px(29), Density.dip2px(15), Density.dip2px(31), Density.dip2px(45));
		Rect inrect2 = new Rect(Density.dip2px(15), Density.dip2px(29), Density.dip2px(45), Density.dip2px(31));
		canvas.drawRect(inrect1, paint);
		canvas.drawRect(inrect2, paint);
	}

	@Override
	public void setAlpha(int alpha) {

	}

	@Override
	public void setColorFilter(ColorFilter colorFilter) {

	}

	@Override
	public int getOpacity() {
		return 0;
	}

}
