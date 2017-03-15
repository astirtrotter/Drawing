package com.draw.olimex.drawing.com.draw.olimex.drawing.styles;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.draw.olimex.drawing.R;
import com.draw.olimex.drawing.Surface;
import com.draw.olimex.drawing.utils.Utils;

import java.util.HashMap;

class Pen2Style implements Style {
    private float prevX;
    private float prevY;
    private float prevSize;
    private Drawable pattern;

    {
        pattern = ContextCompat.getDrawable(Utils.activity, R.drawable.pen1_drawing);
        pattern.setAlpha(StylesFactory.ALPHA_PEN2);
    }

    @Override
    public void stroke(Canvas c, float x, float y) {
        pattern.setColorFilter(Surface.color, PorterDuff.Mode.SRC_IN);

        float deltaX = x - prevX;
        float deltaY = y - prevY;
        float dist = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        float size = Math.min(100.0f / dist, 20);
        size = Math.min(size, prevSize + 1);
        size = Math.max(size, prevSize - 2);
        size = Math.max(size, 0.5f);

        int cnt = (int) Math.max(dist * 2, 1);
        for (int i = 0; i < cnt; i ++) {
            float xx = prevX + (x - prevX) * i / cnt;
            float yy = prevY + (y - prevY) * i / cnt;
            float s = Utils.getDimention(prevSize + (size - prevSize) * i / cnt);
            s += Surface.thickness * s / 2;

            pattern.setBounds((int) (xx - s / 2), (int) (yy - s / 2), (int) (xx + s / 2), (int) (yy + s / 2));
            pattern.draw(c);
        }

        prevX = x;
        prevY = y;
        prevSize = size;
    }

    @Override
    public void strokeStart(float x, float y) {
        prevSize = 1;
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
