package com.sure.pure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.google.gson.Gson;
import com.sure.pure.common.GlobalClass;
import com.sure.pure.db.DatabaseHelper;
import com.sure.pure.payment.Payment;
import com.sure.pure.payment.PayuMoneyActivity;
import com.sure.pure.utils.WSUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Creative IT Works on 28-Jun-17.
 */

public class Checkout extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton cash,card_payment;
    GlobalClass global;
    Button continue_button;
    DatabaseHelper database;
    String total;
    Toolbar toolbar;
    Typeface fonts,bold;
    public static TextView title,cartcount;
    ImageView carticon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);


        Intent i=getIntent();
        total=i.getStringExtra("Total");

        radioGroup=(RadioGroup) findViewById(R.id.myRadioGroup);
        cash=(RadioButton) findViewById(R.id.cash);
        continue_button=(Button)findViewById(R.id.continue_button);
        card_payment=(RadioButton) findViewById(R.id.card);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.title);
        cartcount=(TextView)findViewById(R.id.cartcount);
        carticon=(ImageView)findViewById(R.id.carticon);
        fonts = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa_Regular.ttf");
        bold= Typeface.createFromAsset(getAssets(), "fonts/Comfortaa_Bold.ttf");
        global=(GlobalClass)getApplicationContext();
        database=new DatabaseHelper(getApplicationContext());
        setSupportActionBar(toolbar);
        title.setText("Check Out");
        title.setTypeface(bold);
        //getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                Intent i=new Intent(getApplicationContext(),CartPage.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                finish();
            }
        });


        if(global.cartValues.size()>0)
        {


            cartcount.setVisibility(View.VISIBLE);
            cartcount.setText(String.valueOf(global.cartValues.size()));



        }else
        {
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


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected

                if (checkedId == R.id.cash) {

                    Toast.makeText(getApplicationContext(), "You selected Cash",

                            Toast.LENGTH_SHORT).show();

                } else if (checkedId == R.id.card) {


                   // addToOrders();



                }

            }
        });


        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToOrders();

                Intent i=new Intent(Checkout.this, PayuMoneyActivity.class);
                i.putExtra("Total",total);
                startActivity(i);
            }
        });

    }

    public void addToOrders() {
        class uploadTOserver extends AsyncTask<String, String, String> {
            ProgressDialog dialog;
            String response = "";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(Checkout.this);
                dialog.setMessage("Loading..");
                dialog.show();
            }

            @Override
            protected String doInBackground(String[] params) {
                try {

                    String charset = "UTF-8";


                    JSONObject object;
                    global.jsonArraydetails=new JSONArray();

                    for(int i=0;i<global.cartValues.size();i++)
                    {
                        object=new JSONObject();
                        try{
                            object.put("product_id",global.cartValues.get(i).getProduct_id());
                            object.put("product_price",global.cartValues.get(i).getOfferprice());
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
                        arr.put(database.getUser().id,global.jsonArraydetails);
                    }catch (Exception e)
                    {

                    }


                    Log.i("JSSSSSSSSSSOOOO","JSSSSSSSSSSSOOO"+arr.toString());


                   String requestURL = "http://sridharchits.com/surepure/index.php/mobile/addto_cart/";
                    WSUtils utils = new WSUtils();
                    response = utils.responsedetailsfromserver(requestURL,arr.toString());

                    System.out.println("SERVER REPLIED:" + response);

                    dialog.dismiss();


                } catch (Exception ex) {
                    System.err.println(ex);
                }
                return response;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }
        new uploadTOserver().execute();

    }

}
