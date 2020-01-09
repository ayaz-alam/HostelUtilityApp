package officials_module.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.code_base_update.beans.HostelNoticeBean;
import com.code_base_update.ui.BaseActivity;
import com.code_base_update.ui.MyDialog;
import com.medeveloper.ayaz.hostelutility.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;

import officials_module.model.SendNoticeModel;
import officials_module.presenter.ISendNoticePresenter;
import officials_module.view.ISendNoticeView;

public class SendNoticeActivity extends BaseActivity<ISendNoticeView, ISendNoticePresenter> implements ISendNoticeView {

    private static final int CAMERA_REQUEST = 1211;

    private Uri imageUri = null;
    private ProgressDialog progressDialog;


    @Override
    protected ISendNoticePresenter createPresenter() {
        return new SendNoticeModel();
    }

    @Override
    protected void initViewsAndEvents() {
        setupToolbar("Send Notice");
        enableNavigation();
        progressDialog = new MyDialog().getProgressDialog("Please wait...",this);

        getView(R.id.btn_send_notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    mPresenter.sendNotice(mContext,getNotice());
                }
            }
        });
        getView(R.id.btn_add_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   firePhotoIntent();
            }
        });

    }

    private HostelNoticeBean getNotice() {
        long timeStamp = Calendar.getInstance().getTimeInMillis();
        HostelNoticeBean notice  = new HostelNoticeBean();
        notice.setNoticeId(timeStamp+"");
        notice.setDate(timeStamp);
        notice.setTimeStamp(timeStamp);
        notice.setImageUrl("");
        notice.setByFaculty(getUserManager().getFacultyName());
        notice.setByFaculty(getUserManager().getFacultyId());
        notice.setNoticeBody(fetchText(R.id.et_notice_desc));
        notice.setNoticeSubject(fetchText(R.id.et_notice));
        return notice;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_official_send_notice;
    }

    @Override
    public Uri getImage() {
        return imageUri;
    }

    @Override
    public void sendingInitiated() {
        progressDialog.show();
    }

    @Override
    public void sendingFailed(String msg) {
        toastMsg("Failed: "+msg);
        progressDialog.dismiss();
    }

    @Override
    public void sentSuccessfully() {
        toastMsg("Sent successfully");
        clearViews();
        progressDialog.dismiss();
    }

    @Override
    public void onErrorOccurred() {
        toastMsg("Failed");
        progressDialog.dismiss();

    }

    @Override
    public void uploadingImage() {
        toastMsg("Uploading image...");

    }

    private boolean validateInput(){
        clearAllErrors();
        if(TextUtils.isEmpty(fetchText(R.id.et_notice))||fetchText(R.id.et_notice).length()<20){
            setError(R.id.et_notice,"Atleast 20 characters");
            return false;
        }else if(TextUtils.isEmpty(fetchText(R.id.et_notice_desc))||fetchText(R.id.et_notice_desc).length()<50){
            setError(R.id.et_notice_desc,"Atleast 50 characters");
            return false;
        }
        return true;
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
                    .setGuidelines(CropImageView.Guidelines.ON)
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
                imageUri = result.getUri();
                setImageUrl(R.id.img_upload,result.getUri().toString());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d("CROP_IMAGE_ERROR", error.getMessage());
            }
        }

    }
}
