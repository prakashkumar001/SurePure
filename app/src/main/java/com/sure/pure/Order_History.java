package com.sure.pure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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


import com.sure.pure.common.GlobalClass;
import com.sure.pure.fragments.Delivered;
import com.sure.pure.fragments.Pending;
import com.sure.pure.model.Deliver;
import com.sure.pure.model.Pendings;
import com.sure.pure.utils.FileUploader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creative IT Works on 15-Jun-17.
 */

public class Order_History extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    GlobalClass globalClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderhistory);
        globalClass=(GlobalClass)getApplicationContext();

        uploadFile();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));





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
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog=new ProgressDialog(Order_History.this);
                dialog.setMessage("Loading..");
                dialog.show();
            }

            @Override
            protected String doInBackground(String[] params) {
                String charset = "UTF-8";
                //File uploadFile1 = new File("e:/Test/PIC1.JPG");
                //File uploadFile2 = new File("e:/Test/PIC2.JPG");

                String response=null;
                String requestURL = "http://sridharchits.com/surepure/index.php/mobile/mobile_userhistory";

                try {

                    FileUploader multipart = new FileUploader(requestURL, charset,getApplicationContext());
                      multipart.addFormField("userid","7");

                    response = multipart.finish();

                    globalClass.deliverdata=new ArrayList<>();
                    globalClass.pendingdata=new ArrayList<>();

                    System.out.println("SERVER REPLIED:"+response);

                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        String id=object.getString("id");
                        String status=object.getString("status");
                        String message=object.getString("message");

                        if(status.equalsIgnoreCase("deliverd"))
                        {
                            globalClass.deliverdata.add(new Deliver(id,status,message));
                        }else {
                            globalClass.pendingdata.add(new Pendings(id,status,message));
                        }
                    }


                    dialog.dismiss();


                } catch (IOException ex) {
                    System.err.println(ex);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return response;
            }


            @Override
            protected void onPostExecute(String o) {

                dialog.dismiss();
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
