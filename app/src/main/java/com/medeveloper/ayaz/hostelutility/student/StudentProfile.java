package com.medeveloper.ayaz.hostelutility.student;


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
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.MyData;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
        rootView=inflater.inflate(R.layout.student_your_profile, container, false);
        setUpDialogForImageChange();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
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

        setUpUserData();

        if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()!=null)
        Picasso.get().
                load(user.getPhotoUrl())
                .centerCrop()
                .transform(new CircularTransform())
                .fit()
                .into(displayImage);

        return rootView;
    }

    private void setUpUserData() {


        FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id)).
                child(getString(R.string.student_list_ref)).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //If user has details in the firebase, then proceed.
                        if(dataSnapshot.exists())
                        {
                            myDetails=dataSnapshot.getValue(StudentDetailsClass.class);
                            ((TextView)rootView.findViewById(R.id.display_name)).setText(myDetails.Name);
                            ((TextView)rootView.findViewById(R.id.student_hostel)).setText(getString(R.string.hostel_id));
                            ((TextView)rootView.findViewById(R.id.student_room_no)).setText(myDetails.RoomNo);
                            ((TextView)rootView.findViewById(R.id.student_branch)).setText(myDetails.Branch);
                            ((TextView)rootView.findViewById(R.id.enroll_no)).setText(myDetails.EnrollNo);
                            ((TextView)rootView.findViewById(R.id.student_adhar_no)).setText(myDetails.AdhaarNo);
                            ((TextView)rootView.findViewById(R.id.student_phone)).setText(myDetails.MobileNo);
                            ((TextView)rootView.findViewById(R.id.student_email)).setText(myDetails.Email);
                            ((TextView)rootView.findViewById(R.id.father_name)).setText(myDetails.FatherName);
                            ((TextView)rootView.findViewById(R.id.father_contact)).setText(myDetails.FatherContact);
                            ((TextView)rootView.findViewById(R.id.student_category)).setText(myDetails.Category);
                            ((TextView)rootView.findViewById(R.id.student_blood_group)).setText(myDetails.BloodGroup);
                            ((TextView)rootView.findViewById(R.id.student_address)).setText(myDetails.Address);
                            ((TextView)rootView.findViewById(R.id.local_guardian_no)).setText(myDetails.LocalGuardianNo);
                            ((Button)rootView.findViewById(R.id.change_password)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getContext(),ChangePassword.class));
                                }
                            });

                        }
                        else new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).
                                setTitleText("Can't show your profile\nTry again").show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

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
                   ((Home)getActivity()).setUpUser();

               }
               else
                   Toast.makeText(getContext(),"Unsuccessful : "+task.getException(),Toast.LENGTH_SHORT).show();

                }
            });



        }
    }

}
