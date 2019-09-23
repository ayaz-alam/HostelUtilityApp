package com.code_base_update.presenters;

import java.util.ArrayList;

public interface IModelToComplaintPresenter {
    void onSubDomainLoaded(ArrayList<String> subDomain);

    void onDescriptionLoaded(ArrayList<String> descriptions);

    void registrationStatus(int code);
}