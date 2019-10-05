package com.code_base_update.ui;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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
                startActivity(new Intent(getApplicationContext(),NewLogin.class));
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
