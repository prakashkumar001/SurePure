package com.sure.pure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.common.api.Api;
import com.sure.pure.adapter.DrawerAdapter;
import com.sure.pure.common.CustomTypeface;
import com.sure.pure.common.GlobalClass;
import com.sure.pure.db.DatabaseHelper;
import com.sure.pure.fragments.Home;
import com.sure.pure.model.Product;
import com.sure.pure.retrofit.APIInterface;
import com.sure.pure.utils.DrawerItem;
import com.sure.pure.utils.FileUploader;
import com.sure.pure.utils.ProfilePicture;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sure.pure.fragments.Delivered.adapter;

public class MainActivity extends AppCompatActivity
        {
    ArrayList<DrawerItem> drawerList;

    public static LayerDrawable icon;
    GlobalClass global;
    public static TextView title, cartcount, text;
    ImageView carticon, sorticon;
    Point p;
    DatabaseHelper databaseHelper;
    int backPressedCount = 0;
    Typeface fonts, bold;
    RecyclerView drawer;
    private ProgressDialog pDialog;
    public static DrawerLayout drawerlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (RecyclerView) findViewById(R.id.drawer);
        title = (TextView) findViewById(R.id.title);
        cartcount = (TextView) findViewById(R.id.cartcount);
        //profile = (ImageView) findViewById(R.id.profile);
        carticon = (ImageView) findViewById(R.id.carticon);
        sorticon = (ImageView) findViewById(R.id.sorticon);
        fonts = Typeface.createFromAsset(getAssets(), "fonts/Monitorica_Rg.ttf");
        bold = Typeface.createFromAsset(getAssets(), "fonts/Monitorica_Bd.ttf");
        databaseHelper = new DatabaseHelper(getApplicationContext());
        setSupportActionBar(toolbar);

        invalidateOptionsMenu();

        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.setDrawerListener(toggle);
        toggle.syncState();


        selectFirstItemAsDefault();


        getAllCategory();


        if (global.cartValues.size() > 0) {
            cartcount.setVisibility(View.VISIBLE);
            cartcount.setText(String.valueOf(global.cartValues.size()));
        } else {
            cartcount.setVisibility(View.GONE);
        }

        carticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CartPage.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                finish();
            }
        });

        sorticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SortActivity.class);
                startActivity(i);
            }
        });




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            if (backPressedCount == 1) {
                finish();
            } else {
                backPressedCount++;
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.press_again), Toast.LENGTH_SHORT);

                toast.show();
                new Thread() {
                    @Override
                    public void run() {
                        //super.run();
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            backPressedCount = 0;
                        }
                    }
                }.start();
            }
        } else {
            super.onBackPressed();
        }


    }




    private void selectFirstItemAsDefault() {


        Home comedy = new Home();
        Bundle bundle = new Bundle();
        comedy.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, comedy, comedy.getClass().getSimpleName()).commit();


    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //setBadgeCount(MainActivity.this, icon, global.BadgeCount);

        return super.onPrepareOptionsMenu(menu);
    }


    public void onPopupButtonClick(View button) {
        PopupMenu popup = new PopupMenu(this, button);
        popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());

        MenuItem shareItem = popup.getMenu().findItem(R.id.profile);
        if (shareItem.getItemId() == R.id.profile) {
            shareItem.setVisible(false);
        }


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {


                int id = item.getItemId();


                if (id == R.id.signup) {
                    Intent i = new Intent(getApplicationContext(), Signup.class);
                    startActivity(i);
                } else if (id == R.id.login) {
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                }


                return true;
            }
        });

        popup.show();
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Monitorica_Bd.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypeface("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void onPopupButtonClickProfile(View button) {
        PopupMenu popup = new PopupMenu(this, button);
        popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());


        MenuItem signup = popup.getMenu().findItem(R.id.signup);
        MenuItem login = popup.getMenu().findItem(R.id.login);
        if (signup.getItemId() == R.id.signup) {
            signup.setVisible(false);
        }

        if (login.getItemId() == R.id.login) {
            login.setVisible(false);
        }


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {


                int id = item.getItemId();
                if (id == R.id.profile) {
                    Intent i = new Intent(getApplicationContext(), Profile.class);
                    startActivity(i);
                }


                return true;
            }
        });

        popup.show();
    }


    public void privacyData(View view) {
        Intent i = new Intent(getApplicationContext(), WebActivity.class);
        //i.putExtra("url", "file:///android_asset/privacy.html");
        i.putExtra("url", "https://termsfeed.com/blog/privacy-policy-url-facebook-app/");

        i.putExtra("name", "privacy");
        startActivity(i);

    }

    public void webData(View view) {
        Intent i = new Intent(getApplicationContext(), WebActivity.class);
        //i.putExtra("url", "file:///android_asset/aboutus.html");
        i.putExtra("url", "https://www.facebook.com/pg/facebook/about/");

        i.putExtra("name", "aboutus");
        startActivity(i);

    }

    public void getAllCategory() {

        //Creating a retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        APIInterface api = retrofit.create(APIInterface.class);

        //now making the call object
        //Here we are using the api method that we created inside the api interface
        Call<List<DrawerItem>> call = api.getCategoryList();
        // Set up progress before call

        call.enqueue(new Callback<List<DrawerItem>>() {


            @Override
            public void onResponse(Call<List<DrawerItem>> call, retrofit2.Response<List<DrawerItem>> response) {

                List<DrawerItem> categoryList = response.body();
                Home home=new Home();

                Log.i("RESPONSE","RESPONSE"+categoryList);
                DrawerAdapter adapter = new DrawerAdapter(MainActivity.this,categoryList,home);
                drawer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                drawer.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<DrawerItem>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

            }


}
