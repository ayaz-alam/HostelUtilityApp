package com.code_base_update.models;

import android.content.Context;

import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.HostelNoticeBean;
import com.code_base_update.interfaces.DataCallback;
import com.code_base_update.presenters.IHostelNoticePresenter;
import com.code_base_update.view.IHostelNoticeView;

import java.util.ArrayList;

public class HostelNoticeModel implements IHostelNoticePresenter {

    private IHostelNoticeView view;
    private Context context;

    public HostelNoticeModel(Context context) {
        this.context = context;
    }

    @Override
    public void loadNotice() {
        DatabaseManager database = new DatabaseManager(context);
        database.loadAllNotice(new DataCallback<ArrayList<HostelNoticeBean>>() {
            @Override
            public void onSuccess(ArrayList<HostelNoticeBean> hostelNoticeBeans) {
                view.onNoticeLoaded(hostelNoticeBeans);
            }

            @Override
            public void onFailure(String msg) {
                view.onErrorOccurred(msg);
            }

            @Override
            public void onError(String msg) {
                view.onErrorOccurred(msg);
            }
        });
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
