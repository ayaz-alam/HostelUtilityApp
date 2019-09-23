package com.code_base_update.presenters;

public interface IBasePresenter<V> {
    void attachView(V view);

    void detachView();
}
