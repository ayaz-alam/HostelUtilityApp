package com.medeveloper.ayaz.hostelutility.Registration;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.MyData;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;
import com.medeveloper.ayaz.hostelutility.interfaces.onCompletionListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class Third extends Fragment {

    private static final String USER_DET = "USER_DETAILS";
    private static final String USER_CODE = "USER_SEG";
    private EditText pass;
    private EditText confirm_pass;
    private onCompletionListener callBack;

    public Third() {
        // Required empty public constructor
    }


    StudentDetailsClass student;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            student=new Gson().
                    fromJson(
                            new MyData(getActivity()).
                            getData(MyData.CURRENT_STUDENT),
                            StudentDetailsClass.class);

    }

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.registration_third, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        pass=rootView.findViewById(R.id.pass_key);
        confirm_pass=rootView.findViewById(R.id.confirm_password);
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
                           callBack.onComplete(false);
                           new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                                   .setTitleText("Registration failed")
                                   .setContentText(""+task.getException().getMessage()).show();
                       }
                        }
                    });

        }
        else
        {
            callBack.onComplete(false);
        }
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
                       callBack.onComplete(false);
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
        callBack.onComplete(true);
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

    public boolean canProceed(onCompletionListener listener) {
        callBack = listener;
        registerUser();
        return false;
    }
}
