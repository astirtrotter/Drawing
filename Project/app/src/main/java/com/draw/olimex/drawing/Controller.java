package com.draw.olimex.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.draw.olimex.drawing.com.draw.olimex.drawing.styles.Style;
import com.draw.olimex.drawing.com.draw.olimex.drawing.styles.StylesFactory;
import com.draw.olimex.drawing.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class Controller implements View.OnTouchListener {
	private final Canvas mCanvas;

	public Controller(Canvas canvas) {
		mCanvas = canvas;
	}

	private SparseArray<Style> styles = new SparseArray<>();
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int pointerIndex = event.getActionIndex();
		int pointerID = event.getPointerId(pointerIndex);
		Style style = styles.get(pointerID);

		float x = event.getX(pointerIndex);
		float y = event.getY(pointerIndex);

		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				style = StylesFactory.getStyleInstance(Utils.drawingTool);
				styles.put(pointerID, style);

				style.strokeStart(x, y);
				break;
			case MotionEvent.ACTION_MOVE:
				for (int i = event.getPointerCount() - 1; i >= 0; i--) {
					style = styles.get(event.getPointerId(i));
					if (style != null) {
						x = event.getX(i);
						y = event.getY(i);
						style.stroke(mCanvas, x, y);
					}
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_CANCEL:
				style.strokeEnd(x, y);
				styles.remove(pointerID);
				break;
		}
		return true;
	}
}
