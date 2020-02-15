package officials_module.model;

import android.content.Context;

import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.interfaces.DataCallback;

import java.util.ArrayList;

import officials_module.presenter.IStudentComplaintsPresenter;
import officials_module.view.IStudentComplaintsView;

public class StudentComplaintListModel implements IStudentComplaintsPresenter {

    private IStudentComplaintsView mView;

    private Context context;
    public StudentComplaintListModel(Context context){
        this.context = context;
    }

    @Override
    public void loadComplaints() {
        new DatabaseManager(context).fetchComplaintList(new DataCallback<ArrayList<ComplaintBean>>(){
            @Override
            public void onSuccess(ArrayList<ComplaintBean> complaintBeans) {
                mView.onComplaintListLoaded(complaintBeans);
            }

            @Override
            public void onFailure(String msg) {
                mView.onError(msg);
            }

            @Override
            public void onError(String msg) {
                mView.onError(msg);
            }
        });


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
