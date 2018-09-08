package com.sure.pure;

import android.app.ProgressDialog;
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

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.sure.pure.adapter.CartpageAdapter;
import com.sure.pure.common.GlobalClass;
import com.sure.pure.db.DatabaseHelper;
import com.sure.pure.model.Product;
import com.sure.pure.pojo.CheckoutResponse;
import com.sure.pure.pojo.ProductData;
import com.sure.pure.pojo.ProductList;
import com.sure.pure.retrofit.APIInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.StringReader;
import java.util.ArrayList;

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
    TextView emptytext,gsttext,subtotaltext,totaltext;
    ImageView carticon,home;
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
        fonts = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        bold = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        databaseHelper = new DatabaseHelper(getApplicationContext());
        // getSupportActionBar().setIcon(R.drawable.logo);
        home=(ImageView)findViewById(R.id.home);

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


        gsttext = (TextView) findViewById(R.id.gsttext);
        subtotaltext = (TextView) findViewById(R.id.subtotaltext);
        totaltext = (TextView) findViewById(R.id.totaltext);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);



        totaltext.setTypeface(bold);
        subtotaltext.setTypeface(bold);
        gsttext.setTypeface(bold);
        total.setTypeface(bold);
        sub.setTypeface(bold);
        gstamount.setTypeface(bold);
        placeorder.setTypeface(bold);
        back.setTypeface(bold);
        double roundoffsubtotal=Math.round(adapter.subtotalvalue());
        sub.setText(String.format ("%.2f",roundoffsubtotal));

        double roundofftotal=Math.round(adapter.totalvalue());
        total.setText(String.format ("%.2f",roundofftotal));
        double roundoffgstamount=Math.round(adapter.getgst());
        gstamount.setText(String.format("%.2f",roundoffgstamount));
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(global.cartValues.size()>0)
                {
                    addToOrders();
                }else
                {
                    Toast.makeText(CartPage.this,"Please add product to continue",Toast.LENGTH_SHORT).show();
                }






            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);*/
                Intent data = new Intent();
               // String text = "Result to be returned...."

                setResult(RESULT_OK, data);
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
               // finish();
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

        // Set up progress before call
        final ProgressDialog dialog;
        dialog = new ProgressDialog(CartPage.this);
        dialog.setMessage("Loading...");
        // show it
        dialog.show();
        //Creating a retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        APIInterface api = retrofit.create(APIInterface.class);

        JSONObject object;
        global.jsonArraydetails=new JSONArray();

        ArrayList<ProductData> productData=new ArrayList<>();
        for(int i=0;i<global.cartValues.size();i++)
        {

            productData.add(new ProductData(global.cartValues.get(i).getProduct_id(),global.cartValues.get(i).getSellerprice(),global.cartValues.get(i).getQuantity(),global.cartValues.get(i).getTotalprice(),global.cartValues.get(i).getProductname()));

        }
        ProductList productList=new ProductList();
        productList.setEmailid("akshav00@gmail.com");
        productList.setProductData(productData);





        Gson gson = new Gson();
        String product=gson.toJson(productList);
        ProductList data = gson.fromJson(product, ProductList.class);



        Log.d("addToOrders", "addToOrders: "+data);
        Log.d("addToOrders", "addToOrders: "+data);

        //now making the call object
        //Here we are using the api method that we created inside the api interface

        Call<CheckoutResponse> call = api.checkout(data);
        call.enqueue(new Callback<CheckoutResponse>() {


            @Override
            public void onResponse(Call<CheckoutResponse> call, retrofit2.Response<CheckoutResponse> response) {

                dialog.dismiss();
                CheckoutResponse result=response.body();
                if(result.getStatus().equals("200"))
                {
                    Intent i=new Intent(CartPage.this,Checkout.class);
                    startActivity(i);
                    finish();
                }







            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
