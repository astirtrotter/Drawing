package com.draw.olimex.drawing.com.draw.olimex.drawing.styles;

import android.graphics.Canvas;

import java.util.HashMap;

public interface Style {
    public void strokeStart(float x, float y);

    public void stroke(Canvas c, float x, float y);

    public void strokeEnd(float x, float y);

    public void draw(Canvas c);
    public void draw(Canvas c, boolean reset);

    public void setColor(int color);

    public void saveState(HashMap<Integer, Object> state);

    public void restoreState(HashMap<Integer, Object> state);
}
