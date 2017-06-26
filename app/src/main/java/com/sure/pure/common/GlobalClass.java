package com.sure.pure.common;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sure.pure.model.Deliver;
import com.sure.pure.model.Pendings;
import com.sure.pure.model.Product;

import java.util.ArrayList;

public class GlobalClass extends Application {


    public static ArrayList<Product> cartValues=new ArrayList<Product>();
    public static ArrayList<String> productIDS=new ArrayList<String>();
    public static String BadgeCount="0";
    public static String listmodel;
    public static ArrayList<Deliver> deliverdata=new ArrayList<>();
    public static ArrayList<Pendings> pendingdata=new ArrayList<>();


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
