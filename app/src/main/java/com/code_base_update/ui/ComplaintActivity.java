package com.code_base_update.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.code_base_update.presenters.IComplaintPresenter;
import com.google.android.material.chip.ChipGroup;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;
import java.util.Calendar;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.view.IComplaintView;
import com.code_base_update.models.ComplaintModel;
import com.robertlevonyan.views.chip.Chip;

public class ComplaintActivity extends BaseActivity<IComplaintView, IComplaintPresenter> implements IComplaintView {

    private ChipGroup cgSubdomain;
    private Spinner spDomain;


    @Override
    protected IComplaintPresenter createPresenter() {
        return new ComplaintModel(this);
    }

    @Override
    protected void initViewsAndEvents() {
        setupToolbar("");
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
                registerComplaint();
            }
        });

        enableNavigation();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_student_complaint;
    }

    @Override
    public void onDomainLoaded(ArrayList<String> domain) {
        spDomain.setEnabled(true);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, domain);
        spDomain.setAdapter(adapter);
    }

    @Override
    public void domainLoadError(String msg) {
        Toast.makeText(this, "Error: " + msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSubDomainLoaded(final ArrayList<String> subDomain) {

        //TODO This chip thing is not working
        cgSubdomain = findViewById(R.id.cg_subdomain);
        cgSubdomain.setSingleSelection(true);
        int i = 10001;
        cgSubdomain.clearCheck();
        setVisible(R.id.tv_problem_text, true);
        for (String description : subDomain) {
            Chip chip = (Chip) LayoutInflater.from(this).inflate(R.layout.chip, null);
            chip.setText(description);
            chip.setId(i++);
            chip.setClickable(true);
            cgSubdomain.addView(chip);
        }

    }

    @Override
    public void subdomainLoadError(String msg) {
        Toast.makeText(this, "Error: " + msg, Toast.LENGTH_LONG).show();
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
        Toast.makeText(this, "Please select a problem", Toast.LENGTH_LONG).show();
    }

    public ArrayList<String> getDescription() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < cgSubdomain.getChildCount(); i++) {
            Chip chip = (Chip) cgSubdomain.getChildAt(i);
            if (chip != null && chip.isSelected())
                list.add(chip.getText().toString());
        }
        return list;
    }

    public ComplaintBean getComplaint() {

        ComplaintBean thisComplaint = new ComplaintBean();
        thisComplaint.setComplaintId("complaint_no_" + Calendar.getInstance().getTime().getTime());
        thisComplaint.setStudentId(getSession().getStudentId());
        thisComplaint.setTimeStamp(Calendar.getInstance().getTime().getTime());
        thisComplaint.setComplaintDomainId(getDomainID());
        thisComplaint.setDescriptions(getDescription());
        thisComplaint.setOptionalDescription(getOptionalDescription());
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
        Toast.makeText(this, "Please wait..", Toast.LENGTH_LONG).show();
    }

    @Override
    public void registrationFailed(String msg) {
        Toast.makeText(this, "Failed: " + msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void registeredSuccessfully() {
        Toast.makeText(this, "Registered successfully", Toast.LENGTH_LONG).show();
    }


}

