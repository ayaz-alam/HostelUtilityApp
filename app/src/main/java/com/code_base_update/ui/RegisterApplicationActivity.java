package com.code_base_update.ui;

import android.app.ProgressDialog;
import android.view.View;
import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.models.AddApplicatoinModel;
import com.code_base_update.presenters.IApplicationPresenter;
import com.code_base_update.view.IApplicationView;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.Calendar;

public class RegisterApplicationActivity extends BaseActivity<IApplicationView, IApplicationPresenter> implements IApplicationView {

    ProgressDialog mProgressDialog;

    @Override
    protected IApplicationPresenter createPresenter() {
        return new AddApplicatoinModel();
    }

    @Override
    protected void initViewsAndEvents() {

        mProgressDialog = new MyDialog().getProgressDialog("Sending..",this);
        getView(R.id.btn_register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput())
                    mPresenter.saveApplication(getApplicationObject());
            }
        });
    }

    private boolean validateInput() {
        //TODO Validate the input
        return false;
    }

    private ApplicationBean getApplicationObject() {

        //TODO fetch details from UI
        ApplicationBean application = new ApplicationBean(1);
        application.setApplicationDomain("Domain");
        application.setSubject("Sub");
        application.setDescription("Description Description Description Description " +
                "Description Description Description Description " +
                "Description");
        application.setInitDate(Calendar.getInstance().getTime().getTime());
        application.setOptionalDescription("Null");

        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_application_activity;
    }


    @Override
    public void clearView() {
        mProgressDialog.dismiss();

    }

    @Override
    public void onInitiated() {
        mProgressDialog.dismiss();
        //TODO show progressbar
        mProgressDialog.show();

    }

    @Override
    public void onSuccess() {
        mProgressDialog.dismiss();
        clearView();
    }

    @Override
    public void onFailure(String msg) {
        mProgressDialog.dismiss();

    }
}
