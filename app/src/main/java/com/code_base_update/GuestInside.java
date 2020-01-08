package com.code_base_update;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.medeveloper.ayaz.hostelutility.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class GuestInside extends AppCompatActivity {

    CarouselView carouselView;

    int[] sampleImages = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5};
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_guest);
        setupToolbar("Rooms");
        enableNavigation();

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);
    }

    public void setupToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
            TextView head = (TextView) findViewById(R.id.tv_head);
            if (head != null)
                head.setText(title);
            setSupportActionBar(toolbar);
//            toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

    }

    public void enableNavigation() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
