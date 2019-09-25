package com.code_base_update.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.code_base_update.presenters.IComplaintPresenter;
import com.code_base_update.ui.adapters.SingleCheckBoxAdapter;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.view.IComplaintView;
import com.code_base_update.interfaces.OnItemClickListener;
import com.code_base_update.models.ComplaintModel;

public class ComplaintActivity extends BaseActivity<IComplaintView,IComplaintPresenter> implements IComplaintView {

    private ArrayList<String> subdomains;
    private ArrayList<String> descriptions;

    @Override
    protected IComplaintPresenter createPresenter() {
        return new ComplaintModel();
    }

    @Override
    protected void initViewsAndEvents() {
        Spinner spDomain = findViewById(R.id.sp_domain);
        spDomain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    onDomainSpinnerClicked(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button registrationButton = findViewById(R.id.btn_register_button);

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerComplaint();
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_student_complaint;
    }


    private void onDomainSpinnerClicked(int i) {
        mPresenter.onDomainSelected(i);
    }

    public String getDomainID() {
        int position = ((Spinner)getView(R.id.sp_domain)).getSelectedItemPosition();
        if(position==0){
            showError();
        }else{
            return getDomainIdFromPosition(position);
        }
        return null;
    }

    private String getDomainIdFromPosition(int position) {
        return "TODO";
    }

    private void showError() {

    }

    public String getSubDomain() {
        return null;
    }

    public String getDescription() {
        return null;
    }

    public long getProblemFromDate() {
        return 0;
    }

    public ComplaintBean getComplaint() {

        //TODO fetch these details from parent
        ComplaintBean thisComplaint = new ComplaintBean();
        thisComplaint.setComplaintId("1234");
        thisComplaint.setStudentId("abc");

        //TODO implement Fetch data from the view and prepare for the object
        thisComplaint.setComplaintDomainId(getDomainID());
        thisComplaint.setComplaintDescription(getDescription());
        thisComplaint.setComplaintSubDomain(getSubDomain());
        thisComplaint.setProblemFacingFromDate(getProblemFromDate());
        return thisComplaint;
    }

    public void registerComplaint() {
        mPresenter.registerComplaint(getComplaint());
    }

    @Override
    public void onSubDomainLoaded(final ArrayList<String> subDomain) {
        //If sub domain is present then update the new UI
        if (subDomain.size() > 0) {
            setVisible(R.id.tv_problem_text,true);
            RecyclerView rvSubDomain = findViewById(R.id.rv_subdomain);
            rvSubDomain.setVisibility(View.VISIBLE);
            //rvSubDomain.setLayoutManager(new GridLayoutManager(this, 2));
            SingleCheckBoxAdapter subDomainAdapter = new SingleCheckBoxAdapter(this, R.layout.new_complaint_card, subDomain);
            subDomainAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mPresenter.onSubDomainSelected(subDomain.get(position));
                }
            });
            rvSubDomain.setAdapter(subDomainAdapter);
        } else {
            setVisible(R.id.rv_subdomain, false);
            mPresenter.onSubDomainSelected(null);
        }
    }

    @Override
    public void onDescriptionLoaded(ArrayList<String> descriptions) {
        RecyclerView rvDescription = findViewById(R.id.rv_description);
        rvDescription.setVisibility(View.VISIBLE);
        setVisible(R.id.tv_description_title,true);
        //rvSubDomain.setLayoutManager(new GridLayoutManager(this, 2));
        SingleCheckBoxAdapter subDomainAdapter = new SingleCheckBoxAdapter(this, R.layout.new_complaint_card, descriptions);
        subDomainAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                setVisible(R.id.et_description, true);
            }
        });
        rvDescription.setAdapter(subDomainAdapter);
    }

    public void registrationStatus(int code) {
        //TODO show information to the user using some method list toast, snack bar etc.
    }

}

