package com.code_base_update.ui.view_managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class ViewManager {

    private View parentView;
    private Context context;
    private SparseArray<View> views;

    public ViewManager(View parentView, Context context) {
        this.parentView = parentView;
        this.context = context;
        views = new SparseArray<>();
    }

    public View setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return view;
    }

    public View setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return view;
    }

    public View setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        if(view!=null)
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return view;
    }

    public ImageView setImageResource(int viewId, int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return view;
    }

    public ImageView setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return view;
    }

    public ImageView setImageUrl(int viewId, String imageUrl) {
        ImageView view = getView(viewId);
        Glide.with(context).load(imageUrl).into(view);
        return view;
    }

    public ImageView setImageUrl(int viewId, String imageUrl, int defResourceId) {
        ImageView view = getView(viewId);
        Glide.with(context).load(imageUrl).placeholder(defResourceId).into(view);
        return view;
    }

    public ImageView setImageUrl(int viewId, String imageUrl, int defResourceId, BitmapTransformation... transformations) {
        ImageView view = getView(viewId);
        Glide.with(context).load(imageUrl).placeholder(defResourceId).transform(transformations).into(view);
        return view;
    }


    public ImageView setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return view;
    }

    public TextView setText(int viewId, String value) {
        TextView view = getView(viewId);
        view.setText(value);
        return view;
    }

    public TextView setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return view;
    }

    public TextView setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(context.getResources().getColor(textColorRes));
        return view;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = parentView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


}
