package com.code_base_update.utility;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;

import com.code_base_update.beans.CollegeBean;
import com.code_base_update.beans.HostelBean;
import com.code_base_update.beans.Student;
import com.code_base_update.interfaces.SuccessCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;

import static com.code_base_update.Constants.STUDENT;

public class UserManager {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public UserManager() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public String getName() {
        if (user != null)
            return user.getDisplayName();
        else return "Null";
    }


    public String getImageUrl() {
        if (user.getPhotoUrl() != null)
            return user.getPhotoUrl().toString();
        else return "";
    }

    public boolean isUserLoggedIn() {
        return user != null;
    }

    public void reAuthenticateUser(final SuccessCallback successCallback, String email, String oldPassword, final String newPassword) {
        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);

        mAuth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    mAuth.getCurrentUser().updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                successCallback.onSuccess();
                            } else {
                                successCallback.onFailure(task.getException().getLocalizedMessage());
                            }
                        }
                    });

                } else {
                    successCallback.onFailure(task.getException().getLocalizedMessage());
                }

            }
        });


    }

    public void logout(Context context) {
        mAuth.signOut();
        new SessionManager(context).clear();
    }

    public String getEmail() {
        return mAuth.getCurrentUser().getEmail();
    }

    public void createUser(final Student studentDetails, final SuccessCallback callback) {
        callback.onInitiated();

        mAuth.createUserWithEmailAndPassword(studentDetails.getEmail(), studentDetails.getMobileNo()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(studentDetails.getName())
                            .build();
                    mAuth.getCurrentUser().updateProfile(profileUpdates);
                    callback.onSuccess();
                }
                else callback.onFailure(task.getException().getLocalizedMessage());

            }
        });
    }

    public void setStudent(Student student,Context context) {
        new SessionManager(context).saveStudent(student);
    }

    public void changeImage(Uri resultUri, OnCompleteListener<Void> callback) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(resultUri).build();
        user.updateProfile(profileUpdates).addOnCompleteListener(callback);
    }

    public String getUID() {
       return InputHelper.removeDot(getEmail());
    }

    public String getFacultyName() {
        return "";
    }

    public String getFacultyId() {
        return "";
    }

    public void setUserType(int userType,Context context){
        new SessionManager(context).setUserType(userType);

    }

    public int getUserType(Context context) {
        return new SessionManager(context).getUserType();
    }

    public void setMVHostel(Context context){
        SessionManager sessionManager  =new SessionManager(context);
        CollegeBean collegeBean =new CollegeBean();
        collegeBean.setCollegeId("College_id_1234");
        collegeBean.setCollegeName("CTAE,Udaipur");
        ArrayList<HostelBean> hostelList= new ArrayList<>();
        HostelBean bean = new HostelBean();
        bean.setHostelId("h_no_1");
        bean.setHostelName("MV Hostel");
        hostelList.add(bean);
        collegeBean.setHostels(hostelList);
        sessionManager.setCollege(collegeBean);
        sessionManager.setHostel(bean);
        sessionManager.setHostelId(bean.getHostelId());
        sessionManager.setCollegeId(collegeBean.getCollegeId());
    }
}
