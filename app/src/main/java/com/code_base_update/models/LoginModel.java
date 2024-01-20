package com.code_base_update.models;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.CollegeBean;
import com.code_base_update.beans.HostelBean;
import com.code_base_update.beans.Student;
import com.code_base_update.interfaces.DataCallback;
import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.presenters.ILoginPresenter;
import com.code_base_update.ui.RegistrationActivity;
import com.code_base_update.ui.dialogs.ForgotPasswordDialog;
import com.code_base_update.utility.SessionManager;
import com.code_base_update.utility.UserManager;
import com.code_base_update.view.ILoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.code_base_update.Constants.STUDENT;
import static com.code_base_update.Constants.TEACHER;

public class LoginModel implements ILoginPresenter {

    private ILoginView loginView;
    private FirebaseAuth mAuth;
    private Context context;

    public LoginModel(Context context){
        this.context = context;
        mAuth =FirebaseAuth.getInstance();
    }

    @Override
    public void performLogin(String username, String password, int userType) {
        loginView.onLoginInitiated();
        if (userType==STUDENT)
            mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    fetchDetails(context);
                else
                    loginView.onBadCredential(task.getException().getMessage());
            }
        });
        else if(userType==TEACHER){
            mAuth.signInWithEmailAndPassword(username,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                if(mAuth.getCurrentUser()!=null)
                                    verifyOfficial(mAuth.getCurrentUser().getEmail(),mAuth.getCurrentUser().getUid());
                                else
                                    loginView.onBadCredential("Couldn't log you in please check your credetials and Internet");
                            }else
                                loginView.onLoginFailure(task.getException().getLocalizedMessage());
                        }
                    });
        }else
            loginView.onLoginFailure("Failed");

    }

    private void verifyOfficial(String email, String uid) {

        new DatabaseManager(context).verifyOfficial(email, uid, new SuccessCallback() {
            @Override
            public void onInitiated() {

            }

            @Override
            public void onSuccess() {
                loginView.onLoginSuccess(TEACHER);
                new UserManager().setUserType(TEACHER,context);
                new UserManager().setMVHostel(context);
            }

            @Override
            public void onFailure(String msg) {
                loginView.onLoginFailure(msg);
            }
        });
    }

    private void fetchDetails(final Context context){
        String email = mAuth.getCurrentUser().getEmail();

        final DatabaseManager database = new DatabaseManager(context);
        database.fetchStudent(email, new DataCallback<Student>() {
            @Override
            public void onSuccess(Student student) {
                SessionManager sessionManager = new SessionManager(context);
                saveHostelAndCollege(student,database,sessionManager);
            }

            @Override
            public void onFailure(String msg) {
                loginView.onLoginFailure(msg);
            }

            @Override
            public void onError(String msg) {
                loginView.onLoginFailure(msg);
            }
        });

    }

    private void saveHostelAndCollege(final Student student, DatabaseManager database, final SessionManager sessionManager) {
        database.getCollege(student.getCollegeId(),new DataCallback<CollegeBean>(){
            @Override
            public void onSuccess(CollegeBean collegeBean) {
                sessionManager.saveStudent(student);
                sessionManager.setCollege(collegeBean);
                for(HostelBean hostel: collegeBean.getHostels()){
                    sessionManager.setHostel(hostel);
                }
                loginView.onLoginSuccess(STUDENT);
                new UserManager().setUserType(STUDENT,context);
            }

            @Override
            public void onFailure(String msg) {
                loginView.onLoginFailure(msg);
            }

            @Override
            public void onError(String msg) {
                loginView.onLoginFailure(msg);
            }
        });

    }


    @Override
    public void startSignUpActivity(Context mCtx) {
        mCtx.startActivity(new Intent(mCtx, RegistrationActivity.class));
    }

    @Override
    public void startForgotPassWordDialog(Context mCtx) {
        ForgotPasswordDialog dialog = new ForgotPasswordDialog(mCtx);
        dialog.show();
    }

    @Override
    public void attachView(ILoginView view) {
        this.loginView = view;
    }
    @Override
    public void detachView() {
        this.loginView =null;
    }
}
