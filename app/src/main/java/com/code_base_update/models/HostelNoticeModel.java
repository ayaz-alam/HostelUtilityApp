package com.code_base_update.models;

import com.code_base_update.beans.HostelNoticeBean;
import com.code_base_update.presenters.IHostelNoticePresenter;
import com.code_base_update.view.IHostelNoticeView;

import java.util.ArrayList;

public class HostelNoticeModel implements IHostelNoticePresenter {

    private IHostelNoticeView view;

    @Override
    public void loadNotice() {
        //TODO load notice from the database and return to the view
        view.onNoticeLoaded(new ArrayList<HostelNoticeBean>());
    }

    @Override
    public void openNoticeDetail(String noticeId) {

    }

    @Override
    public void attachView(IHostelNoticeView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
