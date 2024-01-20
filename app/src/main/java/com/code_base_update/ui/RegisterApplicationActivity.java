package com.code_base_update.ui;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.models.AddApplicationModel;
import com.code_base_update.presenters.IApplicationPresenter;
import com.code_base_update.view.IApplicationView;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.Calendar;

public class RegisterApplicationActivity extends BaseActivity<IApplicationView, IApplicationPresenter> implements IApplicationView {

    ProgressDialog mProgressDialog;

    @Override
    protected IApplicationPresenter createPresenter() {
        return new AddApplicationModel();
    }

    @Override
    protected void initViewsAndEvents() {

        setupToolbar("Register Application");
        enableNavigation();

        mProgressDialog = new MyDialog().getProgressDialog("Sending..",this);
        getView(R.id.btn_register_application).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationBean application  = getApplicationObject();
                if(validateInput(application))
                    mPresenter.saveApplication(application,getApplicationContext());
            }
        });
    }

    private boolean validateInput(ApplicationBean application) {
        clearError();
        if(TextUtils.isEmpty(application.getApplicationDomain())||application.getApplicationDomain().equals("NUll")){
            Toast.makeText(this,"Please select a domain",Toast.LENGTH_LONG).show();
            return false;
        }else if(TextUtils.isEmpty(application.getSubject())){
            setError(R.id.et_subject,"Required");
            return false;
        }else if(TextUtils.isEmpty(getDescription())){
            setError(R.id.et_description,"Required");
            return false;
        }
        return true;
    }

    private ApplicationBean getApplicationObject() {

        ApplicationBean application = new ApplicationBean(Calendar.getInstance().getTime().getTime());
        application.setApplicationDomain(getDomain());
        application.setSubject(getSubject());
        application.setDescription(getDescription());
        application.setTimeStamp(Calendar.getInstance().getTime().getTime()*-1);
        application.setDate(Calendar.getInstance().getTime().getTime());
        application.setStudentId(getSession().getStudentId());
        return application;
    }

    private String getDomain(){
        Spinner spinner = (Spinner)getView(R.id.sp_domain);
        if(spinner.getSelectedItemPosition()==0)
            return "Null";
        else return ((Spinner)getView(R.id.sp_domain)).getSelectedItem().toString();
    }

    private String getSubject(){
        return ((EditText)getView(R.id.et_subject)).getText().toString();
    }

    private String getDescription(){
        return ((EditText)getView(R.id.et_description)).getText().toString();
    }

    private void clearError(){
        ((EditText)getView(R.id.et_subject)).setError(null);
        ((EditText)getView(R.id.et_description)).setError(null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_application_activity;
    }


    @Override
    public void clearView() {
        mProgressDialog.dismiss();
        clearError();
        ((EditText)getView(R.id.et_subject)).setText(null);
        ((EditText)getView(R.id.et_description)).setText(null);
        ((Spinner)getView(R.id.sp_domain)).setSelection(0);
    }

    @Override
    public void onInitiated() {
        mProgressDialog.show();
        Toast.makeText(this,"Please wait...",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess() {
        mProgressDialog.dismiss();
        clearView();
        Toast.makeText(this,"Successfully registered",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String msg) {
        mProgressDialog.dismiss();
        Toast.makeText(this,"Failed: "+msg,Toast.LENGTH_LONG).show();

    }
}
