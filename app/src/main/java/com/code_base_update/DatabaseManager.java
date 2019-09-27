package com.code_base_update;

import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.interfaces.SuccessCallback;

import java.util.ArrayList;

public class DatabaseManager {


    public ArrayList<ComplaintBean> loadAllComplaint() {

        //TODO how to fetch data from database
        return new ArrayList<>();

    }

    public void saveApplication(SuccessCallback callback, ApplicationBean applicationToSave) {
        callback.onInitiated();
        boolean success =true;
        //TODO save to database
        if(success)
            callback.onSuccess();
        else callback.onFailure("Failed");

    }
}
