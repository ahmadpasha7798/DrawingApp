 package com.example.drawingapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.MediaCodec;
import android.media.MediaSync;
import android.media.projection.MediaProjection;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
public class Drawing extends androidx.appcompat.widget.AppCompatImageView {
//public class Drawing extends View{

    List shapes = new ArrayList<iDrawable>();
    private Paint drawPaint, canvasPaint,n_paint;
    boolean drawingEnabled=true;
    private Path drawPath;
    Paint erase_paint;
    private int paintColor=0xFF660000;
    private RectF rect=new RectF();
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    boolean rbanding=false;
    int width;
    int height;
   // private Paint textPaint;
    //private int textColor = Color.BLUE;
    //private float textHeight = 32;
    //private Paint rectPaint;
    private String shape="Path";
    private boolean erase=false;
    float x,y,cx,cy;

    public Drawing(@NonNull Context context) {
        super(context);
        init();
    }


    public Drawing(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public Drawing(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    void init()
    {
        erase_paint=new Paint();
        erase_paint.setColor(Color.WHITE);
        erase_paint.setAntiAlias(true);
        erase_paint.setStrokeWidth(10);
        erase_paint.setStyle(Paint.Style.STROKE);
        erase_paint.setStrokeJoin(Paint.Join.ROUND);
        erase_paint.setStrokeCap(Paint.Cap.ROUND);
        n_paint=new Paint();
        n_paint.setColor(Color.RED);
        n_paint.setStrokeWidth(2);
        n_paint.setStyle(Paint.Style.STROKE);
        drawPath=new Path();
        drawPaint=new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(10);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint=new Paint(Paint.DITHER_FLAG);
        //////////////////////////////////////////
        /*textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        if (textHeight == 0) {
            textHeight = textPaint.getTextSize();
        } else {
            textPaint.setTextSize(textHeight);
        }
        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setStyle(Paint.Style.FILL);*/
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ////////////////////////////
        width=w;
        height=h;
        canvasBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        drawCanvas=new Canvas(canvasBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(drawingEnabled){
            int i=0;
            for (Object shap:shapes) {
                if(i<shapes.size()) {
                    ((iDrawable) shap).Draw(canvas);
                    i++;
                }
                else
                    break;
            }
            canvas.drawBitmap(canvasBitmap,0,0,canvasPaint);
            if(shape.equals("Path")){
                canvas.drawPath(drawPath, drawPaint);
            }
            else if(shape.equals("Erase")){
                canvas.drawPath(drawPath, erase_paint);
            }
            else if(shape.equals("Rectangle"))
            {
                canvas.drawRect(rect,n_paint);
            }
            else if(shape.equals("Circle"))
            {
                canvas.drawOval(rect,n_paint);
            }
            else if(shape.equals("Line"))
            {
                canvas.drawLine(x,y,cx,cy,drawPaint);
            }
            else if(shape.equals("Custom"))
            {
                Custom.DrawCustom(rect,n_paint,canvas);
            }

            //canvas.drawPath(drawPath,drawPaint);

        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(drawingEnabled) {
            if(!erase)
                drawPaint.setColor(paintColor);
        /*if(shape.equals("Star")) {
            shapes.add(new Shape((int)x, (int)y, 30, 5));
        }
        else if(shape.equals("Rectangle"))
        {
            Rect r=new Rect((int)x-50,(int)y-50,(int)x+50,(int)y+50);
            shapes.add(new Rectangle(r));
        }
        else if(shape.equals("Circle"))
        {
            shapes.add(new Circle((int)x,(int)y,50));
        }
        Log.i("DRAW","Shape: " + x + ", " + y);*/
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d("Mouse","Down Event");
                    x=event.getX();
                    y=event.getY();
                    cx=x;
                    cy=y;
                    rbanding=true;
                    if (shape.equals("Path")||erase)
                        drawPath.moveTo(x, y);
                        break;
                case MotionEvent.ACTION_MOVE:
                    Log.d("Mouse","Move Event");
                    cx=event.getX();
                    cy=event.getY();
                    if(shape.equals("Path")||erase)
                        drawPath.lineTo(cx, cy);
                    else
                        rect=new RectF(x,y,cx,cy);
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d("Mouse","Up Event");
                    if(shape.equals("Path")){
                        shapes.add(new com.example.drawingapp.Path(drawPath,paintColor));
                        //drawCanvas.drawPath(drawPath, drawPaint);
                        drawPath.reset();
                    }
                    else if(erase){
                        shapes.add(new com.example.drawingapp.Path(drawPath,Color.WHITE));
                        //drawCanvas.drawPath(drawPath, drawPaint);
                        drawPath.reset();
                    }
                    else if(shape.equals("Rectangle"))
                    {
                        shapes.add(new Rectangle(rect,paintColor));
                        rect= new RectF(0,0,0,0);
                    }
                    else if(shape.equals("Circle"))
                    {
                        shapes.add(new Circle(rect,paintColor));
                        rect= new RectF(0,0,0,0);
                    }
                    else if(shape.equals("Line"))
                    {
                        shapes.add(new Line(x,y,cx,cy,paintColor));
                        x=y=cx=cy=0;
                    }
                    else if(shape.equals("Custom"))
                    {
                        shapes.add(new Custom(rect,paintColor));
                        rect= new RectF(0,0,0,0);
                    }
                    rbanding=false;
                    break;
                default:
                    return false;
            }
            invalidate();
        }
        return true;
    }

    public void setShape(String shape) {
        this.shape = shape;
        erase=false;
    }

    public void setColor(String color) {
        paintColor=Color.parseColor(color);
        drawPaint.setColor(paintColor);
    }


    public void setErase(boolean erase) {
            this.erase = erase;
            shape="Erase";
    }

    public boolean getErase() {
        return erase;
    }

    public void revert() {
        Log.d("revert",Integer.toString(shapes.size()));
        if(shapes.size()>0) {
            shapes.remove((shapes.size())-1);
            this.invalidate();
        }
    }

    public void Reset() {
        Log.d("reset",Integer.toString(shapes.size()));
        shapes.clear();
        x=y=cx=cy=0;
        rect=new RectF();
        drawPath.reset();
        this.invalidate();
    }

    Bitmap getCanvasBitmap()
    {
        //canvasBitmap=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        //canvasBitmap=Bitmap.createScaledBitmap(Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888),width,height,false);
        //this.setDrawingCacheEnabled(true);
        //this.buildDrawingCache();
        //canvasBitmap=Bitmap.createBitmap(this.getDrawingCache());
        return canvasBitmap;
    }



}