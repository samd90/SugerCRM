package com.androidcrm.wakensys.sugercrm.AddNewEntry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.androidcrm.wakensys.sugercrm.R;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.INPUT_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.JSON;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.METHOD;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MODULE_NAME;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.NAME_VALUE_LIST;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RESPONSE_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.REST_DATA;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SESSION;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SET_ENTRY;

/**
 * Created by Sameera on 2/16/2015.
 */
public class AddNewLeadsAndContactsAndTarget extends Fragment implements View.OnClickListener {
    public static final String TAG = AddNewLeadsAndContactsAndTarget.class.getSimpleName();

    public static AddNewLeadsAndContactsAndTarget newInstance(){
        return new AddNewLeadsAndContactsAndTarget();
    }

    private String sessionId, restUrl, name, id,phone_fax,moduleName,
            phone_office,email1, response;
    private EditText txt_fname, txt_lname, txt_title, txt_department, txt_account_name, txt_oPhone, txt_fax, txt_mobile,txt_bStreet,txt_bCity,txt_bState, txt_bPostal, txt_bCountry, txt_email;
    private Button btn_save, btn_cancel;
    private boolean fromEdit = false;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_new_lead_and_contact_target, container, false);
        // Get sessionId and RestUrl from AddNewItem_selectMenu class
        Bundle b = getArguments();
        sessionId = b.getString("sessionId");
        restUrl = b.getString("restUrl");
        moduleName = b.getString("module_name");
        response = b.getString("response");
        // Check where request came from
        fromEdit = b.getBoolean("from_edit");   // if fromEdit Boolean true request came from Edit Button

        txt_fname = (EditText) rootView.findViewById(R.id.txt_fname);
        if(txt_fname.getText().toString().length() == 0) {
            txt_fname.setError("Name is required!");
        }
        txt_lname = (EditText) rootView.findViewById(R.id.txt_lname);
        if(txt_lname.getText().toString().length() == 0) {
            txt_lname.setError("Name is required!");
        }
        txt_title = (EditText) rootView.findViewById(R.id.txt_title);
        txt_department = (EditText) rootView.findViewById(R.id.txt_department);
        txt_account_name = (EditText) rootView.findViewById(R.id.txt_o_Phone);
        txt_oPhone = (EditText) rootView.findViewById(R.id.txt_o_Phone);
        txt_fax = (EditText) rootView.findViewById(R.id.txt_fax);
        txt_mobile = (EditText) rootView.findViewById(R.id.txt_mobile);
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
        // Check what want to fill EditText
        if(fromEdit == true){
          try{
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please wait..");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show ();

            JSONObject name_value_list = new JSONObject(response);

            JSONObject first_name_ = name_value_list.getJSONObject("first_name");
             String first_name = first_name_.getString("value");

            JSONObject last_name_ = name_value_list.getJSONObject("last_name");
            String last_name = last_name_.getString("value");

            JSONObject title_ = name_value_list.getJSONObject("title");
             String title = title_.getString("value");

            JSONObject department_ = name_value_list.getJSONObject("department");
            String department = department_.getString("value");

            JSONObject id_ = name_value_list.getJSONObject("id");
            id = id_.getString("value");

            JSONObject account_name_ = name_value_list
                    .getJSONObject("account_name");
             String account_name = account_name_.getString("value");

            JSONObject phone_work_ = name_value_list
                    .getJSONObject("phone_work");
             String phone_work = phone_work_.getString("value");

            JSONObject phone_fax_ = name_value_list
                    .getJSONObject("phone_fax");
             String phone_fax = phone_fax_.getString("value");

            JSONObject phone_mobile_ = name_value_list
                    .getJSONObject("phone_mobile");
             String phone_mobile = phone_mobile_.getString("value");

              JSONObject primary_address_street_ = name_value_list
                      .getJSONObject("primary_address_street");
              String primary_address_street = primary_address_street_.getString("value");

              JSONObject primary_address_city_ = name_value_list
                      .getJSONObject("primary_address_city");
              String primary_address_city = primary_address_city_.getString("value");

              JSONObject primary_address_state_ = name_value_list
                      .getJSONObject("primary_address_state");
              String primary_address_state = primary_address_state_.getString("value");

              JSONObject primary_address_postalcode_ = name_value_list
                      .getJSONObject("primary_address_postalcode");
              String primary_address_postalcode = primary_address_postalcode_.getString("value");

              JSONObject primary_address_country_ = name_value_list
                      .getJSONObject("primary_address_country");
              String primary_address_country = primary_address_country_.getString("value");

              JSONObject email1_ = name_value_list
                      .getJSONObject("email1");
              String email1 = email1_.getString("value");

              txt_fname.setText(first_name);
              txt_lname.setText(last_name);
              txt_title.setText(title);
              txt_department.setText(department);
              txt_account_name.setText(account_name);
              txt_oPhone.setText(phone_work);
              txt_fax.setText(phone_fax);
              txt_mobile.setText(phone_mobile);
              txt_bStreet.setText(primary_address_street);
              txt_bCity.setText(primary_address_city);
              txt_bState.setText(primary_address_state);
              txt_bPostal.setText(primary_address_postalcode);
              txt_bCountry.setText(primary_address_country);
              txt_email.setText(email1);

            dialog.dismiss();

        } catch (JSONException e) {
            Log.e("My App", "Could not parse JSON");
            e.printStackTrace();
        } catch (Exception e) {
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
                new EditEntryLeadsCOntactsTarget().execute(sessionId, restUrl, moduleName, id);
            }else{
                    if ((txt_fname.getText().toString().equals("") || txt_fname.getText().toString().equals(null)) && (txt_lname.getText().toString().equals("") || txt_lname.getText().toString().equals(null))) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error !")
                                .setMessage("Please First Name and Last Name the Details")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();

                    } else {
                        // Execute AddNewEntryAccount class
                        new AddNewEntryAccount().execute(sessionId, restUrl, moduleName);
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
    class EditEntryLeadsCOntactsTarget extends AsyncTask<String, Void, Boolean>{
        private String url, sessionId, moduleName;
        private Map<String, String> nameValueList;
        private String first_name = txt_fname.getText().toString();
        private String last_name = txt_lname.getText().toString();
        private String title = txt_title.getText().toString();
        private String department = txt_department.getText().toString();
        private String account_name = txt_account_name.getText().toString();
        private String officePhone = txt_oPhone.getText().toString();
        private String fax = txt_fax.getText().toString();
        private String mobile = txt_mobile.getText().toString();
        private String billingStreet = txt_bStreet.getText().toString();
        private String billingCity = txt_bCity.getText().toString();
        private String billingState = txt_bState.getText().toString();
        private String billingPostal = txt_bPostal.getText().toString();
        private String billingCountry = txt_bCountry.getText().toString();
        private String email = txt_email.getText().toString();

        private JSONObject firstNameValue = new JSONObject();
        private JSONObject lastNameValue = new JSONObject();
        private JSONObject titleValue = new JSONObject();
        private JSONObject departmentValue = new JSONObject();
        private JSONObject accountNameValue = new JSONObject();
        private JSONObject officePhoneValue = new JSONObject();
        private JSONObject faxValue = new JSONObject();
        private JSONObject mobileValue = new JSONObject();
        private JSONObject billingStreetValue = new JSONObject();
        private JSONObject billingCityValue = new JSONObject();
        private JSONObject billingStateValue = new JSONObject();
        private JSONObject billingPostalValue = new JSONObject();
        private JSONObject billingCountryValue = new JSONObject();
        private JSONObject idValue = new JSONObject();
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
            moduleName = params[2];
            id = params[3];
            boolean successful = false;
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, moduleName);

            try {
                JSONArray nameValueArray = new JSONArray();
                idValue.put("name", "id");
                idValue.put("value", id);
                nameValueArray.put(idValue);
                firstNameValue.put("name", "first_name");
                firstNameValue.put("value", first_name);
                nameValueArray.put(firstNameValue);
                lastNameValue.put("name", "last_name");
                lastNameValue.put("value", last_name);
                nameValueArray.put(lastNameValue);
                titleValue.put("name", "title");
                titleValue.put("value", title);
                departmentValue.put("name", "department");
                departmentValue.put("value", department);
                nameValueArray.put(departmentValue);
                accountNameValue.put("name", "account_name");
                accountNameValue.put("value", account_name);
                nameValueArray.put(accountNameValue);
                officePhoneValue.put("name", "phone_work");
                officePhoneValue.put("value", officePhone);
                nameValueArray.put(officePhoneValue);
                officePhoneValue.put("name", "phone_work");
                officePhoneValue.put("value", officePhone);
                nameValueArray.put(officePhoneValue);
                mobileValue.put("name", "phone_mobile");
                mobileValue.put("value", mobile);
                nameValueArray.put(mobileValue);
                faxValue.put("name", "phone_fax");
                faxValue.put("value", fax);
                nameValueArray.put(faxValue);
                billingStreetValue.put("name", "primary_address_street");
                billingStreetValue.put("value", billingStreet);
                nameValueArray.put(billingStreetValue);
                billingCityValue.put("name", "primary_address_city");
                billingCityValue.put("value", billingCity);
                nameValueArray.put(billingCityValue);
                billingStateValue.put("name", "primary_address_state");
                billingStateValue.put("value", billingState);
                nameValueArray.put(billingStateValue);
                billingPostalValue.put("name", "primary_address_postalcode");
                billingPostalValue.put("value", billingPostal);
                nameValueArray.put(billingPostalValue);
                billingCountryValue.put("name", "primary_address_country");
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
                                b.putString("moduleName", moduleName);
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
        private String first_name = txt_fname.getText().toString();
        private String last_name = txt_lname.getText().toString();
        private String title = txt_title.getText().toString();
        private String department = txt_department.getText().toString();
        private String account_name = txt_account_name.getText().toString();
        private String officePhone = txt_oPhone.getText().toString();
        private String fax = txt_fax.getText().toString();
        private String mobile = txt_mobile.getText().toString();
        private String billingStreet = txt_bStreet.getText().toString();
        private String billingCity = txt_bCity.getText().toString();
        private String billingState = txt_bState.getText().toString();
        private String billingPostal = txt_bPostal.getText().toString();
        private String billingCountry = txt_bCountry.getText().toString();
        private String email = txt_email.getText().toString();

        private JSONObject firstNameValue = new JSONObject();
        private JSONObject lastNameValue = new JSONObject();
        private JSONObject titleValue = new JSONObject();
        private JSONObject departmentValue = new JSONObject();
        private JSONObject accountNameValue = new JSONObject();
        private JSONObject officePhoneValue = new JSONObject();
        private JSONObject faxValue = new JSONObject();
        private JSONObject mobileValue = new JSONObject();
        private JSONObject billingStreetValue = new JSONObject();
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
            moduleName = params[2];
            boolean successful = false;
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, moduleName);

            try {
                JSONArray nameValueArray = new JSONArray();
                firstNameValue.put("name", "first_name");
                firstNameValue.put("value", first_name);
                nameValueArray.put(firstNameValue);
                lastNameValue.put("name", "last_name");
                lastNameValue.put("value", last_name);
                nameValueArray.put(lastNameValue);
                titleValue.put("name", "title");
                titleValue.put("value", title);
                departmentValue.put("name", "department");
                departmentValue.put("value", department);
                nameValueArray.put(departmentValue);
                accountNameValue.put("name", "account_name");
                accountNameValue.put("value", account_name);
                nameValueArray.put(accountNameValue);
                officePhoneValue.put("name", "phone_work");
                officePhoneValue.put("value", officePhone);
                nameValueArray.put(officePhoneValue);
                officePhoneValue.put("name", "phone_work");
                officePhoneValue.put("value", officePhone);
                nameValueArray.put(officePhoneValue);
                mobileValue.put("name", "phone_mobile");
                mobileValue.put("value", mobile);
                nameValueArray.put(mobileValue);
                faxValue.put("name", "phone_fax");
                faxValue.put("value", fax);
                nameValueArray.put(faxValue);
                billingStreetValue.put("name", "primary_address_street");
                billingStreetValue.put("value", billingStreet);
                nameValueArray.put(billingStreetValue);
                billingCityValue.put("name", "primary_address_city");
                billingCityValue.put("value", billingCity);
                nameValueArray.put(billingCityValue);
                billingStateValue.put("name", "primary_address_state");
                billingStateValue.put("value", billingState);
                nameValueArray.put(billingStateValue);
                billingPostalValue.put("name", "primary_address_postalcode");
                billingPostalValue.put("value", billingPostal);
                nameValueArray.put(billingPostalValue);
                billingCountryValue.put("name", "primary_address_country");
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
                                b.putString("moduleName", moduleName);
                                fragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }

        }
    }
}
