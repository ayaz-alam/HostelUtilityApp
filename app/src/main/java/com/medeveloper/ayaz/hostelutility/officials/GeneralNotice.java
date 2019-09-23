package com.medeveloper.ayaz.hostelutility.officials;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.medeveloper.ayaz.hostelutility.R;


public class GeneralNotice extends Fragment {

    public GeneralNotice() {
        // Required empty public constructor
    }

    View rootView;
    ProgressBar mProgressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_general_notice, container, false);

        WebView myWebView = rootView.findViewById(R.id.general_notice);

        mProgressBar=rootView.findViewById(R.id.progress_bar);


        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.setWebViewClient(new MyBrowser());
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.loadUrl("https://ctae.ac.in");
        mProgressBar.setVisibility(View.VISIBLE);


        return rootView;
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
