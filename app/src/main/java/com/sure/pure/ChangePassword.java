package com.sure.pure;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sure.pure.common.GlobalClass;
import com.sure.pure.common.User;
import com.sure.pure.db.DatabaseHelper;
import com.sure.pure.utils.FileUploader;
import com.sure.pure.utils.WSUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Creative IT Works on 10-Jul-17.
 */

public class ChangePassword extends AppCompatActivity {
    Toolbar toolbar;
    Typeface fonts,bold;
    public static TextView title,cartcount;
    ImageView carticon;
    GlobalClass globalClass;
    DatabaseHelper databaseHelper;
    EditText oldpass,newpass;
    String pass;
    Button submit;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.title);
        submit=(Button)findViewById(R.id.submit);
        oldpass=(EditText) findViewById(R.id.old);
        newpass=(EditText)findViewById(R.id.newpass);
        cartcount=(TextView)findViewById(R.id.cartcount);
        carticon=(ImageView)findViewById(R.id.carticon);
        fonts = Typeface.createFromAsset(getAssets(), "fonts/Monitorica_Rg.ttf");
        bold= Typeface.createFromAsset(getAssets(), "fonts/Monitorica_Bd.ttf");
        globalClass=(GlobalClass)getApplicationContext();
        databaseHelper=new DatabaseHelper(getApplicationContext());
        user=databaseHelper.getUser();
        setSupportActionBar(toolbar);
        title.setText("Change Password");
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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("DAAAa","DAAAAaa"+oldpass.getText().toString()+"====="+databaseHelper.getUser().password);
                if(oldpass.getText().toString().equalsIgnoreCase(databaseHelper.getUser().password))
                {

                }else
                {
                    oldpass.setError("Please enter old passsword");
                }

                if(newpass.getText().toString().length()>3)
                {

                }else
                {
                    newpass.setError("Please enter new passsword");
                }

                if(oldpass.getText().toString().equalsIgnoreCase(databaseHelper.getUser().password) && newpass.getText().toString().length()>3)
                {
                    pass=newpass.getText().toString();

                    registerData();

                }
            }
        });


    }


    public void registerData() {

        class uploadTOserver extends AsyncTask<String, Void, String> {
            ProgressDialog dialog;
            String response = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(ChangePassword.this);
                dialog.setMessage("Loading..");
                dialog.show();
            }

            @Override
            protected String doInBackground(String[] params) {

                    /*String charset = "UTF-8";
                    //File uploadFile1 = new File("e:/Test/PIC1.JPG");
                    //File uploadFile2 = new File("e:/Test/PIC2.JPG");


                    String requestURL = "http://sridharchits.com/surepure/index.php/mobile/update_registration";
                    FileUploader multipart = new FileUploader(requestURL, charset, ChangePassword.this);



                    multipart.addFormField("oldpassword", globalClass.user.password);
                    multipart.addFormField("password", pass);



                    response = multipart.finish();
*/

                String charset = "UTF-8";
                HashMap<String,String> data=new HashMap<>();
                data.put("password",pass);
                data.put("user_id",user.id);


                String requestURL = "http://sridharchits.com/surepure/index.php/mobile/update_registration";
                WSUtils utils=new WSUtils();
                response= utils.getResultFromHttpRequest(requestURL,"POST",data);

                System.out.println("SERVER REPLIED:" + response);


                return response;
            }


            @Override
            protected void onPostExecute(String o) {

                dialog.dismiss();


                if (o.length()>0)
                {
                    User user1;

                        user1 = new User("1", user.name, user.email, user.mobile, "india", user.image, user.city, user.address, user.pincode,pass);



                        databaseHelper.updateUser(user1);



                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            ChangePassword.this);

                    // set title
                    alertDialogBuilder.setTitle("Alert");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Successfully Updated !!")
                            .setCancelable(false)

                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    dialog.dismiss();

                                    Intent i = new Intent(ChangePassword.this, Login.class);
                                    startActivity(i);
                                    finish();
                                }
                            })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            ChangePassword.this);

                    // set title
                    alertDialogBuilder.setTitle("Alert");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Please try again Later")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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



            }
        }
        new uploadTOserver().execute();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(ChangePassword.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }
}
