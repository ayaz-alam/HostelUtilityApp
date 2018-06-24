package com.medeveloper.ayaz.hostelutility.Registration;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.MyData;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;
import com.medeveloper.ayaz.hostelutility.student.Home;
import com.medeveloper.ayaz.hostelutility.student.StudentForm;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class Third extends Fragment {

    private static final String USER_DET = "USER_DETAILS";
    private static final String USER_CODE = "USER_SEG";
    private OnFragmentInteractionListener mListener;
    private int UserCode;
    private EditText pass;
    private EditText confirm_pass;
    private Button submit;

    public Third() {
        // Required empty public constructor
    }

    public static Third newInstance(StudentDetailsClass student,int userCode) {
        Third fragment = new Third();
        Bundle args = new Bundle();
        args.putSerializable(USER_DET,student);
        args.putInt(USER_CODE,userCode);
        fragment.setArguments(args);
        return fragment;
    }

    StudentDetailsClass student;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            student=(StudentDetailsClass)getArguments().getSerializable(USER_DET);
            UserCode=getArguments().getInt(USER_CODE);
        }

    }

    View rootView;
    SweetAlertDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.registration_third, container, false);
        initView();




        return rootView;
    }

    private void initView() {
        pDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setTitleText("Please wait...");
        pass=rootView.findViewById(R.id.pass_key);
        confirm_pass=rootView.findViewById(R.id.confirm_pass_key);
        submit=rootView.findViewById(R.id.reg_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.show();
                registerUser();
            }
        });

    }

    private void registerUser() {
        if(fieldOkay())
        {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(student.Email,pass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful())
                       {
                           setUpUser();
                       }
                       else {
                           pDialog.dismiss();
                           new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                                   .setTitleText("Registration failed")
                                   .setContentText(""+task.getException().getMessage()).show();
                       }
                        }
                    });

        }
        else
            pDialog.dismiss();
    }

    private void setUpUser() {
        FirebaseDatabase.getInstance().getReference(getContext().getString(R.string.college_id))
                .child(getContext().getString(R.string.hostel_id))
                .child(getContext().getString(R.string.student_list_ref))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(student)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful())
                   {
                       createUser();
                   }
                   else
                   {
                       pDialog.dismiss();
                   }
                    }
                });




    }

    /**
     * Create user
     */
    private void createUser()
    {
        /**
         * Saving some data in the SharedPrefrences so that they can Accessed easily
         * For now, saving only HostelID,EnrollmentNo,Name and RoomNo
         * */
        new MyData(getContext()).saveStudentPrefs(student);
        createCredentials(student.Name);

        pDialog.dismiss();
        final SweetAlertDialog d=new SweetAlertDialog(getContext(),SweetAlertDialog.SUCCESS_TYPE).setTitleText("Successfully Registered").
                setContentText("Please add profile photo once you hopp in");
        d.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                d.dismiss();
                sweetAlertDialog.dismiss();
                startActivity(new Intent(getActivity(),Home.class));
                getActivity().finishAffinity();
            }
        });
        d.show();
    }

    private boolean fieldOkay() {
        boolean isOkay=true;
        pass.setError(null);
        confirm_pass.setError(null);

        if(pass.getText().toString().equals(""))
        {
            confirm_pass.setError("Required");
            confirm_pass.requestFocus();
            isOkay=false;
        }
        else if(pass.getText().length()<6)
        {
            confirm_pass.setError("6 digit passkey");
            confirm_pass.requestFocus();
            isOkay=false;
        }
        else if(confirm_pass.getText().toString().equals(""))
        {
            confirm_pass.setError("Required");
            confirm_pass.requestFocus();
            isOkay=false;
        }
        if(!confirm_pass.getText().toString().equals(pass.getText().toString()))
        {
            confirm_pass.setError("Not Same");
            confirm_pass.requestFocus();
            isOkay=false;
        }
        return isOkay;
    }

    private void createCredentials(String Name) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(Name).build();
            user.updateProfile(profileUpdates);
            new MyData(getContext()).setFirstTimeUser(true);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
