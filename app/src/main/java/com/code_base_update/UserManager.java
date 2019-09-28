package com.code_base_update;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserManager {

    FirebaseAuth mAuth;
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
}
