package com.draw.olimex.drawing;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.draw.olimex.drawing.com.draw.olimex.drawing.styles.StylesFactory;
import com.draw.olimex.drawing.utils.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int[] palettes = new int[] {0xfff27f85, 0xfff79f6b, 0xff82c89e, 0xff92bde3, 0xffe486b6, 0xff323232,
                                                     0xffc92934, 0xffeb7332, 0xff23a75f, 0xff2d71b5, 0xffa5287e, 0xffb2b2b2,
                                                     0xffe6204d, 0xfffae939, 0xff67bb4d, 0xff25a1d8, 0xff823e95, 0xff828282,
                                                     0xffee5e88, 0xfffaef6f, 0xffbdda81, 0xff5cccf4, 0xffd3add0, 0xff000000};
    public static boolean isPaletteVisible = false;
    private View.OnTouchListener touchListener = null;
    private View.OnClickListener onPaletteClickListener = null;
    private Surface surface;

    private ImageButton btnPen1, btnPen2, btnPen3, btnBrush, btnAirCompressor, btnEraser, btnPalette, btnNewPage, btnHome;

    private LinearLayout layoutThickness;
    private ImageButton btnThickSmall, btnThickMedium, btnThickLarge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.activity = this;
        Utils.lastActionTime = System.currentTimeMillis();

        super.onCreate(savedInstanceState);

        // hide Action bar
        getSupportActionBar().hide();

        Utils.makeFullScreen(this);

        // show its content
        setContentView(R.layout.activity_main);

        surface = (Surface) findViewById(R.id.surface);

        touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    v.getBackground().setAlpha(130);
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    v.getBackground().setAlpha(255);

                Utils.lastActionTime = System.currentTimeMillis();
                return false;
            }
        };

        btnPen1 = (ImageButton) findViewById(R.id.pen1);
        btnPen2 = (ImageButton) findViewById(R.id.pen2);
        btnPen3 = (ImageButton) findViewById(R.id.pen3);
        btnBrush = (ImageButton) findViewById(R.id.brush);
        btnAirCompressor = (ImageButton) findViewById(R.id.aircompressor);
        btnEraser = (ImageButton) findViewById(R.id.eraser);
        btnPalette = (ImageButton) findViewById(R.id.palette);
        btnNewPage = (ImageButton) findViewById(R.id.newpage);
        btnHome = (ImageButton) findViewById(R.id.homebutton);

        btnPen1.setOnTouchListener(touchListener);
        btnPen2.setOnTouchListener(touchListener);
        btnPen3.setOnTouchListener(touchListener);
        btnBrush.setOnTouchListener(touchListener);
        btnAirCompressor.setOnTouchListener(touchListener);
        btnEraser.setOnTouchListener(touchListener);
        btnPalette.setOnTouchListener(touchListener);
        btnNewPage.setOnTouchListener(touchListener);
        btnHome.setOnTouchListener(touchListener);

        btnPalette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPalette();
            }
        });


        onPaletteClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout ll[] = new LinearLayout[] {(LinearLayout) findViewById(R.id.palette_group1),
                                                        (LinearLayout) findViewById(R.id.palette_group2),
                                                        (LinearLayout) findViewById(R.id.palette_group3),
                                                        (LinearLayout) findViewById(R.id.palette_group4)};
                int i;
                for (i = 0; i < 24; i ++) {
                    ImageButton ib = (ImageButton) ll[i / 6].getChildAt(i % 6);
                    if (v.getId() == ib.getId())
                        break;
                }
                if (i < 24) {
                    changeColor(palettes[i]);
                    exitPalette();
                }
            }
        };
        LinearLayout ll[] = new LinearLayout[] {(LinearLayout) findViewById(R.id.palette_group1),
                                                (LinearLayout) findViewById(R.id.palette_group2),
                                                (LinearLayout) findViewById(R.id.palette_group3),
                                                (LinearLayout) findViewById(R.id.palette_group4)};
        for (int i = 0; i < 24; i ++) {
            ImageButton ib = (ImageButton) ll[i / 6].getChildAt(i % 6);
            ib.getBackground().setColorFilter(palettes[i], PorterDuff.Mode.SRC_IN);
            ib.setOnTouchListener(touchListener);
            ib.setOnClickListener(onPaletteClickListener);
        }

        layoutThickness = (LinearLayout) findViewById(R.id.thickness_layout);
        btnThickSmall = (ImageButton) findViewById(R.id.thickness_small);
        btnThickMedium = (ImageButton) findViewById(R.id.thickness_medium);
        btnThickLarge = (ImageButton) findViewById(R.id.thickness_large);

        btnThickSmall.setOnTouchListener(touchListener);
        btnThickMedium.setOnTouchListener(touchListener);
        btnThickLarge.setOnTouchListener(touchListener);

        // Init Drawing
        changeColor(Surface.color);
        isPaletteVisible = false;
    }

    public void enterPalette()
    {
        if (isPaletteVisible)
            return;

        Animation animLeftRight = AnimationUtils.loadAnimation(this, R.anim.left_right);
        Animation animRightLeft = AnimationUtils.loadAnimation(this, R.anim.right_left);

        LinearLayout llTools = (LinearLayout) findViewById(R.id.drawing_tool_layout);
        llTools.startAnimation(animRightLeft);
        llTools.setVisibility(View.INVISIBLE);
//        layoutThickness.startAnimation(animRightLeft);
        layoutThickness.setVisibility(View.INVISIBLE);
        LinearLayout llPalette = (LinearLayout) findViewById(R.id.palette_layout);
        llPalette.startAnimation(animLeftRight);
        llPalette.setVisibility(View.VISIBLE);

        isPaletteVisible = true;
    }

    public void exitPalette()
    {
        if (!isPaletteVisible)
            return;

        Animation animLeftRight = AnimationUtils.loadAnimation(this, R.anim.left_right);
        Animation animRightLeft = AnimationUtils.loadAnimation(this, R.anim.right_left);

        LinearLayout llPalette = (LinearLayout) findViewById(R.id.palette_layout);
        llPalette.startAnimation(animRightLeft);
        llPalette.setVisibility(View.GONE);

        LinearLayout llTools = (LinearLayout) findViewById(R.id.drawing_tool_layout);
        llTools.startAnimation(animLeftRight);
        llTools.setVisibility(View.VISIBLE);
//        layoutThickness.startAnimation(animLeftRight);
        layoutThickness.setVisibility(View.VISIBLE);

        isPaletteVisible = false;
    }

    public void onThickness(View v)
    {
        if (v.getId() == btnThickSmall.getId())
            Surface.thickness = -1;
        else if (v.getId() == btnThickMedium.getId())
            Surface.thickness = 0;
        else if (v.getId() == btnThickLarge.getId())
            Surface.thickness = 1;

        changeColor(Surface.color);
    }

    public void changeTool(View v)
    {
        switch (v.getId())
        {
            case R.id.pen1:
                getSurface().setStyle(StylesFactory.PEN1);
                break;
            case R.id.pen2:
                getSurface().setStyle(StylesFactory.PEN2);
                break;
            case R.id.pen3:
                getSurface().setStyle(StylesFactory.PEN3);
                break;
            case R.id.brush:
                getSurface().setStyle(StylesFactory.BRUSH);
                break;
            case R.id.aircompressor:
                getSurface().setStyle(StylesFactory.AIRCOMPRESSOR);
                break;

            case R.id.eraser:
                getSurface().setStyle(StylesFactory.ERASER);
                break;
        }
        changeColor(Surface.color);
    }

    public void changeColor(int color)
    {
        Surface.color = color;
        btnPalette.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        int style = Utils.drawingTool;

        int unusedColor = Color.BLACK;
        if (color == unusedColor)
            unusedColor = Color.GRAY;

        btnPen1.getBackground().setColorFilter(style == StylesFactory.PEN1?color:unusedColor, PorterDuff.Mode.SRC_IN);
        btnPen2.getBackground().setColorFilter(style == StylesFactory.PEN2?color:unusedColor, PorterDuff.Mode.SRC_IN);
        btnPen3.getBackground().setColorFilter(style == StylesFactory.PEN3?color:unusedColor, PorterDuff.Mode.SRC_IN);
        btnBrush.getBackground().setColorFilter(style == StylesFactory.BRUSH?color:unusedColor, PorterDuff.Mode.SRC_IN);
        btnAirCompressor.getBackground().setColorFilter(style == StylesFactory.AIRCOMPRESSOR?color:unusedColor, PorterDuff.Mode.SRC_IN);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        if (style == StylesFactory.ERASER) {
            btnEraser.setBackgroundResource(isPortrait ? R.drawable.eraser_selected : R.drawable.eraser_selected_rot);
            btnEraser.setAlpha(1f);
        } else {
            btnEraser.setBackgroundResource(isPortrait ? R.drawable.eraser : R.drawable.eraser_rot);
            btnEraser.setAlpha((color == Color.WHITE) ? 0.5f : 1f);
        }

        btnThickSmall.getBackground().setColorFilter(Surface.thickness == -1 ? color : unusedColor, PorterDuff.Mode.SRC_IN);
        btnThickMedium.getBackground().setColorFilter(Surface.thickness == 0 ? color : unusedColor, PorterDuff.Mode.SRC_IN);
        btnThickLarge.getBackground().setColorFilter(Surface.thickness == 1 ? color : unusedColor, PorterDuff.Mode.SRC_IN);
    }

    public void newPage(View v)
    {
        getSurface().clearBitmap();

        if (isPaletteVisible)
            exitPalette();
    }

    public void home(View v) {
        newPage(null);

        surface.getDrawThread().stopDrawing();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Utils.freeBitmap(Utils.savedBitmap);
        Utils.savedBitmap = null;

        finish();
    }

