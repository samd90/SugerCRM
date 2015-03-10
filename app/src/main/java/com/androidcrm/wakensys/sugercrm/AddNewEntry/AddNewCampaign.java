package com.androidcrm.wakensys.sugercrm.AddNewEntry;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class AddNewCampaign extends Fragment implements View.OnClickListener {

    public static final String TAG = AddNewCampaign.class.getSimpleName();
    private String id, sessionId, restUrl, response;
    private boolean from_edit = false;
    private EditText txt_subject, txt_date_start, txt_date_end, txt_budget, txt_actual_cost, txt_objective, txt_description, txt_impressions, txt_expected_cost, txt_expected_revenue;
    private Button btn_save, btn_cancel, btn_start_date, btn_end_date;
    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Spinner statusSpinner, typeSpinner, frequencySpinner;
    private List<String> currencyList, statusList, typeList, frequencyList;
    private ArrayAdapter<String> currencyAdapter, statusAdapter, typeAdapter, frequencyAdapter;
    private String module_name, status_name, currency_name, type_name, frequency_name;
    private ProgressDialog dialog;

    public static AddNewCampaign newInstance() {
        return new AddNewCampaign();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_new_campaign, container, false);

        // Get sessionId and RestUrl from AddNewItem_selectMenu class
        Bundle b = getArguments();
        sessionId = b.getString("sessionId");
        restUrl = b.getString("restUrl");
        response = b.getString("campaignsDetails");
        from_edit = b.getBoolean("from_edit");
        module_name = b.getString("module_name");

        txt_subject = (EditText) rootView.findViewById(R.id.txt_subject);
        txt_date_start = (EditText) rootView.findViewById(R.id.txt_start_date);
        txt_date_end = (EditText) rootView.findViewById(R.id.txt_end_date);
        txt_budget = (EditText) rootView.findViewById(R.id.txt_budjet);
        txt_actual_cost = (EditText) rootView.findViewById(R.id.txt_actual_cost);
        txt_objective = (EditText) rootView.findViewById(R.id.txt_objective);
        txt_description = (EditText) rootView.findViewById(R.id.txt_description);
        txt_impressions = (EditText) rootView.findViewById(R.id.txt_impressions);
        txt_expected_cost = (EditText) rootView.findViewById(R.id.txt_ex_cost);
        txt_expected_revenue = (EditText) rootView.findViewById(R.id.txt_ex_revenue);

        // Get the reference of buttons
        btn_save = (Button) rootView.findViewById(R.id.btn_save);
        btn_cancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btn_end_date = (Button) rootView.findViewById(R.id.btn_end_date);
        btn_start_date = (Button) rootView.findViewById(R.id.btn_start_date);
        // Get the reference of Spinners
        statusSpinner = (Spinner) rootView.findViewById(R.id.spinner_status);
        typeSpinner = (Spinner) rootView.findViewById(R.id.spinner_type);
        frequencySpinner = (Spinner) rootView.findViewById(R.id.spinner_frequency);
        // Set on ClickListener on buttons
        btn_save.setOnClickListener(this);
        btn_start_date.setOnClickListener(this);
        btn_end_date.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        // Array list for Status Spinner 1
        statusList = new ArrayList<String>();
        statusList.add(" ");
        statusList.add("Active");
        statusList.add("Inactive");
        statusList.add("Complete");
        statusList.add("In Queue");
        statusList.add("Sending");
        // Array list for Status Spinner 2
        typeList = new ArrayList<String>();
        typeList.add(" ");
        typeList.add("Telesales");
        typeList.add("Mail");
        typeList.add("Email");
        typeList.add("Print");
        typeList.add("Web");
        typeList.add("Radio");
        typeList.add("Television");
        typeList.add("Newsletter");
        // Array List for Frequency Spinner
        frequencyList = new ArrayList<String>();
        frequencyList.add(" ");
        frequencyList.add("Weekly");
        frequencyList.add("Monthly");
        frequencyList.add("Quarterly");
        frequencyList.add("Annually");

        // Set array lists to Array adapters
        statusAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, statusList);
        typeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, typeList);
        frequencyAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, frequencyList);

        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set Array Adapter to Spinner
        statusSpinner.setAdapter(statusAdapter);

        typeSpinner.setAdapter(typeAdapter);
        frequencySpinner.setAdapter(frequencyAdapter);

        if (from_edit == true) {
            try {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Please wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();

                JSONObject name_value_list = new JSONObject(response);

                JSONObject name_ = name_value_list.getJSONObject("name");
                String name = name_.getString("value");

                JSONObject start_date_ = name_value_list.getJSONObject("start_date");
                String start_date = start_date_.getString("value");

                JSONObject end_date_ = name_value_list.getJSONObject("end_date");
                String end_date = end_date_.getString("value");

                JSONObject id_ = name_value_list.getJSONObject("id");
                id = id_.getString("value");

                JSONObject budget_ = name_value_list
                        .getJSONObject("budget");
                String budget = budget_.getString("value");

                JSONObject actual_cost_ = name_value_list
                        .getJSONObject("actual_cost");
                String actual_cost = actual_cost_.getString("value");

                JSONObject objective_ = name_value_list
                        .getJSONObject("objective");
                String objective = objective_.getString("value");

                JSONObject description_ = name_value_list
                        .getJSONObject("description");
                String description = description_.getString("value");

                JSONObject impressions_ = name_value_list
                        .getJSONObject("impressions");
                String impressions = impressions_.getString("value");

                JSONObject expected_cost_ = name_value_list
                        .getJSONObject("expected_cost");
                String expected_cost = expected_cost_.getString("value");

                JSONObject expected_revenue_ = name_value_list
                        .getJSONObject("expected_revenue");
                String expected_revenue = expected_revenue_.getString("value");

                JSONObject status_ = name_value_list
                        .getJSONObject("status");
                String status = status_.getString("value");

                JSONObject campaign_type_ = name_value_list
                        .getJSONObject("campaign_type");
                String campaign_type = campaign_type_.getString("value");

                JSONObject frequency_ = name_value_list
                        .getJSONObject("frequency");
                String frequency = frequency_.getString("value");

                txt_subject.setText(name);
                txt_date_start.setText(start_date);
                txt_date_end.setText(end_date);
                txt_budget.setText(budget);
                txt_actual_cost.setText(actual_cost);
                txt_objective.setText(objective);
                txt_description.setText(description);
                txt_impressions.setText(impressions);
                txt_expected_cost.setText(expected_cost);
                txt_expected_revenue.setText(expected_revenue);

                statusSpinner.setSelection(((ArrayAdapter<String>) statusSpinner.getAdapter()).getPosition(status));
                typeSpinner.setSelection(((ArrayAdapter<String>) typeSpinner.getAdapter()).getPosition(campaign_type));
                if (type_name.equals("Newsletter")) {
                    frequencySpinner.setVisibility(View.VISIBLE);
                    frequencySpinner.setSelection(((ArrayAdapter<String>) frequencySpinner.getAdapter()).getPosition(frequency));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_name = parent.getItemAtPosition(position).toString();
                if (type_name.equals("Newsletter")) {
                    frequencySpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                frequency_name = parent.getItemAtPosition(position).toString();
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
            case R.id.btn_start_date:
                // Process to get Current Date
                final Calendar cal = Calendar.getInstance();
                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                txt_date_start.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
                break;
            case R.id.btn_end_date:
                // Process to get Current Date
                final Calendar ca = Calendar.getInstance();
                mYear = ca.get(Calendar.YEAR);
                mMonth = ca.get(Calendar.MONTH);
                mDay = ca.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dp = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                txt_date_end.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                dp.show();
                break;
            case R.id.btn_save:
                if (from_edit == true) {
                    new UpdateRecord().execute(status_name, type_name, frequency_name, restUrl, sessionId, id);
                } else {
                    new AddRecord().execute(status_name, type_name, frequency_name, restUrl, sessionId);
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

    class UpdateRecord extends AsyncTask<String, Void, Boolean> {
        private String durationMinutes, status_1_name, status_2_name, reminder_time_period, frequency_name, related_module_name, email_reminder_time_period;

        private Map<String, String> nameValueList;
        private ProgressDialog dialog;
        private String subject = txt_subject.getText().toString();
        private String start_date = txt_date_start.getText().toString();
        private String end_date = txt_date_end.getText().toString();
        private String budget = txt_budget.getText().toString();
        private String actualCost = txt_actual_cost.getText().toString();
        private String objective = txt_objective.getText().toString();
        private String description = txt_description.getText().toString();
        private String impressions = txt_impressions.getText().toString();
        private String expected_cost = txt_expected_cost.getText().toString();
        private String expected_revenue = txt_expected_revenue.getText().toString();

        private JSONObject subjectValue = new JSONObject();
        private JSONObject idValue = new JSONObject();
        private JSONObject start_dateValue = new JSONObject();
        private JSONObject end_dateValue = new JSONObject();
        private JSONObject budgetValue = new JSONObject();
        private JSONObject actualCostValue = new JSONObject();
        private JSONObject objectiveValue = new JSONObject();
        private JSONObject descriptionValue = new JSONObject();
        private JSONObject impressionsValue = new JSONObject();
        private JSONObject expected_costValue = new JSONObject();
        private JSONObject expected_revenueValue = new JSONObject();

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please Wait..");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            status_name = params[0];
            type_name = params[1];
            frequency_name = params[2];
            restUrl = params[3];
            sessionId = params[4];
            id = params[5];

            boolean successful = false;
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, module_name);

            try {
                JSONArray nameValueArray = new JSONArray();
                idValue.put("name", "id");
                idValue.put("value", id);
                nameValueArray.put(idValue);
                subjectValue.put("name", "name");
                subjectValue.put("value", subject);
                nameValueArray.put(subjectValue);
                start_dateValue.put("name", "start_date");
                start_dateValue.put("value", start_date);
                nameValueArray.put(start_dateValue);
                end_dateValue.put("name", "end_date");
                end_dateValue.put("value", end_date);
                nameValueArray.put(end_dateValue);
                budgetValue.put("name", "budget");
                budgetValue.put("value", budget);
                nameValueArray.put(budgetValue);
                actualCostValue.put("name", "actual_cost");
                actualCostValue.put("value", actualCost);
                nameValueArray.put(actualCostValue);
                objectiveValue.put("name", "objective");
                objectiveValue.put("value", objective);
                nameValueArray.put(objectiveValue);
                descriptionValue.put("name", "description");
                descriptionValue.put("value", description);
                nameValueArray.put(descriptionValue);
                impressionsValue.put("name", "impressions");
                impressionsValue.put("value", impressions);
                nameValueArray.put(impressionsValue);
                expected_costValue.put("name", "expected_cost");
                expected_costValue.put("value", expected_cost);
                nameValueArray.put(expected_costValue);
                expected_revenueValue.put("name", "expected_revenue");
                expected_revenueValue.put("value", expected_revenue);
                nameValueArray.put(expected_revenueValue);

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

    class AddRecord extends AsyncTask<String, Void, Boolean> {
        private String durationMinutes, status_1_name, status_2_name, reminder_time_period, frequency_name, related_module_name, email_reminder_time_period;

        private Map<String, String> nameValueList;
        private ProgressDialog dialog;
        private String subject = txt_subject.getText().toString();
        private String start_date = txt_date_start.getText().toString();
        private String end_date = txt_date_end.getText().toString();
        private String budget = txt_budget.getText().toString();
        private String actualCost = txt_actual_cost.getText().toString();
        private String objective = txt_objective.getText().toString();
        private String description = txt_description.getText().toString();
        private String impressions = txt_impressions.getText().toString();
        private String expected_cost = txt_expected_cost.getText().toString();
        private String expected_revenue = txt_expected_revenue.getText().toString();

        private JSONObject subjectValue = new JSONObject();
        private JSONObject start_dateValue = new JSONObject();
        private JSONObject end_dateValue = new JSONObject();
        private JSONObject budgetValue = new JSONObject();
        private JSONObject actualCostValue = new JSONObject();
        private JSONObject objectiveValue = new JSONObject();
        private JSONObject descriptionValue = new JSONObject();
        private JSONObject impressionsValue = new JSONObject();
        private JSONObject expected_costValue = new JSONObject();
        private JSONObject expected_revenueValue = new JSONObject();

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please Wait..");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            status_name = params[0];
            type_name = params[1];
            frequency_name = params[2];
            restUrl = params[3];
            sessionId = params[4];

            boolean successful = false;
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, "Campaigns");

            try {
                JSONArray nameValueArray = new JSONArray();
                subjectValue.put("name", "name");
                subjectValue.put("value", subject);
                nameValueArray.put(subjectValue);
                start_dateValue.put("name", "start_date");
                start_dateValue.put("value", start_date);
                nameValueArray.put(start_dateValue);
                end_dateValue.put("name", "end_date");
                end_dateValue.put("value", end_date);
                nameValueArray.put(end_dateValue);
                budgetValue.put("name", "budget");
                budgetValue.put("value", budget);
                nameValueArray.put(budgetValue);
                actualCostValue.put("name", "actual_cost");
                actualCostValue.put("value", actualCost);
                nameValueArray.put(actualCostValue);
                objectiveValue.put("name", "objective");
                objectiveValue.put("value", objective);
                nameValueArray.put(objectiveValue);
                descriptionValue.put("name", "description");
                descriptionValue.put("value", description);
                nameValueArray.put(descriptionValue);
                impressionsValue.put("name", "impressions");
                impressionsValue.put("value", impressions);
                nameValueArray.put(impressionsValue);
                expected_costValue.put("name", "expected_cost");
                expected_costValue.put("value", expected_cost);
                nameValueArray.put(expected_costValue);
                expected_revenueValue.put("name", "expected_revenue");
                expected_revenueValue.put("value", expected_revenue);
                nameValueArray.put(expected_revenueValue);

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
                                b.putString("moduleName", "Campaigns");
                                fragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        }

    }
}
