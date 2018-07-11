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
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import com.sure.pure.common.GlobalClass;
import com.sure.pure.model.Product;

import java.io.Serializable;
import java.util.List;

/**
 * Created by v-62 on 10/26/2016.
 */

public class ProductDetailPage extends AppCompatActivity {
   // String name,sellerprice,offerprice,description,product_id;
    String images;
    TextView productname,price,productdescription,quantity;
    ViewPager viewPager;
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
    Product p;
    Typeface fonts,bold;
    Button next,previous;
    List<Product> productList;
    int position=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_page);
        next=(Button) findViewById(R.id.next);
        previous=(Button)findViewById(R.id.previous);

        productname=(TextView)findViewById(R.id.productname);
        price=(TextView)findViewById(R.id.price);
        quantity=(TextView)findViewById(R.id.quantity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
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
        fonts = Typeface.createFromAsset(getAssets(), "fonts/Monitorica_Rg.ttf");
        bold= Typeface.createFromAsset(getAssets(), "fonts/Monitorica_Bd.ttf");
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
        title.setText("HTC Furniture");
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
          /*  name=i.getStringExtra("name");
            sellerprice=i.getStringExtra("sellerprice");
            offerprice=i.getStringExtra("offerprice");
            description=i.getStringExtra("description");
            images=i.getStringExtra("image");
            product_id=i.getStringExtra("id");*/
             p=(Product)i.getSerializableExtra("product");
             position=i.getIntExtra("position",1);
             productList=(((List<Product>) getIntent().getExtras().getSerializable("productList")));
           // image.setImageResource(images);

            if(position==0)
            {
                previous.setVisibility(View.INVISIBLE);
            }else
            {
                previous.setVisibility(View.VISIBLE);

            }

            if(position==productList.size()-1)
            {
                next.setVisibility(View.INVISIBLE);
            }else
            {
                next.setVisibility(View.VISIBLE);

            }

            productname.setText(p.getProductname());
            String seller = getApplicationContext().getResources().getString(R.string.Rupees);
            price.setText(seller + p.getSellerprice());
            productdescription.setText(p.getProductdes());

            productname.setTypeface(fonts);
            price.setTypeface(fonts);
            productdescription.setTypeface(fonts);


            // productdescription.setText(description);

        }catch (Exception e)
        {

        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProductDetailPage.class);
                i.putExtra("product",productList.get(position+1));
                i.putExtra("position",position+1);
                i.putExtra("productList", (Serializable) productList);

                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                finish();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProductDetailPage.class);
                i.putExtra("product",productList.get(position-1));
                i.putExtra("position",position-1);
                i.putExtra("productList", (Serializable) productList);

                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                finish();
            }
        });


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // global.cartValues.get(position).setQuantity(value+1);
                value= Integer.parseInt(quantity.getText().toString());
                if(!p.getSellerprice().equals("0"))
                {
                    add.setVisibility(View.VISIBLE);

                }
                    value=value+1;
                    quantity.setText(String.valueOf(value));

                   b=value* Double.parseDouble(p.getSellerprice());
                    //total.setText(String.valueOf(b));
                tv.setText(String.format("%.2f",b));

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




                     b=value* Double.parseDouble(p.getSellerprice());
                    tv.setText(String.format("%.2f",b));
                    //total.setText(String.valueOf(b));

                }else
                {
                    if(!p.getSellerprice().equals("0"))
                    {
                        add.setVisibility(View.VISIBLE);

                    }                    value=value-1;
                    if(value==0)
                    {
                        add.setVisibility(View.INVISIBLE);
                    }
                    quantity.setText(String.valueOf(value));

                     b=value* Double.parseDouble(p.getSellerprice());
                    tv.setText(String.format("%.2f",b));

                    //total.setText(String.valueOf(b));

                }

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seller = getApplication().getResources().getString(R.string.Rupees);
                //String offer = ctx.getResources().getString(R.string.seventyrupees);

                Log.i("ProductID","ProductId"+p.getProduct_id());
                Log.i("Gloabal","Gloabal"+global.cartValues);





                  if(global.productIDS.contains(p.getProduct_id()))
                  {
                      Toast.makeText(getApplicationContext(),"Product Added in Cart", Toast.LENGTH_SHORT).show();
                     for(int i=0;i<global.cartValues.size();i++)
                     {
                         if(global.cartValues.get(i).getProduct_id().equalsIgnoreCase(p.getProduct_id()))
                         {
                             global.cartValues.get(i).setQuantity(Integer.parseInt(quantity.getText().toString()));

                         }
                     }


                  }else
                  {
                      Product products=new Product();
                      products.setProduct_id(p.getProduct_id());
                      //products.setProductimage(images);
                      products.setProductname(p.getProductname());
                      products.setSellerprice(p.getSellerprice());
                      products.setOfferprice(p.getOfferprice());
                      products.setProductimage(images);
                      products.setQuantity(Integer.parseInt(quantity.getText().toString()));
                      products.setTotalprice(String.valueOf(b));
                      global.cartValues.add(products);
                      global.productIDS.add(p.getProduct_id());
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

        viewPager.setAdapter(new CustomPagerAdapter(this));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.



         tv = new TextView(this);
        tv.setText(String.format("%.2f",b));
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
        ActivityCompat.finishAffinity(ProductDetailPage.this);
        finish();
    }

    public class CustomPagerAdapter extends PagerAdapter {

        private Context mContext;
        String[]   drawables=new String[]{p.getProductimage(),p.getProductimage(),p.getProductimage()};
        public CustomPagerAdapter(Context context) {
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {

            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_item, collection, false);
            ImageView imageView=(ImageView)layout.findViewById(R.id.image);
            //imageView.setImageResource(drawables[position]);
            loader.displayImage("http://www.boolfox.com/rest"+drawables[position],imageView);
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return drawables.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


    }
}
