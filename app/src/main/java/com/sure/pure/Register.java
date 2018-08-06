package com.sure.pure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sure.pure.adapter.DrawerAdapter;
import com.sure.pure.common.PrefManager;
import com.sure.pure.fragments.Home;
import com.sure.pure.pojo.CheckoutResponse;
import com.sure.pure.retrofit.APIInterface;
import com.sure.pure.utils.DrawerItem;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    EditText phoneNumber,Email;
    Button submit;
    String macAddress;
    String email,mobile;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        phoneNumber=(EditText)findViewById(R.id.mobile);
        Email=(EditText)findViewById(R.id.email);
        submit=(Button) findViewById(R.id.register);



    }

  String  getMacAddress()
    {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
        }
        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        macAddress=  getMacAddress();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isValidEmail(Email.getText().toString()) || !isValidPhone(phoneNumber.getText().toString()))
                {
                    if(!isValidEmail(Email.getText().toString()))
                    {

                        Email.setError("Error");
                    }else
                    {

                    }

                    if(!isValidPhone(phoneNumber.getText().toString()))
                    {
                        phoneNumber.setError("Error");


                    }else
                    {

                    }

                }else if(isValidEmail(Email.getText().toString()) && isValidPhone(phoneNumber.getText().toString()))
                {
                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                    email=Email.getText().toString();
                    mobile=phoneNumber.getText().toString();
                    registerTOServer();
                }
            }
        });


    }

    void registerTOServer()
    {
        //Creating a retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        APIInterface api = retrofit.create(APIInterface.class);

        //now making the call object
        //Here we are using the api method that we created inside the api interface
        Call<CheckoutResponse> call = api.register(mobile,email,macAddress);
        // Set up progress before call

        call.enqueue(new Callback<CheckoutResponse>() {


            @Override
            public void onResponse(Call<CheckoutResponse> call, retrofit2.Response<CheckoutResponse> response) {


                CheckoutResponse result=response.body();
                if(result.getMessage().equalsIgnoreCase("Not Registered"))
                {
                    Toast.makeText(getApplicationContext(),result.getMessage(),Toast.LENGTH_SHORT).show();
                }else
                {
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString("mobile", mobile);
                    editor.putString("email", email);
                    editor.putString("macaddress", macAddress);
                    editor.commit();
                    Intent i=new Intent(Register.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public boolean isValidPhone(CharSequence phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }
}
