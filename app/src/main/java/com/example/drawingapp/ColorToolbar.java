package com.example.drawingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ColorToolbar extends Fragment implements View.OnClickListener {
    private ImageButton currPaint;
    LinearLayout paintLayout;
    PaintListener paintListener;

    public interface PaintListener{
        void paintSent(String paint);

        void sendAction(String action);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.colortoolbar,container,false);
        paintLayout=(LinearLayout)v.findViewById(R.id.paint_colors);
        v.findViewById(R.id.btn_1).setOnClickListener(this);
        currPaint=(ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
        v.findViewById(R.id.btn_2).setOnClickListener(this);
        v.findViewById(R.id.btn_3).setOnClickListener(this);
        v.findViewById(R.id.btn_4).setOnClickListener(this);
        v.findViewById(R.id.btn_5).setOnClickListener(this);
        v.findViewById(R.id.btn_6).setOnClickListener(this);
        v.findViewById(R.id.btn_7).setOnClickListener(this);
        v.findViewById(R.id.btn_8).setOnClickListener(this);
        v.findViewById(R.id.btn_9).setOnClickListener(this);
        v.findViewById(R.id.btn_10).setOnClickListener(this);
        v.findViewById(R.id.btn_11).setOnClickListener(this);
        v.findViewById(R.id.btn_12).setOnClickListener(this);
        v.findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintListener.sendAction("Save");
            }
        });
        v.findViewById(R.id.reset_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintListener.sendAction("Reset");
            }
        });
        return v;
    }
        @Override
        public void onClick(View v) {
            paintClicked(v);
        }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void paintClicked(View view){
        if(view!=currPaint)
        {
            ImageButton imgView=(ImageButton)view;
            String color=view.getTag().toString();
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint=(ImageButton)view;
            paintListener.paintSent(color);
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof PaintListener)
            paintListener= (PaintListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        paintListener=null;
    }
}
