package com.androidcrm.wakensys.sugercrm.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.androidcrm.wakensys.sugercrm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class EntryDetails_tasks extends ActionBarActivity {

    private TextView subject, taskStatus, start_date, due_date, assign_to,
            taskPriority;

    private String name;
    private String priority;
    private String status;
    private String date_start;
    private String date_due;
    private String assigned_user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_entry_task_details);
        try {
            subject = (TextView) findViewById(R.id.text_subje);
            taskStatus = (TextView) findViewById(R.id.text_status);
            start_date = (TextView) findViewById(R.id.text_start_Date);
            due_date = (TextView) findViewById(R.id.text_due_date);
            assign_to = (TextView) findViewById(R.id.text_assign_to);
            taskPriority = (TextView) findViewById(R.id.text_priority);
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);

            Intent ij = getIntent();
            Bundle b = ij.getExtras();
            String s = b.getString("taskDetails");

            setSupportActionBar(toolbar);
            JSONObject name_value_list = new JSONObject(s);
            Log.d("jsonObj", "" + name_value_list.toString());

            JSONObject name_ = name_value_list.getJSONObject("name");
            name = name_.getString("value");
            Log.d("name", name);

            JSONObject priority_ = name_value_list.getJSONObject("priority");
            priority = priority_.getString("value");
            Log.d("priority", priority);

            JSONObject status_ = name_value_list.getJSONObject("status");
            status = status_.getString("value");
            Log.d("status", status);

            JSONObject date_start_ = name_value_list
                    .getJSONObject("date_start");
            date_start = date_start_.getString("value");
            Log.d("date_start", date_start);

            JSONObject date_due_ = name_value_list.getJSONObject("date_due");
            date_due = date_due_.getString("value");
            Log.d("date_due", date_due);

            JSONObject assigned_user_name_ = name_value_list
                    .getJSONObject("assigned_user_name");
            assigned_user_name = assigned_user_name_.getString("value");
            Log.d("assigned_user_name", assigned_user_name);

            subject.setText(name);
            taskStatus.setText(status);
            start_date.setText(date_start);
            due_date.setText(date_due);
            taskPriority.setText(priority);
            assign_to.setText(assigned_user_name);

        } catch (JSONException e) {
            Log.e("My App", "Could not parse JSON");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
