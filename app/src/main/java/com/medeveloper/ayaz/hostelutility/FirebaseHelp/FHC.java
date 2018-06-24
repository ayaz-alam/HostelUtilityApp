package com.medeveloper.ayaz.hostelutility.FirebaseHelp;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.medeveloper.ayaz.hostelutility.R;

public class FHC {

    /**
     * Returns the base reference
     * @param context
     * @return
     */
    public static DatabaseReference getBase(Context context)
    {
        return FirebaseDatabase.getInstance().getReference(context.getString(R.string.college_id)).child(context.getString(R.string.hostel_id));
    }
}
