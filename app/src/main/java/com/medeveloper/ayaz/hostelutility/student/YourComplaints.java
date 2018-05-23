package com.medeveloper.ayaz.hostelutility.student;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.Complaint;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.ComplaintAdapter;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.student_your_complaints, container, false);

        pDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setTitleText("Please wait..");
        pDialog.show();
        recyclerView = (RecyclerView)rootView.findViewById(R.id.my_recycler_view);

        final ArrayList<Complaint> mComplaintList = new ArrayList<>();//ArrayList to store the data
        baseRef= FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id));
        baseRef.child(getString(R.string.complaint_ref)).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            for(DataSnapshot d:dataSnapshot.getChildren())
                                mComplaintList.add(d.getValue(Complaint.class));


                            adapter = new ComplaintAdapter(getContext(), mComplaintList,0);
                            recyclerView.setAdapter(adapter);
                            // recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter.notifyDataSetChanged();
                            pDialog.dismiss();
                        }

                        else new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).
                                setTitleText("No Data Available").show();//,Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        // Inflate the layout for this fragment
        return rootView;
    }

}
