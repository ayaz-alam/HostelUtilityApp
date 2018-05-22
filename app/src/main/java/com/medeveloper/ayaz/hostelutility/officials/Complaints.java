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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView=inflater.inflate(R.layout.officials_complaint_list, container, false);

         mComplaintList=new ArrayList<>();
         mRecyclerView = (RecyclerView)rootView.findViewById(R.id.my_recycler_view);

        baseRef= FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id));
         baseRef.child(getString(R.string.complaint_ref)).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 if(dataSnapshot.exists())
                 {
                     for(DataSnapshot d:dataSnapshot.getChildren())
                     {
                         for(DataSnapshot d2:d.getChildren())
                         {
                             mComplaintList.add(d2.getValue(Complaint.class));

                         }


                     }


                 }
                 else Toast.makeText(getContext(),"No Data available",Toast.LENGTH_LONG).show();

                 adapter = new ComplaintAdapter(getContext(), mComplaintList,1);
                 mRecyclerView.setAdapter(adapter);
                 // recyclerView.setHasFixedSize(true);
                 mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                 adapter.notifyDataSetChanged();


             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

         /*
         adapter=new ComplaintAdapter(getContext(),mComplaintList,1);
         mRecyclerView.setAdapter(adapter);
         adapter.notifyDataSetChanged();
         */








         return rootView;
    }

}
