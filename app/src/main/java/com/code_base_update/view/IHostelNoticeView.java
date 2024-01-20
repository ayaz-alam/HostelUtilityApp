package com.code_base_update.view;

import com.code_base_update.beans.HostelNoticeBean;

import java.util.ArrayList;

public interface IHostelNoticeView extends IBaseView {

    void onNoticeLoaded(ArrayList<HostelNoticeBean> list);

    void onErrorOccurred(String msg);
}
