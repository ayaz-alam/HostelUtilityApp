package officials_module.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.ui.BaseRecyclerActivity;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

import officials_module.adapters.StudentComplaintAdapter;
import officials_module.model.StudentComplaintListModel;
import officials_module.presenter.IStudentComplaintsPresenter;
import officials_module.view.IStudentComplaintsView;

public class StudentComplaintsActivity extends BaseRecyclerActivity<IStudentComplaintsView, IStudentComplaintsPresenter, StudentComplaintAdapter>  implements IStudentComplaintsView{

    @Override
    public RecyclerView getRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return recyclerView;
    }

    @Override
    public StudentComplaintAdapter getAdapter() {
        return new StudentComplaintAdapter(this,new ArrayList<ComplaintBean>());
    }

    @Override
    public void initViews() {
        mPresenter.loadComplaints();

    }

    @Override
    public void refreshLayout() {
        mPresenter.loadComplaints();

    }

    @Override
    protected IStudentComplaintsPresenter createPresenter() {
        return new StudentComplaintListModel(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.simple_recycler_activity;
    }

    @Override
    public void onComplaintListLoaded(ArrayList<ComplaintBean> list) {
        super.onDataLoaded(list.size());
        adapter.update(list);
    }

    @Override
    public void onError(String msg) {
        toastMsg(msg);
    }
}
