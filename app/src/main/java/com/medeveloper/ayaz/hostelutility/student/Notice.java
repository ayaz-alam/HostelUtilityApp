package com.medeveloper.ayaz.hostelutility.student;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.NoticeClass;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.NoticeClassAdapter;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Notice extends Fragment {


    public Notice() {
        // Required empty public constructor
    }

    View rootView;
    DatabaseReference mRef;
    ArrayList<NoticeClass> noticeList;
    NoticeClassAdapter adapter;
    RecyclerView mRecyclerView;
    SweetAlertDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_with_a_recycler, container, false);

        pDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setTitleText("Please wait..");
        pDialog.show();
        noticeList=new ArrayList<>();
        mRecyclerView=rootView.findViewById(R.id.my_recycler_view);
        mRef= FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id)).child(getString(R.string.notice_ref));
        mRef.keepSynced(true);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    noticeList.clear();
                    for(DataSnapshot d:dataSnapshot.getChildren())
                        noticeList.add(d.getValue(NoticeClass.class));
                    {
                        if(noticeList.size()>0)
                        {
                            (rootView.findViewById(R.id.no_data_found)).setVisibility(View.GONE);
                            (rootView.findViewById(R.id.no_data_found_text)).setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                    //Toast.makeText(getContext(),"List Length"+noticeList.size(),Toast.LENGTH_SHORT).show();
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new NoticeClassAdapter(getContext(),noticeList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    pDialog.dismiss();

                }
                else {
                    mRecyclerView.setVisibility(View.GONE);
                    (rootView.findViewById(R.id.no_data_found)).setVisibility(View.VISIBLE);
                    (rootView.findViewById(R.id.no_data_found_text)).setVisibility(View.VISIBLE);
                    ((TextView)rootView.findViewById(R.id.no_data_found_text)).setText("It seems that there's no notice yet");
                    pDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
    }

}
