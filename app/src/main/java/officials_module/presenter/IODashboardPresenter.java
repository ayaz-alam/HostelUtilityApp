package officials_module.presenter;

import com.code_base_update.presenters.IBasePresenter;

import officials_module.view.IODashboardView;

public interface IODashboardPresenter extends IBasePresenter<IODashboardView> {
    void loadData();

    void loadProfileImage();

}
