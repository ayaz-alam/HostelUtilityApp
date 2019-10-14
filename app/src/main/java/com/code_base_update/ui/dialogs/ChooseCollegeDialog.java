package com.code_base_update.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.code_base_update.Constants;
import com.code_base_update.beans.CollegeBean;
import com.code_base_update.beans.HostelBean;
import com.code_base_update.interfaces.IChooseCollegeCallback;
import com.code_base_update.interfaces.SimpleCallback;
import com.code_base_update.models.ChooseCollegeModel;
import com.code_base_update.presenters.IChooseCollegePresenter;
import com.code_base_update.view.IChooseCollegeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

public class ChooseCollegeDialog extends Dialog implements IChooseCollegeView {

    private ProgressBar pgCollege;
    private ProgressBar pgHostel;
    private Spinner spCollege;
    private Spinner spHostel;
    private IChooseCollegePresenter mPresenter;
    private Button proceed;
    private IChooseCollegeCallback callback;
    private CollegeBean myCollege;
    private HostelBean myHostel;
    private SimpleCallback backPressed;

    public ChooseCollegeDialog(@NonNull Context context, IChooseCollegeCallback callback, SimpleCallback backPressed) {
        super(context);
        this.callback = callback;
        this.backPressed = backPressed;
        setContentView(R.layout.dialog_choose_college);
        initViews();
        mPresenter = new ChooseCollegeModel(getContext());
        mPresenter.attachView(this);
        mPresenter.fetchCollegeList();
    }

    private void initViews() {
        pgCollege = findViewById(R.id.pg_loading);
        pgHostel = findViewById(R.id.pg_loading_hostel);
        spCollege = findViewById(R.id.sp_college);
        spHostel = findViewById(R.id.sp_hostel);
        proceed = findViewById(R.id.btn_proceed);
        spHostel.setEnabled(false);
        spCollege.setEnabled(false);
        proceed.setEnabled(false);
    }

    @Override
    public void initiatedFetch() {
        spHostel.setEnabled(false);
        pgCollege.setVisibility(View.VISIBLE);
    }

    @Override
    public void collegeListFetched(final ArrayList<CollegeBean> collegeList) {
        pgCollege.setVisibility(View.GONE);
        spCollege.setEnabled(true);

        ArrayList<String> list = new ArrayList<>();
        for (CollegeBean collegeBean : collegeList)
            list.add(collegeBean.getCollegeName());
        list.add(0, "Select College");

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        spCollege.setAdapter(adapter);
        spCollege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    pgHostel.setVisibility(View.VISIBLE);
                    mPresenter.fetchHostelList(collegeList.get(position-1).getCollegeId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void hostelListFetched(final ArrayList<HostelBean> hostelList, final CollegeBean college) {

        pgHostel.setVisibility(View.GONE);
        spHostel.setEnabled(true);

        ArrayList<String> list = new ArrayList<>();
        for (HostelBean collegeBean : hostelList)
            list.add(collegeBean.getHostelName());
        list.add(0, "Select Hostel");

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        spHostel.setAdapter(adapter);
        spHostel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    myCollege = college;
                    myHostel = hostelList.get(position-1);
                    enableProceed();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void failed(String msg) {

    }

    @Override
    public void couldNotFetchHostels() {

    }

    private void enableProceed() {
        proceed.setEnabled(true);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    callback.onSuccess(myCollege,myHostel);
                    dismiss();
                }else{
                    Toast.makeText(getContext(),"Please select college and hostel properly",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isShowing()){
            backPressed.onCallback();
        }
    }

    private boolean validate() {
        return spCollege.getSelectedItemPosition()!=0&&spHostel.getSelectedItemPosition() != 0;
    }
}
