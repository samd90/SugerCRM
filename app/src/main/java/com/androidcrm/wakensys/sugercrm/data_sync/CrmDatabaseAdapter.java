package com.androidcrm.wakensys.sugercrm.data_sync;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CrmDatabaseAdapter {

    CrmDatabaseHelper helper;

    public CrmDatabaseAdapter(Context context) {
        helper = new CrmDatabaseHelper(context);
    }

    public long InsertAvailableModules(String module_key, String module_label, Boolean favorite_enabled, int action_edit, int action_delete, int action_list, int action_view, int action_import, int action_export) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrmDatabaseHelper.MODULE_KEY, module_key);
        contentValues.put(CrmDatabaseHelper.MODULE_LABEL, module_label);
        contentValues.put(CrmDatabaseHelper.FAVORITE_ENABLED, favorite_enabled);
        contentValues.put(CrmDatabaseHelper.ACTION_EDIT, action_edit);
        contentValues.put(CrmDatabaseHelper.ACTION_DELETE, action_delete);
        contentValues.put(CrmDatabaseHelper.ACTION_LIST, action_list);
        contentValues.put(CrmDatabaseHelper.ACTION_VIEW, action_view);
        contentValues.put(CrmDatabaseHelper.ACTION_IMPORT, action_import);
        contentValues.put(CrmDatabaseHelper.ACTION_EXPORT, action_export);
        String Table_name = CrmDatabaseHelper.TABLE_NAME_GET_AVAILABLE_MODULES;
        //Check getAvilable module table exists
        String queryAck = "SELECT * FROM "+ CrmDatabaseHelper.TABLE_NAME_GET_AVAILABLE_MODULES +" Where "+ CrmDatabaseHelper.MODULE_LABEL +" = '"
                + module_label + "'";
        Cursor cursor  = db.rawQuery(queryAck, null);
        long id = 0;
        if(cursor.getCount() == 0) {
            id = db.insert(CrmDatabaseHelper.TABLE_NAME_GET_AVAILABLE_MODULES, CrmDatabaseHelper.MODULE_KEY, contentValues);

        }
        return id;
    }

    public long InsertLastViwed(String item_id, String id, String item_summary, String module_name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrmDatabaseHelper.ITEM_ID, item_id);
        contentValues.put(CrmDatabaseHelper.RID, id);
        contentValues.put(CrmDatabaseHelper.ITEM_SUMMARY, item_summary);
        contentValues.put(CrmDatabaseHelper.MODULE_NAME, module_name);
        //Check InsertLastViewd module table exists
        String queryAck = "SELECT * FROM "+ CrmDatabaseHelper.RECENT_TABLE_NAME +" Where "+ CrmDatabaseHelper.ITEM_ID +" = '"
                + item_id + "'";
        Cursor cursor  = db.rawQuery(queryAck, null);
        long get_id = 0;
        if(cursor.getCount() == 0) {
            get_id = db.insert(CrmDatabaseHelper.RECENT_TABLE_NAME, CrmDatabaseHelper.ITEM_ID, contentValues);

        }

        return get_id;

    }

    //Insert data to Account Table
    public long InsertAccountTable(String account_id, String name, String email1, String parent_name,String phone_office, String phone_fax, String website, String employees, String ticker_symbol, String annual_revenue, String billing_address_street,String billing_address_street_2, String billing_address_street_3 ,String billing_address_street_4,
                                   String billing_address_city ,String billing_address_state ,String billing_address_postalcode ,String billing_address_country ,String shipping_address_street ,String shipping_address_street_2 ,String shipping_address_street_3 ,String shipping_address_street_4 ,String shipping_address_city ,String shipping_address_state ,
                                   String shipping_address_postalcode ,String shipping_address_country ,String assigned_user_name ,String created_by_name ,String date_entered ,String date_modified ,String deleted ){
        SQLiteDatabase db = helper.getWritableDatabase();   //Get sqlite databse
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrmDatabaseHelper.ACCOUNT_ID , account_id);
        contentValues.put(CrmDatabaseHelper.NAME ,name);
        contentValues.put(CrmDatabaseHelper.EMAIL1 ,email1);
        contentValues.put(CrmDatabaseHelper.PARENT_NAME ,parent_name);
        contentValues.put(CrmDatabaseHelper.PHONE_OFFICE ,phone_office);
        contentValues.put(CrmDatabaseHelper.PHONE_FAX ,phone_fax);
        contentValues.put(CrmDatabaseHelper.WEBSITE ,website);
        contentValues.put(CrmDatabaseHelper.EMPLOYEES ,employees);
        contentValues.put(CrmDatabaseHelper.TICKER_SYMBOL ,ticker_symbol);
        contentValues.put(CrmDatabaseHelper.ANNUAL_REVENUE ,annual_revenue);
        contentValues.put(CrmDatabaseHelper.BILLING_ADDRESS_STREET ,billing_address_street);
        contentValues.put(CrmDatabaseHelper.BILLING_ADDRESS_STREET_2 ,billing_address_street_2);
        contentValues.put(CrmDatabaseHelper.BILLING_ADDRESS_STREET_3 ,billing_address_street_3);
        contentValues.put(CrmDatabaseHelper.BILLING_ADDRESS_STREET_4 ,billing_address_street_4);
        contentValues.put(CrmDatabaseHelper.BILLING_ADDRESS_CITY ,billing_address_city);
        contentValues.put(CrmDatabaseHelper.BILLING_ADDRESS_STATE ,billing_address_state);
        contentValues.put(CrmDatabaseHelper.BILLING_ADDRESS_POSTALCODE , billing_address_postalcode);
        contentValues.put(CrmDatabaseHelper.BILLING_ADDRESS_COUNTRY ,billing_address_country);
        contentValues.put(CrmDatabaseHelper.SHIPPING_ADDRESS_STREET ,shipping_address_street);
        contentValues.put(CrmDatabaseHelper.SHIPPING_ADDRESS_STREET_2   ,shipping_address_street_2);
        contentValues.put(CrmDatabaseHelper.SHIPPING_ADDRESS_STREET_3 ,shipping_address_street_3);
        contentValues.put(CrmDatabaseHelper.SHIPPING_ADDRESS_STREET_4 ,shipping_address_street_4);
        contentValues.put(CrmDatabaseHelper.SHIPPING_ADDRESS_CITY ,shipping_address_city);
        contentValues.put(CrmDatabaseHelper.SHIPPING_ADDRESS_STATE ,shipping_address_state);
        contentValues.put(CrmDatabaseHelper.SHIPPING_ADDRESS_POSTALCODE ,shipping_address_postalcode);
        contentValues.put(CrmDatabaseHelper.SHIPPING_ADDRESS_COUNTRY ,shipping_address_country);
        contentValues.put(CrmDatabaseHelper.ASSIGNED_USER_NAME ,assigned_user_name);
        contentValues.put(CrmDatabaseHelper.CREATED_BY_NAME ,created_by_name);
        contentValues.put(CrmDatabaseHelper.DATE_ENTERED ,date_entered);
        contentValues.put(CrmDatabaseHelper.DATE_MODIFIED ,date_modified);
        contentValues.put(CrmDatabaseHelper.DELETED ,deleted);
        //Check InsertLastViewd module table exists
        String queryAck = "SELECT * FROM "+ CrmDatabaseHelper.ACCOUNTS_TABLE_NAME +" Where "+ CrmDatabaseHelper.NAME +" = '"
                + name + "'";
        Cursor cursor  = db.rawQuery(queryAck, null);
        long get_account_id = 0;
        if(cursor.getCount() == 0) {
            get_account_id = db.insert(CrmDatabaseHelper.ACCOUNTS_TABLE_NAME,CrmDatabaseHelper.NAME, contentValues);
        }

        return get_account_id;

    }

    //Insert data to Opportunities Table
    public long InsertOpportunitiesTable(String opportunity_id, String name, String account_name, String amount,String amount_usdollar, String assigned_user_id,String assigned_user_name, String campaign_name,
                                         String created_by, String created_by_name,String currency_id, String currency_name, String currency_symbol, String date_closed,String date_entered, String date_modified, String description,String lead_source,String modified_by_name, String modified_user_id ,String next_step,
                                        String opportunity_type ,String probability ,String sales_stage, String deleted  ){
        SQLiteDatabase db = helper.getWritableDatabase();   //Get sqlite databse
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrmDatabaseHelper.OPPORTUNITY_ID , opportunity_id);
        contentValues.put(CrmDatabaseHelper.NAME ,name);
        contentValues.put(CrmDatabaseHelper.ACCOUNT_NAME ,account_name);
        contentValues.put(CrmDatabaseHelper.AMOUNT ,amount);
        contentValues.put(CrmDatabaseHelper.AMOUNT_USDOLLAR ,amount_usdollar);
        contentValues.put(CrmDatabaseHelper.ASSIGNED_USER_ID ,assigned_user_id);
        contentValues.put(CrmDatabaseHelper.ASSIGNED_USER_NAME ,assigned_user_name);
        contentValues.put(CrmDatabaseHelper.CAMPAIGN_NAME ,campaign_name);
        contentValues.put(CrmDatabaseHelper.CREATED_BY ,created_by);
        contentValues.put(CrmDatabaseHelper.CREATED_BY_NAME ,created_by_name);
        contentValues.put(CrmDatabaseHelper.CURRENCY_ID ,currency_id);
        contentValues.put(CrmDatabaseHelper.CURRENCY_NAME ,currency_name);
        contentValues.put(CrmDatabaseHelper.CURRENCY_SYMBOL ,currency_symbol);
        contentValues.put(CrmDatabaseHelper.DATE_CLOSED ,date_closed);
        contentValues.put(CrmDatabaseHelper.DATE_ENTERED ,date_entered);
        contentValues.put(CrmDatabaseHelper.DATE_MODIFIED ,date_modified);
        contentValues.put(CrmDatabaseHelper.DESCRIPTION , description);
        contentValues.put(CrmDatabaseHelper.LEAD_SOURCE ,lead_source);
        contentValues.put(CrmDatabaseHelper.MODIFIED_BY_NAME ,modified_by_name);
        contentValues.put(CrmDatabaseHelper.MODIFIED_USER_ID   ,modified_user_id);
        contentValues.put(CrmDatabaseHelper.NEXT_STEP ,next_step);
        contentValues.put(CrmDatabaseHelper.OPPORTUNITY_TYPE ,opportunity_type);
        contentValues.put(CrmDatabaseHelper.PROBABILITY ,probability);
        contentValues.put(CrmDatabaseHelper.SALES_STAGE ,sales_stage);
        contentValues.put(CrmDatabaseHelper.DELETED ,deleted);
        //Check InsertLastViewd module table exists
        String queryAck = "SELECT * FROM "+ CrmDatabaseHelper.OPPORTUNITIES_TABLE_NAME +" Where "+ CrmDatabaseHelper.NAME +" = '"
                + name + "'";
        Cursor cursor  = db.rawQuery(queryAck, null);
        long get_account_id = 0;
        if(cursor.getCount() == 0) {
            get_account_id = db.insert(CrmDatabaseHelper.OPPORTUNITIES_TABLE_NAME,CrmDatabaseHelper.NAME, contentValues);
        }

        return get_account_id;

    }

    //Insert data to Contacts Table
    public long InsertContactsTable(String contact_id, String first_name, String last_name, String account_name, String phone_mobile,String phone_work, String email1,
                                    String assigned_user_name, String created_by, String modified_by_name, String created_by_name, String date_entered, String date_modified
                                    ,String deleted){
        SQLiteDatabase db = helper.getWritableDatabase();   //Get sqlite databse
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrmDatabaseHelper.CONTACT_ID , contact_id);
        contentValues.put(CrmDatabaseHelper.FIRST_NAME ,first_name);
        contentValues.put(CrmDatabaseHelper.LAST_NAME ,last_name);
        contentValues.put(CrmDatabaseHelper.ACCOUNT_NAME ,account_name);
        contentValues.put(CrmDatabaseHelper.PHONE_MOBILE ,phone_mobile);
        contentValues.put(CrmDatabaseHelper.PHONE_WORK ,phone_work);
        contentValues.put(CrmDatabaseHelper.EMAIL1 ,email1);
        contentValues.put(CrmDatabaseHelper.ASSIGNED_USER_NAME ,assigned_user_name);
        contentValues.put(CrmDatabaseHelper.CREATED_BY ,created_by);
        contentValues.put(CrmDatabaseHelper.MODIFIED_BY_NAME ,modified_by_name);
        contentValues.put(CrmDatabaseHelper.CREATED_BY_NAME ,created_by_name);
        contentValues.put(CrmDatabaseHelper.DATE_ENTERED ,date_entered);
        contentValues.put(CrmDatabaseHelper.DATE_MODIFIED ,date_modified);
        contentValues.put(CrmDatabaseHelper.DELETED ,deleted);
        //Check InsertLastViewd module table exists
        String queryAck = "SELECT * FROM "+ CrmDatabaseHelper.CONTACTS_TABLE_NAME +" Where "+ CrmDatabaseHelper.FIRST_NAME +" = '"
                + first_name + "'";
        Cursor cursor  = db.rawQuery(queryAck, null);
        long get_contacts_id = 0;
        if(cursor.getCount() == 0) {
            get_contacts_id = db.insert(CrmDatabaseHelper.CONTACTS_TABLE_NAME,CrmDatabaseHelper.NAME, contentValues);
        }

        return get_contacts_id;
    }

    //Insert data to Leads Table
    public long InsertLeadsTable(String leads_id, String first_name, String last_name, String lead_source,String email1, String phone_work,String phone_fax,
                                 String account_name, String title, String assigned_user_name, String created_by_name, String date_entered, String date_modified,String deleted){
        SQLiteDatabase db = helper.getWritableDatabase();   //Get sqlite databse
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrmDatabaseHelper.LEADS_ID , leads_id);
        contentValues.put(CrmDatabaseHelper.FIRST_NAME ,first_name);
        contentValues.put(CrmDatabaseHelper.LAST_NAME ,last_name);
        contentValues.put(CrmDatabaseHelper.LEAD_SOURCE ,lead_source);
        contentValues.put(CrmDatabaseHelper.EMAIL1 ,email1);
        contentValues.put(CrmDatabaseHelper.PHONE_WORK ,phone_work);
        contentValues.put(CrmDatabaseHelper.PHONE_FAX ,phone_fax);
        contentValues.put(CrmDatabaseHelper.ACCOUNT_NAME ,account_name);
        contentValues.put(CrmDatabaseHelper.TITLE ,title);
        contentValues.put(CrmDatabaseHelper.ASSIGNED_USER_NAME ,assigned_user_name);
        contentValues.put(CrmDatabaseHelper.CREATED_BY_NAME ,created_by_name);
        contentValues.put(CrmDatabaseHelper.DATE_ENTERED ,date_entered);
        contentValues.put(CrmDatabaseHelper.DATE_MODIFIED ,date_modified);
        contentValues.put(CrmDatabaseHelper.DELETED ,deleted);
        //Check InsertLastViewd module table exists
        String queryAck = "SELECT * FROM "+ CrmDatabaseHelper.LEADS_TABLE_NAME +" Where "+ CrmDatabaseHelper.FIRST_NAME +" = '"
                + first_name + "'";
        Cursor cursor  = db.rawQuery(queryAck, null);
        long get_leads_id = 0;
        if(cursor.getCount() == 0) {
            get_leads_id = db.insert(CrmDatabaseHelper.LEADS_TABLE_NAME,CrmDatabaseHelper.FIRST_NAME, contentValues);
        }

        return get_leads_id;

    }

    //Insert data to Meetings Table
    public long InsertMeetingsTable(String meeting_id, String name, String contact_name, String status,String location, String start_date,String duration_hours,
                                  String duration_minutes, String assigned_user_name,String description, String created_by_name, String date_entered, String date_modified,String deleted){
        SQLiteDatabase db = helper.getWritableDatabase();   //Get sqlite databse
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrmDatabaseHelper.MEETING_ID , meeting_id);
        contentValues.put(CrmDatabaseHelper.NAME ,name);
        contentValues.put(CrmDatabaseHelper.CONTACT_NAME ,contact_name);
        contentValues.put(CrmDatabaseHelper.STATUS ,status);
        contentValues.put(CrmDatabaseHelper.LOCATION ,location);
        contentValues.put(CrmDatabaseHelper.START_DATE ,start_date);
        contentValues.put(CrmDatabaseHelper.DURATION_HOURS ,duration_hours);
        contentValues.put(CrmDatabaseHelper.DURATION_MINUTES ,duration_minutes);
        contentValues.put(CrmDatabaseHelper.ASSIGNED_USER_NAME ,assigned_user_name);
        contentValues.put(CrmDatabaseHelper.DESCRIPTION ,description);
        contentValues.put(CrmDatabaseHelper.CREATED_BY_NAME ,created_by_name);
        contentValues.put(CrmDatabaseHelper.DATE_ENTERED ,date_entered);
        contentValues.put(CrmDatabaseHelper.DATE_MODIFIED ,date_modified);
        contentValues.put(CrmDatabaseHelper.DELETED ,deleted);
        //Check InsertLastViewd module table exists
        String queryAck = "SELECT * FROM "+ CrmDatabaseHelper.MEETINGS_TABLE_NAME +" Where "+ CrmDatabaseHelper.NAME +" = '"
                + name + "'";
        Cursor cursor  = db.rawQuery(queryAck, null);
        long get_meeting_id = 0;
        if(cursor.getCount() == 0) {
            get_meeting_id = db.insert(CrmDatabaseHelper.MEETINGS_TABLE_NAME,CrmDatabaseHelper.NAME, contentValues);
        }

        return get_meeting_id;
    }

    //Insert data to Calls Table
    public long InsertCallsTable(String call_id, String name, String contact_name, String status,String start_date,String duration_hours,
                                    String duration_minutes, String assigned_user_name,String description, String created_by_name, String date_entered, String date_modified,String deleted){
        SQLiteDatabase db = helper.getWritableDatabase();   //Get sqlite databse
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrmDatabaseHelper.CALL_ID , call_id);
        contentValues.put(CrmDatabaseHelper.NAME ,name);
        contentValues.put(CrmDatabaseHelper.CONTACT_NAME ,contact_name);
        contentValues.put(CrmDatabaseHelper.STATUS ,status);
        contentValues.put(CrmDatabaseHelper.START_DATE ,start_date);
        contentValues.put(CrmDatabaseHelper.DURATION_HOURS ,duration_hours);
        contentValues.put(CrmDatabaseHelper.DURATION_MINUTES ,duration_minutes);
        contentValues.put(CrmDatabaseHelper.ASSIGNED_USER_NAME ,assigned_user_name);
        contentValues.put(CrmDatabaseHelper.DESCRIPTION ,description);
        contentValues.put(CrmDatabaseHelper.CREATED_BY_NAME ,created_by_name);
        contentValues.put(CrmDatabaseHelper.DATE_ENTERED ,date_entered);
        contentValues.put(CrmDatabaseHelper.DATE_MODIFIED ,date_modified);
        contentValues.put(CrmDatabaseHelper.DELETED ,deleted);

        String queryAck = "SELECT * FROM "+ CrmDatabaseHelper.CALLS_TABLE_NAME +" Where "+ CrmDatabaseHelper.NAME +" = '"
                + name + "'";
        Cursor cursor  = db.rawQuery(queryAck, null);
        long get_call_id = 0;
        if(cursor.getCount() == 0) {
            get_call_id = db.insert(CrmDatabaseHelper.CALLS_TABLE_NAME,CrmDatabaseHelper.NAME, contentValues);
        }

        return get_call_id;
    }

    //Insert data to Cases Table
    public long InsertCasesTable(String case_id, String name, String account_name, String case_number,String priority,String assigned_user_name,
                                 String status,String description, String resolution,String created_by_name, String date_entered, String date_modified,String deleted){
        SQLiteDatabase db = helper.getWritableDatabase();   //Get sqlite databse
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrmDatabaseHelper.CASE_ID , case_id);
        contentValues.put(CrmDatabaseHelper.NAME ,name);
        contentValues.put(CrmDatabaseHelper.ACCOUNT_NAME ,account_name);
        contentValues.put(CrmDatabaseHelper.CASE_NUMBER ,case_number);
        contentValues.put(CrmDatabaseHelper.PRIORITY ,priority);
        contentValues.put(CrmDatabaseHelper.ASSIGNED_USER_NAME ,assigned_user_name);
        contentValues.put(CrmDatabaseHelper.STATUS ,status);
        contentValues.put(CrmDatabaseHelper.DESCRIPTION ,description);
        contentValues.put(CrmDatabaseHelper.RESOLUTION ,resolution);
        contentValues.put(CrmDatabaseHelper.CREATED_BY_NAME ,created_by_name);
        contentValues.put(CrmDatabaseHelper.DATE_ENTERED ,date_entered);
        contentValues.put(CrmDatabaseHelper.DATE_MODIFIED ,date_modified);
        contentValues.put(CrmDatabaseHelper.DELETED ,deleted);

        String queryAck = "SELECT * FROM "+ CrmDatabaseHelper.CASES_TABLE_NAME +" Where "+ CrmDatabaseHelper.NAME +" = '"
                + name + "'";
        Cursor cursor  = db.rawQuery(queryAck, null);
        long get_case_id = 0;
        if(cursor.getCount() == 0) {
            get_case_id = db.insert(CrmDatabaseHelper.CASES_TABLE_NAME,CrmDatabaseHelper.NAME, contentValues);
        }

        return get_case_id;
    }

    //Insert data to CAMPAIGNS Table
    public long InsertCampaignsTable(String campaigns_id, String name, String status, String start_date,String end_date,String campaign_type,
                                 String budget, String actual_cost,String expected_cost, String expected_revenue, String impressions,String objective, String frequency, String assigned_user_name, String description,
                                 String created_by_name, String date_entered, String date_modified, String deleted){
        SQLiteDatabase db = helper.getWritableDatabase();   //Get sqlite databse
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrmDatabaseHelper.CAMPAIGNS_ID , campaigns_id);
        contentValues.put(CrmDatabaseHelper.NAME ,name);
        contentValues.put(CrmDatabaseHelper.STATUS ,status);
        contentValues.put(CrmDatabaseHelper.START_DATE ,start_date);
        contentValues.put(CrmDatabaseHelper.END_DATE ,end_date);
        contentValues.put(CrmDatabaseHelper.CAMPAIGN_TYPE ,campaign_type);
        contentValues.put(CrmDatabaseHelper.BUDGET ,budget);
        contentValues.put(CrmDatabaseHelper.ACTUAL_COST ,actual_cost);
        contentValues.put(CrmDatabaseHelper.EXPECTED_COST ,expected_cost);
        contentValues.put(CrmDatabaseHelper.EXPECTED_REVENUE ,expected_revenue);
        contentValues.put(CrmDatabaseHelper.IMPRESSIONS ,impressions);
        contentValues.put(CrmDatabaseHelper.OBJECTIVE ,objective);
        contentValues.put(CrmDatabaseHelper.FREQUENCY ,frequency);
        contentValues.put(CrmDatabaseHelper.ASSIGNED_USER_NAME ,assigned_user_name);
        contentValues.put(CrmDatabaseHelper.DESCRIPTION ,description);
        contentValues.put(CrmDatabaseHelper.CREATED_BY_NAME ,created_by_name);
        contentValues.put(CrmDatabaseHelper.DATE_ENTERED ,date_entered);
        contentValues.put(CrmDatabaseHelper.DATE_MODIFIED ,date_modified);
        contentValues.put(CrmDatabaseHelper.DELETED ,deleted);

        String queryAck = "SELECT * FROM "+ CrmDatabaseHelper.CAMPAIGNS_TABLE_NAME +" Where "+ CrmDatabaseHelper.NAME +" = '"
                + name + "'";
        Cursor cursor  = db.rawQuery(queryAck, null);
        long get_campaigns_id = 0;
        if(cursor.getCount() == 0) {
            get_campaigns_id = db.insert(CrmDatabaseHelper.CAMPAIGNS_TABLE_NAME,CrmDatabaseHelper.NAME, contentValues);
        }

        return get_campaigns_id;
    }

    //Get Data From Account Table
     public String getAccountData(){
         SQLiteDatabase db = helper.getWritableDatabase();

         String[] columns = {CrmDatabaseHelper.ACCOUNT_ID ,CrmDatabaseHelper.NAME ,CrmDatabaseHelper.EMAIL1 ,CrmDatabaseHelper.PARENT_NAME ,CrmDatabaseHelper.PHONE_OFFICE ,
                            CrmDatabaseHelper.PHONE_FAX ,CrmDatabaseHelper.WEBSITE ,CrmDatabaseHelper.EMPLOYEES ,CrmDatabaseHelper.TICKER_SYMBOL, CrmDatabaseHelper.ANNUAL_REVENUE ,
                            CrmDatabaseHelper.BILLING_ADDRESS_STREET ,CrmDatabaseHelper.BILLING_ADDRESS_STREET_2 ,CrmDatabaseHelper.BILLING_ADDRESS_STREET_3 ,CrmDatabaseHelper.BILLING_ADDRESS_STREET_4 ,CrmDatabaseHelper.BILLING_ADDRESS_CITY ,CrmDatabaseHelper.BILLING_ADDRESS_STATE ,
                            CrmDatabaseHelper.BILLING_ADDRESS_POSTALCODE ,CrmDatabaseHelper.BILLING_ADDRESS_COUNTRY ,CrmDatabaseHelper.SHIPPING_ADDRESS_STREET ,CrmDatabaseHelper.SHIPPING_ADDRESS_STREET_2 ,
                            CrmDatabaseHelper.SHIPPING_ADDRESS_STREET_3 ,CrmDatabaseHelper.SHIPPING_ADDRESS_STREET_4 ,CrmDatabaseHelper.SHIPPING_ADDRESS_CITY ,CrmDatabaseHelper.SHIPPING_ADDRESS_STATE ,CrmDatabaseHelper.SHIPPING_ADDRESS_POSTALCODE ,CrmDatabaseHelper.SHIPPING_ADDRESS_COUNTRY ,
                            CrmDatabaseHelper.ASSIGNED_USER_NAME ,CrmDatabaseHelper.CREATED_BY_NAME ,CrmDatabaseHelper.DATE_ENTERED ,CrmDatabaseHelper.DATE_MODIFIED ,CrmDatabaseHelper.DELETED};  //Adding All Available Columns for Account Table

        // String[] columns = {CrmDatabaseHelper.ID ,CrmDatabaseHelper.NAME};  //Adding All Available Columns for Account Table

         Cursor cursor = db.query(CrmDatabaseHelper.ACCOUNTS_TABLE_NAME,columns,null,null,null,null,null);
         StringBuffer buffer = new StringBuffer();
         while(cursor.moveToNext()){
             int id = cursor.getInt(0);
             String account_name = cursor.getString(1);

             buffer.append(id +" "+ account_name + "\n");
         }
         return buffer.toString();

     }

    public Cursor getLastViewData() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {CrmDatabaseHelper.ID, CrmDatabaseHelper.ITEM_ID, CrmDatabaseHelper.RID, CrmDatabaseHelper.ITEM_SUMMARY, CrmDatabaseHelper.MODULE_NAME};
        Cursor cursor = db.query(CrmDatabaseHelper.RECENT_TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(0);
            String itemId = cursor.getString(1);
            String rId = cursor.getString(2);
            String ItemSummery = cursor.getString(3);
            String moduleName = cursor.getString(4);

            buffer.append(cid + " " + itemId + " " + rId + " " + ItemSummery + " " + moduleName + "\n");

        }
        return cursor;
    }
