package com.code_base_update.models;

import android.content.Context;

import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.presenters.IScrollingPresenter;
import com.code_base_update.utility.InputHelper;
import com.code_base_update.utility.UserManager;
import com.code_base_update.view.IScrollingView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ScrollerModel implements IScrollingPresenter {

    private IScrollingView<ComplaintBean> mView;
    private int pageSize;
    private Context context;
    private String lastLoadedKey;

    public ScrollerModel(int pageSize, Context context) {
        this.pageSize = pageSize;
        this.context = context;
    }


    @Override
    public void attachView(IScrollingView<ComplaintBean> view) {
        mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void loadFirstBatch() {
        Query ref = DatabaseManager.getBaseRef(context)
                .child(DatabaseManager.COMPLAINT_FOLDER)
                .child(InputHelper.removeDot(new UserManager().getEmail()))
                .orderByChild("timeStamp");
        ref.keepSynced(true);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    ArrayList<ComplaintBean> mList = new ArrayList<>();
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        mList.add(d.getValue(ComplaintBean.class));
                    }
                    lastLoadedKey = mList.get(mList.size() - 1).getComplaintId();
                    mView.firstPageReceived(mList);
                } else
                    mView.noDataFound();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.onErrorOccurred(databaseError.getMessage());
            }
        });


    }

    @Override
    public void loadNextBatch() {
        Query ref = DatabaseManager.getBaseRef(context)
                .child(DatabaseManager.COMPLAINT_FOLDER)
                .child(InputHelper.removeDot(new UserManager().getEmail()))
                .startAt(lastLoadedKey)
                .orderByKey()
                .limitToFirst(pageSize);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<ComplaintBean> mList = new ArrayList<>();
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {

                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        mList.add(d.getValue(ComplaintBean.class));
                    }

                    lastLoadedKey = mList.get(mList.size() - 1).getComplaintId();
                    mView.onNextPageReceived(mList);

                } else {
                    mView.onEndOfListReached();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.onErrorOccurred(databaseError.getMessage());
            }
        });
    }

    @Override
    public void markResolved(ComplaintBean complaintId, SuccessCallback callback) {
        complaintId.setResolved(true);
        complaintId.setResolvedOnDate(Calendar.getInstance().getTime().getTime());
        new DatabaseManager(context).markComplaintAsResolved(complaintId, callback);
    }

}
