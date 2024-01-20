package com.code_base_update.view;

import com.code_base_update.beans.ComplaintBean;

import java.util.ArrayList;

public interface IComplaintListView extends IBaseView{
    void onListLoaded(ArrayList<ComplaintBean> complaintBean);
}
