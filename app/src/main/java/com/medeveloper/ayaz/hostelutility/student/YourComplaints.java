package com.medeveloper.ayaz.hostelutility.student;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.LoginAcitivity;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.Complaint;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.ComplaintAdapter;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class YourComplaints extends Fragment {


    public YourComplaints() {
        // Required empty public constructor
    }

    SweetAlertDialog pDialog;

    RecyclerView recyclerView;
    ComplaintAdapter adapter;
    View rootView;
    DatabaseReference baseRef;
    ArrayList<Complaint> mComplaintList;
    StudentDetailsClass student;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.student_your_complaints, container, false);

        pDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setTitleText("Please wait..");
        pDialog.show();
        recyclerView = (RecyclerView)rootView.findViewById(R.id.my_recycler_view);

         mComplaintList= new ArrayList<>();//ArrayList to store the data
        baseRef= FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id));


        if(!getPrefs(getString(R.string.pref_enroll),"NULL").equals("NULL"))
        prepareList(getPrefs(getString(R.string.pref_enroll),"NULL"));
        else
            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("Session Expired!!").setConfirmText("Please login again..").setConfirmText("Login").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                startActivity(new Intent(getActivity(), LoginAcitivity.class));
                sweetAlertDialog.dismiss();
                getActivity().finish();
            }

        });


        // Inflate the layout for this fragment
        return rootView;
    }

    void prepareList(String EnrollmentNo)
    {
        baseRef.child(getString(R.string.complaint_ref)).child(EnrollmentNo)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            mComplaintList.clear();
                            for(DataSnapshot d:dataSnapshot.getChildren())
                                mComplaintList.add(d.getValue(Complaint.class));
                            adapter = new ComplaintAdapter(getContext(), mComplaintList,0);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter.notifyDataSetChanged();
                            pDialog.dismiss();
                        }
                        else
                        {
                            pDialog.dismiss();
                            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).
                                    setTitleText("You didn't submit any complaint").show();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



    }

    String getPrefs(String Key,String defaultValue)
    {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Key, defaultValue);
    }


}
