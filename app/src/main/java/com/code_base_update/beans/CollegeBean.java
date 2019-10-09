package com.code_base_update.beans;

import java.util.ArrayList;

public class CollegeBean {

    private String collegeName;
    private String collegeId;
    private ArrayList<HostelBean> hostels;

    public ArrayList<HostelBean> getHostels() {
        return hostels;
    }

    public void setHostels(ArrayList<HostelBean> hostels) {
        this.hostels = hostels;
    }

    public CollegeBean() {
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }
}
