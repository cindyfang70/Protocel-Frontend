package com.example.protocel;

import android.app.AlertDialog;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ProtocolWebViewClient extends WebViewClient {

    ProtocolActivity mContext;

    public ProtocolWebViewClient(ProtocolActivity context) {
        this.mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Uri url = request.getUrl();
        String path = url.getPath(); // gives /popup/<popup id>


        if (path.contains("/popup/")) {
            // it is one of our popups
            // the path should be something like this, https://www.protocel.com/api/v1/popup/<popupid>
            // all we need to do is to use that url to get the popup text
            // TODO: Get the alert information and show it
            showAlert("This is a sample dialogue when clicking something like this");
            return true;
        }
        return false;
    }

    public void showAlert(String message) {
        new AlertDialog.Builder(this.mContext)
                .setTitle("Definition")
                .setMessage(message)
                .setNeutralButton("OK", null)
                .show();
    }
}
