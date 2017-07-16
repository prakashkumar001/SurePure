package com.sure.pure;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import com.sure.pure.common.GlobalClass;
import com.sure.pure.db.DatabaseHelper;
import com.sure.pure.fragments.Home;
import com.sure.pure.common.User;
import com.sure.pure.utils.FileUploader;
import com.sure.pure.utils.InternetPermissions;
import com.sure.pure.utils.RuntimePermissionActivity;
import com.sure.pure.utils.WSUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.maxHeight;
import static android.R.attr.maxWidth;
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
    public String names,pass;
    ImageView back;
    Button signupfree;

    private static final int REQUEST_PERMISSIONS = 20;
    DatabaseHelper databaseHelper;
   GlobalClass global;
    InternetPermissions internetPermissions;
    private static final int NETPERMISSION = 1888;
    Typeface fonts,bold;
    TextView title,account;

    //Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        user=(EditText)findViewById(R.id.user);
        password=(EditText)findViewById(R.id.password);
        signin=(Button)findViewById(R.id.signup);
        back=(ImageView) findViewById(R.id.back);
        account=(TextView) findViewById(R.id.account);
        signupfree=(Button)findViewById(R.id.signupfree);
        databaseHelper=new DatabaseHelper(getApplicationContext());
        global=(GlobalClass)getApplicationContext();
        title=(TextView)findViewById(R.id.title);
        fonts = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa_Regular.ttf");
        bold= Typeface.createFromAsset(getAssets(), "fonts/Comfortaa_Bold.ttf");
        title.setText("Login");
        title.setTypeface(bold);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        Login.super.requestAppPermissions(new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE}, R.string
                        .runtime_permissions_txt
                , REQUEST_PERMISSIONS);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        user.setTypeface(fonts);
        password.setTypeface(fonts);
        signin.setTypeface(fonts);
        signupfree.setTypeface(fonts);
        account.setTypeface(fonts);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names = user.getText().toString();
                pass = password.getText().toString();



                if(names.length()>0 && pass.length()>0)
                {
                    internetPermissions=new InternetPermissions(Login.this);
                    if(internetPermissions.isInternetOn())
                    {
                        loginService();
                    }else
                    {

                        Snackbar snack= Snackbar.make(findViewById(android.R.id.content), "No Internet Connection",Snackbar.LENGTH_LONG);
                        View vv=snack.getView();
                        TextView textView=(TextView)vv.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setGravity(Gravity.CENTER);
                        snack.show();
                        snack.setAction("Enable", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(internetPermissions.isInternetOn())
                                {
                                    loginService();
                                }else
                                {
                                    startActivityForResult(new Intent(Settings.ACTION_SETTINGS),NETPERMISSION);
                                }
                            }
                        });


                    }



                }else if(names.equalsIgnoreCase("") || pass.equalsIgnoreCase(""))
                {
                    if(names.equalsIgnoreCase("") && pass.equalsIgnoreCase("") )
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
        class uploadTOserver extends AsyncTask<String, String, String> {
            ProgressDialog dialog;
            String response="";
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
                    HashMap<String,String> data=new HashMap<>();
                    data.put("email",names);
                    data.put("password",pass);


                    String requestURL = "http://sridharchits.com/surepure/index.php/mobile/mobile_login";
                      WSUtils utils=new WSUtils();
                     response= utils.getResultFromHttpRequest(requestURL,"POST",data);

                    System.out.println("SERVER REPLIED:"+response);

                    dialog.dismiss();


                } catch (Exception ex) {
                    System.err.println(ex);
                }
                return response;
            }


            @Override
            protected void onPostExecute(String o) {


                Log.i("DATADATADATA","DATADATADATA"+response);
                try {

                    JSONArray object = new JSONArray(response);


                    for(int i=0;i<object.length();i++)
                    {



                    JSONObject object1=object.getJSONObject(0);
                    String id=object1.getString("id");
                    String name=object1.getString("uname");
                    String email=object1.getString("email");
                    String phone=object1.getString("mobile");
                    String city=object1.getString("city");
                    String address=object1.getString("address");
                    String country=object1.getString("country");
                    String pincode=object1.getString("pincode");



                        User user1;
                        if(databaseHelper.getSignup().equalsIgnoreCase("true"))
                        {

                             user1=new User(id,name,email,phone,country,databaseHelper.getUser().image,city,address,pincode,pass);
                            global.user=user1;
                            databaseHelper.updateUser(global.user);
                        }else
                        {
                            user1=new User(id,name,email,phone,country,profiledata(),city,address,pincode,pass);
                            global.user=user1;
                            databaseHelper.addUser(global.user);
                        }






                    }
                    if(global.user.name.length()>0)
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                Login.this);

                        // set title

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Welcome to Sure Pure")
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        dialog.dismiss();


                                        new Handler().postDelayed(new Runnable() {
                                            public void run() {

                                                Login.this.finish();
                                                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);


                                                Intent mainIntent = new Intent(
                                                        Login.this,
                                                        MainActivity.class);

                                                Login.this.startActivity(mainIntent);


                                                ActivityCompat.finishAffinity(Login.this);

                                            }
                                        }, 500);




                                            }



                                });


                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }else {
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

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Login.this);

                    // set title
                    alertDialogBuilder.setTitle("Alert");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Invalid email or password")
                            .setCancelable(false)
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    user.setText("");
                                    password.setText("");

                                    dialog.dismiss();
                                }
                            });


                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                    e.printStackTrace();
                }

            }
        } new uploadTOserver().execute();


    }
    @Override
    public void onBackPressed() {

        finish();




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
    public byte[] profiledata()
    {
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.sundar);
        //Bitmap resized = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
        Bitmap bb=resize(bitmap,1000,1000);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bb.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    public byte[] sundarprofile()
    {
        Bitmap b= BitmapFactory.decodeResource(getResources(),R.drawable.sundar);



       Bitmap bb=resize(b,1000,1000);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bb.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public void privacyData(View view)
    {
        Intent i = new Intent(getApplicationContext(), WebActivity.class);
        i.putExtra("url","file:///android_asset/privacy.html");
        startActivity(i);

    }

    public void webData(View view)
    {
        Intent i = new Intent(getApplicationContext(), WebActivity.class);
        i.putExtra("url","file:///android_asset/aboutus.html");
        startActivity(i);

    }
}
