package com.example.drawingapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class Custom implements iDrawable {
    public int cX;
    public int cY;
    public int Radius;
    public int Corners;
    private int color;

    public Custom(){
        cX=cY=Radius=Corners=0;
    }

    public Custom(RectF rec, int c)
    {
        if(rec.left<rec.right)
            cX = (int) (rec.left+(Math.abs(rec.right-rec.left)/2));
        else
            cX = (int) (rec.right+(Math.abs(rec.right-rec.left)/2));
        if(rec.top<rec.bottom)
            cY = (int) (rec.top+(Math.abs(rec.top-rec.bottom)/2));
        else
            cY = (int) (rec.bottom+(Math.abs(rec.top-rec.bottom)/2));
        if(Math.abs(cY-rec.top)>Math.abs(cX-rec.left))
            Radius= (int) Math.abs(cY-rec.top);
        else
            Radius= (int) Math.abs(cX-rec.left);
        Corners = 8;
        color=c;
    }

    static public void DrawCustom(RectF rec,Paint paint, Canvas canvas)
    {
        int X,Y,r,corners;
        if(rec.left<rec.right)
             X= (int) (rec.left+(Math.abs(rec.right-rec.left)/2));
        else
            X = (int) (rec.right+(Math.abs(rec.right-rec.left)/2));
        if(rec.top<rec.bottom)
            Y = (int) (rec.top+(Math.abs(rec.top-rec.bottom)/2));
        else
            Y = (int) (rec.bottom+(Math.abs(rec.top-rec.bottom)/2));
        if(Math.abs(Y-rec.top)>Math.abs(X-rec.left))
            r= (int) Math.abs(Y-rec.top);
        else
            r= (int) Math.abs(X-rec.left);
        corners = 8;
        /*Path vectorPath = new Path();
        vectorPath.moveTo(6.5f, 79.99f);
        vectorPath.lineTo(37.21f, 50.5f);
        vectorPath.lineTo(6.5f, 19.79f);
        vectorPath.lineTo(18.79f, 7.5f);
        vectorPath.lineTo(49.5f, 38.21f);
        vectorPath.lineTo(80.21f, 7.5f);
        vectorPath.lineTo(92.5f, 19.79f);
        vectorPath.lineTo(61.79f, 50.5f);
        vectorPath.lineTo(92.5f, 79.99f);
        vectorPath.lineTo(80.21f, 93.5f);
        vectorPath.lineTo(49.5f, 62.79f);
        vectorPath.lineTo(18.79f, 93.5f);
        vectorPath.close();*/

        //star draw
        Path ctx = new Path();
        ctx.moveTo(X , Y);
        for(int i = 1; i <= corners * 2; i++)
        {
            double theta, x, y;
            if(i % 2 == 0){
                theta = i * (Math.PI * 2) / (corners * 2);
                x = X + (r * Math.cos(theta));
                y = Y + (r * Math.sin(theta));
            } else {
                theta = i * (Math.PI * 2) / (corners * 2);
                x = X + ((r/2) * Math.cos(theta));
                y = Y + ((r/2) * Math.sin(theta));
                ctx.lineTo((int)x ,(int)y);
            }


        }
        ctx.close();
        canvas.drawPath(ctx,paint);
    }
    public void Draw(Canvas canvas) {
        Paint paint =new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        /*Path vectorPath = new Path();
        vectorPath.moveTo(6.5f, 79.99f);
        vectorPath.lineTo(37.21f, 50.5f);
        vectorPath.lineTo(6.5f, 19.79f);
        vectorPath.lineTo(18.79f, 7.5f);
        vectorPath.lineTo(49.5f, 38.21f);
        vectorPath.lineTo(80.21f, 7.5f);
        vectorPath.lineTo(92.5f, 19.79f);
        vectorPath.lineTo(61.79f, 50.5f);
        vectorPath.lineTo(92.5f, 79.99f);
        vectorPath.lineTo(80.21f, 93.5f);
        vectorPath.lineTo(49.5f, 62.79f);
        vectorPath.lineTo(18.79f, 93.5f);
        vectorPath.close();*/

        //star draw
        Path ctx = new Path();
        ctx.moveTo(cX, cY);
        for(int i = 1; i <= Corners * 2; i++)
        {
            double theta, x, y;
            if(i % 2 == 0){
                theta = i * (Math.PI * 2) / (Corners * 2);
                x = cX + (Radius * Math.cos(theta));
                y = cY + (Radius * Math.sin(theta));
            } else {
                theta = i * (Math.PI * 2) / (Corners * 2);
                x = cX + ((Radius/2) * Math.cos(theta));
                y = cY + ((Radius/2) * Math.sin(theta));
                ctx.lineTo((int)x ,(int)y);
            }


        }
        ctx.close();
        canvas.drawPath(ctx,paint);
    }
}
