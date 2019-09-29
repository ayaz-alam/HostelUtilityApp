package com.code_base_update.view;

import java.util.ArrayList;

public interface IComplaintView extends IBaseView {
    void onDomainLoaded(ArrayList<String> domain);
    void onSubDomainLoaded(ArrayList<String> subDomainList);
    void registrationStarted();
    void registrationFailed(String s);
    void registeredSuccessfully();
    void domainLoadError(String msg);
    void subdomainLoadError(String msg);
}
