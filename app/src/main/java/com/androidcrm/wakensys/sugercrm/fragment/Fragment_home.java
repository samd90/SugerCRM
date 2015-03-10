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

import static com.androidcrm.wakensys.sugercrm.MainActivity.moduleNames;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.androidcrm.wakensys.sugercrm.AdapterClass.ListAdapter;
import com.androidcrm.wakensys.sugercrm.R;
import com.androidcrm.wakensys.sugercrm.data_sync.CrmDatabaseAdapter;

public class Fragment_home extends Fragment implements OnClickListener, OnEditorActionListener, AdapterView.OnItemClickListener {


    private JSONObject jObject;
    JSONObject jsonObj;

    private ProgressDialog dialog;

    private EditText search;
    public static List<String> entry_names;
    public static List<String> entry_ids;
    public static List<String> entry_cnt_names;

    public static List<String> item_names;
    public static List<String> item_ids;
    public static List<String> item_summaries;

    public static String account_name;
    private String query;
    private String orderBy;
    private String offset;
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
    private List<String> module_;

    private TextView text;

    CrmDatabaseAdapter databaseAdapter ;

    public final static String TAG = Fragment_home.class.getSimpleName();

    public static Fragment_home newInstance() {
        return new Fragment_home();
    }

    // Disable back button on fragments
    public void callParentMethod() {
        getActivity().onBackPressed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);
        try {

            module_ = new ArrayList<String>();
            search = (EditText) rootView.findViewById(R.id.et_search);

            search.setOnEditorActionListener(this);
            new LoadingModuleLayout().execute();
            // btnFav = (Button) rootView.findViewById(R.id.btn_Fav);
            // btnFav.setOnClickListener(this);
             mEntryList = (ListView) rootView.findViewById(R.id.listView);
              mEntryList.setOnItemClickListener(this);
            Bundle b = getArguments();
            sessionId = b.getString("sessionId");
            item_summaries = new ArrayList<String>();
            item_names = new ArrayList<String>();
            item_ids = new ArrayList<String>();

            entry_names = new ArrayList<String>();
            entry_ids = new ArrayList<String>();
            entry_cnt_names = new ArrayList<String>();

            display_entry = new ArrayList<String>();
            entry_co = new ArrayList<String>();

            databaseAdapter = new CrmDatabaseAdapter(getActivity());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }



    class LoadingModuleLayout extends AsyncTask<String, String, String> {

        private String[] module1 = {"Accounts", "Contacts", "Leads", "Cases", "Calls", "Meetings", "Tasks", "Opportunities"};
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                Log.d("Loading", "Account Details Loading");
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
        protected String doInBackground(String... args) {

            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(
                    MODULE_NAMES,
                    (module1 != null && module1.length != 0) ? new JSONArray(
                            Arrays.asList(module1)) : "");

            try {
                String restData = org.json.simple.JSONValue.toJSONString(data);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost req = new HttpPost("http://crm2.demoplease.com/service/v4_1/rest.php");
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD, GET_LAST_VIEWED));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, restData));
                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Send POST request
                httpClient.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = httpClient.execute(req);
                Log.e("nameValuePairs", nameValuePairs.toString());
                if (res.getEntity() == null) {
                    Log.e("LOG_TAG", "FAILED TO CONNECT!");

                }
                response = EntityUtils.toString(res.getEntity());

            } catch (IOException ioe) {

            }
            return null;
        }


        @Override

        protected void onPostExecute(String file_url) {
            try {
                dialog.dismiss();

                JSONArray jsonArr = new JSONArray(response);
                Log.d("Recent Items", jsonArr.toString());
                for (int i = 0; i < jsonArr.length(); i++) {

                    JSONObject JsonObj = jsonArr.getJSONObject(i);

                    String module_name = JsonObj.getString("module_name");
                    String id = JsonObj.getString("id");
                    String item_summary = JsonObj.getString("item_summary");
                    String item_id = JsonObj.getString("item_id");

                    item_ids.add(item_id);


                    long get_id = databaseAdapter.InsertLastViwed(item_id, id, item_summary, module_name);
                        if (get_id > 0){
                            Log.d("Successful", "Successful");
                        }else
                        {
                            Log.d("Error","error");
                        }
                }

                Cursor curser = databaseAdapter.getLastViewData();
                int i = 0;
                String[] itemLabel = new String[(curser.getCount()-1)];
                String[] moduleName = new String[(curser.getCount()-1)];

                curser.moveToFirst();

                while (curser.moveToNext()) {

                    itemLabel[i] = curser.getString(3);
                    moduleName[i] = curser.getString(4);

                    item_summaries.add(itemLabel[i]);
                    item_names.add(moduleName[i]);

                    i++;
                }

                Log.d("module_name", item_ids.toString());
                Log.d("item_summary", item_summaries.toString());
                Log.d("item_id", item_names.toString());

                ListAdapter menuadapter = new ListAdapter(
                        getActivity().getApplicationContext(), item_names, item_summaries);
                mEntryList.setAdapter(menuadapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            String item_id_ = item_ids.get(position);
            String item_name = item_names.get(position);
            if (item_name.equals("Accounts")) {

                EntriesDetailsController fragment = new EntriesDetailsController();
                Bundle b = new Bundle();
                b.putString("entry_id", item_id_);
                fragment.setArguments(b);


                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();

            } else if (item_name.equals("Contacts")) {

            } else if (item_name.equals("Opportunities")) {


            } else if (item_name.equals("Leads")) {




            } else if (item_name.equals("Calls")) {


            } else if (item_name.equals("Meetings")) {


            } else if (item_name.equals("Cases")) {


            } else if (item_name.equals("Tasks")) {


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        // btnFav.setBackgroundDrawable(getResources().getDrawable(R.drawable.star_select));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            new performSearch(search.getText().toString()).execute();
            Log.d("Clicked", "Search button clicked");

            return true;
        }

        return false;
    }

    class performSearch extends AsyncTask<String, String, String> {


        private String searchString;
        private String[] module1;
        //module1[0] = modules.
        private String offset;
        private String maxResults;
        // private String assigned_user_id;
        private Boolean favorites = false;


        public performSearch(String search) {

            module1 = new String[moduleNames.size()];
            searchString = search;

            for (int ig = 0; ig < moduleNames.size(); ig++) {

                if (!moduleNames.get(ig).equals("Home")) {
                    module1[ig] = moduleNames.get(ig);
                }


            }

        }


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
        protected String doInBackground(String... params) {

            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(SEARCH_STRING, searchString != null ? searchString : "");
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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
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