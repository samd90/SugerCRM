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

public class EntryDetails_calls extends ActionBarActivity {

	// Variables Layout Items
	TextView subject, direc, stus, sDate, duration, descrip, assg_to,
			createdBy;

	// JsonObject
	private String name;
	private String date_start;
	private String direction;
	private String status;
	private String duration_hours;
	private String duration_minutes;
	private String parent_type;
	private String parent_name;
	private String assigned_user_name;
	private String created_by_name;
	private String call_time;
	
	//progress dialog
	private Dialog dialog;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entry_details_calls_layout);
		
        try{
		// Assign Layout Items
		subject = (TextView) findViewById(R.id.text_subject);
		direc = (TextView) findViewById(R.id.text_derection);
		stus = (TextView) findViewById(R.id.text_status);
		sDate = (TextView) findViewById(R.id.text_s_date);
		duration = (TextView) findViewById(R.id.text_durat);
		assg_to = (TextView) findViewById(R.id.text_assign);
		createdBy = (TextView) findViewById(R.id.text_cret_by);
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);
		// Get Bundle infromation from parent Class
		Intent ij=getIntent();
		Bundle b = ij.getExtras();
		String s = b.getString("callDetails");
		//Log.d("Call_Fragment", "" + getArguments().getString("callDetails"));

		// Get String Values from JSONObjests
		
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_progress);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.show();

			JSONObject name_value_list = new JSONObject(s);
			Log.d("jsonObj", "" + name_value_list.toString());

			JSONObject name_ = name_value_list.getJSONObject("name");
			name = name_.getString("value");
			Log.d("name", name);

			JSONObject date_start_ = name_value_list
					.getJSONObject("date_start");
			date_start = date_start_.getString("value");
			Log.d("date_start", date_start);

			JSONObject direction_ = name_value_list.getJSONObject("direction");
			direction = direction_.getString("value");
			Log.d("direction", direction);

			JSONObject status_ = name_value_list.getJSONObject("status");
			status = status_.getString("value");
			Log.d("status", status);

			JSONObject duration_hours_ = name_value_list
					.getJSONObject("duration_hours");
			duration_hours = duration_hours_.getString("value");
			JSONObject duration_minutes_ = name_value_list
					.getJSONObject("duration_minutes");
			duration_minutes = duration_minutes_.getString("value");

			call_time = duration_hours + ":" + duration_minutes;

			JSONObject parent_type_ = name_value_list
					.getJSONObject("parent_type");
			parent_type = parent_type_.getString("value");
			Log.d("parent_type", parent_type);

			JSONObject parent_name_ = name_value_list
					.getJSONObject("parent_name");
			parent_name = parent_name_.getString("value");
			Log.d("parent_name", parent_name);

			JSONObject assigned_user_name_ = name_value_list
					.getJSONObject("assigned_user_name");
			assigned_user_name = assigned_user_name_.getString("value");
			Log.d("assigned_user_name", assigned_user_name);

			JSONObject created_by_name_ = name_value_list
					.getJSONObject("created_by_name");
			created_by_name = created_by_name_.getString("value");
			Log.d("created_by_name", created_by_name);

			// Put String values to Textviews
			subject.setText(name);
			direc.setText(direction);
			stus.setText(status);
			sDate.setText(date_start);
			duration.setText(call_time);
			assg_to.setText(assigned_user_name);
			createdBy.setText(created_by_name);

            dialog.dismiss();

		} catch (JSONException e) {
			Log.e("My App", "Could not parse JSON");
			e.printStackTrace();
		}	catch (Exception e){
            e.printStackTrace();
        }

	}

	
}
