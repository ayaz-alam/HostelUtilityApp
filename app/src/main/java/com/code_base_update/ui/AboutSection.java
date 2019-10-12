package com.code_base_update.ui;

import com.code_base_update.presenters.IBasePresenter;
import com.medeveloper.ayaz.hostelutility.R;

public class AboutSection extends BaseActivity{
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

        setupToolbar("About");
        enableNavigation();
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_back);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_about;
    }

}
