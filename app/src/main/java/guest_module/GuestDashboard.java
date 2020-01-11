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
import java.util.HashMap;

import static guest_module.GuestConstants.HOSTEL_TEXT;

public class GuestDashboard extends BaseActivity {

    private ArrayList<DashBoardBean> list;
    private DashboardRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private SparseArray<String[]> imageMaps;
    private ProgressDialog progressDialog;
    private HashMap<String, String> textHash;

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
        collegeNotice.setDrawableId(R.drawable.ic_hostel);
        list.add(collegeNotice);

        DashBoardBean hostelNotice = new DashBoardBean();
        hostelNotice.setTitle("Rooms and Bathrooms");
        hostelNotice.setDrawableId(R.drawable.ic_new_rooms);
        list.add(hostelNotice);

        DashBoardBean complaintActivity = new DashBoardBean();
        complaintActivity.setTitle("Mess");
        complaintActivity.setDrawableId(R.drawable.ic_mess);
        list.add(complaintActivity);

        DashBoardBean complaintListActivity = new DashBoardBean();
        complaintListActivity.setTitle("Other Facilites");
        complaintListActivity.setDrawableId(R.drawable.ic_other_facilities);
        list.add(complaintListActivity);

        onDataLoaded(list);
    }

    public void initViews() {

        setupToolbar("");
        list = new ArrayList<>();
        loadData();
        loadImages();
        setText(R.id.tv_username, "Guest");

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
                intent.putExtra(GuestConstants.TEXT_TYPE,textHash.get(getTextType(type)));
                startActivity(intent);

            }
        });
    }

    private String getTextType(int type){
        switch (type){
            case GuestConstants.HOSTEL_OUTSIDE:return HOSTEL_TEXT;
            case GuestConstants.HOSTEL_INSIDE:return GuestConstants.HOSTEL_INTERIOR;
            case GuestConstants.MESS:return GuestConstants.HOSTEL_MESS;
            case GuestConstants.ROOM:return GuestConstants.HOSTEL_ROOM;
        }
        return HOSTEL_TEXT;
    }

    private void loadText(){
        new DatabaseManager(mContext).loadHostelText(new DataCallback<HashMap<String, String>>() {
            @Override
            public void onSuccess(HashMap<String, String> stringStringHashMap) {
                textHash = stringStringHashMap;
                removeProgressBar();
            }

            @Override
            public void onFailure(String msg) {
                removeProgressBar();
            }

            @Override
            public void onError(String msg) {
                removeProgressBar();
            }
        });
    }

    private void loadImages() {
        new DatabaseManager(mContext).loadHostelImages(new DataCallback<SparseArray<String[]>>() {

            @Override
            public void onSuccess(SparseArray<String[]> sparseArray) {
                imageMaps = sparseArray;
                loadText();
            }

            @Override
            public void onFailure(String msg) {
                removeProgressBar();
                toastMsg(msg);
            }

            @Override
            public void onError(String msg) {
                loadText();
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
