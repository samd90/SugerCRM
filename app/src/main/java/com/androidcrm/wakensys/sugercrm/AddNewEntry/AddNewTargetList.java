package com.androidcrm.wakensys.sugercrm.AddNewEntry;

import android.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.INPUT_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.JSON;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.METHOD;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.MODULE_NAME;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.NAME_VALUE_LIST;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.RESPONSE_TYPE;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.REST_DATA;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SESSION;
import static com.androidcrm.wakensys.sugercrm.AdapterClass.RestUtilConstants.SET_ENTRY;

import com.androidcrm.wakensys.sugercrm.R;
import com.androidcrm.wakensys.sugercrm.fragment.Fragment_Entries;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sameera on 2/27/2015.
 */
public class AddNewTargetList extends Fragment implements View.OnClickListener {

    public final static String TAG = AddNewTargetList.class.getSimpleName();
    private Button btn_save, btn_cancel;
    private Spinner typeSpinner;
    private EditText txtName, txtDescription, txtDomain;
    private List<String> typeList;
    private ArrayAdapter<String> typeArrayAdapter;
    private String typeName, sessionId, restUrl, module_name, id;
    private boolean from_edit = false;
    private ProgressDialog dialog;

    public static AddNewTargetList newInstance() {
        return new AddNewTargetList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_new_targetlist, container, false);
        // Get Reference of Buttons
        btn_save = (Button) rootView.findViewById(R.id.btn_save);
        btn_cancel = (Button) rootView.findViewById(R.id.btn_cancel);
        // Get Reference of EditTexts
        txtName = (EditText) rootView.findViewById(R.id.txt_name);
        txtDescription = (EditText) rootView.findViewById(R.id.txt_description);
        txtDomain = (EditText) rootView.findViewById(R.id.txt_domain);
        // Get Reference of Spinners
        typeSpinner = (Spinner) rootView.findViewById(R.id.spinner_type);
        // Add value to TypeList ArrayList
        typeList = new ArrayList<String>();
        typeList.add("Default");
        typeList.add("Seed");
        typeList.add("Suppression List - By Domain");
        typeList.add("Suppression List - By Email Address");
        typeList.add("Suppression List - By Id");
        typeList.add("Test");
        // Add Type Array to ArrayAdapter
        typeArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, typeList);
        typeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set TypeArray adapter to Spinner Type
        typeSpinner.setAdapter(typeArrayAdapter);
        // Set onItemClickListner to Buttons
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        // Get sessionId and RestUrl from AddNewItem_selectMenu class
        Bundle b = getArguments();
        sessionId = b.getString("sessionId");
        restUrl = b.getString("restUrl");
        module_name = b.getString("module_name");
        String response = b.getString("prospectListsDetails");
        from_edit = b.getBoolean("from_edit");

        // if Request came from TargetListEdit Button
        if (from_edit == true) {
            try {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Please Wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();

                JSONObject name_value_list = new JSONObject(response);
                Log.d(TAG + " jsonObj", "" + name_value_list.toString());

                JSONObject name_ = name_value_list.getJSONObject("name");
                String name = name_.getString("value");

                JSONObject description_ = name_value_list
                        .getJSONObject("description");
                String description = description_.getString("value");

                JSONObject list_type_ = name_value_list.getJSONObject("list_type");
                String list_type = list_type_.getString("value");

                JSONObject id_ = name_value_list
                        .getJSONObject("id");
                id = id_.getString("value");

                // Put String values to Textviews
                txtName.setText(name);
                txtDescription.setText(description);
                // Set value in Spinner
                typeSpinner.setSelection(((ArrayAdapter<String>) typeSpinner.getAdapter()).getPosition(list_type));
                if (list_type.equals("Suppression List - By Domain")) {
                    txtDomain.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }
        // Set Onitem select listner for Spinner
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeName = parent.getItemAtPosition(position).toString();
                if (typeName.equals("Suppression List - By Domain")) {
                    txtDomain.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if (from_edit == true) {
                    new UpdateRecord().execute(sessionId, restUrl, module_name, typeName, id);

                } else {
                    new AddNewTargetDetails().execute(sessionId, restUrl, module_name, typeName);
                }
                break;
            case R.id.btn_cancel:
                Fragment_Entries fragment = new Fragment_Entries();
                Bundle b = new Bundle();
                b.putString("restUrl", restUrl);
                b.putString("sessionId", sessionId);
                b.putString("module_name", module_name);
                fragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                break;
            default:
                break;
        }

    }

    class AddNewTargetDetails extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog pDialog;
        private String sessionId = null;
        private String restUrl = null;
        private String moduleName = null;
        private String typeName = null;
        private String name = txtName.getText().toString();
        private String description = txtDescription.getText().toString();
        private String domain = txtDomain.getText().toString();

        private JSONObject nameValue = new JSONObject();
        private JSONObject descripValue = new JSONObject();
        private JSONObject domainValue = new JSONObject();
        private JSONObject typeNameValue = new JSONObject();

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean successfull = false;
            sessionId = params[0];
            restUrl = params[1];
            module_name = params[2];
            typeName = params[3];

            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, module_name);
            try {
                JSONArray nameValueArray = new JSONArray();
                nameValue.put("name", "name");
                nameValue.put("value", name);
                nameValueArray.put(nameValue);
                descripValue.put("name", "description");
                descripValue.put("value", description);
                nameValueArray.put(descripValue);
                domainValue.put("name", "domain_name");
                domainValue.put("value", domain);
                nameValueArray.put(domainValue);
                typeNameValue.put("name", "list_type");
                typeNameValue.put("value", typeName);
                nameValueArray.put(typeNameValue);

                data.put(NAME_VALUE_LIST, nameValueArray);
                String rest_data = org.json.simple.JSONValue.toJSONString(data);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost req = new HttpPost(restUrl);
                // Add Data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD, SET_ENTRY));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, rest_data));
                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Send POST request
                httpClient.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = httpClient.execute(req);
                if (res.getEntity() == null) {
                    Log.e(TAG, "FAILED TO CONNECT!");

                }
                String response = EntityUtils.toString(res.getEntity()).toString();
                JSONObject jsonResponse = new JSONObject(response);
                Log.i(TAG, "setEntry response : " + response);
                Log.d(TAG, nameValuePairs.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                successfull = true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                successfull = true;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                successfull = true;
            } catch (IOException e) {
                e.printStackTrace();
                successfull = true;
            }

            return successfull;
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

    class UpdateRecord extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog pDialog;
        private String sessionId = null;
        private String restUrl = null;
        private String module_name = null;
        private String typeName = null;
        private String name = txtName.getText().toString();
        private String description = txtDescription.getText().toString();
        private String domain = txtDomain.getText().toString();

        private JSONObject nameValue = new JSONObject();
        private JSONObject idValue = new JSONObject();
        private JSONObject descripValue = new JSONObject();
        private JSONObject domainValue = new JSONObject();
        private JSONObject typeNameValue = new JSONObject();

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean successfull = false;
            sessionId = params[0];
            restUrl = params[1];
            module_name = params[2];
            typeName = params[3];
            id = params[4];

            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, module_name);
            try {
                JSONArray nameValueArray = new JSONArray();
                idValue.put("name", "id");
                idValue.put("value", id);
                nameValueArray.put(idValue);
                nameValue.put("name", "name");
                nameValue.put("value", name);
                nameValueArray.put(nameValue);
                descripValue.put("name", "description");
                descripValue.put("value", description);
                nameValueArray.put(descripValue);
                domainValue.put("name", "domain_name");
                domainValue.put("value", domain);
                nameValueArray.put(domainValue);
                typeNameValue.put("name", "list_type");
                typeNameValue.put("value", typeName);
                nameValueArray.put(typeNameValue);

                data.put(NAME_VALUE_LIST, nameValueArray);
                String rest_data = org.json.simple.JSONValue.toJSONString(data);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost req = new HttpPost(restUrl);
                // Add Data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(METHOD, SET_ENTRY));
                nameValuePairs.add(new BasicNameValuePair(INPUT_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(RESPONSE_TYPE, JSON));
                nameValuePairs.add(new BasicNameValuePair(REST_DATA, rest_data));
                req.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Send POST request
                httpClient.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse res = httpClient.execute(req);
                if (res.getEntity() == null) {
                    Log.e(TAG, "FAILED TO CONNECT!");

                }
                String response = EntityUtils.toString(res.getEntity()).toString();
                JSONObject jsonResponse = new JSONObject(response);
                Log.i(TAG, "setEntry response : " + response);
                Log.d(TAG, nameValuePairs.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                successfull = true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                successfull = true;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                successfull = true;
            } catch (IOException e) {
                e.printStackTrace();
                successfull = true;
            }

            return successfull;
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
