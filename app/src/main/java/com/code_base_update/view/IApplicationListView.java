package com.code_base_update.view;

import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.beans.ComplaintBean;

import java.util.ArrayList;

public interface IApplicationListView extends IBaseView{
    void onListLoaded(ArrayList<ApplicationBean> complaintBean);
    void onFailure(String msg);
}