//    public void hideOtherTools()
//    {
//        int style = Utils.drawingTool;
//        btnPen1.setVisibility(style == StylesFactory.PEN1 ? View.VISIBLE : View.INVISIBLE);
//        btnPen2.setVisibility(style == StylesFactory.PEN2 ? View.VISIBLE : View.INVISIBLE);
//        btnPen3.setVisibility(style == StylesFactory.PEN3 ? View.VISIBLE : View.INVISIBLE);
//        btnBrush.setVisibility(style == StylesFactory.BRUSH ? View.VISIBLE : View.INVISIBLE);
//        btnAirCompressor.setVisibility(style == StylesFactory.AIRCOMPRESSOR ? View.VISIBLE : View.INVISIBLE);
//        btnEraser.setVisibility(style == StylesFactory.ERASER ? View.VISIBLE : View.INVISIBLE);
//
//        layoutThickness.setVisibility(View.GONE);
//    }
//
//    public void showAllTools()
//    {
//        btnPen1.setVisibility(View.VISIBLE);
//        btnPen2.setVisibility(View.VISIBLE);
//        btnPen3.setVisibility(View.VISIBLE);
//        btnBrush.setVisibility(View.VISIBLE);
//        btnAirCompressor.setVisibility(View.VISIBLE);
//        btnEraser.setVisibility(View.VISIBLE);
//
//        layoutThickness.setVisibility(View.VISIBLE);
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            home(btnHome);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public Surface getSurface() {
        return surface;
    }


    @Override
    protected void onPause() {
        super.onPause();

        new Thread() {
            @Override
            public void run() {
                Utils.freeBitmap(Utils.savedBitmap);
                Utils.savedBitmap = null;

                Bitmap bitmap = surface.getBitmap();
                if (bitmap != null)
                    Utils.savedBitmap = Bitmap.createBitmap(bitmap);
            }
        }.run();

        Utils.lastActionTime = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.lastActionTime = System.currentTimeMillis();
    }

    @Override
    public void onClick(View v) {

    }
}
