package com.sure.pure;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import com.sure.pure.common.GlobalClass;
import com.sure.pure.model.Product;

/**
 * Created by v-62 on 10/26/2016.
 */

public class ProductDetailPage extends AppCompatActivity {
    String name,sellerprice,offerprice,description,product_id;
    String images;
    TextView productname,price,productdescription,quantity;

    ImageView image,plus,minus;
    Button add;
   // Toolbar toolbar;
    public static LayerDrawable icon;
    public static MenuItem itemCart;
    GlobalClass global;
    ImageLoader loader;
    int count=0;
    int value;
    double b;
    TextView tv;
    Toolbar toolbar;
    public static TextView title,cartcount;
    ImageView carticon;
    Typeface fonts,bold;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_page);
        productname=(TextView)findViewById(R.id.productname);
        price=(TextView)findViewById(R.id.price);
        quantity=(TextView)findViewById(R.id.quantity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  total=(TextView)findViewById(R.id.total);
        productdescription=(TextView)findViewById(R.id.description);
        image=(ImageView)findViewById(R.id.image);
        plus=(ImageView)findViewById(R.id.plus);
        minus=(ImageView)findViewById(R.id.minus);
        add=(Button)findViewById(R.id.cart);
        global=(GlobalClass)getApplicationContext();

        title=(TextView)findViewById(R.id.title);
        cartcount=(TextView)findViewById(R.id.cartcount);
        carticon=(ImageView)findViewById(R.id.carticon);
        fonts = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa_Regular.ttf");
        bold= Typeface.createFromAsset(getAssets(), "fonts/Comfortaa_Bold.ttf");
        loader=ImageLoader.getInstance();

        add.setVisibility(View.INVISIBLE);

        add.setTypeface(fonts);

        if(global.cartValues.size()>0)
        {
            cartcount.setVisibility(View.VISIBLE);
            cartcount.setText(String.valueOf(global.cartValues.size()));
        }else {
            cartcount.setVisibility(View.GONE);
        }

        cartcount.setTypeface(fonts);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        title.setText("Sure Pure");
        title.setTypeface(bold);
       // getSupportActionBar().setIcon(R.drawable.surelogo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });

        carticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CartPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   /* HomeFragment comedy=new HomeFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, comedy, comedy.getClass().getSimpleName()).commit();
*/

                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        try{
            Intent i=getIntent();
            name=i.getStringExtra("name");
            sellerprice=i.getStringExtra("sellerprice");
            offerprice=i.getStringExtra("offerprice");
            description=i.getStringExtra("description");
            images=i.getStringExtra("image");
            product_id=i.getStringExtra("id");
           // image.setImageResource(images);
            loader.displayImage("http://sridharchits.com/surepure/uploads/products/"+images,image);
            productname.setText(name);
            String seller = getApplicationContext().getResources().getString(R.string.Rupees);
            price.setText(seller + offerprice);
            productdescription.setText(description);

            productname.setTypeface(fonts);
            price.setTypeface(fonts);
            productdescription.setTypeface(fonts);


            // productdescription.setText(description);

        }catch (Exception e)
        {

        }



        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // global.cartValues.get(position).setQuantity(value+1);
                value= Integer.parseInt(quantity.getText().toString());
                add.setVisibility(View.VISIBLE);
                    value=value+1;
                    quantity.setText(String.valueOf(value));

                   b=value* Double.parseDouble(offerprice);
                    //total.setText(String.valueOf(b));
                tv.setText(String.valueOf(b));

                tv.setTypeface(fonts);



            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // global.cartValues.get(position).setQuantity(value+1);
                value= Integer.parseInt(quantity.getText().toString());
                if(value<=0)
                {
                    value=0;
                    add.setVisibility(View.INVISIBLE);
                    quantity.setText(String.valueOf(value));




                     b=value* Double.parseDouble(offerprice);
                    tv.setText(String.valueOf(b));
                    //total.setText(String.valueOf(b));

                }else
                {
                    add.setVisibility(View.VISIBLE);
                    value=value-1;
                    if(value==0)
                    {
                        add.setVisibility(View.INVISIBLE);
                    }
                    quantity.setText(String.valueOf(value));

                     b=value* Double.parseDouble(offerprice);
                    tv.setText(String.valueOf(b));

                    //total.setText(String.valueOf(b));

                }

            }
        });
      /*  toolbar = (Toolbar) findViewById(R.id.toolbar);

            setSupportActionBar(toolbar);

*/

       /* getSupportActionBar().setTitle(null);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
      /*  toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   *//* HomeFragment comedy=new HomeFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, comedy, comedy.getClass().getSimpleName()).commit();
*//*

                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });*/

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seller = getApplication().getResources().getString(R.string.Rupees);
                //String offer = ctx.getResources().getString(R.string.seventyrupees);

                Log.i("ProductID","ProductId"+product_id);
                Log.i("Gloabal","Gloabal"+global.cartValues);


              /*  for(int i=0;i<global.cartValues.size();i++)
                {
                    if(global.cartValues.get(i).getProduct_id().equalsIgnoreCase(product_id))
                    {
                        Toast.makeText(getApplicationContext(),"Already in Cart",Toast.LENGTH_SHORT).show();
                        break;
                    }else
                    {

                    }
                }*/


                  if(global.productIDS.contains(product_id))
                  {
                      Toast.makeText(getApplicationContext(),"Product Added in Cart", Toast.LENGTH_SHORT).show();
                     for(int i=0;i<global.cartValues.size();i++)
                     {
                         if(global.cartValues.get(i).getProduct_id().equalsIgnoreCase(product_id))
                         {
                             global.cartValues.get(i).setQuantity(Integer.parseInt(quantity.getText().toString()));

                         }
                     }


                  }else
                  {
                      Product products=new Product();
                      products.setProduct_id(product_id);
                      //products.setProductimage(images);
                      products.setProductname(name);
                      products.setSellerprice(sellerprice);
                      products.setOfferprice(offerprice);
                      products.setProductimage(images);
                      products.setQuantity(Integer.parseInt(quantity.getText().toString()));
                      products.setTotalprice(String.valueOf(b));
                      global.cartValues.add(products);
                      global.productIDS.add(product_id);
                      count=global.cartValues.size();
                      String value= String.valueOf(count);
                      Log.i("Count","Count"+value);
                     /* MainActivity.setBadgeCount(getApplicationContext(),MainActivity.icon,value);
                      global.BadgeCount=value;
                      setBadgeCount(getApplicationContext(),icon,global.BadgeCount);
*/

                      cartcount.setVisibility(View.VISIBLE);
                      AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(ProductDetailPage.this,R.animator.flip);
                      set.setTarget(cartcount);
                      cartcount.setText(String.valueOf(global.cartValues.size()));
                      set.start();
                  }






            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.



         tv = new TextView(this);
        tv.setText(String.valueOf(b));
        tv.setTextColor(getResources().getColor(android.R.color.white));
        tv.setPadding(5, 0, 5, 0);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextSize(14);
        tv.setTypeface(fonts);
        menu.add(0, 1, 1, R.string.Rupees).setActionView(tv).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        invalidateOptionsMenu();
        return true;
    }




   /* public int totalvalue()
    {
        String seller = getApplicationContext().getResources().getString(R.string.Rupees);
        int totalValue=0;
        for(int i=0;i<global.cartValues.size();i++)
        {
            String price=global.cartValues.get(i).getOfferprice();
            String[] arr=price.split(seller+"");
            String pr=arr[1];

            Log.i("text","text"+pr);
            int value=Integer.parseInt(pr) * global.cartValues.get(i).getQuantity();
            totalValue=totalValue + value;

        }

        return totalValue;
    }*/


    @Override
    public void onBackPressed() {

        Intent i=new Intent(ProductDetailPage.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}
