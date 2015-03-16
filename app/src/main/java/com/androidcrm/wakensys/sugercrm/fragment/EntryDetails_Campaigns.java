package com.androidcrm.wakensys.sugercrm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.androidcrm.wakensys.sugercrm.R;

import org.json.JSONException;
import org.json.JSONObject;

public class EntryDetails_Campaigns extends ActionBarActivity {

    TextView subject, status, location, duration, assign, descrp, crtd_by, date_crtd, date_modi;

    private String descr;
    private String dur;
    private String locat;
    private String m_name;
    private String assigned_user_name;
    private String date_entered;
    private String date_modified;
    private String stuts;
    private String created_by_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_details_meetings_layout);
        try {
            subject = (TextView) findViewById(R.id.text_subject);
            status = (TextView) findViewById(R.id.text_stut);
            location = (TextView) findViewById(R.id.text_loct);
            duration = (TextView) findViewById(R.id.text_durat);
            assign = (TextView) findViewById(R.id.text_assgn);
            descrp = (TextView) findViewById(R.id.text_des);
            crtd_by = (TextView) findViewById(R.id.text_cretd);
            date_crtd = (TextView) findViewById(R.id.text_d_crtd);
            date_modi = (TextView) findViewById(R.id.text_d_modi);
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);
            Intent ij = getIntent();
            Bundle b = ij.getExtras();
            String s = b.getString("meetingDetails");

            JSONObject name_value_list = new JSONObject(s);
            Log.d("jsonObj", "" + name_value_list.toString());

            JSONObject m_name_ = name_value_list.getJSONObject("name");
            m_name = m_name_.getString("value");

            JSONObject location_ = name_value_list.getJSONObject("location");
            locat = location_.getString("value");

            JSONObject stuts_ = name_value_list.getJSONObject("status");
            stuts = stuts_.getString("value");

            JSONObject duration_hours_ = name_value_list.getJSONObject("duration_hours");
            String duration_hrs = duration_hours_.getString("value");

            JSONObject duration_minutes_ = name_value_list.getJSONObject("duration_minutes");
            String duration_mins = duration_minutes_.getString("value");

            dur = duration_hrs + ":" + duration_mins;

            JSONObject description = name_value_list.getJSONObject("description");
            descr = description.getString("value");

            JSONObject created_by_name_ = name_value_list.getJSONObject("created_by_name");
            created_by_name = created_by_name_.getString("value");

            JSONObject assigned_user_name_ = name_value_list
                    .getJSONObject("assigned_user_name");
            assigned_user_name = assigned_user_name_.getString("value");

            JSONObject dateEntered = name_value_list
                    .getJSONObject("date_entered");
            date_entered = dateEntered.getString("value");

            JSONObject date_modified_ = name_value_list
                    .getJSONObject("date_modified");
            date_modified = date_modified_.getString("value");

            subject.setText(m_name);
            status.setText(stuts);
            location.setText(locat);
            duration.setText(dur);
            assign.setText(assigned_user_name);
            descrp.setText(descr);
            crtd_by.setText(created_by_name);
            date_crtd.setText(date_entered);
            date_modi.setText(date_modified);

        } catch (JSONException e) {
            Log.e("My App", "Could not parse JSON");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
