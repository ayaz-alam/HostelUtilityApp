package officials_module.presenter;

import com.code_base_update.presenters.IBasePresenter;

import officials_module.view.IStudentComplaintsView;

public interface IStudentComplaintsPresenter extends IBasePresenter<IStudentComplaintsView> {
    void loadComplaints();
    void changeRequestStatus(String requestId,boolean isAccepted);
}
