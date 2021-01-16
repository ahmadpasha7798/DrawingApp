package com.example.drawingapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {
    public static boolean checkPermissionWRITE_EXTERNAL_STORAGE(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //showDialog("External storage", context, Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 101);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }


}
