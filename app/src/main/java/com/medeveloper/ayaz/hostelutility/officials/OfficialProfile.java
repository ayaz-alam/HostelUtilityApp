package com.medeveloper.ayaz.hostelutility.officials;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.ChangePassword;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.CameraUtitlity;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.CircularTransform;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.OfficialsDetailsClass;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfficialProfile extends Fragment {


    private static final int PICK_IMAGE_FROM_GALLERY = 212;
    private ImageView displayImage;
    private CameraUtitlity cameraUtitlity;
    private SweetAlertDialog dialogForImageChange;
    private Bitmap myPhoto;
    private static final int TAKE_IMAGE_FROM_CAMERA=121;
    private OfficialsDetailsClass myDetails;

    public OfficialProfile() {
        // Required empty public constructor
    }


    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.officials_your_profile, container, false);
        setUpDialogForImageChange();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        displayImage=(ImageView)rootView.findViewById(R.id.display_image);
        final TextView changePhoto=(TextView)rootView.findViewById(R.id.edit_photo);
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

        ((Button)rootView.findViewById(R.id.change_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ChangePassword.class));
            }
        });

        setUpUserData();

        if(user.getPhotoUrl()!=null)
        Picasso.get().
                load(user.getPhotoUrl())
                .centerCrop()
                .transform(new CircularTransform())
                .fit()
                .into(displayImage);
                

        return rootView;
    }

    private void setUpUserData() {
        FirebaseAuth user=FirebaseAuth.getInstance();
        if(user==null)
            Toast.makeText(getActivity(),"user is not present ",Toast.LENGTH_SHORT).show();
        else
        FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).
                child(getString(R.string.official)).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
               .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //If user has details in the firebase, then proceed.
                        if(dataSnapshot.exists())
                        {

                            myDetails=dataSnapshot.getValue(OfficialsDetailsClass.class);
                            //Header Fields
                            ((TextView)rootView.findViewById(R.id.display_name)).setText(myDetails.mName);
                            ((TextView)rootView.findViewById(R.id.student_hostel)).setText(getString(R.string.hostel_id));
                            ((TextView)rootView.findViewById(R.id.student_room_no)).setText(myDetails.mDepartment);

                            //Below texts
                            ((TextView)rootView.findViewById(R.id.employee_id)).setText(myDetails.mEmployeeID);
                            ((TextView)rootView.findViewById(R.id.employee_adhar_no)).setText(myDetails.mAdhaarNo);
                            ((TextView)rootView.findViewById(R.id.employee_phone)).setText(myDetails.mPhone);
                            ((TextView)rootView.findViewById(R.id.employee_email)).setText(myDetails.mEmail);
                            ((TextView)rootView.findViewById(R.id.employee_hostel_name)).setText(myDetails.mHostelName);
                            ((TextView)rootView.findViewById(R.id.employee_post)).setText(myDetails.mPost);



                        }
                        else new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).
                                setTitleText("Can't show your profile\nTry again").show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).
                                setTitleText("Error")
                                .setContentText(""+databaseError.getMessage()).show();

                    }
                });

    }

    private void setUpDialogForImageChange() {
        dialogForImageChange=new SweetAlertDialog(getContext(),
                SweetAlertDialog.CUSTOM_IMAGE_TYPE)
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
                myPhoto=cameraUtitlity.rotatePhoto(myPhoto,displayImage);

                Picasso.get().
                        load(cameraUtitlity.getImageUri(getActivity(),myPhoto))
                        .centerCrop()
                        .transform(new CircularTransform())
                        .fit()
                        .into(displayImage);
                UpdateUserPhoto();

            } catch (Exception e) {
                Toast.makeText(getContext(), "Failed to load", Toast.LENGTH_SHORT).show();

            }



        }
        else if (requestCode == PICK_IMAGE_FROM_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null)//The Result given by Phot Picker
        {

            Uri uri = data.getData();
            Picasso.get().
                    load(uri)
                    .centerCrop()
                    .transform(new CircularTransform())
                    .fit()
                    .into(displayImage);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                myPhoto=cameraUtitlity.getResizedBitmap(bitmap,500);
                UpdateUserPhoto();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                        ((OfficialsHome)getActivity()).setUpUser();
                    }
                    else
                        Toast.makeText(getContext(),"Unsuccessful : "+task.getException(),Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}
