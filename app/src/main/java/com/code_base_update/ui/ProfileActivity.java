package com.code_base_update.ui;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.code_base_update.Human;
import com.code_base_update.beans.Student;
import com.code_base_update.utility.UserManager;
import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.ui.dialogs.ChangePasswordDialog;
import com.medeveloper.ayaz.hostelutility.R;

public class ProfileActivity extends BaseActivity {
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
}
