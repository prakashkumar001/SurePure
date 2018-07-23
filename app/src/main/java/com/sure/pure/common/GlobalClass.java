package com.sure.pure.common;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sure.pure.model.*;

import org.json.JSONArray;

import java.util.ArrayList;

public class GlobalClass extends Application {


    public static ArrayList<Product> cartValues=new ArrayList<Product>();
    public static ArrayList<String> productIDS=new ArrayList<String>();
    public static String BadgeCount="0";
    public static String listmodel;
    public static ArrayList<Deliver> deliverdata=new ArrayList<>();
    public static ArrayList<Pendings> pendingdata=new ArrayList<>();
    public static User user;
    public static Bitmap profile;
    public static String sort="Price High-Low";
    public static String Category=null;

    public static JSONArray jsonArraydetails=new JSONArray();
    public static String Userid;


    public void onCreate() {



        super.onCreate();

        initImageLoader(getApplicationContext());

    }



    public static void initImageLoader(Context context) {



        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)

                .threadPriority(Thread.NORM_PRIORITY - 2)

                .denyCacheImageMultipleSizesInMemory()

                .discCacheFileNameGenerator(new Md5FileNameGenerator())

                .tasksProcessingOrder(QueueProcessingType.LIFO)

                .build();



        ImageLoader.getInstance().init(config);

    }


}
