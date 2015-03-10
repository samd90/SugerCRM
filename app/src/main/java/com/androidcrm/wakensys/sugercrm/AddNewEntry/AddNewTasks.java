package com.androidcrm.wakensys.sugercrm.AddNewEntry;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

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
public class AddNewTasks extends Fragment implements View.OnClickListener {
    public static final String TAG = AddNewTasks.class.getSimpleName();
    private String sessionId, restUrl, id;
    private EditText txt_subject, txt_start_date, txt_start_time, txt_end_date, txt_end_time, txt_entry_name, txt_description, txt_contact_name;
    private Button btn_save, btn_cancel, btn_start_date, btn_start_time, btn_end_date, btn_end_time;
    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Spinner prioritySpinner, statusSpinner, relatedTo_moduleName;
    private List<String> priorityList, statusList, relatedModuleNameList;
    private ArrayAdapter<String> priorityAdapter, status2Adapter, relatedModuleNameAdapter;
    private String module_name, priority, status_2_name, related_module_name, response, start_time, start_date, end_date, end_time;
    private boolean from_edit = false;
    private ProgressDialog dialog;

    public static AddNewTasks newInstance() {
        return new AddNewTasks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_new_tasks, container, false);

        // Get sessionId and RestUrl from AddNewItem_selectMenu class
        Bundle b = getArguments();
        sessionId = b.getString("sessionId");
        restUrl = b.getString("restUrl");
        from_edit = b.getBoolean("from_edit");
        response = b.getString("response");
        module_name = b.getString("module_name");

        txt_subject = (EditText) rootView.findViewById(R.id.txt_subject);
        txt_start_date = (EditText) rootView.findViewById(R.id.txt_start_date);
        txt_start_time = (EditText) rootView.findViewById(R.id.txt_start_time);
        txt_end_date = (EditText) rootView.findViewById(R.id.txt_end_date);
        txt_end_time = (EditText) rootView.findViewById(R.id.txt_end_time);
        txt_entry_name = (EditText) rootView.findViewById(R.id.txt_entry_name);
        txt_contact_name = (EditText) rootView.findViewById(R.id.txt_contact_name);
        txt_description = (EditText) rootView.findViewById(R.id.txt_description);
        // Get the reference of buttons
        btn_save = (Button) rootView.findViewById(R.id.btn_save);
        btn_cancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btn_start_date = (Button) rootView.findViewById(R.id.btn_start_date);
        btn_start_time = (Button) rootView.findViewById(R.id.btn_start_time);
        btn_end_date = (Button) rootView.findViewById(R.id.btn_end_date);
        btn_end_time = (Button) rootView.findViewById(R.id.btn_end_time);
        // Get the reference of Spinners
        statusSpinner = (Spinner) rootView.findViewById(R.id.spinner_in_out);
        relatedTo_moduleName = (Spinner) rootView.findViewById(R.id.spinner_entry_name);
        prioritySpinner = (Spinner) rootView.findViewById(R.id.spinner_priority);

        // Set on ClickListener on buttons
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_start_date.setOnClickListener(this);
        btn_start_time.setOnClickListener(this);
        btn_end_date.setOnClickListener(this);
        btn_end_time.setOnClickListener(this);
        // Array list for DurationMinutes
        priorityList = new ArrayList<String>();
        priorityList.add("High");
        priorityList.add("Medium");
        priorityList.add("Low");
        // Array list for Status Spinner 2
        statusList = new ArrayList<String>();
        statusList.add("Not Started");
        statusList.add("In Progress");
        statusList.add("Completed");
        statusList.add("Pending Input");
        statusList.add("Deferred");
        // Array list for Related to Modle names
        relatedModuleNameList = new ArrayList<String>();
        relatedModuleNameList.add("Account");
        relatedModuleNameList.add("Bug");
        relatedModuleNameList.add("Case");
        relatedModuleNameList.add("Contact");
        relatedModuleNameList.add("Lead");
        relatedModuleNameList.add("Opportunity");
        relatedModuleNameList.add("Project");
        relatedModuleNameList.add("Project Task");
        relatedModuleNameList.add("Target");
        relatedModuleNameList.add("Task");

