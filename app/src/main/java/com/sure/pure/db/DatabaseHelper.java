package com.sure.pure.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sure.pure.model.User;

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

    // Contacts Table Columns names
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PIN = "pin";
    private static final String ADDRESS = "address";
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
                + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT," + EMAIL + " TEXT," + PIN + " TEXT,"+ IMAGE + " BLOB,"+ PASSWORD + " TEXT,"+ CITY + " TEXT," +PHONE + " TEXT,"+ ADDRESS + " TEXT" + ")";

        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + ID + " INTEGER PRIMARY KEY," + EMAIL + " TEXT," + PASSWORD + " TEXT" +  ")";

        db.execSQL(CREATE_SIGNUP_TABLE);
        db.execSQL(CREATE_LOGIN_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGNUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);


        // Create tables again
        onCreate(db);

    }
    // Adding new contact
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PIN, user.pin); // Contact Name
        values.put(NAME, user.name); // Contact Phone Number
        values.put(ADDRESS, user.address);
        values.put(IMAGE, user.image);
        values.put(EMAIL, user.email);
        values.put(PASSWORD, user.password);
        values.put(PHONE, user.phone);
        // Inserting Row
        db.insert(TABLE_SIGNUP, null, values);
        db.close(); // Closing database connection
    }

    public int updateUser(User staff) {

        SQLiteDatabase db = this.getWritableDatabase();



        ContentValues values = new ContentValues();
        values.put(PIN, staff.pin); // Contact Name
        values.put(NAME, staff.name); // Contact Phone Number
        values.put(ADDRESS, staff.address);
        values.put(IMAGE, staff.image);
        values.put(EMAIL, staff.email);
        values.put(PASSWORD, staff.password);
        values.put(PHONE, staff.phone);


        // updating row

        return db.update(TABLE_SIGNUP, values, ID + " = ?",

                new String[] { String.valueOf(1) });

    }

    public User getUser() {
        User user=null;
        String resume="";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  *  FROM " + TABLE_SIGNUP  ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                user=new User(cursor.getString(1),cursor.getString(6),cursor.getString(3),cursor.getString(2),cursor.getString(5),cursor.getBlob(4),cursor.getString(8),cursor.getString(7));


            } while (cursor.moveToNext());
        }
        cursor.close();

        return user;

    }

    // Adding new contact
    public void addLogin(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();



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

    public int updateLogin(String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();



        ContentValues values = new ContentValues();

        values.put(EMAIL, email);
        values.put(PASSWORD, password);



        // updating row

        return db.update(TABLE_SIGNUP, values, ID + " = ?",

                new String[] { String.valueOf(1) });

    }
    public int removeLogin()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_LOGIN, ID + " = ?",

                new String[] { String.valueOf(1) });

    }

    public String getSignup() {
        String user="false";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  *  FROM " + TABLE_SIGNUP  ;
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
