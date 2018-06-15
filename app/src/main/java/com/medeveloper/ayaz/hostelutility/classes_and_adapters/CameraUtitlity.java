package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import java.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class CameraUtitlity {
    Context mContext;
    public static final int CAMERA=0;
    public static final int GALLERY=1;
    public Uri photoUri=null;

    public CameraUtitlity(Context context) {
        mContext=context;
    }

    // Function to call ImagePicker and Camera

    public Intent dispatchPhotoIntent(int Code) {
        Intent photoIntent=null;
        if (Code == CAMERA) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(mContext.getApplicationContext(),
                            "com.medeveloper.ayaz.hostelutility",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    this.photoUri=photoURI;
                    photoIntent=takePictureIntent;
                }
            }


        } else if (Code == GALLERY) {

            Intent intent = new Intent();
            intent.setType("image/*");// Show only images, no videos or anything else
            intent.setAction(Intent.ACTION_GET_CONTENT);//Always show the chooser (if there are multiple options available)
            photoIntent=intent;
        }


        return photoIntent;
    }




    //Codes to rotate the photo
    public Bitmap rotatePhoto(Bitmap bitmap, ImageView mImageView)
    {
        Matrix mMatrix = new Matrix();
        Matrix mat=mImageView.getImageMatrix();
        mMatrix.set(mat);
        mMatrix.setRotate(90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), mMatrix, false);
        mImageView.setImageBitmap(bitmap);
        return bitmap;
    }


    //Resizing the photo to upload.
    /**
     * reduces the size of the image
     * @param image
     * @param maxSize
     * @return
     */
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    //Generating Uri from Bitmap
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    //Creating a file in temporary source
    String mCurrentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



}
