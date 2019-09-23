package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import androidx.annotation.Keep;

/**
 * Created by Ayaz on 5/2/2018.
 */

public class StaffDetailsClass {
    @Keep
    public String NameOfStaff;
    @Keep
    public String JoiningDate;
    @Keep
    public String ContactNumber;

    @Keep
    public String Department;

    @Keep
    public StaffDetailsClass(String nameOfStaff, String contactNumber,String department,String joiningDate) {
        NameOfStaff = nameOfStaff;
        ContactNumber = contactNumber;
        Department=department;
        JoiningDate = joiningDate;
    }

    @Keep
    public StaffDetailsClass() {
    }
}

