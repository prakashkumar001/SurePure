package com.sure.pure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sure.pure.common.GlobalClass;
import com.sure.pure.common.User;
import com.sure.pure.db.DatabaseHelper;
import com.sure.pure.fragments.Home;
import com.sure.pure.model.Product;
import com.sure.pure.pojo.CheckoutResponse;
import com.sure.pure.retrofit.APIInterface;
import com.sure.pure.utils.WSUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Created by Creative IT Works on 28-Jun-17.
 */

public class Checkout extends AppCompatActivity {



GlobalClass globalClass;
ImageView back;
Toolbar toolbar;
    ImageView home;
    DatabaseHelper databaseHelper;
    Typeface fonts, bold;
    TextView team,thanks,title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        back=(ImageView)findViewById(R.id.back);
        home=(ImageView)findViewById(R.id.home);
        team=(TextView) findViewById(R.id.team);
        thanks=(TextView) findViewById(R.id.thanks);
        title=(TextView) findViewById(R.id.title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fonts = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        bold = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        globalClass=(GlobalClass)getApplicationContext();
        globalClass.cartValues.clear();
        globalClass.productIDS.clear();

        thanks.setTypeface(fonts);
        team.setTypeface(fonts);
        title.setTypeface(fonts);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFirstItemAsDefault();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Checkout.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        ActivityCompat.finishAffinity(Checkout.this);
        finish();
    }

    private void selectFirstItemAsDefault() {

        Intent i=new Intent(Checkout.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        ActivityCompat.finishAffinity(Checkout.this);
        finish();

    }
}
