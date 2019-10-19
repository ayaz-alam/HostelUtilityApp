package com.code_base_update.ui;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.ui.BaseActivity;
import com.medeveloper.ayaz.hostelutility.R;


public class GeneralNotice extends BaseActivity {

    ProgressBar mProgressBar;
    ConstraintLayout connectionLayout;
    WebView myWebView;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initViewsAndEvents() {

        setupToolbar("College Notice");
        enableNavigation();
        mProgressBar = findViewById(R.id.progress_bar);
        connectionLayout = findViewById(R.id.connectionLayout);

        myWebView = findViewById(R.id.general_notice);
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.setWebViewClient(new MyBrowser());
        myWebView.setWebChromeClient(new MyChromeClient());
        myWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setHorizontalScrollBarEnabled(false);
        myWebView.setVerticalScrollBarEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        if (!isConnected(this)) {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    mProgressBar.setVisibility(View.GONE);
                    connectionLayout.setVisibility(View.VISIBLE);
                }
            }, 1000);

        } else {
            connectionLayout.setVisibility(View.GONE);
            myWebView.loadUrl("https://ctae.ac.in");
            myWebView.setOnTouchListener(new View.OnTouchListener() {
                float m_downX;

                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getPointerCount() > 1) {
                        //Multi touch detected
                        return true;
                    }

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            // save the x
                            m_downX = event.getX();
                            break;
                        }
                        case MotionEvent.ACTION_MOVE:
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_UP: {
                            // set x so that it doesn't move
                            event.setLocation(m_downX, event.getY());
                            break;
                        }

                    }
                    return false;
                }
            });
        }
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private class MyChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            mProgressBar.setProgress(progress);
            if (progress == 100) {
                mProgressBar.setVisibility(View.GONE);

            } else {
                mProgressBar.setVisibility(View.VISIBLE);

            }
        }
    }

    private class MyBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
//            mProgressBar.setVisibility(View.VISIBLE);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_general_notice;
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            mProgressBar.setVisibility(View.VISIBLE);
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
