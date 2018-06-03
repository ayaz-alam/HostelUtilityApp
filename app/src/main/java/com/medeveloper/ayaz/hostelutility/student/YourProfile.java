package com.medeveloper.ayaz.hostelutility.student;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.ChangePassword;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class
YourProfile extends Fragment {


    public YourProfile() {
        // Required empty public constructor
    }


    View rootView;
    StudentDetailsClass myDetails;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.student_your_profile, container, false);
        FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id)).
                child(getString(R.string.student_list_ref)).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //If user has details in the firebase, then proceed.
                if(dataSnapshot.exists())
                {
                    myDetails=dataSnapshot.getValue(StudentDetailsClass.class);
                    ((TextView)rootView.findViewById(R.id.display_name)).setText(myDetails.Name);
                    ((TextView)rootView.findViewById(R.id.student_hostel)).setText(getString(R.string.hostel_id));
                    ((TextView)rootView.findViewById(R.id.student_room_no)).setText(myDetails.RoomNo);
                    ((TextView)rootView.findViewById(R.id.student_branch)).setText(myDetails.Branch);
                    ((TextView)rootView.findViewById(R.id.enroll_no)).setText(myDetails.EnrollNo);
                    ((TextView)rootView.findViewById(R.id.student_adhar_no)).setText(myDetails.AdhaarNo);
                    ((TextView)rootView.findViewById(R.id.student_phone)).setText(myDetails.MobileNo);
                    ((TextView)rootView.findViewById(R.id.student_email)).setText(myDetails.Email);
                    ((TextView)rootView.findViewById(R.id.father_name)).setText(myDetails.FatherName);
                    ((TextView)rootView.findViewById(R.id.father_contact)).setText(myDetails.FatherContact);
                    ((TextView)rootView.findViewById(R.id.student_category)).setText(myDetails.Category);
                    ((TextView)rootView.findViewById(R.id.student_blood_group)).setText(myDetails.BloodGroup);
                    ((TextView)rootView.findViewById(R.id.student_address)).setText(myDetails.Address);
                    ((TextView)rootView.findViewById(R.id.local_guardian_no)).setText(myDetails.LocalGuardianNo);
                    ((Button)rootView.findViewById(R.id.change_password)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(),ChangePassword.class));
                        }
                    });

                }
                else new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).
                        setTitleText("Can't show your profile\nTry again").show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        return rootView;
    }

}
