package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.support.annotation.Keep;

public class OfficialID
{
    @Keep
    public String mName,mDepartment,mPost,mEmployeeId,mEmail,mPhone,mHostelID;
    @Keep
    public OfficialID(String mName, String mDepartment, String mPost, String mEmployeeId, String mEmail, String mPhone,String mHostelID) {
        this.mName = mName;
        this.mDepartment = mDepartment;
        this.mEmployeeId = mEmployeeId;
        this.mPost = mPost;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
        this.mHostelID=mHostelID;
    }

    @Keep
    public OfficialID() {
    }
}
