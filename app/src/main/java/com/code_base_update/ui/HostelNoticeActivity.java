package com.code_base_update.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code_base_update.beans.HostelNoticeBean;
import com.code_base_update.interfaces.OnItemClickListener;
import com.code_base_update.models.HostelNoticeModel;
import com.code_base_update.presenters.IHostelNoticePresenter;
import com.code_base_update.ui.adapters.HostelNoticeAdapter;
import com.code_base_update.view.IHostelNoticeView;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

public class HostelNoticeActivity extends BaseRecyclerActivity<IHostelNoticeView,IHostelNoticePresenter,HostelNoticeAdapter> implements IHostelNoticeView{

    @Override
    public HostelNoticeAdapter getAdapter(){
        return new HostelNoticeAdapter(this, R.layout.new_card_notice,new ArrayList<HostelNoticeBean>());
    }

    @Override
    public void initViews() {
        setupToolbar("");
        enableNavigation();
        mPresenter.loadNotice();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String noticeId = adapter.getItem(position).getNoticeId();
                mPresenter.openNoticeDetail(noticeId);
            }
        });
        enableNavigation();
    }

    @Override
    protected IHostelNoticePresenter createPresenter() {
        return new HostelNoticeModel();
    }

    @Override
    public RecyclerView getRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return recyclerView;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.simple_recycler_activity;
    }

    @Override
    public void onNoticeLoaded(ArrayList<HostelNoticeBean> list) {
        adapter.update(list);
    }
}
