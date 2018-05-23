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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StaffAdapter;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StaffDetailsClass;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffDetails extends Fragment {


    private DatabaseReference baseRef;

    public StaffDetails() {
        // Required empty public constructor
    }


    View rootView;
    ArrayList<StaffDetailsClass> staffList;
    RecyclerView mRecyclerView;
    StaffAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.officials_staff_details, container, false);
        staffList=new ArrayList<>();
        mRecyclerView=rootView.findViewById(R.id.my_recycler_view);
        baseRef= FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id));
        /*String Array[]=getResources().getStringArray(R.array.complaint_array);
            for(int i=0;i<Array.length;i++)*/

        baseRef.child(getString(R.string.staff_ref)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    for(DataSnapshot d:dataSnapshot.getChildren())
                     staffList.add(d.getValue(StaffDetailsClass.class));
                else Toast.makeText(getContext(),"No data found "+staffList.size(),Toast.LENGTH_LONG).show();

                adapter=new StaffAdapter(getContext(),staffList);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return rootView;
    }

}
