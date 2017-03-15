package com.draw.olimex.drawing.com.draw.olimex.drawing.styles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.draw.olimex.drawing.Surface;
import com.draw.olimex.drawing.utils.Utils;

import java.util.HashMap;

class EraserStyle implements Style {
	private float prevX;
	private float prevY;

	private Paint paint = new Paint();

	{
        paint.setColor(Surface.backColor);
		paint.setAlpha(StylesFactory.ALPHA_ERASER);
        paint.setDither(true);                    // set the dither to true
        paint.setStyle(Paint.Style.STROKE);       // set to STOKE
        paint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
        paint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        paint.setAntiAlias(true);                 // set anti alias so it smooths
	}

	@Override
	public void stroke(Canvas c, float x, float y) {
		c.drawLine(prevX, prevY, x, y, paint);

		prevX = x;
		prevY = y;
	}

	@Override
	public void strokeStart(float x, float y) {
        float size = Utils.getDimention(30);
        size += Surface.thickness * size / 3;
        paint.setStrokeWidth(size);
		prevX = x;
		prevY = y;
	}

    @Override
    public void strokeEnd(float x, float y) {

    }

    @Override
	public void draw(Canvas c) {
	}

    @Override
    public void draw(Canvas c, boolean reset) {

    }

    @Override
	public void setColor(int color) {
	}

	@Override
	public void saveState(HashMap<Integer, Object> state) {
	}

	@Override
	public void restoreState(HashMap<Integer, Object> state) {
	}
}
