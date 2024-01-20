package com.code_base_update.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.code_base_update.presenters.IComplaintPresenter;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;
import java.util.Calendar;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.view.IComplaintView;
import com.code_base_update.models.ComplaintModel;

public class ComplaintActivity extends BaseActivity<IComplaintView, IComplaintPresenter> implements IComplaintView {

    private Spinner spDomain;


    @Override
    protected IComplaintPresenter createPresenter() {
        return new ComplaintModel(this);
    }

    @Override
    protected void initViewsAndEvents() {
        setupToolbar("Register Complaint");
        enableNavigation();
        spDomain = findViewById(R.id.sp_domain);
        spDomain.setEnabled(false);
        mPresenter.loadDomain();


        spDomain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    onDomainSpinnerClicked(spDomain.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        findViewById(R.id.btn_register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs())
                    registerComplaint();
            }
        });
    }

    private boolean validateInputs() {
        if(spDomain.getSelectedItemPosition()==0){
            toastMsg("Please select a problem type");
            return false;
        }else if(((Spinner)getView(R.id.sp_subdomain)).getSelectedItemPosition()==0){
            toastMsg("Please select the problem");
            return false;
        }
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_student_complaint;
    }

    @Override
    public void onDomainLoaded(ArrayList<String> domain) {
        getView(R.id.pg_problem_loading).setVisibility(View.GONE);
        spDomain.setAlpha(1.0f);
        getView(R.id.btn_register_button).setEnabled(true);
        spDomain.setEnabled(true);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, domain);
        spDomain.setAdapter(adapter);
    }

    @Override
    public void domainLoadError(String msg) {
        toastMsg("Error: " + msg);
    }

    @Override
    public void onSubDomainLoaded(final ArrayList<String> subDomain) {
        subDomain.add(0,"Select problem");
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, subDomain);
        ((Spinner)getView(R.id.sp_subdomain)).setAdapter(adapter);
    }

    @Override
    public void subdomainLoadError(String msg) {
        toastMsg("Error: " + msg);
    }

    private void onDomainSpinnerClicked(String domain) {
        mPresenter.onDomainSelected(domain);
    }

    public String getDomainID() {
        int position = ((Spinner) getView(R.id.sp_domain)).getSelectedItemPosition();
        if (position == 0) {
            showError();
        } else {
            return ((Spinner) getView(R.id.sp_domain)).getSelectedItem().toString();
        }
        return null;
    }

    private void showError() {
        toastMsg("Please select a problem");
    }

    public String getDescription() {
        return ((Spinner)getView(R.id.sp_subdomain)).getSelectedItem().toString();
    }

    public ComplaintBean getComplaint() {

        ComplaintBean thisComplaint = new ComplaintBean();
        thisComplaint.setComplaintId("complaint_no_" + Calendar.getInstance().getTime().getTime());
        thisComplaint.setStudentId(getSession().getStudentId());
        thisComplaint.setTimeStamp(Calendar.getInstance().getTime().getTime()*-1);
        thisComplaint.setComplaintDomainId(getDomainID());
        thisComplaint.setDescriptions(getDescription());
        thisComplaint.setOptionalDescription(getOptionalDescription());
        thisComplaint.setStudentName(getSession().getStudent().getName());
        thisComplaint.setRoomNo(getSession().getStudent().getRoom());

        return thisComplaint;
    }

    private String getOptionalDescription() {
        return ((EditText) getView(R.id.et_description)).getText().toString();
    }

    public void registerComplaint() {
        mPresenter.registerComplaint(getComplaint());
    }


    @Override
    public void registrationStarted() {
        toastMsg("Please wait..");
    }

    @Override
    public void registrationFailed(String msg) {
        toastMsg("Failed: " + msg);
    }

    @Override
    public void registeredSuccessfully() {
        //clearViews();
        toastMsg("Complaint registered successfully");
    }


}

