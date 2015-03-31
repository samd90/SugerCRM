package com.androidcrm.wakensys.sugercrm.data_sync;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.androidcrm.wakensys.sugercrm.LoginController;

import java.util.HashMap;

/**
 * Created by Wakensys on 3/23/2015.
 */
public class SessionManagement {

    SharedPreferences pref;                             // Shared Preference
    Editor editor;                                      // Editor for shared preference
    Context _context;                                   // Context
    int PRIVATE_MODE = 0;                               // shared Preference Mode
    private static final String PREF_NAME = "crm";      // Shared preference file name
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_SESSION_ID = "session_id";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_REST_URL = "rest_url";

    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    // Create Login session
    public void createLoginSession(String rest_url, String name, String password, String session_id){
        editor.putBoolean(IS_LOGIN, true);                  // check user is logged
        editor.putString(KEY_REST_URL, rest_url);           // storing url in pref
        editor.putString(KEY_NAME, name);                   // storing name in pref
        editor.putString(KEY_PASSWORD, password);           // storing password in pref
        editor.putString(KEY_SESSION_ID, session_id);
        editor.commit();
    }

    public void checkUserLogin(){
        // check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginController.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    // Get stored session data
    public HashMap<String , String>getUserData(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_REST_URL, pref.getString(KEY_REST_URL, null));                 // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));                         // Name
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_SESSION_ID, pref.getString(KEY_SESSION_ID, null));
        return user;
    }

    // Clear session details
    public void logoutUSer(){
        // Clearing All data from shared preference
        editor.clear();
        editor.commit();                // commit the changes

        Intent i = new Intent(_context, LoginController.class);
        // closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);          // add flag to new task
        _context.startActivity(i);

    }

    // Quick check for login
    //Get login state
    public boolean isLoggedIn(){
        return  pref.getBoolean(IS_LOGIN, false);
    }
}
