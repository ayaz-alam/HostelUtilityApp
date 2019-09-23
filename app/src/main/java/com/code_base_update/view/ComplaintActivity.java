package com.code_base_update.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.code_base_update.view.adapters.SingleCheckBoxAdapter;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.interfaces.IComplaintView;
import com.code_base_update.interfaces.OnItemClickListener;
import com.code_base_update.models.ComplaintModel;
import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.presenters.IModelToComplaintPresenter;

public class ComplaintActivity extends BaseActivity implements IComplaintView, IModelToComplaintPresenter {

    private ComplaintModel model;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        //Instantiate model for the activity
        model = new ComplaintModel();
        model.setPresenter(this);

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
        model.onDomainSelected(i);
    }

    @Override
    public String getDomainID() {

        return null;
    }

    @Override
    public String getSubDomain() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public long getProblemFromDate() {
        return 0;
    }

    @Override
    public ComplaintBean getComplaint() {

        ComplaintBean thisComplaint = new ComplaintBean();
        thisComplaint.setComplaintId("1234");
        thisComplaint.setStudentId("abc");

        //Fetch data from the view and prepare for the object
        thisComplaint.setComplaintDomainId(getDomainID());
        thisComplaint.setComplaintDescription(getDescription());
        thisComplaint.setComplaintSubDomain(getSubDomain());
        thisComplaint.setProblemFacingFromDate(getProblemFromDate());
        return thisComplaint;
    }

    public void registerComplaint() {
        model.registerComplaint(getComplaint());
    }

    @Override
    public void onSubDomainLoaded(final ArrayList<String> subDomain) {
        //If sub domain is present then update the new UI
        if (subDomain.size() > 0) {
            RecyclerView rvSubDomain = findViewById(R.id.rv_subdomain);
            rvSubDomain.setVisibility(View.VISIBLE);
            //rvSubDomain.setLayoutManager(new GridLayoutManager(this, 2));
            SingleCheckBoxAdapter subDomainAdapter = new SingleCheckBoxAdapter(this, R.layout.new_complaint_card, subDomain);
            subDomainAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    model.onSubDomainSelected(subDomain.get(position));
                }
            });
            rvSubDomain.setAdapter(subDomainAdapter);
        } else {
            setVisible(R.id.rv_subdomain, false);
            model.onSubDomainSelected(null);
        }
    }

    @Override
    public void onDescriptionLoaded(ArrayList<String> descriptions) {
        RecyclerView rvDescription = findViewById(R.id.rv_description);
        rvDescription.setVisibility(View.VISIBLE);
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

    @Override
    public void registrationStatus(int code) {
        //TODO show information to the user using some method list toast, snack bar etc.
    }

}

