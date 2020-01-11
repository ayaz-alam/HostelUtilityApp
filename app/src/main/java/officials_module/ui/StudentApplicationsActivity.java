package officials_module.ui;

import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.ui.BaseActivity;
import com.medeveloper.ayaz.hostelutility.R;

public class StudentApplicationsActivity extends BaseActivity {
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.simple_recycler_activity;
    }
}
