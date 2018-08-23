package com.sure.pure.adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sure.pure.CartPage;
import com.sure.pure.MainActivity;
import com.sure.pure.ProductDetailPage;

import com.sure.pure.R;
import com.sure.pure.common.GlobalClass;
import com.sure.pure.model.Product;
import com.sure.pure.retrofit.APIInterface;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by v-62 on 10/21/2016.
 */

public class CartpageAdapter extends RecyclerView.Adapter<CartpageAdapter.MyViewHolder> {

    public int[] drawables;
    String title;
    ArrayList<Product> items;
    Activity ctx;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    GlobalClass global;
    private static final int TYPE_FOOTER = 2;


    ImageLoader loader;
    Typeface fonts, bold;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView productname, description;
        public TextView offerprice, quantity;
        public ImageView image, increase, decrease;
        public RelativeLayout remove;
        public TextView plus, minus, total;

        public TextView pricedummy, quantext, totaltext;


        public MyViewHolder(View view) {
            super(view);
            productname = (TextView) view.findViewById(R.id.product);
            description = (TextView) view.findViewById(R.id.description);
            offerprice = (TextView) view.findViewById(R.id.offerprice);
            decrease = (ImageView) view.findViewById(R.id.minus);
            quantity = (TextView) view.findViewById(R.id.quantity);
            total = (TextView) view.findViewById(R.id.total);
            //removetext = (TextView) view.findViewById(R.id.remove);

            increase = (ImageView) view.findViewById(R.id.add);
            image = (ImageView) view.findViewById(R.id.image);
            remove = (RelativeLayout) view.findViewById(R.id.removelay);

            pricedummy = (TextView) view.findViewById(R.id.pricedummy);
            quantext = (TextView) view.findViewById(R.id.quantext);
            totaltext = (TextView) view.findViewById(R.id.totaltext);


        }
    }


    public CartpageAdapter(Activity context, ArrayList<Product> items) {

        this.items = items;
        ctx = context;
        global = (GlobalClass) ctx.getApplicationContext();
        // drawables=new int[]{R.drawable.apple,R.drawable.banana,R.drawable.kiwi,R.drawable.grapes,R.drawable.starwbery,R.drawable.orange,R.drawable.watermelon};
        loader = ImageLoader.getInstance();
        fonts = Typeface.createFromAsset(ctx.getAssets(), "fonts/futura.ttf");
        bold = Typeface.createFromAsset(ctx.getAssets(), "fonts/futura.ttf");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cartpage_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.logo) // resource or drawable
                .showImageForEmptyUri(R.drawable.logo) // resource or drawable
                .showImageOnFail(R.drawable.logo) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(10)
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .build();

        //Product product = moviesList[position];
        // holder.price.setText(product.getProductprice());
        // holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        //String images=String.valueOf(drawables[position]);
        //loader.displayImage(images,holder.image,options);
        //holder.image.setImageResource(items.get(position).getProductimage());
        // holder.sellerprice.setText(items.get(position).getSellerprice());
        // holder.sellerprice.setPaintFlags(holder.sellerprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        String seller = ctx.getResources().getString(R.string.Rupees);
        String price = global.cartValues.get(position).getSellerprice();

        final double totals = global.cartValues.get(position).getQuantity() * Double.parseDouble(price);

        holder.total.setText(seller + String.format ("%.2f",totals));
        holder.productname.setText(global.cartValues.get(position).getProductname());
        holder.description.setText(global.cartValues.get(position).getProductdes());
        holder.offerprice.setText(global.cartValues.get(position).getSellerprice());


        holder.total.setTypeface(fonts);
        holder.productname.setTypeface(bold);
        holder.description.setTypeface(fonts);
        holder.offerprice.setTypeface(fonts);
        holder.quantext.setTypeface(bold);


        holder.totaltext.setTypeface(fonts);
        holder.pricedummy.setTypeface(fonts);
        holder.totaltext.setTypeface(fonts);


        //holder.image.setImageResource(global.cartValues.get(position).getProductimage());
        loader.displayImage(global.cartValues.get(position).getProductimage(), holder.image, options);
        holder.quantity.setText(String.valueOf(global.cartValues.get(position).getQuantity()));


       /* holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value=Integer.parseInt(holder.quantity.getText().toString());
                global.cartValues.get(position).setQuantity(value+1);
                holder.quantity.setText(String.valueOf(global.cartValues.get(position).getQuantity()));
                CartPage.total.setText(String.valueOf(totalvalue()));

            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value=Integer.parseInt(holder.quantity.getText().toString());
                global.cartValues.get(position).setQuantity(value-1);
                holder.quantity.setText(String.valueOf(global.cartValues.get(position).getQuantity()));
                CartPage.total.setText(String.valueOf(totalvalue()));

            }
        });*/

        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double b;
                int values = Integer.parseInt(holder.quantity.getText().toString());
                if(values<Integer.parseInt(global.cartValues.get(position).getStock()))

                {
                    values = values + 1;

                    global.cartValues.get(position).setQuantity(values);

                    holder.quantity.setText(String.valueOf(global.cartValues.get(position).getQuantity()));
                    String seller = ctx.getResources().getString(R.string.Rupees);
                    b = global.cartValues.get(position).getQuantity() * Double.parseDouble(global.cartValues.get(position).getSellerprice());
                    global.cartValues.get(position).setTotalprice(String.format ("%.2f",b));
                    holder.total.setText(seller + String.format ("%.2f",b));
                    double roundofftotal=Math.round(totalvalue());
                    double roundoffsubtotal=Math.round(subtotalvalue());
                    double roundoffgst=Math.round(getgst());
                    CartPage.total.setText(String.format ("%.2f",roundofftotal));
                    CartPage.sub.setText(String.format ("%.2f",roundoffsubtotal));
                    CartPage.gstamount.setText(String.format ("%.2f",roundoffgst));


                }else {

                }



            }
        });


        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double b;
                int values = Integer.parseInt(holder.quantity.getText().toString());
                if (values == 1) {

                } else {
                    values = values - 1;
                    global.cartValues.get(position).setQuantity(values);

                    holder.quantity.setText(String.valueOf(global.cartValues.get(position).getQuantity()));
                    String seller = ctx.getResources().getString(R.string.Rupees);
                    b = global.cartValues.get(position).getQuantity() * Double.parseDouble(global.cartValues.get(position).getSellerprice());
                    global.cartValues.get(position).setTotalprice(String.format ("%.2f",b));
                    holder.total.setText(seller + String.format ("%.2f",b));
                    double roundofftotal=Math.round(totalvalue());
                    double roundoffsubtotal=Math.round(subtotalvalue());
                    double roundoffgst=Math.round(getgst());
                    CartPage.total.setText(String.format ("%.2f",roundofftotal));
                    CartPage.sub.setText(String.format ("%.2f",roundoffsubtotal));
                    CartPage.gstamount.setText(String.format ("%.2f",roundoffgst));
                }


            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setCancelable(false)
                        .setMessage("Are you sure want to Remove ?")
                        //.setMessage("Payu's Data : " + data.getStringExtra("payu_response") + "\n\n\n Merchant's Data: " + data.getStringExtra("result"))
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();

                                global.productIDS.remove(position);
                                global.cartValues.remove(position);
                                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(ctx, R.animator.flip);
                                set.setTarget(CartPage.cartcount);
                                CartPage.cartcount.setText(String.valueOf(global.cartValues.size()));
                                set.start();
                                if (global.cartValues.size() > 0) {
                                    CartPage.cartcount.setVisibility(View.VISIBLE);
                                    double roundofftotal=Math.round(totalvalue());
                                    double roundoffsubtotal=Math.round(subtotalvalue());
                                    double roundoffgst=Math.round(getgst());
                                    CartPage.total.setText(String.format ("%.2f",roundofftotal));
                                    CartPage.sub.setText(String.format ("%.2f",roundoffsubtotal));
                                    CartPage.gstamount.setText(String.format ("%.2f",roundoffgst));
                                } else {
                                    CartPage.cartcount.setVisibility(View.GONE);
                                    CartPage.total.setText("0.0");
                                    CartPage.sub.setText("0.0");
                                    CartPage.gstamount.setText("0.0");

                                }

                                notifyDataSetChanged();
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                    }
                }).show();




              /*  MainActivity.setBadgeCount(ctx,MainActivity.icon, String.valueOf(global.cartValues.size()));
                CartPage.setBadgeCount(ctx,CartPage.icon, String.valueOf(global.cartValues.size()));
                CartPage.total.setText(String.valueOf(totalvalue()));
                global.BadgeCount= String.valueOf(global.cartValues.size());
              */ // ProductDetailPage.setBadgeCount(ctx,ProductDetailPage.icon, String.valueOf(global.cartValues.size()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public double subtotalvalue() {
        String seller = ctx.getResources().getString(R.string.Rupees);
        double totalValue = 0.0;
        for (int i = 0; i < global.cartValues.size(); i++) {
            String price = global.cartValues.get(i).getSellerprice();

            double value = Double.parseDouble(price) * global.cartValues.get(i).getQuantity();
            totalValue = totalValue + value;

        }

        return totalValue;
    }


    public double getgst()
    {

        String seller = ctx.getResources().getString(R.string.Rupees);
        double gst = 0.0;
        for (int i = 0; i < global.cartValues.size(); i++) {
            String price = global.cartValues.get(i).getSellerprice();

            double value = Double.parseDouble(price) * global.cartValues.get(i).getQuantity();
            double gstamount=value*18/100;
            gst = gst+gstamount ;

        }

        return gst;
    }

    public double totalvalue() {
        String seller = ctx.getResources().getString(R.string.Rupees);
        double totalValue = 0.0;
        for (int i = 0; i < global.cartValues.size(); i++) {
            String price = global.cartValues.get(i).getSellerprice();

            double value = Double.parseDouble(price) * global.cartValues.get(i).getQuantity();
            double gstamount=value*18/100;
            totalValue = totalValue + value+gstamount;


        }

        return totalValue;
    }

}