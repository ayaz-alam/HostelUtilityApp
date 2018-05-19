package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.support.annotation.Keep;

/**
 * Created by Ayaz on 5/1/2018.
 */

public class Complaint {

    @Keep
    public String StudentUID,StudentName,HostelID,RoomNo, Field,StaffContact,WardenUID,ComplaintDate,ComplaintDescription;
    @Keep
    boolean Resolved;

    @Keep
    public Complaint()
    {

    }
    @Keep
    public Complaint(String StudentUID,String StudentName, String HostelID,String RoomNo,
                     String field, String staffContact,
                     String WardenUID, String complaintDate,
                     String complaintDescription,boolean Resolved)
    {

        this.StudentUID=StudentUID;
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
