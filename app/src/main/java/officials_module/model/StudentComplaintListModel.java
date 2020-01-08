package officials_module.model;

import officials_module.presenter.IStudentComplaintsPresenter;
import officials_module.view.IStudentComplaintsView;

public class StudentComplaintListModel implements IStudentComplaintsPresenter {

    private IStudentComplaintsView mView;

    @Override
    public void loadComplaints() {

    }

    @Override
    public void changeRequestStatus(String requestId, boolean isAccepted) {

    }

    @Override
    public void attachView(IStudentComplaintsView view) {
        this.mView  = view;
    }

    @Override
    public void detachView() {

    }
}
