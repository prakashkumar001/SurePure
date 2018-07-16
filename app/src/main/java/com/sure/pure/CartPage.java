package com.sure.pure;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sure.pure.adapter.CartpageAdapter;
import com.sure.pure.common.GlobalClass;
import com.sure.pure.db.DatabaseHelper;
import com.sure.pure.pojo.CheckoutResponse;
import com.sure.pure.retrofit.APIInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by v-62 on 10/21/2016.
 */

public class CartPage extends AppCompatActivity {
    public static RecyclerView recyclerView;
    public static TextView total, sub,gstamount;
    public static LayerDrawable icon;
    public static MenuItem itemCart;
    public static TextView title, cartcount;
    CartpageAdapter adapter;
    Button placeorder, back;
    GlobalClass global;
    Toolbar toolbar;
    LinearLayout footerlay;
    TextView emptytext;
    ImageView carticon;
    DatabaseHelper databaseHelper;
    Typeface fonts, bold;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartpage);
        global = (GlobalClass) getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        emptytext = (TextView) findViewById(R.id.empty);
        cartcount = (TextView) findViewById(R.id.cartcount);
        carticon = (ImageView) findViewById(R.id.carticon);
        title = (TextView) findViewById(R.id.title);
        fonts = Typeface.createFromAsset(getAssets(), "fonts/Monitorica_Rg.ttf");
        bold = Typeface.createFromAsset(getAssets(), "fonts/Monitorica_Bd.ttf");
        databaseHelper = new DatabaseHelper(getApplicationContext());
        // getSupportActionBar().setIcon(R.drawable.logo);

        if (global.cartValues.size() > 0) {

            emptytext.setVisibility(View.INVISIBLE);
            cartcount.setVisibility(View.VISIBLE);
            cartcount.setText(String.valueOf(global.cartValues.size()));


        } else {

            emptytext.setVisibility(View.VISIBLE);
            cartcount.setVisibility(View.GONE);
        }
        adapter = new CartpageAdapter(CartPage.this, global.cartValues);

        setSupportActionBar(toolbar);
        title.setText("HTC Furniture");
        title.setTypeface(bold);
        //getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
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
        placeorder = (Button) findViewById(R.id.place_order);
        back = (Button) findViewById(R.id.back);
        sub = (TextView) findViewById(R.id.sub);
        total = (TextView) findViewById(R.id.total);
        gstamount= (TextView) findViewById(R.id.gstamount);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);


        total.setTypeface(bold);
        sub.setTypeface(bold);
        placeorder.setTypeface(bold);
        back.setTypeface(bold);
        sub.setText(String.format ("%.2f",adapter.totalvalue()));
        total.setText(String.format ("%.2f",getTotal()));
        gstamount.setText(String.format("%.2f",getgst()));
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                  addToOrders();




            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                finish();
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


    @Override
    public void onBackPressed() {
       /* Intent i=new Intent(getApplicationContext(),MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
*/
    }

    public double getTotal()
    {
        double gst =adapter.totalvalue()*18/100;
        gstamount.setText(String.format("%.2f",gst));
        double total=adapter.totalvalue()+gst;

        return total;
    }

    public double getgst()
    {
        double gst =adapter.totalvalue()*18/100;
        gstamount.setText(String.format("%.2f",gst));

        return gst;
    }

    public void addToOrders() {
        //Creating a retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        APIInterface api = retrofit.create(APIInterface.class);

        JSONObject object;
        global.jsonArraydetails=new JSONArray();

        for(int i=0;i<global.cartValues.size();i++)
        {
            object=new JSONObject();
            try{
                object.put("product_id",global.cartValues.get(i).getProduct_id());
                object.put("product_price",global.cartValues.get(i).getSellerprice());
                object.put("product_quantity",global.cartValues.get(i).getQuantity());
                object.put("product_total",global.cartValues.get(i).getTotalprice());
                object.put("product_name",global.cartValues.get(i).getProductname());
                global.jsonArraydetails.put(object);

            }catch (Exception e)
            {

            }
        }

        JSONObject arr=new JSONObject();
        try {
            arr.put("checkout",global.jsonArraydetails);
            arr.put("mobilenumber","9962526526");
        }catch (Exception e)
        {

        }


        Log.d("addToOrders", "addToOrders: "+arr.toString());
        Log.d("addToOrders", "addToOrders: "+arr.toString());

        //now making the call object
        //Here we are using the api method that we created inside the api interface
        Call<CheckoutResponse> call = api.checkout(arr);
        call.enqueue(new Callback<CheckoutResponse>() {


            @Override
            public void onResponse(Call<CheckoutResponse> call, retrofit2.Response<CheckoutResponse> response) {









            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
