package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

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

