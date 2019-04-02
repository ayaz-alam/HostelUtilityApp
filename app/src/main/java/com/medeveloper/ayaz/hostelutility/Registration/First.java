package com.medeveloper.ayaz.hostelutility.Registration;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.MyData;

public class First extends Fragment {

    private OnFragmentInteractionListener mListener;

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
    RadioGroup regRadioGroup;
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
        regRadioGroup=rootView.findViewById(R.id.signup_radio);

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
    }

    /**
     * This function is triggered by button on the parent activity
     * which communicate with the fragment
     * @return
     */
    public boolean canProceed()
    {
        boolean okay=false;
        if(fieldOkay())
        {
            if(tryTOAuthenticate(User))// if user exists in database
            {
                okay=true;
            }
            else // if user is not present in the database
            {

            }
        }

        return okay;
    }

    boolean validUser=false;
    private boolean tryTOAuthenticate(final int Code) {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference(getString(R.string.college_id));

        if(Registration.EMPLOYEE==Code)
            mRef=mRef.child(getString(R.string.officials_id_ref)).child(uniqueId.getText().toString());
        else if(Code==Registration.STUDENT)
            mRef=mRef.child(getString(R.string.hostel_id)).child(getString(R.string.enroll_student_list_ref)).child(uniqueId.getText().toString());

        final String ID_1=adhaarNumber.getText().toString();
        final String ID_2=uniqueId.getText().toString();
        final String ID_3=email.getText().toString();
        //TODO remove when database rule is set
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
                        {
                            MyData data = new MyData(getActivity());
                            data.savePrefs(MyData.MAIL,ID_3);
                            data.savePrefs(MyData.ADHAAR,ID_2);
                            data.savePrefs(MyData.ENROLLMENT_NO,ID_1);
                            validUser=true;

                        }
                    }
                    else if(Code==Registration.EMPLOYEE)
                    {
                        //TODO generate a base ref for the employee
                        if(ID_2.equals(dataSnapshot.getKey()))
                            if(ID_1.equals(dataSnapshot.child("AdhaarNo").getValue()))
                            {
                                //TODO save the instance of data
                            /*    MyData data = new MyData(getActivity());
                                data.savePrefs(MyData.MAIL,ID_3);
                                data.savePrefs(MyData.ADHAAR,ID_2);
                                data.savePrefs(MyData.EMPLOYEE_ID,ID_1);*/
                                validUser=true;
                            }
                    }

                }
                else
                {
                    showDialog(0);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return validUser;
    }

    private void showDialog(int i) {
        if(i==0)
        {
            //Error

        }

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
