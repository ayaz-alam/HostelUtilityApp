package com.medeveloper.ayaz.hostelutility.Registration;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;


public class Second extends Fragment {

    private static final String EMAIL = "EMAIL";
    private static final String ADHAAR = "ADHAAR_NO";
    private static final String UID = "USER_ID";
    private static final String USER_CODE = "USER_SEG";
    private OnFragmentInteractionListener mListener;
    private Button submit;

    public Second() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param adhaar Parameter 1.
     * @param User_ID Parameter 2.
     * @param email Parameter 3.
     * @return A new instance of fragment Second.
     */
    // TODO: Rename and change types and number of parameters
    public static Second newInstance(String adhaar, String User_ID,String email,int userCode) {
        Second fragment = new Second();
        Bundle args = new Bundle();
        args.putString(ADHAAR,adhaar);
        args.putString(UID,User_ID);
        args.putString(EMAIL,email);
        args.putInt(USER_CODE,userCode);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Email = getArguments().getString(EMAIL);
            Adhaar = getArguments().getString(ADHAAR);
            Uid=getArguments().getString(UID);
            UserCode=getArguments().getInt(USER_CODE);
        }

    }

    private String Email,Adhaar,Uid;
    private int UserCode;

    private String Name,Category,BloodGroup,
            FatherName,Class,Year,Branch,
            RoomNo,MobileNo,WhatsAppContact="NA",FatherContact,
            LocalGuardianNo="NA", CompleteAddress;

    private EditText nameofStudent,
            fatherName,roomNo,mobileNo,fatherContact,localGuardianNo,
            address;
    private Spinner category,bloodGroup,year,branch,class_;

    DatabaseReference mRef;
    DataSnapshot studentList;
    private Button Submit;
    SweetAlertDialog pDialog;



    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.registration_second, container, false);
        initViews();




        return rootView;
    }

    private StudentDetailsClass prepareData() {
        final StudentDetailsClass thisStudent=new StudentDetailsClass(
                Uid,Adhaar,Name,Category,
                BloodGroup,FatherName,Class,
                Year,Branch,RoomNo,MobileNo,Email,FatherContact,
                LocalGuardianNo,CompleteAddress
        );

        return thisStudent;
    }
    void getData()
    {
        Name=nameofStudent.getText().toString();
        Category=category.getSelectedItem().toString();
        BloodGroup=bloodGroup.getSelectedItem().toString();
        FatherName=fatherName.getText().toString();
        Class=class_.getSelectedItem().toString();
        Year=year.getSelectedItem().toString();
        Branch=branch.getSelectedItem().toString();
        RoomNo=roomNo.getText().toString();
        MobileNo=mobileNo.getText().toString();
        FatherContact=fatherContact.getText().toString();
        LocalGuardianNo=localGuardianNo.getText().toString();
        if(LocalGuardianNo.equals(""))
            LocalGuardianNo="NA";
        CompleteAddress =address.getText().toString();
    }

    private void initViews()
    {

        nameofStudent=rootView.findViewById(R.id.your_name);
        //Spinners
        category=rootView.findViewById(R.id.student_category);
        bloodGroup=rootView.findViewById(R.id.student_blood_group);
        year=rootView.findViewById(R.id.year);
        branch=rootView.findViewById(R.id.branch);
        class_=rootView.findViewById(R.id.class_);
        pDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Please wait..").setContentText("Please wait while we register you").setCancelable(false);
        fatherName=rootView.findViewById(R.id.student_father);
        roomNo=rootView.findViewById(R.id.room_no);
        mobileNo=rootView.findViewById(R.id.mobile_no);
        fatherContact=rootView.findViewById(R.id.father_contact);
        localGuardianNo=rootView.findViewById(R.id.local_guardian_no);
        address=rootView.findViewById(R.id.permanent_address);
        submit =rootView.findViewById(R.id.reg_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                if(checkDetails())
                {
                    Third thirdStep=Third.newInstance(prepareData(),UserCode);
                    FragmentTransaction fn=getActivity().getSupportFragmentManager().beginTransaction();
                    fn.replace(R.id.fragment_layout, thirdStep, "Second").commit();
                }
            }
        });

    }


    private boolean checkDetails() {
        boolean isOkay=true;
        nameofStudent.setError(null);
        fatherName.setError(null);
        roomNo.setError(null);
        mobileNo.setError(null);
        fatherContact.setError(null);
        localGuardianNo.setError(null);
        address.setError(null);
        nameofStudent.setError(null);

        if(Name.equals("")) {
            nameofStudent.setError("Required");
            nameofStudent.requestFocus();
            isOkay = false;
        }
        else if(fatherName.getText().toString().equals(""))
        {
            fatherName.setError("Required");
            fatherName.requestFocus();
            isOkay=false;
        }
        else if(mobileNo.getText().toString().equals(""))
        {
            mobileNo.setError("Required");
            mobileNo.requestFocus();
            isOkay=false;

        }
        else if(roomNo.getText().toString().equals(""))
        {
            roomNo.setError("Required");
            roomNo.requestFocus();
            isOkay=false;
        }
        else if(category.getSelectedItem().equals("Select Category"))
        {
            Toast.makeText(getContext(),"Please select category",Toast.LENGTH_SHORT).show();
            isOkay=false;
            category.requestFocus();
        }
        else if(bloodGroup.getSelectedItem().equals("Select Blood Group"))
        {
            Toast.makeText(getContext(),"Please select blood group",Toast.LENGTH_SHORT).show();
            isOkay=false;
            bloodGroup.requestFocus();
        }
        else if(year.getSelectedItem().equals("Year")||branch.getSelectedItem().equals("Branch")||class_.getSelectedItem().equals("Class"))
        {
            Toast.makeText(getContext(),"Please select valid class details",Toast.LENGTH_SHORT).show();
            isOkay=false;
            year.requestFocus();

        }
        return isOkay;
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
