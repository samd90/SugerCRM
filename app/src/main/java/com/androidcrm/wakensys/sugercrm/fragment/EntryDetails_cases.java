package com.androidcrm.wakensys.sugercrm.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.androidcrm.wakensys.sugercrm.R;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;

import android.widget.TextView;

public class EntryDetails_cases extends ActionBarActivity {

    private TextView accountName, number, assign_to, caseStatus, caseDescription, caseResolution, crtd_by, date_crtd;

    private String account_name;
    private String case_number;
    private String assigned_user_name;
    private String status;
    private String description;
    private String resolution;
    private String created_by_name;
    private String date_entered;

    //progress dialog
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_details_cases_layout);
        try {
            accountName = (TextView) findViewById(R.id.text_subje);
            number = (TextView) findViewById(R.id.text_num);
            assign_to = (TextView) findViewById(R.id.text_ass);
            caseStatus = (TextView) findViewById(R.id.text_sttus);
            caseDescription = (TextView) findViewById(R.id.text_des);
            caseResolution = (TextView) findViewById(R.id.text_res);
            crtd_by = (TextView) findViewById(R.id.text_crtd_by);
            date_crtd = (TextView) findViewById(R.id.text_date_crtd);

            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            Intent ij = getIntent();
            Bundle b = ij.getExtras();
            String s = b.getString("caseDetails");
            setSupportActionBar(toolbar);
            //Log.d("Case_Fragment_deatils",""+getArguments().getString("caseDetails"));

            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_progress);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

            JSONObject name_value_list = new JSONObject(s);
            Log.d("jsonObj", "" + name_value_list.toString());

            JSONObject account_name_ = name_value_list.getJSONObject("account_name");
            account_name = account_name_.getString("value");
            Log.d("account_name", account_name);

            JSONObject case_number_ = name_value_list.getJSONObject("case_number");
            case_number = case_number_.getString("value");
            Log.d("case_number", case_number);

            JSONObject assigned_user_name_ = name_value_list.getJSONObject("assigned_user_name");
            assigned_user_name = assigned_user_name_.getString("value");
            Log.d("assigned_user_name", assigned_user_name);

            JSONObject status_ = name_value_list.getJSONObject("status");
            status = status_.getString("value");
            Log.d("status", status);

            JSONObject description_ = name_value_list.getJSONObject("description");
            description = description_.getString("value");
            Log.d("description", description);

            JSONObject resolution_ = name_value_list.getJSONObject("resolution");
            resolution = resolution_.getString("value");
            Log.d("resolution", resolution);

            JSONObject created_by_name_ = name_value_list.getJSONObject("created_by_name");
            created_by_name = created_by_name_.getString("value");
            Log.d("created_by_name", created_by_name);

            JSONObject date_entered_ = name_value_list.getJSONObject("date_entered");
            date_entered = date_entered_.getString("value");
            Log.d("date_entered", date_entered);

            dialog.dismiss();

            accountName.setText(account_name);
            number.setText(case_number);
            assign_to.setText(assigned_user_name);
            caseStatus.setText(status);
            caseDescription.setText(description);
            caseResolution.setText(resolution);
            crtd_by.setText(created_by_name);
            date_crtd.setText(date_entered);

        } catch (JSONException e) {
            Log.e("My App", "Could not parse JSON");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
