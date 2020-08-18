package com.example.protocel;

import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ProtocolWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Uri url = request.getUrl();
        String path = url.getPath(); // gives /popup/<popup id>


        if (path.contains("/popup/")) {
            // it is one of our popups
            // the path should be something like this, https://www.protocel.com/api/v1/popup/<popupid>
            // all we need to do is to use that url to get the popup text

            return true;
        }
        return false;
    }
}
