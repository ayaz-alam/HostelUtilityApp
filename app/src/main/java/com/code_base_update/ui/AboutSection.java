package com.code_base_update.ui;

import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.widget.Toolbar;

import com.code_base_update.presenters.IBasePresenter;
import com.medeveloper.ayaz.hostelutility.R;

public class AboutSection extends BaseActivity{
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_back);
//          getSupportActionBar().hide();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_about;
    }

}
