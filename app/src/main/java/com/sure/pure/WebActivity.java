package com.sure.pure;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by Prakash on 7/15/2017.
 */

public class WebActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weblay);
        Intent i=getIntent();

        String url=i.getStringExtra("url");
        webView=(WebView)findViewById(R.id.webview);

        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

