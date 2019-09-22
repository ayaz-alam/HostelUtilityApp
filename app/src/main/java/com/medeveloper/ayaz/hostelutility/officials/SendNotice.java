package com.medeveloper.ayaz.hostelutility.officials;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import java.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.CameraUtitlity;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.NoticeClass;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
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
    DatabaseReference baseRef;
    TextView noticeTitle,noticeBody;


    private static final int MY_CAMERA_REQUEST_CODE = 100;
    SweetAlertDialog pDialog;//ProgressDialog
    CameraUtitlity myCamera;
    ImageView removePhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.officials_send_notice, container, false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isPermissionGranted();//Checking the permission
        }
        pDialog=ShowDialog("Sending, please wait..",2);
        noticeTitle=rootView.findViewById(R.id.notice_title);
        noticeBody=rootView.findViewById(R.id.notice_body);
        baseRef= FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id));

        myCamera=new CameraUtitlity(getContext());
        //Camera Button
        (rootView.findViewById(R.id.camera)).setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if(isPermissionGranted())
                dispatchTakePictureIntent(0);

            }
        });

        removePhoto=rootView.findViewById(R.id.cancel_photo_button);
        removePhoto.setVisibility(View.GONE);
        removePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myPhoto!=null)
                    mImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_icon));
                    mImageView.setAlpha(0.5f);
                    (rootView.findViewById(R.id.photo_hint)).setVisibility(View.VISIBLE);
                    (rootView.findViewById(R.id.cancel_photo_button)).setVisibility(View.GONE);
                    myPhoto=null;

            }
        });

        //Open Gallery Button
        (rootView.findViewById(R.id.gallery)).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if(isPermissionGranted())
                dispatchTakePictureIntent(1);

            }
        });

        //Send button
        (rootView.findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


                String Title=noticeTitle.getText().toString();
                String Body=noticeBody.getText().toString();
                if(Title.equals(""))
                    ShowDialog("Please give title for Notice",4).show();
                else if(Body.equals(""))
                    ShowDialog("Notice body can't be empty",4).show();
                else
                    if(isNetworkAvailable())//Checking for internet connection
                if (myPhoto != null)
                {
                    SendPhotoNotice(Title,Body);
                    pDialog.show();
                }
                else
                {
                    pDialog.show();
                    Date currentTime = Calendar.getInstance().getTime();
                    baseRef.child(getString(R.string.notice_ref)).
                            push().setValue(
                                    new NoticeClass(Title, Body,
                                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                            currentTime)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                pDialog.dismiss();
                                noticeBody.setText(null);
                                noticeTitle.setText(null);
                                mImageView.setImageBitmap(null);
                                ShowDialog("Successfully sent",3).setContentText("Notice has been sent successfully").show();

                            }
                        }
                    });
                }
                else
                    ShowDialog("No Internet Connection",4);

            }
        });



        mImageView = rootView.findViewById(R.id.image_notice);
        mImageView.setAlpha(0.5f);//ImageView Reference

        ImageView rotatePhotoButton=rootView.findViewById(R.id.rotate_photo);//Rotate Photo Reference
        rotatePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myPhoto!=null)
                rotatePhoto(myPhoto);
                else ShowDialog("No photo selected",1).show();
            }
        });



        return rootView;
    }

    //When Photo Notice is being sent
    void SendPhotoNotice(final String title, final String body)
    {
        Log.d("Tag","Photo Intent called");
        //Trimming the size of the Image
        Uri ImageUri=getImageUri(getContext(),getResizedBitmap(myPhoto,1800));

        StorageReference storageRef=FirebaseStorage.getInstance().
                getReference(getString(R.string.college_id)).
                child(getString(R.string.hostel_id));
        storageRef.child(ImageUri.getLastPathSegment()).
                putFile(ImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Date currentTime = Calendar.getInstance().getTime();
                        //Creating notice class object to push into the firebase
                        NoticeClass newNotice= new NoticeClass(title,body,
                                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                taskSnapshot.getDownloadUrl().toString(), currentTime);
                        baseRef.child(getString(R.string.notice_ref)).push().setValue(newNotice).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    noticeBody.setText(null);
                                    noticeTitle.setText(null);
                                    mImageView.setImageBitmap(null);
                                    pDialog.dismiss();
                                    ShowDialog("Successfully sent",3).setContentText("Notice has been sent successfully").show();
                                }
                            }
                        });
                    }
                });


    }


    //Generating Uri from Bitmap
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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

    // Function to call ImagePicker and Camera
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

    //Creating a file in temporary source
    String mCurrentPhotoPath;
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

        //This is called when we select a photo or click a photo

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)//Camera Intent Result
        {

            Uri mImageUri=thePhotoURI;
            getActivity().getContentResolver().notifyChange(mImageUri, null);
            ContentResolver cr = getActivity().getContentResolver();
            Bitmap bitmap;
            try {
                bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
                myPhoto=bitmap;
                rotatePhoto(myPhoto);
                removePhoto.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.photo_hint).setVisibility(View.GONE);
                mImageView.setImageBitmap(myPhoto);
                mImageView.setAlpha(1f);

            } catch (Exception e) {
                Toast.makeText(getContext(), "Failed to load", Toast.LENGTH_SHORT).show();

            }



        }
        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)//The Result given by Phot Picker
        {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                rootView.findViewById(R.id.photo_hint).setVisibility(View.GONE);
                removePhoto.setVisibility(View.VISIBLE);
                mImageView.setAlpha(1f);
                mImageView.setImageBitmap(bitmap);
                myPhoto=bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //To Check Permissions
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

    //To check network connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    //Alert Dialog Box

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
            myDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText(msg);
            myDialog.setCancelable(false);
        }
        else if(code==3)
        {
            myDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.SUCCESS_TYPE).setTitleText(msg);
        }
        else if(code==4)
        {
            myDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE).setTitleText(msg);
        }



        return myDialog;
    }




}




