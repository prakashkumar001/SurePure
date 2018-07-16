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

import com.sure.pure.common.GlobalClass;
import com.sure.pure.common.User;
import com.sure.pure.db.DatabaseHelper;
import com.sure.pure.fragments.Home;
import com.sure.pure.model.Product;
import com.sure.pure.payment.PayuMoneyActivity;
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

import static com.sure.pure.fragments.Home.layoutchange1;

/**
 * Created by Creative IT Works on 28-Jun-17.
 */

public class Checkout extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton cash,card_payment;
    Button continue_button;
    Toolbar toolbar;
    Typeface fonts,bold;
    public static TextView title,cartcount;
    ImageView carticon;
    String PaymentType="";
    TextView shipping,paymentdetails,nametext,phonetext,emailtext,addresstext;



    DatabaseHelper databaseHelper;
    GlobalClass global;
    String Total;

    TextView name,email,address,phone;
User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);


        Intent i=getIntent();
        Total=i.getStringExtra("Total");



        name=(TextView)findViewById(R.id.name);
        email=(TextView)findViewById(R.id.email);
        address=(TextView)findViewById(R.id.address);
        phone=(TextView)findViewById(R.id.phone);


        nametext=(TextView)findViewById(R.id.nametext);
        phonetext=(TextView)findViewById(R.id.emailtext);
        emailtext=(TextView)findViewById(R.id.addresstext);
        addresstext=(TextView)findViewById(R.id.phonetext);
        paymentdetails=(TextView)findViewById(R.id.paymentdetails);
        shipping=(TextView)findViewById(R.id.shipping);





        radioGroup=(RadioGroup) findViewById(R.id.myRadioGroup);
        cash=(RadioButton) findViewById(R.id.cash);
        continue_button=(Button)findViewById(R.id.continue_button);
        card_payment=(RadioButton) findViewById(R.id.card);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.title);
        cartcount=(TextView)findViewById(R.id.cartcount);
        carticon=(ImageView)findViewById(R.id.carticon);
        fonts = Typeface.createFromAsset(getAssets(), "fonts/Monitorica_Rg.ttf");
        bold= Typeface.createFromAsset(getAssets(), "fonts/Monitorica_Bd.ttf");
        global=(GlobalClass)getApplicationContext();
        databaseHelper=new DatabaseHelper(getApplicationContext());
        setSupportActionBar(toolbar);
        title.setText("Check Out");
        title.setTypeface(bold);
        TextView privacy = (TextView) findViewById(R.id.privacy);
        TextView aboutus = (TextView) findViewById(R.id.aboutus);
        TextView copyrights = (TextView) findViewById(R.id.copyrights);
        copyrights.setTypeface(bold);
        privacy.setTypeface(bold);
        aboutus.setTypeface(bold);
        //getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        user=databaseHelper.getUser();
        name.setText(user.name);
        email.setText(user.email);
        phone.setText(user.mobile);
        address.setText(user.address+"\n"+user.pincode);

        name.setTypeface(bold);
        email.setTypeface(bold);
        phone.setTypeface(bold);
        address.setTypeface(bold);

        nametext.setTypeface(bold);
        emailtext.setTypeface(bold);
        phonetext.setTypeface(bold);
        addresstext.setTypeface(bold);

        paymentdetails.setTypeface(bold);
        cash.setTypeface(bold);
        shipping.setTypeface(bold);
        continue_button.setTypeface(bold);




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


                    PaymentType="Cash";

                } else if (checkedId == R.id.card) {

                    PaymentType="Card";
                   // addToOrders();




                }

            }
        });


        continue_button.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(PaymentType.equalsIgnoreCase("Card"))
                {
                    Intent i = new Intent(getApplicationContext(), PayuMoneyActivity.class);
                    i.putExtra("Total",Total);
                    startActivity(i);
                }else

                {
                }


            }
        });

    }

   /* public void addToOrders() {
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
*/

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
