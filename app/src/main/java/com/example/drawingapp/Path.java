package com.example.drawingapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Path implements iDrawable {
    private android.graphics.Path path=new android.graphics.Path();
    private Paint.Style p= Paint.Style.STROKE;
    private int color;

    public Path(android.graphics.Path p,int c){
        Log.d("Path","Path Created");
        path.addPath(p);
        color=c;
    }
    @Override
    public void Draw(Canvas canvas) {
        Paint paint=new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setStyle(p);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawPath(path,paint);
    }
}
