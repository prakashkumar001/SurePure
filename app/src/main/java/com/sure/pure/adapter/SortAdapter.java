package com.sure.pure.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.sure.pure.R;
import com.sure.pure.model.HolderClass;

import java.util.ArrayList;

/**
 * Created by v-62 on 11/19/2016.
 */

public class SortAdapter extends BaseAdapter {
   public static ArrayList<String> data;
    Context context;
    int selectedPosition;
    Typeface fonts,bold;
    public SortAdapter(Context context, ArrayList<String> data)
    {
        this.data=data;
        this.context=context;
        fonts = Typeface.createFromAsset(context.getAssets(), "fonts/ethnocentric_rg_it.ttf");
        bold= Typeface.createFromAsset(context.getAssets(), "fonts/ethnocentric_rg_it.ttf");

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView=inflater.inflate(R.layout.list_content,null);
        final ViewHolder holder=new ViewHolder();

        if (HolderClass.optionSelected == position) {

            convertView.setBackgroundColor(Color.GRAY);


        } else {

            convertView.setBackgroundColor(Color.WHITE);

        }


        holder.text=(TextView)convertView.findViewById(R.id.tv);
       // holder.radio=(RadioButton)convertView.findViewById(R.id.radio);


        holder.text.setText(data.get(position));
        holder.text.setTypeface(bold);


        convertView.setOnClickListener(new OnItemClickListener(position));


        return convertView;
    }

    static class ViewHolder
    {
        TextView text;
        //RadioButton radio;
    }
    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {

            HolderClass.optionSelected = mPosition;

            //Toast.makeText(context,"You selected"+data.get(HolderClass.optionSelected), Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();

        }
    }
}
