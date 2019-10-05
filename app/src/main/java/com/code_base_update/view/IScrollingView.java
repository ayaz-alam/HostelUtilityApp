package com.code_base_update.view;

import com.code_base_update.beans.BaseBean;

import java.util.ArrayList;

public interface IScrollingView<T extends BaseBean> extends IBaseView {
    void firstPageReceived(ArrayList<T> list);
    void onNextPageReceived(ArrayList<T> list);
    void onEndOfListReached();
    void onErrorOccurred(String message);
    void noDataFound();
}
