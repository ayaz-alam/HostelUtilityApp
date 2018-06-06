package com.medeveloper.ayaz.hostelutility.officials;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StaffAdapter;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StaffDetailsClass;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentListAdapter;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentList extends Fragment {


    private SweetAlertDialog pDialog;
    private ArrayList<StudentDetailsClass> studentList;
    private RecyclerView mRecyclerView;
    private StudentListAdapter adapter;

    public StudentList() {
        // Required empty public constructor
    }


    View rootView;
    DatabaseReference mRef;
    TextView defaultText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.officials_student_list, container, false);
        defaultText=rootView.findViewById(R.id.default_text);


        pDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setTitleText("Loading...");
        pDialog.show();
        studentList =new ArrayList<>();
        mRecyclerView=rootView.findViewById(R.id.my_recycler_view);
        mRef= FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id));

        mRef.child(getString(R.string.student_list_ref)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentList.clear();
                if(dataSnapshot.exists())
                {
                    defaultText.setVisibility(View.GONE);
                    for(DataSnapshot d:dataSnapshot.getChildren())
                        studentList.add(d.getValue(StudentDetailsClass.class));

                    pDialog.dismiss();

                    adapter=new StudentListAdapter(getContext(), studentList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else Toast.makeText(getContext(),"No data found "+ studentList.size(),Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return rootView;
    }

}
