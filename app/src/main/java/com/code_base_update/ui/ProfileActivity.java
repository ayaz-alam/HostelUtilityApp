package com.code_base_update.ui;

import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.code_base_update.UserManager;
import com.code_base_update.presenters.IBasePresenter;
import com.medeveloper.ayaz.hostelutility.R;

public class ProfileActivity extends BaseActivity {
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

        //kamal's code
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().hide();

        getView(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserManager().logout();
            }
        });
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
        }

        return super.onOptionsItemSelected(item);
    }
}
