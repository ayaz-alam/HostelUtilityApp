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
    public String studentEnrollNo,StudentName,HostelID,RoomNo, Field,StaffContact,WardenUID,ComplaintDate,ComplaintDescription,complaintUID;
    @Keep
    boolean Resolved;

    @Keep
    public Complaint()
    {

    }
    @Keep
    public Complaint(String studentEnrollNo, String studentName, String hostelID, String roomNo, String field, String staffContact, String wardenUID, String complaintDate, String complaintDescription, String complaintUID, boolean resolved) {
        this.studentEnrollNo = studentEnrollNo;
        StudentName = studentName;
        HostelID = hostelID;
        RoomNo = roomNo;
        Field = field;
        StaffContact = staffContact;
        WardenUID = wardenUID;
        ComplaintDate = complaintDate;
        ComplaintDescription = complaintDescription;
        this.complaintUID = complaintUID;
        Resolved = resolved;
    }

}
