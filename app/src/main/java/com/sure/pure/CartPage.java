package com.sure.pure;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.gson.Gson;
import com.sure.pure.adapter.CartpageAdapter;
import com.sure.pure.badge.BadgeDrawable;
import com.sure.pure.common.GlobalClass;

import static android.view.Gravity.BOTTOM;


/**
 * Created by v-62 on 10/21/2016.
 */

public class CartPage extends AppCompatActivity {
    public static RecyclerView recyclerView;
    CartpageAdapter adapter;
    Button placeorder;
    public static TextView total;
    public static LayerDrawable icon;
    public static MenuItem itemCart;
    GlobalClass global;
    Toolbar toolbar;
    LinearLayout place,totals,footerlay;
    TextView emptytext;
    public static TextView title,cartcount;
    ImageView carticon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartpage);
        global=(GlobalClass)getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        place = (LinearLayout) findViewById(R.id.place);
        totals = (LinearLayout) findViewById(R.id.totals);
        footerlay = (LinearLayout) findViewById(R.id.footerlay);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        emptytext=(TextView)findViewById(R.id.empty);
        cartcount=(TextView)findViewById(R.id.cartcount);
        carticon=(ImageView)findViewById(R.id.carticon);

          // getSupportActionBar().setIcon(R.drawable.logo);

        if(global.cartValues.size()>0)
        {
            place.setVisibility(View.VISIBLE);
            totals.setVisibility(View.VISIBLE);
            emptytext.setVisibility(View.INVISIBLE);
            footerlay.setVisibility(View.VISIBLE);
            cartcount.setText(String.valueOf(global.cartValues.size()));


        }else
        {
            place.setVisibility(View.INVISIBLE);
            totals.setVisibility(View.INVISIBLE);
            emptytext.setVisibility(View.VISIBLE);
            footerlay.setVisibility(View.INVISIBLE);
        }
            adapter=new CartpageAdapter(getApplicationContext(),global.cartValues);

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Sure Pure");
            //getSupportActionBar().setIcon(R.drawable.logo);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //What to do on back clicked
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            });
           /* toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   *//* HomeFragment comedy=new HomeFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, comedy, comedy.getClass().getSimpleName()).commit();
*//*

                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            });*/
            placeorder=(Button) findViewById(R.id.place_order);
            total=(TextView) findViewById(R.id.total);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);

            total.setText(String.valueOf(adapter.totalvalue()));
            placeorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  Intent i=new Intent(CartPage.this,ShippingAddress.class);
                    startActivity(i);
                    finish();
                    Gson gson = new Gson();
// This can be any object. Does not have to be an arraylist.
                    String json = gson.toJson(global.cartValues);

                    Log.i("GSON","GSON"+json);
*/

// How to retrieve your Java object back from the string
                    Intent i=new Intent(CartPage.this,CommingSoon.class);
                    startActivity(i);

                }
            });




    }

   /* public int totalvalue()
    {
        int totalValue=0;
        for(int i=0;i<global.cartValues.size();i++)
        {
            String price=global.cartValues.get(i).getOfferprice();
            String[] arr=price.split(" Rupees");
            String pr=arr[0];

            Log.i("text","text"+pr);
            int value=Integer.parseInt(pr);
            totalValue=totalValue + value;

        }

        return totalValue;
    }*/


    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

    }
}
