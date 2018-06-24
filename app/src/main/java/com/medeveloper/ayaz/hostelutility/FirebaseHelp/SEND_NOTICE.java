package com.medeveloper.ayaz.hostelutility.FirebaseHelp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.MyData;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.NoticeClass;

import java.util.Calendar;
import java.util.Date;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import tyrantgit.explosionfield.Utils;

public class SEND_NOTICE extends AsyncTask {

    private static final String CHANNEL_ID = "Send Notice";
    private static final CharSequence NOTICE = "Notice notification";
    private static final String NOTICE_DES = "This displays notice notification";
    private Context mContext;
    private Uri ImageUri;
    private String title;
    private String body;

    NotificationCompat.Builder mBuilder;
    NotificationManagerCompat notificationManager;
    private int notificationId=122344;

    public SEND_NOTICE(Context mContext, Uri ImageUri, String title, String body) {
        this.mContext = mContext;
        this.ImageUri=ImageUri;
        this.title=title;
        this.body=body;
    }


    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        // When done, update the notification one more time to remove the progress bar

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(mContext,"Sending notice",Toast.LENGTH_SHORT).show();
        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(mContext);
        mBuilder = new NotificationCompat.Builder(mContext, CHANNEL_ID);
        mBuilder.setContentTitle("Sending notice")
                .setContentText("Notice is being sent")
                .setSmallIcon(R.drawable.ic_main_logo)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        // Issue the initial notification with zero progress
        int PROGRESS_MAX = 100;
        int PROGRESS_CURRENT = 0;
        mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
        notificationManager.notify(122344, mBuilder.build());
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        StorageReference storageRef= FirebaseStorage.getInstance().
                getReference(mContext.getString(R.string.college_id)).
                child(mContext.getString(R.string.hostel_id));
        storageRef.child(ImageUri.getLastPathSegment()).
                putFile(ImageUri)
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        mBuilder.setProgress(0,0,true);
                        notificationManager.notify(notificationId, mBuilder.build());
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {




                        Date currentTime = Calendar.getInstance().getTime();
                        //Creating notice class object to push into the firebase
                        NoticeClass newNotice= new NoticeClass(title,body,
                                new MyData(mContext).getData(MyData.NAME),
                                taskSnapshot.getDownloadUrl().toString(), currentTime);
                        FHC.getBase(mContext).child(mContext.getString(R.string.notice_ref)).push().setValue(newNotice).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    mBuilder.setContentTitle("Completed")
                                            .setContentText("Notice sent")
                                            .setProgress(0,0,false);
                                    notificationManager.notify(notificationId, mBuilder.build());
                                    Toast.makeText(mContext,"Notice sent",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
        return null;
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name =NOTICE;
            String description = NOTICE_DES;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
