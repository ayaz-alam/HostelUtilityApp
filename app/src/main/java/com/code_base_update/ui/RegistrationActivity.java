package com.code_base_update.ui;

import com.code_base_update.models.RegistrationModel;
import com.code_base_update.presenters.IRegistratonPresenter;
import com.code_base_update.view.IRegistrationView;
import com.medeveloper.ayaz.hostelutility.R;

public class RegistrationActivity extends BaseActivity<IRegistrationView, IRegistratonPresenter> implements IRegistrationView {

    @Override
    protected IRegistratonPresenter createPresenter() {
        return new RegistrationModel();
    }

    @Override
    protected void initViewsAndEvents() {
        //TODO @Kanika Use this in place of onCreate



    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_registration_activity;
    }
}
