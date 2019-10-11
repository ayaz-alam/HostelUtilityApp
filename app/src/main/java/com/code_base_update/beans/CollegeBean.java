package com.code_base_update.beans;

import androidx.annotation.Keep;

import java.util.ArrayList;

@Keep public class CollegeBean {

    @Keep private String collegeName;
    @Keep private String collegeId;
    @Keep private ArrayList<HostelBean> hostels;

    @Keep public CollegeBean() {
    }

    @Keep public ArrayList<HostelBean> getHostels() {
        return hostels;
    }

    @Keep public void setHostels(ArrayList<HostelBean> hostels) {
        this.hostels = hostels;
    }

    @Keep public String getCollegeName() {
        return collegeName;
    }

    @Keep public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    @Keep public String getCollegeId() {
        return collegeId;
    }

    @Keep public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }
}
