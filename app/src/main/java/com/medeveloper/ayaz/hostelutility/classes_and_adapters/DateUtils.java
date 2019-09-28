package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import java.text.SimpleDateFormat;

import java.util.Date;

public  class DateUtils {
    public static String getDate(Date time) {
        String d=null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm E, dd MMM yyyy");
        d=simpleDateFormat.format(time);
        return d;
    }
}
