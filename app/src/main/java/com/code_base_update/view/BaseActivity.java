package com.code_base_update.view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.code_base_update.view.view_managers.IImageManager;
import com.code_base_update.view.view_managers.ITextManager;
import com.code_base_update.view.view_managers.ViewManager;
import com.medeveloper.ayaz.hostelutility.R;

import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.presenters.IBaseView;
public abstract class BaseActivity<V extends IBaseView, P extends
        IBasePresenter<V>> extends AppCompatActivity implements IImageManager, ITextManager {

    private ViewManager viewManager;
    private IBasePresenter<V> presenter;

    protected abstract P createPresenter();

    protected abstract void initViewsAndEvents();

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        presenter = createPresenter();
        viewManager = new ViewManager(getParentView(), this);
        initViewsAndEvents();
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
}
