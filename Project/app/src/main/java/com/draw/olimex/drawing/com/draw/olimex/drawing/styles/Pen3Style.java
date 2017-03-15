package com.draw.olimex.drawing.com.draw.olimex.drawing.styles;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.draw.olimex.drawing.R;
import com.draw.olimex.drawing.Surface;
import com.draw.olimex.drawing.utils.Utils;

import java.util.HashMap;

class Pen3Style implements Style {
    private float prevX;
    private float prevY;
    private Drawable pattern;

    {
        pattern = ContextCompat.getDrawable(Utils.activity, R.drawable.pen3_drawing);
        pattern.setAlpha(StylesFactory.ALPHA_PEN3);
    }

    @Override
    public void stroke(Canvas c, float x, float y) {
        int size = (int)Utils.getDimention(50);
        size += Surface.thickness * size / 3;
        pattern.setColorFilter(Surface.color, PorterDuff.Mode.SRC_IN);

        float deltaX = x - prevX;
        float deltaY = y - prevY;
        float dist = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        int cnt = (int) Math.max(dist/4, 1);
        for (int i = 0; i < cnt; i ++) {
            float xx = prevX + (x - prevX) * i / cnt;
            float yy = prevY + (y - prevY) * i / cnt;
            pattern.setBounds((int) xx - size / 2, (int) yy - size / 2, (int) xx + size / 2, (int) yy + size / 2);
            pattern.draw(c);
        }

        prevX = x;
        prevY = y;
    }

    @Override
    public void strokeStart(float x, float y) {
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
