package com.draw.olimex.drawing.com.draw.olimex.drawing.styles;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.draw.olimex.drawing.R;
import com.draw.olimex.drawing.Surface;
import com.draw.olimex.drawing.utils.Utils;

import java.util.HashMap;

class BrushStyle implements Style {
    private float prevX;
    private float prevY;
    private Drawable pattern;
    private float prevAlphaRatio;

    @Override
    public void stroke(Canvas c, float x, float y) {
        float deltaX = x - prevX;
        float deltaY = y - prevY;
        float dist = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        int cnt = (int) Math.max(dist / 4, 1);

        int size = (int)Utils.getDimention(50);
        size += Surface.thickness * size / 3;
        int id = 0;
        id = R.drawable.brush_drawing;

        pattern = ContextCompat.getDrawable(Utils.activity, id);
        pattern.setColorFilter(Surface.color, PorterDuff.Mode.SRC_IN);
        float alphaRatio = Math.min(Utils.getDimention(300) / (dist*dist), 1);
        alphaRatio = Math.min(prevAlphaRatio * 1.5f, alphaRatio);
        alphaRatio = Math.max(prevAlphaRatio * 0.5f, alphaRatio);
        pattern.setAlpha((int)(StylesFactory.ALPHA_BRUSH * alphaRatio));

        for (int i = 0; i < cnt; i ++) {
            float xx = prevX + (x - prevX) * i / cnt;
            float yy = prevY + (y - prevY) * i / cnt;
            pattern.setBounds((int) xx - size / 2, (int) yy - size / 2, (int) xx + size / 2, (int) yy + size / 2);
            pattern.draw(c);
        }

        prevX = x;
        prevY = y;
        prevAlphaRatio = alphaRatio;
    }

    @Override
    public void strokeStart(float x, float y) {
        prevX = x;
        prevY = y;

        prevAlphaRatio = 0.1f;
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
