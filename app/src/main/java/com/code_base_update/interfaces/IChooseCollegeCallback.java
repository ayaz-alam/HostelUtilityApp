package com.code_base_update.interfaces;

import com.code_base_update.beans.CollegeBean;
import com.code_base_update.beans.HostelBean;

public interface IChooseCollegeCallback {
    void onSuccess(CollegeBean collegeBean, HostelBean hostelBean);
    void onFailure(String msg);
}
