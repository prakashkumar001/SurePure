package com.sure.pure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.sure.pure.common.GlobalClass;
import com.sure.pure.db.DatabaseHelper;
import com.sure.pure.fragments.Delivered;
import com.sure.pure.fragments.Pending;
import com.sure.pure.model.Deliver;
import com.sure.pure.model.Pendings;
import com.sure.pure.utils.FileUploader;
import com.sure.pure.utils.WSUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Creative IT Works on 15-Jun-17.
 */

public class Order_History extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    GlobalClass globalClass;
    DatabaseHelper databaseHelper;
    Toolbar toolbar;
    Typeface fonts,bold;
    public static TextView title,cartcount;
    ImageView carticon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderhistory);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.title);
        cartcount=(TextView)findViewById(R.id.cartcount);
        carticon=(ImageView)findViewById(R.id.carticon);
        fonts = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa_Regular.ttf");
        bold= Typeface.createFromAsset(getAssets(), "fonts/Comfortaa_Bold.ttf");
        globalClass=(GlobalClass)getApplicationContext();
        databaseHelper=new DatabaseHelper(getApplicationContext());
        Log.i("user_id","user_id"+databaseHelper.getLoginid());
        uploadFile();
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);
        title.setText("Order History");
        title.setTypeface(bold);
        //getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                finish();
            }
        });

        if(globalClass.cartValues.size()>0)
        {


            cartcount.setVisibility(View.VISIBLE);
            cartcount.setText(String.valueOf(globalClass.cartValues.size()));



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

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Pending(), "PENDING");
        adapter.addFragment(new Delivered(), "DELIVERED");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    public void uploadFile() {


        class uploads extends AsyncTask<String, Void, String> {
            ProgressDialog dialog;
            String response;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog=new ProgressDialog(Order_History.this);
                dialog.setMessage("Loading..");
                dialog.show();
            }

            @Override
            protected String doInBackground(String[] params) {
                try {

                    HashMap<String,String> data=new HashMap<>();

                    data.put("user_id",databaseHelper.getUser().id);



                    String requestURL = "http://sridharchits.com/surepure/index.php/mobile/mobile_userhistory";
                    WSUtils utils=new WSUtils();
                    response= utils.getResultFromHttpRequest(requestURL,"POST",data);

                    System.out.println("SERVER REPLIED:"+response);




                } catch (Exception ex) {
                    System.err.println(ex);
                }
                return response;
            }



            @Override
            protected void onPostExecute(String o) {

                globalClass.deliverdata=new ArrayList<>();
                globalClass.pendingdata=new ArrayList<>();
                dialog.dismiss();

                try {
                    JSONArray array=new JSONArray(o);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        String orderid=object.getString("order_id");
                        String productname=object.getString("prd_name");
                        String productprice=object.getString("prd_price");
                        String quantity=object.getString("qty");
                        String payment_type=object.getString("payment_type");
                        String status=object.getString("status");
                        String payment_date=object.getString("payment_date");

                        if(status.equalsIgnoreCase("paid"))
                        {
                            globalClass.deliverdata.add(new Deliver(orderid,status,productname,productprice,quantity,payment_date,payment_type));
                        }else {
                            globalClass.pendingdata.add(new Pendings(orderid,status,productname,productprice,quantity,payment_date,payment_type));

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setupViewPager(viewPager);

                tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
                tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));


            }
        } new uploads().execute();

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
