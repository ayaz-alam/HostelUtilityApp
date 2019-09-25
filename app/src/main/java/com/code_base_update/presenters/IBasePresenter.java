package com.code_base_update.presenters;

import com.code_base_update.view.IBaseView;

public interface IBasePresenter<V extends IBaseView> {
    void attachView(V view);

    void detachView();
}
