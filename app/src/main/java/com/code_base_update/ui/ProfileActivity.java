package com.code_base_update.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.code_base_update.Human;
import com.code_base_update.beans.Student;
import com.code_base_update.utility.UserManager;
import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.ui.dialogs.ChangePasswordDialog;
import com.medeveloper.ayaz.hostelutility.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class ProfileActivity extends BaseActivity {
    private static final int CAMERA_REQUEST = 125;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

        setupToolbar("");
        enableNavigation();
        getView(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserManager().logout();
                startActivity(new Intent(getApplicationContext(), NewLogin.class));
                finishAffinity();

            }
        });
        getView(R.id.btn_change_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordDialog dialog = new ChangePasswordDialog(ProfileActivity.this);
                dialog.show();
            }
        });

        getView(R.id.iv_edit_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firePhotoIntent();
            }
        });

        UserManager userManager = new UserManager();
        Student student = new Student();

        userManager.getImageUrl();
        student.setStudentName("Ayaz Alam");
        student.setEmail(userManager.getEmail());
        student.setRoom("125");
        student.setMobileNo("9079935675");
        student.setBloodGroup("O+");
        student.setCategory("General");
        student.setEnrollNo("2016/CTAE/062");
        student.setAdharNo("014785236901");
        student.setAddress("Allahabad");
        student.setGuardiaName("Kanika");
        student.setClassName("B.Tech");
        student.setYear("IV");
        student.setBranch("IT");
        setUpUser(student);


    }


    public void setUpUser(Student student) {
        setText(R.id.tv_username, student.getStudentName());
        setText(R.id.tv_emailaddress, student.getEmail());
        setText(R.id.tv_user_location, student.getRoom());
        setText(R.id.tv_mobile_umber, student.getMobileNo());
        setText(R.id.tv_blood_group, student.getBloodGroup());
        setText(R.id.tv_cast, student.getCategory());
        setText(R.id.tv_enrollment_no, student.getEnrollNo());
        setText(R.id.tv_aadhar_no, student.getAdharNo());
        setText(R.id.tv_perma_address, student.getAddress());
        setText(R.id.tv_guardian_name, student.getGuardiaName());
        setText(R.id.sp_class, student.getClassName());
        setText(R.id.sp_year, student.getYear());
        setText(R.id.sp_branch, student.getBranch());
        setImageUrl(R.id.iv_display_image, new UserManager().getImageUrl().toString(),
                student.getSex().equals(Human.FEMALE) ? R.drawable.ic_undraw_female_avatar : R.drawable.ic_undraw_male_avatar, new CircleCrop());


    }


    @Override
    protected int getLayoutId() {
        return R.layout.new_profile_activity;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case android.R.id.edit:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private boolean checkPermissionForStorageRead() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForCamera() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode==CAMERA_REQUEST)
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1]== PackageManager.PERMISSION_GRANTED) {
                CropImage.activity()
                        .setAspectRatio(1,1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setFixAspectRatio(true)
                        .start(this);
            } else {

                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
    }

    public void firePhotoIntent() {

            if(checkPermissionForCamera()&&checkPermissionForStorageRead()) {
                CropImage.activity()
                        .setAspectRatio(1,1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setFixAspectRatio(true)
                        .start(this);
            }
            else
            {
                final String [] permissions=new String []{ Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions, CAMERA_REQUEST);
            }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Bitmap bitmap1 = result.getBitmap();
                if (bitmap1 != null) {
                    //Setup Listener
                    //mPhotoIntentResult.onPhotoIntentResult(result.getBitmap(), requestCode,null);
                } else {
                    try {
                        //SetupListener
                        bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d("CROP_IMAGE_ERROR", error.getMessage());
            }
        }

    }

}
