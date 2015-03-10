package com.androidcrm.wakensys.sugercrm.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.androidcrm.wakensys.sugercrm.R;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;

import android.widget.TextView;

public class EntryDetails_accounts extends ActionBarActivity {

	TextView name_txt, officePhone, fax, email, webSite, billingAddress,
			assg_to, industry_txt, type;

	private String assigned_user_name;
	private String modified_by_name;
	private String created_by_name;
	private String name;
	private String date_entered;
	private String date_modified;
	private String account_type;
	private String industry;
	private String annual_revenue;
	private String phone_fax;
	private String billing_address_street;
	private String billing_address_street_2;
	private String billing_address_street_3;
	private String billing_address_street_4;
	private String billing_address_city;
	private String billing_address_state;
	private String billing_address_postalcode;
	private String billing_address_country;
	private String rating;
	private String phone_office;
	private String phone_alternate;
	private String website;
	private String ownership;
	private String employees;
	private String shipping_address_street;
	private String shipping_address_street_2;
	private String shipping_address_street_3;
	private String shipping_address_street_4;
	private String shipping_address_city;
	private String shipping_address_state;
	private String shipping_address_postalcode;
	private String shipping_address_country;
	private String email1;
	private String email_addresses_non_primary;

	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entry_details_accounts_layout);
		try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);
            name_txt = (TextView) findViewById(R.id.text_subject);
            fax = (TextView) findViewById(R.id.text_fax);
            email = (TextView) findViewById(R.id.text_email);
            officePhone = (TextView) findViewById(R.id.text_o_phone);
            webSite = (TextView) findViewById(R.id.text_web);
            billingAddress = (TextView) findViewById(R.id.text_billing);
            assg_to = (TextView) findViewById(R.id.text_ass);
            industry_txt = (TextView) findViewById(R.id.text_industry);
            type = (TextView) findViewById(R.id.text_type);

            Intent ij = getIntent();
            Bundle b = ij.getExtras();
            String s = b.getString("accountDetails");

            dialog = new ProgressDialog(this);
            dialog.setMessage("Please wait..");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

			JSONObject name_value_list = new JSONObject(s);
			Log.d("jsonObj", "" + name_value_list.toString());

			JSONObject assignedUserName = name_value_list
					.getJSONObject("assigned_user_name");
			assigned_user_name = assignedUserName.getString("value");
			Log.d("assigned_user_name", assigned_user_name);

			JSONObject modifiedByName = name_value_list
					.getJSONObject("modified_by_name");
			modified_by_name = modifiedByName.getString("value");
			Log.d("modified_by_name", modified_by_name);

			JSONObject name_ = name_value_list.getJSONObject("name");
			name = name_.getString("value");
			Log.d("name", name);

			JSONObject createdByName = name_value_list
					.getJSONObject("created_by_name");
			created_by_name = createdByName.getString("value");
			Log.d("created_by_name", created_by_name);

			JSONObject dateEntered = name_value_list
					.getJSONObject("date_entered");
			date_entered = dateEntered.getString("value");
			Log.d("date_entered", date_entered);

			JSONObject accountType = name_value_list
					.getJSONObject("account_type");
			account_type = accountType.getString("value");
			Log.d("account_type", account_type);

			JSONObject industry_ = name_value_list.getJSONObject("industry");
			industry = industry_.getString("value");
			Log.d("industry", industry);

			JSONObject annualRevenue = name_value_list
					.getJSONObject("annual_revenue");
			annual_revenue = annualRevenue.getString("value");
			Log.d("annual_revenue", annual_revenue);

			JSONObject phoneFax = name_value_list.getJSONObject("phone_fax");
			phone_fax = phoneFax.getString("value");
			Log.d("phone_fax", phone_fax);

			JSONObject billingAddressStreet = name_value_list
					.getJSONObject("billing_address_street");
			// billing_address_street = billingAddressStreet.getString("value");
			// Log.d("billing_address_street", billing_address_street);

			JSONObject billing_address_street2 = name_value_list
					.getJSONObject("billing_address_street_2");
			billing_address_street_2 = billing_address_street2
					.getString("value");

			JSONObject billing_address_street3 = name_value_list
					.getJSONObject("billing_address_street_3");
			billing_address_street_3 = billing_address_street3
					.getString("value");

			JSONObject billing_address_street4 = name_value_list
					.getJSONObject("billing_address_street_4");
			billing_address_street_4 = billing_address_street4
					.getString("value");

			JSONObject billingAddressCity = name_value_list
					.getJSONObject("billing_address_city");
			billing_address_city = billingAddressCity.getString("value");
			Log.d("billing_address_city", billing_address_city);

			JSONObject billingAddressState = name_value_list
					.getJSONObject("billing_address_state");
			billing_address_state = phoneFax.getString("value");
			Log.d("billing_address_state", billing_address_state);

			JSONObject billingAddressPostalcode = name_value_list
					.getJSONObject("billing_address_postalcode");
			billing_address_postalcode = billingAddressPostalcode
					.getString("value");

			JSONObject billingAddressCountry = name_value_list
					.getJSONObject("billing_address_country");
            billing_address_country = billingAddressCountry
					.getString("value");
			Log.d("billing_address_country", "" + billing_address_country);

			String billing_address = billing_address_street + ", "
					+ billing_address_street_2 + ", "
					+ billing_address_street_3 + ", "
					+ billing_address_street_4 + ", " + billing_address_city
					+ ", " + billing_address_state + ", "
					+ billing_address_postalcode + ", "
					+ billing_address_postalcode;

			JSONObject phoneOffice = name_value_list
					.getJSONObject("phone_office");
			phone_office = phoneOffice.getString("value");
			Log.d("phone_office", phone_office);

			JSONObject phoneAlternate = name_value_list
					.getJSONObject("phone_alternate");
			phone_alternate = phoneAlternate.getString("value");
			Log.d("phone_alternate", phone_alternate);

			JSONObject website_ = name_value_list.getJSONObject("website");
			website = website_.getString("value");
			Log.d("website", website);

			JSONObject shippingAddressStreet = name_value_list
					.getJSONObject("shipping_address_street");
			shipping_address_street = shippingAddressStreet.getString("value");
			Log.d("shipping_address_street", shipping_address_street);

			JSONObject shippingAddressStreet_2 = name_value_list
					.getJSONObject("shipping_address_street_2");
			shipping_address_street_2 = shippingAddressStreet_2
					.getString("value");
			Log.d("shipping_address_street", shipping_address_street);

			JSONObject shippingAddressStreet_3 = name_value_list
					.getJSONObject("shipping_address_street_3");
			shipping_address_street_3 = shippingAddressStreet_3
					.getString("value");

			JSONObject shippingAddressStreet_4 = name_value_list
					.getJSONObject("shipping_address_street_4");
			shipping_address_street_4 = shippingAddressStreet_4
					.getString("value");

			JSONObject shipping_address_city_ = name_value_list
					.getJSONObject("shipping_address_city");
			shipping_address_city = shipping_address_city_.getString("value");
			Log.d("shipping_address_city", shipping_address_city);

			JSONObject shipping_address_state_ = name_value_list
					.getJSONObject("shipping_address_state");
			shipping_address_state = shipping_address_state_.getString("value");
			Log.d("shipping_address_state", shipping_address_state);

			JSONObject shipping_address_country_ = name_value_list
					.getJSONObject("shipping_address_country");
			shipping_address_country = shipping_address_country_
					.getString("value");

			JSONObject email1_ = name_value_list.getJSONObject("email1");
			email1 = email1_.getString("value");
			Log.d("email1", email1);

			JSONObject email_addresses_non_primary_ = name_value_list
					.getJSONObject("email_addresses_non_primary");
			email_addresses_non_primary = email_addresses_non_primary_
					.getString("value");

			name_txt.setText(name);
			officePhone.setText(phone_office);
			fax.setText(phone_fax);
			email.setText(email1);
			webSite.setText(website);
			billingAddress.setText(billing_address);
			assg_to.setText(assigned_user_name);
			industry_txt.setText(industry);
			type.setText(account_type);

            dialog.dismiss();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}catch (Exception e){
            e.printStackTrace();
        }

	}

}
