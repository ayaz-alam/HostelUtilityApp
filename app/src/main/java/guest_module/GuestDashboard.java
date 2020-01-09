package guest_module;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.recyclerview.widget.RecyclerView;
import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.DashBoardBean;
import com.code_base_update.interfaces.DataCallback;
import com.code_base_update.interfaces.OnItemClickListener;
import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.ui.AboutSection;
import com.code_base_update.ui.BaseActivity;
import com.code_base_update.ui.adapters.DashboardRecyclerAdapter;
import com.medeveloper.ayaz.hostelutility.R;
import java.util.ArrayList;

public class GuestDashboard extends BaseActivity {

    private ArrayList<DashBoardBean> list;
    private DashboardRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private SparseArray<String[]> imageMaps;
    private ProgressDialog progressDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.new_activity_dashboard;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        adapter = getAdapter();
        recyclerView = getRecyclerView();
        recyclerView.setAdapter(adapter);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        initViews();
    }

    public RecyclerView getRecyclerView() {
        return (RecyclerView) getView(R.id.recycler_view);
    }

    public DashboardRecyclerAdapter getAdapter() {
        return new DashboardRecyclerAdapter(this, R.layout.new_dashboard_cardui, list);
    }

    public void loadData() {
        ArrayList<DashBoardBean> list = new ArrayList<>();

        DashBoardBean collegeNotice = new DashBoardBean();
        collegeNotice.setTitle("Hostel");
        collegeNotice.setDrawableId(R.drawable.ic_new_college_notice);
        list.add(collegeNotice);

        DashBoardBean hostelNotice = new DashBoardBean();
        hostelNotice.setTitle("Hostel Interior");
        hostelNotice.setDrawableId(R.drawable.ic_new_notice);
        list.add(hostelNotice);

        DashBoardBean complaintActivity = new DashBoardBean();
        complaintActivity.setTitle("Rooms");
        complaintActivity.setDrawableId(R.drawable.ic_new_complaint);
        list.add(complaintActivity);

        DashBoardBean complaintListActivity = new DashBoardBean();
        complaintListActivity.setTitle("Mess");
        complaintListActivity.setDrawableId(R.drawable.ic_new_complaint_list);
        list.add(complaintListActivity);

        onDataLoaded(list);
    }

    public void initViews() {

        setupToolbar("");
        list = new ArrayList<>();
        loadData();
        loadImages();

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent  =new Intent(mContext, ImageGalleyActivity.class);
                int type = GuestConstants.HOSTEL_OUTSIDE;
                switch (position) {
                    //Show hostel images
                    case 0:  type = GuestConstants.HOSTEL_OUTSIDE; break;
                    case 1:  type = GuestConstants.HOSTEL_INSIDE; break;
                    case 2:  type = GuestConstants.ROOM; break;
                    case 3:  type = GuestConstants.MESS; break;
                }
                intent.putExtra(GuestConstants.IMAGE_ARRAY,imageMaps.get(type));
                intent.putExtra(GuestConstants.IMAGE_TYPE,type);
                startActivity(intent);

            }
        });
    }

    private void loadImages() {
        new DatabaseManager(mContext).loadHostelImages(new DataCallback<SparseArray<String[]>>() {

            @Override
            public void onSuccess(SparseArray<String[]> sparseArray) {
                imageMaps = sparseArray;
                removeProgressBar();
            }

            @Override
            public void onFailure(String msg) {
                removeProgressBar();
                toastMsg(msg);
            }

            @Override
            public void onError(String msg) {
                removeProgressBar();
                toastMsg(msg);
            }
        });



    }

    private void removeProgressBar() {
        progressDialog.dismiss();
    }

    private void openAboutSection() {
        startActivity(new Intent(this, AboutSection.class));
    }


    public void onDataLoaded(ArrayList<DashBoardBean> list) {
        this.list = list;
        adapter.update(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                openAboutSection();
                break;
            case R.id.logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
