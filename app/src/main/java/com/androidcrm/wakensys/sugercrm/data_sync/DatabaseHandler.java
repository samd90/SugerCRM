package com.androidcrm.wakensys.sugercrm.data_sync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wakensys on 3/20/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static Variables
    private static final int DATABASE_VERSION = 1;                  // Database Version
    private static final String DATABASE_NAME = "crm";              // Database Name
    private static final String TABLE_MODULE = "module";           // Table name

    // module table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_MODULE_LABEL = "module_label";
    private static final String KEY_MODULE_KEY = "module_key";
    private static final String KEY_VIEW_ACCESS = "view_access";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MODULE_TABLE = " CREATE TABLE " + TABLE_MODULE + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_MODULE_KEY + " TEXT, " + KEY_MODULE_LABEL + " TEXT, " + KEY_VIEW_ACCESS + " INTEGER " + " ) ";
        db.execSQL(CREATE_MODULE_TABLE);
    }
    // Update Tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table if exists.
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_MODULE);
        // Crete Tables Again
        onCreate(db);
    }

    //=============All CURD Operations=============

    //Adding new Module
    public void addModule(Module module){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MODULE_KEY, module.getModuleKey());          // Module Kye
        values.put(KEY_MODULE_LABEL, module.get_module_label());    // Module label
        values.put(KEY_VIEW_ACCESS, module.getViewAccess());        // View Access

        // Inserting a Row
        db.insert(TABLE_MODULE,null,values);
        db.close();                                                 // closing the database connection
    }

    // Getting single Module
    Module getModule(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_MODULE, new String[]{KEY_ID, KEY_MODULE_KEY, KEY_MODULE_LABEL, KEY_VIEW_ACCESS}, KEY_ID + "=?", new String[]{ String.valueOf(id) }, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Module module = new Module(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));
        return module;
    }

    // Getting All contacts
    public List<Module> getAllModules(){
        List<Module> moduleList = new ArrayList<Module>();
        // Select All Query
        String selectQuery = " SELECT * FROM " + TABLE_MODULE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through All rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Module module = new Module();
                module.setID(Integer.parseInt(cursor.getString(0)));
                module.setModuleKey(cursor.getString(1));
                module.set_module_label(cursor.getString(2));
                module.setViewAccess(Integer.parseInt(cursor.getString(3)));
                // adding modules to list
                moduleList.add(module);
            }while (cursor.moveToNext());
        }
        // return module list
        return moduleList;
    }

    // update single module
    public int updateModule(Module module){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MODULE_KEY, module.getModuleKey());
        values.put(KEY_MODULE_LABEL, module.get_module_label());
        values.put(KEY_VIEW_ACCESS, module.getViewAccess());

        //updating row
        return db.update(TABLE_MODULE, values, KEY_ID + "=?", new String[]{String.valueOf(module.getID())});
    }

    // Getting contact count
    public int getContactsCount(){
        String countQuery = "SELECT * FROM " + TABLE_MODULE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        //return count
        return cursor.getCount();
    }
}
