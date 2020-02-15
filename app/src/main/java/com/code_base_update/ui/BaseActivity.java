package com.code_base_update.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.transition.Fade;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.code_base_update.utility.SessionManager;
import com.code_base_update.ui.view_managers.IImageManager;
import com.code_base_update.ui.view_managers.ITextManager;
import com.code_base_update.ui.view_managers.ViewManager;
import com.code_base_update.utility.UserManager;
import com.google.android.material.textfield.TextInputLayout;
import com.medeveloper.ayaz.hostelutility.R;

import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.view.IBaseView;

public abstract class BaseActivity<V extends IBaseView, P extends IBasePresenter<V>> extends AppCompatActivity implements IImageManager, ITextManager {

    private ViewManager viewManager;
    public P mPresenter;
    private SparseArray<View> viewHashMap;
    private SessionManager session;
    private UserManager userManager;
    public Context mContext;

    protected abstract P createPresenter();

    protected abstract void initViewsAndEvents();

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mPresenter = createPresenter();
        viewManager = new ViewManager(getParentView(), this);
        viewHashMap = new SparseArray<>();
        mContext = this;

        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
        initViewsAndEvents();
    }

    public void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public SessionManager getSession() {
        if (session == null) {
            session = new SessionManager(this);
        }
        return session;
    }

    public void setupToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
            TextView head = (TextView)getView(R.id.tv_head);
            if(head!=null)
                head.setText(title);
            setSupportActionBar(toolbar);
//            toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

    }

    private View getParentView() {
        return findViewById(R.id.parent);
    }

    @Override
    public ImageView setImageResource(int viewId, int imageResId) {
        return viewManager.setImageResource(viewId, imageResId);
    }

    @Override
    public ImageView setImageDrawable(int viewId, Drawable drawable) {
        return viewManager.setImageDrawable(viewId, drawable);
    }

    @Override
    public ImageView setImageUrl(int viewId, String imageUrl) {
        return viewManager.setImageUrl(viewId, imageUrl);
    }

    @Override
    public ImageView setImageUrl(int viewId, String imageUrl, int defResourceId) {
        return viewManager.setImageUrl(viewId, imageUrl, defResourceId);
    }

    @Override
    public ImageView setImageUrl(int viewId, String imageUrl, int defResourceId, BitmapTransformation... transformations) {
        return viewManager.setImageUrl(viewId, imageUrl, defResourceId, transformations);
    }

    @Override
    public ImageView setImageBitmap(int viewId, Bitmap bitmap) {
        return viewManager.setImageBitmap(viewId, bitmap);
    }

    @Override
    public TextView setText(int viewId, String value) {
        return viewManager.setText(viewId, value);
    }

    @Override
    public TextView setTextColor(int viewId, int textColor) {
        return viewManager.setTextColor(viewId, textColor);
    }

    @Override
    public TextView setTextColorRes(int viewId, int textColorRes) {
        return viewManager.setTextColorRes(viewId, textColorRes);
    }

    @Override
    public View setBackgroundColor(int viewId, int color) {
        return viewManager.setBackgroundColor(viewId, color);
    }

    @Override
    public View setBackgroundRes(int viewId, int backgroundRes) {
        return viewManager.setBackgroundRes(viewId, backgroundRes);
    }

    @Override
    public View setVisible(int viewId, boolean visible) {
        return viewManager.setVisible(viewId, visible);
    }

    public View getView(int resId) {
        if (viewHashMap.get(resId) == null) {
            viewHashMap.append(resId, findViewById(resId));
        }
        return viewHashMap.get(resId);
    }

    public void enableNavigation() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    public void setError(int viewId, String error) {
        ((EditText) getView(viewId)).setError(error);
        getView(viewId).requestFocus();
    }

    public void setILError(int viewId, String error) {
        TextInputLayout inputLayout = ((TextInputLayout) getView(viewId));
        if (inputLayout != null)
            inputLayout.setError(error);
    }

    public String fetchText(int veiwId) {
        EditText editText = ((EditText) getView(veiwId));
        if (editText != null) return editText.getText().toString();
        return "";
    }

    public Spinner getSpinner(int veiwId) {
        Spinner spinner = ((Spinner) getView(veiwId));
        if (spinner.getSelectedItemPosition() == 0) {
            spinner.setFocusable(true);
            spinner.setFocusableInTouchMode(true);
            spinner.requestFocus();
        }
        return spinner;
    }

    public void clearViews(){
        ViewGroup viewGroup = (ViewGroup)getView(R.id.parent);
        resetAllViews(viewGroup);
    }

    public void clearAllErrors() {
        clearError((ViewGroup) getView(R.id.parent));
    }

    private void clearError(ViewGroup viewGroup) {
        if (viewGroup == null || viewGroup.getChildCount() == 0) return;

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof TextInputLayout) {
                ((TextInputLayout) view).setError(null);
            } else if (view instanceof EditText) {
                ((EditText) view).setError(null);
            } else if (view instanceof ViewGroup) {
                clearError((ViewGroup) view);
            }

        }

    }

    private void resetAllViews(ViewGroup parent){
        if(parent.getChildCount()==0) return;

        for(int i = 0;i<parent.getChildCount();i++){
            View view = parent.getChildAt(i);

            if(view!=null){

                if(view instanceof EditText){
                    ((EditText)view).setText(null);
                }else if(view instanceof Spinner){
                    ((Spinner)view).setSelection(0);
                }else if(view instanceof ViewGroup)
                    resetAllViews((ViewGroup)view);

            }

        }

    }

    public void setFadeAnim() {
        Fade fade = new Fade();
        fade.excludeTarget(R.id.toolbar, true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else {
            return false;
        }
    }

    public void logout(){
        getUserManager().logout(this);
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
    }

    public UserManager getUserManager(){
        if(userManager ==null) userManager = new UserManager();
        return userManager;
    }

}
