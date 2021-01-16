package com.example.drawingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class MainActivity extends AppCompatActivity implements ShapesToolbar.ShapeListener,ColorToolbar.PaintListener {

    DrawingFragment drawing_board=new DrawingFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawing_board=(DrawingFragment)getSupportFragmentManager().findFragmentById(R.id.drawing_board);
    }

    @Override
    public void shapeSent(String shape) {
        drawing_board.setShape(shape);
    }

    @Override
    public void actionSent(String action) {
        if(action.equals("Erase"))
        {
            drawing_board.enableErase(true);
        }
        else
        {
            drawing_board.revert();
        }
    }

    @Override
    public void paintSent(String paint) {
        drawing_board.setColor(paint);
    }

    @Override
    public void sendAction(String action) {
        if (action.equals("Reset")) {
            AlertDialog.Builder newDialog=new AlertDialog.Builder(this);
            newDialog.setTitle("New Drawing");
            newDialog.setMessage("Start new drawing (you will lose all unsaved data)?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    drawing_board.Reset();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            newDialog.show();
        }

        else if(action.equals("Save")){
            AlertDialog.Builder newDialog=new AlertDialog.Builder(this);
            newDialog.setTitle("New Drawing");
            newDialog.setMessage("Save drawing to Gallery?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveImageFunc();
                    //drawing_board.takeScreenshot();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            newDialog.show();
        }
    }

    void saveImageFunc(){
        ImageView v=(ImageView)drawing_board.getDrawingView();
        v.setDrawingCacheEnabled(true);

        /*Bitmap b=drawing_board.getBitmap();

        Bitmap emptyBitmap=Bitmap.createBitmap(b.getWidth(),b.getHeight(),b.getConfig());
        if(b.sameAs(emptyBitmap))
        Log.d("bitmap",b.toString());
        File f=new File(Environment.getExternalStorageDirectory().toString()+ UUID.randomUUID().toString()+".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"Oops! Saving Process Failed",Toast.LENGTH_SHORT).show();
        }
        FileOutputStream fout=null;
        try{
            fout=new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,"Oops! Saving Process Failed",Toast.LENGTH_SHORT).show();
        }

        b.compress(Bitmap.CompressFormat.JPEG,0,fout);
        try{
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        String imgSaved=null;

        if (PermissionManager.checkPermissionWRITE_EXTERNAL_STORAGE(this)){
         imgSaved= MediaStore.Images.Media.insertImage(this.getContentResolver(),v.getDrawingCache(), UUID.randomUUID().toString()+".png","drawing");
        }
        if(imgSaved!=null)
        {
            Toast.makeText(this,"Saved!",Toast.LENGTH_SHORT).show();
        }
        else
       {
            Toast.makeText(this,"Oops! Saving Process Failed",Toast.LENGTH_SHORT).show();
        }

        v.destroyDrawingCache();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission given",
                            Toast.LENGTH_SHORT).show();
                    //saveImage(finalBitmap, image_name); <- or whatever you want to do after permission was given . For instance, open gallery or something
                } else {
                    Toast.makeText(this, "Permission Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}