        // Set array lists to Array adapters
        status2Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, statusList);
        relatedModuleNameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, relatedModuleNameList);
        priorityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, priorityList);

        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relatedModuleNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set Array Adapter to Spinner
        statusSpinner.setAdapter(status2Adapter);
        relatedTo_moduleName.setAdapter(relatedModuleNameAdapter);
        prioritySpinner.setAdapter(priorityAdapter);

        if(from_edit == true){
            try{
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Please wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show ();

                JSONObject name_value_list = new JSONObject(response);

                JSONObject name_ = name_value_list.getJSONObject("name");
                String name = name_.getString("value");

                JSONObject priority_ = name_value_list.getJSONObject("priority");
                String priority = priority_.getString("value");

                JSONObject description_ = name_value_list.getJSONObject("description");
                String description = description_.getString("value");

                JSONObject id_ = name_value_list.getJSONObject("id");
                id = id_.getString("value");

                JSONObject status_ = name_value_list
                        .getJSONObject("status");
                String status_name = status_.getString("value");

                JSONObject contact_name_ = name_value_list
                        .getJSONObject("contact_name");
                String contact_name = contact_name_.getString("value");

                JSONObject date_start_ = name_value_list
                        .getJSONObject("date_start");
                String date_start = date_start_.getString("value");

                JSONObject date_due_ = name_value_list
                        .getJSONObject("date_due");
                String date_due = date_due_.getString("value");

                JSONObject time_due_ = name_value_list
                        .getJSONObject("time_due");
                String time_due = time_due_.getString("value");

                JSONObject parent_name_ = name_value_list
                        .getJSONObject("parent_name");
                String parent_name = parent_name_.getString("value");

                JSONObject parent_type_ = name_value_list
                        .getJSONObject("parent_type");
                String parent_type = parent_type_.getString("value");

                if (date_due != null && !date_due.equals("")) {
                    String[] a = date_due.split(" ");
                    end_date = a[0];
                    end_time = a[1];
                }
                if (date_start != null && !date_start.equals("")) {
                    String[] e = date_start.split(" ");
                    start_date = e[0];
                    start_time = e[1];
                }
                txt_subject.setText(name);
                txt_start_date.setText(start_date);
                txt_start_time.setText(start_time);
                txt_end_date.setText(end_date);
                txt_end_time.setText(end_time);
                txt_entry_name.setText(parent_name);
                txt_contact_name.setText(contact_name);
                txt_description.setText(description);

                statusSpinner.setSelection(((ArrayAdapter<String>) statusSpinner.getAdapter()).getPosition(status_name));
                relatedTo_moduleName.setSelection(((ArrayAdapter<String>) relatedTo_moduleName.getAdapter()).getPosition(parent_type));
                prioritySpinner.setSelection(((ArrayAdapter<String>) prioritySpinner.getAdapter()).getPosition(priority));

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }
        // Set OnItemSelectListeners to Spinners
        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status_2_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        relatedTo_moduleName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                related_module_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
                                txt_start_date.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
                break;
            case R.id.btn_start_time:
                // Process to get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // Display Selected time in textbox
                                txt_start_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                tpd.show();
                break;
            case R.id.btn_end_date:
                // Process to get Current Date
                final Calendar cal1 = Calendar.getInstance();
                mYear = cal1.get(Calendar.YEAR);
                mMonth = cal1.get(Calendar.MONTH);
                mDay = cal1.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd1 = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textBox
                                txt_end_date.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                dpd1.show();
                break;
            case R.id.btn_end_time:
                // Process to get Current Time
                final Calendar c1 = Calendar.getInstance();
                mHour = c1.get(Calendar.HOUR_OF_DAY);
                mMinute = c1.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog tpd1 = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // Display Selected time in textBox
                                txt_end_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                tpd1.show();
                break;
            case R.id.btn_save:
                if (from_edit == true) {
                    new UpdateRecord().execute(priority, status_2_name, related_module_name, restUrl, sessionId, id);
                } else {
                    new AddNewEntryTask().execute(priority, status_2_name, related_module_name, restUrl, sessionId);
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
        private String priority, status_2_name, related_module_name, restUrl, sessionId;

        private Map<String, String> nameValueList;
        private ProgressDialog dialog;
        private String subject = txt_subject.getText().toString();
        private String startTime = txt_start_time.getText().toString();
        private String start_date = txt_start_date.getText().toString();
        private String start_time = startTime + ":" + "00";
        private String end_date = txt_end_date.getText().toString();
        private String endTime = txt_end_time.getText().toString();
        private String end_time = endTime + ":" + "00";
        private String contactName = txt_contact_name.getText().toString();
        private String description = txt_description.getText().toString();
        private String parentName = txt_entry_name.getText().toString();

        private JSONObject subjectValue = new JSONObject();
        private JSONObject idValue = new JSONObject();
        private JSONObject startDateTimeValue = new JSONObject();
        private JSONObject dateDueValue = new JSONObject();
        private JSONObject timeDueValue = new JSONObject();
        private JSONObject statusValue = new JSONObject();
        private JSONObject priorityValue = new JSONObject();
        private JSONObject descriptionValue = new JSONObject();
        private JSONObject contactValue = new JSONObject();
        private JSONObject parentNameValue = new JSONObject();
        private JSONObject parentTypeValue = new JSONObject();

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
            priority = params[0];
            status_2_name = params[1];
            related_module_name = params[2];
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
                priorityValue.put("name", "priority");
                priorityValue.put("value", priority);
                nameValueArray.put(priorityValue);
                descriptionValue.put("name", "description");
                descriptionValue.put("value", description);
                nameValueArray.put(descriptionValue);
                statusValue.put("name", "status");
                statusValue.put("value", status_2_name);
                nameValueArray.put(statusValue);
                contactValue.put("name", "contact_name");
                contactValue.put("value", contactName);
                nameValueArray.put(contactValue);
                startDateTimeValue.put("name", "date_start");
                startDateTimeValue.put("value", start_date + " " + start_time);
                nameValueArray.put(startDateTimeValue);
                dateDueValue.put("name", "date_due");
                dateDueValue.put("value", end_date + " " + end_time);
                nameValueArray.put(dateDueValue);
                timeDueValue.put("name", "time_due");
                timeDueValue.put("value", end_time);
                nameValueArray.put(timeDueValue);
                parentNameValue.put("name", "parent_name");
                parentNameValue.put("value", parentName);
                nameValueArray.put(parentNameValue);
                parentTypeValue.put("name", "parent_type");
                parentTypeValue.put("value", related_module_name);
                nameValueArray.put(parentTypeValue);

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
                    successful = true;
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

    class AddNewEntryTask extends AsyncTask<String, Void, Boolean> {
        private String priority, status_2_name, related_module_name, restUrl, sessionId;

        private Map<String, String> nameValueList;
        private ProgressDialog dialog;
        private String subject = txt_subject.getText().toString();
        private String startTime = txt_start_time.getText().toString();
        private String start_date = txt_start_date.getText().toString();
        private String start_time = startTime+ ":" + "00";
        private String end_date = txt_end_date.getText().toString();
        private String endTime = txt_end_time.getText().toString();
        private String end_time = endTime + ":" + "00";
        private String contactName = txt_contact_name.getText().toString();
        private String description = txt_description.getText().toString();
        private String parentName = txt_entry_name.getText().toString();

        private JSONObject subjectValue = new JSONObject();
        private JSONObject startDateTimeValue = new JSONObject();
        private JSONObject dateDueValue = new JSONObject();
        private JSONObject timeDueValue = new JSONObject();
        private JSONObject statusValue = new JSONObject();
        private JSONObject priorityValue = new JSONObject();
        private JSONObject descriptionValue = new JSONObject();
        private JSONObject contactValue = new JSONObject();
        private JSONObject parentNameValue = new JSONObject();
        private JSONObject parentTypeValue = new JSONObject();

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
            priority = params[0];
            status_2_name = params[1];
            related_module_name = params[2];
            restUrl = params[3];
            sessionId = params[4];

            boolean successful = false;
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put(SESSION, sessionId);
            data.put(MODULE_NAME, module_name);

            try {
                JSONArray nameValueArray = new JSONArray();
                subjectValue.put("name", "name");
                subjectValue.put("value", subject);
                nameValueArray.put(subjectValue);
                priorityValue.put("name", "priority");
                priorityValue.put("value", priority);
                nameValueArray.put(priorityValue);
                descriptionValue.put("name", "description");
                descriptionValue.put("value", description);
                nameValueArray.put(descriptionValue);
                statusValue.put("name", "status");
                statusValue.put("value", status_2_name);
                nameValueArray.put(statusValue);
                contactValue.put("name", "contact_name");
                contactValue.put("value", contactName);
                nameValueArray.put(contactValue);
                startDateTimeValue.put("name", "date_start");
                startDateTimeValue.put("value", start_date+" "+start_time);
                nameValueArray.put(startDateTimeValue);
                dateDueValue.put("name", "date_due");
                dateDueValue.put("value", end_date + " " +end_time);
                nameValueArray.put(dateDueValue);
                timeDueValue.put("name", "time_due");
                timeDueValue.put("value", end_time);
                nameValueArray.put(timeDueValue);
                parentNameValue.put("name", "parent_name");
                parentNameValue.put("value", parentName);
                nameValueArray.put(parentNameValue);
                parentTypeValue.put("name", "parent_type");
                parentTypeValue.put("value", related_module_name);
                nameValueArray.put(parentTypeValue);

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
                    successful = true;
                }
                String response = EntityUtils.toString(res.getEntity()).toString();
                JSONObject jsonResponse = new JSONObject(response);
                Log.i(TAG, "setEntry response : " + response);
                Log.d(TAG,nameValuePairs.toString());


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
                                b.putString("module_name", module_name);
                                fragment.setArguments(b);
                                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, TAG).addToBackStack(TAG).commit();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        }

    }
}
