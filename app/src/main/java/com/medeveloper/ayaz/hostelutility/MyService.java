package com.medeveloper.ayaz.hostelutility;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.student.Home;

public class MyService extends Service {
    public MyService() {
    }

    FirebaseAuth mAuth;
    DatabaseReference mRef;
    ChildEventListener noticeListener;
    ValueEventListener dietOffAcceptListener;
    private static final String CHANNEL_ID = "123";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         Toast.makeText(this,"Service started",Toast.LENGTH_SHORT).show();
         mAuth=FirebaseAuth.getInstance();
         DatabaseReference mRef=FirebaseDatabase.getInstance().getReference("https://hostelutilityproject.firebaseio.com/CollegeID/HostelID/Notices");
        initListeners();


        return super.onStartCommand(intent, flags, startId);
    }

    private void initListeners() {
        noticeListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //new Notice added


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {


        Toast.makeText(this,"Service destroyed",Toast.LENGTH_SHORT).show();
        Intent i=new Intent(this,MyService.class);
        startService(i);
        super.onDestroy();
    }

    void buildNot()
    {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setAction("NEW_ACTION");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New Notice")
                .setContentText("Hey, there this is notification send by the hostel app")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(15, mBuilder.build());
    }
}
