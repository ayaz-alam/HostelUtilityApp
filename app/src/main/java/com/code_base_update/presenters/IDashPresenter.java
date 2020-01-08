package com.code_base_update.presenters;
import android.net.Uri;

import com.code_base_update.view.IDashView;

public interface IDashPresenter extends IBasePresenter<IDashView> {
    void loadData();
    void loadUserName();
    void loadUserImageUrl();
}
