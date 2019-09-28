package com.medeveloper.ayaz.hostelutility;


import android.view.MenuItem;

import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.ui.BaseActivity;

public class AboutSection extends BaseActivity {

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        enableNavigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_about;
    }
}
