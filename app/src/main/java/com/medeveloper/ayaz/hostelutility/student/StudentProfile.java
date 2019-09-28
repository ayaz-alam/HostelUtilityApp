package com.medeveloper.ayaz.hostelutility.student;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.gson.Gson;
import com.medeveloper.ayaz.hostelutility.ChangePassword;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.utility.CameraUtitlity;
import com.medeveloper.ayaz.hostelutility.utility.CircularTransform;
import com.medeveloper.ayaz.hostelutility.utility.MyData;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class
StudentProfile extends Fragment {


    private static final int PICK_IMAGE_FROM_GALLERY = 121;
    private int TAKE_IMAGE_FROM_CAMERA=212;
    private Bitmap myPhoto=null;

    public StudentProfile() {
        // Required empty public constructor
    }


    View rootView;
    StudentDetailsClass myDetails;
    SweetAlertDialog dialogForImageChange;
    CameraUtitlity cameraUtitlity;
    ImageView displayImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.profile, container, false);


        setUpDialogForImageChange();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        displayImage=(ImageView)rootView.findViewById(R.id.display_image);
        final ImageView changePhoto=rootView.findViewById(R.id.edit_photo);
        cameraUtitlity=new CameraUtitlity(getActivity());
        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForImageChange.show();
            }
        });
        displayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialogForImageChange.show();
            }
        });

        setUpUserData();

        Picasso.get().
                load(user.getPhotoUrl())
                .placeholder(getContext().getDrawable(R.drawable.ic_boy))
                .centerCrop()
                .transform(new CircularTransform())
                .fit()
                .into(displayImage);

        return rootView;
    }

    private void setUpUserData() {

        myDetails = new Gson().fromJson(new MyData(getContext()).getData(MyData.CURRENT_STUDENT),StudentDetailsClass.class);
        if(myDetails!=null) {
            ((TextView) rootView.findViewById(R.id.name)).setText(myDetails.Name);
            ((TextView) rootView.findViewById(R.id.hostel_name)).setText(getString(R.string.hostel_id));
            ((TextView) rootView.findViewById(R.id.room)).setText("Room No: " + myDetails.RoomNo);
            ((TextView) rootView.findViewById(R.id.year_and_branch)).setText("Class: " + myDetails.Branch + " " + myDetails.Year);
            ((TextView) rootView.findViewById(R.id.phone)).setText(myDetails.MobileNo);
            ((TextView) rootView.findViewById(R.id.mail)).setText(myDetails.Email);
            ((TextView) rootView.findViewById(R.id.address)).setText(myDetails.Address);
            (rootView.findViewById(R.id.change_password)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), ChangePassword.class));
                }
            });
        }else new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).
                setTitleText("Can't show your profile\nTry again").show();
    }

    private void setUpDialogForImageChange() {
        dialogForImageChange=new SweetAlertDialog(getContext(),
                SweetAlertDialog.NORMAL_TYPE)
                .setCustomImage(R.drawable.ic_add_a_photo)
                .setTitleText("Change photo")
                .setContentText("Choose from gallery or click a new one!!")
                .setConfirmText("Gallery")
                .setCancelText("Camera")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        startActivityForResult(cameraUtitlity.dispatchPhotoIntent(CameraUtitlity.GALLERY),PICK_IMAGE_FROM_GALLERY);
                        //From Gallery
                        dialogForImageChange.dismiss();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        //From Camera

                        startActivityForResult(cameraUtitlity.dispatchPhotoIntent(CameraUtitlity.CAMERA),TAKE_IMAGE_FROM_CAMERA);
                        dialogForImageChange.dismiss();
                    }
                });
              dialogForImageChange.setCancelable(true);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //This is called when we select a photo or click a photo

        if (requestCode == TAKE_IMAGE_FROM_CAMERA && resultCode == RESULT_OK)//Camera Intent Result
        {

            Uri mImageUri=cameraUtitlity.photoUri;
            getActivity().getContentResolver().notifyChange(mImageUri, null);
            ContentResolver cr = getActivity().getContentResolver();
            Bitmap bitmap;
            try {
                bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
                myPhoto=cameraUtitlity.getResizedBitmap(bitmap,500);
                UpdateUserPhoto();

            } catch (Exception e) {
                Toast.makeText(getContext(), "Failed to load", Toast.LENGTH_SHORT).show();

            }



        }
        else if (requestCode == PICK_IMAGE_FROM_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null)//The Result given by Phot Picker
        {

            Uri uri = data.getData();
            try {
                myPhoto = CameraUtitlity.getThumbnail(uri,getActivity());
            } catch (IOException e) {
                e.printStackTrace();
            }

            UpdateUserPhoto();

        }
    }

    private void UpdateUserPhoto() {
        Uri ImageUri;
        if(myPhoto!=null)
        {
            ImageUri=cameraUtitlity.getImageUri(getContext(),cameraUtitlity.getResizedBitmap(myPhoto,500));
            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(ImageUri).build();
            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
               if(task.isSuccessful())
               {
                   Toast.makeText(getContext(),"Successfull",Toast.LENGTH_SHORT).show();
                   Home.photoListener.onPhotoChanged();
                   Picasso.get().
                           load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                           .memoryPolicy(MemoryPolicy.NO_CACHE)
                           .networkPolicy(NetworkPolicy.NO_CACHE)
                           .centerCrop()
                           .transform(new CircularTransform())
                           .fit()
                           .into(displayImage);
               }
               else
               {
                   Toast.makeText(getContext(),"Unsuccessful : "+task.getException(),Toast.LENGTH_SHORT).show();
               }

                }
            });

        }
    }

}
