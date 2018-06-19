package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.icu.text.DateFormat;
import java.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;

import java.util.Date;

public  class CustomDateClass {
    public static String getDate(Date time) {
        String d=null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm E, dd MMM yyyy");
        d=simpleDateFormat.format(time);
        return d;
    }
}
