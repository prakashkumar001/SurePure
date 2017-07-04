package com.sure.pure.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sure.pure.common.User;
import com.sure.pure.db.DatabaseHelper;

/**
 * Created by Creative IT Works on 04-Jul-17.
 */

public class ProfilePicture {
    public static Bitmap bitmap;
    public static byte[] data;
    public  Context context;
    public DatabaseHelper databaseHelper;

    public ProfilePicture(Context context,byte[] data)
    {
        databaseHelper=new DatabaseHelper(context);
       this.data=data;
        bitmap= BitmapFactory.decodeByteArray(data, 0, data.length);
    }


}
