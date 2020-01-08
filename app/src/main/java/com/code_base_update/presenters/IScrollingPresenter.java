package com.code_base_update.presenters;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.view.IScrollingView;

public interface IScrollingPresenter extends IBasePresenter<IScrollingView<ComplaintBean>> {

    void loadFirstBatch();
    void loadNextBatch();
    void markResolved(ComplaintBean complaintId, SuccessCallback callback);
}
