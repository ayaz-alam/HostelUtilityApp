package officials_module.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.code_base_update.Human;
import com.code_base_update.ImageHelper;
import com.code_base_update.beans.Student;
import com.code_base_update.interfaces.ImageUploadCallback;
import com.code_base_update.ui.BaseActivity;
import com.code_base_update.ui.LoginActivity;
import com.code_base_update.ui.MyDialog;
import com.code_base_update.utility.UserManager;
import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.ui.dialogs.ChangePasswordDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.medeveloper.ayaz.hostelutility.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class OfficialProfileActivity extends BaseActivity {

    private static final int CAMERA_REQUEST = 125;

    private ProgressDialog profileUpdateDialog;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

        setupToolbar("");
        enableNavigation();
        setFadeAnim();
        getView(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finishAffinity();

            }
        });
        getView(R.id.btn_change_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordDialog dialog = new ChangePasswordDialog(OfficialProfileActivity.this);
                dialog.show();
            }
        });

        getView(R.id.iv_edit_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firePhotoIntent();
            }
        });

        profileUpdateDialog = new MyDialog().getProgressDialog("Please wait..",this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_profile_activity;
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


        if (requestCode == CAMERA_REQUEST)
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                firePhotoIntent();
            } else {

                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
    }

    public void firePhotoIntent() {

        if (checkPermissionForCamera() && checkPermissionForStorageRead()) {
            CropImage.activity()
                    .setAspectRatio(1, 1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setFixAspectRatio(true)
                    .start(this);
        } else {
            final String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
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
                saveImageToFirebase(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d("CROP_IMAGE_ERROR", error.getMessage());
            }
        }

    }

    private void saveImageToFirebase(Uri resultUri) {
        try {
            File currentFile =  new Compressor(this).compressToFile(new File(resultUri.getPath()));
            Uri compressedUri = Uri.fromFile(currentFile);

            StorageReference mProfileReference = FirebaseStorage.getInstance().getReference("profilePhotos/"+getUserManager().getEmail());
            ImageHelper.saveImageToServer( compressedUri, mProfileReference, new ImageUploadCallback() {
                @Override
                public void initiated() {
                    profileUpdateDialog.show();
                }

                @Override
                public void success(Uri Url) {
                    updateUserImage(Url);
                }

                @Override
                public void failed(String message) {
                    profileUpdateDialog.dismiss();
                    toastMsg(message);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUserImage(final Uri resultUri) {
        getUserManager().changeImage(resultUri, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    profileUpdateDialog.dismiss();
                    setImageUrl(
                            R.id.iv_display_image,//View id
                            resultUri.toString(),//URL
                            getSession().getStudent().getSex().equals(Human.MALE) ? R.drawable.ic_undraw_male_avatar : R.drawable.ic_undraw_female_avatar, //Place holder
                            new CircleCrop()//Crop options
                    );

                    toastMsg("Profile photo changed");
                }
                else {
                    profileUpdateDialog.dismiss();
                    toastMsg("Error: " + task.getException().getLocalizedMessage());
                }
            }
        });
    }

}
