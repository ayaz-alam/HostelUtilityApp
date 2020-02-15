package com.code_base_update;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.code_base_update.interfaces.ImageUploadCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ImageHelper {

    public static void saveImageToServer(Uri uri, StorageReference reference, @NonNull final ImageUploadCallback successCallback) {
        successCallback.initiated();
        reference.putFile(uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                successCallback.failed(exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                successCallback.success(taskSnapshot.getDownloadUrl());
            }
        });


    }

}
