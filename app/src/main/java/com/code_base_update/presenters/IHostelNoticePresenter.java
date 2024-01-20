package com.code_base_update.presenters;

import com.code_base_update.view.IHostelNoticeView;

public interface IHostelNoticePresenter extends IBasePresenter<IHostelNoticeView> {
    void loadNotice();
    void openNoticeDetail(String noticeId);
}
