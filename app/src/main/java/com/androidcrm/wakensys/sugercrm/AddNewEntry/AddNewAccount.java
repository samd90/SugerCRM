package com.androidcrm.wakensys.sugercrm.AddNewEntry;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SESSION;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MODULE_NAME;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.NAME_VALUE_LIST;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.INPUT_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.JSON;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SET_ENTRY;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.METHOD;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RESPONSE_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.REST_DATA;

import com.androidcrm.wakensys.sugercrm.R;
import com.androidcrm.wakensys.sugercrm.fragment.EntriesDetailsController;
import com.androidcrm.wakensys.sugercrm.fragment.Fragment_Entries;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Sameera on 2/16/2015.
 */
public class AddNewAccount extends Fragment implements View.OnClickListener {

    public static final String TAG = AddNewAccount.class.getSimpleName();

    public static AddNewAccount newInstance() {
        return new AddNewAccount();
    }

    private String sessionId, restUrl, name, id,phone_fax, billing_address_street, billing_address_street_2, billing_address_street_3, billing_address_street_4, billing_address_city, billing_address_state, billing_address_postalcode,
            phone_office, website, email1, response, billing_address_country;
    private EditText txt_name, txt_web, txt_oPhone, txt_fax, txt_bStreet, txt_bCity, txt_bState, txt_bPostal, txt_bCountry, txt_email;
    private Button btn_save, btn_cancel;
    private ProgressDialog dialog;
    private boolean fromEdit = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_new_account, container, false);
        txt_name = (EditText) rootView.findViewById(R.id.txt_name);
        if(txt_name.getText().toString().length() == 0) {
            txt_name.setError("Name is required!");
        }
        txt_web = (EditText) rootView.findViewById(R.id.txt_web);
        txt_oPhone = (EditText) rootView.findViewById(R.id.txt_o_Phone);
        txt_fax = (EditText) rootView.findViewById(R.id.txt_fax);
        txt_bStreet = (EditText) rootView.findViewById(R.id.txt_b_Street);
        txt_bCity = (EditText) rootView.findViewById(R.id.txt_b_city);
        txt_bState = (EditText) rootView.findViewById(R.id.txt_b_state);
        txt_bPostal = (EditText) rootView.findViewById(R.id.txt_b_postal);
        txt_bCountry = (EditText) rootView.findViewById(R.id.txt_b_country);
        txt_email = (EditText) rootView.findViewById(R.id.txt_email);
        btn_save = (Button) rootView.findViewById(R.id.btn_save);
        btn_cancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        // Get sessionId and RestUrl from AddNewItem_selectMenu class
        Bundle b = getArguments();
        sessionId = b.getString("sessionId");
        restUrl = b.getString("restUrl");
        response = b.getString("accountDetails");
        // Check where request came from
        fromEdit = b.getBoolean("from_edit");   // if fromEdit Boolean true request came from Edit Button
        // Check what want to fill EditText
        if(fromEdit == true){
            try {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please wait..");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

            JSONObject name_value_list = new JSONObject(response);
            Log.d("jsonObj", "" + name_value_list.toString());

           JSONObject name_ = name_value_list.getJSONObject("name");
            name = name_.getString("value");

            JSONObject phoneFax = name_value_list.getJSONObject("phone_fax");
            phone_fax = phoneFax.getString("value");
            Log.d("phone_fax", phone_fax);

            JSONObject billingAddressStreet = name_value_list
                    .getJSONObject("billing_address_street");
             billing_address_street = billingAddressStreet.getString("value");

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

            JSONObject billingAddressState = name_value_list
                    .getJSONObject("billing_address_state");
            billing_address_state = phoneFax.getString("value");

            JSONObject billingAddressPostalcode = name_value_list
                    .getJSONObject("billing_address_postalcode");
            billing_address_postalcode = billingAddressPostalcode
                    .getString("value");

            JSONObject billingAddressCountry = name_value_list
                    .getJSONObject("billing_address_country");
            billing_address_country = billingAddressCountry
                    .getString("value");

            JSONObject phoneOffice = name_value_list
                    .getJSONObject("phone_office");
            phone_office = phoneOffice.getString("value");

            JSONObject website_ = name_value_list.getJSONObject("website");
            website = website_.getString("value");

            JSONObject email1_ = name_value_list.getJSONObject("email1");
            email1 = email1_.getString("value");

            JSONObject id_ = name_value_list.getJSONObject("id");
            id = id_.getString("value");

            txt_name.setText(name);
            txt_oPhone.setText(phone_office);
            txt_fax.setText(phone_fax);
            txt_email.setText(email1);
            txt_web.setText(website);
            txt_bStreet.setText(billing_address_street + ", " + billing_address_street2 + ", " + billing_address_street3 + ", " + billing_address_street4);
            txt_bCity.setText(billing_address_city);
            txt_bState.setText(billing_address_state);
            txt_bPostal.setText(billing_address_postalcode);
            txt_bCountry.setText(billing_address_country);

            dialog.dismiss();

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if(fromEdit == true) {
                   new UpdateEntryAccount().execute(sessionId, restUrl);
                }else {
                    if (txt_name.getText().toString().equals("") || txt_name.getText().toString().equals(null)) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error !")
                                .setMessage("Please Enter the Details")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();

                    } else {
                        // Execute AddNewEntryAccount class
                        new AddNewEntryAccount().execute(sessionId, restUrl);
                    }
                }
                break;
            case R.id.btn_cancel:
                AddNewItem_SelectMenu fragment = new AddNewItem_SelectMenu();
                Bundle b = new Bundle();
                b.putString("restUrl", restUrl);
                b.putString("sessionId", sessionId);
                fragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                break;
            default:
                break;

        }
    }

    class UpdateEntryAccount extends AsyncTask<String, Void, Boolean>{
        private Map<String, String> nameValueList;
        private String name = txt_name.getText().toString();
        private String website = txt_web.getText().toString();
        private String officePhone = txt_oPhone.getText().toString();
        private String fax = txt_fax.getText().toString();
        private String billingStreet = txt_bStreet.getText().toString();
        private String billingCity = txt_bCity.getText().toString();
        private String billingState = txt_bState.getText().toString();
        private String billingPostal = txt_bPostal.getText().toString();
        private String billingCountry = txt_bCountry.getText().toString();
        private String email = txt_email.getText().toString();

        private JSONObject nameValue = new JSONObject();
        private JSONObject websiteValue = new JSONObject();
        private JSONObject officePhoneValue = new JSONObject();
        private JSONObject billingStreetValue = new JSONObject();
        private JSONObject faxValue = new JSONObject();
        private JSONObject billingCityValue = new JSONObject();
        private JSONObject billingStateValue = new JSONObject();
        private JSONObject billingPostalValue = new JSONObject();
        private JSONObject billingCountryValue = new JSONObject();
        private JSONObject emailValue = new JSONObject();
        private JSONObject idValue = new JSONObject();

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please wait..");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String sessionId = params[0];
            String restUrl = params[1];
            boolean successful = false;
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, "Accounts");

            try {
                JSONArray nameValueArray = new JSONArray();
                idValue.put("name", "id");
                idValue.put("value", id);
                nameValueArray.put(idValue);
                nameValue.put("name", "name");
                nameValue.put("value", name);
                nameValueArray.put(nameValue);
                websiteValue.put("name", "website");
                websiteValue.put("value", website);
                nameValueArray.put(websiteValue);
                officePhoneValue.put("name", "phone_office");
                officePhoneValue.put("value", officePhone);
                nameValueArray.put(officePhoneValue);
                billingStreetValue.put("name", "billing_address_street");
                billingStreetValue.put("value", billingStreet);
                nameValueArray.put(billingStreetValue);
                faxValue.put("name", "phone_fax");
                faxValue.put("value", fax);
                nameValueArray.put(faxValue);
                billingCityValue.put("name", "billing_address_city");
                billingCityValue.put("value", billingCity);
                nameValueArray.put(billingCityValue);
                billingStateValue.put("name", "billing_address_state");
                billingStateValue.put("value", billingState);
                nameValueArray.put(billingStateValue);
                billingPostalValue.put("name", "billing_address_postalcode");
                billingPostalValue.put("value", billingPostal);
                nameValueArray.put(billingPostalValue);
                billingCountryValue.put("name", "billing_address_country");
                billingCountryValue.put("value", billingCountry);
                nameValueArray.put(billingCountryValue);
                emailValue.put("name", "email1");
                emailValue.put("value", email);
                nameValueArray.put(emailValue);

                data.put(NAME_VALUE_LIST, nameValueArray);

                String restData = org.json.simple.JSONValue.toJSONString(data);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost req = new HttpPost(restUrl);
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD, SET_ENTRY));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, restData));
                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                Log.e(TAG, nameValuePairs.toString());
                // Send POST request
                httpClient.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = httpClient.execute(req);
                if (res.getEntity() == null) {
                    Log.e(TAG, "FAILED TO CONNECT!");
                    successful = true;
                }
                String response = EntityUtils.toString(res.getEntity()).toString();
                JSONObject jsonResponse = new JSONObject(response);
                Log.i(TAG, "setEntry response : " + response);

            } catch (IOException ioe) {
                ioe.printStackTrace();
                successful = true;

            } catch (JSONException jsone) {
                jsone.printStackTrace();
                successful = true;

            }
            return successful;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();
            if(result != true){
                new AlertDialog.Builder(getActivity())
                        .setTitle("Alert")
                        .setMessage("Record Successfully Added.")
                        .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Fragment_Entries fragment = new Fragment_Entries();
                                Bundle b = new Bundle();
                                b.putString("restUrl", restUrl);
                                b.putString("sessionId", sessionId);
                                b.putString("moduleName", "Accounts");
                                fragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        }
    }

    class AddNewEntryAccount extends AsyncTask<String, Void, Boolean> {
        private String url, sessionId, moduleName;
        private Map<String, String> nameValueList;
        private String name = txt_name.getText().toString();
        private String website = txt_web.getText().toString();
        private String officePhone = txt_oPhone.getText().toString();
        private String fax = txt_fax.getText().toString();
        private String billingStreet = txt_bStreet.getText().toString();
        private String billingCity = txt_bCity.getText().toString();
        private String billingState = txt_bState.getText().toString();
        private String billingPostal = txt_bPostal.getText().toString();
        private String billingCountry = txt_bCountry.getText().toString();
        private String email = txt_email.getText().toString();

        private JSONObject nameValue = new JSONObject();
        private JSONObject websiteValue = new JSONObject();
        private JSONObject officePhoneValue = new JSONObject();
        private JSONObject billingStreetValue = new JSONObject();
        private JSONObject faxValue = new JSONObject();
        private JSONObject billingCityValue = new JSONObject();
        private JSONObject billingStateValue = new JSONObject();
        private JSONObject billingPostalValue = new JSONObject();
        private JSONObject billingCountryValue = new JSONObject();
        private JSONObject emailValue = new JSONObject();
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String sessionId = params[0];
            String restUrl = params[1];
            boolean successful = false;
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, "Accounts");

            try {
                JSONArray nameValueArray = new JSONArray();
                nameValue.put("name", "name");
                nameValue.put("value", name);
                nameValueArray.put(nameValue);
                websiteValue.put("name", "website");
                websiteValue.put("value", website);
                nameValueArray.put(websiteValue);
                officePhoneValue.put("name", "phone_office");
                officePhoneValue.put("value", officePhone);
                nameValueArray.put(officePhoneValue);
                billingStreetValue.put("name", "billing_address_street");
                billingStreetValue.put("value", billingStreet);
                nameValueArray.put(billingStreetValue);
                faxValue.put("name", "phone_fax");
                faxValue.put("value", fax);
                nameValueArray.put(faxValue);
                billingCityValue.put("name", "billing_address_city");
                billingCityValue.put("value", billingCity);
                nameValueArray.put(billingCityValue);
                billingStateValue.put("name", "billing_address_state");
                billingStateValue.put("value", billingState);
                nameValueArray.put(billingStateValue);
                billingPostalValue.put("name", "billing_address_postalcode");
                billingPostalValue.put("value", billingPostal);
                nameValueArray.put(billingPostalValue);
                billingCountryValue.put("name", "billing_address_country");
                billingCountryValue.put("value", billingCountry);
                nameValueArray.put(billingCountryValue);
                emailValue.put("name", "email1");
                emailValue.put("value", email);
                nameValueArray.put(emailValue);

                data.put(NAME_VALUE_LIST, nameValueArray);

                String restData = org.json.simple.JSONValue.toJSONString(data);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost req = new HttpPost(restUrl);
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD, SET_ENTRY));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, restData));
                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                Log.e(TAG, nameValuePairs.toString());
                // Send POST request
                httpClient.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = httpClient.execute(req);
                if (res.getEntity() == null) {
                    Log.e(TAG, "FAILED TO CONNECT!");
                    successful = true;
                }
                String response = EntityUtils.toString(res.getEntity()).toString();
                JSONObject jsonResponse = new JSONObject(response);
                Log.i(TAG, "setEntry response : " + response);

            } catch (IOException ioe) {
                ioe.printStackTrace();
                successful = true;

            } catch (JSONException jsone) {
                jsone.printStackTrace();
                successful = true;

            }
            return successful;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            pDialog.dismiss();
            if(result != true){
                new AlertDialog.Builder(getActivity())
                        .setTitle("Alert")
                        .setMessage("Record Successfully Added.")
                        .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Fragment_Entries fragment = new Fragment_Entries();
                                Bundle b = new Bundle();
                                b.putString("restUrl", restUrl);
                                b.putString("sessionId", sessionId);
                                b.putString("moduleName", "Accounts");
                                fragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }

        }
    }
}
