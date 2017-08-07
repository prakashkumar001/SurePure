package com.sure.pure;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

/**
 * Created by Prakash on 7/15/2017.
 */

public class WebActivity extends AppCompatActivity {
    WebView webView;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weblay);

        Intent i=getIntent();

        String url=i.getStringExtra("url");
        String name=i.getStringExtra("name");

        webView=(WebView)findViewById(R.id.webview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(name.equalsIgnoreCase("privacy"))
        {
            getSupportActionBar().setTitle("Privacy Policy");
        }else if(name.equalsIgnoreCase("aboutus"))
        {
            getSupportActionBar().setTitle("About Us");
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

