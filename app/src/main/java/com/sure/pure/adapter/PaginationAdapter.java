package com.sure.pure.adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Suleiman on 19/10/16.
 */

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;


    private boolean isLoadingAdded = false;
    List<Product> product=new ArrayList<>();
    Context ctx;
    String listformat;
    String title;
    int count=0;
    GlobalClass global;
    String sort="Price Low-High";
    ImageLoader loader;

    Typeface fonts,bold;
    public PaginationAdapter(Context context) {
        this.ctx = context;
        product = new ArrayList<>();
    }

    public List<Product> getMovies() {
        return product;
    }

    public void setMovies(List<Product> product) {
        this.product = product;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {



        switch (getItemViewType(position)) {
            case ITEM:
                MovieVH movieVH = (MovieVH) holder;

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

               /* if(sort.equalsIgnoreCase("Quantity High-Low"))
                {



                    Collections.sort(product,new Product.OrderByQuantityHigh());


                }else if(sort.equalsIgnoreCase("Price Low-High"))
                {
                    Collections.sort(product,new Product.OrderByAmountdouble());
                }else if(sort.equalsIgnoreCase("Quantity Low-High"))
                {
                    Collections.sort(product, Collections.reverseOrder(new Product.OrderByQuantityHigh()));
                }else if(sort.equalsIgnoreCase("Price High-Low"))
                {
                    Collections.sort(product, Collections.reverseOrder(new Product.OrderByAmountdouble()));
                }*/
                String seller = ctx.getResources().getString(R.string.Rupees);
                //String offer = ctx.getResources().getString(R.string.seventyrupees);


                movieVH.sellerprice.setText(seller + product.get(position).getSellerprice());
                //movieVH.offerprice.setText(seller + product.get(position).getOfferprice());
                if(product.get(position).getSellerprice().equals("0"))
                {
                    movieVH.outofstock.setText("Out of Stock");
                    movieVH.sellerprice.setVisibility(View.GONE);
                    movieVH.add.setVisibility(View.GONE);

                }else
                {
                    movieVH.outofstock.setVisibility(View.GONE);

                }
                // movieVH.sellerprice.setPaintFlags(movieVH.sellerprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                movieVH.productname.setText(product.get(position).getProductname());

                movieVH.sellerprice.setTypeface(fonts);
                //movieVH.offerprice.setTypeface(fonts);
                movieVH.productname.setTypeface(bold);
                movieVH.add.setTypeface(bold);

                //String images=String.valueOf(drawables[position]);
                //loader.displayImage(images,movieVH.image,options);
                //movieVH.image.setImageResource(product.get(position).getProductimage());
                // movieVH.image.setImageResource(product.get(position).getProductimage());

                try
                {
                    loader.displayImage("http://www.boolfox.com/rest"+product.get(position).getProductimage(),movieVH.image,options);

                }catch (Exception e)
                {

                }

                movieVH.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(ctx,ProductDetailPage.class);
                       i.putExtra("product",product.get(position));
                        i.putExtra("position",position);
                        i.putExtra("productList", (Serializable) product);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        ctx.startActivity(i);

                    }
                });
                movieVH.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Toast.makeText(ctx,"DGSGDFSDFF",Toast.LENGTH_SHORT).show();
                        String seller = ctx.getResources().getString(R.string.Rupees);
                        //String offer = ctx.getResources().getString(R.string.seventyrupees);

                        if(global.productIDS.contains(product.get(position).getProduct_id()))
                        {

                            for(int i=0;i<global.cartValues.size();i++)
                            {
                                if(global.cartValues.get(i).getProduct_id().equalsIgnoreCase(product.get(position).getProduct_id()))
                                {
                                    Product p=global.cartValues.get(i);
                                    p.setQuantity(p.getQuantity()+1);
                                }


                            }
                            //   Toast.makeText(ctx,"Already added in the cart", Toast.LENGTH_SHORT).show();

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

                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return product == null ? 0 : product.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == product.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Product mc) {
        product.add(mc);
        notifyItemInserted(product.size() - 1);
    }

    public void addAll(List<Product> mcList) {
        for (Product mc : mcList) {
            add(mc);
        }
    }

    public void remove(Product city) {
        int position = product.indexOf(city);
        if (position > -1) {
            product.remove(position);
            notifyItemRemoved(position);
        }
    }



    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Product());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = product.size() - 1;
        Product item = getItem(position);

        if (item != null) {
            product.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Product getItem(int position) {
        return product.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class MovieVH extends RecyclerView.ViewHolder {
        public TextView offerprice,productname,sellerprice,outofstock;
        public ImageView image;
        public TextView add;

        public MovieVH(View view) {
            super(view);

            sellerprice = (TextView) view.findViewById(R.id.sellerprice);
            offerprice = (TextView) view.findViewById(R.id.offerprice);
            image = (ImageView) view.findViewById(R.id.image);
            add=(TextView)view.findViewById(R.id.addtocart);
            productname= (TextView) view.findViewById(R.id.product);
            outofstock= (TextView) view.findViewById(R.id.outofstock);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
