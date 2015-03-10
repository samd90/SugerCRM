package com.androidcrm.wakensys.sugercrm.AddNewEntry;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
public class AddNewCases extends Fragment implements View.OnClickListener {

    public static final String TAG = AddNewCases.class.getSimpleName();

    public static AddNewCases newInstance() {
        return new AddNewCases();
    }

    private String id, module_name, sessionId, restUrl, status_name, type_name, state_name, priority_name, response, account_name, resolution, type, status,state, priority,name;
    private boolean from_edit = false;
    private EditText txt_name, txt_web, txt_oPhone, txt_resolution, txt_account_name;
    private Button btn_save, btn_cancel;
    private Spinner prioritySelectSpinner, stateSelectSpinner, statusSelectSpinner, typeSelectSpinner;
    private List<String> priorityItem, stateItem, statusItem, typeItem;
    private ArrayAdapter<String> priorityDataAdapter, stateDataAdapter, statusDataAdapter, typeDataAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_new_case, container, false);

        // Get sessionId and RestUrl from AddNewItem_selectMenu class
        Bundle b = getArguments();
        sessionId = b.getString("sessionId");
        restUrl = b.getString("restUrl");
        from_edit = b.getBoolean("from_edit");
        response = b.getString("caseDetails");
        module_name = b.getString("module_name");

        txt_name = (EditText) rootView.findViewById(R.id.txt_subject);
        txt_resolution = (EditText) rootView.findViewById(R.id.txt_resolution);
        txt_account_name = (EditText) rootView.findViewById(R.id.txt_account_name);

        prioritySelectSpinner = (Spinner) rootView.findViewById(R.id.spinner_pri);
        statusSelectSpinner = (Spinner) rootView.findViewById(R.id.spinner_status);
        stateSelectSpinner = (Spinner) rootView.findViewById(R.id.spinner_state);
        typeSelectSpinner = (Spinner) rootView.findViewById(R.id.spinner_type);

        // Array list for Priority Spinner
        priorityItem = new ArrayList<String>();
        priorityItem.add("High");
        priorityItem.add("Medium");
        priorityItem.add("Low");

        // Array list for Status Spinner
        statusItem = new ArrayList<String>();
        statusItem.add("New");
        statusItem.add("Assigned");
        statusItem.add("Pending Input");

        // Array list for State Spinner
        stateItem = new ArrayList<String>();
        stateItem.add("Open");
        stateItem.add("Closed");

        // Array list for Type Spinner
        typeItem = new ArrayList<String>();
        typeItem.add("Administration");
        typeItem.add("Product");
        typeItem.add("User");

        priorityDataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, priorityItem);
        statusDataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, statusItem);
        stateDataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stateItem);
        typeDataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, typeItem);

        priorityDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        statusSelectSpinner.setAdapter(statusDataAdapter);          // Set adapter to Status spinner
        stateSelectSpinner.setAdapter(stateDataAdapter);            // Set adapter to State spinner
        typeSelectSpinner.setAdapter(typeDataAdapter);              // Set adapter to type spinner
        prioritySelectSpinner.setAdapter(priorityDataAdapter);       // Set adapter to priority spinner

        // Set onclick listners to Spinners
        statusSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // on select spinner item position
                status_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        stateSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typeSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        prioritySelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_save = (Button) rootView.findViewById(R.id.btn_save);
        btn_cancel = (Button) rootView.findViewById(R.id.btn_cancel);

        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        if(from_edit == true) {
            try {
                JSONObject name_value_list = new JSONObject(response);

                JSONObject name_ = name_value_list.getJSONObject("name");
                name = name_.getString("value");

                JSONObject priority_ = name_value_list.getJSONObject("priority");
                priority = priority_.getString("value");

                JSONObject state_ = name_value_list.getJSONObject("state");
                state = state_.getString("value");

                JSONObject status_ = name_value_list.getJSONObject("status");
                status = status_.getString("value");

                JSONObject type_ = name_value_list.getJSONObject("type");
                type = type_.getString("value");

                JSONObject resolution_ = name_value_list.getJSONObject("resolution");
                resolution = resolution_.getString("value");

                JSONObject account_name_ = name_value_list.getJSONObject("account_name");
                account_name = account_name_.getString("value");

                JSONObject id_ = name_value_list.getJSONObject("id");
                id = id_.getString("value");

                txt_name.setText(name);
                txt_resolution.setText(resolution);
                txt_account_name.setText(account_name);

                statusSelectSpinner.setSelection(((ArrayAdapter<String>) statusSelectSpinner.getAdapter()).getPosition(status));
                stateSelectSpinner.setSelection(((ArrayAdapter<String>) stateSelectSpinner.getAdapter()).getPosition(status));
                typeSelectSpinner.setSelection(((ArrayAdapter<String>) typeSelectSpinner.getAdapter()).getPosition(type));
                prioritySelectSpinner.setSelection(((ArrayAdapter<String>) prioritySelectSpinner.getAdapter()).getPosition(priority));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if(from_edit == false){
                    new EditRecord().execute(sessionId, restUrl, status_name, type_name, state_name, priority_name, id);

                }else {
                    new AddNewEntryAccount().execute(sessionId, restUrl, status_name, type_name, state_name, priority_name);

                }break;
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
    class EditRecord extends AsyncTask<String, Void, Boolean> {
        private String sessionId, restUrl, status_name, type_name, state_name, priority_name;
        private Map<String, String> nameValueList;
        private ProgressDialog pDialog;
        private String name = txt_name.getText().toString();
        private String resolution = txt_resolution.getText().toString();
        private String account_name = txt_account_name.getText().toString();

        private JSONObject nameValue = new JSONObject();
        private JSONObject resolutionValue = new JSONObject();
        private JSONObject accountNameValue = new JSONObject();
        private JSONObject statusValue = new JSONObject();
        private JSONObject stateValue = new JSONObject();
        private JSONObject priorityValue = new JSONObject();
        private JSONObject typeValue = new JSONObject();
        private JSONObject idValue = new JSONObject();

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            sessionId = params[0];
            restUrl = params[1];
            status_name = params[2];
            type_name = params[3];
            state_name = params[4];
            priority_name = params[5];
            id = params[6];
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
                resolutionValue.put("name", "resolution");
                resolutionValue.put("value", resolution);
                nameValueArray.put(resolutionValue);
                accountNameValue.put("name", "account_name");
                accountNameValue.put("value", account_name);
                nameValueArray.put(accountNameValue);
                statusValue.put("name", "status");
                statusValue.put("value", status_name);
                nameValueArray.put(statusValue);
                stateValue.put("name", "state");
                stateValue.put("value", state_name);
                nameValueArray.put(stateValue);
                priorityValue.put("name", "priority");
                priorityValue.put("value", priority_name);
                nameValueArray.put(priorityValue);
                typeValue.put("name", "type");
                typeValue.put("value", type_name);
                nameValueArray.put(typeValue);

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
            if (result != true) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Alert")
                        .setMessage("Record Successfully Added.")
                        .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Fragment_Entries fragment = new Fragment_Entries();
                                Bundle b = new Bundle();
                                b.putString("restUrl", restUrl);
                                b.putString("sessionId", sessionId);
                                b.putString("module_name", module_name);
                                fragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        }

    }

    class AddNewEntryAccount extends AsyncTask<String, Void, Boolean> {
        private String sessionId, restUrl, status_name, type_name, state_name, priority_name;
        private Map<String, String> nameValueList;
        private ProgressDialog pDialog;
        private String name = txt_name.getText().toString();
        private String resolution = txt_resolution.getText().toString();
        private String account_name = txt_account_name.getText().toString();

        private JSONObject nameValue = new JSONObject();
        private JSONObject resolutionValue = new JSONObject();
        private JSONObject accountNameValue = new JSONObject();
        private JSONObject statusValue = new JSONObject();
        private JSONObject stateValue = new JSONObject();
        private JSONObject priorityValue = new JSONObject();
        private JSONObject typeValue = new JSONObject();

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            sessionId = params[0];
            restUrl = params[1];
            status_name = params[2];
            type_name = params[3];
            state_name = params[4];
            priority_name = params[5];
            boolean successful = false;

            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, "Accounts");

            try {
                JSONArray nameValueArray = new JSONArray();
                nameValue.put("name", "name");
                nameValue.put("value", name);
                nameValueArray.put(nameValue);
                resolutionValue.put("name", "resolution");
                resolutionValue.put("value", resolution);
                nameValueArray.put(resolutionValue);
                accountNameValue.put("name", "account_name");
                accountNameValue.put("value", account_name);
                nameValueArray.put(accountNameValue);
                statusValue.put("name", "status");
                statusValue.put("value", status_name);
                nameValueArray.put(statusValue);
                stateValue.put("name", "state");
                stateValue.put("value", state_name);
                nameValueArray.put(stateValue);
                priorityValue.put("name", "priority");
                priorityValue.put("value", priority_name);
                nameValueArray.put(priorityValue);
                typeValue.put("name", "type");
                typeValue.put("value", type_name);
                nameValueArray.put(typeValue);

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
            if (result != true) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Alert")
                        .setMessage("Record Successfully Added.")
                        .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Fragment_Entries fragment = new Fragment_Entries();
                                Bundle b = new Bundle();
                                b.putString("restUrl", restUrl);
                                b.putString("sessionId", sessionId);
                                b.putString("module_name", module_name);
                                fragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        }

    }
}
