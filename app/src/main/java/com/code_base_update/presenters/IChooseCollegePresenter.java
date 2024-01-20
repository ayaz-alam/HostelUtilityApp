package com.code_base_update.presenters;
import com.code_base_update.view.IChooseCollegeView;

public interface IChooseCollegePresenter extends IBasePresenter<IChooseCollegeView>{
    void fetchCollegeList();
    void fetchHostelList(String collegeId);

}
