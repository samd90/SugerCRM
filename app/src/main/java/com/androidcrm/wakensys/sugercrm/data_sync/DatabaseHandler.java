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
    private static final int DATABASE_VERSION = 11;                  // Database Version
    private static final String DATABASE_NAME = "crm";              // Database Name
    private static final String TABLE_MODULE = "module";           // Table name module
    private static final String TABLE_LOGIN = "login";              // Table name login
    private static final String TABLE_ALL_RECORD = "all_record";    // All Record Table
    private static final String TABLE_ACCOUNT= "account";// All Record details table

    // module table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_MODULE_LABEL = "module_label";
    private static final String KEY_MODULE_KEY = "module_key";
    private static final String KEY_VIEW_ACCESS = "view_access";
    // login table Columns
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    // All Record columns
    private static final String KEY_NAME = "name";
    private static final String KEY_MODULE_NAME = "module_name";
    // Record details table
    private static final String KEY_ASSIGNED_USER_NAME = "assigned_user_name";
    private static final String KEY_CREATED_BY_NAME = "created_by_name";
    private static final String KEY_DATE_ENTERED = "date_entered";
    private static final String KEY_DATE_MODIFIED = "date_modified";
    private static final String KEY_DELETE = "deleted";
    private static final String KEY_ANNUAL_REVENUE = "annual_revenue";
    private static final String KEY_PHONE_FAX = "phone_fax";
    private static final String KEY_BILLING_ADDRESS_STREET = "billing_address_street";
    private static final String KEY_BILLING_ADDRESS_STREET_2 = "billing_address_street_2";
    private static final String KEY_BILLING_ADDRESS_STREET_3 = "billing_address_street_3";
    private static final String KEY_BILLING_ADDRESS_STREET_4 = "billing_address_street_4";
    private static final String KEY_BILLING_ADDRESS_CITY = "billing_address_city";
    private static final String KEY_BILLING_ADDRESS_STATE = "billing_address_state";
    private static final String KEY_BILLING_ADDRESS_POSTALCODE = "billing_address_postalcode";
    private static final String KEY_BILLING_ADDRESS_COUNTRY = "billing_address_country";
    private static final String KEY_PHONE_OFFICE = "phone_office";
    private static final String KEY_WEBSITE = "website";
    private static final String KEY_EMPLOYEES = "employees";
    private static final String KEY_TICKER_SYMBOL = "ticker_symbol";
    private static final String KEY_SHIPPING_ADDRESS_STREET = "shipping_address_street";
    private static final String KEY_SHIPPING_ADDRESS_STREET_2 = "shipping_address_street_2";
    private static final String KEY_SHIPPING_ADDRESS_STREET_3 = "shipping_address_street_3";
    private static final String KEY_SHIPPING_ADDRESS_STREET_4 = "shipping_address_street_4";
    private static final String KEY_SHIPPING_ADDRESS_CITY = "shipping_address_city";
    private static final String KEY_SHIPPING_ADDRESS_STATE = "shipping_address_state";
    private static final String KEY_SHIPPING_ADDRESS_POSTALCODE = "shipping_address_postalcode";
    private static final String KEY_SHIPPING_ADDRESS_COUNTRY = "shipping_address_country";
    private static final String KEY_PARENT_NAME = "parent_name";
    private static final String KEY_INDUSTRY = "industry";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MODULE_TABLE = " CREATE TABLE " + TABLE_MODULE + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_MODULE_KEY + " TEXT, " + KEY_MODULE_LABEL + " TEXT, " + KEY_VIEW_ACCESS + " INTEGER " + " ) ";
        String CREATE_LOGIN_TABLE = " CREATE TABLE " + TABLE_LOGIN + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_USER_ID + " TEXT, " + KEY_USER_NAME + " TEXT " + " ) ";
        String CREATE_ALL_RECORD_TABLE = " CREATE TABLE " + TABLE_ALL_RECORD + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " + KEY_MODULE_NAME + " TEXT " + " ) ";
        String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_ASSIGNED_USER_NAME + " TEXT, "  + KEY_USER_ID + " TEXT, " + KEY_NAME  + " TEXT, " + KEY_CREATED_BY_NAME + " TEXT, " + KEY_DATE_ENTERED + " TEXT, " + KEY_DATE_MODIFIED + " TEXT," +
                KEY_DELETE + " TEXT, " + KEY_ANNUAL_REVENUE + " TEXT, "+ KEY_PHONE_FAX + " TEXT, " + KEY_BILLING_ADDRESS_STREET + " TEXT, " + KEY_BILLING_ADDRESS_STREET_2 + " TEXT, "+ KEY_BILLING_ADDRESS_STREET_3 + " TEXT, "+ KEY_BILLING_ADDRESS_STREET_4 + " TEXT, " +
                KEY_BILLING_ADDRESS_CITY + " TEXT, "  + KEY_BILLING_ADDRESS_STATE + " TEXT, " + KEY_BILLING_ADDRESS_POSTALCODE + " TEXT, " + KEY_BILLING_ADDRESS_COUNTRY + " TEXT, " +
                KEY_PHONE_OFFICE + " TEXT, "+ KEY_WEBSITE + " TEXT, "+ KEY_EMPLOYEES + " TEXT, "+ KEY_TICKER_SYMBOL + " TEXT, "+ KEY_SHIPPING_ADDRESS_STREET + " TEXT, " +
                KEY_SHIPPING_ADDRESS_STREET_2 + " TEXT, " + KEY_SHIPPING_ADDRESS_STREET_3 + " TEXT, " + KEY_SHIPPING_ADDRESS_STREET_4 + " TEXT, " + KEY_SHIPPING_ADDRESS_CITY + " TEXT, " +
                KEY_SHIPPING_ADDRESS_STATE + " TEXT, " + KEY_SHIPPING_ADDRESS_POSTALCODE + " TEXT, " + KEY_SHIPPING_ADDRESS_COUNTRY + " TEXT, " + KEY_PARENT_NAME + " TEXT, " + KEY_INDUSTRY + " TEXT " +" )";

        db.execSQL(CREATE_MODULE_TABLE);
        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_ALL_RECORD_TABLE);
        db.execSQL(CREATE_ACCOUNT_TABLE);
    }
    // Update Tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table if exists.
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_MODULE);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_ALL_RECORD);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
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
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MODULE + " WHERE " +  KEY_MODULE_KEY  + " = '" + module.getModuleKey() + "' AND " + KEY_MODULE_LABEL + " = '" + module.get_module_label() + "' AND " + KEY_VIEW_ACCESS + " = '" + module.getViewAccess() + "' ",null);
        int count = cursor.getCount();
        if(count <= 0) {
            // Inserting a Row
            db.insert(TABLE_MODULE, null, values);
        }
        db.close();                                                 // closing the database connection
    }

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

    public void addAllRecord(AllRecords allRecords){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, allRecords.get_name());
        values.put(KEY_MODULE_NAME, allRecords.get_module_name());

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ALL_RECORD + " WHERE " + KEY_NAME + " = ' " + allRecords.get_name() + " ' AND " + KEY_MODULE_NAME + " = '" + allRecords.get_module_name() + "' ", null);
        int count = cursor.getCount();
        if(count <= 0){
            // Inserting row
            db.insert(TABLE_ALL_RECORD, null, values);
        }
        db.close();
    }

    // Add account to table
    public void addAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, account.get_name());
        values.put(KEY_USER_ID, account.get_user_id());
        values.put(KEY_ASSIGNED_USER_NAME, account.get_assigned_user_name());
        values.put(KEY_CREATED_BY_NAME, account.get_created_by_name());
        values.put(KEY_DATE_ENTERED, account.get_date_entered());
        values.put(KEY_DATE_MODIFIED, account.get_date_modified());
        values.put(KEY_DELETE, account.get_deleted());
        values.put(KEY_ANNUAL_REVENUE, account.get_annual_revenue());
        values.put(KEY_PHONE_FAX, account.get_phone_fax());
        values.put(KEY_BILLING_ADDRESS_STREET, account.get_billing_address_street());
        values.put(KEY_BILLING_ADDRESS_STREET_2, account.get_billing_address_street_2());
        values.put(KEY_BILLING_ADDRESS_STREET_3, account.get_billing_address_street_3());
        values.put(KEY_BILLING_ADDRESS_STREET_4, account.get_billing_address_street_4());
        values.put(KEY_BILLING_ADDRESS_CITY, account.get_billing_address_city());
        values.put(KEY_BILLING_ADDRESS_STATE, account.get_billing_address_state());
        values.put(KEY_BILLING_ADDRESS_POSTALCODE, account.get_billing_address_postalcode());
        values.put(KEY_BILLING_ADDRESS_COUNTRY, account.get_billing_address_country());
        values.put(KEY_PHONE_OFFICE, account.get_phone_office());
        values.put(KEY_WEBSITE, account.get_website());
        values.put(KEY_EMPLOYEES, account.get_employees());
        values.put(KEY_TICKER_SYMBOL, account.get_ticker_symbol());
        values.put(KEY_SHIPPING_ADDRESS_STREET, account.get_shipping_address_street());
        values.put(KEY_SHIPPING_ADDRESS_STREET_2, account.get_shipping_address_street_2());
        values.put(KEY_SHIPPING_ADDRESS_STREET_3, account.get_shipping_address_street_3());
        values.put(KEY_SHIPPING_ADDRESS_STREET_4, account.get_shipping_address_street_4());
        values.put(KEY_SHIPPING_ADDRESS_CITY, account.get_shipping_address_city());
        values.put(KEY_SHIPPING_ADDRESS_STATE, account.get_shipping_address_state());
        values.put(KEY_SHIPPING_ADDRESS_POSTALCODE, account.get_shipping_address_postalcode());
        values.put(KEY_SHIPPING_ADDRESS_COUNTRY, account.get_shipping_address_country());
        values.put(KEY_PARENT_NAME, account.get_parent_name());
        values.put(KEY_INDUSTRY, account.get_industry());

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_ACCOUNT + " WHERE " + KEY_USER_ID + " ='" + account.get_user_id() +"' ", null);
        int count = cursor.getCount();
        if(count <= 0)
            db.insert(TABLE_ACCOUNT, null, values);

        db.close();
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

    // Getting single Record
    AllRecords getRecord(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_LOGIN, new String[]{KEY_ID, KEY_NAME, KEY_MODULE_NAME}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        AllRecords allRecords = new AllRecords(cursor.getString(0), cursor.getString(1));
        return allRecords;
    }

    // Get Account
    Account getAccount(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNT, new String[]{KEY_ID, KEY_NAME, KEY_ASSIGNED_USER_NAME, KEY_CREATED_BY_NAME, KEY_DATE_ENTERED, KEY_DATE_MODIFIED, KEY_DELETE, KEY_ANNUAL_REVENUE,
                KEY_PHONE_FAX, KEY_BILLING_ADDRESS_STREET, KEY_BILLING_ADDRESS_STREET_2, KEY_BILLING_ADDRESS_STREET_3, KEY_BILLING_ADDRESS_STREET_4, KEY_BILLING_ADDRESS_CITY, KEY_BILLING_ADDRESS_STATE,
                KEY_BILLING_ADDRESS_POSTALCODE, KEY_BILLING_ADDRESS_COUNTRY, KEY_PHONE_OFFICE, KEY_WEBSITE, KEY_EMPLOYEES, KEY_TICKER_SYMBOL, KEY_SHIPPING_ADDRESS_STREET, KEY_SHIPPING_ADDRESS_STREET_2,
                KEY_SHIPPING_ADDRESS_STREET_3, KEY_SHIPPING_ADDRESS_STREET_4, KEY_SHIPPING_ADDRESS_CITY, KEY_SHIPPING_ADDRESS_STATE, KEY_SHIPPING_ADDRESS_COUNTRY, KEY_PARENT_NAME, KEY_INDUSTRY}, KEY_ID +
                "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();
        Account account = new Account(cursor.getString(0), cursor.getString(1),cursor.getString(2), cursor.getString(3),cursor.getString(4), cursor.getString(5),cursor.getString(6), cursor.getString(7),cursor.getString(8), cursor.getString(9),cursor.getString(10), cursor.getString(11),
                cursor.getString(12), cursor.getString(12),cursor.getString(14), cursor.getString(15),cursor.getString(16), cursor.getString(17),cursor.getString(18), cursor.getString(19),cursor.getString(20), cursor.getString(21),cursor.getString(22), cursor.getString(23),cursor.getString(24),
                cursor.getString(25),cursor.getString(26), cursor.getString(27),cursor.getString(28), cursor.getString(29),cursor.getString(30));
        return account;
    }

    // Getting All modules
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

    // Getting all Records
    public List<AllRecords> getAllRecords(){
        List<AllRecords> recordList = new ArrayList<AllRecords>();
        String selectQuery = " SELECT * FROM " + TABLE_ALL_RECORD + " WHERE " + KEY_MODULE_NAME + " = '" + Fragment_Entries.module_name + "' " ;
        //String selectQuery = " SELECT * FROM " + TABLE_ALL_RECORD + " WHERE " + "module_name" + " = '" + "Leads" + "' " ;

        //String selectQuery = " SELECT * FROM " + TABLE_ALL_RECORD ;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        //looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                AllRecords allRecords = new AllRecords();
                allRecords.set_id(Integer.parseInt(cursor.getString(0)));
                allRecords.set_name(cursor.getString(1));
                allRecords.set_module_name(cursor.getString(2));

                //adding records to list
                recordList.add(allRecords);
            }while (cursor.moveToNext());
        }
        return  recordList;
    }

    // get All account
    public List<Account> getAllAccount(){
        List<Account> accountList = new ArrayList<Account>();
        String selectQuery = " SELECT * FROM " + TABLE_ACCOUNT ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Account account = new Account();
                account.set_id(Integer.parseInt(cursor.getString(0)));
                account.set_user_id(cursor.getString(1));
                account.set_name(cursor.getString(2));
                account.set_assigned_user_name(cursor.getString(3));
                account.set_created_by_name(cursor.getString(4));
                account.set_date_entered(cursor.getString(5));
                account.set_date_modified(cursor.getString(6));
                account.set_deleted(cursor.getString(7));
                account.set_annual_revenue(cursor.getString(8));
                account.set_phone_fax(cursor.getString(9));
                account.set_billing_address_street(cursor.getString(10));
                account.set_billing_address_street_2(cursor.getString(11));
                account.set_billing_address_street_3(cursor.getString(12));
                account.set_billing_address_street_4(cursor.getString(13));
                account.set_billing_address_city(cursor.getString(14));
                account.set_billing_address_state(cursor.getString(15));
                account.set_billing_address_postalcode(cursor.getString(16));
                account.set_billing_address_country(cursor.getString(17));
                account.set_phone_office(cursor.getString(18));
                account.set_website(cursor.getString(19));
                account.set_employees(cursor.getString(20));
                account.set_ticker_symbol(cursor.getString(21));
                account.set_shipping_address_street(cursor.getString(22));
                account.set_shipping_address_street_2(cursor.getString(23));
                account.set_shipping_address_street_3(cursor.getString(24));
                account.set_shipping_address_street_4(cursor.getString(25));
                account.set_shipping_address_city(cursor.getString(26));
                account.set_shipping_address_state(cursor.getString(27));
                account.set_shipping_address_postalcode(cursor.getString(28));
                account.set_shipping_address_country(cursor.getString(29));
                account.set_parent_name(cursor.getString(30));
                account.set_industry(cursor.getString(31));

            }while(cursor.moveToNext());
        }
        return accountList;
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

    // update single login
    public int updateLogin(Login login){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, login.get_user_id());
        values.put(KEY_USER_NAME, login.get_user_name());

        //updating row
        return db.update(TABLE_LOGIN, values, KEY_ID + "=?", new String[]{String.valueOf(login.get_id())});
    }

    // Update single record
    public int updateRecord(AllRecords allRecords){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, allRecords.get_name());
        values.put(KEY_MODULE_NAME, allRecords.get_module_name());

        //Updating row
        return db.update(TABLE_ALL_RECORD, values, KEY_ID + "=?", new String[]{String.valueOf(allRecords.get_id())});
    }

    //update single Account
    public int updateAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, account.get_name());
        values.put(KEY_USER_ID, account.get_user_id());
        values.put(KEY_ASSIGNED_USER_NAME, account.get_assigned_user_name());
        values.put(KEY_CREATED_BY_NAME, account.get_created_by_name());
        values.put(KEY_DATE_ENTERED, account.get_date_entered());
        values.put(KEY_DATE_MODIFIED, account.get_date_modified());
        values.put(KEY_DELETE, account.get_deleted());
        values.put(KEY_ANNUAL_REVENUE, account.get_annual_revenue());
        values.put(KEY_PHONE_FAX, account.get_phone_fax());
        values.put(KEY_BILLING_ADDRESS_STREET, account.get_billing_address_street());
        values.put(KEY_BILLING_ADDRESS_STREET_2, account.get_billing_address_street_2());
        values.put(KEY_BILLING_ADDRESS_STREET_3, account.get_billing_address_street_3());
        values.put(KEY_BILLING_ADDRESS_STREET_4, account.get_billing_address_street_4());
        values.put(KEY_BILLING_ADDRESS_CITY, account.get_billing_address_city());
        values.put(KEY_BILLING_ADDRESS_STATE, account.get_billing_address_state());
        values.put(KEY_BILLING_ADDRESS_POSTALCODE, account.get_billing_address_postalcode());
        values.put(KEY_BILLING_ADDRESS_COUNTRY, account.get_billing_address_country());
        values.put(KEY_PHONE_OFFICE, account.get_phone_office());
        values.put(KEY_WEBSITE, account.get_website());
        values.put(KEY_EMPLOYEES, account.get_employees());
        values.put(KEY_TICKER_SYMBOL, account.get_ticker_symbol());
        values.put(KEY_SHIPPING_ADDRESS_STREET, account.get_shipping_address_street());
        values.put(KEY_SHIPPING_ADDRESS_STREET_2, account.get_shipping_address_street_2());
        values.put(KEY_SHIPPING_ADDRESS_STREET_3, account.get_shipping_address_street_3());
        values.put(KEY_SHIPPING_ADDRESS_STREET_4, account.get_shipping_address_street_4());
        values.put(KEY_SHIPPING_ADDRESS_CITY, account.get_shipping_address_city());
        values.put(KEY_SHIPPING_ADDRESS_STATE, account.get_shipping_address_state());
        values.put(KEY_SHIPPING_ADDRESS_POSTALCODE, account.get_shipping_address_postalcode());
        values.put(KEY_SHIPPING_ADDRESS_COUNTRY, account.get_shipping_address_country());
        values.put(KEY_PARENT_NAME, account.get_parent_name());
        values.put(KEY_INDUSTRY, account.get_industry());

        return db.update(TABLE_ACCOUNT, values, KEY_ID + "=?", new String[]{String.valueOf(account.get_id())});
    }

    // Getting contact count
    public int getModulesCount(){
        String countQuery = "SELECT * FROM " + TABLE_MODULE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        //return count
        return cursor.getCount();
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

    //getting record count
    public int getAllRecordCount(){
        String countQuery = " SELECT * FROM " + TABLE_ALL_RECORD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    // Account count
    public int getAccountCount(){
        String countQuery= " SELECT * FROM " + TABLE_ACCOUNT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
}
