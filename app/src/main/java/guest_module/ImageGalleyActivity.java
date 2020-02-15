package guest_module;

import android.content.Intent;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.code_base_update.DatabaseManager;
import com.code_base_update.interfaces.DataCallback;
import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.ui.BaseActivity;
import com.medeveloper.ayaz.hostelutility.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class ImageGalleyActivity extends BaseActivity {

    private String[] images = new String[]{"https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
            "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
            "https://homepages.cae.wisc.edu/~ece533/images/boat.png",
            "https://homepages.cae.wisc.edu/~ece533/images/goldhill.bmp"};
    CarouselView carouselView;;

    private int[] noImageStringArray = new int[] {R.drawable.no_image_back,R.drawable.no_image_back,R.drawable.no_image_back,R.drawable.no_image_back};

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(R.drawable.no_image_back);
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(mContext);
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius( 30f);
            circularProgressDrawable.start();
            if(images==null||images.length==0)
                imageView.setImageResource(noImageStringArray[position]);
            else {
                Glide.with(mContext).
                        load(images[position]).
                        placeholder(circularProgressDrawable).
                        error(R.drawable.no_image_back).
                        into(imageView);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imageView.setForeground(getDrawable(R.drawable.image_fore_grad));
                }
            }
        }
    };

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        setContentView(R.layout.sample_guest);
        setupToolbar("Gallery");
        enableNavigation();
        TextView heading = findViewById(R.id.tv_headline);

        Intent intent  = getIntent();
        TextView textView = findViewById(R.id.tv_head_desc);
        textView.setText(intent.getStringExtra(GuestConstants.TEXT_TYPE));
        images = intent.getStringArrayExtra(GuestConstants.IMAGE_ARRAY);
        switch(intent.getIntExtra(GuestConstants.IMAGE_TYPE,GuestConstants.HOSTEL_OUTSIDE)){
            case GuestConstants.HOSTEL_OUTSIDE:
                heading.setText(GuestConstants.HostelOutside);
                break;
            case GuestConstants.HOSTEL_INSIDE:
                heading.setText(GuestConstants.HostelInterior);
                break;
            case GuestConstants.ROOM:
                heading.setText(GuestConstants.Room);
                break;
            case GuestConstants.MESS:
                heading.setText(GuestConstants.Mess);
                break;
        }

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(noImageStringArray.length);
        carouselView.setImageListener(imageListener);
    }

    private void loadImageArray(final String imagesId) {
        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.fetchImageCarousel(imagesId,new DataCallback<String[]>(){
            @Override
            public void onSuccess(String[] strings) {
                images = strings;
            }

            @Override
            public void onFailure(String msg) {
                toastMsg(msg);
            }

            @Override
            public void onError(String msg) {
                toastMsg(msg);
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.sample_guest;
    }
}
