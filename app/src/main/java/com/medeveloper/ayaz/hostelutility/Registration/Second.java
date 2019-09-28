package com.medeveloper.ayaz.hostelutility.Registration;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.utility.MyData;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;
import com.medeveloper.ayaz.hostelutility.interfaces.onCompletionListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;


public class Second extends Fragment {

    private onCompletionListener callBack;
    private String Uid="null";

    public Second() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            MyData data = new MyData(getActivity());
            Email = data.getData(MyData.MAIL);
            Adhaar = data.getData(MyData.ADHAAR);
            mAuth= FirebaseAuth.getInstance();
    }

    private String Email,Adhaar;
    FirebaseAuth mAuth;
    private String Name,Category,BloodGroup,
            FatherName,Class,Year,Branch,
            RoomNo,MobileNo,WhatsAppContact="NA",FatherContact,
            LocalGuardianNo="NA", CompleteAddress;

    private EditText
            fatherName,roomNo,mobileNo,fatherContact,localGuardianNo,
            address;
    private Spinner category,bloodGroup,year,branch,class_;

    DatabaseReference mRef;
    SweetAlertDialog pDialog;



    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.registration_second, container, false);
        mRef = FirebaseDatabase.getInstance().getReference(getString(R.string.college_id))
        .child(getString(R.string.hostel_id)).child(getString(R.string.student_list_ref));
        initViews();
        Uid = new MyData(getActivity()).getData(MyData.ENROLLMENT_NO);
        return rootView;
    }

    private StudentDetailsClass prepareData() {

        return new StudentDetailsClass(
                Uid,Adhaar,Name,Category,
                BloodGroup,FatherName,Class,
                Year,Branch,RoomNo,MobileNo,Email,FatherContact,
                LocalGuardianNo,CompleteAddress
        );
    }
    void getData()
    {
        Name=new MyData(getActivity()).getName();
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

    }


    private boolean checkDetails() {
        boolean isOkay=true;
//        nameofStudent.setError(null);
        fatherName.setError(null);
        roomNo.setError(null);
        mobileNo.setError(null);
        fatherContact.setError(null);
        localGuardianNo.setError(null);
        address.setError(null);

        if(mobileNo.getText().toString().equals(""))
        {
            mobileNo.setError("Required");
            mobileNo.requestFocus();
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
        else if(fatherName.getText().toString().equals(""))
        {
            fatherName.setError("Required");
            fatherName.requestFocus();
            isOkay=false;
        }
        else if(fatherContact.getText().toString().equals(""))
        {
            fatherContact.setError("Required");
            fatherContact.requestFocus();
            isOkay =false;

        }
        else if(year.getSelectedItem().equals("Year")||branch.getSelectedItem().equals("Branch")||class_.getSelectedItem().equals("Class"))
        {
            Toast.makeText(getContext(),"Please select valid class details",Toast.LENGTH_SHORT).show();
            isOkay=false;
            year.requestFocus();

        }
        else if(roomNo.getText().toString().equals(""))
        {
            roomNo.setError("Required");
            roomNo.requestFocus();
            isOkay=false;
        }
        else if(address.getText().toString().equals(""))
        {
            address.setError("Required");
            address.requestFocus();
            isOkay =false;
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

    }

    public StudentDetailsClass canProceed(onCompletionListener onCompletionListener) {
        callBack = onCompletionListener;
        if(checkDetails())
        {
            getData();
            StudentDetailsClass thisStudent =  prepareData();
            new MyData(getActivity()).savePrefs(MyData.CURRENT_STUDENT,new Gson().toJson(thisStudent));
            callBack.onComplete(true);
        }
        else callBack.onComplete(false);
        return null;
    }
}
