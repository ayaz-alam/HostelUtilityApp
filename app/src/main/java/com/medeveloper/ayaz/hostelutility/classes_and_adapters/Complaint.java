package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import androidx.annotation.Keep;

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
