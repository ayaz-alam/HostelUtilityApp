package com.code_base_update.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.code_base_update.utility.SessionManager;
import com.code_base_update.ui.view_managers.IImageManager;
import com.code_base_update.ui.view_managers.ITextManager;
import com.code_base_update.ui.view_managers.ViewManager;
import com.medeveloper.ayaz.hostelutility.R;

import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.view.IBaseView;

public abstract class BaseActivity<V extends IBaseView, P extends IBasePresenter<V>> extends AppCompatActivity implements IImageManager, ITextManager {

    private ViewManager viewManager;
    public P mPresenter;
    private SparseArray<View> viewHashMap;
    private SessionManager session;

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

        if(mPresenter != null){
            mPresenter.attachView((V) this);
        }
        initViewsAndEvents();
    }

    public SessionManager getSession(){
        if(session==null){
            session =new SessionManager(this);
        }
        return session;
    }

    public void setupToolbar(String title){
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
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

    public View getView(int resId){
        if(viewHashMap.get(resId)==null){
            viewHashMap.append(resId,findViewById(resId));
        }
        return  viewHashMap.get(resId);
    }

    public void enableNavigation(){
        ActionBar actionBar =getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    public void setError(int resId,String error){
        ((EditText)getView(resId)).setError(error);
        getView(resId).requestFocus();
    }

}
