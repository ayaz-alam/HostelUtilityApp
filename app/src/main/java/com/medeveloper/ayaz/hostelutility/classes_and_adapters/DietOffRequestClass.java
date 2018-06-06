package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.support.annotation.Keep;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Ayaz on 5/2/2018.
 */

public class DietOffRequestClass {
    @Keep
    public String Name,RoomNo,EnrollmentNo,To,From,Reason,requestID,studentMobile;
    @Keep
    public Date time;
    @Keep
    boolean accepted,seen;

    public DietOffRequestClass() {
    }

    @Keep
    public DietOffRequestClass(String name, String roomNo, String enrollmentNo, String to, String from, String reason,Date time,boolean accepted,String requestID,String studentMobile,boolean seen) {
        Name = name;
        RoomNo = roomNo;
        EnrollmentNo = enrollmentNo;
        To = to;
        From = from;
        Reason = reason;
        this.time =time;
        this.accepted=accepted;
        this.requestID=requestID;
        this.studentMobile=studentMobile;
        this.seen=seen;
    }
}
