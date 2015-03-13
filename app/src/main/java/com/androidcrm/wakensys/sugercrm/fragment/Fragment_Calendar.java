package com.androidcrm.wakensys.sugercrm.fragment;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.CalendarView.OnDateChangeListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.CalendarView;

import com.androidcrm.wakensys.sugercrm.AdapterClass.EntriesListAdapter;
import com.androidcrm.wakensys.sugercrm.R;

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

import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.DELETED;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.ENTRY_LIST;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.GET_ENTRY_LIST;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.INPUT_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.JSON;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.LINK_NAME_TO_FIELDS_ARRAY;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MAX_RESULTS;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.METHOD;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MODULE_NAME;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.OFFSET;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.ORDER_BY;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.QUERY;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RESPONSE_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.REST_DATA;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SELECT_FIELDS;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SESSION;

/**
 * Created by Wakensys on 3/10/2015.
 */
public class Fragment_Calendar extends Fragment implements AdapterView.OnItemClickListener {

    public final static String TAG = Fragment_Calendar.class
            .getSimpleName();
    CalendarView calendar;
    List<String> meeting_names = new ArrayList<String>();
    List<String> entry_ids = new ArrayList<String>();
    EntriesListAdapter drawerAdapter;
    private String sessionId = null;
    private String rest_url = null;
    private String module_name = null;
    private List<String> meeting_start_date = new ArrayList<String>();
    private ListView meeting_list;
    private String response = null;

    public static Fragment_Calendar newInstance() {
        return new Fragment_Calendar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_view, container, false);

        Bundle b = getArguments();
        //Get Rest Url
        rest_url = b.getString("restUrl");
        sessionId = b.getString("sessionId");
        module_name = "Meetings";


        calendar = (CalendarView) rootView.findViewById(R.id.calendar);
        meeting_list = (ListView) rootView.findViewById(R.id.list_meeting);
        meeting_list.setOnItemClickListener(this);
        meeting_list.setAdapter(null);

        calendar.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Toast.makeText(getActivity(),
                        dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                String get_calender_click_date = year + "-" + checkDigit(month + 1) + "-" + checkDigit(dayOfMonth);
                get_avilable_meetings(get_calender_click_date);
            }
        });
        //Execute getrecord class for get meeting deting details
        new GetRecord().execute(rest_url, sessionId, module_name);

        return rootView;
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    private void get_avilable_meetings(String get_calender_click_date) {

        try {
            JSONObject responceObject = new JSONObject(response);
            JSONArray jsonArray = responceObject.getJSONArray(ENTRY_LIST);

            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject obj = jsonArray.getJSONObject(j);
                String id = obj.getString("id");
                JSONObject nameValueList = obj.getJSONObject("name_value_list");
                JSONObject date_start_ = nameValueList
                        .getJSONObject("date_start");
                String date_start = date_start_.getString("value");
                String date = null;
                if (date_start != null && !date_start.equals("")) {
                    String a[] = date_start.split(" ");
                    date = a[0];
                }
                if (get_calender_click_date.equals(date)) {

                    JSONObject name_value = nameValueList.getJSONObject("name");
                    String name = name_value.getString("value");

                    meeting_names.clear();
                    meeting_names.add(name);
                    entry_ids.add(id);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        drawerAdapter = new EntriesListAdapter(getActivity().getApplicationContext(), meeting_names);
        meeting_list.setAdapter(drawerAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Log.v("TAG", "Drawer Item clicked");
            // String entry_name = meeting_names.get(position);
            module_name = "Meetings";
            String entry_id = entry_ids.get(position);
            EntriesDetailsController fragment = new EntriesDetailsController();
            //Put EntryId, module label into bundle
            Bundle b = new Bundle();
            b.putString("sessionId", sessionId);
            b.putString("module_name", module_name);
            b.putString("entry_id", entry_id);
            b.putString("restUrl", rest_url);

          /*
            Log.d(TAG + " sessionId", sessionId);
            Log.d(TAG + " module name" , module_name);
            Log.d(TAG + " entry_id", entry_id);
            Log.d(TAG + " restUrl" , restUrl);
          */
            //Set bundle into fragment
            fragment.setArguments(b);
            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class GetRecord extends AsyncTask<String, Void, Boolean> {
        private String query, orderBy, offset, maxResults, deleted, acc_name, entry_id;
        private String sessionId = null;
        private String rest_url = null;
        private boolean successful = false;
        private String errorMessage = null;
        private String moduleLabel = null;
        private ProgressDialog dialog;
        private String[] selectFields;
        private Map<String, List<String>> linkNameToFieldsArray;

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

            rest_url = params[0];
            sessionId = params[1];
            module_name = params[2];

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
                    for (Map.Entry<String, List<String>> entry : linkNameToFieldsArray
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
                HttpPost req = new HttpPost(rest_url);
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


            }
        }
    }
}
