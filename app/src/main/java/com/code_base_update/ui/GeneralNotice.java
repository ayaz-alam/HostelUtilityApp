package com.code_base_update.ui;


import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.ui.BaseActivity;
import com.medeveloper.ayaz.hostelutility.R;


public class GeneralNotice extends BaseActivity {

    ProgressBar mProgressBar;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

        enableNavigation();
        WebView myWebView = findViewById(R.id.general_notice);
        mProgressBar=findViewById(R.id.progress_bar);
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.setWebViewClient(new MyBrowser());
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.loadUrl("https://ctae.ac.in");
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_general_notice;
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            mProgressBar.setVisibility(View.VISIBLE);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            mProgressBar.setVisibility(View.GONE);
        }


    }


}
