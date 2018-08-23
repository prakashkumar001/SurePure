package com.sure.pure.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sure.pure.MainActivity;
import com.sure.pure.R;
import com.sure.pure.common.GlobalClass;
import com.sure.pure.fragments.Home;
import com.sure.pure.utils.DrawerItem;

import java.util.ArrayList;
import java.util.List;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {
   private List<DrawerItem> drawerMenuList;
    Activity context;
    Home home;
    GlobalClass global;
    Typeface fonts,bold;
   public DrawerAdapter(Activity context,List<DrawerItem> drawerMenuList,Home home) {
     this.drawerMenuList = drawerMenuList;
       this.context=context;
       this.home=home;
       global=(GlobalClass)context.getApplicationContext();
   }  
   @Override  
   public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     View view;
     view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item, parent, false);
     return new DrawerViewHolder(view);  
   }  
   @Override  
   public void onBindViewHolder(DrawerViewHolder holder, final int position) {
        fonts = Typeface.createFromAsset(context.getAssets(), "fonts/futura.ttf");
        bold= Typeface.createFromAsset(context.getAssets(), "fonts/futura.ttf");

       holder.title.setTypeface(fonts);
     holder.title.setText(drawerMenuList.get(position).getCategory());
     //holder.icon.setImageResource(drawerMenuList.get(position).getId());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Bundle args = new Bundle();
               args.putString("category", drawerMenuList.get(position).getCategory());

               if(context instanceof MainActivity){
                   MainActivity.drawerlayout.closeDrawers();
                   //((MainActivity)context).getSelectCategory(drawerMenuList.get(position).getCategory());
                  // home.getSelectCategory(drawerMenuList.get(position).getCategory());
                   global.Category=drawerMenuList.get(position).getCategory();

                   home=new Home();
                  // home.queryData=null;
                   FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                   fragmentManager.beginTransaction()
                           .replace(R.id.container, home)
                           .commit();
               }

           }
       });
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