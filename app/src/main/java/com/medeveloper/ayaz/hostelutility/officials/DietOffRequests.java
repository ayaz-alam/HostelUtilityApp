package com.medeveloper.ayaz.hostelutility.officials;


import android.os.Bundle;
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
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.DietOffAdapter;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.DietOffRequestClass;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.NoticeClass;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.NoticeClassAdapter;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DietOffRequests extends Fragment {


    private ArrayList<DietOffRequestClass> requestList;
    private RecyclerView mRecyclerView;
    private DatabaseReference mRef;
    private DietOffAdapter adapter;

    public DietOffRequests() {
        // Required empty public constructor
    }


    View rootView;
    SweetAlertDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.officials_diet_off_requests, container, false);

        pDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setTitleText("Please Wait...");
        pDialog.show();


        requestList=new ArrayList<>();
        mRecyclerView=rootView.findViewById(R.id.my_recycler_view);
        mRef= FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id));

        mRef.child(getString(R.string.diet_off_complaints_ref)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    for(DataSnapshot d:dataSnapshot.getChildren())
                        requestList.add(d.getValue(DietOffRequestClass.class));
                    //Toast.makeText(getContext(),"List Length"+requestList.size(),Toast.LENGTH_SHORT).show();
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new DietOffAdapter(getContext(),requestList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    pDialog.dismiss();

                }
                else Toast.makeText(getContext(),"No data found",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        return rootView;
    }

}
