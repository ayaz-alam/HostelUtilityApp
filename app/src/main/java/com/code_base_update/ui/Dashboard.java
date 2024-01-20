package com.code_base_update.ui;

import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.code_base_update.Constants;
import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.CollegeBean;
import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.beans.DashBoardBean;
import com.code_base_update.interfaces.OnItemClickListener;
import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.models.DashboardModel;
import com.code_base_update.presenters.IDashPresenter;
import com.code_base_update.ui.adapters.DashboardRecyclerAdapter;
import com.code_base_update.utility.UserManager;
import com.code_base_update.view.IDashView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;
import java.util.Calendar;

public class Dashboard extends BaseActivity<IDashView,IDashPresenter> implements IDashView{

    private ArrayList<DashBoardBean> list;
    private DashboardRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected IDashPresenter createPresenter() {
        return new DashboardModel();
    }

    @Override
    protected void initViewsAndEvents() {
        adapter = getAdapter();
        recyclerView = getRecyclerView();
        recyclerView.setAdapter(adapter);
        initViews();
    }

    public RecyclerView getRecyclerView() {
        return (RecyclerView)getView(R.id.recycler_view);
    }

    public DashboardRecyclerAdapter getAdapter() {
        return new DashboardRecyclerAdapter(this,R.layout.new_dashboard_cardui,list);
    }

    public void initViews() {

        setupToolbar("");
        list = new ArrayList<>();
        mPresenter.loadData();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position){

                    case 0:openCollegeNotice();break;

                    case 1:openHostelNotice();break;

                    case 2:openRegisterComplaint();break;

                    case 3:openRegisterComplaintList();break;

                    case 4:openRegisterApplication();break;

                    case 5:openRegisterApplicationList();break;
                }

            }
        });

        getView(R.id.iv_display_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profileActivity = new Intent(Dashboard.this, ProfileActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<>(getView(R.id.view), "bg_anim");
                pairs[1] = new Pair<>(getView(R.id.iv_display_image), "profile_anim");
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(Dashboard.this, pairs);
                startActivity(profileActivity, optionsCompat.toBundle());
            }
        });

        mPresenter.loadUserImageUrl();
        mPresenter.loadUserName();
    }

    private void openAboutSection() {
        startActivity(new Intent(this,AboutSection.class));
    }

    private void openHostelNotice() {
        startActivity(new Intent(this,HostelNoticeActivity.class));
    }

    private void openCollegeNotice() {
        startActivity(new Intent(this, GeneralNotice.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_activity_dashboard;
    }

    @Override
    public void onDataLoaded(ArrayList<DashBoardBean> list) {
        this.list = list;
        adapter.update(list);
    }

    @Override
    public void openRegisterComplaint() {
        startActivity(new Intent(this,ComplaintActivity.class));
    }

    @Override
    public void openRegisterComplaintList() {
        startActivity(new Intent(this, InfiniteScrollingActivity.class));
    }

    @Override
    public void openRegisterApplication() {
        startActivity(new Intent(this,RegisterApplicationActivity.class));
    }

    @Override
    public void openRegisterApplicationList() {
        startActivity(new Intent(this,ApplicationListActivity.class));
    }

    @Override
    public void  onDisplayImageLoaded(String imageUrl) {
        if(imageUrl!=null)
            setImageUrl(R.id.iv_display_image, imageUrl.toString(), R.drawable.ic_undraw_male_avatar, new CircleCrop());
        else
            setImageUrl(R.id.iv_display_image, "", R.drawable.ic_undraw_male_avatar, new CircleCrop());
    }

    @Override
    public void userNameLoaded(String name) {
        if(name!=null&&name.length()>0)
            setText(R.id.tv_username, name);
        else
            setText(R.id.tv_username, "Username");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:openAboutSection();break;
            case R.id.logout:logout();break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume(){
        super.onResume();
        mPresenter.loadUserImageUrl();
    }
}