/*
    public String getAllAvailableModule() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {CrmDatabaseHelper.ID, CrmDatabaseHelper.MODULE_KEY, CrmDatabaseHelper.MODULE_LABEL, CrmDatabaseHelper.FAVORITE_ENABLED, CrmDatabaseHelper.ACTION_EDIT, CrmDatabaseHelper.ACTION_DELETE, CrmDatabaseHelper.ACTION_LIST, CrmDatabaseHelper.ACTION_VIEW, CrmDatabaseHelper.ACTION_IMPORT, CrmDatabaseHelper.ACTION_EXPORT};
        Cursor cursor = db.query(CrmDatabaseHelper.TABLE_NAME_GET_AVAILABLE_MODULES, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(0);
            String moduleKey = cursor.getString(1);
            String moduleLabel = cursor.getString(2);
            String favoriteEnable = cursor.getString(3);
            String ActionEdit = cursor.getString(4);
            String ActionDelete = cursor.getString(5);
            String ActionList = cursor.getString(6);
            String ActionView = cursor.getString(7);
            String ActionImport = cursor.getString(8);
            String actionImport = cursor.getString(9);

            buffer.append(moduleLabel + " "+ ActionView + "\n");

        }
        return buffer.toString();
    }
*/
    public Cursor getAllAvailableModule_1() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {CrmDatabaseHelper.ID, CrmDatabaseHelper.MODULE_KEY, CrmDatabaseHelper.MODULE_LABEL, CrmDatabaseHelper.FAVORITE_ENABLED, CrmDatabaseHelper.ACTION_EDIT, CrmDatabaseHelper.ACTION_DELETE, CrmDatabaseHelper.ACTION_LIST, CrmDatabaseHelper.ACTION_VIEW, CrmDatabaseHelper.ACTION_IMPORT, CrmDatabaseHelper.ACTION_EXPORT};
        Cursor cursor = db.query(CrmDatabaseHelper.TABLE_NAME_GET_AVAILABLE_MODULES, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(0);
            String moduleKey = cursor.getString(1);
            String moduleLabel = cursor.getString(2);
            String favoriteEnable = cursor.getString(3);
            String ActionEdit = cursor.getString(4);
            String ActionDelete = cursor.getString(5);
            String ActionList = cursor.getString(6);
            String ActionView = cursor.getString(7);
            String ActionImport = cursor.getString(8);
            String actionImport = cursor.getString(9);

            buffer.append(moduleLabel + "\n");

        }
        return cursor;
    }

    public Cursor getAllAccount()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {CrmDatabaseHelper.ACCOUNT_ID, CrmDatabaseHelper.NAME, CrmDatabaseHelper.EMAIL1, CrmDatabaseHelper.WEBSITE, CrmDatabaseHelper.PHONE_OFFICE, CrmDatabaseHelper.BILLING_ADDRESS_STREET, CrmDatabaseHelper.BILLING_ADDRESS_STREET_2, CrmDatabaseHelper.BILLING_ADDRESS_STREET_3
                            , CrmDatabaseHelper.BILLING_ADDRESS_STREET_4, CrmDatabaseHelper.BILLING_ADDRESS_CITY,CrmDatabaseHelper.BILLING_ADDRESS_STATE,CrmDatabaseHelper.BILLING_ADDRESS_COUNTRY, CrmDatabaseHelper.BILLING_ADDRESS_POSTALCODE};
        Cursor cursor = db.query(CrmDatabaseHelper.ACCOUNTS_TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            String website = cursor.getString(3);
            String phoneOffice = cursor.getString(4);
            String billingAddressStreet = cursor.getString(5);
            String billingAddressStreet2 = cursor.getString(6);
            String billingAddressStreet3 = cursor.getString(7);
            String billingAddressStreet4 = cursor.getString(8);
            String billingAddressCity = cursor.getString(9);
            String billingAddressState = cursor.getString(10);
            String billingAddressCountry = cursor.getString(11);
            String billingAddressPostalCode = cursor.getString(12);

            buffer.append(name +"\n");
        }
        return cursor;

    }

    public Cursor getAllCall()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {CrmDatabaseHelper.CALL_ID, CrmDatabaseHelper.NAME};
        Cursor cursor = db.query(CrmDatabaseHelper.CALLS_TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            buffer.append(name +"\n");
        }
        return cursor;

    }

    public Cursor getAllCases()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {CrmDatabaseHelper.CASE_ID, CrmDatabaseHelper.NAME};
        Cursor cursor = db.query(CrmDatabaseHelper.CASES_TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            buffer.append(name +"\n");
        }
        return cursor;

    }

    public Cursor getAllContacts()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {CrmDatabaseHelper.CONTACT_ID, CrmDatabaseHelper.FIRST_NAME,CrmDatabaseHelper.LAST_NAME};
        Cursor cursor = db.query(CrmDatabaseHelper.CONTACTS_TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            String last_name = cursor.getString(1);
            buffer.append(name + " " + last_name +"\n");
        }
        return cursor;

    }

    public Cursor getAllLeads()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {CrmDatabaseHelper.LEADS_ID, CrmDatabaseHelper.FIRST_NAME,CrmDatabaseHelper.LAST_NAME};
        Cursor cursor = db.query(CrmDatabaseHelper.LEADS_TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            String last_name = cursor.getString(1);
            buffer.append(name + " " + last_name +"\n");
        }
        return cursor;

    }
    public Cursor getAllMeetings()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {CrmDatabaseHelper.MEETING_ID, CrmDatabaseHelper.NAME};
        Cursor cursor = db.query(CrmDatabaseHelper.MEETINGS_TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            buffer.append(name +"\n");
        }
        return cursor;

    }
    public Cursor getAllOpportunities()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {CrmDatabaseHelper.OPPORTUNITY_ID, CrmDatabaseHelper.NAME};
        Cursor cursor = db.query(CrmDatabaseHelper.OPPORTUNITIES_TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            buffer.append(name +"\n");
        }
        return cursor;

    }
    public Cursor getAccount(String entry_id){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {CrmDatabaseHelper.ACCOUNT_ID, CrmDatabaseHelper.NAME, CrmDatabaseHelper.WEBSITE, CrmDatabaseHelper.EMAIL1, CrmDatabaseHelper.PHONE_OFFICE, CrmDatabaseHelper.BILLING_ADDRESS_STREET, CrmDatabaseHelper.BILLING_ADDRESS_STREET_2, CrmDatabaseHelper.BILLING_ADDRESS_STREET_3
                ,CrmDatabaseHelper.BILLING_ADDRESS_STREET_4,CrmDatabaseHelper.BILLING_ADDRESS_CITY, CrmDatabaseHelper.BILLING_ADDRESS_STATE, CrmDatabaseHelper.BILLING_ADDRESS_POSTALCODE, CrmDatabaseHelper.BILLING_ADDRESS_COUNTRY};
        Cursor cursor = db.query(CrmDatabaseHelper.ACCOUNTS_TABLE_NAME,columns,CrmDatabaseHelper.ACCOUNT_ID+" = '"+entry_id+"'",null,null,null,null);
        while(cursor.moveToNext()){
            String account_id = cursor.getString(0);
            String name = cursor.getString(1);
            String web = cursor.getString(2);
            String email = cursor.getString(3);
            String phone_office = cursor.getString(4);
            String street_1 = cursor.getString(5);
            String string_2 = cursor.getString(6);
            String street_3 = cursor.getString(7);
            String street_4 = cursor.getString(8);
            String city = cursor.getString(9);
            String state = cursor.getString(10);
            String postal_code = cursor.getString(11);
            String country = cursor.getString(12);
        }
        return cursor;
    }

    static class CrmDatabaseHelper extends SQLiteOpenHelper {

        //Database Name
        private static final String DATABASE_NAME = "wakensysCRM_database";
        //Database Version
        private static final int DATABASE_VERSION = 19;
        //Table Names
        private static final String TABLE_NAME_GET_AVAILABLE_MODULES = "get_available_modules";

        private static final String ACCOUNTS_TABLE_NAME = "accounts";
        private static final String CONTACTS_TABLE_NAME = "contacts";
        private static final String LEADS_TABLE_NAME = "leads";
        private static final String RECENT_TABLE_NAME = "recent";
        private static final String OPPORTUNITIES_TABLE_NAME = "opportunities";
        private static final String MEETINGS_TABLE_NAME = "meetings";
        private static final String CALLS_TABLE_NAME = "calls";
        private static final String CASES_TABLE_NAME = "cases";
        private static final String CAMPAIGNS_TABLE_NAME = "campaigns";
        private Context context;

        //Table Modules Columns
        private static final String ID = "_id";
        private static final String MODULE_KEY = "module_key";
        private static final String MODULE_LABEL = "module_label";
        private static final String FAVORITE_ENABLED = "favorite_enabled";
        private static final String ACTION_EDIT = "action_edit";
        private static final String ACTION_DELETE = "action_delete";
        private static final String ACTION_LIST = "action_list";
        private static final String ACTION_VIEW = "action_view";
        private static final String ACTION_IMPORT = "action_import";
        private static final String ACTION_EXPORT = "action_export";

        //Table Opportunities Columns
        private static final String OPPORTUNITY_ID = "opportunity_id";
        private static final String AMOUNT = "amount";
        private static final String AMOUNT_USDOLLAR = "amount_usdollar";
        private static final String ASSIGNED_USER_ID = "assigned_user_id";
        private static final String CAMPAIGN_NAME = "campaign_name";
        private static final String CURRENCY_ID = "currency_id";
        private static final String CURRENCY_NAME = "currency_name";
        private static final String CURRENCY_SYMBOL = "currency_symbol";
        private static final String DATE_CLOSED = "date_closed";
        private static final String DESCRIPTION = "description";
        private static final String MODIFIED_USER_ID = "modified_user_id";
        private static final String NEXT_STEP = "next_step";
        private static final String OPPORTUNITY_TYPE = "opportunity_type";
        private static final String PROBABILITY = "probability";
        private static final String SALES_STAGE = "sales_stage";
        private static final String LEAD_SOURCE = "lead_source";

        //Table Recent Item Columns
        private static final String ITEM_ID = "item_id";
        private static final String RID = "r_id";
        private static final String ITEM_SUMMARY = "item_summary";
        private static final String MODULE_NAME = "module_name";

        //Table Account Columns
        private static final String ACCOUNT_ID = "account_id";
        private static final String NAME = "name";
        private static final String EMAIL1 = "email1";
        private static final String PARENT_NAME = "parent_name";
        private static final String PHONE_OFFICE = "phone_office";
        private static final String PHONE_FAX = "phone_fax";
        private static final String WEBSITE = "website";
        private static final String EMPLOYEES = "employees";
        private static final String TICKER_SYMBOL = "ticker_symbol";
        private static final String ANNUAL_REVENUE = "annual_revenue";
        private static final String BILLING_ADDRESS_STREET = "billing_address_street";
        private static final String BILLING_ADDRESS_STREET_2 = "billing_address_street_2";
        private static final String BILLING_ADDRESS_STREET_3 = "billing_address_street_3";
        private static final String BILLING_ADDRESS_STREET_4 = "billing_address_street_4";
        private static final String BILLING_ADDRESS_CITY = "billing_address_city";
        private static final String BILLING_ADDRESS_STATE = "billing_address_state";
        private static final String BILLING_ADDRESS_POSTALCODE = "billing_address_postalcode";
        private static final String BILLING_ADDRESS_COUNTRY = "billing_address_country";
        private static final String SHIPPING_ADDRESS_STREET = "shipping_address_street";
        private static final String SHIPPING_ADDRESS_STREET_2 = "shipping_address_street_2";
        private static final String SHIPPING_ADDRESS_STREET_3 = "shipping_address_street_3";
        private static final String SHIPPING_ADDRESS_STREET_4 = "shipping_address_street_4";
        private static final String SHIPPING_ADDRESS_CITY = "shipping_address_city";
        private static final String SHIPPING_ADDRESS_STATE = "shipping_address_state";
        private static final String SHIPPING_ADDRESS_POSTALCODE = "shipping_address_postalcode";
        private static final String SHIPPING_ADDRESS_COUNTRY = "shipping_address_country";
        private static final String ASSIGNED_USER_NAME = "assigned_user_name";
        private static final String CREATED_BY_NAME = "created_by_name";
        private static final String DATE_ENTERED = "date_entered";
        private static final String DATE_MODIFIED = "date_modified";
        private static final String DELETED = "deleted";

        //Table Contacts Columns
        private static final String CONTACT_ID = "contact_id";
        private static final String FIRST_NAME = "first_name";
        private static final String LAST_NAME = "last_name";
        private static final String ACCOUNT_NAME = "account_name";
        private static final String PHONE_MOBILE = "phone_mobile";
        private static final String PHONE_WORK = "phone_work";
        private static final String CREATED_BY = "created_by";
        private static final String MODIFIED_BY_NAME = "modified_by_name";

        //Table Leads Columns
        private static final String LEADS_ID = "lead_id";
        private static final String TITLE = "title";

        //Table Meetings Columns
        private static final String MEETING_ID = "meeting_id";
        private static final String STATUS = "status";
        private static final String LOCATION = "location";
        private static final String START_DATE = "start_date";
        private static final String DURATION_HOURS = "duration_hours";
        private static final String DURATION_MINUTES = "duration_minutes";

        //Table Calls Columns
        private static final String CALL_ID = "call_id";
        private static final String CONTACT_NAME = "contact_name";

        //Table Cases Columns
        private static final String CASE_ID = "case_id";
        private static final String CASE_NUMBER = "case_number";
        private static final String PRIORITY = "priority";
        private static final String RESOLUTION = "resolution";

        //Table Campaigns Columns
        private static final String CAMPAIGNS_ID = "campaigns_id";
        private static final String END_DATE = "end_date";
        private static final String CAMPAIGN_TYPE = "campaign_type";
        private static final String BUDGET = "budget";
        private static final String ACTUAL_COST = "actual_cost";
        private static final String EXPECTED_COST = "expected_cost";
        private static final String EXPECTED_REVENUE = "expected_revenue";
        private static final String IMPRESSIONS = "impressions";
        private static final String OBJECTIVE = "objective";
        private static final String FREQUENCY = "frequency";


        //Drop Tables
        private static final String DROP_TABLE_GET_AVAILABLE_MODULES = "DROP TABLE IF EXISTS " + TABLE_NAME_GET_AVAILABLE_MODULES;
        private static final String DROP_TABLE_LAST_VIEWED = " DROP TABLE IF EXISTS " + RECENT_TABLE_NAME;
        private static final String DROP_TABLE_ACCOUNT = " DROP TABLE IF EXISTS " + ACCOUNTS_TABLE_NAME;
        private static final String DROP_TABLE_CONTACT = " DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME;
        private static final String DROP_TABLE_OPPORTUNITIES = " DROP TABLE IF EXISTS " + OPPORTUNITIES_TABLE_NAME;
        private static final String DROP_TABLE_LEADS = " DROP TABLE IF EXISTS " + LEADS_TABLE_NAME;
        private static final String DROP_TABLE_MEETINGS = " DROP TABLE IF EXISTS " + MEETINGS_TABLE_NAME;
        private static final String DROP_TABLE_CALLS = " DROP TABLE IF EXISTS " + CALLS_TABLE_NAME;
        private static final String DROP_TABLE_CASES = " DROP TABLE IF EXISTS " + CASES_TABLE_NAME;
        private static final String DROP_TABLE_CAMPAIGNS = " DROP TABLE IF EXISTS " + CAMPAIGNS_TABLE_NAME;

        private static final String CREATE_TABLE_GET_AVAILABLE_MODULES = "CREATE TABLE " + TABLE_NAME_GET_AVAILABLE_MODULES + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MODULE_KEY + " VARCHAR(255)," +
                MODULE_LABEL + " VARCHAR(255), " + FAVORITE_ENABLED + " BOOLEAN , " + ACTION_EDIT + " INTEGER, " +
                ACTION_DELETE + " INTEGER, " + ACTION_LIST + " INTEGER, " + ACTION_VIEW + " INTEGER, " + ACTION_IMPORT + " INTEGER, " +
                ACTION_EXPORT + " INTEGER );";

        private static final String CREATE_TABLE_LAST_VIEWED = " CREATE TABLE " + RECENT_TABLE_NAME + "(  " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_ID + " VARCHAR(255), " +
                RID + " INTEGER, " + ITEM_SUMMARY + " VARCHAR(255), " + MODULE_NAME + " VARCHAR(255)); ";

        private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + ACCOUNTS_TABLE_NAME + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + ACCOUNT_ID + " VARCHAR(255), "  + NAME + " VARCHAR(255),"
                + EMAIL1 + " VARCHAR(255),"
                + PARENT_NAME + " VARCHAR(255),"
                + PHONE_OFFICE + " VARCHAR(255),"
                + PHONE_FAX + " VARCHAR(255),"
                + WEBSITE + " VARCHAR(255),"
                + EMPLOYEES + " VARCHAR(255),"
                + TICKER_SYMBOL + " VARCHAR(255),"
                + ANNUAL_REVENUE + " VARCHAR(255),"
                + BILLING_ADDRESS_STREET + " VARCHAR(255),"
                + BILLING_ADDRESS_STREET_2 + " VARCHAR(255),"
                + BILLING_ADDRESS_STREET_3 + " VARCHAR(255),"
                + BILLING_ADDRESS_STREET_4 + " VARCHAR(255),"
                + BILLING_ADDRESS_CITY + " VARCHAR(255),"
                + BILLING_ADDRESS_STATE + " VARCHAR(255),"
                + BILLING_ADDRESS_POSTALCODE + " VARCHAR(255),"
                + BILLING_ADDRESS_COUNTRY + " VARCHAR(255),"
                + SHIPPING_ADDRESS_STREET + " VARCHAR(255),"
                + SHIPPING_ADDRESS_STREET_2 + " VARCHAR(255),"
                + SHIPPING_ADDRESS_STREET_3 + " VARCHAR(255),"
                + SHIPPING_ADDRESS_STREET_4 + " VARCHAR(255),"
                + SHIPPING_ADDRESS_CITY + " VARCHAR(255),"
                + SHIPPING_ADDRESS_STATE + " VARCHAR(255),"
                + SHIPPING_ADDRESS_POSTALCODE + " VARCHAR(255),"
                + SHIPPING_ADDRESS_COUNTRY + " VARCHAR(255),"
                + ASSIGNED_USER_NAME + " VARCHAR(255),"
                + CREATED_BY_NAME + " VARCHAR(255),"
                + DATE_ENTERED + " VARCHAR(255),"
                + DATE_MODIFIED + " VARCHAR(255),"
                + DELETED + " INTEGER );";

        //Create Contacts Table
        private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE " + CONTACTS_TABLE_NAME + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTACT_ID + " VARCHAR(255), "  + FIRST_NAME + " VARCHAR(255),"
                + LAST_NAME + " VARCHAR(255),"
                + ACCOUNT_NAME + " VARCHAR(255),"
                + PHONE_MOBILE + " VARCHAR(255),"
                + PHONE_WORK + " VARCHAR(255),"
                + EMAIL1 + " VARCHAR(255),"
                + ASSIGNED_USER_NAME + " VARCHAR(255),"
                + CREATED_BY + " VARCHAR(255),"
                + MODIFIED_BY_NAME + " VARCHAR(255),"
                + CREATED_BY_NAME + " VARCHAR(255),"
                + DATE_ENTERED + " VARCHAR(255),"
                + DATE_MODIFIED + " VARCHAR(255),"
                + DELETED + " INTEGER );";

        //Create Opportunities Table
        private static final String CREATE_TABLE_OPPORTUNITIES = "CREATE TABLE " + OPPORTUNITIES_TABLE_NAME + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + OPPORTUNITY_ID + " VARCHAR(255), "  + NAME + " VARCHAR(255),"
                + ACCOUNT_NAME + " VARCHAR(255),"
                + AMOUNT + " VARCHAR(255),"
                + AMOUNT_USDOLLAR + " VARCHAR(255),"
                + ASSIGNED_USER_ID + " VARCHAR(255),"
                + ASSIGNED_USER_NAME + " VARCHAR(255),"
                + CAMPAIGN_NAME + " VARCHAR(255),"
                + CREATED_BY + " VARCHAR(255),"
                + CREATED_BY_NAME + " VARCHAR(255),"
                + CURRENCY_ID + " VARCHAR(255),"
                + CURRENCY_NAME + " VARCHAR(255),"
                + CURRENCY_SYMBOL + " VARCHAR(255),"
                + DATE_CLOSED + " VARCHAR(255),"
                + DATE_ENTERED + " VARCHAR(255),"
                + DATE_MODIFIED + " VARCHAR(255),"
                + DESCRIPTION + " VARCHAR(255),"
                + LEAD_SOURCE + " VARCHAR(255),"
                + MODIFIED_BY_NAME + " VARCHAR(255),"
                + MODIFIED_USER_ID + " VARCHAR(255),"
                + NEXT_STEP + " VARCHAR(255),"
                + OPPORTUNITY_TYPE + " VARCHAR(255),"
                + PROBABILITY + " VARCHAR(255),"
                + SALES_STAGE + " VARCHAR(255),"
                + DELETED + " INTEGER );";

        //Create Leads Table
        private static final String CREATE_TABLE_LEADS = "CREATE TABLE " + LEADS_TABLE_NAME + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + LEADS_ID + " VARCHAR(255), "
                + FIRST_NAME + " VARCHAR(255),"
                + LAST_NAME + " VARCHAR(255),"
                + LEAD_SOURCE + " VARCHAR(255),"
                + EMAIL1 + " VARCHAR(255),"
                + PHONE_WORK + " VARCHAR(255),"
                + PHONE_FAX + " VARCHAR(255),"
                + ACCOUNT_NAME + " VARCHAR(255),"
                + TITLE + " VARCHAR(255),"
                + ASSIGNED_USER_NAME + " VARCHAR(255),"
                + CREATED_BY_NAME + " VARCHAR(255),"
                + DATE_ENTERED + " VARCHAR(255),"
                + DATE_MODIFIED + " VARCHAR(255),"
                + DELETED + " VARCHAR(255));";

        //Create Meetings Table
        private static final String CREATE_TABLE_MEETINGS= "CREATE TABLE " + MEETINGS_TABLE_NAME + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + MEETING_ID + " VARCHAR(255), "
                + NAME + " VARCHAR(255),"
                + CONTACT_NAME + " VARCHAR(255),"
                + STATUS + " VARCHAR(255),"
                + LOCATION + " VARCHAR(255),"
                + START_DATE + " VARCHAR(255),"
                + DURATION_HOURS + " VARCHAR(255),"
                + DURATION_MINUTES + " VARCHAR(255),"
                + ASSIGNED_USER_NAME + " VARCHAR(255),"
                + DESCRIPTION + " VARCHAR(255),"
                + CREATED_BY_NAME + " VARCHAR(255),"
                + DATE_ENTERED + " VARCHAR(255),"
                + DATE_MODIFIED + " VARCHAR(255),"
                + DELETED + " VARCHAR(255));";

        //Create Calls Table
        private static final String CREATE_TABLE_CALLS = "CREATE TABLE " + CALLS_TABLE_NAME + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + CALL_ID + " VARCHAR(255), "
                + NAME + " VARCHAR(255),"
                + CONTACT_NAME + " VARCHAR(255),"
                + STATUS + " VARCHAR(255),"
                + START_DATE + " VARCHAR(255),"
                + DURATION_HOURS + " VARCHAR(255),"
                + DURATION_MINUTES + " VARCHAR(255),"
                + ASSIGNED_USER_NAME + " VARCHAR(255),"
                + DESCRIPTION + " VARCHAR(255),"
                + CREATED_BY_NAME + " VARCHAR(255),"
                + DATE_ENTERED + " VARCHAR(255),"
                + DATE_MODIFIED + " VARCHAR(255),"
                + DELETED + " VARCHAR(255));";

        //Create Cases Table
        private static final String CREATE_TABLE_CASES = "CREATE TABLE " + CASES_TABLE_NAME + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + CASE_ID + " VARCHAR(255), "
                + NAME + " VARCHAR(255),"
                + ACCOUNT_NAME + " VARCHAR(255),"
                + CASE_NUMBER + " VARCHAR(255),"
                + PRIORITY + " VARCHAR(255),"
                + ASSIGNED_USER_NAME + " VARCHAR(255),"
                + STATUS + " VARCHAR(255),"
                + DESCRIPTION + " VARCHAR(255),"
                + RESOLUTION + " VARCHAR(255),"
                + CREATED_BY_NAME + " VARCHAR(255),"
                + DATE_ENTERED + " VARCHAR(255),"
                + DATE_MODIFIED + " VARCHAR(255),"
                + DELETED + " VARCHAR(255));";
/*
        //Create Cases Table
        private static final String CREATE_TABLE_CAMPAIGNS = "CREATE TABLE " + CAMPAIGNS_TABLE_NAME + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + CAMPAIGNS_ID + " VARCHAR(255), "
                + NAME + " VARCHAR(255),"
                + STATUS + " VARCHAR(255),"
                + START_DATE + " VARCHAR(255),"
                + END_DATE + " VARCHAR(255),"
                + CAMPAIGN_TYPE + " VARCHAR(255),"
                + BUDGET + " VARCHAR(255),"
                + ACTUAL_COST + " VARCHAR(255),"
                + EXPECTED_COST + " VARCHAR(255),"
                + EXPECTED_REVENUE + " VARCHAR(255),"
                + IMPRESSIONS + " VARCHAR(255),"
                + OBJECTIVE + " VARCHAR(255),"
                + FREQUENCY + " VARCHAR(255),"
                + ASSIGNED_USER_NAME + " VARCHAR(255),"
                + EXPECTED_COST + " VARCHAR(255),"
                + DESCRIPTION + " VARCHAR(255),"
                + CREATED_BY_NAME + " VARCHAR(255),"
                + DATE_ENTERED + " VARCHAR(255),"
                + DATE_MODIFIED + " VARCHAR(255),"
                + DELETED + " VARCHAR(255));";
        */
        public CrmDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_GET_AVAILABLE_MODULES);
            db.execSQL(CREATE_TABLE_LAST_VIEWED);
            db.execSQL(CREATE_TABLE_ACCOUNT);
            db.execSQL(CREATE_TABLE_CONTACTS);
            db.execSQL(CREATE_TABLE_OPPORTUNITIES);
            db.execSQL(CREATE_TABLE_LEADS);
            db.execSQL(CREATE_TABLE_MEETINGS);
            db.execSQL(CREATE_TABLE_CALLS);
            db.execSQL(CREATE_TABLE_CASES);
           // db.execSQL(CREATE_TABLE_CAMPAIGNS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE_GET_AVAILABLE_MODULES);
            db.execSQL(DROP_TABLE_LAST_VIEWED);
            db.execSQL(DROP_TABLE_ACCOUNT);
            db.execSQL(DROP_TABLE_CONTACT);
            db.execSQL(DROP_TABLE_OPPORTUNITIES);
            db.execSQL(DROP_TABLE_LEADS);
            db.execSQL(DROP_TABLE_MEETINGS);
            db.execSQL(DROP_TABLE_CALLS);
            db.execSQL(DROP_TABLE_CASES);
           // db.execSQL(DROP_TABLE_CAMPAIGNS);
            onCreate(db);
        }
    }

}
