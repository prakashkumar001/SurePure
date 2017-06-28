package com.sure.pure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.sure.pure.R;
import com.sure.pure.model.Deliver;

import java.util.ArrayList;

/**
 * Created by Creative IT Works on 19-Jun-17.
 */

public class DeliverAdapter extends RecyclerView.Adapter<DeliverAdapter.MyViewHolder> {


    ArrayList<Deliver> items;
    Context ctx;






    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderid,paymenttype,orderquantity,orderstatus,prd_name,prd_price,paymentdate;

        public MyViewHolder(View view) {
            super(view);
            orderid = (TextView) view.findViewById(R.id.orderid);
            paymenttype = (TextView) view.findViewById(R.id.ordertype);
            orderquantity = (TextView) view.findViewById(R.id.orderqunatity);
            orderstatus = (TextView) view.findViewById(R.id.orderstatus);
            prd_name = (TextView) view.findViewById(R.id.productname);
            prd_price = (TextView) view.findViewById(R.id.price);
            paymentdate = (TextView) view.findViewById(R.id.date);



        }
    }


    public DeliverAdapter(Context context, ArrayList<Deliver> items) {

        this.items=items;
        ctx=context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Deliver item=items.get(position);
        holder.orderid.setText(item.order_id);
        holder.orderquantity.setText(item.prd_quantity);
        holder.paymenttype.setText(item.payment_type);
        holder.prd_name.setText(item.prd_name);
        holder.prd_price.setText(item.prd_price);
        holder.paymentdate.setText(item.payment_date);
        holder.orderstatus.setText(item.status);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}