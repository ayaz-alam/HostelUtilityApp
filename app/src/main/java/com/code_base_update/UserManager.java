package com.code_base_update;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.code_base_update.interfaces.SuccessCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserManager {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    public UserManager(){
        mAuth  = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public String getName(){
        if(user!=null)
            return user.getDisplayName();
        else return "Null";
    }


    public Uri getImageUrl() {
        return user.getPhotoUrl();
    }

    public boolean isUserLoggedIn() {
        return user!=null;
    }

    public void reAuthenticateUser(final SuccessCallback successCallback, String email, String oldPassword,final String newPassword) {
        AuthCredential credential= EmailAuthProvider.getCredential(email,oldPassword);

        mAuth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    mAuth.getCurrentUser().updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                successCallback.onSuccess();
                            }
                            else{
                                successCallback.onFailure(task.getException().getLocalizedMessage());
                            }
                        }
                    });

                }
                else{
                    successCallback.onFailure(task.getException().getLocalizedMessage());
                }

            }
        });


    }
}
