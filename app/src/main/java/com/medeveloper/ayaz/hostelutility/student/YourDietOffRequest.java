package com.medeveloper.ayaz.hostelutility.student;


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
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.MyData;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class YourDietOffRequest extends Fragment {


    private ArrayList<DietOffRequestClass> requestList;
    private RecyclerView mRecyclerView;
    private DatabaseReference mRef;
    private DietOffAdapter adapter;

    public YourDietOffRequest() {
        // Required empty public constructor
    }


    View rootView;
    SweetAlertDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.student_diet_off_requests, container, false);

        pDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setTitleText("Please Wait...");
        pDialog.show();


        requestList=new ArrayList<>();
        mRecyclerView=rootView.findViewById(R.id.my_recycler_view);
        mRef= FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id));

        mRef.child(getString(R.string.diet_off_req_ref)).child(new MyData(getContext()).getData(MyData.ENROLLMENT_NO)).
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                requestList.clear();
                if(dataSnapshot.exists())
                {
                        for(DataSnapshot x:dataSnapshot.getChildren())//Traversing in the main node
                        requestList.add(x.getValue(DietOffRequestClass.class));

                    //Toast.makeText(getContext(),"List Length"+requestList.size(),Toast.LENGTH_SHORT).show();

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new DietOffAdapter(getContext(),requestList,DietOffRequestClass.STUDENT_SIDE);
                    mRecyclerView.setAdapter(adapter);
                    requestList=reverseList(requestList);
                    adapter.notifyDataSetChanged();
                    pDialog.dismiss();

                }
                else {
                    pDialog.dismiss();
                    Toast.makeText(getContext(),"No data found",Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("No data found")
                            .setContentText("It seems that no student have submitted any request yet")
                            .show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        return rootView;
    }

    private ArrayList<DietOffRequestClass> reverseList(ArrayList<DietOffRequestClass> list)
    {
        ArrayList<DietOffRequestClass> tempList=new ArrayList<>(list.size());
        int size=list.size();
        for(int i=0;i<size;i++)
        tempList.add(list.get(size-1-i));
        return tempList;
    }

}
