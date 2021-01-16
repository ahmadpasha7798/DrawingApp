package com.example.drawingapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShapesToolbar extends Fragment {
    ShapeListener listener;
    ImageButton line;
    ImageButton path;
    ImageButton custom;
    ImageButton Rectangle;
    ImageButton Circle;
    ImageButton Erase;
    ImageButton revert;

    public interface ShapeListener{
        void shapeSent(String shape);
        void actionSent(String action);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.toolbar,container,false);
        line=v.findViewById(R.id.line);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.shapeSent("Line");
            }
        });
        path=v.findViewById(R.id.path);
        path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.shapeSent("Path");
            }
        });
        Erase=v.findViewById(R.id.erase_btn);
        Erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.actionSent("Erase");
            }
        });
        revert=v.findViewById(R.id.revert_btn);
        revert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.actionSent("Revert");
            }
        });
        custom=v.findViewById(R.id.custom);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.shapeSent("Custom");
            }
        });

       Rectangle=v.findViewById(R.id.Rectangle);
        Rectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.shapeSent("Rectangle");
            }
        });

        Circle=v.findViewById(R.id.Circle);
        Circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.shapeSent("Circle");
            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ShapeListener)
            listener= (ShapeListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }
}
