package com.draw.olimex.drawing.com.draw.olimex.drawing.styles;

import java.util.HashMap;
import java.util.Map;

public class StylesFactory {
    public static final int ALPHA_PEN1 = 25;
    public static final int ALPHA_PEN2 = 75;
    public static final int ALPHA_PEN3 = 60;
    public static final int ALPHA_BRUSH = 80;
    public static final int ALPHA_AIRCOMPRESSOR = 90;
    public static final int ALPHA_ERASER = 255;

    public static final int PEN1 = 0x1001;
    public static final int PEN2 = 0x1002;
    public static final int PEN3 = 0x1003;
    public static final int BRUSH = 0x1004;
    public static final int AIRCOMPRESSOR = 0x1005;
	public static final int ERASER = 0x1006;

	public static Style getStyleInstance(int id) {
		switch (id) {
            case PEN1:
                return new Pen1Style();
            case PEN2:
                return new Pen2Style();
            case PEN3:
                return new Pen3Style();
            case BRUSH:
                return new BrushStyle();
            case AIRCOMPRESSOR:
                return new AirCompressorStyle();
            case ERASER:
                return new EraserStyle();

		default:
			throw new RuntimeException("Invalid style ID");
		}
	}
}
