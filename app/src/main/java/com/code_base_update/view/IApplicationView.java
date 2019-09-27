package com.code_base_update.view;

import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.view.IBaseView;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

public interface IApplicationView extends IBaseView,SuccessCallback {
    void clearView();
}
