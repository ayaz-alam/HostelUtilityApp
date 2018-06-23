package com.medeveloper.ayaz.hostelutility.Registration;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class First extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Button submit;

    public First() {
        // Required empty public constructor
    }

    public static First newInstance(String param1, String param2) {
        First fragment = new First();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        */
    }

    View rootView;
    EditText adhaarNumber,uniqueId,email;
    //RadioGroup regRadioGroup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.registration_first, container, false);

        /**
         * In this we check whether the student exists or not
         * @params adhaarNumber,uniqueId,email
         * */
        initViews();

        return rootView;
    }

    public int User=Registration.STUDENT;
    private void initViews() {
        adhaarNumber=rootView.findViewById(R.id.adhaar_no);
        uniqueId=rootView.findViewById(R.id.unique_id);
        email=rootView.findViewById(R.id.email_address);
       // regRadioGroup=rootView.findViewById(R.id.signup_radio);
        submit=rootView.findViewById(R.id.reg_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fieldOkay())
                    tryTOAuthenticate(User);
            }
        });
/*
        regRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.student_radio)
                {
                    User=Registration.STUDENT;
                    uniqueId.setHint(R.string.enroll_hint);
                }
                else if(checkedId==R.id.teacher_radio)
                {
                   User=Registration.EMPLOYEE;
                    uniqueId.setHint(R.string.empId_hint);
                }
            }
        });
        */
    }

    boolean validUser=false;
    SweetAlertDialog pDialog;
    private boolean tryTOAuthenticate(final int Code) {
        pDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Please wait...")
                .setContentText("While we check details in database");
        pDialog.show();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference(getString(R.string.college_id));
        if(Registration.EMPLOYEE==Code)
            mRef=mRef.child(getString(R.string.officials_id_ref)).child(uniqueId.getText().toString());
        else if(Code==Registration.STUDENT)
            mRef=mRef.child(getString(R.string.hostel_id)).child(getString(R.string.enroll_student_list_ref)).child(uniqueId.getText().toString());

        final String ID_1=adhaarNumber.getText().toString();
        final String ID_2=uniqueId.getText().toString();
        final String ID_3=email.getText().toString();
        //TODO remove when -/database rule is set
       // FirebaseAuth.getInstance().signInAnonymously();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    if(Code==Registration.STUDENT)
                    {
                        if(ID_2.equals(dataSnapshot.getKey()))
                        if(ID_1.equals(dataSnapshot.child("AdhaarNo").getValue()))
                            validUser=true;
                        if(validUser)
                        {
                            pDialog.dismiss();
                            Registration.FLAG_STEP_NUMBER++;
                            Second second=Second.newInstance(ID_1,ID_2,ID_3,Registration.STUDENT);
                            FragmentManager fn = getActivity().getSupportFragmentManager();
                            fn.beginTransaction().replace(R.id.fragment_layout, second, "Second").commit();
                        }
                        else
                        {
                            pDialog.dismiss();
                            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Ooops..")
                                    .setContentText("It seems that you don't exist\nObviously in our database\nWhat you can do" +
                                            " is try with correct data or contact admin").show();
                        }

                    }
                    else if(Code==Registration.EMPLOYEE)
                    {

                        //TODO generate a base ref for the employee
                        /*
                        if(ID_2.equals(dataSnapshot.getKey()))
                            if(ID_1.equals(dataSnapshot.child("AdhaarNo").getValue()))
                                validUser=true;
                        if(validUser)
                        {
                            pDialog.dismiss();
                            Registration.FLAG_STEP_NUMBER++;
                            FragmentManager fn = getActivity().getSupportFragmentManager();
                            Second second=new Second();
                            fn.beginTransaction().replace(R.id.fragment_layout, new Second(), "Second").commit();
                        }
                        else
                        {
                            pDialog.dismiss();
                            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Ooops..")
                                    .setContentText("It seems that you don't exist\nObviously in our database\nWhat you can do" +
                                            "is try with correct data or contact admin").show();
                        }
                        */
                    }

                }
                else
                {
                    pDialog.dismiss();
                    new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Ooops..")
                            .setContentText("It seems that you don't exist..\nObviously in our database\nWhat you can do" +
                                    "is try with correct data or contact admin").show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return validUser;
    }


    private boolean fieldOkay()
    {
        boolean isOkay=true;
        adhaarNumber.setError(null);
        email.setError(null);
        uniqueId.setError(null);
        if(adhaarNumber.equals(""))
        {
            adhaarNumber.setError("Required");
            adhaarNumber.requestFocus();
            isOkay=false;
        }
        else if(adhaarNumber.length()<12)
        {
            adhaarNumber.setError("Inavlid adhaar number");
            adhaarNumber.requestFocus();
            isOkay=false;
        }
        else if(uniqueId.equals(""))
        {
            uniqueId.setError("Required");
            uniqueId.requestFocus();
            isOkay=false;
        }
        else if(email.getText().toString().equals(""))
        {
            email.setError("Required");
            email.requestFocus();
            isOkay=false;
        }
        else if(!(email.getText().toString().contains("@")||email.getText().toString().contains(".com")))
        {
            email.setError("Invalid email");
            email.requestFocus();
            isOkay=false;
        }
        return isOkay;
    }





    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
