package com.medeveloper.ayaz.hostelutility.officials;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.icu.text.SimpleDateFormat;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.medeveloper.ayaz.hostelutility.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.io.File;
import java.io.IOException;
import java.util.Date;


import static android.app.Activity.RESULT_OK;

public class SendNotice extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 111;
    private static final int REQUEST_TAKE_PHOTO = 121;
    private Uri thePhotoURI=null;

    public SendNotice() {
        // Required empty public constructor
    }


    View rootView;
    ImageView mImageView;
    Bitmap myPhoto;
    Uri ImageUri = null;


    private static final int MY_CAMERA_REQUEST_CODE = 100;
    SweetAlertDialog pDialog;//ProgressDialog

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.officials_send_notice, container, false);


        isPermissionGranted();//Checking the permission
        pDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Sending, Please wait..");
        pDialog.setCancelable(false);



        //Camera Button
        ((ImageView) rootView.findViewById(R.id.camera)).setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if(isPermissionGranted())
                dispatchTakePictureIntent(0);

            }
        });

        //Open Gallery Button
        ((ImageView) rootView.findViewById(R.id.gallery)).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                rotatePhoto(myPhoto);
                /*
                if(isPermissionGranted())
                dispatchTakePictureIntent(1);
                */
            }
        });

        //Send button
        ((Button) rootView.findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable())
                if (ImageUri != null) {
                    pDialog.show();
                    StorageReference mStorage = FirebaseStorage.getInstance().getReference();
                    mStorage.child("CollegeID/HostelID/").child(ImageUri.getLastPathSegment()).
                            putFile(ImageUri)
                            .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(getContext(), "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                        pDialog.dismiss();
                                        ShowDialog("Successfully Uploaded",3).show();
                                    }
                                    else
                                    {
                                        ShowDialog("Error in sending",1).show();
                                    }

                                }
                            });


                } else
                    Toast.makeText(getContext(), "Please Click/Select Photo First", Toast.LENGTH_SHORT).show();
                else new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE).setTitleText("No internet connection").show();

            }
        });


        mImageView = rootView.findViewById(R.id.image_notice);



        return rootView;
    }

    //Codes to rotate the photo
    void rotatePhoto(Bitmap bitmap)
    {
        Matrix mMatrix = new Matrix();
        Matrix mat=mImageView.getImageMatrix();
        mMatrix.set(mat);
        mMatrix.setRotate(90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), mMatrix, false);
        myPhoto=bitmap;
        mImageView.setImageBitmap(bitmap);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    private void dispatchTakePictureIntent(int Code) {
        if (Code == 0) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(getContext().getApplicationContext(),
                            "com.medeveloper.ayaz.hostelutility",
                            photoFile);
                    thePhotoURI=photoURI;
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }


        } else if (Code == 1) {

            Intent intent = new Intent();
            intent.setType("image/*");// Show only images, no videos or anything else
            intent.setAction(Intent.ACTION_GET_CONTENT);//Always show the chooser (if there are multiple options available)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    }


    String mCurrentPhotoPath;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            Uri mImageUri=thePhotoURI;
            getActivity().getContentResolver().notifyChange(mImageUri, null);
            ContentResolver cr = getActivity().getContentResolver();
            Bitmap bitmap;
            try {
                bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
                mImageView.setImageBitmap(bitmap);
                myPhoto=bitmap;
                ImageUri=thePhotoURI;
            } catch (Exception e) {
                Toast.makeText(getContext(), "Failed to load", Toast.LENGTH_SHORT).show();

            }



        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                mImageView.setImageBitmap(bitmap);
                myPhoto=bitmap;
                ImageUri = uri;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void setPic() {

        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isPermissionGranted() {


        boolean okay = true;

        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            okay = false;
        }

        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                okay = false;
            }
        }

    return okay;
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }



    private SweetAlertDialog ShowDialog(String msg,int code)
    {
        /*
        * code = 0 : Normal Message
        * code = 1 : Error Message
        * code = 3 : ProgressBar
        * code = 4 : Success Dialog
        * */
        SweetAlertDialog myDialog=null;

        if(code==0)
        {
           myDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.NORMAL_TYPE).setTitleText(msg);

        }
        else if(code==1)
        {
            myDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText(msg);

        }
        else if(code==2)
        {
            myDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setTitleText(msg);

        }
        else if(code==3)
        {
            myDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.SUCCESS_TYPE).setTitleText(msg);
        }



        return myDialog;
    }




}




