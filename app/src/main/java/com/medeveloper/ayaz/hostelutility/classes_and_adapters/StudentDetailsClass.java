package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.support.annotation.Keep;

import java.io.Serializable;

/**
 * Created by Ayaz on 5/2/2018.
 */

public class StudentDetailsClass implements Serializable {
    @Keep
    public String EnrollNo,AdhaarNo,Name,Category,BloodGroup,FatherName,Class,Year,Branch,RoomNo,MobileNo,Email,FatherContact,LocalGuardianNo,Address;

    @Keep
    public StudentDetailsClass(String enrollNo, String adhaarNo, String name,
                               String category, String bloodGroup, String fatherName,
                               String aClass, String year, String branch, String roomNo,
                               String mobileNo, String email, String fatherContact,
                               String localGuardianNo, String address)
    {

        EnrollNo = enrollNo;
        AdhaarNo = adhaarNo;
        Name = name;

        Category = category;
        BloodGroup = bloodGroup;
        FatherName = fatherName;

        Class = aClass;
        Year = year;
        Branch = branch;

        RoomNo = roomNo;
        MobileNo = mobileNo;
        Email = email;
        FatherContact = fatherContact;
        LocalGuardianNo = localGuardianNo;
        Address = address;

    }

    @Keep
    public StudentDetailsClass() {
    }
}


