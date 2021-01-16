package com.example.drawingapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Circle implements iDrawable {
    /*private int cx;
    private int cy;
    private int radius;*/
    RectF rect;
    int color;

    //public Circle(int x,int y, int r)
    public Circle(RectF r,int c)
    {
        rect=r;
        color=c;
        /*cx=x;
        cy=y;
        radius=r;*/
    }
    @Override
    public void Draw(Canvas canvas) {

        Paint paint=new Paint();
        paint.setColor(color);
        //canvas.drawCircle(cx,cy,radius,textColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(rect, paint);
    }
}
