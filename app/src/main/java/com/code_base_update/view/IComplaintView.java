package com.code_base_update.view;

import java.util.ArrayList;

public interface IComplaintView extends IBaseView {
    void onSubDomainLoaded(ArrayList<String> subDomainList);
    void onDescriptionLoaded(ArrayList<String> descriptionList);
    void registrationStatus(int statusCode);
}
