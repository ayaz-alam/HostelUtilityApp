package com.code_base_update.view;

import com.code_base_update.beans.CollegeBean;
import com.code_base_update.beans.HostelBean;

import java.util.ArrayList;

public interface IChooseCollegeView extends IBaseView {
    void initiatedFetch();
    void collegeListFetched(ArrayList<CollegeBean> collegeList);
    void hostelListFetched(ArrayList<HostelBean> hostelList,CollegeBean collegeBean);
    void failed(String msg);
    void couldNotFetchHostels();
}
