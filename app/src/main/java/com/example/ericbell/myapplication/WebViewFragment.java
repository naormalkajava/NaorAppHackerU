package com.example.ericbell.myapplication;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ericbell.myapplication.R;

import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {


    public static WebViewFragment newInstance(String link) {

        Bundle args = new Bundle();
        args.putString("link",link);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    WebView webViewID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web_view, container, false);

        String link = getArguments().getString("link");
        webViewID = (WebView) v.findViewById(R.id.wbViewId);
       webconfig(webViewID);
        webViewID.loadUrl(link);
        return v;

    }

    private void webconfig(final WebView webViewID) {
        WebSettings settings = webViewID.getSettings();
        settings.setJavaScriptEnabled(true);
        webViewID.setWebViewClient(new WebViewClient(){

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                webViewID.loadUrl(url);
                webViewID.animate().rotation(30).rotation(0);
               return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webViewID.loadUrl(url);
                return false;
            }
        });



    }


}
