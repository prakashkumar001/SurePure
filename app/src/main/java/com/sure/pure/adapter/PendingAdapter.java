package com.sure.pure.adapter;

/**
 * Created by Creative IT Works on 19-Jun-17.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.sure.pure.R;
import com.sure.pure.model.Pendings;

import java.util.ArrayList;

/**
 * Created by Creative IT Works on 19-Jun-17.
 */

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.MyViewHolder> {


    ArrayList<Pendings> items;
    Context ctx;
    Typeface font;





    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderid,paymenttype,orderquantity,orderstatus,prd_name,prd_price,paymentdate;
        TextView order_text,type_text,quantity_text,status_text,product_text,price_text,date_text;

        public MyViewHolder(View view) {
            super(view);
            orderid = (TextView) view.findViewById(R.id.orderid);
            paymenttype = (TextView) view.findViewById(R.id.ordertype);
            orderquantity = (TextView) view.findViewById(R.id.orderqunatity);
            orderstatus = (TextView) view.findViewById(R.id.orderstatus);
            prd_name = (TextView) view.findViewById(R.id.productname);
            prd_price = (TextView) view.findViewById(R.id.price);
            paymentdate = (TextView) view.findViewById(R.id.date);

            order_text = (TextView) view.findViewById(R.id.order_text);
            type_text = (TextView) view.findViewById(R.id.type_text);
            quantity_text = (TextView) view.findViewById(R.id.quantity_text);
            status_text = (TextView) view.findViewById(R.id.status_text);
            product_text = (TextView) view.findViewById(R.id.product_text);
            price_text = (TextView) view.findViewById(R.id.price_text);
            date_text = (TextView) view.findViewById(R.id.date_text);


        }
    }


    public PendingAdapter(Context context, ArrayList<Pendings> items) {

        this.items=items;
        ctx=context;

        font = Typeface.createFromAsset(ctx.getAssets(), "fonts/Monitorica_Rg.ttf");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Pendings item=items.get(position);
        holder.orderid.setText(item.order_id);
        holder.orderquantity.setText(item.prd_quantity);
        holder.paymenttype.setText(item.payment_type);
        holder.prd_name.setText(item.prd_name);
        holder.prd_price.setText(item.prd_price);
        holder.paymentdate.setText(item.payment_date);
        holder.orderstatus.setText(item.status);

        holder.order_text.setTypeface(font);
        holder.quantity_text.setTypeface(font);
        holder.type_text.setTypeface(font);
        holder.product_text.setTypeface(font);
        holder.price_text.setTypeface(font);
        holder.date_text.setTypeface(font);
        holder.status_text.setTypeface(font);

        holder.orderid.setTypeface(font);
        holder.orderquantity.setTypeface(font);
        holder.paymenttype.setTypeface(font);
        holder.prd_name.setTypeface(font);
        holder.prd_price.setTypeface(font);
        holder.paymentdate.setTypeface(font);
        holder.orderstatus.setTypeface(font);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}