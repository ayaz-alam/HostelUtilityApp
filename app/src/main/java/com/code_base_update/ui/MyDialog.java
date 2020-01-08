package com.code_base_update.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

public class MyDialog {

    public ProgressDialog getProgressDialog(String title, Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        return progressDialog;
    }
}
