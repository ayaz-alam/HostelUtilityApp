package com.code_base_update.ui.view_managers;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public interface IImageManager extends IViewManager {


    ImageView setImageResource(int viewId, int imageResId);

    ImageView setImageDrawable(int viewId, Drawable drawable);

    ImageView setImageUrl(int viewId, String imageUrl);

    ImageView setImageUrl(int viewId, String imageUrl, int defResourceId);

    ImageView setImageUrl(int viewId, String imageUrl, int defResourceId, BitmapTransformation... transformations);

    ImageView setImageBitmap(int viewId, Bitmap bitmap);

}
