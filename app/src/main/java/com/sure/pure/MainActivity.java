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
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.PopupMenu;
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


import com.sure.pure.common.CustomTypeface;
import com.sure.pure.common.GlobalClass;
import com.sure.pure.db.DatabaseHelper;
import com.sure.pure.fragments.Home;
import com.sure.pure.model.Product;
import com.sure.pure.utils.ProfilePicture;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static LayerDrawable icon;
    GlobalClass global;
    public static TextView title, cartcount, text;
    ImageView carticon, sorticon, profile;
    Point p;
    DatabaseHelper databaseHelper;
    int backPressedCount = 0;
    Typeface fonts, bold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        cartcount = (TextView) findViewById(R.id.cartcount);
        profile = (ImageView) findViewById(R.id.profile);
        carticon = (ImageView) findViewById(R.id.carticon);
        sorticon = (ImageView) findViewById(R.id.sorticon);
        fonts = Typeface.createFromAsset(getAssets(), "fonts/Monitorica_Rg.ttf");
        bold = Typeface.createFromAsset(getAssets(), "fonts/Monitorica_Bd.ttf");
        databaseHelper = new DatabaseHelper(getApplicationContext());
        setSupportActionBar(toolbar);
        title.setText("Home");
        title.setTypeface(bold);

        invalidateOptionsMenu();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);


            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.name);
        ImageView userpic = (ImageView) hView.findViewById(R.id.profile);


        Log.i("LLLLLLLLL","LLLLLLLLLL"+databaseHelper.getUser());

        if (databaseHelper.getSignup().equalsIgnoreCase("true")) {
            nav_user.setTypeface(bold);
            nav_user.setText("Hello User");
            nav_user.setText(databaseHelper.getUser().name);



                global.profile=new ProfilePicture(getApplicationContext(),databaseHelper.getUser().image).bitmap;
                userpic.setImageBitmap(global.profile);

        }else
        {
            userpic.setImageResource(R.drawable.users);
        }


        navigationView.setNavigationItemSelectedListener(this);

        selectFirstItemAsDefault();


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

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(databaseHelper.getSignup().equalsIgnoreCase("true"))
                {
                    onPopupButtonClickProfile(view);

                }else {
                    onPopupButtonClick(view);
                }
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if(id==R.id.changepass)
        {
            if(databaseHelper.getSignup()=="false")
            {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);


            }else {
                Intent i = new Intent(MainActivity.this, ChangePassword.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                finish();

            }
        }

        if (id == R.id.history) {

            if (databaseHelper.getSignup() == "false") {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);


            } else {

                Intent i = new Intent(MainActivity.this, Order_History.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                finish();

            }


        }

        if (id == R.id.logout) {

            databaseHelper.removeLogin();
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            finish();
        }

        if (id == R.id.profile) {
            if (databaseHelper.getSignup() == "false") {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);


            } else {

                Intent i = new Intent(MainActivity.this, Profile.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                finish();

            }

        }

        if (id == R.id.contactus) {

                Intent i = new Intent(MainActivity.this, ContactUs.class);
                 i.putExtra("url","file:///android_asset/contactus.html");
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);


            }



        return true;
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
        if(shareItem.getItemId()==R.id.profile)
        {
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
        if(signup.getItemId()==R.id.signup)
        {
            signup.setVisible(false);
        }

        if(login.getItemId()==R.id.login)
        {
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

    public byte[] sundarprofile()
    {
        Bitmap b= BitmapFactory.decodeResource(getResources(),R.drawable.sundar);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    public void privacyData(View view)
    {
        Intent i = new Intent(getApplicationContext(), WebActivity.class);
        i.putExtra("url","file:///android_asset/privacy.html");
        i.putExtra("name","privacy");
        startActivity(i);

    }

    public void webData(View view)
    {
        Intent i = new Intent(getApplicationContext(), WebActivity.class);
        i.putExtra("url","file:///android_asset/aboutus.html");
        i.putExtra("name","aboutus");
        startActivity(i);

    }
}
