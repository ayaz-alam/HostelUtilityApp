package com.code_base_update.models;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.BaseBean;
import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.beans.HostelNoticeBean;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyModel {
    private ArrayList<HostelNoticeBean> posts;

    void addNews(int mPosts) {
        posts = new ArrayList<>();
        Query ref = FirebaseDatabase.getInstance().getReference()
                .child("database")
                .child("posts")
                .orderByChild("date")
                .limitToFirst(mPosts);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                HostelNoticeBean post = dataSnapshot.getValue(HostelNoticeBean.class);
                posts.add(post);
                /*
                todo as this is the only case we've fucked up
                postAdapter.setData(posts);
                postList.setHasFixedSize(true);
                postList.setLayoutManager(layoutManager);
                postList.setAdapter(postAdapter);*/
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }

    void addNewPost(String id){
        Query ref = FirebaseDatabase.getInstance().getReference()
                .child("database")
                .child("post")
                .orderByChild("date")
                .startAt(id)
                .limitToFirst(1);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    HostelNoticeBean post = dataSnapshot.getValue(HostelNoticeBean.class);
                    if(!posts.contains(post)) {
                        posts.add(post);/*
                        postAdapter.setData(posts);
                        postAdapter.notifyDataSetChanged();*/
                    }
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
/**
 * 1. Define mPosts, an integer constant, the number of posts to be viewed at once on the screen.
 *
 * 2. Retrieve the first batch of posts and pass it to the RecyclerView Adapter.
 *
 * 3. Override the onScrolled method of the RecyclerView to load more data upon scrolling the RecyclerView.
 *
 * 4. Use LinearLayoutManager to find the last fully visible item of the list.
 *
 * 5. Load more data just before the last item is to be scrolled.
 *
 * 6. Add the loaded data in the ArrayList of object. And subsequently notify the adapter that the data set has changed.
 *
 * 7. To maintain unique entries, check if the ArrayList has unique objects of class Post. For this, “equals” method must be overridden in the Posts class.
 *
 *
 *
 *
 *
 */

class RefreshAdapter{





}



class PageManager {

    private int pageSize;
    private Context context;
    private String lastLoadedKey=null;

    public PageManager(int pageSize, Context context){
        this.context = context;
        this.pageSize = pageSize;
    }


    public void loadFirstPage(){
        Query ref = DatabaseManager.getBaseRef(context)
                .child("database")
                .child("posts")
                .orderByKey()
                .limitToFirst(pageSize);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ComplaintBean> mList = new ArrayList<>();
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    mList.add(d.getValue(ComplaintBean.class));
                }
                lastLoadedKey =mList.get(mList.size()-1).getComplaintId();
                //TODO inform view about new load
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void loadNextPage(String lastLoadComplaintId){
        Query ref = DatabaseManager.getBaseRef(context)
                .child("database")
                .child("posts")
                .startAt(lastLoadComplaintId)
                .orderByKey()
                .limitToFirst(pageSize);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<ComplaintBean> mList = new ArrayList<>();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        mList.add(d.getValue(ComplaintBean.class));
                        //TODO inform view about new load
                    }
                }else{
                    //Database do not exist any further,update the view accordingly.

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}

