package com.sure.pure;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sure.pure.common.User;
import com.sure.pure.db.DatabaseHelper;

/**
 * Created by Creative IT Works on 09-Jun-17.
 */

public class Profile extends AppCompatActivity {

    TextView name,email,phonenumber,pinnumber,address,address_info,personal;
    DatabaseHelper databaseHelper;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    User user;
    Typeface font;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        databaseHelper=new DatabaseHelper(getApplicationContext());
        user=databaseHelper.getUser();
        name=(TextView)findViewById(R.id.name);
        address_info=(TextView)findViewById(R.id.address_info);
        personal=(TextView)findViewById(R.id.personal);
        email=(TextView)findViewById(R.id.email);
        phonenumber=(TextView)findViewById(R.id.phone);
        pinnumber=(TextView)findViewById(R.id.pin);
        address=(TextView)findViewById(R.id.address);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

         font = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa_Regular.ttf");
        collapsingToolbarLayout.setTitle(user.name);


        dynamicToolbarColor();

        toolbarTextAppernce();

        name.setTypeface(font);
        email.setTypeface(font);
        phonenumber.setTypeface(font);
        pinnumber.setTypeface(font);
        address.setTypeface(font);

        Typeface fonts = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa_Bold.ttf");
        personal.setTypeface(fonts);
        address_info.setTypeface(fonts);


        name.setText(user.name);
        email.setText(user.email);
        phonenumber.setText(user.mobile);
        pinnumber.setText(user.pincode);
        address.setText(user.address);


    }

    private void dynamicToolbarColor() {

       /* Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.water1);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {*/
                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark));
          /*  }
        });*/
    }


    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTypeface(font);
        collapsingToolbarLayout.setExpandedTitleTypeface(font);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }

}
