package com.androidcrm.wakensys.sugercrm.fragment;

import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.DELETED;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.FAVORITES;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.GET_ENTRY_LIST;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.INPUT_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.JSON;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.ENTRY_LIST;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.LINK_NAME_TO_FIELDS_ARRAY;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MAX_RESULTS;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.METHOD;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MODULES;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MODULE_NAME;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.OFFSET;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.ORDER_BY;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.QUERY;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RESPONSE_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.REST_DATA;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SEARCH_BY_MODULE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SEARCH_STRING;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SELECT_FIELDS;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SESSION;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.androidcrm.wakensys.sugercrm.R;

import com.androidcrm.wakensys.sugercrm.AdapterClass.EntriesListAdapter;
import com.androidcrm.wakensys.sugercrm.data_sync.CrmDatabaseAdapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class Fragment_Entries extends Fragment implements
        OnItemClickListener, OnEditorActionListener {

    private ProgressDialog dialog;
    private EditText search;
    public static List<String> entry_names;
    public static List<String> entry_ids;
    public static List<String> entry_cnt_names;
    public static String account_name;
    private String query, orderBy, offset, maxResults, deleted, acc_name, entry_id;
    private String[] selectFields;
    private Map<String, List<String>> linkNameToFieldsArray;
    private String module_name;
    public static List<String> display_entry;
    // Button btnFav;
    private ListView mEntryList;
    private List<String> entry_co;
    private String sessionId = null;
    private String restUrl = null;
    private String module_label = null;

    private CrmDatabaseAdapter databseAdapter;

    public final static String TAG = Fragment_Entries.class
            .getSimpleName();

    public static Fragment_Entries newInstance() {
        return new Fragment_Entries();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_entries, container,
                false);
        try {
            mEntryList = (ListView) rootView.findViewById(R.id.lv_getEntry);
            mEntryList.setOnItemClickListener(this);
            entry_cnt_names = new ArrayList<String>();
            display_entry = new ArrayList<String>();
            entry_co = new ArrayList<String>();
            search = (EditText) rootView.findViewById(R.id.et_search);
            search.setOnEditorActionListener(this);
            entry_names = new ArrayList<String>();
            entry_ids = new ArrayList<String>();
            //Get Data from  MainActivity and EntriesDetailsController
            Bundle b = getArguments();
            //Get Rest Url
            String rest_url = b.getString("restUrl");
            sessionId = b.getString("sessionId");
            module_label = b.getString("module_name");
            //Get Rest Response from EntrisDetailsController
            String response_entryDetailsController = b.getString("message");
            //Get Boolean value From EntriesDetailsController for if condition
            //to check where from data coming from
            boolean comeFromEntriesDetailsController = false;
            comeFromEntriesDetailsController = b.getBoolean("relationship");
            String module_name_enries_details_cntrl = b.getString("linkFieldName");
            databseAdapter = new CrmDatabaseAdapter(getActivity());

            //Check whether Which rest call want to use get_relationship or get_entryList
            if (comeFromEntriesDetailsController == true) {
                //If data comming from EntriesDetailsController
                JSONObject responseJObj = new JSONObject(response_entryDetailsController);
                JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject obj = jArray.getJSONObject(i);
                    String id = obj.getString("id");
                    JSONObject name_value_list = obj
                            .getJSONObject("name_value_list");
                    JSONObject name_ = name_value_list.getJSONObject("name");
                    String name = name_.getString("value");
                    //Add module names and Id to array
                    entry_names.add(name);
                    entry_ids.add(id);
                }
                //Set Entry_names array to listview Adapter
                EntriesListAdapter entryListViewAdapter = new EntriesListAdapter(getActivity().getApplicationContext(), entry_names);
                mEntryList.setAdapter(entryListViewAdapter);

                module_name = module_name_enries_details_cntrl;
                module_name = module_name.substring(0, 1).toUpperCase() + module_name.substring(1).toLowerCase();
                restUrl = rest_url;

            } else {
                //If data come from MainActivity
                //Set module name and restUrl to Execute LoadingModuleLayout class
                module_name = module_label;
                restUrl = rest_url;
                new LoadingModuleLayout().execute(restUrl, sessionId, module_name);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    class LoadingModuleLayout extends AsyncTask<String, Void, Boolean> {

        private String sessionId = null;
        private String restUrl = null;
        private String response = null;
        private boolean successful = false;
        private String errorMessage = null;
        private String moduleLabel = null;

        @Override
        protected void onPreExecute() {
            try {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Please Wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {

            restUrl = params[0];
            sessionId = params[1];
            moduleLabel = params[2];

            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, module_name);
            data.put(QUERY, query != null ? query.toLowerCase() : "");
            data.put(ORDER_BY, orderBy != null ? orderBy : "");
            data.put(OFFSET, offset != null ? offset : "");
            data.put(
                    SELECT_FIELDS,
                    (selectFields != null && selectFields.length != 0) ? new JSONArray(
                            Arrays.asList(selectFields)) : "");
            try {
                JSONArray nameValueArray = new JSONArray();
                if (linkNameToFieldsArray != null
                        && linkNameToFieldsArray.size() != 0) {
                    for (Entry<String, List<String>> entry : linkNameToFieldsArray
                            .entrySet()) {
                        JSONObject nameValue = new JSONObject();
                        nameValue.put("name", entry.getKey());
                        nameValue.put("value", new JSONArray(entry.getValue()));
                        nameValueArray.put(nameValue);
                    }
                }
                data.put(LINK_NAME_TO_FIELDS_ARRAY, nameValueArray);
                data.put(MAX_RESULTS, maxResults != null ? maxResults : "");
                data.put(DELETED, deleted != null ? deleted : 0);

                String restData = org.json.simple.JSONValue.toJSONString(data);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost req = new HttpPost(restUrl);
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD,
                        GET_ENTRY_LIST));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, restData));
                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Send POST request
                httpClient.getParams().setBooleanParameter(
                        CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = httpClient.execute(req);
                if (res.getEntity() == null) {
                    Log.e(TAG, "FAILED TO CONNECT!");
                    successful = true;
                }
                response = EntityUtils.toString(res.getEntity());

                Log.e(TAG + " nameValuePairs", nameValuePairs.toString());

                Log.e(TAG + "response is " , response);

            } catch (JSONException jo) {
                successful = true;
                errorMessage = jo.toString();

            } catch (IOException ioe) {
                successful = true;
                errorMessage = ioe.toString();

            } catch (Exception e) {
                successful = true;
                errorMessage = e.toString();
            }
            return successful;

        }

        @Override
        protected void onPostExecute(Boolean result) {

            dialog.dismiss();

            if (result != true) {

                try {
                    JSONObject responceObject = new JSONObject(response);
                    JSONArray jsonArray = responceObject.getJSONArray(ENTRY_LIST);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        JSONObject nameValueList = obj.getJSONObject("name_value_list");
                        JSONObject name_value = nameValueList.getJSONObject("name");
                        String nameValue = name_value.getString("value");

                        entry_names.add(nameValue);
                    }
                    //Add entry_names Array to array adapter
                    EntriesListAdapter draweradapter = new EntriesListAdapter(getActivity().getApplicationContext(), entry_names);
                    mEntryList.setAdapter(draweradapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Store Entries in SQLite
                if (module_name.equals("Accounts")) {
                    try {

                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");

                            JSONObject name_value_list = obj
                                    .getJSONObject("name_value_list");

                            JSONObject name = name_value_list.getJSONObject("name");
                            acc_name = name.getString("value");
                            JSONObject email1_ = name_value_list.getJSONObject("email1");
                            String email1 = email1_.getString("value");
                            JSONObject parent_name_ = name_value_list.getJSONObject("parent_name");
                            String parent_name = parent_name_.getString("value");
                            JSONObject phone_office_ = name_value_list.getJSONObject("phone_office");
                            String phone_office = phone_office_.getString("value");
                            JSONObject phone_fax_ = name_value_list.getJSONObject("phone_fax");
                            String phone_fax = phone_fax_.getString("value");
                            JSONObject website_ = name_value_list.getJSONObject("website");
                            String website = website_.getString("value");
                            JSONObject employees_ = name_value_list.getJSONObject("employees");
                            String employees = employees_.getString("value");
                            JSONObject ticker_symbol_ = name_value_list.getJSONObject("ticker_symbol");
                            String ticker_symbol = ticker_symbol_.getString("value");
                            JSONObject annual_revenue_ = name_value_list.getJSONObject("annual_revenue");
                            String annual_revenue = annual_revenue_.getString("value");
                            JSONObject billing_address_street_ = name_value_list.getJSONObject("billing_address_street");
                            String billing_address_street = billing_address_street_.getString("value");
                            JSONObject billing_address_street2 = name_value_list.getJSONObject("billing_address_street_2");
                            String billing_address_street_2 = billing_address_street2.getString("value");
                            JSONObject billing_address_street3 = name_value_list.getJSONObject("billing_address_street_3");
                            String billing_address_street_3 = billing_address_street3.getString("value");
                            JSONObject billing_address_street4 = name_value_list.getJSONObject("billing_address_street_4");
                            String billing_address_street_4 = billing_address_street4.getString("value");
                            JSONObject billing_address_city_ = name_value_list.getJSONObject("billing_address_city");
                            String billing_address_city = billing_address_city_.getString("value");
                            JSONObject billing_address_state_ = name_value_list.getJSONObject("billing_address_state");
                            String billing_address_state = billing_address_state_.getString("value");
                            JSONObject billing_address_postalcode_ = name_value_list.getJSONObject("billing_address_postalcode");
                            String billing_address_postalcode = billing_address_postalcode_.getString("value");
                            JSONObject billing_address_country_ = name_value_list.getJSONObject("billing_address_country");
                            String billing_address_country = billing_address_country_.getString("value");
                            JSONObject shipping_address_street_ = name_value_list.getJSONObject("shipping_address_street");
                            String shipping_address_street = shipping_address_street_.getString("value");
                            JSONObject shipping_address_street_2_ = name_value_list.getJSONObject("shipping_address_street_2");
                            String shipping_address_street_2 = shipping_address_street_2_.getString("value");
                            JSONObject shipping_address_street_3_ = name_value_list.getJSONObject("shipping_address_street_3");
                            String shipping_address_street_3 = shipping_address_street_3_.getString("value");
                            JSONObject shipping_address_street_4_ = name_value_list.getJSONObject("shipping_address_street_4");
                            String shipping_address_street_4 = shipping_address_street_4_.getString("value");
                            JSONObject shipping_address_city_ = name_value_list.getJSONObject("shipping_address_city");
                            String shipping_address_city = shipping_address_city_.getString("value");
                            JSONObject shipping_address_state_ = name_value_list.getJSONObject("shipping_address_state");
                            String shipping_address_state = shipping_address_state_.getString("value");
                            JSONObject shipping_address_postalcode_ = name_value_list.getJSONObject("shipping_address_postalcode");
                            String shipping_address_postalcode = shipping_address_postalcode_.getString("value");
                            JSONObject shipping_address_country_ = name_value_list.getJSONObject("shipping_address_country");
                            String shipping_address_country = shipping_address_country_.getString("value");
                            JSONObject assigned_user_name_ = name_value_list.getJSONObject("assigned_user_name");
                            String assigned_user_name = assigned_user_name_.getString("value");
                            JSONObject created_by_name_ = name_value_list.getJSONObject("created_by_name");
                            String created_by_name = created_by_name_.getString("value");
                            JSONObject date_entered_ = name_value_list.getJSONObject("date_entered");
                            String date_entered = date_entered_.getString("value");
                            JSONObject date_modified_ = name_value_list.getJSONObject("date_modified");
                            String date_modified = date_modified_.getString("value");
                            JSONObject deleted_ = name_value_list.getJSONObject("deleted");
                            String deleted = deleted_.getString("value");


                            entry_ids.add(id);

                            long account_id = databseAdapter.InsertAccountTable(id, acc_name, email1, parent_name, phone_office, phone_fax, website, employees, ticker_symbol, annual_revenue,
                                    billing_address_street, billing_address_street_2, billing_address_street_3, billing_address_street_4, billing_address_city, billing_address_state, billing_address_postalcode,
                                    billing_address_country, shipping_address_street, shipping_address_street_2, shipping_address_street_3, shipping_address_street_4, shipping_address_city, shipping_address_state,
                                    shipping_address_postalcode, shipping_address_country, assigned_user_name, created_by_name, date_entered, date_modified, deleted);
                            if (account_id > 0) {
                                Log.d(TAG + " InsertAccountTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertAccountTable", "Error");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (module_name.equals("Calls")) {
                    try {
                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");

                            JSONObject name_value_list = obj
                                    .getJSONObject("name_value_list");

                            JSONObject name = name_value_list.getJSONObject("name");
                            acc_name = name.getString("value");
                            JSONObject contact_name_ = name_value_list.getJSONObject("contact_name");
                            String contact_name = contact_name_.getString("value");
                            JSONObject status_ = name_value_list.getJSONObject("status");
                            String status = status_.getString("value");
                            JSONObject date_start_ = name_value_list.getJSONObject("date_start");
                            String date_start = date_start_.getString("value");
                            JSONObject duration_hours_ = name_value_list.getJSONObject("duration_hours");
                            String duration_hours = duration_hours_.getString("value");
                            JSONObject duration_minutes_ = name_value_list.getJSONObject("duration_minutes");
                            String duration_minutes = duration_minutes_.getString("value");
                            JSONObject assigned_user_name_ = name_value_list.getJSONObject("assigned_user_name");
                            String assigned_user_name = assigned_user_name_.getString("value");
                            JSONObject description_ = name_value_list.getJSONObject("description");
                            String description = description_.getString("value");
                            JSONObject created_by_name_ = name_value_list.getJSONObject("created_by_name");
                            String created_by_name = created_by_name_.getString("value");
                            JSONObject date_entered_ = name_value_list.getJSONObject("date_entered");
                            String date_entered = date_entered_.getString("value");
                            JSONObject date_modified_ = name_value_list.getJSONObject("date_modified");
                            String date_modified = date_modified_.getString("value");
                            JSONObject deleted_ = name_value_list.getJSONObject("deleted");
                            String deleted = deleted_.getString("value");

                            entry_ids.add(id);

                            long calls_id = databseAdapter.InsertCallsTable(id, acc_name, contact_name, status, date_start, duration_hours, duration_minutes, assigned_user_name, description
                                    , created_by_name, date_entered, date_modified, deleted);
                            if (calls_id > 0) {
                                Log.d(TAG + " InsertCallsTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertCallsTable", "Error");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (module_name.equals("Cases")) {
                    try {
                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");

                            JSONObject name_value_list = obj
                                    .getJSONObject("name_value_list");

                            JSONObject name = name_value_list.getJSONObject("name");
                            acc_name = name.getString("value");
                            JSONObject account_name_ = name_value_list.getJSONObject("account_name");
                            String account_name = account_name_.getString("value");
                            JSONObject case_number_ = name_value_list.getJSONObject("case_number");
                            String case_number = case_number_.getString("value");
                            JSONObject priority_ = name_value_list.getJSONObject("priority");
                            String priority = priority_.getString("value");
                            JSONObject assigned_user_name_ = name_value_list.getJSONObject("assigned_user_name");
                            String assigned_user_name = assigned_user_name_.getString("value");
                            JSONObject status_ = name_value_list.getJSONObject("status");
                            String status = status_.getString("value");
                            JSONObject description_ = name_value_list.getJSONObject("description");
                            String description = description_.getString("value");
                            JSONObject resolution_ = name_value_list.getJSONObject("resolution");
                            String resolution = resolution_.getString("value");
                            JSONObject created_by_name_ = name_value_list.getJSONObject("created_by_name");
                            String created_by_name = created_by_name_.getString("value");
                            JSONObject date_entered_ = name_value_list.getJSONObject("date_entered");
                            String date_entered = date_entered_.getString("value");
                            JSONObject date_modified_ = name_value_list.getJSONObject("date_modified");
                            String date_modified = date_modified_.getString("value");
                            JSONObject deleted_ = name_value_list.getJSONObject("deleted");
                            String deleted = deleted_.getString("value");

                            entry_ids.add(id);

                            long cases_id = databseAdapter.InsertCallsTable(id, acc_name, account_name, case_number, priority, assigned_user_name, status, description, resolution
                                    , created_by_name, date_entered, date_modified, deleted);
                            if (cases_id > 0) {
                                Log.d(TAG + " InsertCallsTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertCallsTable", "Error");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (module_name.equals("Contacts")) {
                    try {
                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");

                            JSONObject name_value_list = obj
                                    .getJSONObject("name_value_list");

                            JSONObject name = name_value_list.getJSONObject("name");
                            acc_name = name.getString("value");
                            JSONObject first_name_ = name_value_list.getJSONObject("first_name");
                            String first_name = first_name_.getString("value");
                            JSONObject last_name_ = name_value_list.getJSONObject("last_name");
                            String last_name = last_name_.getString("value");
                            JSONObject account_name_ = name_value_list.getJSONObject("account_name");
                            String account_name = account_name_.getString("value");
                            JSONObject phone_mobile_ = name_value_list.getJSONObject("phone_mobile");
                            String phone_mobile = phone_mobile_.getString("value");
                            JSONObject phone_work_ = name_value_list.getJSONObject("phone_work");
                            String phone_work = phone_work_.getString("value");
                            JSONObject email1_ = name_value_list.getJSONObject("email1");
                            String email1 = email1_.getString("value");
                            JSONObject assigned_user_name_ = name_value_list.getJSONObject("assigned_user_name");
                            String assigned_user_name = assigned_user_name_.getString("value");
                            JSONObject created_by_ = name_value_list.getJSONObject("created_by");
                            String created_by = created_by_.getString("value");
                            JSONObject modified_by_name_ = name_value_list.getJSONObject("modified_by_name");
                            String modified_by_name = modified_by_name_.getString("value");
                            JSONObject created_by_name_ = name_value_list.getJSONObject("created_by_name");
                            String created_by_name = created_by_name_.getString("value");
                            JSONObject date_entered_ = name_value_list.getJSONObject("date_entered");
                            String date_entered = date_entered_.getString("value");
                            JSONObject date_modified_ = name_value_list.getJSONObject("date_modified");
                            String date_modified = date_modified_.getString("value");
                            JSONObject deleted_ = name_value_list.getJSONObject("deleted");
                            String deleted = deleted_.getString("value");

                            entry_ids.add(id);

                            long contact_id = databseAdapter.InsertContactsTable(id, first_name, last_name, account_name, phone_mobile, phone_work, email1, assigned_user_name, created_by,
                                    modified_by_name, created_by_name, date_entered, date_modified, deleted);
                            if (contact_id > 0) {
                                Log.d(TAG + " InsertContactsTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertContactsTable", "Error");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (module_name.equals("Leads")) {
                    try {
                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");

                            JSONObject name_value_list = obj
                                    .getJSONObject("name_value_list");

                            JSONObject name = name_value_list.getJSONObject("name");
                            acc_name = name.getString("value");
                            JSONObject first_name_ = name_value_list.getJSONObject("first_name");
                            String first_name = first_name_.getString("value");
                            JSONObject last_name_ = name_value_list.getJSONObject("last_name");
                            String last_name = last_name_.getString("value");
                            JSONObject lead_source_ = name_value_list.getJSONObject("lead_source");
                            String lead_source = lead_source_.getString("value");
                            JSONObject email1_ = name_value_list.getJSONObject("email1");
                            String email1 = email1_.getString("value");
                            JSONObject phone_work_ = name_value_list.getJSONObject("phone_work");
                            String phone_work = phone_work_.getString("value");
                            JSONObject phone_fax_ = name_value_list.getJSONObject("phone_fax");
                            String phone_fax = phone_fax_.getString("value");
                            JSONObject account_name_ = name_value_list.getJSONObject("account_name");
                            String account_name = account_name_.getString("value");
                            JSONObject title_ = name_value_list.getJSONObject("title");
                            String title = title_.getString("value");
                            JSONObject assigned_user_name_ = name_value_list.getJSONObject("assigned_user_name");
                            String assigned_user_name = assigned_user_name_.getString("value");
                            JSONObject created_by_name_ = name_value_list.getJSONObject("created_by_name");
                            String created_by_name = created_by_name_.getString("value");
                            JSONObject date_entered_ = name_value_list.getJSONObject("date_entered");
                            String date_entered = date_entered_.getString("value");
                            JSONObject date_modified_ = name_value_list.getJSONObject("date_modified");
                            String date_modified = date_modified_.getString("value");
                            JSONObject deleted_ = name_value_list.getJSONObject("deleted");
                            String deleted = deleted_.getString("value");

                            entry_ids.add(id);

                            long lead_id = databseAdapter.InsertLeadsTable(id, first_name, last_name, lead_source, email1, phone_work, phone_fax, account_name, title,
                                    assigned_user_name, created_by_name, date_entered, date_modified, deleted);
                            if (lead_id > 0) {
                                Log.d(TAG + " InsertLeadsTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertLeadsTable", "Error");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (module_name.equals("Meetings")) {
                    try {
                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");
                            JSONObject name_value_list = obj.getJSONObject("name_value_list");
                            JSONObject name = name_value_list.getJSONObject("name");
                            acc_name = name.getString("value");
                            JSONObject contact_name_ = name_value_list.getJSONObject("contact_name");
                            String contact_name = contact_name_.getString("value");
                            JSONObject status_ = name_value_list.getJSONObject("status");
                            String status = status_.getString("value");
                            JSONObject location_ = name_value_list.getJSONObject("location");
                            String location = location_.getString("value");
                            JSONObject date_start_ = name_value_list.getJSONObject("date_start");
                            String date_start = date_start_.getString("value");
                            JSONObject duration_hours_ = name_value_list.getJSONObject("duration_hours");
                            String duration_hours = duration_hours_.getString("value");
                            JSONObject duration_minutes_ = name_value_list.getJSONObject("duration_minutes");
                            String duration_minutes = duration_minutes_.getString("value");
                            JSONObject assigned_user_name_ = name_value_list.getJSONObject("assigned_user_name");
                            String assigned_user_name = assigned_user_name_.getString("value");
                            JSONObject description_ = name_value_list.getJSONObject("description");
                            String description = description_.getString("value");
                            JSONObject created_by_name_ = name_value_list.getJSONObject("created_by_name");
                            String created_by_name = created_by_name_.getString("value");
                            JSONObject date_entered_ = name_value_list.getJSONObject("date_entered");
                            String date_entered = date_entered_.getString("value");
                            JSONObject date_modified_ = name_value_list.getJSONObject("date_modified");
                            String date_modified = date_modified_.getString("value");
                            JSONObject deleted_ = name_value_list.getJSONObject("deleted");
                            String deleted = deleted_.getString("value");

                            entry_ids.add(id);

                            long meetings_id = databseAdapter.InsertMeetingsTable(id, acc_name, contact_name, status, location, date_start, duration_hours, duration_minutes, description, assigned_user_name,
                                    created_by_name, date_entered, date_modified, deleted);
                            if (meetings_id > 0) {
                                Log.d(TAG + " InsertMeetingsTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertMeetingsTable", "Error");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (module_name.equals("Opportunities")) {
                    try {
                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");
                            JSONObject name_value_list = obj.getJSONObject("name_value_list");
                            JSONObject name = name_value_list.getJSONObject("name");
                            acc_name = name.getString("value");
                            JSONObject account_name_ = name_value_list.getJSONObject("account_name");
                            String account_name = account_name_.getString("value");
                            JSONObject amount_ = name_value_list.getJSONObject("amount");
                            String amount = amount_.getString("value");
                            JSONObject amount_usdollar_ = name_value_list.getJSONObject("amount_usdollar");
                            String amount_usdollar = amount_usdollar_.getString("value");
                            JSONObject assigned_user_id_ = name_value_list.getJSONObject("assigned_user_id");
                            String assigned_user_id = assigned_user_id_.getString("value");
                            JSONObject assigned_user_name_ = name_value_list.getJSONObject("assigned_user_name");
                            String assigned_user_name = assigned_user_name_.getString("value");
                            JSONObject campaign_name_ = name_value_list.getJSONObject("campaign_name");
                            String campaign_name = campaign_name_.getString("value");
                            JSONObject created_by_ = name_value_list.getJSONObject("created_by");
                            String created_by = created_by_.getString("value");
                            JSONObject created_by_name_ = name_value_list.getJSONObject("created_by_name");
                            String created_by_name = created_by_name_.getString("value");
                            JSONObject date_entered_ = name_value_list.getJSONObject("date_entered");
                            String date_entered = date_entered_.getString("value");
                            JSONObject currency_id_ = name_value_list.getJSONObject("currency_id");
                            String currency_id = currency_id_.getString("value");
                            JSONObject currency_name_ = name_value_list.getJSONObject("currency_name");
                            String currency_name = currency_name_.getString("value");
                            JSONObject currency_symbol_ = name_value_list.getJSONObject("currency_symbol");
                            String currency_symbol = currency_symbol_.getString("value");
                            JSONObject date_closed_ = name_value_list.getJSONObject("date_closed");
                            String date_closed = date_closed_.getString("value");
                            JSONObject date_modified_ = name_value_list.getJSONObject("date_modified");
                            String date_modified = date_modified_.getString("value");
                            JSONObject description_ = name_value_list.getJSONObject("description");
                            String description = description_.getString("value");
                            JSONObject lead_source_ = name_value_list.getJSONObject("lead_source");
                            String lead_source = lead_source_.getString("value");
                            JSONObject modified_by_name_ = name_value_list.getJSONObject("modified_by_name");
                            String modified_by_name = modified_by_name_.getString("value");
                            JSONObject modified_user_id_ = name_value_list.getJSONObject("modified_user_id");
                            String modified_user_id = modified_user_id_.getString("value");
                            JSONObject next_step_ = name_value_list.getJSONObject("next_step");
                            String next_step = next_step_.getString("value");
                            JSONObject opportunity_type_ = name_value_list.getJSONObject("opportunity_type");
                            String opportunity_type = opportunity_type_.getString("value");
                            JSONObject probability_ = name_value_list.getJSONObject("probability");
                            String probability = probability_.getString("value");
                            JSONObject sales_stage_ = name_value_list.getJSONObject("sales_stage");
                            String sales_stage = sales_stage_.getString("value");
                            JSONObject deleted_ = name_value_list.getJSONObject("deleted");
                            String deleted = deleted_.getString("value");

                            entry_ids.add(id);

                            long opportunity_id = databseAdapter.InsertOpportunitiesTable(id, acc_name, account_name, amount, amount_usdollar, assigned_user_id, assigned_user_name, campaign_name, created_by, created_by_name,
                                    currency_id, currency_name, currency_symbol, date_closed, date_entered, date_modified, description, lead_source, modified_by_name, modified_user_id,
                                    next_step, opportunity_type, probability, sales_stage, deleted);
                            if (opportunity_id > 0) {
                                Log.d(TAG + " InsertOpportunitiesTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertOpportunitiesTable", "Error");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (module_name.equals("Campaigns")) {
                    try {
                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");

                            entry_ids.add(id);

                        /*    long opportunity_id = databseAdapter.InsertOpportunitiesTable(id, acc_name, account_name, amount, amount_usdollar, assigned_user_id, assigned_user_name, campaign_name,created_by, created_by_name,
                                    currency_id, currency_name, currency_symbol, date_closed,date_entered, date_modified, description, lead_source, modified_by_name, modified_user_id,
                                    next_step, opportunity_type, probability, sales_stage, deleted);
                            if (opportunity_id > 0) {
                                Log.d(TAG + " InsertOpportunitiesTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertOpportunitiesTable", "Error");
                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (module_name.equals("ProspectLists")) {
                    try {
                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");

                            entry_ids.add(id);

                        /*    long opportunity_id = databseAdapter.InsertOpportunitiesTable(id, acc_name, account_name, amount, amount_usdollar, assigned_user_id, assigned_user_name, campaign_name,created_by, created_by_name,
                                    currency_id, currency_name, currency_symbol, date_closed,date_entered, date_modified, description, lead_source, modified_by_name, modified_user_id,
                                    next_step, opportunity_type, probability, sales_stage, deleted);
                            if (opportunity_id > 0) {
                                Log.d(TAG + " InsertOpportunitiesTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertOpportunitiesTable", "Error");
                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (module_name.equals("Prospects")) {
                    try {
                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");

                            entry_ids.add(id);

                        /*    long opportunity_id = databseAdapter.InsertOpportunitiesTable(id, acc_name, account_name, amount, amount_usdollar, assigned_user_id, assigned_user_name, campaign_name,created_by, created_by_name,
                                    currency_id, currency_name, currency_symbol, date_closed,date_entered, date_modified, description, lead_source, modified_by_name, modified_user_id,
                                    next_step, opportunity_type, probability, sales_stage, deleted);
                            if (opportunity_id > 0) {
                                Log.d(TAG + " InsertOpportunitiesTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertOpportunitiesTable", "Error");
                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (module_name.equals("Tasks")) {
                    try {
                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");

                            entry_ids.add(id);

                        /*    long opportunity_id = databseAdapter.InsertOpportunitiesTable(id, acc_name, account_name, amount, amount_usdollar, assigned_user_id, assigned_user_name, campaign_name,created_by, created_by_name,
                                    currency_id, currency_name, currency_symbol, date_closed,date_entered, date_modified, description, lead_source, modified_by_name, modified_user_id,
                                    next_step, opportunity_type, probability, sales_stage, deleted);
                            if (opportunity_id > 0) {
                                Log.d(TAG + " InsertOpportunitiesTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertOpportunitiesTable", "Error");
                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (module_name.equals("Bugs")) {
                    try {
                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");

                            entry_ids.add(id);

                        /*    long opportunity_id = databseAdapter.InsertOpportunitiesTable(id, acc_name, account_name, amount, amount_usdollar, assigned_user_id, assigned_user_name, campaign_name,created_by, created_by_name,
                                    currency_id, currency_name, currency_symbol, date_closed,date_entered, date_modified, description, lead_source, modified_by_name, modified_user_id,
                                    next_step, opportunity_type, probability, sales_stage, deleted);
                            if (opportunity_id > 0) {
                                Log.d(TAG + " InsertOpportunitiesTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertOpportunitiesTable", "Error");
                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (module_name.equals("Bugs")) {
                    try {
                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");

                            entry_ids.add(id);

                        /*    long opportunity_id = databseAdapter.InsertOpportunitiesTable(id, acc_name, account_name, amount, amount_usdollar, assigned_user_id, assigned_user_name, campaign_name,created_by, created_by_name,
                                    currency_id, currency_name, currency_symbol, date_closed,date_entered, date_modified, description, lead_source, modified_by_name, modified_user_id,
                                    next_step, opportunity_type, probability, sales_stage, deleted);
                            if (opportunity_id > 0) {
                                Log.d(TAG + " InsertOpportunitiesTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertOpportunitiesTable", "Error");
                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (module_name.equals("Project")) {
                    try {
                        JSONObject responseJObj = new JSONObject(response);
                        JSONArray jArray = responseJObj.getJSONArray(ENTRY_LIST);

                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject obj = jArray.getJSONObject(i);
                            String id = obj.getString("id");

                            entry_ids.add(id);

                        /*    long opportunity_id = databseAdapter.InsertOpportunitiesTable(id, acc_name, account_name, amount, amount_usdollar, assigned_user_id, assigned_user_name, campaign_name,created_by, created_by_name,
                                    currency_id, currency_name, currency_symbol, date_closed,date_entered, date_modified, description, lead_source, modified_by_name, modified_user_id,
                                    next_step, opportunity_type, probability, sales_stage, deleted);
                            if (opportunity_id > 0) {
                                Log.d(TAG + " InsertOpportunitiesTable", "Successful");
                            } else {
                                Log.d(TAG + " InsertOpportunitiesTable", "Error");
                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }// ----------------------------------------------- didnt add target,targetlist,bug,projects,campaigns,task to sqlite
            else {
                if (module_name.equals("Accounts")) {
                    try {
                        Cursor csr = databseAdapter.getAllAccount();
                        int i = 0;
                        String[] moduleId = new String[(csr.getCount() - 1)];
                        String[] moduleLabel = new String[(csr.getCount() - 1)];
                        csr.moveToFirst();

                        while (csr.moveToNext()) {
                            moduleId[i] = csr.getString(0);
                            moduleLabel[i] = csr.getString(1);
                            entry_ids.add(moduleId[i]);
                            entry_names.add(moduleLabel[i]);
                            i++;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (module_name.equals("Calls")) {
                    try {

                        Cursor csr = databseAdapter.getAllCall();
                        int i = 0;
                        String[] moduleId = new String[(csr.getCount() - 1)];
                        String[] moduleLabel = new String[(csr.getCount() - 1)];
                        csr.moveToFirst();

                        while (csr.moveToNext()) {
                            moduleId[i] = csr.getString(0);
                            moduleLabel[i] = csr.getString(1);
                            entry_ids.add(moduleId[i]);
                            entry_names.add(moduleLabel[i]);
                            i++;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (module_name.equals("Cases")) {
                    try {

                        Cursor csr = databseAdapter.getAllCases();
                        int i = 0;
                        String[] moduleId = new String[(csr.getCount() - 1)];
                        String[] moduleLabel = new String[(csr.getCount() - 1)];
                        csr.moveToFirst();

                        while (csr.moveToNext()) {
                            moduleId[i] = csr.getString(0);
                            moduleLabel[i] = csr.getString(1);
                            entry_ids.add(moduleId[i]);
                            entry_names.add(moduleLabel[i]);
                            i++;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (module_name.equals("Contacts")) {
                    try {

                        Cursor csr = databseAdapter.getAllContacts();
                        int i = 0;
                        String[] moduleId = new String[(csr.getCount() - 1)];
                        String[] firstName = new String[(csr.getCount() - 1)];
                        String[] last_name = new String[(csr.getCount() - 1)];
                        csr.moveToFirst();

                        while (csr.moveToNext()) {
                            moduleId[i] = csr.getString(0);
                            firstName[i] = csr.getString(1);
                            last_name[i] = csr.getString(2);
                            String name = firstName[i] + " " + last_name[i];
                            entry_ids.add(moduleId[i]);
                            entry_names.add(name);
                            i++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (module_name.equals("Leads")) {
                    try {

                        Cursor csr = databseAdapter.getAllLeads();
                        int i = 0;
                        String[] moduleId = new String[(csr.getCount() - 1)];
                        String[] firstName = new String[(csr.getCount() - 1)];
                        String[] last_name = new String[(csr.getCount() - 1)];
                        csr.moveToFirst();

                        while (csr.moveToNext()) {
                            moduleId[i] = csr.getString(0);
                            firstName[i] = csr.getString(1);
                            last_name[i] = csr.getString(2);
                            String name = firstName[i] + " " + last_name[i];
                            entry_ids.add(moduleId[i]);
                            entry_names.add(name);
                            i++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (module_name.equals("Meetings")) {
                    try {

                        Cursor csr = databseAdapter.getAllMeetings();
                        int i = 0;
                        String[] moduleId = new String[(csr.getCount() - 1)];
                        String[] moduleLabel = new String[(csr.getCount() - 1)];
                        csr.moveToFirst();

                        while (csr.moveToNext()) {
                            moduleId[i] = csr.getString(0);
                            moduleLabel[i] = csr.getString(1);
                            entry_ids.add(moduleId[i]);
                            entry_names.add(moduleLabel[i]);
                            i++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (module_name.equals("Opportunities")) {
                    try {

                        Cursor csr = databseAdapter.getAllOpportunities();
                        int i = 0;
                        String[] moduleId = new String[(csr.getCount() - 1)];
                        String[] moduleLabel = new String[(csr.getCount() - 1)];
                        csr.moveToFirst();

                        while (csr.moveToNext()) {
                            moduleId[i] = csr.getString(0);
                            moduleLabel[i] = csr.getString(1);
                            entry_ids.add(moduleId[i]);
                            entry_names.add(moduleLabel[i]);
                            i++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Toast.makeText(getActivity(), "Error ! " + errorMessage, Toast.LENGTH_LONG).show();
                EntriesListAdapter drawerAdapter = new EntriesListAdapter(getActivity().getApplicationContext(), entry_names);
                mEntryList.setAdapter(drawerAdapter);
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            new performSearch().execute(search.getText().toString(), restUrl, sessionId);
            Log.d("Clicked", "Search button clicked");

            return true;
        }

        return false;
    }

    class performSearch extends AsyncTask<String, Void, Boolean> {
        JSONObject jsonObj;
        private String searchString = null;
        private String sessionId = null;
        private String restUrl = null;
        private String[] module1 = {"Accounts"};
        private String offset = null;
        private String maxResults = null;
        private Boolean favorites = false;
        private String response = null;
        private String errorMessage = null;

        @Override
        protected void onPreExecute() {

            try {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Please Wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(true);
                dialog.show();

            } catch (Exception e) {

                e.printStackTrace();

            }
        }

        @Override
        protected Boolean doInBackground(String... params) {

            searchString = params[0];
            restUrl = params[1];
            sessionId = params[2];
            boolean successful = false;

            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(SEARCH_STRING, searchString != null ? searchString : "");
            data.put(MODULES,
                    (module1 != null && module1.length != 0) ? new JSONArray(
                            Arrays.asList(module1)) : "");
            data.put(OFFSET, offset != null ? offset : 0);
            data.put(MAX_RESULTS, maxResults != null ? maxResults : 100);
            // data.put(ASSIGNED_USER_ID, assigned_user_id != null ?
            // assigned_user_id : "");
            data.put(FAVORITES, favorites != null ? favorites : "");

            try {

                String restData = org.json.simple.JSONValue.toJSONString(data);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost req = new HttpPost(restUrl);
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD,
                        SEARCH_BY_MODULE));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, restData));
                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Send POST request
                httpClient.getParams().setBooleanParameter(
                        CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = httpClient.execute(req);

                response = EntityUtils.toString(res.getEntity());
                jsonObj = new JSONObject(response);

                Log.d("searchResult", jsonObj.toString());

            } catch (IOException ioe) {

                successful = true;
                errorMessage = ioe.toString();

            } catch (JSONException e) {

                successful = true;
                e.printStackTrace();
                errorMessage = e.toString();

            } catch (Exception e) {
                successful = true;
                errorMessage = e.toString();

            }

            return successful;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            dialog.dismiss();

            if (result != true) {

                try {

                    JSONArray jsonArray = jsonObj.getJSONArray(ENTRY_LIST);
                    entry_ids.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObj = jsonArray.getJSONObject(i);
                        // Log.d("searchResult",jObject.toString());
                        String module_name;
                        module_name = jsonObj.getString("name");
                        Log.d("module_name", module_name);

                        JSONArray records = jsonObj.getJSONArray("records");
                        Log.d("records", records.toString());

                        for (int j = 0; j < records.length(); j++) {

                            JSONObject obj = records.getJSONObject(j);

                            JSONObject id_ = obj.getJSONObject("id");
                            String id = id_.getString("value");

                            entry_ids.add(id);

                            JSONObject name = obj.getJSONObject("name");
                            String name_value = name.getString("value");
                            Log.d("name", name_value);

                            entry_co.add(name_value);
                            // Log.d("entry_names - Contacts",
                            // entry_contacts.toString());

                        }

                    }

                    EntriesListAdapter draweradapter = new EntriesListAdapter(
                            getActivity().getApplicationContext(), entry_co);

                    mEntryList.setAdapter(draweradapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(getActivity(), "Error !! " + errorMessage, Toast.LENGTH_LONG).show();

            }

        }
    }

    //Go to individual entry details on Drawer item click
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long ids) {
        try {
            Log.v("TAG", "Drawer Item clicked");
            account_name = entry_names.get(position);
            entry_id = entry_ids.get(position);
            EntriesDetailsController fragment = new EntriesDetailsController();
            //Put EntryId, module label into bundle
            Bundle b = new Bundle();
            b.putString("sessionId", sessionId);
            b.putString("module_label", module_name);
            b.putString("entry_id", entry_id);
            b.putString("restUrl", restUrl);

          /*  Log.d(TAG + " sessionId", sessionId);
            Log.d(TAG + " module name" , module_name);
            Log.d(TAG + " entry_id", entry_id);
            Log.d(TAG + " restUrl" , restUrl);
*/
            //Set bundle into fragment
            fragment.setArguments(b);
            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
            Log.d("Entry name", account_name + " click");
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

}