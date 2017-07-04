package com.sure.pure.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.sure.pure.common.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creative IT Works on 02-May-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "surepure";

    // Contacts table name
    private static final String TABLE_LOGIN = "login";

    private static final String TABLE_SIGNUP = "signup";

    private static final String TABLE_USER_DETAIL = "user_detail";

    // Contacts Table Columns names
    private static final String ID = "id";
    private static final String USERID = "user_id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PIN = "pin";
    private static final String ADDRESS = "address";
    private static final String COUNTRY = "country";
    private static final String CITY = "city";
    private static final String IMAGE ="image";
    private static final String PASSWORD = "password";
    private static final String PHONE = "phone";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SIGNUP_TABLE = "CREATE TABLE " + TABLE_SIGNUP + "("
                + ID + " INTEGER PRIMARY KEY," + USERID + " TEXT,"+ NAME + " TEXT," + EMAIL + " TEXT," + PIN + " TEXT,"+ IMAGE + " BLOB,"+ PASSWORD + " TEXT,"+ CITY + " TEXT," +PHONE + " TEXT,"+ ADDRESS + " TEXT" + ")";

        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + ID + " INTEGER PRIMARY KEY," + USERID + " TEXT," + EMAIL + " TEXT," + PASSWORD + " TEXT" +  ")";

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER_DETAIL + "("
                + ID + " INTEGER PRIMARY KEY," + USERID + " TEXT,"+ NAME + " TEXT," + EMAIL + " TEXT," + PHONE + " TEXT,"+ COUNTRY + " TEXT,"+ IMAGE + " BLOB,"+ CITY + " TEXT," +ADDRESS + " TEXT,"+ PIN + " TEXT" + ")";

        db.execSQL(CREATE_SIGNUP_TABLE);
        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGNUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DETAIL);


        // Create tables again
        onCreate(db);

    }
    // Adding new contact
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERID, user.id); // Contact Name
        values.put(NAME, user.name); // Contact Phone Number
        values.put(ADDRESS, user.address);
        values.put(IMAGE, user.image);
        values.put(EMAIL, user.email);
        values.put(PHONE, user.mobile);
        values.put(CITY, user.city);
        values.put(COUNTRY, user.country);
        values.put(PIN, user.pincode);
        // Inserting Row
        db.insert(TABLE_USER_DETAIL, null, values);
        db.close(); // Closing database connection
    }

    public int updateUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();



        ContentValues values = new ContentValues();
        values.put(USERID, user.id); // Contact Name
        values.put(NAME, user.name); // Contact Phone Number
        values.put(ADDRESS, user.address);
        values.put(IMAGE, user.image);
        values.put(EMAIL, user.email);
        values.put(PHONE, user.mobile);
        values.put(CITY, user.city);
        values.put(COUNTRY, user.country);
        values.put(PIN, user.pincode);


        // updating row

        return db.update(TABLE_USER_DETAIL, values, ID + " = ?",

                new String[] { String.valueOf(1) });

    }

    public User getUser() {
        User user=null;
        String resume="";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  *  FROM " + TABLE_USER_DETAIL  ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                Log.i("id","id"+cursor.getString(cursor.getColumnIndex(ID)));
                Log.i("name","name"+cursor.getString(2));
                Log.i("email","email"+cursor.getString(3));
                Log.i("mobile","mobile"+cursor.getString(4));
                Log.i("country","country"+cursor.getString(5));
                Log.i("inage","inage"+cursor.getBlob(6));
                Log.i("city","city"+cursor.getString(7));
                Log.i("address","address"+cursor.getString(8));
                Log.i("pincode","pincode"+cursor.getString(9));


                user=new User(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getBlob(6),cursor.getString(7),cursor.getString(8),cursor.getString(9));


            } while (cursor.moveToNext());
        }
        cursor.close();

        return user;

    }

    // Adding new contact
    public void addLogin(String id,String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(USERID, id);
        values.put(EMAIL, email);
        values.put(PASSWORD, password);


        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }

    public String getLogin() {
        String user="false";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  *  FROM " + TABLE_LOGIN  ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                user="true";

            } while (cursor.moveToNext());
        }
        cursor.close();

        return user;

    }

    public String getLoginid() {
        String userid="false";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  *  FROM " + TABLE_LOGIN  ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                userid=cursor.getString(1);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return userid;

    }

    public int updateLogin(String id,String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();



        ContentValues values = new ContentValues();
        values.put(USERID, id);
        values.put(EMAIL, email);
        values.put(PASSWORD, password);



        // updating row

        return db.update(TABLE_SIGNUP, values, ID + " = ?",

                new String[] { String.valueOf(1) });

    }
    public int removeLogin()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_USER_DETAIL, ID + " = ?",

                new String[] { String.valueOf(1) });

    }

    public String getSignup() {
        String user="false";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  *  FROM " + TABLE_USER_DETAIL  ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                user="true";

            } while (cursor.moveToNext());
        }
        cursor.close();

        return user;

    }
}
