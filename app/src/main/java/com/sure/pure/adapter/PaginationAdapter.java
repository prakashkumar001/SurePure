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
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    List<Product> product;
    Context ctx;
    String sort = "";
    Typeface fonts, bold;
    int count = 0;
    ImageLoader loader;
    GlobalClass global;


    /*
     * isLoading - to set the remote loading and complete status to fix back to back load more call
     * isMoreDataAvailable - to set whether more data from server available or not.
     * It will prevent useless load more request even after all the server data loaded
     * */


    public PaginationAdapter(Context context, List<Product> movies,String sort) {
        this.ctx = context;
        this.product = movies;
        loader = ImageLoader.getInstance();
        global = (GlobalClass) ctx.getApplicationContext();
        this.sort=sort;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        viewHolder = getViewHolder(parent, inflater);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder recyclerHolder, final int position) {
        MovieVH holder = (MovieVH) recyclerHolder;
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
        if (sort.equalsIgnoreCase("Quantity Low-High")) {
            Collections.sort(product, new Product.lowToHighQuantity());
        } else if (sort.equalsIgnoreCase("Quantity High-Low")) {

            Collections.sort(product, Collections.reverseOrder(new Product.lowToHighQuantity()));

        } else if (sort.equalsIgnoreCase("Price Low-High")) {
            Collections.sort(product, new Product.lowToHighPrice());
        } else if (sort.equalsIgnoreCase("Price High-Low")) {
            Collections.sort(product, Collections.reverseOrder(new Product.lowToHighPrice()));
        }
        String seller = ctx.getResources().getString(R.string.Rupees);
        //String offer = ctx.getResources().getString(R.string.seventyrupees);


        holder.sellerprice.setText(seller + product.get(position).getSellerprice());
        //holder.offerprice.setText(seller + product.get(position).getOfferprice());
        if (product.get(position).getSellerprice().equals("0")) {
            holder.outofstock.setText("Out of Stock");
            holder.sellerprice.setVisibility(View.GONE);
            holder.add.setVisibility(View.GONE);

        } else {
            holder.outofstock.setVisibility(View.GONE);

        }
        // holder.sellerprice.setPaintFlags(holder.sellerprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productname.setText(product.get(position).getProductname());

        holder.sellerprice.setTypeface(fonts);
        //holder.offerprice.setTypeface(fonts);
        holder.productname.setTypeface(bold);
        holder.add.setTypeface(bold);

        //String images=String.valueOf(drawables[position]);
        //loader.displayImage(images,holder.image,options);
        //holder.image.setImageResource(product.get(position).getProductimage());
        // holder.image.setImageResource(product.get(position).getProductimage());

        try {
            loader.displayImage("http://www.boolfox.com/rest" + product.get(position).getProductimage(), holder.image, options);

        } catch (Exception e) {

        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, ProductDetailPage.class);
              /*  i.putExtra("name",product.get(position).getProductname());
                i.putExtra("id",product.get(position).getProduct_id());
                i.putExtra("sellerprice",product.get(position).getSellerprice());
                i.putExtra("offerprice",product.get(position).getOfferprice());
                i.putExtra("description",product.get(position).getProductdes());
                i.putExtra("image",product.get(position).getProductimage());*/
                i.putExtra("product", product.get(position));
                i.putExtra("position", position);
                i.putExtra("productList", (Serializable) product);
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

                if (global.productIDS.contains(product.get(position).getProduct_id())) {

                    for (int i = 0; i < global.cartValues.size(); i++) {
                        if (global.cartValues.get(i).getProduct_id().equalsIgnoreCase(product.get(position).getProduct_id())) {
                            Product p = global.cartValues.get(i);
                            p.setQuantity(p.getQuantity() + 1);
                        }


                    }
                    //   Toast.makeText(ctx,"Already added in the cart", Toast.LENGTH_SHORT).show();

                } else {
                    Product products = new Product();
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
                    count = global.cartValues.size();
                    String value = String.valueOf(count);
                    Log.i("Count", "Count" + product.get(position).getProductname());
                    global.BadgeCount = value;

                   /* MainActivity.setBadgeCount(ctx, MainActivity.icon,value);
                    global.BadgeCount=value;
                   */
                    Toast.makeText(ctx, product.get(position).getProductname() + " " + "Added in the cart", Toast.LENGTH_SHORT).show();
                    // notifyDataSetChanged();

                    MainActivity.cartcount.setVisibility(View.VISIBLE);
                    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(ctx, R.animator.flip);
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


    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder = null;
        ;


        View itemView = null;
        if (global.listmodel.equalsIgnoreCase("list")) {
            itemView = inflater.inflate(R.layout.product_list_item, parent, false);
            viewHolder = new MovieVH(itemView);

        } else if (global.listmodel.equalsIgnoreCase("grid")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid, parent, false);
            viewHolder = new MovieVH(itemView);

        }
        return viewHolder;
    }

    public void setFilter(List<Product> countryModels) {
        product = new ArrayList<>();
        product.addAll(countryModels);
        notifyDataSetChanged();

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    protected class MovieVH extends RecyclerView.ViewHolder {
        public TextView offerprice, productname, sellerprice, outofstock;
        public ImageView image;
        public TextView add;

        public MovieVH(View view) {
            super(view);

            sellerprice = (TextView) view.findViewById(R.id.sellerprice);
            offerprice = (TextView) view.findViewById(R.id.offerprice);
            image = (ImageView) view.findViewById(R.id.image);
            add = (TextView) view.findViewById(R.id.addtocart);
            productname = (TextView) view.findViewById(R.id.product);
            outofstock = (TextView) view.findViewById(R.id.outofstock);
        }


    }
}







