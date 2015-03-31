package com.androidcrm.wakensys.sugercrm.fragment;

import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.INPUT_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.JSON;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MAX_RESULTS;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.METHOD;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MODULES;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.OFFSET;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RESPONSE_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.REST_DATA;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SEARCH_BY_MODULE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SEARCH_STRING;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SESSION;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.FAVORITES;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MODULE_NAMES;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.GET_LAST_VIEWED;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.androidcrm.wakensys.sugercrm.AdapterClass.ExpandableListAdapter;
import com.androidcrm.wakensys.sugercrm.AdapterClass.ListAdapter;
import com.androidcrm.wakensys.sugercrm.MainActivity;
import com.androidcrm.wakensys.sugercrm.R;
import com.androidcrm.wakensys.sugercrm.Setting.Setting_homeScreen;
import com.androidcrm.wakensys.sugercrm.data_sync.SessionManagement;

public class Fragment_home extends Fragment implements OnEditorActionListener,  ExpandableListView.OnChildClickListener {

    private String module;
    private JSONObject jObject;
    private JSONObject jsonObj;

    private ProgressDialog dialog;

    private EditText search;
    public static List<String> entry_names;
    public static List<String> entry_ids;
    public static List<String> entry_cnt_names;

    public static List<String> item_names;
    public static List<String> item_ids;

    public static String account_name;
    private String query;
    private String orderBy;
    private String offset, restUrl;
    private String[] selectFields;
    private Map<String, List<String>> linkNameToFieldsArray;
    private String maxResults;
    private String deleted;
    private String acc_name;
    public static List<String> display_entry;
    public static String id, sessionId;
    public static String entry_id;

    // Button btnFav;
    private ListView mEntryList;
    private List<String> entry_co;
    private List<String> module_list = new ArrayList<String>();

    // Variable for che Setting values for displaying Expandable ListView
    boolean accountsSet = true;
    boolean contactSet = true;
    boolean taskSet = true;
    boolean caseSet = true;
    boolean opportunitySet = true;
    boolean meetingSet = true;
    boolean leadSet = true;
    boolean callSet = true;

    String response;
    private TextView text;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private String[] module1 = {"Accounts", "Contacts", "Leads", "Cases", "Calls", "Meetings", "Tasks", "Opportunities"};

    public final static String TAG = Fragment_home.class.getSimpleName();

    public static Fragment_home newInstance() {
        return new Fragment_home();
    }

    // Disable back button on fragments
    public void callParentMethod() {
        getActivity().onBackPressed();
    }

