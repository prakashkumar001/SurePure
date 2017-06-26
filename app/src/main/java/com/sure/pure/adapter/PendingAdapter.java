package com.sure.pure.adapter;

/**
 * Created by Creative IT Works on 19-Jun-17.
 */

import android.content.Context;
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






    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderid,ordertype,orderquantity,orderstatus;

        public MyViewHolder(View view) {
            super(view);
            orderid = (TextView) view.findViewById(R.id.orderid);
            ordertype = (TextView) view.findViewById(R.id.ordertype);
            orderquantity = (TextView) view.findViewById(R.id.orderqunatity);
            orderstatus = (TextView) view.findViewById(R.id.orderstatus);



        }
    }


    public PendingAdapter(Context context, ArrayList<Pendings> items) {

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

        final Pendings item=items.get(position);
        holder.orderid.setText(item.id);
        holder.orderquantity.setText("1");
        holder.ordertype.setText(item.message);
        holder.orderstatus.setText(item.Status);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}