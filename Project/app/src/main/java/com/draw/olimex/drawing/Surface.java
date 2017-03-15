package com.draw.olimex.drawing;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.draw.olimex.drawing.com.draw.olimex.drawing.styles.Style;
import com.draw.olimex.drawing.utils.Utils;

public final class Surface extends SurfaceView implements Callback {
    public static final int backColor = Color.WHITE;
    public static int color = MainActivity.palettes[0];
    public static int thickness = 0;        // -1:small, 0:medium, 1:large

	public final class DrawThread extends Thread {
		private boolean mRun = true;
		private boolean mPause = false;

		@Override
		public void run() {
			waitForBitmap();

			final SurfaceHolder surfaceHolder = getHolder();
			Canvas canvas = null;

			while (mRun) {
				try {
					while (mRun && mPause) {
						Thread.sleep(100);
					}

                    Utils.checkNoAction();

					canvas = surfaceHolder.lockCanvas();
					if (canvas == null) {
						break;
					}

					synchronized (surfaceHolder) {
                        canvas.drawBitmap(bitmap, 0, 0, null);
//						controller.draw(canvas);
					}

					Thread.sleep(10);
				} catch (InterruptedException e) {
				} finally {
					if (canvas != null) {
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}

		private void waitForBitmap() {
			while (bitmap == null) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void stopDrawing() {
			mRun = false;
		}

		public void pauseDrawing() {
			mPause = true;
		}

		public void resumeDrawing() {
			mPause = false;
		}
	}

	private DrawThread drawThread;
	private final Canvas drawCanvas = new Canvas();
	private final Controller controller = new Controller(drawCanvas);
//	private Bitmap initialBitmap;
	private Bitmap bitmap;

	public Surface(Context context, AttributeSet attributes) {
		super(context, attributes);

		getHolder().addCallback(this);
		setFocusable(true);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
        Utils.lastActionTime = System.currentTimeMillis();

        if (MainActivity.isPaletteVisible)
        {
            if (event.getAction() == MotionEvent.ACTION_UP)
                Utils.activity.exitPalette();
            return true;
        }

//		switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Utils.activity.hideOtherTools();
//                break;
//		    case MotionEvent.ACTION_UP:
//                Utils.activity.showAllTools();
//                break;
//		}
		return controller.onTouch(this, event);
	}

	public void setStyle(int style) {
		Utils.drawingTool = style;
	}

	public DrawThread getDrawThread() {
		if (drawThread == null) {
			drawThread = new DrawThread();
		}
		return drawThread;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
        int size = Math.max(width, height);
		bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		bitmap.eraseColor(backColor);

		drawCanvas.setBitmap(bitmap);

		if (Utils.savedBitmap != null) {
			Bitmap bitmap = Utils.savedBitmap;
			int orientation = getResources().getConfiguration().orientation;
			if (orientation != Utils.savedBitmapOrientation)
				bitmap = Utils.rotateBitmap(Utils.savedBitmap, 90 * ((orientation - Configuration.ORIENTATION_PORTRAIT) * 2 - 1));

			drawCanvas.drawBitmap(bitmap, 0, 0, null);
		}

		Utils.savedBitmapOrientation = getResources().getConfiguration().orientation;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		getDrawThread().start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		getDrawThread().stopDrawing();
		while (true) {
			try {
				getDrawThread().join();
				break;
			} catch (InterruptedException e) {
			}
		}
		drawThread = null;
	}

	public void clearBitmap() {
		bitmap.eraseColor(backColor);
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
}
