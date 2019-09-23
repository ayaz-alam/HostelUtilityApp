package com.medeveloper.ayaz.hostelutility.student;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.medeveloper.ayaz.hostelutility.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PhotoViewer extends AppCompatActivity {

    ImageView image;
    ImageView backButton;
    SweetAlertDialog pDialog;
    boolean expanded=false;
    TextView expand_colapse;
    ViewGroup.LayoutParams viewParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final String NoticeBody=getIntent().getStringExtra("NoticeBody");
        final String noticeTitle=getIntent().getStringExtra("NoticeTitle");

        TextView title=findViewById(R.id.photo_viewer_title);
        final TextView body=findViewById(R.id.photo_viewer_text);
        body.setText(NoticeBody);
        title.setText(noticeTitle);


        viewParams=body.getLayoutParams();
       // getSupportActionBar().hide();
        Uri uri=getIntent().getData();


        expand_colapse=findViewById(R.id.expand_colapse);
        if(NoticeBody.length()>100)
            expand_colapse.setVisibility(View.VISIBLE);
        else expand_colapse.setVisibility(View.GONE);

        expand_colapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expanded)
                {

                   body.setMaxLines(2);
                    expand_colapse.setText("Read more..");

                }
                else
                {
                    body.setMaxLines(100);
                    expand_colapse.setText("Read less..");



                }

                expanded=!expanded;
            }
        });




        image=findViewById(R.id.image_view);

        backButton=findViewById(R.id.back_button);
        pDialog=new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE).setTitleText("Please wait")
                .setContentText("Please wait while we load the image");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pDialog.show();
        ;
     Picasso.get().
                load(uri)

                .placeholder(R.drawable.ic_face_white_24dp)
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Exception e) {
                       pDialog.dismiss();
                       image.setImageDrawable(getDrawable(R.drawable.ic_cancel));
                    }
                });

    }


    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}
