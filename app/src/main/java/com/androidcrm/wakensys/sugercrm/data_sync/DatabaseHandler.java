package com.androidcrm.wakensys.sugercrm.data_sync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidcrm.wakensys.sugercrm.fragment.Fragment_Entries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wakensys on 3/20/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static Variables
    private static final int DATABASE_VERSION = 12;                  // Database Version
    private static final String DATABASE_NAME = "crm";               // Database Name
    private static final String TABLE_LOGIN = "login";               // Table name login
    private static final String TABLE_TASKS= "tasks";                // All Record details table

    // module table Columns
    private static final String KEY_ID = "id";
    // login table Columns
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = " CREATE TABLE " + TABLE_LOGIN + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_USER_ID + " TEXT, " + KEY_USER_NAME + " TEXT " + " ) ";

        db.execSQL(CREATE_LOGIN_TABLE);
    }
    // Update Tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table if exists.
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_LOGIN);
        // Crete Tables Again
        onCreate(db);
    }

    //=============All CURD Operations=============


    //Adding new Login detail
    public void addLogin(Login login){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, login.get_user_id());          // user id
        values.put(KEY_USER_NAME, login.get_user_name());    // user name

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOGIN + " WHERE " +  KEY_USER_ID  + " = '" + login.get_user_id() + "' AND " + KEY_USER_NAME + " = '" + login.get_user_name() + "' ",null);
        int count = cursor.getCount();
        if(count <= 0) {
            // Inserting a Row
            db.insert(TABLE_LOGIN, null, values);
        }
        db.close();                                                 // closing the database connection
    }



    // Getting single Login
    Login getLogin(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_LOGIN, new String[]{KEY_ID, KEY_USER_ID, KEY_USER_NAME}, KEY_ID + "=?", new String[]{ String.valueOf(id) }, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Login login = new Login( cursor.getString(0), cursor.getString(1));
        return login;
    }



    // Getting All login
    public List<Login> getAllLogin(){
        List<Login> loginList = new ArrayList<Login>();
        // Select All Query
        String selectQuery = " SELECT * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through All rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Login login = new Login();
                login.set_id(Integer.parseInt(cursor.getString(0)));
                login.set_user_id(cursor.getString(1));
                login.set_user_name(cursor.getString(2));

                // adding modules to list
                loginList.add(login);
            }while (cursor.moveToNext());
        }
        // return module list
        return loginList;
    }



    // update single login
    public int updateLogin(Login login){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, login.get_user_id());
        values.put(KEY_USER_NAME, login.get_user_name());

        //updating row
        return db.update(TABLE_LOGIN, values, KEY_ID + "=?", new String[]{String.valueOf(login.get_id())});
    }


    // Getting login count
    public int getLoginCount(){
        String countQuery = "SELECT * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        //return count
        return cursor.getCount();
    }


}
