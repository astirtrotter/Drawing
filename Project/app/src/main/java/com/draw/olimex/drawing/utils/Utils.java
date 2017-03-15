package com.draw.olimex.drawing.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.draw.olimex.drawing.MainActivity;
import com.draw.olimex.drawing.com.draw.olimex.drawing.styles.StylesFactory;

public class Utils {
    private static final long NOACTION_PERIOD = 120000; // 120 s

    public static long lastActionTime;
    public static MainActivity activity;
    public static Bitmap savedBitmap = null;
    public static int savedBitmapOrientation;

    public static int drawingTool = StylesFactory.PEN1;

    public static void checkNoAction()
    {
        if (System.currentTimeMillis() - lastActionTime > NOACTION_PERIOD) {
            activity.newPage(null);
            activity.home(null);
        }
    }

    public static float getDimention(float distance)
    {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, distance, dm);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int angle)
    {
        if (bitmap == null)
            return null;

        Matrix matrix = new Matrix();

        matrix.postRotate(angle);
        bitmap = Bitmap.createBitmap(bitmap ,
                0, 0,
                bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
        return bitmap;
    }

    public static void freeBitmap(Bitmap bitmap) {
        System.gc();
        if (bitmap != null)
            bitmap.recycle();
    }

    public static void makeFullScreen(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE;
        }
        decorView.setSystemUiVisibility(uiOptions);
    }
}
