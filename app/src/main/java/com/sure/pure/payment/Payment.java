package com.sure.pure.payment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.sure.pure.Checkout;
import com.sure.pure.MainActivity;
import com.sure.pure.R;

/**
 * Created by Creative IT Works on 03-Jul-17.
 */

public class Payment extends AppCompatActivity {

    Button submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        submit=(Button)findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Payment.this, MainActivity.class);
                startActivity(i);
                ActivityCompat.finishAffinity(Payment.this);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
