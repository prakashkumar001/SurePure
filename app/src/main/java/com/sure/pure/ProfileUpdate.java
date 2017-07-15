package com.sure.pure;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.sure.pure.common.GlobalClass;
import com.sure.pure.common.User;
import com.sure.pure.db.DatabaseHelper;
import com.sure.pure.utils.CircleImage;
import com.sure.pure.utils.FilePickUtils;
import com.sure.pure.utils.FileUploader;

import com.sure.pure.utils.RuntimePermissionActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;



public class ProfileUpdate extends RuntimePermissionActivity {
    EditText name, email, phone, citys, pins, address;
    Button update;
    String user, pass, city, pin, emails, phoneno, add;
    ImageView back;
    CircleImage profile;
    String filePath = null;
    Bitmap bitmap;
    private static final int REQUEST_PERMISSIONS = 20;
    // Toolbar toolbar;
    private int PICK_IMAGE_REQUEST = 1;
    DatabaseHelper databaseHelper;
    GlobalClass global;
    Typeface fonts, bold;
    public static TextView title, cartcount;
    ImageView carticon;
    User detail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_update);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        citys = (EditText) findViewById(R.id.city);
        pins = (EditText) findViewById(R.id.pin);
        update = (Button) findViewById(R.id.signup);
        back = (ImageView) findViewById(R.id.back);
        profile = (CircleImage) findViewById(R.id.profileimage);
        title=(TextView)findViewById(R.id.title);
        cartcount=(TextView)findViewById(R.id.cartcount);
        carticon=(ImageView)findViewById(R.id.carticon);
        fonts = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa_Regular.ttf");
        bold = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa_Bold.ttf");
        title.setText("Sign Up");
        title.setTypeface(bold);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        global = (GlobalClass) getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        title.setText("Profile Update");
        title.setTypeface(bold);

        detail=databaseHelper.getUser();

        ProfileUpdate.super.requestAppPermissions(new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, R.string
                        .runtime_permissions_txt
                , REQUEST_PERMISSIONS);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                Intent i = new Intent(getApplicationContext(), Profile.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                finish();
            }
        });

        if (global.cartValues.size() > 0) {


            cartcount.setVisibility(View.VISIBLE);
            cartcount.setText(String.valueOf(global.cartValues.size()));


        } else {
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



        profile.setImageBitmap(global.profile);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        name.setTypeface(fonts);
        phone.setTypeface(fonts);
        email.setTypeface(fonts);
        citys.setTypeface(fonts);
        pins.setTypeface(fonts);
        address.setTypeface(fonts);
        update.setTypeface(fonts);

        name.setText(detail.name);
        phone.setText(detail.mobile);
        email.setText(detail.email);
        citys.setText(detail.city);
        pins.setText(detail.pincode);
        address.setText(detail.address);



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = name.getText().toString();
                phoneno = phone.getText().toString();
                emails = email.getText().toString();
                city = citys.getText().toString();
                pin = pins.getText().toString();
                add = address.getText().toString();


                if (user.length() > 3 || emails.length() > 3 || phoneno.length() > 3 || city.length() > 3 || pin.length() > 5)
                {

                        registerData();


                } else {
                    if ((user.length()) > 3) {

                    } else {
                        name.setError("Please Enter Correct name");
                    }


                    if ((phoneno.length()) == 10) {

                    } else {
                        phone.setError("Please Enter Correct number");
                    }

                    if (city.length() > 3) {

                    } else {
                        citys.setError("Please Enter Correct City");
                    }

                    if (pin.length() > 5) {

                    } else {
                        pins.setError("Please Enter Correct Pin");
                    }

                    if (add.length() > 5) {

                    } else {
                        address.setError("Please Enter Correct Address");
                    }

                    if ((emails.length()) > 0) {
                        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                        if (emails.matches(emailPattern)) {
                            Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
                            // or

                        } else {


                        }
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            filePath = FilePickUtils.getSmartFilePath(getApplicationContext(), uri);


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                profile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void registerData() {

        class uploadTOserver extends AsyncTask<String, Void, String> {
            ProgressDialog dialog;
            String response = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(ProfileUpdate.this);
                dialog.setMessage("Loading..");
                dialog.show();
            }

            @Override
            protected String doInBackground(String[] params) {
                try {

                    String charset = "UTF-8";
                    //File uploadFile1 = new File("e:/Test/PIC1.JPG");
                    //File uploadFile2 = new File("e:/Test/PIC2.JPG");


                    String requestURL = "http://sridharchits.com/surepure/index.php/mobile/update_registration";
                    FileUploader multipart = new FileUploader(requestURL, charset, ProfileUpdate.this);


                    File f = new File(filePath);


                    multipart.addFilePart("uploaded_file", f);


                    multipart.addFormField("uname", user);
                    multipart.addFormField("user_id", detail.id);
                    multipart.addFormField("email", emails);
                    multipart.addFormField("mobile", phoneno);
                    multipart.addFormField("city", city);
                    multipart.addFormField("address", add);
                    multipart.addFormField("pincode", pin);

                    Log.i("DDDDDDDDDDDD", "DDDDDDDDDDD" + requestURL + user + pass + phoneno + city + add);

                    response = multipart.finish();

                    System.out.println("SERVER REPLIED:" + response);


                    Log.i("DDDDDDDDDDDD", "DDDDDDDDDDD" + requestURL + user + pass + phoneno + city + add);

                    response = multipart.finish();

                    System.out.println("SERVER REPLIED:" + response);


                } catch (IOException ex) {
                    System.err.println(ex);
                }

                return response;
            }


            @Override
            protected void onPostExecute(String o) {

                dialog.dismiss();


                if (o.length()>0)
                {
                    User user1;
                    if (bitmap == null) {
                        user1 = new User("1", user, emails, phoneno, "india", sundarprofile(), city, add, pin,databaseHelper.getUser().password);

                    } else {
                        user1 = new User("1", user, emails, phoneno, "india", profiledata(), city, add, pin,databaseHelper.getUser().password);

                    }

                    global.user = user1;
                    if (databaseHelper.getSignup().equalsIgnoreCase("true")) {
                        databaseHelper.updateUser(global.user);
                    } else {
                        databaseHelper.addUser(global.user);
                    }


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            ProfileUpdate.this);

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

                                    Intent i = new Intent(ProfileUpdate.this, Login.class);
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
                            ProfileUpdate.this);

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

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    public byte[] profiledata()
    {
        // Bitmap resized = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
        Bitmap resized= resize(bitmap,1000,1000);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    public byte[] sundarprofile()
    {
        Bitmap b= BitmapFactory.decodeResource(getResources(),R.drawable.sundar);
        //Bitmap resized = Bitmap.createScaledBitmap(b, 300, 300, true);
        Bitmap resized= resize(b,1000,1000);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
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
}

