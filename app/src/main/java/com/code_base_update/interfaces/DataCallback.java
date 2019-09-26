package com.code_base_update.interfaces;

public interface DataCallback<T> {
        void onSuccess(T t);
        void onFailure(String msg);
        void onError(String msg);
}
