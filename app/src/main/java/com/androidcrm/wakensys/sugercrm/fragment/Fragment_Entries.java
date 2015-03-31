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
import com.androidcrm.wakensys.sugercrm.data_sync.DatabaseHandler;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public final static String TAG = Fragment_Entries.class
            .getSimpleName();
    public static List<String> entry_names;
    public static List<String> entry_ids;
    public static List<String> entry_cnt_names;
    public static String account_name;
    public static List<String> display_entry;
    private ProgressDialog dialog;
    private EditText search;
    private String query, orderBy, offset, maxResults, deleted, acc_name, entry_id;
    private String[] selectFields;
    private Map<String, List<String>> linkNameToFieldsArray;
    public static String module_name;
    // Button btnFav;
    private ListView mEntryList;
    private List<String> entry_co;
    private List<String> module_names;
    private String sessionId = null;
    private String restUrl = null;
    private String module_label = null;

    DatabaseHandler db;

    public static Fragment_Entries newInstance() {
        return new Fragment_Entries();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_entries, container,
                false);
        try {
            db = new DatabaseHandler(getActivity());

            mEntryList = (ListView) rootView.findViewById(R.id.lv_getEntry);
            mEntryList.setOnItemClickListener(this);
            entry_cnt_names = new ArrayList<String>();
            display_entry = new ArrayList<String>();
            entry_co = new ArrayList<String>();
            search = (EditText) rootView.findViewById(R.id.et_search);
            search.setOnEditorActionListener(this);
            entry_names = new ArrayList<String>();
            entry_ids = new ArrayList<String>();
            module_names = new ArrayList<String>();
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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            new performSearch().execute(search.getText().toString(), restUrl, sessionId);
            Log.d("Clicked", "Search button clicked");

            return true;
        }

        return false;
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
            b.putString("module_name", module_name);
            b.putString("entry_id", entry_id);
            b.putString("restUrl", restUrl);

            //Set bundle into fragment
            fragment.setArguments(b);
            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
            Log.d("Entry name", account_name + " click");
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    class LoadingModuleLayout extends AsyncTask<String, Void, Boolean> {

        private String sessionId = null;
        private String restUrl = null;
        private String response = null;
        private boolean successful = false;
        private String errorMessage = null;
        private String moduleLabel = null;
        String other_module_name;

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

                Log.e(TAG + "response is ", response);

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
                        String id = obj.getString("id");
                        other_module_name = obj.getString("module_name");

                        module_names.add(other_module_name);
                        entry_ids.add(id);
                        String nameValue = name_value.getString("value");

                        entry_names.add(nameValue);
                    }
                    //Add entry_names Array to array adapter
                    EntriesListAdapter draweradapter = new EntriesListAdapter(getActivity().getApplicationContext(), entry_names);
                    mEntryList.setAdapter(draweradapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }// ----------------------------------------------- didnt add target,targetList,bug,projects,campaigns,task to sqlite
            else {

                    //  Do the SQLite



                    Toast.makeText(getActivity(), "Error ! " + errorMessage, Toast.LENGTH_LONG).show();
                    EntriesListAdapter drawerAdapter = new EntriesListAdapter(getActivity().getApplicationContext(), entry_names);
                    mEntryList.setAdapter(drawerAdapter);

            }
        }
    }

    class performSearch extends AsyncTask<String, Void, Boolean> {
        JSONObject jsonObj;
        private String searchString = null;
        private String sessionId = null;
        private String restUrl = null;
        private String offset = null;
        private String maxResults = null;
        private String[] module1 = {module_label};
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

}