package com.example.drawingapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class DrawingFragment extends Fragment {
    Drawing drawing_board;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.drawing_fragment,container,false);
        drawing_board=v.findViewById(R.id.Drawing_board);

        return v;
    }

    public void setShape(String shape)
    {
        drawing_board.setShape(shape);
    }

    public void setColor(String color) {
        drawing_board.setColor(color );
    }

    public void enableErase(boolean b) {
        drawing_board.setErase(b);
    }

    public void revert() {
        drawing_board.revert();
    }

    public void Reset() {
        drawing_board.Reset();
    }



    public Drawing getDrawingView() {
        return drawing_board;
    }

    public Bitmap getBitmap() {
        View view=(View)drawing_board;
        Bitmap returnedBitmap=Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(returnedBitmap);
        Drawable bgDrawable=  view.getBackground();
        if(bgDrawable==null)
        {
            bgDrawable.draw(canvas);
        }
        else
        {
            canvas.drawColor(Color.WHITE);
        }

        view.draw(canvas);
        return returnedBitmap;
    }


    public void takeScreenshot()
    {
        View MainView=getActivity().getWindow().getDecorView();
        MainView.setDrawingCacheEnabled(true);
        MainView.buildDrawingCache();
        Bitmap MainBitmap=MainView.getDrawingCache();
        Rect frame=new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

        int statusBarHeight=frame.top;
        int width=getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int height=getActivity().getWindowManager().getDefaultDisplay().getHeight();

        Bitmap OutBitmap=Bitmap.createBitmap(MainBitmap,0,statusBarHeight,width,height-statusBarHeight);
        MainView.destroyDrawingCache();

        try{
            String path = Environment.getExternalStorageDirectory().toString();
            OutputStream fout=null;
            String name= UUID.randomUUID().toString();

            File file=new File(path,name+".png");
            fout=new FileOutputStream(file);

            OutBitmap.compress(Bitmap.CompressFormat.PNG,90,fout);

            fout.flush();
            fout.close();

            String imgSaved=MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
            if(imgSaved!=null)
            {
                //Toast.makeText(this,"Saved!",Toast.LENGTH_SHORT).show();

            }
            else
            {
                Log.d("Failed",imgSaved);
                //Toast.makeText(this,"Oops! Saving Process Failed",Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
