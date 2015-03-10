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

public class EntryDetails_leads extends ActionBarActivity {

	private TextView lead_sorce, off_phone, fax, email_add, ac_name,
			lead_title, assign_to, crtd_by, date_crtd, date_modf;

	//progress dialog
	private Dialog dialog;
	
	private String lead_source;
	private String phone_work;
	private String phone_fax;
	private String email1;
	private String account_name;
	private String title;
	private String assigned_user_name;
	private String created_by_name;
	private String date_entered;
	private String date_modified;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.entry_details_leads_layout);
	
		lead_sorce = (TextView) findViewById(R.id.text_l_source);
		off_phone = (TextView) findViewById(R.id.text_o_phone);
		fax = (TextView) findViewById(R.id.text_fax);
		email_add = (TextView) findViewById(R.id.text_email);
		ac_name = (TextView) findViewById(R.id.text_a_name);
		lead_title = (TextView) findViewById(R.id.text_title);
		//assign_to = (TextView) findViewById(R.id.text_assign);
		crtd_by = (TextView) findViewById(R.id.text_cretd);
		date_crtd = (TextView) findViewById(R.id.text_d_crtd);
		date_modf = (TextView) findViewById(R.id.text_d_modi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
		Intent ij=getIntent();
		Bundle b = ij.getExtras();
		String s = b.getString("leadsDetails");
		// Log.d("Lead_Fragment_deatils",""+s);
        setSupportActionBar(toolbar);
		dialog = new Dialog (this);
		dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
		dialog.setContentView (R.layout.dialog_progress);
		dialog.getWindow ().setBackgroundDrawableResource (android.R.color.transparent);
		dialog.show ();
		
		try {
			JSONObject name_value_list = new JSONObject(s);
			//Log.d("jsonObj", "" + name_value_list.toString());

			/*
			 * JSONObject m_name_ = name_value_list.getJSONObject("name");
			 * acc_name = m_name_.getString("value"); Log.d("acc_name",
			 * acc_name);
			 */

			JSONObject lead_source_ = name_value_list
					.getJSONObject("lead_source");
			lead_source = lead_source_.getString("value");
			Log.d("lead_source", lead_source);

			JSONObject phone_work_ = name_value_list
					.getJSONObject("phone_work");
			phone_work = phone_work_.getString("value");
			Log.d("phone_work", phone_work);

			/* JSONObject phone_fax_ = name_value_list.getJSONObject("phone_fax");
			phone_fax = phone_fax_.getString("value");
			Log.d("phone_fax", phone_fax);
			*/

			JSONObject email1_ = name_value_list.getJSONObject("email1");
			email1 = email1_.getString("value");
			Log.d("email1", email1);

			JSONObject account_name_ = name_value_list
					.getJSONObject("account_name");
			account_name = account_name_.getString("value");
			Log.d("account_name", account_name);

			JSONObject title_ = name_value_list.getJSONObject("title");
			title = title_.getString("value");
			Log.d("title", title);

			JSONObject assigned_user_name_ = name_value_list
					.getJSONObject("assigned_user_name");
			assigned_user_name = assigned_user_name_.getString("value");
			Log.d("assigned_user_name", assigned_user_name);

			JSONObject created_by_name_ = name_value_list
					.getJSONObject("assigned_user_name");
			created_by_name = assigned_user_name_.getString("value");
			Log.d("created_by_name", created_by_name);

			JSONObject date_entered_ = name_value_list
					.getJSONObject("date_entered");
			date_entered = date_entered_.getString("value");
			Log.d("date_entered", date_entered);

			JSONObject date_modified_ = name_value_list
					.getJSONObject("date_modified");
			date_modified = date_modified_.getString("value");
			Log.d("date_modified", date_modified);

		} catch (JSONException e) {
			Log.e("My App", "Could not parse JSON");
			
		}

		dialog.dismiss();
		
		lead_sorce.setText(lead_source);
		off_phone.setText(phone_work);
		//fax.setText(phone_fax);
		email_add.setText(email1);
		ac_name.setText(account_name);
		lead_title.setText(title);
		//assign_to.setText(assigned_user_name);
		crtd_by.setText(created_by_name);
		date_crtd.setText(date_entered);
		date_modf.setText(date_modified);



	}
	
	

}
