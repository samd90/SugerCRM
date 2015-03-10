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

public class EntryDetails_contacts extends ActionBarActivity {

	private TextView name_txt, officePhone, mobile, email, createdBy, assg_to, d_created, date_modif;
	
	//progress dialog
	private Dialog dialog;
	
	private String phone_work;
	private String phone_mobile;
	private String c_email;
	private String c_name;
	private String assigned_user_name;
	private String date_entered;
	private String date_modified;
	
	private String created_by_name;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entry_details_contacts_layout);
		try{
		name_txt = (TextView) findViewById(R.id.text_subject);
		mobile = (TextView) findViewById(R.id.text_mobile);
		email = (TextView) findViewById(R.id.text_email);
		officePhone = (TextView) findViewById(R.id.text_oPhone);
		createdBy = (TextView) findViewById(R.id.text_crtd_by);
		assg_to = (TextView) findViewById(R.id.text_assign);
		d_created = (TextView) findViewById(R.id.text_d_crtd);
		date_modif = (TextView) findViewById(R.id.text_date_m);

            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
		Intent ij=getIntent();
		Bundle b = ij.getExtras();
		String s = b.getString("contactDetails");
			//Log.d("Contact_Fragment_deatils",""+getArguments().getString("contactDetails"));
            setSupportActionBar(toolbar);
		dialog = new Dialog (this);
		dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
		dialog.setContentView (R.layout.dialog_progress);
		dialog.getWindow ().setBackgroundDrawableResource (android.R.color.transparent);
		dialog.show ();

			JSONObject name_value_list = new JSONObject(s);
			Log.d("jsonObj",""+name_value_list.toString());
			
			JSONObject c_name_ = name_value_list.getJSONObject("name");
			c_name = c_name_.getString("value");
			Log.d("Name", c_name);
			
			JSONObject phone_work_ = name_value_list.getJSONObject("phone_work");
			phone_work = phone_work_.getString("value");
			Log.d("phone_work",phone_work);
						
			JSONObject phone_mobile_ = name_value_list.getJSONObject("phone_mobile");
			phone_mobile = phone_mobile_.getString("value");
			Log.d("phone_mobile", phone_mobile);
			
			JSONObject email_ = name_value_list.getJSONObject("email");
			c_email = email_.getString("value");
			Log.d("email",c_email);
			
			JSONObject assigned_user_name_ = name_value_list
					.getJSONObject("assigned_user_name");
			assigned_user_name = assigned_user_name_.getString("value");
			Log.d("assigned_user_name", assigned_user_name);

			JSONObject createdByName = name_value_list
					.getJSONObject("created_by_name");
			created_by_name = createdByName.getString("value");
			Log.d("created_by_name", created_by_name);

			JSONObject dateEntered = name_value_list
					.getJSONObject("date_entered");
			date_entered = dateEntered.getString("value");
			Log.d("date_entered", date_entered);

			JSONObject date_modified_ = name_value_list
					.getJSONObject("date_modified");
			date_modified = date_modified_.getString("value");
			Log.d("date_modified", date_modified);

            // after getting Name Text App will crash
            name_txt.setText(c_name);
            officePhone.setText(phone_work);
            mobile.setText(phone_mobile);
            email.setText(c_email);
            createdBy.setText(created_by_name);
            d_created.setText(date_entered);
            assg_to.setText(assigned_user_name);
            date_modif.setText(date_modified);

            dialog.dismiss();

		} catch (JSONException e) {
			Log.e("My App", "Could not parse JSON");
			e.printStackTrace();
		} catch (Exception e) {
            e.printStackTrace();
        }
    }
}
