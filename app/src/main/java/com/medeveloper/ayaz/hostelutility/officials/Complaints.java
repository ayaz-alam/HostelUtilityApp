package com.medeveloper.ayaz.hostelutility.officials;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
public class Complaints extends Fragment {


    public Complaints() {
        // Required empty public constructor
    }


    View rootView;
    DatabaseReference baseRef;
    ArrayList<Complaint> mComplaintList;
    RecyclerView mRecyclerView;
    ComplaintAdapter adapter;
    SweetAlertDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView=inflater.inflate(R.layout.officials_complaint_list, container, false);
         pDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setTitleText("Loading...");
         pDialog.show();
         mComplaintList=new ArrayList<>();
         mRecyclerView = (RecyclerView)rootView.findViewById(R.id.my_recycler_view);

        baseRef= FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id));
         baseRef.child(getString(R.string.complaint_ref)).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 mComplaintList.clear();
                 if(dataSnapshot.exists())
                 {
                     for(DataSnapshot d:dataSnapshot.getChildren())
                         for(DataSnapshot d2:d.getChildren())
                             mComplaintList.add(d2.getValue(Complaint.class));

                     reverseList();
                     adapter = new ComplaintAdapter(getContext(), mComplaintList,1);
                     mRecyclerView.setAdapter(adapter);
                     mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                     adapter.notifyDataSetChanged();
                     pDialog.dismiss();

                 }
                 else new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("No Data available").show();

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

         return rootView;
    }

    private void reverseList() {
        int len=mComplaintList.size();
        for(int i=0;i<len/2;i++)
        {
            Complaint temp=mComplaintList.get(i);
            mComplaintList.add(i,mComplaintList.get(len-i-1));
            mComplaintList.add(len-i-1,temp);

        }
    }

}
