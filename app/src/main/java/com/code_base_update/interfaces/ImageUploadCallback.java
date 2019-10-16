package com.code_base_update.interfaces;

import android.net.Uri;

public interface ImageUploadCallback {
    void initiated();
    void success(Uri Url);
    void failed(String message);
}
