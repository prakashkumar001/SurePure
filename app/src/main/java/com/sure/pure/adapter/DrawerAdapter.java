package com.sure.pure.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sure.pure.R;
import com.sure.pure.utils.DrawerItem;

import java.util.ArrayList;
import java.util.List;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {
   private List<DrawerItem> drawerMenuList;
   public DrawerAdapter(List<DrawerItem> drawerMenuList) {
     this.drawerMenuList = drawerMenuList;  
   }  
   @Override  
   public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     View view;
     view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item, parent, false);
     return new DrawerViewHolder(view);  
   }  
   @Override  
   public void onBindViewHolder(DrawerViewHolder holder, int position) {  
     holder.title.setText(drawerMenuList.get(position).getCategory());
     //holder.icon.setImageResource(drawerMenuList.get(position).getId());
   }  
   @Override  
   public int getItemCount() {  
     return drawerMenuList.size();  
   }  
   class DrawerViewHolder extends RecyclerView.ViewHolder {  
     TextView title;
     ImageView icon;
     public DrawerViewHolder(View itemView) {  
       super(itemView);  
       title = (TextView) itemView.findViewById(R.id.title);  
       icon = (ImageView) itemView.findViewById(R.id.icon);  
     }  
   }  
 }  