package com.sure.pure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.sure.pure.common.GlobalClass;
import com.sure.pure.db.DatabaseHelper;
import com.sure.pure.fragments.Home;
import com.sure.pure.model.Product;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static MenuItem itemCart;
    public static LayerDrawable icon;
    GlobalClass global;
   public static TextView title,cartcount;
    ImageView carticon,sorticon,profile;
    Point p;
    DatabaseHelper databaseHelper;
    int backPressedCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.title);
        cartcount=(TextView)findViewById(R.id.cartcount);
        profile=(ImageView)findViewById(R.id.profile);
        carticon=(ImageView)findViewById(R.id.carticon);
        sorticon=(ImageView)findViewById(R.id.sorticon);
        databaseHelper=new DatabaseHelper(getApplicationContext());
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sure Pure");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.name);
        ImageView userpic = (ImageView)hView.findViewById(R.id.profile);
        if(databaseHelper.getSignup().equalsIgnoreCase("true"))
        {
            nav_user.setText(databaseHelper.getUser().name);
            userpic.setImageResource(R.drawable.sundar);

        }



        navigationView.setNavigationItemSelectedListener(this);

        selectFirstItemAsDefault();


        if(global.cartValues.size()>0)
        {
            cartcount.setVisibility(View.VISIBLE);
            cartcount.setText(String.valueOf(global.cartValues.size()));
        }else {
            cartcount.setVisibility(View.GONE);
        }

        carticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CartPage.class);
                startActivity(i);
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

                onPopupButtonClick(view);
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
                Toast toast= Toast.makeText(getApplicationContext(), getResources().getString(R.string.press_again), Toast.LENGTH_SHORT);

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
        } else{
            super.onBackPressed();
        }



    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.history)
        {

            if(databaseHelper.getSignup()=="false")
            {
                Intent i=new Intent(MainActivity.this,Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);



            }else {

                Intent i=new Intent(MainActivity.this,Order_History.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                finish();

            }



        }

        if(id==R.id.logout)
        {

            databaseHelper.removeLogin();
            Intent i=new Intent(MainActivity.this,Login.class);
            startActivity(i);
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            finish();
        }

         if(id==R.id.profile)
        {
            if(databaseHelper.getSignup()=="false")
            {
                Intent i=new Intent(MainActivity.this,Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);



            }else {

                Intent i=new Intent(MainActivity.this,Profile.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                finish();

            }

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                int id=item.getItemId();
                if(id==R.id.signup)
                {
                    Intent i = new Intent(getApplicationContext(), Signup.class);
                    startActivity(i);
                }else if(id==R.id.login)
                {
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                }

                return true;
            }
        });

        popup.show();
    }


}
