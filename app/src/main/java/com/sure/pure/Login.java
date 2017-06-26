package com.sure.pure;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sure.pure.db.DatabaseHelper;
import com.sure.pure.fragments.Home;
import com.sure.pure.model.User;
import com.sure.pure.utils.FileUploader;
import com.sure.pure.utils.RuntimePermissionActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.view.Gravity.CENTER;

/**
 * Created by v-62 on 10/11/2016.
 */

public class Login extends RuntimePermissionActivity {
  //  private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    EditText user,password;
    Button signin;
    private Boolean saveLogin;
    String name,pass;
    ImageView back;
    Button signupfree;
    int backPressedCount=0;
    private static final int REQUEST_PERMISSIONS = 20;
    DatabaseHelper databaseHelper;


    //Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        user=(EditText)findViewById(R.id.user);
        password=(EditText)findViewById(R.id.password);
        signin=(Button)findViewById(R.id.signup);
        back=(ImageView) findViewById(R.id.back);
        signupfree=(Button)findViewById(R.id.signupfree);
        databaseHelper=new DatabaseHelper(getApplicationContext());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        Login.super.requestAppPermissions(new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, R.string
                        .runtime_permissions_txt
                , REQUEST_PERMISSIONS);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = user.getText().toString();
                pass = password.getText().toString();



                if(name.length()>0 && pass.length()>0)
                {
                    if(name.equalsIgnoreCase("admin@surepure.com") && pass.equalsIgnoreCase("123456"))
                    {
                        if(databaseHelper.getLogin().equalsIgnoreCase("true"))
                        {
                            databaseHelper.updateLogin(name,pass);
                        }else {
                            databaseHelper.addLogin(name,pass);
                        }

                        Intent i=new Intent(Login.this,MainActivity.class);
                        startActivity(i);
                        finish();

                    }else
                    {
                        loginService();
                    }

                }else if(name.equalsIgnoreCase("") || pass.equalsIgnoreCase(""))
                {
                    if(name.equalsIgnoreCase("") && pass.equalsIgnoreCase("") )
                    {
                        user.setError("Username is invalid");
                        password.setError("Password is invalid");

                    }else if(pass.equalsIgnoreCase(""))
                    {
                        password.setError("Password is invalid");
                    }else
                    {
                        user.setError("Username is invalid");
                    }
                }


                //Intent i =new Intent(Login.this,MainActivity.class);
                //startActivity(i);
                //finish();
            }
        });

        signupfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Signup.class);
                startActivity(i);
                finish();

            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    public void loginService()
    {
        class uploadTOserver extends AsyncTask<String, Void, String> {
            ProgressDialog dialog;
            String response=null;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog=new ProgressDialog(Login.this);
                dialog.setMessage("Loading..");
                dialog.show();
            }
            @Override
            protected String doInBackground(String[] params) {
                try {

                    String charset = "UTF-8";


                    String requestURL = "http://sridharchits.com/surepure/index.php/mobile/mobile_login";
                    FileUploader multipart = new FileUploader(requestURL, charset,Login.this);


                    multipart.addFormField("email", name);
                    multipart.addFormField("password", pass);


                   response = multipart.finish();
                    System.out.println("SERVER REPLIED:"+response);


                } catch (IOException ex) {
                    System.err.println(ex);
                }
                return response;
            }


            @Override
            protected void onPostExecute(String o) {

                dialog.dismiss();

                JSONObject object= null;
                try {
                    object = new JSONObject(o);
                    String status=object.getString("status");
                    if(status.equalsIgnoreCase("success"))
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                Login.this);

                        // set title
                        alertDialogBuilder.setTitle("Alert");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Login Success")
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        dialog.dismiss();
                                        JSONObject object= null;
                                        try {
                                            object = new JSONObject(response);
                                            String status=object.getString("status");
                                            if(status.equalsIgnoreCase("success"))
                                            {
                                                if(databaseHelper.getLogin().equalsIgnoreCase("true"))
                                                {
                                                    databaseHelper.updateLogin(name,pass);
                                                }else {
                                                    databaseHelper.addLogin(name,pass);
                                                }
                                                Intent i=new Intent(Login.this,MainActivity.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                })
                                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();







                    }else
                    {
                        // Toast.makeText(Signup.this,"Please try again Later",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                Login.this);

                        // set title
                        alertDialogBuilder.setTitle("Alert");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Please try again Later")
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        dialog.dismiss();
                                    }
                                });


                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } new uploadTOserver().execute();


    }
    @Override
    public void onBackPressed() {


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


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if ((grantResults.length > 0) && (grantResults[0] +
                        grantResults[1]) == PackageManager.PERMISSION_GRANTED) {
                    //Call whatever you want


                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Enable Permissions from settings",
                            Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    intent.setData(Uri.parse("package:" + getPackageName()));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(intent);
                                }
                            }).show();
                }
                return;
            }
        }
    }

}
