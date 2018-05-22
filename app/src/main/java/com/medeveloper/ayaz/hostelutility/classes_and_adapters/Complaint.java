package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.support.annotation.Keep;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medeveloper.ayaz.hostelutility.R;

import static com.medeveloper.ayaz.hostelutility.R.*;

/**
 * Created by Ayaz on 5/1/2018.
 */

public class Complaint {

    @Keep
    public String studentEnrollNo,StudentName,HostelID,RoomNo, Field,StaffContact,WardenUID,ComplaintDate,ComplaintDescription;
    @Keep
    boolean Resolved;

    @Keep
    public Complaint()
    {

    }
    @Keep
    public Complaint(String studentEnrollmentNo,String StudentName,
                     String HostelID,String RoomNo,
                     String field, String staffContact,
                     String WardenUID, String complaintDate,
                     String complaintDescription,boolean Resolved)
    {

        this.studentEnrollNo =studentEnrollmentNo;
        this.WardenUID=WardenUID;
        this.HostelID=HostelID;
        this.StudentName=StudentName;
        this.RoomNo= RoomNo;
        Field = field;
        StaffContact = staffContact;
        ComplaintDate = complaintDate;
        ComplaintDescription = complaintDescription;
        this.Resolved=Resolved;
    }
}
