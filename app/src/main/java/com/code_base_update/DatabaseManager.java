package com.code_base_update;

import android.content.Context;

import androidx.annotation.NonNull;

import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.interfaces.DataCallback;
import com.code_base_update.interfaces.SuccessCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseManager {

    private static final String COMPLAINT_FOLDER = "Complaints";
    private static final String COMPLAINT_TYPES = "ComplaintList";
    private DatabaseReference mDatabase;
    private SessionManager session;

    public DatabaseManager(Context context) {
        session = new SessionManager(context);
        mDatabase = FirebaseDatabase.getInstance().getReference(session.getCollegeId()).child(session.getHostelId());
        prepareOfflineAccessLocations();
    }

    private void prepareOfflineAccessLocations(){
        getBaseRef().child(COMPLAINT_TYPES).keepSynced(true);
    }

    public ArrayList<ComplaintBean> loadAllComplaint() {
        ArrayList<ComplaintBean> list =new ArrayList<>();
        for(int i=0;i<10;i++){
            ComplaintBean comp = new ComplaintBean();
            comp.setComplaintId("id_"+i);
            list.add(comp);
        }
        //TODO how to fetch data from mDatabase
        return list;

    }

    public void saveApplication(SuccessCallback callback, ApplicationBean applicationToSave) {
        callback.onInitiated();
        boolean success =true;
        //TODO save to mDatabase
        if(success)
            callback.onSuccess();
        else callback.onFailure("Failed");

    }

    public ArrayList<ApplicationBean> loadAllApplication() {
        ArrayList<ApplicationBean> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new ApplicationBean(i));
        }
        return list;
    }

    public void registerComplaint(final SuccessCallback successCallback, ComplaintBean complaintToRegister) {
        successCallback.onInitiated();
        mDatabase.child(DatabaseManager.COMPLAINT_FOLDER).child(complaintToRegister.getComplaintId()).
                setValue(complaintToRegister).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) successCallback.onSuccess();
                else successCallback.onFailure(task.getException().getLocalizedMessage());

            }
        });

    }

    public void loadComplaintTypes(final DataCallback<ArrayList<String>> dataCallback) {

        final ArrayList<String> types = new ArrayList<>();
        types.add("Select problem");
        getBaseRef().child(COMPLAINT_TYPES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot d:dataSnapshot.getChildren()){
                        types.add(d.getKey());
                    }
                    dataCallback.onSuccess(types);
                }else dataCallback.onFailure("No data found");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataCallback.onError(databaseError.getMessage());
            }
        });
        
    }

    private DatabaseReference getBaseRef() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public void loadSubDomains(String domain,final DataCallback<ArrayList<String>> callback) {

        getBaseRef().child(COMPLAINT_TYPES).child(domain).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ArrayList<String> types=new ArrayList<>();
                    for(DataSnapshot d:dataSnapshot.getChildren())
                        types.add(d.getValue().toString());
                    callback.onSuccess(types);
                }else callback.onFailure("No data found");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }
}
