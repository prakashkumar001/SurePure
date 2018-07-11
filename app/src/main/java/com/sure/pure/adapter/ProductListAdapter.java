package com.sure.pure.adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sure.pure.MainActivity;
import com.sure.pure.ProductDetailPage;
import com.sure.pure.R;
import com.sure.pure.common.GlobalClass;
import com.sure.pure.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by v-62 on 10/19/2016.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {


    List<String> products=new ArrayList<>();
    List<Product> product=new ArrayList<>();
    List<Product> filterlist=new ArrayList<>();
    Map<String,String> hashmap=new HashMap<>();
    Context ctx;
    String listformat;
    String title;
    int count=0;
    GlobalClass global;
    ArrayList<String> titles;
    String sort;
     ImageLoader loader;

    Typeface fonts,bold;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView offerprice,productname,sellerprice;
        public ImageView image;
        public TextView add;

        public MyViewHolder(View view) {
            super(view);
            sellerprice = (TextView) view.findViewById(R.id.sellerprice);
            offerprice = (TextView) view.findViewById(R.id.offerprice);
            image = (ImageView) view.findViewById(R.id.image);
            add=(TextView)view.findViewById(R.id.addtocart);
            productname= (TextView) view.findViewById(R.id.product);

        }
    }


    public ProductListAdapter(Context context, List<Product> productlist, String item, String listformat) {


        this.listformat=listformat;

        ctx=context;
        global=(GlobalClass)ctx;
        titles=new ArrayList<String>();
        this.product=productlist;
        sort=item;
        Log.i("Log","Log"+listformat);
        loader=ImageLoader.getInstance();
        fonts = Typeface.createFromAsset(ctx.getAssets(), "fonts/Monitorica_Rg.ttf");
        bold= Typeface.createFromAsset(ctx.getAssets(), "fonts/Monitorica_Bd.ttf");

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=null;
        if(listformat.equalsIgnoreCase("list"))
        {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_list_item, parent, false);
        }else if(listformat.equalsIgnoreCase("grid"))
        {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid, parent, false);
        }


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.cart_image) // resource or drawable
                .showImageForEmptyUri(R.drawable.cart_image) // resource or drawable
                .showImageOnFail(R.drawable.cart_image) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(100)
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .build();

        //Product product = moviesList[position];

        if(sort.equalsIgnoreCase("A-Z"))
        {



            Collections.sort(product, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    return lhs.getCategory().compareTo(rhs.getCategory());
                }
            });

        }else if(sort.equalsIgnoreCase("Price Low-High"))
        {
            Collections.sort(product,new Product.OrderByAmountdouble());
        }else if(sort.equalsIgnoreCase("Z-A"))
        {
            Collections.sort(product, Collections.reverseOrder(new Product.OrderByCustomer()));
        }else if(sort.equalsIgnoreCase("Price High-Low"))
        {
            Collections.sort(product, Collections.reverseOrder(new Product.OrderByAmountdouble()));
        }
        String seller = ctx.getResources().getString(R.string.Rupees);
        //String offer = ctx.getResources().getString(R.string.seventyrupees);


        holder.sellerprice.setText(seller + product.get(position).getSellerprice());
        //holder.offerprice.setText(seller + product.get(position).getOfferprice());
        holder.productname.setText(product.get(position).getCategory());
       // holder.sellerprice.setPaintFlags(holder.sellerprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.sellerprice.setTypeface(fonts);
        //holder.offerprice.setTypeface(fonts);
        holder.productname.setTypeface(bold);
        holder.add.setTypeface(bold);

        //String images=String.valueOf(drawables[position]);
        //loader.displayImage(images,holder.image,options);
        //holder.image.setImageResource(product.get(position).getProductimage());
       // holder.image.setImageResource(product.get(position).getProductimage());

        try
        {
            loader.displayImage("http://sridharchits.com/surepure/uploads/products/"+product.get(position).getProductimage(),holder.image,options);

        }catch (Exception e)
        {

        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ctx,ProductDetailPage.class);
              /*  i.putExtra("name",product.get(position).getProductname());
                i.putExtra("id",product.get(position).getProduct_id());
                i.putExtra("sellerprice",product.get(position).getSellerprice());
                i.putExtra("offerprice",product.get(position).getOfferprice());
                i.putExtra("description",product.get(position).getProductdes());
                i.putExtra("image",product.get(position).getProductimage());*/
              i.putExtra("product",product.get(position));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ctx.startActivity(i);

            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(ctx,"DGSGDFSDFF",Toast.LENGTH_SHORT).show();
                String seller = ctx.getResources().getString(R.string.Rupees);
                //String offer = ctx.getResources().getString(R.string.seventyrupees);

                if(global.productIDS.contains(product.get(position).getProduct_id()))
                {
                    Toast.makeText(ctx,"Already added in the cart", Toast.LENGTH_SHORT).show();

                }else
                {
                    Product products=new Product();
                    products.setProduct_id(product.get(position).getProduct_id());
                    products.setProductimage(product.get(position).getProductimage());
                    products.setSellerprice(product.get(position).getSellerprice());
                    products.setProductdes(product.get(position).getProductdes());
                    products.setOfferprice(product.get(position).getOfferprice());
                    products.setProductname(product.get(position).getProductname());
                    products.setTotalprice(product.get(position).getOfferprice());
                    products.setQuantity(1);
                    global.productIDS.add(product.get(position).getProduct_id());
                    global.cartValues.add(products);
                    count=global.cartValues.size();
                    String value= String.valueOf(count);
                    Log.i("Count","Count"+ product.get(position).getProductname());
                    global.BadgeCount=value;

                   /* MainActivity.setBadgeCount(ctx, MainActivity.icon,value);
                    global.BadgeCount=value;
                   */ Toast.makeText(ctx,product.get(position).getProductname()+" "+"Added in the cart", Toast.LENGTH_SHORT).show();
                    // notifyDataSetChanged();

                    MainActivity.cartcount.setVisibility(View.VISIBLE);
                    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(ctx,R.animator.flip);
                    set.setTarget(MainActivity.cartcount);
                    MainActivity.cartcount.setText(global.BadgeCount);
                    set.start();


                }





            }
        });

    }

    @Override
    public int getItemCount() {
        return product.size();
    }


    public void setFilter(List<Product> countryModels){
        product = new ArrayList<>();
        product.addAll(countryModels);
        notifyDataSetChanged();

    }


}
