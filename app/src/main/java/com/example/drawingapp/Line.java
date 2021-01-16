package com.example.drawingapp;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Line implements iDrawable {
    private float sx,sy,ex,ey;
    private int color;

    public Line(float sx, float sy, float ex, float ey, int color) {
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
        this.color = color;
    }

    @Override
    public void Draw(Canvas canvas) {

        Paint paint=new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(10);
        canvas.drawLine(sx,sy,ex,ey,paint);
    }
}