    List<String> accountList;
    List<String> contactList;
    List<String> leadList;
    List<String> caseList;
    List<String> callList;
    List<String> meetingList;
    List<String> opportunityList;
    List<String> taskList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recent, container,
                false);

        sessionId = getArguments().getString("sessionId");
        restUrl = getArguments().getString("restUrl");

        Log.d(TAG, "sessionId" + sessionId + " restUrl " + restUrl);

        new LoadingModuleLayout().execute(sessionId, restUrl);
        // preparing list data

        item_names = new ArrayList<String>();
        item_ids = new ArrayList<String>();

        entry_names = new ArrayList<String>();
        entry_ids = new ArrayList<String>();
        entry_cnt_names = new ArrayList<String>();

        display_entry = new ArrayList<String>();
        entry_co = new ArrayList<String>();

        accountList = new ArrayList<String>();
        contactList = new ArrayList<String>();
        leadList = new ArrayList<String>();
        caseList = new ArrayList<String>();
        callList = new ArrayList<String>();
        meetingList = new ArrayList<String>();
        opportunityList = new ArrayList<String>();
        taskList = new ArrayList<String>();

        SharedPreferences settingPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        search = (EditText) rootView.findViewById(R.id.et_search);

        search.setOnEditorActionListener(this);

        accountsSet = settingPrefs.getBoolean("isAccount", true);
        contactSet = settingPrefs.getBoolean("isContact", true);
        callSet = settingPrefs.getBoolean("isCall", true);
        caseSet = settingPrefs.getBoolean("isCases", true);
        meetingSet = settingPrefs.getBoolean("isMeeting", true);
        opportunitySet = settingPrefs.getBoolean("isOpportunity", true);
        leadSet = settingPrefs.getBoolean("isLeads", true);
        taskSet = settingPrefs.getBoolean("isTasks", true);

        // get the listView
        expListView = (ExpandableListView) rootView.findViewById(R.id.li_ex);
        expListView.setOnChildClickListener(this);

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Log.d(TAG, listDataHeader.get(groupPosition).toString());
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Log.d(TAG, listDataHeader.get(groupPosition).toString());

            }
        });

        return rootView;
    }


    class LoadingModuleLayout extends AsyncTask<String, Void, Boolean> {
        private boolean successful = false;
        private String item_summary;
        List<String> item_summaries_accounts_list = new ArrayList<String>();
        List<String> item_summaries_contacts_list = new ArrayList<String>();
        List<String> item_summaries_calls_list = new ArrayList<String>();
        List<String> item_summaries_tasks_list = new ArrayList<String>();
        List<String> item_summaries_cases_list = new ArrayList<String>();
        List<String> item_summaries_leads_list = new ArrayList<String>();
        List<String> item_summaries_opportunities_list = new ArrayList<String>();
        List<String> item_summaries_meetings_list = new ArrayList<String>();

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {

                sessionId = params[0];
                restUrl = params[1];

                Map<String, Object> data = new LinkedHashMap<String, Object>();
                data.put(SESSION, sessionId);
                data.put(
                        MODULE_NAMES,
                        (module1 != null && module1.length != 0) ? new JSONArray(
                                Arrays.asList(module1)) : "");


                String restData = org.json.simple.JSONValue.toJSONString(data);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost req = new HttpPost(restUrl);
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD, GET_LAST_VIEWED));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, restData));

                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Send POST request
                httpClient.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = null;

                res = httpClient.execute(req);

                Log.e("nameValuePairs", nameValuePairs.toString());
                if (res.getEntity() == null) {
                    Log.e("LOG_TAG", "FAILED TO CONNECT!");
                    successful = true;
                }

                response = EntityUtils.toString(res.getEntity());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                successful = true;
            } catch (IOException e) {
                e.printStackTrace();
                successful = true;
            }


            return successful;
        }


        @Override

        protected void onPostExecute(Boolean result) {
            if (result != true) {

                try {
                    JSONArray jsonArr = new JSONArray(response);
                    Log.d("Recent Items", jsonArr.toString());
                    for (int i = 0; i < jsonArr.length(); i++) {

                        JSONObject JsonObj = null;

                        JsonObj = jsonArr.getJSONObject(i);

                        module = JsonObj.getString("module_name");
                        String id = JsonObj.getString("id");
                        String item_id = JsonObj.getString("item_id");
                        item_summary = JsonObj.getString("item_summary");

                        switch (module) {
                            case "Accounts":

                                item_summaries_accounts_list.add(item_summary);
                                entry_ids.add(item_id);
                                break;
                            case "Contacts":
                                item_summaries_contacts_list.add(item_summary);
                                entry_ids.add(item_id);
                                break;
                            case "Meetings":
                                item_summaries_meetings_list.add(item_summary);
                                entry_ids.add(item_id);
                                break;
                            case "Opportunities":
                                item_summaries_opportunities_list.add(item_summary);
                                entry_ids.add(item_id);
                                break;
                            case "Tasks":
                                item_summaries_tasks_list.add(item_summary);
                                entry_ids.add(item_id);
                                break;
                            case "Leads":
                                item_summaries_leads_list.add(item_summary);
                                entry_ids.add(item_id);
                                break;
                            case "Calls":
                                item_summaries_calls_list.add(item_summary);
                                entry_ids.add(item_id);
                                break;
                            case "Cases":
                                item_summaries_cases_list.add(item_summary);
                                entry_ids.add(item_id);
                                break;
                            default:
                                break;


                        }
                        module_list.add(module);

                    }

                    prepareListData(item_summaries_accounts_list, item_summaries_cases_list, item_summaries_calls_list, item_summaries_leads_list, item_summaries_tasks_list, item_summaries_contacts_list, item_summaries_meetings_list, item_summaries_opportunities_list);

                    listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

                    // setting list adapter
                    expListView.setAdapter(listAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
       * Preparing the list data
       */
    private void prepareListData(List<String> item_summaries_accounts, List<String> item_summaries_cases, List<String> item_summaries_calls, List<String> item_summaries_leads, List<String> item_summaries_tasks, List<String> item_summaries_contacts, List<String> item_summaries_meetings, List<String> item_summaries_opportunities) {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data

        //for (int i = 0; i >= 8; i++) {
        if (accountsSet == true) {
            listDataHeader.add("Accounts");
            listDataChild.put("Accounts", item_summaries_accounts); // Header, Child data
            Log.d(TAG, "module list" + Setting_homeScreen.isAccount);
        }
        if (contactSet == true) {
            listDataHeader.add("Contacts");
            listDataChild.put("Contacts", item_summaries_contacts);
            Log.d(TAG, "module list" + Setting_homeScreen.isContact);
        }
        if (leadSet == true) {
            listDataHeader.add("Leads");
            listDataChild.put("Leads", item_summaries_leads);
            Log.d(TAG, "module list" + Setting_homeScreen.isLeads);
        }
        if (caseSet == true) {
            listDataHeader.add("Cases");
            listDataChild.put("Cases", item_summaries_cases);
            Log.d(TAG, "module list" + Setting_homeScreen.isCases);
        }
        if (callSet == true) {
            listDataHeader.add("Calls");
            listDataChild.put("Calls", item_summaries_calls);
            Log.d(TAG, "module list" + Setting_homeScreen.isCall);
        }
        if (meetingSet == true) {
            listDataHeader.add("Meetings");
            listDataChild.put("Meetings", item_summaries_meetings);
            Log.d(TAG, "module list" + Setting_homeScreen.isMeeting);
        }
        if (taskSet == true) {
            listDataHeader.add("Tasks");
            listDataChild.put("Tasks", item_summaries_tasks);
            Log.d(TAG, "module list" + Setting_homeScreen.isTasks);
        }
        if (opportunitySet == true) {
            listDataHeader.add("Opportunities");
            listDataChild.put("Opportunities", item_summaries_opportunities);
            Log.d(TAG, "module list" + Setting_homeScreen.isOpportunity);
        }
        //}
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        try {

            String item_id_ = entry_ids.get(childPosition);
            String item_name = listDataHeader.get(groupPosition);
            Log.d(TAG,"Item Name" + item_name);
            Log.d(TAG,"Item Id" + item_id_);

            EntriesDetailsController fragment = new EntriesDetailsController();
            Bundle b = new Bundle();
            b.putString("entry_id", item_id_);
            b.putString("sessionId", sessionId);
            b.putString("restUrl", restUrl);
            b.putString("module_name", item_name);
            fragment.setArguments(b);

            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            new performSearch().execute(search.getText().toString());
            Log.d("Clicked", "Search button clicked");

            return true;
        }

        return false;
    }

    class performSearch extends AsyncTask<String, Void, Boolean> {
        String search_string;
        // private String assigned_user_id;
        private Boolean favorites = false;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
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
            search_string = params[0];

            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(SEARCH_STRING, search_string != null ? search_string : "");
            data.put(MODULES, (module1 != null && module1.length != 0) ? new JSONArray(Arrays.asList(module1))
                    : "");
            data.put(OFFSET, offset != null ? offset : 0);
            data.put(MAX_RESULTS, maxResults != null ? maxResults : 100);
            //  data.put(ASSIGNED_USER_ID, assigned_user_id != null ? assigned_user_id : "");
            data.put(FAVORITES, favorites != null ? favorites : "");

            try {
                String restData = org.json.simple.JSONValue.toJSONString(data);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost req = new HttpPost("http://crm2.demoplease.com/service/v4_1/rest.php");
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD, SEARCH_BY_MODULE));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, restData));
                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Send POST request
                httpClient.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = httpClient.execute(req);

                if (res.getEntity() == null) {
                    Log.e("LOG_TAG", "FAILED TO CONNECT!");

                }

                String response = EntityUtils.toString(res.getEntity());
                jsonObj = new JSONObject(response);
                Log.d("searchResult", response);


            } catch (IOException ioe) {

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Boolean result) {

            try {
                dialog.dismiss();
                Bundle bundle = new Bundle();
                String searchResult = jsonObj.toString();
                bundle.putString("searchResult", searchResult);

                Fragment_Entry_Search fragment = new Fragment_Entry_Search();
                fragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}