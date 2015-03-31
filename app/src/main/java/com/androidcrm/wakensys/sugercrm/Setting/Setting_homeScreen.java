package com.androidcrm.wakensys.sugercrm.Setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.androidcrm.wakensys.sugercrm.AddNewEntry.AddNewItem_SelectMenu;
import com.androidcrm.wakensys.sugercrm.MainActivity;
import com.androidcrm.wakensys.sugercrm.R;
import com.androidcrm.wakensys.sugercrm.fragment.Fragment_home;

/**
 * Created by Wakensys on 3/24/2015.
 */
public class Setting_homeScreen extends ActionBarActivity implements View.OnClickListener {
    private Toolbar toolbar;
    CheckBox checkBox_account, checkBox_contact, checkBox_tasks, checkBox_opportunity, checkBox_meeting, checkBox_cases, checkBox_call,checkBox_leads;
    private Button done;
    public static boolean isAccount = true;
    public static boolean isContact = true;
    public static boolean isTasks = true;
    public static boolean isOpportunity = true;
    public static boolean isMeeting =true;
    public static boolean isCases =true;
    public static boolean isCall =true;
    public static boolean isLeads =true;

    // Variable for che Setting values for displaying Expandable ListView
    boolean accountsSet = true;
    boolean contactSet = true;
    boolean taskSet = true;
    boolean caseSet = true;
    boolean opportunitySet = true;
    boolean meetingSet = true;
    boolean leadSet = true;
    boolean callSet = true;

    SharedPreferences settingPrefs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_setting);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        checkBox_account = (CheckBox) findViewById(R.id.chb_account);
        checkBox_contact = (CheckBox) findViewById(R.id.chb_contacts);
        checkBox_tasks = (CheckBox) findViewById(R.id.chb_tasks);
        checkBox_opportunity = (CheckBox) findViewById(R.id.chb_opportunity);
        checkBox_meeting = (CheckBox) findViewById(R.id.chb_meetings);
        checkBox_cases = (CheckBox) findViewById(R.id.chb_cases);
        checkBox_call = (CheckBox) findViewById(R.id.chb_calls);
        checkBox_leads = (CheckBox) findViewById(R.id.chb_leads);

        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(this);

        checkBox_account.setOnClickListener(this);
        checkBox_contact.setOnClickListener(this);
        checkBox_tasks.setOnClickListener(this);
        checkBox_call.setOnClickListener(this);
        checkBox_opportunity.setOnClickListener(this);
        checkBox_meeting.setOnClickListener(this);
        checkBox_account.setOnClickListener(this);
        checkBox_cases.setOnClickListener(this);
        checkBox_leads.setOnClickListener(this);
        settingPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // get saved result from shared preference

        accountsSet = settingPrefs.getBoolean("isAccount", true);
        contactSet = settingPrefs.getBoolean("isContact", true);
        callSet = settingPrefs.getBoolean("isCall", true);
        caseSet = settingPrefs.getBoolean("isCases", true);
        meetingSet = settingPrefs.getBoolean("isMeeting", true);
        opportunitySet = settingPrefs.getBoolean("isOpportunity", true);
        leadSet = settingPrefs.getBoolean("isLeads", true);
        taskSet = settingPrefs.getBoolean("isTasks", true);

        if(meetingSet == true){
            checkBox_meeting.setChecked(true);
        }
        if(accountsSet == true){
            checkBox_account.setChecked(true);
        }
        if(contactSet == true){
            checkBox_contact.setChecked(true);
        }
        if(callSet == true){
            checkBox_call.setChecked(true);
        }
        if(caseSet == true){
            checkBox_cases.setChecked(true);
        }
        if(opportunitySet == true){
            checkBox_opportunity.setChecked(true);
        }
        if(leadSet == true){
            checkBox_leads.setChecked(true);
        }
        if(taskSet == true){
            checkBox_tasks.setChecked(true);
        }

    }

    @Override
    public void onClick(View v) {

        settingPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        switch (v.getId()){
            case R.id.chb_account:
                if(checkBox_account.isChecked() == true ){
                    isAccount = true;
                    settingPrefs.edit().putBoolean("isAccount", true).commit();
                }else  if(checkBox_account.isChecked() == false ){
                    isAccount = false;
                    settingPrefs.edit().putBoolean("isAccount", false).commit();
                }
                break;
            case R.id.chb_contacts:
                if(checkBox_contact.isChecked() == true){
                    isContact = true;
                    settingPrefs.edit().putBoolean("isContact", true).commit();
                }else  if(checkBox_contact.isChecked() == false ){
                    isContact = false;
                    settingPrefs.edit().putBoolean("isContact", false).commit();
                }
                break;
            case R.id.chb_tasks:
                if(checkBox_tasks.isChecked() == true){
                    isTasks = true;
                    settingPrefs.edit().putBoolean("isTasks", true).commit();
                }else  if(checkBox_tasks.isChecked() == false ){
                    isTasks = false;
                    settingPrefs.edit().putBoolean("isTasks", false).commit();
                }
                break;
            case R.id.chb_opportunity:
                if(checkBox_opportunity.isChecked() == true){
                    isOpportunity = true;
                    settingPrefs.edit().putBoolean("isOpportunity", true).commit();
                }else  if(checkBox_opportunity.isChecked() == false ){
                    isOpportunity = false;
                    settingPrefs.edit().putBoolean("isOpportunity", false).commit();
                }
                break;
            case R.id.chb_meetings:
                if(checkBox_meeting.isChecked() == true){
                    isMeeting = true;
                    settingPrefs.edit().putBoolean("isMeeting", true).commit();
                }else  if(checkBox_meeting.isChecked() == false ){
                    isMeeting = false;
                    settingPrefs.edit().putBoolean("isMeeting", false).commit();
                }
                break;
            case R.id.chb_cases:
                if(checkBox_cases.isChecked() == true){
                    isCases = true;
                    settingPrefs.edit().putBoolean("isCases", true).commit();
                }else  if(checkBox_cases.isChecked() == false ){
                    isCases = false;
                    settingPrefs.edit().putBoolean("isCases", false).commit();
                }
                break;
            case R.id.chb_calls:
                if(checkBox_call.isChecked() == true){
                    isCall = true;
                    settingPrefs.edit().putBoolean("isCall", true).commit();
                }else  if(checkBox_call.isChecked() == false ){
                    isCall = false;
                    settingPrefs.edit().putBoolean("isCall", false).commit();
                }
                break;
            case R.id.chb_leads:
                if(checkBox_leads.isChecked() == true){
                    isLeads = true;
                    settingPrefs.edit().putBoolean("isLeads", true).commit();
                }else  if(checkBox_leads.isChecked() == false ){
                    isLeads = false;
                    settingPrefs.edit().putBoolean("isLeads", false).commit();
                }
                break;
            case R.id.done:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
