package com.example.drawingapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Rectangle implements iDrawable {
    private RectF r;
    private int color;

    public Rectangle(RectF rec,int c) {
        r=rec;
        color=c;
    }

    @Override
    public void Draw(Canvas canvas) {
        Paint p=new Paint();
        p.setColor(color);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(r,p);
    }
}
