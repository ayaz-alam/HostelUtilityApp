package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import androidx.annotation.Keep;

/**
 * Created by Ayaz on 5/2/2018.
 */

public class OfficialsDetailsClass {

    @Keep
    public String mName,mEmail,mEmployeeID,mHostelID,mPhone,mPost,mDepartment,mHostelName,mAdhaarNo;

    public OfficialsDetailsClass(String mName, String mEmail,
                                 String mEmployeeID, String mHostelID,
                                 String mPhone, String mPost,
                                 String mDepartment, String mHostelName,
                                 String mAdhaarNo) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mEmployeeID = mEmployeeID;
        this.mHostelID = mHostelID;
        this.mPhone = mPhone;
        this.mPost = mPost;
        this.mDepartment = mDepartment;
        this.mHostelName = mHostelName;
        this.mAdhaarNo = mAdhaarNo;
    }

    @Keep
    public OfficialsDetailsClass() {
    }
}
